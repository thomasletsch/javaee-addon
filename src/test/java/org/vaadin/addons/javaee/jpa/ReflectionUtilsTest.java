package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.vaadin.addons.javaee.jpa.ReflectionUtils;

public class ReflectionUtilsTest {

    private static Log log = LogFactory.getLog(ReflectionUtilsTest.class);

    @Test
    public void testGetAllFields() {
        Field[] allFields = ReflectionUtils.getAllFields(TestEntity.class);
        log.info("Fields: " + allFields);
        assertEquals(8, allFields.length);
    }

    @Test
    public void testGetType() {
        Class<?> type = ReflectionUtils.getType(TestEntity.class, "testString");
        assertEquals(String.class, type);
    }

}
