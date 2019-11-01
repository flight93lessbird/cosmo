package de.hsb.app.os.controller;


//import javax.annotation.PostConstruct;
//import java.util.GregorianCalendar;
//import de.hsb.app.kv.model.Anrede;

import java.io.Serializable;

import java.util.List;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

//import de.hsb.app.os.model.Kunde;
import de.hsb.app.os.enumuration.Rolle;

public class LoginHandler implements Serializable {
	
	private static final long serialVersionUID = -5073567478492831094L;
	
	private String username;
	private String passwort;
//	private Kunde kunde;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction utx;

//	public String login() {
//		Query query = em
//				.createQuery("select k from Kunde k " + "where k.username = :username and k.passwort = :passwort "
//						+ "and k.rolle = " + Rolle.ADMIN.ordinal());
//		query.setParameter("username", username);
//		query.setParameter("passwort", passwort);
//		System.out.println(username + " " + passwort);
//		
//		@SuppressWarnings("unchecked")
//		List<Kunde> kunden = query.getResultList();
//		if (kunden.size() == 1) {
//			kunde = kunden.get(0);
//			return "alleKunden?faces-redirect=true";
//		} else {
//			return null;
//		}
//	}
//
//	public void checkLoggedIn(ComponentSystemEvent cse) {
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (kunde == null) {
//			context.getApplication().getNavigationHandler().handleNavigation(context, null,
//					"login?faces-redirect=true");
//		}
//	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login?faces-redirect=true";
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

//	public Kunde getKunde() {
//		return kunde;
//	}
//
//	public void setKunde(Kunde kunde) {
//		this.kunde = kunde;
//	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public UserTransaction getUtx() {
		return utx;
	}

	public void setUtx(UserTransaction utx) {
		this.utx = utx;
	}
}