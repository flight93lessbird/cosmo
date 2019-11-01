package de.hsb.app.os.model;


import javax.faces.bean.ManagedBean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import javax.validation.constraints.Size;

@NamedQuery(name = "SelectKunden", query = "Select k from Kunde k")
@Entity
@ManagedBean(name = "kunde")
public class Kunde {

	@Id
	@GeneratedValue
	private Integer Id;
	
	@Size(min = 3, max = 30)
	private String vorname;
	
	private String nachname;
	
	private String duftnote;
	
	private String inhalt;
	
	private String preis;
	
	public Kunde () {}

	public Kunde(String vorname, String nachname, String duftnote, String preis, String inhalt)
	{
		super();
		this.vorname = vorname;
		this.nachname = nachname;
		this.duftnote = duftnote;
		this.preis = preis;
		this.inhalt = inhalt;
	
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getDuftnote() {
		return duftnote;
	}

	public void setDuftnote(String duftnote) {
		this.duftnote = duftnote;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public String getPreis() {
		return preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}
	
	
	

}
