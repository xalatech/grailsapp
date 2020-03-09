package sivadm

import util.StringUtil;

class Sporing {

	IntervjuObjekt intervjuObjekt

	Date tidspunkt

	Boolean adresseEndring
	Boolean nyttTelefonnr
	Boolean navnEndring
	Boolean nyBeboer
	Boolean husholdningEndring
	Boolean ingenting

	Boolean guleSider
	Boolean opplysningen
	Boolean google
	Boolean bereg
	Boolean telefonbrev
	Boolean annet

	String redigertAv
	Date redigertDato

	String kommentar

	static constraints = {
		kommentar(blank: false, nullable: false)
		redigertAv(blank: true, nullable: true)
		redigertDato(nullable: true)
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'sporing_seq'],  sqlType: 'integer'
	}

	public String hentResultatListe() {
		StringBuffer buffer = new StringBuffer()

		if( adresseEndring ) {
			buffer.append "Adresseendring, "
		}
		if( nyttTelefonnr ) {
			buffer.append "Nytt telefonnummer, "
		}
		if( navnEndring ) {
			buffer.append "Navneendring, "
		}
		if( nyBeboer ) {
			buffer.append "Ny beboer, "
		}
		if( husholdningEndring ) {
			buffer.append "Husholdningsendring, "
		}
		if( ingenting ) {
			buffer.append "Ingenting, "
		}
		
		return StringUtil.trimCommaSeparatedString( buffer.toString() )
	}

	public String hentKildeListe() {
		
		StringBuffer buffer = new StringBuffer()
		
		if( guleSider ) {
			buffer.append "Gule Sider, "
		}
		if( opplysningen ) {
			buffer.append "Opplysningen 1881, "
		}
		if( google ) {
			buffer.append "Google, "
		}
		if( bereg ) {
			buffer.append "Bereg, "
		}
		if( telefonbrev ) {
			buffer.append "Telefonbrev, "
		}
		if( annet ) {
			buffer.append "Annet, "
		}
		
		return StringUtil.trimCommaSeparatedString( buffer.toString() )
	}
}