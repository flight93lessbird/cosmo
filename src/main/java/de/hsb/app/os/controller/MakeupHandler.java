package de.hsb.app.os.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;
import de.hsb.app.os.model.Produkt;
import de.hsb.app.os.repository.AbstractCrudRepository;

@SessionScoped
public class MakeupHandler extends AbstractCrudRepository<Produkt>{

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
