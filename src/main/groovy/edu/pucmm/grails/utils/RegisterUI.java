package edu.pucmm.grails.utils;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import demograils.AuthenticationService;
import edu.pucmm.grails.LoginPage;

public class RegisterUI extends VerticalLayout implements View {
    public static final String NAME = "Register";

    public RegisterUI(){
        Panel panel = new Panel("Register");
        panel.setSizeUndefined();
        addComponent(panel);
        FormLayout content = new FormLayout();
        TextField name = new TextField("Name");
        TextField username = new TextField("Username");
        content.addComponent(name);
        content.addComponent(username);
        PasswordField password = new PasswordField("Password");
        content.addComponent(password);
        Button send = new Button("Enter");

        send.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!((username.getValue().length()<2) &&
                      (password.getValue().length()<2) &&
                      (name.getValue().length()<2))) {
                    Grails.get(AuthenticationService.class).registerUser(username.getValue(), name.getValue(), password.getValue());
                    getUI().getNavigator().addView(LoginPage.NAME, LoginPage.class);
                    Page.getCurrent().setUriFragment("!" + LoginPage.NAME);
                } else {
                    Notification.show("The fields should have at least 2 characters!!", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        content.addComponent(send);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
    }

}
