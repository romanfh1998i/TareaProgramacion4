package edu.pucmm.grails;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import edu.pucmm.grails.domain.Authentication;
import edu.pucmm.grails.utils.KoffeeUI;
import edu.pucmm.grails.utils.RegisterUI;

/**
 * Created by aluis on 5/27/18.
 */
@Push
@Theme("DemoGrails")
@Widgetset("AppWidgetSet")
@SpringUI(path = "/")
public class AppUI extends UI {

    // Dato importante con los themes, se generan unos .css y unos css.gz que deben ser borrados para ver cambios refrescados.
    private VerticalLayout mainLayout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request) {
        new Navigator(this, this);
//        mainLayout.addComponent(btnEdit);
//        mainLayout.addComponent(btnDelete);
//        mainLayout.addComponent(btnAdd);
//        mainLayout.addComponent(grid);
        setContent(mainLayout);

        getNavigator().addView(LoginPage.NAME, LoginPage.class);
        getNavigator().setErrorView(LoginPage.class);
        Page.getCurrent().addUriFragmentChangedListener(new Page.UriFragmentChangedListener() {
            @Override
            public void uriFragmentChanged(Page.UriFragmentChangedEvent event) {
                router(event.getUriFragment());
            }
        });
        router("");
    }

    private void router(String route) {
        Notification.show(route);
        if (getSession().getAttribute("user") != null) {
            getNavigator().addView(SecurePage.NAME, SecurePage.class);
            getNavigator().addView(OtherSecurePage.NAME, OtherSecurePage.class);
            getNavigator().addView(KoffeeUI.NAME, KoffeeUI.class);
            if (route.equals("!OtherSecure")) {
                getNavigator().navigateTo(OtherSecurePage.NAME);
            } else if (route.equals("!Koffee")) {
                getNavigator().navigateTo(KoffeeUI.NAME);
            } else {
                getNavigator().navigateTo(SecurePage.NAME);
            }
        } else {
            getNavigator().addView(RegisterUI.NAME, RegisterUI.class);
            getNavigator().navigateTo(LoginPage.NAME);
            if(route.equals("!Register")){
                getNavigator().navigateTo(RegisterUI.NAME);
            }
        }
    }
}
