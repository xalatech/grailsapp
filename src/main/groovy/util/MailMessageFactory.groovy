package util

import sivadm.Intervjuer

import java.text.SimpleDateFormat
/**
 * MailMessageFactory for aa bygge mail-innhold.
 */
public class MailMessageFactory {

	public static String lagPCProblemMelding(Intervjuer intervjuer, Date fra,
			Date til, String merknad) {
		StringBuffer message = createHeader(intervjuer);
		message.append("Fravær: ");
		message.append(createFraTil(fra, til, true));
		message.append("Fraværet skyldes: PC- og telefon problemer");
		message.append(createBegrunnelse(merknad));
		return message.toString();
	}


	public static String lagSykdomMelding(Intervjuer intervjuer, Date fra,
			Date til, String merknad, String aarsak, Boolean ikkeRettFraNav) {
		StringBuffer message = createHeader(intervjuer);
		message.append("Fravær: ");
		message.append(createFraTil(fra, til, true));
		message.append("Fraværet skyldes: ");
		message.append(aarsak);
		if (ikkeRettFraNav) {
			message.append("\n\n");
			message.append("Mottar pensjon/stønad og ikke har rett på sykepenger fra nav");
		}
		message.append(createBegrunnelse(merknad));
		return message.toString();
	}


	public static String lagFerieSoknadMelding(Intervjuer intervjuer, Date fra,
			Date til, String merknad) {
		StringBuffer message = createHeader(intervjuer);
		message.append("Ønsker ferie: ");
		message.append(createFraTil(fra, til, false));
		message.append(createBegrunnelse(merknad));
		return message.toString();
	}


	public static String lagPermisjonSoknadMelding(Intervjuer intervjuer,
			int permisjonType, Date fra, Date til, String merknad) {
		StringBuffer message = createHeader(intervjuer);
		if (fra != null && til != null) {
			if (permisjonType==1)
				message.append("Permisjon med lønn: ");
			else {
				message.append("Permisjon uten lønn: ");
			}
			message.append(createFraTil(fra, til, true));
		}
		message.append(createBegrunnelse(merknad));
		return message.toString();
	}


	public static String lagPermisjonBarnSoknadMelding(Intervjuer intervjuer,
			Date fra, Date til, String merknad, Boolean aleneMedOmsorg,
			Integer antallBarn, Date barnFodselsDato) {
		StringBuffer message = createHeader(intervjuer);
		message.append("Permisjon pga. barns/barnepassers sykdom: ");
		message.append(createFraTil(fra, til, true));
		message.append("Alene om omsorgen: ");
		message.append(aleneMedOmsorg ? "Ja" : "Nei");
		message.append("\n\nAntall barn under 12 år: ");
		message.append(antallBarn);
		message.append("\n\nBarnets fødselsdato: ");
		message.append(DateUtil.DATE_FORMAT.format(barnFodselsDato));
		message.append(createBegrunnelse(merknad));
		return message.toString();
	}


	private static StringBuffer createHeader(Intervjuer intervjuer) {
		StringBuffer message = new StringBuffer();
		message.append("  Dato for utfylling:\t");
		message.append(DateUtil.DATE_FORMAT.format(DateUtil.now()));
		message.append("\n  Navn:\t\t\t");
		message.append(intervjuer.getNavn());
		message.append("\n  Ansattnr:\t\t\t");
		message.append(intervjuer.getIntervjuerNummer() == null ? ""
				: intervjuer.getIntervjuerNummer());
		message.append("\n  Klynge:\t\t\t");
		message.append(intervjuer.getKlynge() == null ? "" : intervjuer
				.getKlynge().getKlyngeNavn());
		message.append("\n-----------------------------------------\n\n");
		return message;
	}


	private static String createFraTil(Date fra, Date til, boolean includeTime) {
		SimpleDateFormat dateFormat = includeTime ? DateUtil.FULL_DATE_FORMAT
				: DateUtil.DATE_FORMAT;
		String fraTil = dateFormat.format(fra);
		fraTil += " til og med ";
		fraTil += dateFormat.format(til);
		fraTil += "\n\n";
		return fraTil;
	}


	private static String createBegrunnelse(String begrunnelse) {
		String begrunnelseMessage = "\n\nBegrunnelse: ";
		begrunnelseMessage += begrunnelse == null ? "Ikke oppgitt"
				: begrunnelse;
		return begrunnelseMessage;
	}

}
