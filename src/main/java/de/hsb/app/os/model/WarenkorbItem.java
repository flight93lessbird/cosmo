package de.hsb.app.os.model;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.persistence.*;

/*
 * @Emma: habe es nochmal hingefügt falls du damit weiter arbeiten möchtest.
 * @Pascal
 */
@NamedQuery(name = "SelectWarenkorbItem", query = "Select wki from WarenkorbItem wki")
@Entity

public class WarenkorbItem {
	@Id
	@GeneratedValue
	private Integer Id;

	private int stkZahl;

	@ManyToOne
	private Produkt p;

	@ManyToOne
	private Warenkorb warenkorb;

	public WarenkorbItem() {
	}

	public WarenkorbItem(int stkZahl, Produkt p) {
		super();
		this.stkZahl = stkZahl;
		this.p = p;

	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	/**
	 * get stkZahl value
	 * @return int
	 */
	public int getStkZahl() {
		return stkZahl;
	}
	/**
	 * set stkZahl value
	 * @param spinnerValue
	 */
	public void setStkZahl(int stkZahl) {
		this.stkZahl = stkZahl;
	}

	public Produkt getP() {
		return p;
	}

	public void setP(Produkt p) {
		this.p = p;
	}

	public Warenkorb getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(Warenkorb warenkorb) {
		this.warenkorb = warenkorb;
	}
}
