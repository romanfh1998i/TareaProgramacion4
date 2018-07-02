package edu.pucmm.grails;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import demograils.AuthenticationService;
import edu.pucmm.grails.utils.Grails;
import edu.pucmm.grails.utils.KoffeeUI;
import edu.pucmm.grails.utils.RegisterUI;

public class LoginPage extends VerticalLayout implements View {

    private static final Long serialVersionUID = 1L;
    public static final String NAME = "";

    public LoginPage() {
        Panel panel = new Panel("Login");
        panel.setSizeUndefined();
        addComponent(panel);
        FormLayout content = new FormLayout();
        TextField username = new TextField("Username");
        content.addComponent(username);
        PasswordField password = new PasswordField("Password");
        content.addComponent(password);
        Button send = new Button("Enter");
        Button register = new Button("Register");

        register.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Page.getCurrent().setUriFragment("!"+ RegisterUI.NAME);
            }
        });

        send.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (Grails.get(AuthenticationService.class).autenticate(username.getValue(), password.getValue())) {
                    VaadinSession.getCurrent().setAttribute("user", username.getValue());
                    getUI().getNavigator().addView(SecurePage.NAME, SecurePage.class);
                    getUI().getNavigator().addView(KoffeeUI.NAME, KoffeeUI.class);
                    Page.getCurrent().setUriFragment("!" + KoffeeUI.NAME);

                } else {
                    Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
                }
            }
        });


        content.addComponent(send);
        content.addComponent(register);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

    }


}
