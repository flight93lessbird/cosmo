package de.hsb.app.os.model;

import de.hsb.app.os.enumuration.Kreditkartentyp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Adresse.class)
public abstract class Adresse_ {

	public static volatile SingularAttribute<Adresse, String> ort;
	public static volatile SingularAttribute<Adresse, String> strasse;
	public static volatile SingularAttribute<Adresse, Kreditkartentyp> typ;
	public static volatile SingularAttribute<Adresse, Integer> id;
	public static volatile SingularAttribute<Adresse, String> plz;

}

