/*
 * Copyright 2005, 2012 Joe Walker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.addons.javaee.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class ReflectionUtils {

    private static Log log = LogFactory.getLog(ReflectionUtils.class);

    /**
     * Return a list of all fields (whatever access status, and on whatever superclass they were defined) that can be found on this class.
     * This is like a union of {@link Class#getDeclaredFields()} which ignores and super-classes, and {@link Class#getFields()} which
     * ignored non-public fields
     * 
     * @param enclosingType
     *            The class to introspect
     * @return The complete list of fields
     */
    public static Field[] getAllFields(Class<?> enclosingType) {
        List<Class<?>> classes = getAllSuperclasses(enclosingType);
        classes.add(enclosingType);
        return getAllFields(classes);
    }

    /**
     * As {@link #getAllFields(Class)} but acts on a list of {@link Class}s and uses only {@link Class#getDeclaredFields()}.
     * 
     * @param classes
     *            The list of classes to reflect on
     * @return The complete list of fields
     */
    static Field[] getAllFields(List<Class<?>> classes) {
        Set<Field> fields = new HashSet<Field>();
        for (Class<?> enclosingType : classes) {
            fields.addAll(Arrays.asList(enclosingType.getDeclaredFields()));
        }

        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * Return a List of super-classes for the given class.
     * 
     * @param enclosingType
     *            the class to look up
     * @return the List of super-classes in order going up from this one
     */
    static List<Class<?>> getAllSuperclasses(Class<?> enclosingType) {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        Class<?> superclass = enclosingType.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }

        return classes;
    }

    public static Class<?> getType(Class<?> enclosingType, String propertyName) {
        try {
            java.lang.reflect.Field f = getField(enclosingType, propertyName);
            return f.getType();
        } catch (Exception e) {
            log.error("Could not retrieve type of " + enclosingType.getName() + "." + propertyName, e);
            return null;
        }
    }

    public static <T extends Annotation> T getAnnotation(Class<?> enclosingType, String propertyName, Class<T> annotationClass) {
        try {
            java.lang.reflect.Field f = getField(enclosingType, propertyName);
            return f.getAnnotation(annotationClass);
        } catch (Exception e) {
            log.error("Could not retrieve type of " + enclosingType.getName() + "." + propertyName, e);
            return null;
        }
    }

    static Field getField(Class<?> enclosingType, String propertyName) throws SecurityException, NoSuchFieldException {
        if (propertyName.contains(".")) {
            String[] parts = propertyName.split("\\.", 2);
            Field innerField = getField(enclosingType, parts[0]);
            return getField(innerField.getType(), parts[1]);
        } else {
            try {
                Field field = enclosingType.getDeclaredField(propertyName);
                return field;
            } catch (NoSuchFieldError e) {
                // Try super classes until we reach Object
                Class<?> superClass = enclosingType.getSuperclass();
                if (superClass != Object.class) {
                    return getField(superClass, propertyName);
                } else {
                    throw e;
                }
            }
        }
    }

}