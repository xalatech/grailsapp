package sivadm

class CatiGruppe {

	String navn
	
	static hasMany = [skjemaer:Skjema, intervjuere:Intervjuer]
    
	static constraints = {
		navn(blank: false, nullable: false)
		skjemaer(nullable:true)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'cati_gruppe_seq'],  sqlType: 'integer'
	}
	
	public static List<CatiGruppe> getByIntervjuerId (Long intervjuerId) {
		def results = CatiGruppe.createCriteria().list {
			intervjuere {
				'in'( "id", [intervjuerId] )
			}
		}
		
		return results
	}
}