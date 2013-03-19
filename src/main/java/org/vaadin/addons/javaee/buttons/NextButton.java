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
package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.buttons.clickhandler.NextClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNextButton;
import org.vaadin.addons.javaee.i18n.TranslationKeys;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;

public class NextButton extends BasicButton {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public NextButton(CanHandleNextButton canHandle, String title) {
        super(TranslationKeys.BUTTON_NEXT, title);
        addClickListener(new NextClickHandler(this, canHandle));
        setIcon(new ThemeResource("icons/silk/resultset_next.png"));
        setClickShortcut(KeyCode.ENTER);
        addStyleName("primary");
    }

}
