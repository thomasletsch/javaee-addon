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
package org.vaadin.addons.javaee.portal;

import java.util.Map;

import org.vaadin.addons.javaee.page.AbstractContentView;

public interface PortalView {

    public static final int HEIGHT = 850;

    public static final int WIDTH = 1000;

    public static final int DEFAULT_MARGIN = 5;

    public static final int SMALL_MARGIN = 2;

    public static final int DEFAULT_BORDER_WIDTH = 1;

    public static final int HEADER_HEIGHT = 30;

    public static final int MENU_WIDTH = 185;

    void navigateTo(AbstractContentView portalPagePanel, Map<String, Object> parameters);

}
