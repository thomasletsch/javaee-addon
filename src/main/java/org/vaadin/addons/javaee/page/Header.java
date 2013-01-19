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
package org.vaadin.addons.javaee.page;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class Header extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    private Label title;

    public Header() {
        setWidth(Portal.HEADER_WIDTH, Unit.PIXELS);
        setHeight(Portal.HEADER_HEIGHT, Unit.PIXELS);
        title = new Label();
        addComponent(title);
    }

    public void setTitle(String newTitle) {
        title.setValue(newTitle);
    }
}
