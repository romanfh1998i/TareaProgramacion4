package edu.pucmm.grails;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class OtherSecurePage extends VerticalLayout implements View {
    private static final long serialVersionUID = 1L;
    private Label otherSecure;
    public static final String NAME = "OtherSecure";
    private Button mainsecure;
    public OtherSecurePage() {
        mainsecure = new Button("Main Secure Area");
        mainsecure.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Page.getCurrent().setUriFragment("!"+SecurePage.NAME);
            }
        });
        otherSecure = new Label("Other Secure Page ...");
        addComponent(otherSecure);
        addComponent(mainsecure);
    }
    @Override

    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

}
