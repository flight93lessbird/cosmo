package de.hsb.app.os.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
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
public class MakeupHandler extends AbstractCrudRepository<Produkt>{
	
	@PostConstruct
	public void init() {
			save(new Produkt("L'oreal", "Blush Sculpt 201", "Natürliche Akt-Shades. Verwenden Sie es allein oder mischen Sie die Farbtöne", "...", "5,99",Waehrungtyp.EURO, "30", Mengentyp.GRAMM));
			save(new Produkt("Maybelline", "The Graffiti Nudes", "Enthält 12 wunderschöne Liedschatten, die perfekt aufeinander abgestimmt sind.", "...", "11,99",Waehrungtyp.EURO, "50", Mengentyp.GRAMM));
			save(new Produkt("Maybelline","Vivid Matte Liquid","Mittlere Deckkraft. Schimmernd/Glänzend. Zaubert einen frischen Teint.","blumig", "6,99",Waehrungtyp.EURO, "7,7", Mengentyp.MILLILITER));
			save(new Produkt("Essence", "Künstliche Wimpern", "Lassen sich einfach und schnell aufkleben. Lässt die Augen strahlen", "...", "2,99",Waehrungtyp.EURO, "3", Mengentyp.MILLILITER));
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
