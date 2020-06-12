package sivadm

class Rolle {

	String authority

	static mapping = {
		cache true
		id column: 'ID', generator: 'sequence',  params:[sequence:'rolle_seq'],  sqlType: 'integer'
	}

	static constraints = {
		authority blank: false, nullable: false, unique: true
	}
		
	public String toString() {
		String s = authority 
		if(s.contains('_')) {
			s = s.substring(s.indexOf('_')+1)
		}
		return s 
	}
}