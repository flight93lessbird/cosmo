package de.hsb.app.os.controller;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

import org.primefaces.context.RequestContext;

import de.hsb.app.os.enumuration.Rolle;
import de.hsb.app.os.model.Benutzer;
import de.hsb.app.os.model.Produkt;

@ManagedBean(name = "mbOS")
@SessionScoped
public class cosmoHandler {

	private DataModel<Produkt> os = new ListDataModel<Produkt>();
	private DataModel<Benutzer> user = new ListDataModel<Benutzer>();
	private Benutzer merkeBenutzer = new Benutzer();

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	public void QuittungBe() {
		RequestContext.getCurrentInstance().openDialog("quittung");
	}

	public String Benutzer() {
		try {
			os.setRowIndex(0);
			merkeBenutzer = user.getRowData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "benutzer";
	}

	public String toNeuerBenutzer() {
		try {
			os.setRowIndex(0);
			merkeBenutzer = new Benutzer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "neuerBenutzer";
	}

	public String toLogin() {
		return "konto";
	}

	public String toRegistrieren() {
		merkeBenutzer = new Benutzer();
		return "registrieren";
	}

	public String benutzerRegistrieren() {
		for (Benutzer b : user) {
			if (b.getUsername().equals(merkeBenutzer.getUsername())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dieser Benutzername "
								+ "ist bereits vergeben oder erfüllt nicht die vom Administrator festgelegten Richtlinien.",
								null));
				;

				return null;
			}
		}
		try {
			merkeBenutzer.setRolle(Rolle.KUNDE);
			utx.begin();
			merkeBenutzer = em.merge(merkeBenutzer);
			em.persist(merkeBenutzer);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Herzlich Willkommen und vielen Dank für Ihre Registrierung.", null));

		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "konto";
	}

	public String benutzerAnlegen() {
		for (Benutzer b : user) {
			if (b.getUsername().equals(merkeBenutzer.getUsername())) {

				FacesContext.getCurrentInstance()
						.addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Dieser Benutzername ist bereits vergeben "
												+ "oder erfüllt nicht die vom Administrator festgelegten Richtlinien",
										null));
				return null;
			}
		}
		try {
			utx.begin();
			merkeBenutzer = em.merge(merkeBenutzer);
			em.persist(merkeBenutzer);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();

		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}
		return "benutzer";
	}

	public String neuBenutzer() {
		try {
			os.setRowIndex(0);
			merkeBenutzer = user.getRowData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "benutzer";
	}

	public String deleteUser() {
		merkeBenutzer = user.getRowData();
		try {
			utx.begin();
			merkeBenutzer = em.merge(merkeBenutzer);
			em.remove(merkeBenutzer);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DataModel<Produkt> getOs() {
		return os;
	}

	public void setOs(DataModel<Produkt> os) {
		this.os = os;
	}

	public DataModel<Benutzer> getUser() {
		return user;
	}

	public void setUser(DataModel<Benutzer> user) {
		this.user = user;
	}

	public Benutzer getMerkeBenutzer() {
		return merkeBenutzer;
	}

	public void setMerkeBenutzer(Benutzer merkeBenutzer) {
		this.merkeBenutzer = merkeBenutzer;
	}

}
