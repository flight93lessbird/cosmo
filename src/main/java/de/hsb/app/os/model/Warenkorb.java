package de.hsb.app.os.model;

import com.sun.javafx.scene.control.skin.VirtualFlow; 

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NamedQuery(name = "SelectWarenkorb", query = "Select w from Warenkorb w")
@Entity
public class Warenkorb {

	@Id
	@GeneratedValue
	private Integer Id;
			
	private String artikel;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
			mappedBy = "warenkorb", orphanRemoval = true)
	private List<WarenkorbItem> warenkorbItems = new ArrayList<>();
	
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

	public List<WarenkorbItem> getWarenkorbItems() {
		return warenkorbItems;
	}

	public void setWarenkorbItems(List<WarenkorbItem> warenkorbItems) {
		this.warenkorbItems = warenkorbItems;
	}

	//	public Set<WarenkorbItem> getArticles() {
//		return articles;
//	}
//
//	public void setArticles(Set<WarenkorbItem> articles) {
//		this.articles = articles;
//	}

	
}
