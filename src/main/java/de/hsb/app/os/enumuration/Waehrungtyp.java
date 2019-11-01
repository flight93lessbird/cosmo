package de.hsb.app.os.enumuration;

public enum Waehrungtyp {

	EURO("€"), DOLLAR("$"), PFUND("£");

	private final String label;

	private Waehrungtyp(String label) {
		this.label = label;

	}

	public String getLabel() {
		return label;
	}

}
