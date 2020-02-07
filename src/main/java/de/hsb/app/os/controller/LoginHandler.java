package de.hsb.app.os.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.hsb.app.os.enumuration.Rolle;
import de.hsb.app.os.model.Benutzer;
import de.hsb.app.os.model.Kunde;
import de.hsb.app.os.model.Warenkorb;
import de.hsb.app.os.repository.AbstractCrudRepository;

@ManagedBean
@SessionScoped 
public class LoginHandler extends AbstractCrudRepository<Benutzer> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String passwort;
	private Benutzer benutzer;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction utx;
	
	/**Erstellung eines Datamodels aus der Klasse Benutzer*/
	private DataModel<Benutzer> user = new ListDataModel<Benutzer>();
	
	/**Erstellung des Objektes der Klasse Benutzer*/
	private Benutzer merkeBenutzer = new Benutzer();
	
	@PostConstruct
	public void init(){
		if (this.findAll().isEmpty()) {
			try {
				utx.begin();
				Warenkorb warenkorb1 = new Warenkorb();
				Warenkorb warenkorb2 = new Warenkorb();
				Warenkorb warenkorb3 = new Warenkorb();
				em.persist(warenkorb1);
				em.persist(warenkorb2);
				em.persist(warenkorb3);
				em.persist(new Benutzer("kunde", "kunde", Rolle.KUNDE, warenkorb1));
				em.persist(new Benutzer("admin", "admin", Rolle.ADMIN, warenkorb2));
				em.persist(new Benutzer("lena", "lena", Rolle.KUNDE, warenkorb3));
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public String userLoginText() {
		if(benutzer == null)
			return "Bitte melde dich an oder log dich ein.";
		else
			return "Hallo " + benutzer.getUsername();
	}

	public String pwspeichern() {
		try {
			utx.begin();
			user = em.merge(user);
			em.persist(passwort);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "logout?faces-redirect=true";
	}


	/**Unsere Methode überprüft, ob die eingegebende Daten vorhanden sind oder nicht. 
	 * Haben wir eine Übereinstimmung so werden wir eingeloggt, ansonsten erhalten wir eine Fehlermeldung.**/	
	public String login() {	
			Query query = em.createQuery("Select u from Benutzer u " +
				"where u.username = :username and u.passwort = :passwort ");
			query.setParameter("username", username);
			query.setParameter("passwort", passwort);
		
				@SuppressWarnings("unchecked")
				List<Benutzer> user = query.getResultList();
				System.out.println("Größe von userList: " + user.size());
				if (user.size() == 1) {
					benutzer = user.get(0); 
					if(benutzer.getRolle() == Rolle.ADMIN){
						return "/admin.xhtml";}
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

// Warenkorb
	public String WkLogin() {
		Query query = em.createQuery("Select u from Benutzer u " +
				"where u.username = :username and u.passwort = :passwort ");
		query.setParameter("username", username);
		query.setParameter("passwort", passwort);

		@SuppressWarnings("unchecked")
		List<Benutzer> user = query.getResultList();
		System.out.println("Größe von userList: " + user.size());
		if (user.size() == 1) {
			benutzer = user.get(0);
			if(benutzer.getRolle() == Rolle.ADMIN){
				return "/admin?faces-redirect=true";}
			else {
				return "/kundendatenUeberpruefung?faces-redirect=true";
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

	public String checkLoggedUser(Benutzer benutzer) {
		if(benutzer != null){
			return "/os/Logout.xhtml";
		} else {
			return "/os/konto.xhtml";
		}
	}
	
	/**Lougout = der Benutzer wird von der Webanwendung abgemeldet und wird auf das konto.xhtml Seite weitergeleitet.**/
	public String logout () {
		benutzer = null;
	return "/konto.xhtml?faces -redirect=true";
	}
	
	 /**Da wir keine gleichen Benutzer haben wollen, brauchen wir eine Methode, die uns nicht erlaubt  einen neuen
     * Benutzer anzulegen wenn der schon vorhanden. Ist der Benutzer vorhanden leitet uns unsere WebApplikation zum Facelet
     * "meinKonto.xhtml" weiter ansonsten wird ein neuer Benutzer angelegt. Parallel dazu wird eine Fehlermeldung ausgegeben.*/
	public String benutzerRegistrieren(){
    	for(Benutzer b : user){
    		if(b.getUsername().equals(merkeBenutzer.getUsername())){
    	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Dieser Benutzername "
    	        		+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.", null));  ;  
    	         
    	        return null;
    		}
    	}
    	try {				
    		merkeBenutzer.setRolle(Rolle.KUNDE);
			utx.begin();			
			merkeBenutzer = em.merge(merkeBenutzer);
			em.persist(merkeBenutzer);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());		
			utx.commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Herzlich Willkommen und vielen Dank für Ihre Registrierung.", null));  
			
			utx.commit();
			} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
					| HeuristicRollbackException | SystemException | NotSupportedException e) {
				e.printStackTrace();
			}
		return "meinKonto?faces-redirect=true";
	}

	//Getter + Setter
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

	@Override
	protected Class<Benutzer> getRepositoryClass() {
		return Benutzer.class;
	}

	@Override
	protected String getQueryCommand() {
		return "select b from Benutzer b";
	}

	@Override
	protected String getSelect() {
		return "SelectBenutzer";
	}

	@Override
	protected List<Benutzer> uncheckedSolver(Object var) {
		final List<Benutzer> result = new ArrayList<>();
		if (var instanceof List) {
			for (int i = 0; i < ((List<?>) var).size(); i++) {
				final Object item = ((List<?>) var).get(i);
				if (item instanceof Benutzer) {
					result.add((Benutzer) item);
				}
			}
		}
		return result;
	}
}