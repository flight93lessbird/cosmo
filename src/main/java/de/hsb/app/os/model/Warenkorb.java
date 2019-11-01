package de.hsb.app.os.model;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean("warenkorb")
@SessionScoped
public class Warenkorb {
	private String artikel;

	public String getArtikel() {
		return artikel;
	}

	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}
	

}
