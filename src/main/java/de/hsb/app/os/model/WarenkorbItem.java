package de.hsb.app.os.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class WarenkorbItem {
	@Id
	@GeneratedValue
	private Integer ID;
	private int stkZahl;
	private Produkt p;
	
	public WarenkorbItem() {}
	
	public WarenkorbItem(Produkt pro, int anzahl) {
		p = pro;
		stkZahl = anzahl;
	}

	public Produkt getP() {
		return p;
	}

	public void setP(Produkt p) {
		this.p = p;
	}

	public int getStkZahl() {
		return stkZahl;
	}

	public void setStkZahl(int stkZahl) {
		this.stkZahl = stkZahl;
	}
	
}
