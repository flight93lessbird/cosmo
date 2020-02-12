package de.hsb.app.os.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModelListener;
import javax.persistence.Query;
import javax.transaction.*;

import de.hsb.app.os.enumuration.Rolle;
import de.hsb.app.os.model.Produkt;
import de.hsb.app.os.model.User;
import de.hsb.app.os.model.Warenkorb;
import de.hsb.app.os.model.WarenkorbItem;
import de.hsb.app.os.repository.AbstractCrudRepository;

@ManagedBean(name = "warenkorbhd")
@SessionScoped
/*
 * Die Bean wird für die gesamte Session erhalten bleiben bzw. wird einem User
 * zugeordnet, somit bleibt der Warenkorb für die gesamte Browsersession
 * erhalten.
 */
public class WarenkorbHandler extends AbstractCrudRepository<Warenkorb> implements Serializable {

	private static final long serialVersionUID = 1332117572442977016L;

	// Warenkorb
	private Warenkorb warenkorb = new Warenkorb();

	private int stkZahl = 1;

	public WarenkorbHandler() {

	}

	// damit können wir dann gleich anzeigen, das ein Artikel
	// hinzugefügt oder entfernt wurde
	public void addMessage(String summary) {
		// aktuelle FacesContext Instance besorgen
		FacesContext ctx = FacesContext.getCurrentInstance();
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}

	/**
	 * Den gesamten Warenkorb ausgeben
	 * 
	 * @return
	 */
	public Warenkorb getWarenkorb(User benutzer) {
		if (benutzer != null) {
			return benutzer.getWarenkorb();
		}
		return warenkorb;
	}

//	//visuell anzeigen, dass ein Artikel hinzugefügt wurde
//	public void addProdukte(Produkt produkt) {
//		this.warenkorb.add(produkt);
//		addMessage(produkt.getTitel() + "wurde in den Warenkorb hinzugefügt");
//
//	}
//	//visuell anzeigen, dass ein Artikel entfernt wurde
//	public void Produktentfernen(Produkt produkt) {
//		this.warenkorb.remove(produkt);
//		addMessage(produkt.getTitel() + "wurde aus dem Warenkorb entfernt");
//	}

	public String checkWarenkorb(User benutzer) {
		System.out.println("Checked WK");
		if (benutzer != null && !benutzer.getWarenkorb().getWarenkorbItems().isEmpty()) {
			return "/os/warenkorb.xhtml";
		} else if (!warenkorb.getWarenkorbItems().isEmpty()) {
			return "/os/warenkorb.xhtml";
		} else {
			return "/os/warenkorbLeer.xhtml";
		}
	}

	// brauchen wir nicht
	public void changeStkZahl(int zahl) {

		System.out.println(zahl);
	}

	public void changeStkZahl(Warenkorb warenkorb, WarenkorbItem item, int stkZahl) {
		if (stkZahl > 0) {
			System.out.println("Doit! Stückzahl = " + stkZahl + "Warenkorbitem = " + item.getP().getTitel());
			try {
				this.utx.begin();
				for (WarenkorbItem warenkorbItem : warenkorb.getWarenkorbItems()) {
					if (warenkorbItem.getP().getId().equals(item.getP().getId())) {
						warenkorbItem.setStkZahl(stkZahl);
						break;
					}
				}
				warenkorb = this.em.merge(this.warenkorb);
				this.em.persist(warenkorb);
				this.utx.commit();
			} catch (final NotSupportedException | SystemException | SecurityException | IllegalStateException
					| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			}
		}
	}

	public String addWarenkorbItemToWarekorb(User loggedBenutzer, Produkt produkt, int stkZahl) {
		System.out.println("Produkt: " + produkt.getTitel() + " Stückzahl: " + stkZahl);
		if (produkt != null) {
			if (loggedBenutzer != null) {
				addWarenkorbItemToWarenkorb(loggedBenutzer.getWarenkorb(), produkt, stkZahl);
			} else if (warenkorb != null) {
				System.out.println("Kein Benutzer Warenkorb");
				addWarenkorbItemToWarenkorb(warenkorb, produkt, stkZahl);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Produkt konnte dem Warenkorb nicht hinzugefuegt werden",
					"Es wurde keine Produkt ausgewaehlt."));
		}
		return "warenkorb?faces-redirect=true";
	}

	private String addWarenkorbItemToWarenkorb(Warenkorb warenkorb, Produkt produkt, int stkZahl) {
		if (stkZahl > 0) {
			try {
				this.utx.begin();
				boolean isNewItem = true;
				for (WarenkorbItem warenkorbItem : warenkorb.getWarenkorbItems()) {
					if (warenkorbItem.getP().getId().equals(produkt.getId())) {
						stkZahl = warenkorbItem.getStkZahl();
						warenkorbItem.setStkZahl(stkZahl);
						isNewItem = false;
						break;
					}
				}
				if (isNewItem) {
					produkt = this.em.merge(produkt);
					WarenkorbItem warenkorbItem = this.em.merge(new WarenkorbItem(stkZahl, produkt));
					produkt.getWarenkorbItemList().add(warenkorbItem);
					warenkorb.getWarenkorbItems().add(warenkorbItem);
					warenkorbItem.setWarenkorb(warenkorb);
//					this.em.persist(produkt);
				}
				warenkorb = this.em.merge(this.warenkorb);
				this.em.persist(warenkorb);
				this.utx.commit();
			} catch (final NotSupportedException | SystemException | SecurityException | IllegalStateException
					| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			}
		}
		return "warenkorb?faces-redirect=true";
	}

	public List<WarenkorbItem> findWarenkorbItemsByBenutzer(User loggedBenutzer) {
		if (loggedBenutzer != null) {
			Query query = this.em
					.createQuery("select u.warenkorb.warenkorbItems from User u where u.ID = :userID");
			query.setParameter("userID", loggedBenutzer.getID());
			List<WarenkorbItem> warenkorbItems = this.uncheckedSolverForWarenkorbItems(query.getResultList());
			if (warenkorbItems != null) {
				return warenkorbItems;
			} else {
				return new ArrayList<>();
			}
		}
		return warenkorb.getWarenkorbItems();
	}

	public String computeTotalPrice(String preisString, Integer stkZahl) {
		DecimalFormat f = new DecimalFormat("#0.00");
		double preis = Double.parseDouble(preisString.replace(",", "."));
		double stkueckZahl = Double.valueOf(stkZahl);
		return String.valueOf(f.format(preis * stkueckZahl)).replace(".", ",");
	}

	public String deleteWarenkorbItem(User loggedBenutzer, WarenkorbItem warenkorbItem) {
		if (warenkorbItem != null) {
			if (loggedBenutzer != null) {
				this.warenkorb = loggedBenutzer.getWarenkorb();
			}
			try {
				this.utx.begin();
				Produkt produkt = this.em.merge(warenkorbItem.getP());
				warenkorbItem = this.em.merge(warenkorbItem);
				warenkorb = this.em.merge(this.warenkorb);
				produkt.getWarenkorbItemList().remove(warenkorbItem);
				warenkorb.getWarenkorbItems().remove(warenkorbItem);
				warenkorbItem.setP(null);
				warenkorbItem.setWarenkorb(null);
//				this.em.persist(produkt);
//				this.em.persist(warenkorb);
				this.em.remove(warenkorbItem);
				this.em.flush();
				this.utx.commit();
				if (loggedBenutzer != null) {
					loggedBenutzer.setWarenkorb(this.warenkorb);
					System.out.println("WK zugeordnet: " + loggedBenutzer.getUsername());
				}
				return "warenkorb?faces-redirect=true";
			} catch (final NotSupportedException | SystemException | SecurityException | IllegalStateException
					| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				return "startseite?faces-redirect=true";
			}

		}
		return "startseite?faces-redirect=true";
	}

	public String toKundendaten(User benutzer) {
		if (benutzer != null) {
			return "kundendatenUeberpruefung?faces-redirect=true";
		} else {
			return "kundendaten?faces-redirect=true";
		}
	}

	public String toStartseite() {
		warenkorb = new Warenkorb();
		return "startseite?faces-redirect=true";
		}

	public void setWarenkorb(Warenkorb warenkorb) {
		this.warenkorb = warenkorb;
	}

	public int getStkZahl() {
		return stkZahl;
	}

	public void setStkZahl(int stkZahl) {
		this.stkZahl = stkZahl;
	}

	@Override
	protected Class<Warenkorb> getRepositoryClass() {
		return Warenkorb.class;
	}

	@Override
	protected String getQueryCommand() {
		return "select w from Warenkorb w";
	}

	@Override
	protected String getSelect() {
		return "SelectWarenkorb";
	}

	@Override
	protected List<Warenkorb> uncheckedSolver(Object var) {
		final List<Warenkorb> result = new ArrayList<>();
		if (var instanceof List) {
			for (int i = 0; i < ((List<?>) var).size(); i++) {
				final Object item = ((List<?>) var).get(i);
				if (item instanceof Warenkorb) {
					result.add((Warenkorb) item);
				}
			}
		}
		return result;
	}

	private List<WarenkorbItem> uncheckedSolverForWarenkorbItems(Object var) {
		final List<WarenkorbItem> result = new ArrayList<>();
		if (var instanceof List) {
			for (int i = 0; i < ((List<?>) var).size(); i++) {
				final Object item = ((List<?>) var).get(i);
				if (item instanceof WarenkorbItem) {
					result.add((WarenkorbItem) item);
				}
			}
		}
		return result;
	}

}
