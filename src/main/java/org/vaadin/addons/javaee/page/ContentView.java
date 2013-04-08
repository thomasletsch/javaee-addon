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
package org.vaadin.addons.javaee.page;

import java.util.Map;

import com.vaadin.navigator.View;

/**
 * Main interface for content panel (central window part)
 * 
 * @author thomas
 * 
 */
public interface ContentView extends View {

    /**
     * Called every time the content view is accessed by menu (or other navigation)
     */
    void onShow(String comingFrom, Map<String, Object> parameters);

    /**
     * Returns a unique name for the page
     */
    String getPageName();

    /**
     * Should return true is there are unsaved values on the page. Should check all forms for unsaved values.
     * 
     * Used for displaying the user a dialog if he really wants to leave and loose unsaved values.
     */
    boolean containsUnsavedValues();
}
