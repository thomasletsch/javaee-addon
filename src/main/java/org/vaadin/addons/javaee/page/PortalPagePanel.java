package org.vaadin.addons.javaee.page;

import javax.enterprise.context.Dependent;

import com.vaadin.ui.VerticalLayout;

@Dependent
public abstract class PortalPagePanel extends VerticalLayout {

    private String pageName;

    protected PortalPagePanel() {
    }

    public PortalPagePanel(String pageName) {
        setDebugId(pageName + "Panel");
        this.pageName = pageName;
        setMargin(true);
        setSpacing(true);
        setSizeFull();
    }

    public void onShow(String comingFrom) {
    }

    public String getPageName() {
        return pageName;
    }

    public void prepareShow() {
    }

}
