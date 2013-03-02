package org.vaadin.addons.javaee.selenium.input;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class InputMethodFactory {

    List<InputMethod> inputMethods = new ArrayList<>();

    public InputMethodFactory(WebDriver driver) {
        inputMethods.add(new DateInputMethod(driver));
        inputMethods.add(new DropDownInputMethod(driver));
        inputMethods.add(new OptionGroupInputMethod(driver));
        inputMethods.add(new TextInputMethod(driver));
        inputMethods.add(new CheckBoxInputMethod(driver));
    }

    public InputMethod get(String entityName, String attribute) {
        for (InputMethod inputMethod : inputMethods) {
            if (inputMethod.accepts(entityName, attribute)) {
                return inputMethod;
            }
        }
        return null;
    }
}
