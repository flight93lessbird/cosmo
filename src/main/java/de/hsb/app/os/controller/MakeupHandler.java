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

import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;
import de.hsb.app.os.model.Produkt;

@ApplicationScoped
public class MakeupHandler {
	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Produkt> makeupListe;
	
	private Produkt merkeProdukt = new Produkt();
	
	@PostConstruct
	public void init() {

		try {
			utx.begin();
			
			em.persist(new Produkt("L'oreal", "Blush Sculpt 201", "Natürliche Akt-Shades. Verwenden Sie es allein oder mischen Sie die Farbtöne", "...", "5,99",Waehrungtyp.EURO, "30", Mengentyp.GRAMM));
			em.persist(new Produkt("Maybelline", "The Graffiti Nudes", "Enthält 12 wunderschöne Liedschatten, die perfekt aufeinander abgestimmt sind.", "...", "11,99",Waehrungtyp.EURO, "50", Mengentyp.GRAMM));
			em.persist(new Produkt("Maybelline","Vivid Matte Liquid","Mittlere Deckkraft. Schimmernd/Glänzend. Zaubert einen frischen Teint.","blumig", "6,99",Waehrungtyp.EURO, "7,7", Mengentyp.MILLILITER));
			em.persist(new Produkt("Essence", "Künstliche Wimpern", "Lassen sich einfach und schnell aufkleben. Lässt die Augen strahlen", "...", "2,99",Waehrungtyp.EURO, "3", Mengentyp.MILLILITER));
			

			makeupListe = new ListDataModel<Produkt>();
			makeupListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());

			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
	}

	public DataModel<Produkt> getMakeupListe() {
		return makeupListe;
	}

	public void setMakeupListe(DataModel<Produkt> makeupListe) {
		this.makeupListe = makeupListe;
	}

	public Produkt getMerkeProdukt() {
		return merkeProdukt;
	}

	public void setMerkeProdukt(Produkt merkeProdukt) {
		this.merkeProdukt = merkeProdukt;
	}
	
	public String neu() {
		merkeProdukt = new Produkt();
		return "makeup";
	}
	
	public String speichern() {
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.persist(merkeProdukt);
			makeupListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "makeup";
	}
	
	public String delete() {

		merkeProdukt = makeupListe.getRowData();
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.remove(merkeProdukt);
			makeupListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}

		return "makeup";
	}
	
	public String edit() {
		merkeProdukt = makeupListe.getRowData();
		return "makeup";
	}
	
	public String getKundeFullName() {
        return merkeProdukt.getMarke() + " " + merkeProdukt.getTitel();
    }
}
