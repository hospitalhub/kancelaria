package pl.kalisz.szpital.hr.data.entity;

import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.eclipse.persistence.annotations.Index;

@Entity
@Table(name = "hr_specjalizacja")
public class Specjalizacja {

	public final static String ID = "id";
	public final static String NAZWA = "nazwa";
	private final static HashSet<Specjalizacja> s;
	static {
		s = new HashSet<Specjalizacja>();
		for (SpecjalizacjaEnum e : SpecjalizacjaEnum.values()) {
			s.add(e.getSpecjalizacja());
		}
	}

	enum SpecjalizacjaEnum {
		// inne
		ND("n/d"), BRAK("brak"),
		// specjalizacje podstawowe
		Anestezjologia("Anestezjologia i intensywna terapia"), Audiologia(
				"Audiologia i foniatria"), ChirurgiaDziecieca(
				"Chirurgia dziecięca"), ChirurgiaKlatkiPier(
				"Chirurgia klatki piersiowej"), ChirurgiaOgolna(
				"Chirurgia ogólna"), ChirurgiaPlastyczna("Chirurgia plastyczna"), ChirurgiaSzczekowoTwarzowa(
				"Chirurgia szczękowo-twarzowa"), Chorobywewn(
				"Choroby wewnętrzne"), Chorobyzakazne("Choroby zakaźne"), Dermatologia(
				"Dermatologia i wenerologia"), Diagnostyka(
				"Diagnostyka laboratoryjna"), Epidemiologia("Epidemiologia"), Genetyka(
				"Genetyka kliniczna"), Kardiochirurgia("Kardiochirurgia"), Kardiologia(
				"Kardiologia"), MedycynaNuklearna("Medycyna nuklearna"), MedycynaPracy(
				"Medycyna pracy"), MedycynaRatunkowa("Medycyna ratunkowa"), MedycynaRodzinna(
				"Medycyna rodzinna"), MedycynaSadowa("Medycyna sądowa"), MedycynaTransp(
				"Medycyna transportu"), Mikrobiologia("Mikrobiologia lekarska"), Neonatologia(
				"Neonatologia"), Neurochirurgia("Neurochirurgia"), Neurologia(
				"Neurologia"), Okulistyka("Okulistyka"), Onkologia(
				"Onkologia kliniczna"), Ortopedia(
				"Ortopedia i traumatologia narządu ruchu"), Otorynolaryngologia(
				"Otorynolaryngologia"), Patomorfologia("Patomorfologia"), Pediatria(
				"Pediatria"), PoloznictwoIGinekologia(
				"Położnictwo i ginekologia"), Psychiatria("Psychiatria"), PsychiatriaDzieciIMl(
				"Psychiatria dzieci i młodzieży"), Radiologia(
				"Radiologia i diagnostyka obrazowa"), Radioterapia(
				"Radioterapia onkologiczna"), Rehabilitacja(
				"Rehabilitacja medyczna"), Transfuzjologia(
				"Transfuzjologia kliniczna"), Urologia("Urologia"), Zdrowie(
				"Zdrowie Publiczne"),
		// specjalizacje szczegolowe
		Alergologia("Alergologia"), Angiologia("Angiologia"), Balneologia(
				"Balneologia i medycyna fizykalna"), ChirurgiaNaczyniowa(
				"Chirurgia naczyniowa"), ChirurgiaOnko("Chirurgia onkologiczna"), Choroby(
				"Choroby płuc"), Diabetologia("Diabetologia"), Endokrynologia(
				"Endokrynologia"), Farmakologia("Farmakologia kliniczna"), Gastroenterologia(
				"Gastroenterologia"), Geriatria("Geriatria"), Ginekologia(
				"Ginekologia onkologiczna"), Hematologia("Hematologia"), Hipertensjologia(
				"Hipertensjologia"), Immunologia("Immunologia kliniczna"), KardiologiaDziec(
				"Kardiologia dziecięca"), MedycynaPaliatywna(
				"Medycyna paliatywna"), MedycynaSport("Medycyna sportowa"), Nefrologia(
				"Nefrologia"), NeurologiaDziecieca("Neurologia dziecięca"), Neuropatologia(
				"Neuropatologia"), OnkologiaHematoDzieci(
				"Onkologia i hematologia dziecięca"), OtorynolaryngologiaDzieci(
				"Otorynolaryngologia dziecięca"), Reumatologia("Reumatologia"), Seksuologia(
				"Seksuologia"), Toksykologia("Toksykologia kliniczna"), Transplantologia(
				"Transplantologia kliniczna"), UrologiaDzieci(
				"Urologia dziecięca");
		private String nazwa;

		public Specjalizacja getSpecjalizacja() {
			Specjalizacja specjalizacja = new Specjalizacja();
			specjalizacja.nazwa = this.nazwa;
			return specjalizacja;
		}

		private SpecjalizacjaEnum(String nazwa) {
			this.nazwa = nazwa;
		}
	}

	@Id
	@GeneratedValue(generator = "SpecjalizacjaSeq")
	@TableGenerator(name = "SpecjalizacjaSeq", pkColumnValue = "SPECJALIZACJA_SEQ", initialValue = 0, allocationSize = 1)
	@Column(name = ID)
	private Integer id;

	@Index
	@Column(name = NAZWA, unique = true)
	private String nazwa;

	public String getNazwa() {
		return nazwa;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return nazwa;
	}

	public static HashSet<Specjalizacja> generate() {
		return s;
	}

	public static Specjalizacja valueOf(String value) {
		Specjalizacja specjalizacja = new Specjalizacja();
		specjalizacja.nazwa = value;
		if (s.contains(specjalizacja)) {
			return specjalizacja;
		} else {
			return null;
		}
	}
}
