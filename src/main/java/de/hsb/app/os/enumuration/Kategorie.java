package de.hsb.app.os.enumuration;

public enum Kategorie {
	
	DUEFTE("Düfte"), MAKEUP("Make-Up"), PFLEGE("Pflege");
	
	private final String label;
	
	private Kategorie(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
