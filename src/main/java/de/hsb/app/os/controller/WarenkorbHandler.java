package de.hsb.app.os.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import de.hsb.app.os.model.Produkt;

@ManagedBean(name = "warenkorbhd")
@SessionScoped
/* 
 * Die Bean wird für die gesamte Session erhalten bleiben bzw. wird einem User
 * zugeordnet, somit bleibt der Warenkorb für die gesamte Browsersession erhalten.
 */
public class WarenkorbHandler implements Serializable {

	private static final long serialVersionUID = 1332117572442977016L;
	
	//Warenkorb
	private List<Produkt> warenkorb = new ArrayList<>();
	
	public WarenkorbHandler() {
		
	}
	//damit können wir dann gleich anzeigen, das ein Artikel
	//hinzugefügt oder entfernt wurde
	public void addMessage(String summary) {
		//aktuelle FacesContext Instance besorgen
		FacesContext ctx = FacesContext.getCurrentInstance();
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}
	
	/**
	 * Den gesamten Warenkorb ausgeben
	 * @return
	 */
	public List<Produkt> getWarenkorb() {
		return warenkorb;
	}
	public void addProdukte(Produkt produkt) {
		this.warenkorb.add(produkt);
		addMessage(produkt.getTitel() + "wurde in den Warenkorb hinzugefügt");
		
	}
	
	public void Produktentfernen(Produkt produkt) {
		this.warenkorb.remove(produkt);
		addMessage(produkt.getTitel() + "wurde aus dem Warenkorb entfern");
	}
	
}
