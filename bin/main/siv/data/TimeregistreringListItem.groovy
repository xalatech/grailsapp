package siv.data

import java.text.SimpleDateFormat;
import java.util.Date;

class TimeregistreringListItem {

	Date dato
	Integer kjorteKilometer
	String totaleTimer
	Integer belop
	Boolean sendtInn

	public String getFormattedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd")
		return sdf.format(dato)
	}
}
