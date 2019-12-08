package de.hsb.app.os.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * habe es nochmal hingefügt falls du damit weiter arbeiten möchtest.
 * @Pascal
 */
public class WarenkorbItem {
	@Id
	@GeneratedValue
	private Integer Id;

	private int stkZahl;

	private Produkt p;

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

	public int getStkZahl() {
		return stkZahl;
	}

	public void setStkZahl(int stkZahl) {
		this.stkZahl = stkZahl;
	}

	public Produkt getP() {
		return p;
	}

	public void setP(Produkt p) {
		this.p = p;
	}

}
