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
package org.vaadin.addons.javaee.fields;

import java.text.NumberFormat;


public class CurrencyItemValueFormatter {

    private final String currency;

    public CurrencyItemValueFormatter(String currency) {
        this.currency = currency;
    }

    public String formatValue(Object value) {
        NumberFormat fmt = NumberFormatFactory.getNumberFormat(currency);
        return format(value, fmt);
    }

    public String format(Object value, NumberFormat fmt) {
        if (value instanceof String) {
            return (String) value;
        }
        Number strValue = (Number) value;
        String newValue = null;
        if (value == null) {
            newValue = fmt.format(0);
        } else {
            newValue = fmt.format(strValue);
        }
        return newValue;
    }
}
