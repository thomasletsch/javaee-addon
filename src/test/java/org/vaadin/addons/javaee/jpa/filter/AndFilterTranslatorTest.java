/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.vaadin.addons.javaee.jpa.filter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.vaadin.addons.javaee.container.jpa.filter.AndFilterTranslator;

import com.vaadin.data.util.filter.And;

public class AndFilterTranslatorTest {

    private AndFilterTranslator translator = new AndFilterTranslator();

    @Test
    public void testGetAcceptedClass() {
        assertEquals(And.class, translator.getAcceptedClass());
    }

}
