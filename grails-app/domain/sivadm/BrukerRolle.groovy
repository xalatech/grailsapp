package sivadm

import org.apache.commons.lang.builder.HashCodeBuilder

class BrukerRolle implements Serializable {

	Bruker bruker
	Rolle rolle

	boolean equals(other) {
		if (!(other instanceof BrukerRolle)) {
			return false
		}

		other.bruker?.id == bruker?.id &&
			other.rolle?.id == rolle?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (bruker) builder.append(bruker.id)
		if (rolle) builder.append(rolle.id)
		builder.toHashCode()
	}

	static BrukerRolle get(long brukerId, long rolleId) {
		find 'from BrukerRolle where bruker.id=:brukerId and rolle.id=:rolleId',
			[brukerId: brukerId, rolleId: rolleId]
	}

	static BrukerRolle create(Bruker bruker, Rolle rolle, boolean flush = false) {
		new BrukerRolle(bruker: bruker, rolle: rolle).save(flush: flush, insert: true)
	}

	static boolean remove(Bruker bruker, Rolle rolle, boolean flush = false) {
		BrukerRolle instance = BrukerRolle.findByBrukerAndRolle(bruker, rolle)
		instance ? instance.delete(flush: flush) : false
	}

	static void removeAll(Bruker bruker) {
		executeUpdate 'DELETE FROM BrukerRolle WHERE bruker=:bruker', [bruker: bruker]
	}

	static void removeAll(Rolle rolle) {
		executeUpdate 'DELETE FROM BrukerRolle WHERE rolle=:rolle', [rolle: rolle]
	}

	static mapping = {
		id composite: ['rolle', 'bruker']
		version false
	}
}
