package de.hsb.app.os.model;


import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;


@NamedQuery(name = "SelectProdukt", query = "Select p from Produkt p")
@Entity
@ManagedBean(name = "produkt")
public class Produkt {

	@Id
	@GeneratedValue
	private Integer Id;
	
	@Size(min = 3, max = 30)
	private String marke;
		
	private String titel;
	
	private String beschreibung;
	
	private String duftnote;
	
	private String menge;
	
	private Mengentyp mengentyp;
	
	private String preis;
	
	private Waehrungtyp waehrungtyp;
	
	public Produkt () {}

	public Produkt(String marke, String titel, String beschreibung, String duftnote, String preis, Waehrungtyp waehrungtyp, String menge, Mengentyp mengentyp)
	{
		super();
		this.marke = marke;
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.duftnote = duftnote;
		this.preis = preis;
		this.waehrungtyp = waehrungtyp;
		this.menge = menge;
		this.mengentyp = mengentyp;
	
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getDuftnote() {
		return duftnote;
	}

	public void setDuftnote(String duftnote) {
		this.duftnote = duftnote;
	}
	
	public String getMenge() {
		return menge;
	}

	public void setMenge(String menge) {
		this.menge = menge;
	}

	public Mengentyp getMengentyp() {
		return mengentyp;
	}

	public void setMengentyp(Mengentyp mengentyp) {
		this.mengentyp = mengentyp;
	}

	public String getPreis() {
		return preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}

	public Waehrungtyp getWaehrungtyp() {
		return waehrungtyp;
	}

	public void setWaehrungtyp(Waehrungtyp waehrungtyp) {
		this.waehrungtyp = waehrungtyp;
	}

		

}
