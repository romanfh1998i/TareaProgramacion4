package edu.pucmm.grails;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.Query;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import demograils.KoffeeService;
import edu.pucmm.grails.domain.Authentication;
import edu.pucmm.grails.domain.Koffee;
import edu.pucmm.grails.utils.Grails;
import edu.pucmm.grails.utils.KoffeeUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

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

    public static Authentication AUTH;
    @Override
    protected void init(VaadinRequest request) {
        AUTH = new Authentication();
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
    private void router(String route){
        Notification.show(route);
        if(getSession().getAttribute("user") != null){
            getNavigator().addView(SecurePage.NAME, SecurePage.class);
            getNavigator().addView(OtherSecurePage.NAME, OtherSecurePage.class);
            getNavigator().addView(KoffeeUI.NAME, KoffeeUI.class);
            if(route.equals("!OtherSecure")){
                getNavigator().navigateTo(OtherSecurePage.NAME);
            }else if(route.equals("!Koffee")){
                getNavigator().navigateTo(KoffeeUI.NAME);
            }else{
                getNavigator().navigateTo(SecurePage.NAME);
            }
        }else{
            getNavigator().navigateTo(LoginPage.NAME);
        }
    }
}
