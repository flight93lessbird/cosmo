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
import de.hsb.app.os.model.Benutzer;
import de.hsb.app.os.model.Kreditkarte;
import de.hsb.app.os.model.Kunde;
import de.hsb.app.os.model.Warenkorb;
import de.hsb.app.os.repository.AbstractCrudRepository;

@ManagedBean(name = "userHandler")
@ApplicationScoped
public class UserHandler extends AbstractCrudRepository<Benutzer> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String passwort;
	private Benutzer benutzer;

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	/** Erstellung eines Datamodels aus der Klasse Benutzer */
	private DataModel<Benutzer> user = new ListDataModel<Benutzer>();

	/** Erstellung des Objektes der Klasse Benutzer */
	private Benutzer merkeBenutzer = new Benutzer();

	private DataModel<Kunde> kunden;

	private Kunde merkeKunde = new Kunde();

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
			em.persist(warenkorb1);
			em.persist(warenkorb2);
			em.persist(warenkorb3);
			em.persist(new Benutzer("kunde", "kunde", Rolle.KUNDE, warenkorb1));
			em.persist(new Benutzer("admin", "admin", Rolle.ADMIN, warenkorb2));
			em.persist(new Benutzer("lena", "lena", Rolle.KUNDE, warenkorb3));
			user = new ListDataModel<Benutzer>();
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			/*
			 * init aus KundenHAndler
			 */

			em.persist(new Kunde("lena", "Geheim0!", Rolle.ADMIN, Anrede.FRAU, "Lena", "Eichhorst",
					new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new Kunde("pascal", "Pascal0!", Rolle.ADMIN, Anrede.HERR, "Pascal", "Zacheja",
					new GregorianCalendar(1960, Calendar.FEBRUARY, 2).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new Kunde("emma", "Emma0!", Rolle.ADMIN, Anrede.FRAU, "Emmanuelle", "Zielke",
					new GregorianCalendar(1912, Calendar.JUNE, 23).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new Kunde("donald", "geheim", Rolle.KUNDE, Anrede.HERR, "Donald", "Knuth",
					new GregorianCalendar(1938, Calendar.JANUARY, 10).getTime(), new Kreditkarte(), new Adresse()));
			em.persist(new Kunde("edsger", "geheim", Rolle.KUNDE, Anrede.HERR, "Edsger W.", "Dijkstra",
					new GregorianCalendar(1930, Calendar.MAY, 11).getTime(), new Kreditkarte(), new Adresse()));

			kunden = new ListDataModel<Kunde>();
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());

			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
	}

	public String neu() {
		merkeKunde = new Kunde();
		return "neuerKunde?faces-redirect=true";
	}

	public String formatDateDDMMYYYY(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(date);
	}

	public String neuAdresse() {
		merkeAdresse = new Adresse();
		return "neueAnschrift?faces-redirect=true";
	}

	public String editAdresse() {
		merkeKunde = getKunden().getRowData();
		merkeAdresse = kunden.getRowData().getAdresse();
		if (merkeAdresse == null) {
			merkeAdresse = new Adresse();
		}
		return "anschrift?faces-redirect=true";
	}

	public String adresseSpeichern() {
		merkeKunde.setKreditkarte(merkeKreditkarte);
		try {
			utx.begin();
			merkeKunde = em.merge(merkeKunde);
			merkeAdresse = em.merge(merkeAdresse);
			em.persist(merkeKunde);
			em.persist(merkeAdresse);
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
			utx.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		return "kunde?faces-redirect=true";
	}

	public String neuKreditkarte() {
		merkeKreditkarte = new Kreditkarte();
		return "kreditkarte?faces-redirect=true";
	}

	public String speichern() {
		try {
			utx.begin();
			merkeKunde = em.merge(merkeKunde);
			em.persist(merkeKunde);
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "kunde?faces-redirect=true";
	}

	public String speichernReg() {
		try {
			utx.begin();
			merkeKunde = em.merge(merkeKunde);
			em.persist(merkeKunde);
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "Startseite?faces-redirect=true";
	}

	public String speichernWk() {
		try {
			utx.begin();
			merkeKunde = em.merge(merkeKunde);
			em.persist(merkeKunde);
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "kundendatenUeberpruefung?faces-redirect=true";
	}

	public String kdspeichern() {
		try {
			utx.begin();
			merkeKunde = em.merge(merkeKunde);
			em.persist(merkeKunde);
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "Logout?faces-redirect=true";
	}

	public String edit() {
		merkeKunde = kunden.getRowData();
		return "neuerKunde?faces-redirect=true";
	}

	public String editKreditkarte() {
		merkeKunde = getKunden().getRowData();
		merkeKreditkarte = kunden.getRowData().getKreditkarte();
		if (merkeKreditkarte == null) {
			merkeKreditkarte = new Kreditkarte();
		}
		return "kreditkarte?faces-redirect=true";
	}

	public String kreditkarteSpeichern() {
		merkeKunde.setKreditkarte(merkeKreditkarte);
		try {
			utx.begin();
			merkeKunde = em.merge(merkeKunde);
			merkeKreditkarte = em.merge(merkeKreditkarte);
			em.persist(merkeKunde);
			em.persist(merkeKreditkarte);
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
			utx.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		return "kunde?faces-redirect=true";
	}

	public Kreditkartentyp[] getKreditkartentypValues() {
		return Kreditkartentyp.values();
	}

	public String deleteUser() {

		merkeKunde = kunden.getRowData();
		try {
			utx.begin();
			merkeKunde = em.merge(merkeKunde);
			em.remove(merkeKunde);
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "benutzer?faces-redirect=true";
	}

	/* Getter und Setter */

	public DataModel<Kunde> getKunden() {
		return kunden;
	}

	public Anrede[] getAnredeValues() {
		return Anrede.values();
	}

	public void setKunden(DataModel<Kunde> kunden) {
		this.kunden = kunden;
	}

	public Kunde getMerkeKunde() {
		return merkeKunde;
	}

	public void setMerkeKunde(Kunde merkeKunde) {
		this.merkeKunde = merkeKunde;
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
		if (benutzer == null)
			return " ";
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

	/**
	 * Unsere Methode überprüft, ob die eingegebende Daten vorhanden sind oder
	 * nicht. Haben wir eine Übereinstimmung so werden wir eingeloggt, ansonsten
	 * erhalten wir eine Fehlermeldung.
	 **/
	public String login() {
		System.out.println("Testomato");
		Query query = em
				.createQuery("Select u from Benutzer u " + "where u.username = :username and u.passwort = :passwort ");
		query.setParameter("username", username);
		query.setParameter("passwort", passwort);

		@SuppressWarnings("unchecked")
		List<Benutzer> user = query.getResultList();
		System.out.println("Größe von userList: " + user.size());
		if (user.size() > 0) {
			benutzer = user.get(0);
			if (benutzer.getRolle() == Rolle.ADMIN) {
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

// Warenkorb
	public String WkLogin() {
		Query query = em
				.createQuery("Select u from Benutzer u " + "where u.username = :username and u.passwort = :passwort ");
		query.setParameter("username", username);
		query.setParameter("passwort", passwort);

		@SuppressWarnings("unchecked")
		List<Benutzer> user = query.getResultList();
		System.out.println("Größe von userList: " + user.size());
		if (user.size() == 1) {
			benutzer = user.get(0);
			if (benutzer.getRolle() == Rolle.ADMIN) {
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

	public void checkLoggedIn(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (benutzer == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"/login.xhtml?faces-redirect=true");
		}
	}

	public String checkLoggedUser(Benutzer benutzer) {
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
		benutzer = null;
		return "/konto.xhtml?faces -redirect=true";
	}

	/**
	 * Da wir keine gleichen Benutzer haben wollen, brauchen wir eine Methode, die
	 * uns nicht erlaubt einen neuen Benutzer anzulegen wenn der schon vorhanden.
	 * Ist der Benutzer vorhanden leitet uns unsere WebApplikation zum Facelet
	 * "meinKonto.xhtml" weiter ansonsten wird ein neuer Benutzer angelegt. Parallel
	 * dazu wird eine Fehlermeldung ausgegeben.
	 */
	public String benutzerRegistrieren() {
		for (Benutzer b : user) {
			if (b.getUsername().equals(merkeBenutzer.getUsername())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername "
								+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.",
								null));
				;

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
	/*
	 * \\\\\\\\\\\\\\\\\\\\\\\\\\\\ OS Handler /////////////////////////////
	 */
	

	public String toRegistrieren() {
		merkeBenutzer = new Benutzer();
		return "registrieren?faces-redirect=true";
	}



	public String toKaufBestatigt() {
		merkeBenutzer = new Benutzer();
		return "kaufBestatigt?faces-redirect=true";
	}

//	public String benutzerRegistrieren() {
//		for (Benutzer b : user) {
//			if (b.getUsername().equals(merkeBenutzer.getUsername())) {
//				FacesContext.getCurrentInstance().addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername "
//								+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.",
//								null));
//				;
//
//				return null;
//			}
//		}
//		try {
//			merkeBenutzer.setRolle(Rolle.KUNDE);
//			utx.begin();
//			merkeBenutzer = em.merge(merkeBenutzer);
//			em.persist(merkeBenutzer);
//			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
//			utx.commit();
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//					"Herzlich Willkommen und vielen Dank für Ihre Registrierung.", null));
//		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
//				| HeuristicRollbackException | SystemException | NotSupportedException e) {
//			e.printStackTrace();
//		}
//		return "meinKonto?faces-redirect=true";
//	}

	public String registrierenWk() {
		for (Benutzer b : user) {
			if (b.getUsername().equals(merkeBenutzer.getUsername())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername "
								+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.",
								null));
				;

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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Herzlich Willkommen und vielen Dank für Ihre Registrierung.", null));
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "kundendatenUeberpruefung?faces-redirect=true";
	}

	public String benutzerAnlegen(Warenkorb warenkorb) {
		for (Benutzer b : user) {
			if (b.getUsername().equals(merkeBenutzer.getUsername())) {

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
			merkeBenutzer.setWarenkorb(warenkorb);
			merkeBenutzer = em.merge(merkeBenutzer);
			em.persist(merkeBenutzer);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "benutzer?faces-redirect=true";
	}

//	public String deleteUser() {
//		merkeBenutzer = user.getRowData();
//		try {
//			utx.begin();
//			merkeBenutzer = em.merge(merkeBenutzer);
//			em.remove(merkeBenutzer);
//			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
//			utx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	
	public DataModel<Benutzer> getUser() {
		return user;
	}

	public void setUser(DataModel<Benutzer> user) {
		this.user = user;
	}

	public Benutzer getMerkeBenutzer() {
		return merkeBenutzer;
	}

	public void setMerkeBenutzer(Benutzer merkeBenutzer) {
		this.merkeBenutzer = merkeBenutzer;
	}




}
