package de.hsb.app.os.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
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

import org.primefaces.push.annotation.Singleton;

import de.hsb.app.os.enumuration.Anrede;
import de.hsb.app.os.enumuration.Kreditkartentyp;
import de.hsb.app.os.enumuration.Rolle;
import de.hsb.app.os.model.Adresse;
import de.hsb.app.os.model.Kreditkarte;
import de.hsb.app.os.model.User;
import de.hsb.app.os.model.Warenkorb;
import de.hsb.app.os.repository.AbstractCrudRepository;

@ManagedBean(name = "userHandler")
@ApplicationScoped
public class UserHandler extends AbstractCrudRepository<User> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String passwort;

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	/** Erstellung eines Datamodels aus der Klasse Benutzer */
	private DataModel<User> user = new ListDataModel<User>();

	/** Erstellung des Objektes der Klasse Benutzer */
	private User merkeUser = new User();

	private Kreditkarte merkeKreditkarte = new Kreditkarte();

	private Adresse merkeAdresse = new Adresse();

	/*
	 * \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Kunden Handler
	 * /////////////////////////////
	 */

	@PostConstruct
	@javax.inject.Singleton
	public void init() {

		try {
			utx.begin();
			em.clear();
			/*
			 * init aus LoginHandler
			 */
			Warenkorb warenkorb1 = new Warenkorb();
			Warenkorb warenkorb2 = new Warenkorb();
			Warenkorb warenkorb3 = new Warenkorb();
			Warenkorb warenkorb4 = new Warenkorb();
			Warenkorb warenkorb5 = new Warenkorb();
			Warenkorb warenkorb6 = new Warenkorb();
			Warenkorb warenkorb7 = new Warenkorb();
			Warenkorb warenkorb8 = new Warenkorb();
			em.persist(warenkorb1);
			em.persist(warenkorb2);
			em.persist(warenkorb3);
			em.persist(warenkorb4);
			em.persist(warenkorb5);
			em.persist(warenkorb6);
			em.persist(warenkorb7);
			em.persist(warenkorb8);
			em.persist(new User("kunde", "kunde", Rolle.KUNDE, warenkorb1));
			em.persist(new User("admin", "admin", Rolle.ADMIN, warenkorb2));
			em.persist(new User("lena", "lena", Rolle.KUNDE, warenkorb3));

			/*
			 * init aus KundenHAndler
			 */
			em.persist(new User("lena", "Geheim0!", Rolle.ADMIN, warenkorb4, Anrede.FRAU, "Lena", "Eichhorst",
					new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new User("pascal", "Pascal0!", Rolle.ADMIN, warenkorb5, Anrede.HERR, "Pascal", "Zacheja",
					new GregorianCalendar(1960, Calendar.FEBRUARY, 2).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new User("emma", "Emma0!", Rolle.ADMIN, warenkorb6, Anrede.FRAU, "Emmanuelle", "Zielke",
					new GregorianCalendar(1912, Calendar.JUNE, 23).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new User("donald", "geheim", Rolle.KUNDE, warenkorb7, Anrede.HERR, "Donald", "Knuth",
					new GregorianCalendar(1938, Calendar.JANUARY, 10).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new User("edsger", "geheim", Rolle.KUNDE, warenkorb8, Anrede.HERR, "Edsger W.", "Dijkstra",
					new GregorianCalendar(1930, Calendar.MAY, 11).getTime(), new Kreditkarte(), new Adresse()));

			user = new ListDataModel<User>();
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			System.out.println(em.createNamedQuery("SelectUser").getResultList().size() + " Länge der NamedQuery User");
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
	}

	public String newUser() {
		merkeUser = new User();
		return "startseite?faces-redirect=true";
	}

	public String formatDateDDMMYYYY(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(date);
	}

	// brauchen wir glaub ich nicht
	public String neuAdresse() {
		merkeAdresse = new Adresse();
		return "neueAnschrift?faces-redirect=true";
	}

	public String editAdresse() {
		merkeUser = getKunden().getRowData();
		merkeAdresse = user.getRowData().getAdresse();
		if (merkeAdresse == null) {
			merkeAdresse = new Adresse();
		}
		return "anschrift?faces-redirect=true";
	}

	public String adresseSpeichern() {
		merkeUser.setKreditkarte(merkeKreditkarte);
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			merkeAdresse = em.merge(merkeAdresse);
			em.persist(merkeUser);
			em.persist(merkeAdresse);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		return "kunden?faces-redirect=true";
	}

	// ich glaube brauchen wir nicht
	public String neuKreditkarte() {
		merkeKreditkarte = new Kreditkarte();
		return "kreditkarte?faces-redirect=true";
	}

	public String neuerKundeSpeichern() {
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			Warenkorb warenkorb = merkeUser.getWarenkorb();
			em.persist(warenkorb);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "kunden?faces-redirect=true";
	}

	public String speichernReg() {
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "startseite?faces-redirect=true";
	}

	public String kreditkarteSpeichern() {
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "logout?faces-redirect=true";
	}

	public String kreditkarteSpeichernWk() {
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "kaufBestatigt?faces-redirect=true";
	}

	public String editUser() {
		merkeUser = user.getRowData();
		return "neuerKunde?faces-redirect=true";
	}

	public String editKreditkarte() {
		merkeUser = getKunden().getRowData();
		merkeKreditkarte = user.getRowData().getKreditkarte();
		if (merkeKreditkarte == null) {
			merkeKreditkarte = new Kreditkarte();
		}
		return "kreditkarte?faces-redirect=true";
	}

	public String kreditkarteSpeichernAd() {
		merkeUser.setKreditkarte(merkeKreditkarte);
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			merkeKreditkarte = em.merge(merkeKreditkarte);
			em.persist(merkeUser);
			em.persist(merkeKreditkarte);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		return "kunde?faces-redirect=true";
	}

	public String kundendatenSpeichern() {
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "logout?faces-redirect=true";
	}

	public Kreditkartentyp[] getKreditkartentypValues() {
		return Kreditkartentyp.values();
	}

	public String deleteUser() {
		merkeUser = user.getRowData();
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.remove(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "benutzer?faces-redirect=true";
	}

	/* Getter und Setter */

	public DataModel<User> getKunden() {
		return user;
	}

	public Anrede[] getAnredeValues() {
		return Anrede.values();
	}

	public void setKunden(DataModel<User> kunden) {
		this.user = kunden;
	}

	public User getmerkeUser() {
		return merkeUser;
	}

	public void setmerkeUser(User merkeUser) {
		this.merkeUser = merkeUser;
	}

	public Kreditkarte getMerkeKreditkarte() {
		return merkeKreditkarte;
	}

	public void setMerkeKreditkarte(Kreditkarte merkeKreditkarte) {
		this.merkeKreditkarte = merkeKreditkarte;
	}

	public Adresse getMerkeAdresse() {
		return merkeAdresse;
	}

	public void setMerkeAdresse(Adresse merkeAdresse) {
		this.merkeAdresse = merkeAdresse;
	}

	/*
	 * \\\\\\\\\\\\\\\\\\\\\\\\\\\ LOGIN HANDLER ////////////////////////////
	 */

	public String userLoginText() {
		if (merkeUser == null)
			return " ";
		else
			return "Hallo, " + merkeUser.getVorname() + " ";
	}

	public String pwspeichern() {
		try {
			utx.begin();
			user = em.merge(user);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "logout?faces-redirect=true";
	}

	/**
	 * Unsere Methode überprüft, ob die eingegebende Daten vorhanden sind oder
	 * nicht. Haben wir eine Übereinstimmung so werden wir eingeloggt, ansonsten
	 * erhalten wir eine Fehlermeldung.
	 **/
	public String login() {
		System.out.println("Testomato");
		Query query = em
				.createQuery("Select u from User u" + " where u.username = :username and u.passwort = :passwort ");
		query.setParameter("username", username);
		query.setParameter("passwort", passwort);
		@SuppressWarnings("unchecked")
		List<User> user = query.getResultList();
		System.out.println("Größe von userList: " + user.size());
		if (user.size() > 0) {
			merkeUser = user.get(0);
			if (merkeUser.getRolle() == Rolle.ADMIN) {
				return "/admin.xhtml";
			} else {
				return "/startseite.xhtml?faces-redirect=true";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Du hast einen falschen Benutzernamen " + "oder ein falsches Kennwort eingegeben."
									+ "Vergiss dabei nicht, auf die Groß-/Kleinschreibung des Kennwortes zu achten.)",
							passwort));
		}
		return null;
	}

// login für den Warenkorb
	public String WkLogin() {
		Query query = em
				.createQuery("Select u from Benutzer u " + "where u.username = :username and u.passwort = :passwort ");
		query.setParameter("username", username);
		query.setParameter("passwort", passwort);

		@SuppressWarnings("unchecked")
		List<User> user = query.getResultList();
		System.out.println("Größe von userList: " + user.size());
		if (user.size() == 1) {
			merkeUser = user.get(0);
			if (merkeUser.getRolle() == Rolle.ADMIN) {
				return "/admin?faces-redirect=true";
			} else {
				return "/kundendatenUeberpruefung?faces-redirect=true";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Du hast einen falschen Benutzernamen " + "oder ein falsches Kennwort eingegeben."
									+ "Vergiss dabei nicht, auf die Groß-/Kleinschreibung des Kennwortes zu achten.)",
							passwort));
		}
		return null;
	}

	// brauchen wir glaub ich nicht
	public void checkLoggedIn(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (merkeUser == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"/login.xhtml?faces-redirect=true");
		}
	}

	public String checkLoggedUser(User benutzer) {
		if (benutzer != null) {
			return "/os/logout.xhtml";
		} else {
			return "/os/login.xhtml";
		}
	}

	/**
	 * Lougout = der Benutzer wird von der Webanwendung abgemeldet und wird auf das
	 * konto.xhtml Seite weitergeleitet.
	 **/
	public String logout() {
		merkeUser = null;
		return "/startseite.xhtml?faces -redirect=true";
	}

	/**
	 * Da wir keine gleichen Benutzer haben wollen, brauchen wir eine Methode, die
	 * uns nicht erlaubt einen neuen Benutzer anzulegen wenn der schon vorhanden.
	 * Ist der Benutzer vorhanden leitet uns unsere WebApplikation zum Facelet
	 * "meinKonto.xhtml" weiter ansonsten wird ein neuer Benutzer angelegt. Parallel
	 * dazu wird eine Fehlermeldung ausgegeben.
	 */
	public String benutzerRegistrieren() {
		for (User u : user) {
			if(merkeUser == null){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername "
								+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.",
								null));
				;

				return null;

			}else if (u.getUsername().equals(merkeUser.getUsername())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername "
								+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.",
								null));
				;

				return null;
			}
			
		}
		try {
			merkeUser.setRolle(Rolle.KUNDE);
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Herzlich Willkommen und vielen Dank für Ihre Registrierung.", null));

			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "meinKonto?faces-redirect=true";
	}

	// Getter + Setter
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

	@Override
	protected Class<User> getRepositoryClass() {
		return User.class;
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
	protected List<User> uncheckedSolver(Object var) {
		final List<User> result = new ArrayList<>();
		if (var instanceof List) {
			for (int i = 0; i < ((List<?>) var).size(); i++) {
				final Object item = ((List<?>) var).get(i);
				if (item instanceof User) {
					result.add((User) item);
				}
			}
		}
		return result;
	}
	/*
	 * \\\\\\\\\\\\\\\\\\\\\\\\\\\\ OS Handler /////////////////////////////
	 */

	/*
	 * 
	 * public String toRegistrieren() { merkeUser = new Benutzer(); return
	 * "registrieren?faces-redirect=true"; }
	 * 
	 * 
	 * 
	 * public String toKaufBestatigt() { merkeUser = new Benutzer(); return
	 * "kaufBestatigt?faces-redirect=true"; }
	 * 
	 * // public String benutzerRegistrieren() { // for (Benutzer b : user) { // if
	 * (b.getUsername().equals(merkeUser.getUsername())) { //
	 * FacesContext.getCurrentInstance().addMessage(null, // new
	 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername " // +
	 * "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien."
	 * , // null)); // ; // // return null; // } // } // try { //
	 * merkeUser.setRolle(Rolle.KUNDE); // utx.begin(); // merkeUser =
	 * em.merge(merkeUser); // em.persist(merkeUser); //
	 * user.setWrappedData(em.createNamedQuery("SelectUser").getResultList()); //
	 * utx.commit(); // FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_INFO, //
	 * "Herzlich Willkommen und vielen Dank für Ihre Registrierung.", null)); // }
	 * catch (SecurityException | IllegalStateException | RollbackException |
	 * HeuristicMixedException // | HeuristicRollbackException | SystemException |
	 * NotSupportedException e) { // e.printStackTrace(); // } // return
	 * "meinKonto?faces-redirect=true"; // }
	 * 
	 */
	public String speichernWk() {
		try {
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "zahlungsart?faces-redirect=true";
	}

	public String registrierenWk() {
		for (User u : user) {
			if (u.getUsername().equals(merkeUser.getUsername())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername "
								+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.",
								null));
				;
				return null;
			}
		}
		try {
			merkeUser.setRolle(Rolle.KUNDE);
			utx.begin();
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Herzlich Willkommen und vielen Dank für Ihre Registrierung.", null));
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "kundendatenUeberpruefung?faces-redirect=true";
	}

	// brauchen wir nicht
	public String benutzerAnlegen(Warenkorb warenkorb) {
		for (User u : user) {
			if (u.getUsername().equals(merkeUser.getUsername())) {

				FacesContext.getCurrentInstance()
						.addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Dieser Benutzername ist bereits vergeben "
												+ "oder erfüllt nicht die vom Administrator festgelegten Richtlinien",
										null));
				return null;
			}
		}
		try {
			utx.begin();
			warenkorb = em.merge(warenkorb);
			merkeUser.setWarenkorb(warenkorb);
			merkeUser = em.merge(merkeUser);
			em.persist(merkeUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "benutzer?faces-redirect=true";
	}

//	public String deleteUser() {
//		merkeUser = user.getRowData();
//		try {
//			utx.begin();
//			merkeUser = em.merge(merkeUser);
//			em.remove(merkeUser);
//			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
//			utx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public DataModel<User> getUser() {
		return user;
	}

	public void setUser(DataModel<User> user) {
		this.user = user;
	}

	public User getMerkeUser() {
		return merkeUser;
	}

	public void setMerkeUser(User merkeUser) {
		this.merkeUser = merkeUser;
	}

}
