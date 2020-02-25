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
	private Produkt merkeRdmProdukt1 = new Produkt();
	private Produkt merkeRdmProdukt2 = new Produkt();
	private Produkt merkeRdmProdukt3 = new Produkt();

	private String merkeSuchTag = "";
	
	private Produkt merkeNeuheit1 = new Produkt();
	private Produkt merkeNeuheit2 = new Produkt();
	private Produkt merkeNeuheit3 = new Produkt();


	private List<Produkt> produkte;
	private ListDataModel<Produkt> pdList;

	/** Erstellung eines Datamodels aus der Klasse Produkt */
//	private DataModel<Produkt> pdlist = new ListDataModel<Produkt>();

	@PostConstruct
	public void init() {
		if (this.findAll().isEmpty()) {
			produkte = new ArrayList<>();

			// Create Duefte
			produkte.add(new Produkt("Valentino", "Valentina", "Das Valentina Parfüm vereint moderne mit klassischem.",
					"49,99", Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>(),
					"resources/images/Valentina.PNG"));
			produkte.add(new Produkt("Guess", "1981", "In der Kopfnote wird Veilchen und Abrette verbunden.", "27,99",
					Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>(),
					"resources/images/1981.PNG"));
			produkte.add(new Produkt("Boss", "Ma Vie", "In der Kopfnote befindet sich Kaktusblüten.", "35,99",
					Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>(),
					"resources/images/Ma Vie.PNG"));
			produkte.add(new Produkt("Michael Kors", "Wonderlust",
					"In der Kopfnote wird Bergamotte, rosa Pfeffer und feiner Mandelmilch verbunden.", "49,99",
					Waehrungtyp.EURO, "30", Mengentyp.MILLILITER, Kategorie.DUEFTE, new ArrayList<>(),
					"resources/images/Wonderlust.PNG"));
			// Create Pflege
			produkte.add(new Produkt("Treaclemoon", "Körpermilch Pretty Rose Hearts",
					"Schütz vor austrocknen. Leicht verteilbar und schnelleinziehend.", "5,99", Waehrungtyp.EURO, "350",
					Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>(),
					"resources/images/Koerpermilch Pretty Rose Hearts.PNG"));
			produkte.add(new Produkt("i+m", "Hytro Perform Reinigungsmilch", "Für normale bis trockene Haut.", "9,99",
					Waehrungtyp.EURO, "350", Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>(),
					"resources/images/Hytro Perform Reinigungsmilch.PNG"));
			produkte.add(new Produkt("i+m", "Volumen Haarspülung", "Für alle Haar", "9,99", Waehrungtyp.EURO, "200",
					Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>(),
					"resources/images/Volumen Haarspuelung.PNG"));
			produkte.add(new Produkt("Garnier", "Mizellen Reinigungswasser", "Reinigt empfindliche Haut.", "4,99",
					Waehrungtyp.EURO, "400", Mengentyp.MILLILITER, Kategorie.PFLEGE, new ArrayList<>(),
					"resources/images/Mizellen Reinigungswasser.PNG"));
			// Create Make-Up
			produkte.add(new Produkt("L'oreal", "Blush Sculpt 201", "Natürliche Akt-Shades.", "5,99", Waehrungtyp.EURO,
					"30", Mengentyp.GRAMM, Kategorie.MAKEUP, new ArrayList<>(),
					"resources/images/Blush Sculpt 201.PNG"));
			produkte.add(new Produkt("Maybelline", "The Graffiti Nudes",
					"Enthält 12 wunderschöne Liedschatten, die perfekt aufeinander abgestimmt sind.", "11,99",
					Waehrungtyp.EURO, "50", Mengentyp.GRAMM, Kategorie.MAKEUP, new ArrayList<>(),
					"resources/images/The Graffiti Nudes.PNG"));
			produkte.add(new Produkt("Maybelline", "Vivid Matte Liquid", "Mittlere Deckkraft. Schimmernd/Glänzend.",
					"6,99", Waehrungtyp.EURO, "7,7", Mengentyp.MILLILITER, Kategorie.MAKEUP, new ArrayList<>(),
					"resources/images/Vivid Matte Liquid.PNG"));
			produkte.add(new Produkt("Essence", "Künstliche Wimpern", "Lassen sich einfach und schnell aufkleben.",
					"2,99", Waehrungtyp.EURO, "1", Mengentyp.STUECK, Kategorie.MAKEUP, new ArrayList<>(),
					"resources/images/Kuenstliche Wimpern.PNG"));

			for (Produkt produkt : produkte) {
				this.save(produkt);
			}

			pdList = new ListDataModel<Produkt>();
			pdList.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
		}
	}

	public List<Produkt> findBySuche(){
		System.out.println(merkeSuchTag);
		Query query = this.em.createQuery("Select pr from Produkt pr" + " where pr.titel = :titel or pr.marke = :marke");
		query.setParameter("titel", merkeSuchTag);
		query.setParameter("marke", merkeSuchTag);
		if(query.getResultList().isEmpty()) {
			return new ArrayList<Produkt>();
		}else
			return uncheckedSolver(query.getResultList());
	}

	public String toSuche(){
		List<Produkt> list = findBySuche();
		if(list.isEmpty())
			return "startseite?faces-redirect=true";
		else
			return "suche?faces-redirect=true";
	}

	/** Hier legen wir neue Artikel an */
	public String ProduktAnlegen() {
		merkeProdukt = new Produkt();
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

	public String pdedit(Produkt produkt) {
		merkeProdukt = produkt;
		return "neuProdukt?faces-redirect=true";
	}

	public String pdspeichern() {
		try {
			utx.begin();
			merkeProdukt = em.merge(merkeProdukt);
//			pdList = em.merge(pdList);
			em.persist(merkeProdukt);
			pdList.setWrappedData(em.createNamedQuery("SelectProdukt").getResultList());
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "produkte?faces-redirect=true";

	}

	public String getProductPath(Produkt p) {
		if (p.getPicturePath() == null)
			return "resources/images/COS'MO.PNG";
		else
			return p.getPicturePath();
	}

	/**
	 * Loescht einen Eintrag in der Produkt-Liste.
	 */
	public String pddelete(Produkt produkt) {
		merkeProdukt = produkt;
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
/*
 * --------------> Anfang Random startseite.xhtml
 */
	public String makeToRdmProdukt1() {
		merkeRdmProdukt1 = getRandomProdukt();
		while (merkeRdmProdukt1.getTitel() == null || merkeRdmProdukt1.getTitel().equals(merkeRdmProdukt2.getTitel()) || merkeRdmProdukt1.getTitel().equals(merkeRdmProdukt3.getTitel())) {
			merkeRdmProdukt1 = getRandomProdukt();
		}
		return "/os/ersteEmpfehlung.xhtml";
	}
	public String makeToRdmProdukt2() {
		merkeRdmProdukt2 = getRandomProdukt();
		while (merkeRdmProdukt2.getTitel() == null || merkeRdmProdukt2.getTitel().equals(merkeRdmProdukt1.getTitel()) || merkeRdmProdukt2.getTitel().equals(merkeRdmProdukt3.getTitel())) {
			merkeRdmProdukt2 = getRandomProdukt();
		}
		return "/os/zweiteEmpfehlung.xhtml";
	}
	public String makeToRdmProdukt3() {
		merkeRdmProdukt3 = getRandomProdukt();
		while (merkeRdmProdukt3.getTitel() == null || merkeRdmProdukt3.getTitel().equals(merkeRdmProdukt1.getTitel()) || merkeRdmProdukt3.getTitel().equals(merkeRdmProdukt2.getTitel())) {
			merkeRdmProdukt3 = getRandomProdukt();
		}
		return "/os/dritteEmpfehlung.xhtml";
	}
	
	private Produkt getRandomProdukt() {
		Query query = this.em.createQuery("select pr from Produkt pr");
		List<Produkt> pds = this.uncheckedSolver(query.getResultList());
		Produkt rdmProdukt = new Produkt();
		if (!pds.isEmpty()) {
			int tag = (int) (Math.random() * pds.size());
			rdmProdukt = pds.get((tag) % pds.size());
			System.out.println("Tag: " + tag + " Value: " + rdmProdukt);
		}
		return rdmProdukt;
	}
/*
 * --------------> Ende Random startseite.xhtml
 * 
 * --------------> Anfang Neuheit startseite.xhtml
 */
	
	public String makeToNeuheit1() {
		merkeNeuheit1 = getLatestProdukt(1);
		return "/os/letzteNeuheit.xhtml";
	}
	public String makeToNeuheit2() {
		merkeNeuheit2 = getLatestProdukt(2);
		return "/os/zweitletzteNeuheit.xhtml";
	}
	public String makeToNeuheit3() {
		merkeNeuheit3 = getLatestProdukt(3);
		return "/os/drittletzteNeuheit.xhtml";
	}
	
	private Produkt getLatestProdukt(int idx) {
		Query query = this.em.createQuery("select pr from Produkt pr");
		List<Produkt> pds = this.uncheckedSolver(query.getResultList());
		Produkt rdmProdukt = new Produkt();
		if (!pds.isEmpty()) {
			int tag = pds.size() - idx;
			rdmProdukt = pds.get((tag) % pds.size());
			System.out.println("Tag: " + tag + " Value: " + rdmProdukt);
		}
		return rdmProdukt;
	}

	/*
	 * --------------> Ende Neuheit startseite.xhtml
	 */

	public String ansicht(Produkt produkt) {
		if (produkt != null) {
			merkeProdukt = produkt;
			return "produkt?faces-redirect=true";
		}
		return "startseite?faces-redirect=true";
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

	public Waehrungtyp[] getWaehrungtypValues() {
		return Waehrungtyp.values();
	}

	public void setMerkeProdukt(Produkt merkeProdukt) {
		this.merkeProdukt = merkeProdukt;
	}

	public Produkt getMerkeRdmProdukt1() {
		return merkeRdmProdukt1;
	}

	public void setMerkeRdmProdukt1(Produkt merkeRdmProdukt1) {
		this.merkeRdmProdukt1 = merkeRdmProdukt1;
	}

	public Produkt getMerkeRdmProdukt2() {
		return merkeRdmProdukt2;
	}

	public void setMerkeRdmProdukt2(Produkt merkeRdmProdukt2) {
		this.merkeRdmProdukt2 = merkeRdmProdukt2;
	}

	public Produkt getMerkeRdmProdukt3() {
		return merkeRdmProdukt3;
	}

	public void setMerkeRdmProdukt3(Produkt merkeRdmProdukt3) {
		this.merkeRdmProdukt3 = merkeRdmProdukt3;
	}

	public String getMerkeSuchTag() {
		return merkeSuchTag;
	}

	public void setMerkeSuchTag(String merkeSuchTag) {
		this.merkeSuchTag = merkeSuchTag;
	}

	public Produkt getMerkeNeuheit1() {
		return merkeNeuheit1;
	}

	public void setMerkeNeuheit1(Produkt merkeNeuheit1) {
		this.merkeNeuheit1 = merkeNeuheit1;
	}

	public Produkt getMerkeNeuheit2() {
		return merkeNeuheit2;
	}

	public void setMerkeNeuheit2(Produkt merkeNeuheit2) {
		this.merkeNeuheit2 = merkeNeuheit2;
	}

	public Produkt getMerkeNeuheit3() {
		return merkeNeuheit3;
	}

	public void setMerkeNeuheit3(Produkt merkeNeuheit3) {
		this.merkeNeuheit3 = merkeNeuheit3;
	}

}