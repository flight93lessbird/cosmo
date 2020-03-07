package de.hsb.app.os.controller;
/*
 * Diese Klasse hat die Methoden, 
 * die keine Logik besitzen und einen nur 
 * auf andere xhtml Seiten verweisen
 */

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Locale;

@ManagedBean(name = "pageHandler")
@SessionScoped
public class PageHandler {

	private boolean isDeutsch;
	public PageHandler() {
	}
	
	public String checkLanguage() {
		if (FacesContext.getCurrentInstance().getApplication().getDefaultLocale().getLanguage().equals(new Locale("de").getLanguage()))
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("de"));
		else if (FacesContext.getCurrentInstance().getApplication().getDefaultLocale().getLanguage().equals(new Locale("en").getLanguage()))
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
		return "DE | EN";
	}
	
	public String changeLanguage() {
		language  = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		if(language.getLanguage().equals(new Locale("en").getLanguage()))
			FacesContext.getCurrentInstance().getApplication().setDefaultLocale(new Locale("de"));
		else if(language.getLanguage().equals(new Locale("de").getLanguage()))
			FacesContext.getCurrentInstance().getApplication().setDefaultLocale(new Locale("en"));
		return null;
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

	public String checkLanguage() {
		if (isDeutsch)
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("de"));
		else
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
		return "DE | EN";
	}

	public String changeLanguage() {
		isDeutsch = isDeutsch? false: true;
		return null;
	}
}
