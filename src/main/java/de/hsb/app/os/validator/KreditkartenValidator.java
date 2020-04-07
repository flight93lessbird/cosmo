package de.hsb.app.os.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.Locale;

@FacesValidator(value = "kreditkartenValidator")
public class KreditkartenValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String kknr = String.valueOf(value);
		ArrayList<Integer> kkintarray = new ArrayList<Integer>();
		FacesMessage message;
// ArrayList für Luhn initialisiern
		for (int i = 0; i < kknr.length(); i++)
			kkintarray.add(kknr.charAt(i) - 48); // ASCII -> Integer
		if (!kknr.matches("[0-9]+")) {
			if (FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()
					.equals(new Locale("de").getLanguage())) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültige Kreditkartennummer",
						"Es dürfen nur Ziffern enthalten sein!");
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credit card number",
						"Only digits are allowed!");
			}
			throw new ValidatorException(message);
		} else if (kknr.length() < 12 || kknr.length() > 16) {
			if (FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()
					.equals(new Locale("de").getLanguage())) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültige Kreditkartennummer",
						"Nummer ist zu lang oder zu kurz!");
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credit card number",
						"Number is too long or too short!");
			}
			throw new ValidatorException(message);
		} else if (!isValid(kkintarray)) {
			if (FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()
					.equals(new Locale("de").getLanguage())) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültige Kreditkartennummer",
						"Die Kreditkartennummer ist ungültig!");
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credit card number",
						"The credit card number is invalid!");
			}
			throw new ValidatorException(message);
		}
	}

	private boolean isValid(ArrayList<Integer> digits) {
		int sum = 0;
		int length = digits.size();
		for (int i = 0; i < length; i++) {
			Integer digit = digits.get(length - i - 1);
			// jede 2. Ziffer multipliziert mit 2
			if (i % 2 == 1)
				digit *= 2;
			sum += digit > 9 ? digit - 9 : digit;
		}
		return sum % 10 == 0;
	}
}