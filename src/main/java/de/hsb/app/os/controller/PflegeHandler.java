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
public class PflegeHandler extends AbstractCrudRepository<Produkt>{
	
	@PostConstruct
	public void init() {

			save(new Produkt("Treaclemoon", "Körpermilch Pretty Rose Hearts", "Schütz vor austrocknen. Leicht verteilbar und schnelleinziehend.", "blumig", "5,99",Waehrungtyp.EURO, "350", Mengentyp.MILLILITER));
			save(new Produkt("i+m", "Hytro Perform Reinigungsmilch", "Für normale bis trockene Haut. Reinigend ung Pflegend. Klärend bis porenverfeinernd.", "frisch", "9,99",Waehrungtyp.EURO, "350", Mengentyp.MILLILITER));
			save(new Produkt("i+m","Volumen Haarspülung", "Für alle Haar","blumig", "9,99",Waehrungtyp.EURO, "200", Mengentyp.MILLILITER));
			save(new Produkt("Garnier", "Mizellen Reinigungswasswe", "Reinigt empfindliche Haut. Legt sich wie ein Magnet auf der Haut und reinigt schonend und wirksam die Haut.", "blumig - orientalisch", "4,99",Waehrungtyp.EURO, "400", Mengentyp.MILLILITER));
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