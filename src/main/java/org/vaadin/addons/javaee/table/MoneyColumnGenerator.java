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
package org.vaadin.addons.javaee.table;

import java.text.NumberFormat;

import org.vaadin.addons.javaee.fields.converter.NumberFormatFactory;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class MoneyColumnGenerator implements Table.ColumnGenerator {

    private static final long serialVersionUID = 1L;

    private String currencyId;

    public MoneyColumnGenerator(String currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object generateCell(Table source, Object itemId, Object columnId) {
        Item item = source.getItem(itemId);
        Property<Number> valueProp = item.getItemProperty(columnId);
        Property<String> currencyProp = item.getItemProperty(currencyId);
        NumberFormat format = NumberFormatFactory.getNumberFormat(currencyProp.getValue());
        Number value = valueProp.getValue();
        String formatted = null;
        if (value == null) {
            formatted = format.format(0);
        } else {
            formatted = format.format(value);
        }
        return formatted;
    }
}
