package de.hsb.app.os.model;

import javax.persistence.*;

import de.hsb.app.os.enumuration.Rolle;


@NamedQuery(name="SelectUser", query="Select b from Benutzer b")
@Entity
public class Benutzer {
	
	private static final long serialVersionUID = 2615809714551834760L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String username;
	private String passwort;
	private Rolle rolle;

	@OneToOne
	private Warenkorb warenkorb = new Warenkorb();
	
	public Benutzer(){	}
	
	public Benutzer(String username, String passwort, Rolle rolle, Warenkorb warenkorb){
		this.username = username;
		this.passwort = passwort;
		this.rolle = rolle;
		this.warenkorb = warenkorb;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Warenkorb getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(Warenkorb warenkorb) {
		this.warenkorb = warenkorb;
	}
}