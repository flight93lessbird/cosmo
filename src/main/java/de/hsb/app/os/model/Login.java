package de.hsb.app.os.model;

public class Login {

	private String nutzername;
	private String passwort;

	/**
	 * @return the username
	 */
	public String getNutzername() {
		return nutzername;
	}

	/*
	 * @param username the username to set
	 */
	public void setNutzername(String Nutzername) {
		this.nutzername = nutzername;
	}

	/*
	 * return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}

	/*
	 * @param passwortd the password to set
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String login() {
		if (nutzername.equals("emma") && passwort.equals("passwort")) {
			return "succes";
		} else
			return "failure";
	}
}
