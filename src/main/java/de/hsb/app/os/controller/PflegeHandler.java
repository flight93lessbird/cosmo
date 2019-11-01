package de.hsb.app.os.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;
import de.hsb.app.os.model.Produkt;

public class PflegeHandler {
	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Produkt> pflegeListe;
	
	private Produkt merkeProdukt = new Produkt();
	
	@PostConstruct
	public void init() {

		try {
			utx.begin();
			
			em.persist(new Produkt("Treaclemoon", "Körpermilch Pretty Rose Hearts", "Schütz vor austrocknen. Leicht verteilbar und schnelleinziehend.", "blumig", "5,99",Waehrungtyp.EURO, "350", Mengentyp.MILLILITER));
			em.persist(new Produkt("i+m", "Hytro Perform Reinigungsmilch", "Für normale bis trockene Haut. Reinigend ung Pflegend. Klärend bis porenverfeinernd.", "frisch", "9,99",Waehrungtyp.EURO, "350", Mengentyp.MILLILITER));
			em.persist(new Produkt("i+m","Volumen Haarspülung", "Für alle Haar","blumig", "9,99",Waehrungtyp.EURO, "200", Mengentyp.MILLILITER));
			em.persist(new Produkt("Garnier", "Mizellen Reinigungswasswe", "Reinigt empfindliche Haut. Legt sich wie ein Magnet auf der Haut und reinigt schonend und wirksam die Haut.", "blumig - orientalisch", "4,99",Waehrungtyp.EURO, "400", Mengentyp.MILLILITER));
			

			pflegeListe = new ListDataModel<Produkt>();
			pflegeListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());

			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
	}

	public DataModel<Produkt> getpflegeListe() {
		return pflegeListe;
	}

	public void setpflegeListe(DataModel<Produkt> pflegeListe) {
		this.pflegeListe = pflegeListe;
	}

	public Produkt getMerkeProdukt() {
		return merkeProdukt;
	}

	public void setMerkeProdukt(Produkt merkeProdukt) {
		this.merkeProdukt = merkeProdukt;
	}
	
	public String neu() {
		merkeProdukt = new Produkt();
		return "pflege";
	}
	
	public String speichern() {
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.persist(merkeProdukt);
			pflegeListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "pflege";
	}
	
	public String delete() {

		merkeProdukt = pflegeListe.getRowData();
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.remove(merkeProdukt);
			pflegeListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}

		return "pflege";
	}
	
	public String edit() {
		merkeProdukt = pflegeListe.getRowData();
		return "pflege";
	}
	
	public String getKundeFullName() {
        return merkeProdukt.getMarke() + " " + merkeProdukt.getTitel();
    }
	
}