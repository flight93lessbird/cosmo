package de.hsb.app.os.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import de.hsb.app.os.enumuration.Anrede;
import de.hsb.app.os.enumuration.Rolle;

@NamedQuery(name="SelectUser", query="Select u from User u")
@Entity
public class User {
	private static final long serialVersionUID = 2615809714551834760L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer ID;
	private String username;
	private String passwort;
	private Rolle rolle;
	
	
	
	private Anrede anrede;
	
	@Size(min = 1, max = 30)
	private String vorname;
	
	@Size(min = 1, max= 30)
	private String nachname;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Adresse adresse;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Kreditkarte kreditkarte;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Warenkorb warenkorb;
	
	@Past
	@Temporal(TemporalType.DATE)
	private Date geburtsdatum;
	

	/*
	 * Empty Constructor with Var initialisation
	 */
	
	public User(){
		username = "";
		passwort = "";
		rolle = Rolle.KUNDE;
		warenkorb = new Warenkorb();
		
		vorname = "";
		nachname = "";
		
		kreditkarte = new Kreditkarte();
		adresse = new Adresse();
	}
	/*
	 * @Konstruktor für den Admin
	 */
	public User(String username, String passwort, Rolle rolle, Warenkorb warenkorb){
		this.username = username;
		this.passwort = passwort;
		this.rolle = rolle;
		this.warenkorb = warenkorb;
		Calendar cal = new GregorianCalendar(1987, Calendar.JULY, 05);
		this.geburtsdatum = cal.getTime();
		this.anrede = Anrede.FIRMA;
		this.vorname = "Max";
		this.nachname = "Mustermann";
		this.kreditkarte = null;
		this.adresse = null;
	}
	/*
	 *  @Konstruktor für den Benutzer
	 */
	public User(String username, String passwort, Rolle rolle, Warenkorb warenkorb, Anrede anrede, String vorname, String nachname, Date geburtsdatum, Kreditkarte kreditkarte, Adresse adresse)
	{
		super();
		this.username = username;
		this.passwort = passwort;
		this.rolle = rolle;
		this.warenkorb = warenkorb;
		this.anrede = anrede;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
		this.kreditkarte = kreditkarte;
		this.adresse = adresse;
	
	}
/*
 * 				GETTER / SETTER
 */
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

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

	public Anrede getAnrede() {
		return anrede;
	}

	public void setAnrede(Anrede anrede) {
		this.anrede = anrede;
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

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Kreditkarte getKreditkarte() {
		return kreditkarte;
	}

	public void setKreditkarte(Kreditkarte kreditkarte) {
		this.kreditkarte = kreditkarte;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public Warenkorb getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(Warenkorb warenkorb) {
		this.warenkorb = warenkorb;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
