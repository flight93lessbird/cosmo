package de.hsb.app.os.controller;

import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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

	public String neu() {
		merkeKunde = new Kunde();
		return "konto?faces-redirect=true";
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
		return "konto?faces-redirect=true";
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
