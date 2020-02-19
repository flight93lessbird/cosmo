package de.hsb.app.os.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.hsb.app.os.enumuration.Kategorie;
import de.hsb.app.os.enumuration.Rolle;

@ManagedBean(name="enumController")
@ApplicationScoped
public class EnumController {

	public Kategorie getDuefte(){
		return Kategorie.DUEFTE;
	}
	public Kategorie getPflege(){
		return Kategorie.PFLEGE;
	}
	public Kategorie getMakeUp(){
		return Kategorie.MAKEUP;
	}
	public Rolle getKunde(){ return Rolle.KUNDE; }
	public Rolle getAdmin(){
		return Rolle.ADMIN;
	}
}
