package de.hsb.app.os.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean("warenkorb")
@SessionScoped
public class Warenkorb {
	private String artikel;
	private List<Produkt> article;
	
	public Warenkorb() {
		article = new ArrayList<>();
	}
	
	public void addProdukt(Produkt p) {
		article.add(new Produkt(p.getMarke(), p.getTitel(), p.getBeschreibung(),
									p.getDuftnote(), p.getPreis(), p.getWaehrungtyp(),
									p.getMenge(),p.getMengentyp(),p.getKategorie()));
	}
	public void deleteProdukt(int index) {
		article.remove(index);
	}
	public List<Produkt> getArticle() {
		return article;
	}

	public void setArticle(List<Produkt> article) {
		this.article = article;
	}

	public String getArtikel() {
		return artikel;
	}

	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}
	

}
