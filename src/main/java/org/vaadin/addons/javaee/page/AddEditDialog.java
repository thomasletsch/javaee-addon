package org.vaadin.addons.javaee.page;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.CancelButton;
import org.vaadin.addons.javaee.buttons.OkButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleCancelButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleOkButton;
import org.vaadin.addons.javaee.form.BasicEntityForm;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.vaadin.ui.Root;
import com.vaadin.ui.Window;

public class AddEditDialog extends Window implements CanHandleOkButton, CanHandleCancelButton {

    private TranslationService translationService;

    protected BasicEntityForm<?> form;

    private final EntityContainer<?> container;

    protected OkButton okButton;

    protected CancelButton cancelButton;

    public AddEditDialog(EntityContainer<?> container, BasicEntityForm<?> form, TranslationService translationService) {
        this.translationService = translationService;
        this.container = container;
        this.form = form;

        setDebugId(container.getEntityClass().getSimpleName() + "AddEditDialog");
        setCaption(translationService.get(container.getEntityClass().getSimpleName()));
        setWidth(300, Unit.PIXELS);

        addComponent(form);
        initButtons();
    }

    protected void initButtons() {
        ButtonBar buttons = new ButtonBar();
        OkButton okButton = new OkButton(this, translationService.get(TranslationKeys.BUTTON_OK));
        buttons.addComponent(okButton);
        CancelButton cancelButton = new CancelButton(this, translationService.get(TranslationKeys.BUTTON_CANCEL));
        buttons.addComponent(cancelButton);
        addComponent(buttons);
    }

    public void editSelected(BasicEntityTable<?> table) {
        form.edit(container.getItem(table.getValue()));
        Root.getCurrentRoot().addWindow(this);
        center();
    }

    public void editNew() {
        form.editNew();
        Root.getCurrentRoot().addWindow(this);
        center();
    }

    @Override
    public void cancelClicked() {
        Root.getCurrentRoot().removeWindow(this);
    }

    @Override
    public void okClicked() {
        form.save();
        Root.getCurrentRoot().removeWindow(this);
    }

}