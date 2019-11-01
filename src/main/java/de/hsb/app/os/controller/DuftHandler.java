package de.hsb.app.os.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;
import de.hsb.app.os.model.Produkt;
import de.hsb.app.os.repository.AbstractCrudRepository;

@ApplicationScoped
public class DuftHandler extends AbstractCrudRepository<Produkt>{
	
	
	@PostConstruct
	public void init() {
			save(new Produkt("Valentino", "Valentina", "Das Valentina Parfüm vereint moderne mit klassischem. In der Kopfnote wird Bergamotte", "blumig – orientalisch","49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
			save(new Produkt("Guess", "1981", "In der Kopfnote wird Veilchen und Abrette verbunden. In der Herznote entfalten sich", "blumig", "27,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
			save(new Produkt("Boss"," Ma Vie Pour Femme", "In der Kopfnote befindet sich Kaktusblüten. In der Herznote entfalten sich Pinke","blumig", "35,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
			save(new Produkt("Michael Kors", "Wonderlust", "In der Kopfnote wird Bergamotte, rosa Pfeffer und feiner Mandelmilch verbunden.", "blumig - orientalisch", "49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
	}
	
	public String neu() {
		selectedEntity = new Produkt();
		return "duft";
	}
	
	
	public String speichern() {
		try {
			utx.begin();
			selectedEntity = em.merge(selectedEntity);
			em.persist(selectedEntity);
			entityList.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "duft";
	}

	public String edit() {
		selectedEntity = entityList.getRowData();
		return "neuesProdukt";
	}

	public String getProduktFullName() {
        return selectedEntity.getMarke() + " " + selectedEntity.getTitel();
    }

	@Override
	protected Class<Produkt> getRepositoryClass() {
		// TODO Auto-generated method stub
		return Produkt.class;
	}

	@Override
	protected String getQueryCommand() {
		return "select p from Produkt p";
	}

	@Override
	protected String getSelect() {
		// TODO Auto-generated method stub
		return "SelectProdukt";
	}

	@Override
	protected List<Produkt> uncheckedSolver(Object var) {
		// TODO Auto-generated method stub
		return null;
	}
}
