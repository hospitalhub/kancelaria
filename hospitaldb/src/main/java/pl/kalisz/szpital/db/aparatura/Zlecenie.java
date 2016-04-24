package pl.kalisz.szpital.db.aparatura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.kalisz.szpital.db.HospitalEntity;
import pl.kalisz.szpital.db.kancelaria.Adres;

@SuppressWarnings("serial")
@Entity
@Table(name = "apmed_errand")
public class Zlecenie extends HospitalEntity implements Serializable {

	public final static String NRZLECENIA = "nrZlecenia";
	public final static String DATAZLECENIA = "dataZlecenia";
	public final static String TREŚĆZLECENIA = "treśćZlecenia";
	public final static String USŁUGODAWCA = "usługodawca";
	public final static String PROWADZĄCY = "prowadzący";
	public final static String UWAGI = "uwagi";

	public final static Object[] VISIBLE = { NRZLECENIA, DATAZLECENIA, TREŚĆZLECENIA, USŁUGODAWCA, PROWADZĄCY,
			UWAGI, };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long nrZlecenia;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataZlecenia;
	private List<ZlecenieDetails> zlecenieDetails;
	private String treśćZlecenia;
	private Adres usługodawca;
	private String prowadzący;
	private String uwagi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNrZlecenia(Long nrZlecenia) {
		this.nrZlecenia = nrZlecenia;
	}

	public Long getNrZlecenia() {
		return nrZlecenia;
	}

	public Date getDataZlecenia() {
		return dataZlecenia;
	}

	public void setDataZlecenia(Date dataZlecenia) {
		this.dataZlecenia = dataZlecenia;
	}

	public List<ZlecenieDetails> getZlecenieDetails() {
		return zlecenieDetails;
	}
	
	public void setZlecenieDetails(List<ZlecenieDetails> zlecenieDetails) {
		this.zlecenieDetails = zlecenieDetails;
	}
	
	public String getTreśćZlecenia() {
		return treśćZlecenia;
	}

	public void setTreśćZlecenia(String treśćZlecenia) {
		this.treśćZlecenia = treśćZlecenia;
	}

	public Adres getUsługodawca() {
		return usługodawca;
	}

	public void setUsługodawca(Adres usługodawca) {
		this.usługodawca = usługodawca;
	}

	public String getProwadzący() {
		return prowadzący;
	}

	public void setProwadzący(String prowadzący) {
		this.prowadzący = prowadzący;
	}


	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	@Override
	public String toString() {
		return nrZlecenia.toString();
	}

}
