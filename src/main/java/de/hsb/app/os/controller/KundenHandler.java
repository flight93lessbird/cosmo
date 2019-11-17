package de.hsb.app.os.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.hsb.app.os.enumuration.Anrede;
import de.hsb.app.os.enumuration.Rolle;
import de.hsb.app.os.model.Adresse;
import de.hsb.app.os.model.Kreditkarte;
import de.hsb.app.os.model.Kunde;


@ManagedBean (name= "kdhandler")
@ApplicationScoped
public class KundenHandler {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;
	
	private DataModel<Kunde> kunden;

	private Kunde merkeKunde = new Kunde();

	private Kreditkarte merkeKreditkarte = new Kreditkarte();

	private Adresse merkeAdresse = new Adresse();
	
	@PostConstruct
	public void init() {

		try {
			utx.begin();

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
		return "konto?faces-redirect=true";
	}
	public String abrrechen() {
		return "konto?faces-redirect=true";
	}

	public String formatDateDDMMYYYY(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(date);
	}


	public String neuAdresse() {
		merkeAdresse = new Adresse();
		return "konto?faces-redirect=true";
	}

	public String neuKreditkarte() {
		merkeKreditkarte = new Kreditkarte();
		return "konto?faces-redirect=true";
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
		return "meinkonto?faces-redirect=true";
	}

	public String edit() {
		merkeKunde = kunden.getRowData();
		return "meinKonto?faces-redirect=true";
	}
	
	public String editKreditkarte() {
		merkeKunde = getKunden().getRowData();
		merkeKreditkarte = kunden.getRowData().getKreditkarte();
		if (merkeKreditkarte == null) {
			merkeKreditkarte = new Kreditkarte();
		}
		return "meinKonto?faces-redirect=true";
	}


	/* Getter und Setter */

	public DataModel<Kunde> getKunden() {
		return kunden;
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

}
