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
package org.vaadin.addons.javaee.buttons;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.Button;

public class BasicButton extends Button {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    protected TranslationService translationService;

    @Inject
    private javax.enterprise.event.Event<ButtonClickedEvent> buttonClicked;

    private String titleKey;

    /**
     * Constructor to be used without CDI
     */
    public BasicButton(String titleKey, String title) {
        super();
        setCaption(title);
        setDebugId(titleKey);
    }

    /**
     * Constructor to be used with CDI
     */
    public BasicButton(String titleKey) {
        super();
        this.titleKey = titleKey;
        setDebugId(titleKey);
    }

    @PostConstruct
    protected void finishButton() {
        setCaption(translationService.get(titleKey));
    }

    public void fireEvent() {
        ButtonClickedEvent event = createEvent();
        buttonClicked.fire(event);
    }

    protected ButtonClickedEvent createEvent() {
        return new ButtonClickedEvent();
    }

}
