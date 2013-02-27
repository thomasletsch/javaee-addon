package org.vaadin.addons.javaee.page;

import javax.inject.Inject;

import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.NextButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNextButton;
import org.vaadin.addons.javaee.events.NavigationEvent;
import org.vaadin.addons.javaee.i18n.TranslationKeys;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.event.ShortcutAction.KeyCode;

public abstract class BasicListAndEditForwardPage<ENTITY extends PersistentEntity> extends BasicListAndEditPage<ENTITY> implements
        CanHandleNextButton {

    private static final long serialVersionUID = 1L;

    protected NextButton nextButton;

    @Inject
    protected javax.enterprise.event.Event<NavigationEvent> navigation;

    public BasicListAndEditForwardPage(String pageName) {
        super(pageName);
    }

    @Override
    protected ButtonBar initButtons() {
        initializeNextButton();
        initializeEditButton();
        initializeNewButton();
        ButtonBar buttonLayout = new ButtonBar();
        buttonLayout.addComponent(nextButton);
        buttonLayout.addComponent(editButton);
        buttonLayout.addComponent(newButton);
        return buttonLayout;
    }

    protected void initializeNextButton() {
        nextButton = new NextButton(this, translationService.getText(TranslationKeys.BUTTON_NEXT));
        nextButton.setClickShortcut(KeyCode.ENTER);
    }

    @Override
    public void nextClicked() {
        showReadonly();
        getForm().save();
        if (getNextPage() != null) {
            navigateToNextPage();
        } else {
            getTable().refreshRowCache();
            getTable().selectFirst();
        }
    }

    /**
     * Should be overwritten to enable automatic navigation.
     */
    protected String getNextPage() {
        return null;
    }

    /**
     * Can be overwritten for e.g. adding parameters to navigation event
     */
    protected void navigateToNextPage() {
        navigation.fire(new NavigationEvent(getNextPage()));
    }

    @Override
    protected void showReadonly() {
        super.showReadonly();
        nextButton.setEnabled(false);
    }

    @Override
    protected void showReadWrite() {
        super.showReadWrite();
        nextButton.setEnabled(true);
    }

}
