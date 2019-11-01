package de.hsb.app.os.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "kreditkartenConverter")

public class KreditkartenConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null)
            return null;
        String s = value;
        if (value.contains(" "))
            s = s.replace(" ", "");
        if (value.contains("-"))
            s = s.replace("-", "");
        return s;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (String) value;
    }

}