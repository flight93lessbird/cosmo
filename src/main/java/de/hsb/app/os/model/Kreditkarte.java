package de.hsb.app.os.model;

import java.util.Date;
import javax.annotation.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;

import de.hsb.app.os.enumuration.Kreditkartentyp;


@NamedQuery(name ="SelectKreditkarte", query = "Select kr from Kreditkarte kr")
@Entity
@ManagedBean("Kreditkarte")
public class Kreditkarte {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Kreditkartentyp typ;
	
	private String nummer;
	
	private String inhaber;
		
	@Future
	@Temporal(TemporalType.DATE)
	private Date gueltigBis;

	public Kreditkarte() {}
	
	public Kreditkarte(Kreditkartentyp typ, String nummer, Date gueltigBis, String inhaber) {
		this.typ = typ;
		this.nummer = nummer;
		this.gueltigBis = gueltigBis;
		this.inhaber = inhaber;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Kreditkartentyp getTyp() {
		return typ;
	}

	public void setTyp(Kreditkartentyp typ) {
		this.typ = typ;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public String getInhaber() {
		return inhaber;
	}

	public void setInhaber(String inhaber) {
		this.inhaber = inhaber;
	}

	public Date getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(Date gueltigBis) {
		this.gueltigBis = gueltigBis;
	}
	
	
}
