package de.hsb.app.os.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.hsb.app.os.enumuration.Rolle;
import de.hsb.app.os.model.Benutzer;

@ManagedBean
@SessionScoped 
public class LoginHandler implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String passwort;
	private Benutzer benutzer;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction utx;
	
	@PostConstruct
	public void init(){
		try{
			utx.begin();
			em.persist(new Benutzer("kunde",   "kunde", Rolle.KUNDE));
			em.persist(new Benutzer("admin",  "admin", Rolle.ADMIN));
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			}	
	}
	
	
	public String login() {	
			Query query = em.createQuery("Select u from Benutzer u " +
				"where u.username = :username and u.passwort = :passwort ");
			query.setParameter("username", username);
			query.setParameter("passwort", passwort);
		
				@SuppressWarnings("unchecked")
				List<Benutzer> user = query.getResultList();
				if (user.size() == 1) {
					benutzer = user.get(0); 
					if(benutzer.getRolle() == Rolle.ADMIN){
						return "/benutzer.xhtml";}
					else {
						return "/Startseite.xhtml?faces-redirect=true";	
					}					
				} else {
					 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Du hast einen falschen Benutzernamen "
	    	        		+ "oder ein falsches Kennwort eingegeben."
	    	        		+ "Vergiss dabei nicht, auf die Groß-/Kleinschreibung des Kennwortes zu achten.)", passwort));
					 

				}
				return null;
		} 

		
	public void checkLoggedIn(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (benutzer == null) {
			context.getApplication().getNavigationHandler().
			handleNavigation(context, null,
					"/konto.xhtml?faces-redirect=true");
		}
	}
	
	
	public String logout () {
		benutzer = null;
	return "/konto.xhtml?faces -redirect=true";
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
	
	public Benutzer getBenutzer() {
		return benutzer;
	}
	
	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}	
}