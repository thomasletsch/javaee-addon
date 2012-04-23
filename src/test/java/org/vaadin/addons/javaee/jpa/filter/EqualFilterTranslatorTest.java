package org.vaadin.addons.javaee.jpa.filter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.vaadin.addons.javaee.jpa.filter.EqualFilterTranslator;

import com.vaadin.data.util.filter.Compare.Equal;

public class EqualFilterTranslatorTest {

    private EqualFilterTranslator translator = new EqualFilterTranslator();

    @Test
    public void testGetAcceptedClass() {
        assertEquals(Equal.class, translator.getAcceptedClass());
    }

}
