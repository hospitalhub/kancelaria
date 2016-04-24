package pl.kalisz.szpital.hr.data.entity;

import java.util.Date;
import java.util.Map;

import pl.kalisz.szpital.hr.data.TypStawki;

public class Kontrakt {

	private Date dataZawarciaUmowy;
	private Date dataKoncaKontraktu;
	private Map<TypStawki, Float> stawki;

}
