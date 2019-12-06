package de.hsb.app.os.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@NamedQuery(name = "SelectWarenkorb", query = "Select w from Warenkorb w")
@Entity

public class Warenkorb {

	@Id
	@GeneratedValue
	private Integer Id;
			
	private String artikel;
//	private Set<WarenkorbItem> articles;
	
	public Warenkorb () {}

	public Warenkorb(String artikel)
	{
		super();
		this.artikel = artikel;
//		this.articles = articles;
	
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getArtikel() {
		return artikel;
	}

	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}

//	public Set<WarenkorbItem> getArticles() {
//		return articles;
//	}
//
//	public void setArticles(Set<WarenkorbItem> articles) {
//		this.articles = articles;
//	}

	
}
