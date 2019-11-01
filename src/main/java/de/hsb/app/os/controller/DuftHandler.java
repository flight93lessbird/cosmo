package de.hsb.app.os.controller;

import java.util.ArrayList;
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
		List<Produkt> produkte = new ArrayList<>();
		// Create Duefte
		produkte.add(new Produkt("Valentino", "Valentina", "Das Valentina Parfüm vereint moderne mit klassischem. In der Kopfnote wird Bergamotte", "blumig – orientalisch","49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
		produkte.add(new Produkt("Guess", "1981", "In der Kopfnote wird Veilchen und Abrette verbunden. In der Herznote entfalten sich", "blumig", "27,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
		produkte.add(new Produkt("Boss"," Ma Vie Pour Femme", "In der Kopfnote befindet sich Kaktusblüten. In der Herznote entfalten sich Pinke","blumig", "35,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
		produkte.add(new Produkt("Michael Kors", "Wonderlust", "In der Kopfnote wird Bergamotte, rosa Pfeffer und feiner Mandelmilch verbunden.", "blumig - orientalisch", "49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER));
		// Create Pflege
		produkte.add(new Produkt("Treaclemoon", "Körpermilch Pretty Rose Hearts", "Schütz vor austrocknen. Leicht verteilbar und schnelleinziehend.", "blumig", "5,99",Waehrungtyp.EURO, "350", Mengentyp.MILLILITER));
		produkte.add(new Produkt("i+m", "Hytro Perform Reinigungsmilch", "Für normale bis trockene Haut. Reinigend ung Pflegend. Klärend bis porenverfeinernd.", "frisch", "9,99",Waehrungtyp.EURO, "350", Mengentyp.MILLILITER));
		produkte.add(new Produkt("i+m","Volumen Haarspülung", "Für alle Haar","blumig", "9,99",Waehrungtyp.EURO, "200", Mengentyp.MILLILITER));
		produkte.add(new Produkt("Garnier", "Mizellen Reinigungswasswe", "Reinigt empfindliche Haut. Legt sich wie ein Magnet auf der Haut und reinigt schonend und wirksam die Haut.", "blumig - orientalisch", "4,99",Waehrungtyp.EURO, "400", Mengentyp.MILLILITER));
		// Create Make-Up
		produkte.add(new Produkt("L'oreal", "Blush Sculpt 201", "Natürliche Akt-Shades. Verwenden Sie es allein oder mischen Sie die Farbtöne", "...", "5,99",Waehrungtyp.EURO, "30", Mengentyp.GRAMM));
		produkte.add(new Produkt("Maybelline", "The Graffiti Nudes", "Enthält 12 wunderschöne Liedschatten, die perfekt aufeinander abgestimmt sind.", "...", "11,99",Waehrungtyp.EURO, "50", Mengentyp.GRAMM));
		produkte.add(new Produkt("Maybelline","Vivid Matte Liquid","Mittlere Deckkraft. Schimmernd/Glänzend. Zaubert einen frischen Teint.","blumig", "6,99",Waehrungtyp.EURO, "7,7", Mengentyp.MILLILITER));
		produkte.add(new Produkt("Essence", "Künstliche Wimpern", "Lassen sich einfach und schnell aufkleben. Lässt die Augen strahlen", "...", "2,99",Waehrungtyp.EURO, "3", Mengentyp.MILLILITER));

		int id= 1;
		for (Produkt produkt : produkte){
			// Setze die Id des Produktes ...
			produkt.setId(id++);
			// ... und speichere das Produkt
			save(produkt);
		}
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
