package de.hsb.app.os.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

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
	
	public Benutzer(){}
	
	public Benutzer(String username, String passwort, Rolle rolle){
		this.username = username;
		this.passwort = passwort;
		this.rolle = rolle;
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
}