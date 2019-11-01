package de.hsb.app.os.controller;

import java.util.List;

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
import de.hsb.app.os.repository.AbstractCrudRepository;

@ApplicationScoped
public class DuftHandler extends AbstractCrudRepository<Produkt>{
	
	
	@PostConstruct
	public void init() {

		try {
			utx.begin();
			
			em.persist(new Produkt("Valentino", "Valentina", "Das Valentina Parfüm vereint moderne mit klassischem. In der Kopfnote wird Bergamotte", "blumig – orientalisch","49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
			em.persist(new Produkt("Guess", "1981", "In der Kopfnote wird Veilchen und Abrette verbunden. In der Herznote entfalten sich", "blumig", "27,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
			em.persist(new Produkt("Boss"," Ma Vie Pour Femme", "In der Kopfnote befindet sich Kaktusblüten. In der Herznote entfalten sich Pinke","blumig", "35,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
			em.persist(new Produkt("Michael Kors", "Wonderlust", "In der Kopfnote wird Bergamotte, rosa Pfeffer und feiner Mandelmilch verbunden.", "blumig - orientalisch", "49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
			

			duftListe = new ListDataModel<Produkt>();
			duftListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());

			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	

	public DataModel<Produkt> getduftListe() {
		return duftListe;
	}

	public void setduftListe(DataModel<Produkt> duftListe) {
		this.duftListe = duftListe;
	}

	public Produkt getMerkeProdukt() {
		return merkeProdukt;
	}

	public void setMerkeProdukt(Produkt merkeProdukt) {
		this.merkeProdukt = merkeProdukt;
	}
	
	public String neu() {
		merkeProdukt = new Produkt();
		return "duft";
	}
	
	
	public String speichern() {
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.persist(merkeProdukt);
			duftListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "duft";
	}
	
	public String delete() {

		merkeProdukt = duftListe.getRowData();
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.remove(merkeProdukt);
			duftListe.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}

		return "duft";
	}
	
	public String edit() {
		merkeProdukt = duftListe.getRowData();
		return "neuesProdukt";
	}
	
	public String getKundeFullName() {
        return merkeProdukt.getMarke() + " " + merkeProdukt.getTitel();
    }

	@Override
	protected Class<Produkt> getRepositoryClass() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected String getQueryCommand() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected String getSelect() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected List<Produkt> uncheckedSolver(Object var) {
		// TODO Auto-generated method stub
		return null;
	}
}
