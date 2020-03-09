package sivadm

class SporingService {

    boolean transactional = true

    List hentSporingsListeForIntervjuObjekt( IntervjuObjekt intervjuObjekt) {
		return Sporing.findAllByIntervjuObjekt(intervjuObjekt)
    }
}
