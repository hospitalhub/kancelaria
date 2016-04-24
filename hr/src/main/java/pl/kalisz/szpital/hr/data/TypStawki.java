package pl.kalisz.szpital.hr.data;

public enum TypStawki {

	GDO("Stawka godziny dopołudniowe"), GDY("Stawka godziny dyżurowe"), LD(
			"Stawka lekarz dyżurny"), KO("Stawka kierownik oddziału"), WP(
			"Stawka wartość punktowa"), R("Stawka ryczałt"), KP(
			"Stawka konsultacje / procedury");
	private String nazwa;

	private TypStawki(String nazwa) {
		this.setNazwa(nazwa);
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
}
