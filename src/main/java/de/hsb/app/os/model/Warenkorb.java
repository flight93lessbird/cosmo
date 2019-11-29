package de.hsb.app.os.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ManagedBean("warenkorb")
@Entity
public class Warenkorb {
	@Id
	@GeneratedValue
	private Integer ID;
	private String artikel;
	private Set<WarenkorbItem> articles;
	
	public Warenkorb() {
		articles = new HashSet<>();
	}
	
	public void addProdukt(Produkt p, int stkZahl) {
		articles.add(new WarenkorbItem(p, stkZahl));
	}
	public void deleteProdukt(int index) {
		articles.remove(index);
	}

	public Set<WarenkorbItem> getArticles() {
		return articles;
	}

	public void setArticles(Set<WarenkorbItem> articles) {
		this.articles = articles;
	}

	public Integer getID() {
		return ID;
	}

	public String getArtikel() {
		return artikel;
	}

	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}
	

}
