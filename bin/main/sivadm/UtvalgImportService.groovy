package sivadm

import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.StringTokenizer;
import exception.DuplikatIntervjuObjektException;
import parser.ExtendedUtvalg
import parser.Utvalg;
import parser.UtvalgParser;
import service.UtvalgService;
import util.UtvalgUtil;
import siv.model.translator.UtvalgTranslator;
import sivadm.IntervjuObjekt;


class UtvalgImportService {
	
	boolean transactional = false

	def sessionFactory
	
	def oppdragService
	
	
	/**
	 * Importerer et utvalg inn i databasemodellen. Sjekker for endel constraints, og utvalgsfil
	 * maa ogsaa vaere riktig. Uansett om import lykkes eller ikke saa skal status skrives til
	 * UtvalgImport-tabellen.
	 * 
	 * @param utvalgTekst
	 * @param importertAv
	 * @param skjema
	 * @return
	 */
	def importUtvalg(String utvalgTekst, String importertAv, Skjema skjema) {
		
		UtvalgService utvalgService = new UtvalgService()
		
		def utvalgImport
		
		try {
			// lagre en log paa utvalgsimporten
			utvalgImport = new UtvalgImport()
			
			utvalgImport.setAntallImportert(0)
			utvalgImport.setImportDato(new Date())
			utvalgImport.setImportertAv(importertAv)
			utvalgImport.setSkjema(skjema)
			
			List<Utvalg> utvalgList = UtvalgUtil.stringToList(utvalgTekst)
			
			utvalgImport.setAntallFil(utvalgList.size())
			utvalgImport.setMelding("Importerer...")
			
			// lagrer midlertidig status
			utvalgImport.save(failOnError: true, flush: true)
			
			// Sjekk mot duplikater på Periode x IO-nr i utvalgsfila
			// lager 8-tegns nøkkel av Periode + IO-nr
			// Hvis "adding" av nøkkel ikke lykkes, har vi en dublett
			Set<String> ts = new HashSet<String>();
			for (Utvalg uu : utvalgList) {
				String IONummer = uu.getIoNr()
				String key = uu.getPeriodeNrAsZeroFilledString() + IONummer
				if (!ts.add(key)) {
					throw new DuplikatIntervjuObjektException("FEIL: Utvalg for import inneholder duplikater på Periode x IO-nr ($IONummer)")
				}
			}
			
			// egen duplikat sjekk paa intervjuobjektnummer
			if(utvalgService.utvalgInneholderIntervjuObjektDuplikat(utvalgList, skjema )) {
				throw new DuplikatIntervjuObjektException("FEIL: Utvalg for import inneholder intervjuobjektduplikater med allerede importert utvalg")
			}
			
			// importer utvalg inn i modell
			def antallImportert = utvalgService.importUtvalg(utvalgList, importertAv, skjema, utvalgImport)

			// Hvis import har gått bra, og ingen exceptions, oppdater faktisk antall importert
			utvalgImport.setAntallImportert(antallImportert)
			utvalgImport.setMelding ("OK")
			utvalgImport.save(flush:true)
		}
		catch(Exception e) {
			log.error(e.getMessage())	
			def feilMsg = e.getMessage()
			if( feilMsg && feilMsg.length() > 250 ) {
				feilMsg = feilMsg.substring (0, 250)
			}
			utvalgImport.setMelding(feilMsg)
			utvalgImport.save(flush:true)
		}
	}
	
	
	/**
	 * Finner antall io for en utvalg import
	 * @param utvalgImport
	 * @return
	 */
	def finnAntallIoForUtvalgImport( UtvalgImport utvalgImport ) {
		
		def c = IntervjuObjekt.createCriteria()

		def l = c {	
			projections { countDistinct("id") }	
			eq("utvalgImport", utvalgImport)
		}

		return l.get(0)
	}
	
	
	/**
	 * Sletter et utvalg + relasjoner som intervjuobjekt, oppdrag osv.
	 * 
	 * @param utvalgImport
	 * @return
	 */
	def deleteUtvalg( UtvalgImport utvalgImport) {
		
		def intervjuObjekter = IntervjuObjekt.findAllByUtvalgImport( utvalgImport )

		if( intervjuObjekter.size() > 0 ) {
			
			oppdragService.slettOppdragForIntervjuUtvalg(utvalgImport)
			
			int n = 0
			
			intervjuObjekter.each { IntervjuObjekt intervjuObjekt ->
				
				slettSporinger( intervjuObjekt )
				
				slettIntHistStatistikk(intervjuObjekt)
				
				intervjuObjekt.delete()
				
				if( n == 200 ) {
					def session = sessionFactory.getCurrentSession()
					session.flush()
					session.clear()
					n = 0
				}
				
				n ++
			}
		}

		utvalgImport.delete()		
	}
	
	
	/**
	 * Sletter alle sporinger for et intervjuobjekt
	 * 
	 * @param intervjuObjekt
	 * @return
	 */
	def slettSporinger(IntervjuObjekt intervjuObjekt) {
		def sporingList = Sporing.findAllByIntervjuObjekt(intervjuObjekt)
		
		sporingList.each {
			it.delete()
		} 	
	}
	
	def slettIntHistStatistikk(IntervjuObjekt intervjuObjekt) {
		def intHistList = IntHist.findAllByIntervjuObjekt(intervjuObjekt)
		
		intHistList.each {
			it.delete()
		}
	}

	
	/**
	 * Genererer utvalgstekst utfra datamodell. Brukes for nedlasting av eksportfil, og
	 * skriving av eksportfil til fil.
	 *
	 * @param utvalgImport
	 * @return utvalgstekst
	 */
	def getUtvalgAsString( UtvalgImport utvalgImport ) {
		Skjema skjema = utvalgImport.getSkjema()

		def intervjuObjekts = IntervjuObjekt.findAllByUtvalgImport( utvalgImport )

		UtvalgTranslator translator = new UtvalgTranslator()
		List<Utvalg> utvalgList = new ArrayList<Utvalg>()

		intervjuObjekts.each {
			ExtendedUtvalg utvalg = translator.translateToUtvalg(null, it, skjema, false)
			utvalgList.add((Utvalg) utvalg)
		}

		UtvalgParser utvalgParser = new UtvalgParser()
		String utvalgText = utvalgParser.writeToString(utvalgList)
		
		return utvalgText
	}
	
	
	/**
	 * Skriver en liste med intervjuobjekter til en csv-fil. Brukes av bakgrunnsjobb.
	 * 
	 * @param List intervjuObjekts, String fil
	 * @return
	 */
	public skrivUtvalgListeTilFil (List intervjuObjekter, String fil) {
		genererFil(intervjuObjekter, fil)
	}
	
	/**
	 * Skriver en liste med intervjuobjekter til en csv-fil.
	 *
	 * @param List intervjuObjekts, String fil
	 * @return
	 */
	private void genererFil(List intervjuObjekter, String fil) {
		UtvalgTranslator translator = new UtvalgTranslator()
		List<ExtendedUtvalg> extUtvalgList = new ArrayList<ExtendedUtvalg>()

		intervjuObjekter.each {
			ExtendedUtvalg utvalg = translator.translateToUtvalg(null, it, null, true)
			extUtvalgList.add(utvalg)
		}
		
		File utvalgFil = new File(fil)
		utvalgFil.write(extUtvalgList.get(0).getHeading(), "cp1252")
		extUtvalgList.each {
			utvalgFil.append(it.toCSVString(), "cp1252");
		}
	}

	/**
	 * Skriver utvalgstekst til fil. Brukes av bakgrunnsjobb.
	 * 
	 * @param utvalgImport
	 * @return
	 */
	def skrivUtvalgTilFil( UtvalgImport utvalgImport, String fil ) {
		String utvalgText = getUtvalgAsString(utvalgImport)
			
		File utvalgFil = new File(fil)
		utvalgFil.write(utvalgText, "cp1252")
	}
}
