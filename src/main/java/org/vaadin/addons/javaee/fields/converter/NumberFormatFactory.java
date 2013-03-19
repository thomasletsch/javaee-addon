/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.vaadin.addons.javaee.fields.converter;

import java.text.NumberFormat;
import java.util.Currency;

import com.vaadin.data.util.BeanItem;

public class NumberFormatFactory {

    @SuppressWarnings("rawtypes")
    public static NumberFormat getNumberFormat(final String currencyField, BeanItem beanItem) {
        String currencyCode = getCurrencyCode(currencyField, beanItem);
        NumberFormat fmt = getNumberFormat(currencyCode);
        return fmt;
    }

    public static NumberFormat getNumberFormat(String currencyCode) {
        NumberFormat fmt = null;
        if (currencyCode != null) {
            fmt = NumberFormat.getCurrencyInstance();
            fmt.setCurrency(Currency.getInstance(currencyCode));
        } else {
            fmt = NumberFormat.getCurrencyInstance();
        }
        return fmt;
    }

    @SuppressWarnings("rawtypes")
    public static String getCurrencyCode(final String currencyField, BeanItem beanItem) {
        String value = (String) beanItem.getItemProperty(currencyField).getValue();
        return value;
    }

}
