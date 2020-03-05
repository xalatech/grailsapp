package sil.rapport.data

import java.util.Date;

class UkeRapportUkeNummerData {
	
	int ukeNummerDenneUka
	int ukeNummerEnUkeTilbake
	int ukeNummerToUkerTilbake
	int ukeNummerTreUkerTilbake
	int ukeNummerFireUkerTilbake
	
	public void setUkeNummerDenneUka(int ukeNummerDenneUka) {
		if( ukeNummerDenneUka < 1 ) {
			ukeNummerDenneUka += 52
		}
		this.ukeNummerDenneUka = ukeNummerDenneUka;
	}
	
	public void setUkeNummerEnUkeTilbake(int ukeNummerEnUkeTilbake) {
		if( ukeNummerEnUkeTilbake < 1 ) {
			ukeNummerEnUkeTilbake += 52
		}
		this.ukeNummerEnUkeTilbake = ukeNummerEnUkeTilbake;
	}
	
	public void setUkeNummerToUkerTilbake(int ukeNummerToUkerTilbake) {
		if( ukeNummerToUkerTilbake < 1 ) {
			ukeNummerToUkerTilbake += 52
		}
		this.ukeNummerToUkerTilbake = ukeNummerToUkerTilbake;
	}
	
	public void setUkeNummerTreUkerTilbake(int ukeNummerTreUkerTilbake) {
		if( ukeNummerTreUkerTilbake < 1 ) {
			ukeNummerTreUkerTilbake += 52
		}
		this.ukeNummerTreUkerTilbake = ukeNummerTreUkerTilbake;
	}
	
	public void setUkeNummerFireUkerTilbake(int ukeNummerFireUkerTilbake) {
		if( ukeNummerFireUkerTilbake < 1 ) {
			ukeNummerFireUkerTilbake += 52
		}
		this.ukeNummerFireUkerTilbake = ukeNummerFireUkerTilbake;
	}
	
}
