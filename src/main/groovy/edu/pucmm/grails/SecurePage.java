package edu.pucmm.grails;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import edu.pucmm.grails.utils.KoffeeUI;

public class SecurePage extends VerticalLayout implements View {
    private static final long serialVersionUID = 1L;
    private Label secure;
    private Label currentUser;
    private Button otherSecure;
    private Button logout;
    private Button koffee;
    public static final String NAME = "Secure";

    public SecurePage(){
        otherSecure = new Button("OtherSecure");
        otherSecure.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Page.getCurrent().setUriFragment("!"+OtherSecurePage.NAME);
            }
        });
        logout = new Button("Logout");
        koffee = new Button("Koffee");
        logout.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event)  {
                VaadinSession.getCurrent().getSession().invalidate();
                Page.getCurrent().setLocation("/");
            }
        });

        koffee.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Page.getCurrent().setUriFragment("!"+ KoffeeUI.NAME);
            }
        });
        secure = new Label("secure");
        currentUser = new Label("Current User");
        addComponent(secure);
        addComponent(koffee);
        addComponent(currentUser);
        addComponent(otherSecure);
        addComponent(logout);

    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        currentUser.setCaption("Current user : " + VaadinSession.getCurrent().getAttribute("user").toString());
    }
}
