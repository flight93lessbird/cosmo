package de.hsb.app.os.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
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

import de.hsb.app.os.model.Kunde;


@ApplicationScoped
public class KundenHandler {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Kunde> kunden;
	
	private Kunde merkeKunde = new Kunde();

	
	
	@PostConstruct
	public void init() {

		try {
			utx.begin();
			
			em.persist(new Kunde("Hugo Boss", "Das Valentina Parfüm vereint moderne mit klassischem. In der Kopfnote wird Bergamotte","blumig – orientalisch", "49,99 €", "30ml"));
			em.persist(new Kunde("Guess 1981", "In der Kopfnote wird Veilchen und Abrette verbunden. In der Herznote entfalten sich", "blumig", "27,99 €", "30 ml"));
			em.persist(new Kunde("Boss Ma Vie Pour Femme", "In der Kopfnote befindet sich Kaktusblüten. In der Herznote entfalten sich Pinke","blumig", "35,99 €", "30ml"));
			em.persist(new Kunde("Michael Kors Wonderlust", "In der Kopfnote wird Bergamotte, rosa Pfeffer und feiner Mandelmilch verbunden.", "blumig - orientalisch", "49,99 €", "30ml"));
			em.persist(new Kunde("James Bond 007", "In der Kopfnote wird schwarzer Pfeffer mit Rosenmilch verbunden.", "blumig, holzig", "12,95 €", "30 ml"));

			kunden = new ListDataModel<Kunde>();
			kunden.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());

			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
	}

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
}
