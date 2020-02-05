package de.hsb.app.os.controller;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ListDataModel;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.faces.model.DataModel;

import de.hsb.app.os.enumuration.Anrede;
import de.hsb.app.os.enumuration.Kategorie;
import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;
import de.hsb.app.os.model.Produkt;
import de.hsb.app.os.repository.AbstractCrudRepository;

@ManagedBean(name = "produktHandler")
@ApplicationScoped
public class ProduktHandler extends AbstractCrudRepository<Produkt> {

	private Produkt merkeProdukt = new Produkt();

	private List<Produkt> produkte;

	private ListDataModel<Produkt> pdList;


	@PostConstruct
	public void init() {
		if (this.findAll().isEmpty()) {
			produkte = new ArrayList<>();

			// Create Duefte
			produkte.add(new Produkt("Valentino", "Valentina",
					"Das Valentina Parfüm vereint moderne mit klassischem.", "49,99", Waehrungtyp.EURO, "30",
					Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>()));
			produkte.add(new Produkt("Guess", "1981",
					"In der Kopfnote wird Veilchen und Abrette verbunden.",
					"27,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>()));
			produkte.add(new Produkt("Boss", "Ma Vie",
					"In der Kopfnote befindet sich Kaktusblüten.",
					"35,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>()));
			produkte.add(new Produkt("Michael Kors", "Wonderlust",
					"In der Kopfnote wird Bergamotte, rosa Pfeffer und feiner Mandelmilch verbunden.",
					"49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>()));
			// Create Pflege
			produkte.add(new Produkt("Treaclemoon", "Körpermilch Pretty Rose Hearts",
					"Schütz vor austrocknen. Leicht verteilbar und schnelleinziehend.","5,99",
					Waehrungtyp.EURO, "350", Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>()));
			produkte.add(new Produkt("i+m", "Hytro Perform Reinigungsmilch",
					"Für normale bis trockene Haut.",
					"9,99", Waehrungtyp.EURO, "350", Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>()));
			produkte.add(new Produkt("i+m", "Volumen Haarspülung", "Für alle Haar", "9,99", Waehrungtyp.EURO,
					"200", Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>()));
			produkte.add(new Produkt("Garnier", "Mizellen Reinigungswasser",
					"Reinigt empfindliche Haut.",
					"4,99", Waehrungtyp.EURO, "400", Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>()));
			// Create Make-Up
			produkte.add(new Produkt("L'oreal", "Blush Sculpt 201",
					"Natürliche Akt-Shades.", "5,99",
					Waehrungtyp.EURO, "30", Mengentyp.GRAMM, Kategorie.MAKEUP, new ArrayList<>()));
			produkte.add(new Produkt("Maybelline", "The Graffiti Nudes",
					"Enthält 12 wunderschöne Liedschatten, die perfekt aufeinander abgestimmt sind.", "11,99",
					Waehrungtyp.EURO, "50", Mengentyp.GRAMM, Kategorie.MAKEUP, new ArrayList<>()));
			produkte.add(new Produkt("Maybelline", "Vivid Matte Liquid",
					"Mittlere Deckkraft. Schimmernd/Glänzend.", "6,99",
					Waehrungtyp.EURO, "7,7", Mengentyp.MILLILITER, Kategorie.MAKEUP, new ArrayList<>()));
			produkte.add(new Produkt("Essence", "Künstliche Wimpern",
					"Lassen sich einfach und schnell aufkleben.", "2,99",
					Waehrungtyp.EURO, "1", Mengentyp.STUECK, Kategorie.MAKEUP, new ArrayList<>()));

			for (Produkt produkt : produkte){
				this.save(produkt);
			}

            pdList = new ListDataModel<Produkt>();
            pdList.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
		}
	}


	/**Hier wird der Vorgang abgebrochen*/
	public String abbrechen() {
		return "produkte?faces-redirect=true";
	}


//dient lediglich als platzhalter
	public String aktualisieren() {
		return "warenkorb?faces-redirect=true";
	}
	
	/**Hier legen wir neue Artikel an*/
	public String ProduktAnlegen() {
		try {
//			pdList.setRowIndex(0);
			merkeProdukt = new Produkt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "neuProdukt?faces-redirect=true";
	}
	

	public String speichern() {
		try {
			utx.begin();
			pdList = em.merge(pdList);
			em.persist(pdList);
			pdList.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "produkte?faces-redirect=true";
	}
	
	public String pdedit() {
		merkeProdukt = pdList.getRowData();
		return "neuProdukt?faces-redirect=true";
	}

	public String pdabbrechen() {
		return "produkte?faces-redirect=true";
	}

	public String pdspeichern() {
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.persist(merkeProdukt);
			pdList.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "produkte?faces-redirect=true";
	}

	/**
	 * Loescht einen Eintrag in der Produkt-Liste.
	 */
	// pddelete(Produkt produkt)
	public String pddelete() {
		merkeProdukt = pdList.getRowData();
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
			em.remove(merkeProdukt);
			pdList.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "produkte?faces-redirect=true";
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
		return Produkt.class;
	}

	@Override
	protected String getQueryCommand() {
		return "select p from Produkt p";
	}

	@Override
	protected String getSelect() {
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
	
	public String ansicht(Produkt produkt) {
		if (produkt != null){
			merkeProdukt = produkt;
			return "Produkt?faces-redirect=true";
		}
		return "Startseite?faces-redirect=true";
	}

	public String indenWarenkorb() {
		return "warenkorb?faces-redirect=true";
	}



	public Produkt getMerkeProdukt() {
		return merkeProdukt;
	}

	public Kategorie[] getKategorieValues() {
		return Kategorie.values();
	}

	public Mengentyp[] getMengentypValues() {
		return Mengentyp.values();
	}

	public void setMerkeProdukt(Produkt merkeProdukt) {
		this.merkeProdukt = merkeProdukt;
	}
}