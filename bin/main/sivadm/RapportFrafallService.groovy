package sivadm

import siv.type.Kilde
import siv.type.SkjemaStatus;

class RapportFrafallService {

	static transactional = true

	/**
	 * Finner antall nektere.
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return antall nektere
	 */
	public int finnAntallNektere(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()
		
		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}
			
			'in'("intervjuStatus", 11..19)
			
			ne("katSkjemaStatus", SkjemaStatus.Innlastet)
		}

		return ioList?.get(0)
	}

	/**
	 * Finner antall forhindret.
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return antall forhindret
	 */
	public int finnAntallForhindret(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()

		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}

			'in'("intervjuStatus", 21..32)

			ne("katSkjemaStatus", SkjemaStatus.Innlastet)
		}

		return ioList?.get(0)
	}

	/**
	 * Finner antall ikke truffet
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return antall ikke truffet
	 */
	public int finnAntallIkkeTruffet(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()
		
		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}
			
			'in'("intervjuStatus", 33..39)
			
			ne("katSkjemaStatus", SkjemaStatus.Innlastet)
		}

		return ioList?.get(0)
	}

	/**
	 * Finner bruttoantall
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return antall andre årsaker
	 */
	public int finnBruttoAntall(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()

		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}

			ne("katSkjemaStatus", SkjemaStatus.Innlastet)
		}

		return ioList?.get(0)
	}

	/**
	 * Finner antall andre med andre årsaker
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return antall andre årsaker
	 */
	public int finnAntallAndreAarsaker(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()
		
		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}
			
			eq("intervjuStatus", 41)
			
			ne("katSkjemaStatus", SkjemaStatus.Innlastet)
		}

		return ioList?.get(0)
	}

    /**
     * Finner antall med en gitt kilde
     *
     * @param skjemaId
     * @param periodeNummer
     * @return antall andre årsaker
     */
    public int finnAntallMedKilde(  Long skjemaId, Long periodeNummer, Kilde kilde ) {
        def criteria = IntervjuObjekt.createCriteria()

        def ioList = criteria {
            projections { countDistinct("id") }

            periode {
                skjema { eq("id", skjemaId) }

                if( periodeNummer != null) {
                    eq("periodeNummer", periodeNummer)
                }
            }

            eq("kilde", kilde)

			eq("intervjuStatus", 0)
        }

        return ioList?.get(0)
    }

	/**
	 * Finner antall intervju
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return antall intervju
	 */
	public int finnAntallIntervju(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()
		
		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}
			
			eq("intervjuStatus", 0)
			
			
			eq("katSkjemaStatus", SkjemaStatus.Ferdig)
		}

		return ioList?.get(0)
	}
	
	/**
	 * Finner antall avganger
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return antall avganger
	 */
	public int finnAntallAvganger(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()
		
		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}
			
			'in'("intervjuStatus", 91..99)
			
			eq("katSkjemaStatus", SkjemaStatus.Ferdig)
		}

		return ioList?.get(0)
	}
	
	/**
	 * Finner antall overganger
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallOverføringer(  Long skjemaId, Long periodeNummer ) {
		def criteria = IntervjuObjekt.createCriteria()
		
		def ioList = criteria {
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}
			
			'in'("intervjuStatus", 81..89)
			
			eq("katSkjemaStatus", SkjemaStatus.Ubehandlet)
			
		}

		return ioList?.get(0)
	}
	
	/**
	* Finner antall frafall
	*
	* @param skjemaId
	* @param periodeNummer
	* @return
	*/
   public int finnAntallFrafall(  Long skjemaId, Long periodeNummer ) {
	   def criteria = IntervjuObjekt.createCriteria()
	   
	   def ioList = criteria {
		   projections { countDistinct("id") }

		   periode {
			   skjema { eq("id", skjemaId) }

			   if( periodeNummer != null) {
				   eq("periodeNummer", periodeNummer)
			   }
		   }
		   
		   'in'("intervjuStatus", 11..41)
		   
		   ne("katSkjemaStatus", SkjemaStatus.Innlastet) 
	   }

	   return ioList?.get(0)
   }
}
