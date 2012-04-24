package org.vaadin.addons.javaee.jpa.filter;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vaadin.data.util.filter.And;

public class AndFilterTranslatorTest {

    private AndFilterTranslator translator = new AndFilterTranslator();

    @Test
    public void testGetAcceptedClass() {
        assertEquals(And.class, translator.getAcceptedClass());
    }

}
