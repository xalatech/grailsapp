package sivadm

class BlaiseEventInn {
	
	Long intervjuObjektId
	String statusCase
	String kommentar
	String intervjuStatus
	String internStatus
	String initialer
	Date dato
	Boolean behandlet
	Boolean endringKontaktInfo
	String dayBatchKode
	
    static constraints = {
		intervjuObjektId(nullable: false)
		statusCase(blank: true, nullable: true)
		kommentar(blank: true, nullable: true)
		intervjuStatus(blank: true, nullable: true)
		internStatus(blank: true, nullable: true)
		initialer(blank: true, nullable: true)
		dato(nullable: false)
		behandlet(nullable: true)
		endringKontaktInfo(nullable: true)
		dayBatchKode(blank: true, nullable: true)
	}
	
	static mapping  = {
		version false
		id column: 'ID', generator: 'sequence',  params:[sequence:'blaise_event_seq'],  sqlType: 'integer'
		behandlet index: 'behandlet_indeks'
	}
}