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

import de.hsb.app.os.enumuration.Rolle;
import de.hsb.app.os.model.Benutzer;
import de.hsb.app.os.model.Produkt;
import de.hsb.app.os.model.Warenkorb;

@ManagedBean(name = "mbos")
@SessionScoped
public class OSHandler {
	public OSHandler() {
	}

	
}
