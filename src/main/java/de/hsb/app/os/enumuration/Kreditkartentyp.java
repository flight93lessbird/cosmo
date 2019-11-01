package de.hsb.app.os.enumuration;

public enum Kreditkartentyp {
	MASTER("MasterCard"), VISA("VISA"), AMEX("American Express");
	
	private final String label;
	
	private Kreditkartentyp(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}