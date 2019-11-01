package de.hsb.app.os.model;

import de.hsb.app.os.enumuration.Kreditkartentyp;

import javax.annotation.ManagedBean;
import javax.persistence.*;
import javax.validation.constraints.Size;

@NamedQuery(name = "SelectAdresse" , query = "Select a from Adresse a")
@Entity
@ManagedBean("adresse")
public class Adresse {

    @GeneratedValue
    @Id
    private int id;

    private Kreditkartentyp typ;

    @Size(min=3, max=30)
    private String strasse;

    @Size(min=3, max=30)
    private String plz;

    @Size(min=3, max=30)
    private String ort;

    public Adresse(){}

    public Adresse(Kreditkartentyp typ, String strasse, String plz, String ort) {
        this.typ = typ;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
    }

   
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Kreditkartentyp getTyp() {
		return typ;
	}

	public void setTyp(Kreditkartentyp typ) {
		this.typ = typ;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	@Override
    public String toString() {
        return this.strasse + ", " + this.plz + " " + this.ort;
    }
}
