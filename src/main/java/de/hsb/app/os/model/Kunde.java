package de.hsb.app.os.model;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import de.hsb.app.os.enumuration.Anrede;
import de.hsb.app.os.enumuration.Rolle;

@NamedQuery(name = "SelectKunden", query = "Select k from Kunde k")
@Entity
@ManagedBean(name = "kunde")
public class Kunde {

	@Id
	@GeneratedValue
	private Integer Id;
	
	
	private Anrede anrede;
	
	@Size(min = 3, max = 30)
	private String vorname;
	
	@Size(min = 3, max= 30)
	private String nachname;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private Adresse adresse;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Kreditkarte kreditkarte;
	
	
	@Past
	@Temporal(TemporalType.DATE)
	private Date geburtsdatum;
	
	private String username;
	private String passwort;
	private Rolle rolle;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Rolle getRolle() {
		return rolle;
	}

	public void setRolle(Rolle rolle) {
		this.rolle = rolle;
	}

	public Kunde () {}

	public Kunde(String username, String passwort, Rolle rolle, Anrede anrede, String vorname, String nachname, Date geburtsdatum, Kreditkarte kreditkarte, Adresse adresse)
	{
		super();
		this.username = username;
		this.passwort = passwort;
		this.rolle = rolle;
		this.anrede = anrede;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
		this.kreditkarte = kreditkarte;
		this.adresse = adresse;
	
	}

	public Anrede getAnrede() {
		return anrede;
	}

	public void setAnrede(Anrede anrede) {
		this.anrede = anrede;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
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
	
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public Kreditkarte getKreditkarte() {
		return kreditkarte;
	}

	public void setKreditkarte(Kreditkarte kreditkarte) {
		this.kreditkarte = kreditkarte;
	}

}
