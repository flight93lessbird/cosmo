package de.hsb.app.os.enumuration;

public enum Mengentyp {

	LITER("l"), GRAMM("g"), KILO("kg"), MILLILITER("ml"), MILLIGRAMM("mg"), STUECK("stk");

	private final String label;

	private Mengentyp(String label) {
		this.label = label;

	}

	public String getLabel() {
		return label;
	}

}
