package de.hsb.app.os.validator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "PasswordValidation")
public class PasswordValidation implements Validator {
	private static final String Passwort_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{3,20})";

	private Pattern pattern;
	private Matcher matcher;

	public PasswordValidation() {
		pattern = Pattern.compile(Passwort_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			if (FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()
					.equals(new Locale("de").getLanguage())) {
				FacesMessage msg = new FacesMessage(
						"Das eingegebene Passwort entspricht nicht den von uns bestimmten Sichheitsvoraussetzungen.",
						"Hinweis: Das Passwort sollte mindestens einen Gross- sowie Kleinbuchstaben enthalten. Eine Ziffer von 0-9 und eines der "
								+ "folgenden Zeichen (! @ # $ %)");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			} else {
				FacesMessage msg = new FacesMessage(
						"The password you entered does not meet our security requirements. \",\n"
								+ "\"Note: The password should contain at least one uppercase and lowercase letter. A number from 0-9 and one of the\"\n"
								+ "+ \"following characters (! @ # $%)");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}

	}
}
