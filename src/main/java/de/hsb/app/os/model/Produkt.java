package de.hsb.app.os.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

import de.hsb.app.os.enumuration.Kategorie;
import de.hsb.app.os.enumuration.Mengentyp;
import de.hsb.app.os.enumuration.Waehrungtyp;

import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "SelectProdukt", query = "Select p from Produkt p")
@Entity

public class Produkt {

	@Id
	@GeneratedValue
	private Integer Id;

	@Size(min = 3, max = 30)
	private String marke;

	private String titel;

	private String beschreibung;

	private String menge;

	private Mengentyp mengentyp;

	private String preis;

	private Waehrungtyp waehrungtyp;

	private Kategorie kategorie;

	private String picturePath;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "warenkorb", orphanRemoval = true)
	private List<WarenkorbItem> warenkorbItemList = new ArrayList<>();

	public Produkt() {
	}

	public Produkt(String marke, String titel, String beschreibung, String preis, Waehrungtyp waehrungtyp, String menge,
			Mengentyp mengentyp, Kategorie kategorie, List<WarenkorbItem> warenkorbItemList) {
		super();
		this.marke = marke;
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.preis = preis;
		this.waehrungtyp = waehrungtyp;
		this.menge = menge;
		this.mengentyp = mengentyp;
		this.kategorie = kategorie;
		this.warenkorbItemList = warenkorbItemList;
		picturePath = "resources/images/COS'MO.PNG";
	}

	public Produkt(String marke, String titel, String beschreibung, String preis, Waehrungtyp waehrungtyp, String menge,
			Mengentyp mengentyp, Kategorie kategorie, List<WarenkorbItem> warenkorbItemList, String picPath) {
		super();
		this.marke = marke;
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.preis = preis;
		this.waehrungtyp = waehrungtyp;
		this.menge = menge;
		this.mengentyp = mengentyp;
		this.kategorie = kategorie;
		this.warenkorbItemList = warenkorbItemList;
		this.picturePath = picPath;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getMenge() {
		return menge;
	}

	public void setMenge(String menge) {
		this.menge = menge;
	}

	public Mengentyp getMengentyp() {
		return mengentyp;
	}

	public void setMengentyp(Mengentyp mengentyp) {
		this.mengentyp = mengentyp;
	}

	public String getPreis() {
		return preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}

	public Waehrungtyp getWaehrungtyp() {
		return waehrungtyp;
	}

	public void setWaehrungtyp(Waehrungtyp waehrungtyp) {
		this.waehrungtyp = waehrungtyp;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public List<WarenkorbItem> getWarenkorbItemList() {
		return warenkorbItemList;
	}

	public void setWarenkorbItemList(List<WarenkorbItem> warenkorbItemList) {
		this.warenkorbItemList = warenkorbItemList;
	}

	public String getPicturePath() {
		System.out.println(picturePath);
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
}
