package de.hsb.app.os.enumuration;

import java.util.ResourceBundle;

public enum Rolle {
	ADMIN("Admin"), KUNDE("Kunde");
	private final String label;
	
	ResourceBundle myResources = ResourceBundle.getBundle("de.hsb.app.kv.i18n.messages");

	private Rolle(String label) {
		this.label = label;
	}

	public String getLabel() {
        return myResources.getString(label);
    }
}
