package de.hsb.app.os.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import de.hsb.app.os.enumuration.Kategorie;
import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;
import de.hsb.app.os.model.Produkt;
import de.hsb.app.os.repository.AbstractCrudRepository;

@ManagedBean(name = "produktHandler")
@ApplicationScoped
public class ProduktHandler extends AbstractCrudRepository<Produkt> {

	@PostConstruct
	public void init() {
		if (this.findAll().isEmpty()) {
			List<Produkt> produkte = new ArrayList<>();
			// Create Duefte
			produkte.add(new Produkt("Valentino", "Valentina",
					"Das Valentina Parfüm vereint moderne mit klassischem. In der Kopfnote wird Bergamotte",
					"blumig – orientalisch", "49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE));
			produkte.add(new Produkt("Guess", "1981",
					"In der Kopfnote wird Veilchen und Abrette verbunden. In der Herznote entfalten sich", "blumig",
					"27,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE));
			produkte.add(new Produkt("Boss", " Ma Vie Pour Femme",
					"In der Kopfnote befindet sich Kaktusblüten. In der Herznote entfalten sich Pinke", "blumig",
					"35,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE));
			produkte.add(new Produkt("Michael Kors", "Wonderlust",
					"In der Kopfnote wird Bergamotte, rosa Pfeffer und feiner Mandelmilch verbunden.",
					"blumig - orientalisch", "49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE));
			// Create Pflege
			produkte.add(new Produkt("Treaclemoon", "Körpermilch Pretty Rose Hearts",
					"Schütz vor austrocknen. Leicht verteilbar und schnelleinziehend.", "blumig", "5,99",
					Waehrungtyp.EURO, "350", Mengentyp.MILLILITER, Kategorie.PFLEGE));
			produkte.add(new Produkt("i+m", "Hytro Perform Reinigungsmilch",
					"Für normale bis trockene Haut. Reinigend ung Pflegend. Klärend bis porenverfeinernd.", "frisch",
					"9,99", Waehrungtyp.EURO, "350", Mengentyp.MILLILITER, Kategorie.PFLEGE));
			produkte.add(new Produkt("i+m", "Volumen Haarspülung", "Für alle Haar", "blumig", "9,99", Waehrungtyp.EURO,
					"200", Mengentyp.MILLILITER, Kategorie.PFLEGE));
			produkte.add(new Produkt("Garnier", "Mizellen Reinigungswasswe",
					"Reinigt empfindliche Haut. Legt sich wie ein Magnet auf der Haut und reinigt schonend und wirksam die Haut.",
					"blumig - orientalisch", "4,99", Waehrungtyp.EURO, "400", Mengentyp.MILLILITER, Kategorie.PFLEGE));
			// Create Make-Up
			produkte.add(new Produkt("L'oreal", "Blush Sculpt 201",
					"Natürliche Akt-Shades. Verwenden Sie es allein oder mischen Sie die Farbtöne", "...", "5,99",
					Waehrungtyp.EURO, "30", Mengentyp.GRAMM, Kategorie.MAKEUP));
			produkte.add(new Produkt("Maybelline", "The Graffiti Nudes",
					"Enthält 12 wunderschöne Liedschatten, die perfekt aufeinander abgestimmt sind.", "...", "11,99",
					Waehrungtyp.EURO, "50", Mengentyp.GRAMM, Kategorie.MAKEUP));
			produkte.add(new Produkt("Maybelline", "Vivid Matte Liquid",
					"Mittlere Deckkraft. Schimmernd/Glänzend. Zaubert einen frischen Teint.", "blumig", "6,99",
					Waehrungtyp.EURO, "7,7", Mengentyp.MILLILITER, Kategorie.MAKEUP));
			produkte.add(new Produkt("Essence", "Künstliche Wimpern",
					"Lassen sich einfach und schnell aufkleben. Lässt die Augen strahlen", "...", "2,99",
					Waehrungtyp.EURO, "3", Mengentyp.MILLILITER, Kategorie.MAKEUP));

			int id = 1;
			for (Produkt produkt : produkte) {
				// Setze die Id des Produktes ...
				produkt.setId(id++);
				// ... und speichere das Produkt
				save(produkt);
			}
		}
	}

	public String neu() {
		selectedEntity = new Produkt();
		return "produkt";
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
		return "produkt";
	}

	public String edit() {
		selectedEntity = entityList.getRowData();
		return "neuesProdukt";
	}

	public String getProduktFullName() {
		return selectedEntity.getMarke() + " " + selectedEntity.getTitel();
	}

	public List<Produkt> findAllByKategorie(Kategorie kategorie) {
		Query query = this.em.createQuery("select pr from Produkt pr where pr.kategorie = :kategorie");
		query.setParameter("kategorie", kategorie);
		return this.uncheckedSolver(query.getResultList());
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

	/**
	 * Soll die Warnung "Unchecked cast" loesen.
	 *
	 * @param var Object
	 * @return List<Projekt>
	 */
	@Override
	public List<Produkt> uncheckedSolver(Object var) {
		final List<Produkt> result = new ArrayList<>();
		if (var instanceof List) {
			for (int i = 0; i < ((List<?>) var).size(); i++) {
				final Object item = ((List<?>) var).get(i);
				if (item instanceof Produkt) {
					result.add((Produkt) item);
				}
			}
		}
		return result;
	}
}