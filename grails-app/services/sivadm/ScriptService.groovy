package sivadm

import org.apache.tools.ant.taskdefs.optional.depend.AntAnalyzer;

import sil.*;
import siv.type.SkjemaStatus;

class ScriptService {

	static transactional = false
	
	def sessionFactory

	public void slettAlleKravOgTimeforinger() {
		
		int count = 0
		
		int antallRunderUtenCacheSletting = 300
		
		// Slett alle koblinger fra Krav mot Krav
		Krav.list().each { Krav krav ->
			krav.opprinneligKrav = null
			krav.silMelding = null
			krav.save( failOnError: true )
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count 
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Slette alle koblinger fra Kjorebok mot SilMelding
		Kjorebok.list().each { Kjorebok kjorebok ->
			kjorebok.silMelding = null
			kjorebok.save( failOnError: true ) 
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Slette alle koblinger fra Utlegg mot SilMelding
		Utlegg.list().each { Utlegg utlegg -> 
			utlegg.silMelding = null
			utlegg.save( failOnError: true)
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Slette alle koblinger fra Timeforinger mot SilMelding
		Timeforing.list().each { Timeforing timeforing -> 
			timeforing.silMelding = null
			timeforing.save( failOnError: true)
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Sletter alle SAP-fil
		SapFil.list().each { SapFil sapFil ->
			sapFil.delete()
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Sletter alle Krav
		Krav.list().each { Krav krav ->
			krav.delete()
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Sletter alle Kjorebok
		Kjorebok.list().each { Kjorebok kjorebok ->
			kjorebok.delete()
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Sletter alle Timeforing
		Timeforing.list().each { Timeforing timeforing ->
			timeforing.delete()
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Sletter alle Utlegg
		Utlegg.list().each { Utlegg utlegg ->
			utlegg.delete()
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		// Sletter alle SilMelding
		SilMelding.list().each { SilMelding silMelding ->
			silMelding.delete()
			
			count ++
			
			log.info "Sletter krav og timer. Steg " + count
			
			if( count > antallRunderUtenCacheSletting ) {
				log.info "___Sletter session cache..."
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				count = 0
			}
		}
		
		
	}
}
