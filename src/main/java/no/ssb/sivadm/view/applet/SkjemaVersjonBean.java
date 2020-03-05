package no.ssb.sivadm.view.applet;

import java.io.Serializable;

/**
 * Inneholder informasjon om skjema og versjon
 */
@SuppressWarnings("serial")
public class SkjemaVersjonBean implements Serializable {

	private String skjemaKortnavn;
	private String versjon;


	public String getSkjemaKortnavn() {
		return skjemaKortnavn;
	}


	public void setSkjemaKortnavn(String skjemaKortnavn) {
		this.skjemaKortnavn = skjemaKortnavn;
	}


	public String getVersjon() {
		return versjon;
	}


	public void setVersjon(String versjon) {
		this.versjon = versjon;
	}
}
