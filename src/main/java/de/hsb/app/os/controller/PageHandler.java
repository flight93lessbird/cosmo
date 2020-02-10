package de.hsb.app.os.controller;
/*
 * Diese Klasse hat die Methoden, 
 * die keine Logik besitzen und einen nur 
 * auf andere xhtml Seiten verweisen
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "pageHandler")
@SessionScoped
public class PageHandler {
	public PageHandler() {
	}

	public String toStartseite() {
		return "startseite?faces-redirect=true";
	}

	public String toLogin() {
		return "login?faces-redirect=true";
	}

	public String toWarenkorb() {
		return "warenkorb?faces-redirect=true";
	}

	public String toKunden() {
		return "kunden?faces-redirect=true";
	}

	public String toProdukte() {
		return "produkte?faces-redirect=true";
	}

	public String toMeinKonto() {
		return "meinKonto?faces-redirect=true";
	}

	public String toLogout() {
		return "logout?faces-redirect=true";
	}

	public String toKreditkartendatenAendern() {
		return "kreditkartendatenAendern?faces-redirect=true";
	}

	public String toPasswortAendern() {
		return "passwortAendern?faces-redirect=true";
	}

	public String toRegistrieren() {
		return "registrieren?faces-redirect=true";
	}

	public String toRegistrierenWarenkorb() {
		return "registrierenWarenkorb?faces-redirect=true";
	}

	public String toKundendatenUeberpruefung() {
		return "kundendatenUeberpruefung?faces-redirect=true";
	}

	public String toKundendaten() {
		return "kundendaten?faces-redirect=true";
	}

	public String toKaufBestatigt() {
		return "kaufBestatigt?faces-redirect=true";
	}
}
