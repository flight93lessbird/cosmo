package de.hsb.app.os.enumuration;

import java.util.ResourceBundle;

public enum Rolle {
	ADMIN("Admin"), KUNDE("Kunde");
	private final String label;
	
	private Rolle(String label) {
		this.label = label;
	}

	public String getLabel() {
        return label;
    }
}
