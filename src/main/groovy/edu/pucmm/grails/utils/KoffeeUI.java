package edu.pucmm.grails.utils;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.Query;
import com.vaadin.navigator.View;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import demograils.KoffeeService;
import edu.pucmm.grails.NewKoffee;
import edu.pucmm.grails.domain.Koffee;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class KoffeeUI  extends VerticalLayout implements View {
    public static final String NAME = "Koffee";
    private VerticalLayout mainLayout = new VerticalLayout();

    public KoffeeUI(){

        Grid<Koffee> grid = new Grid<>();

        DataProvider<Koffee, Object> provider = new DataProvider<Koffee, Object>() {
            @Override
            public boolean isInMemory() {
                return false;
            }

            @Override
            public int size(Query<Koffee, Object> query) {
                return Grails.get(KoffeeService.class).countKoffees();
            }

            @Override
            public Stream<Koffee> fetch(Query<Koffee, Object> query) {
                return Grails.get(KoffeeService.class).allKoffees().stream();
            }

            @Override
            public void refreshItem(Koffee item) {

            }

            @Override
            public void refreshAll() {

            }

            @Override
            public Registration addDataProviderListener(DataProviderListener<Koffee> listener) {
                return null;
            }
        };
        // Para ajustar Los themes de la grid solo visitar https://vaadin.com/docs/v8/framework/components/components-grid.html
        // Al final le dice los estilos
        grid.setDataProvider(provider);
        grid.addStyleName(ValoTheme.TABLE_COMPACT); // Poniendo theme compacto a la tabla o grid en vaadin.
        grid.addStyleName("myGrid"); // Personalizado de la Grid

        grid.addColumn(Koffee::getName).setCaption("Name");
        grid.addColumn(Koffee::getDescription).setCaption("Description");
        grid.addColumn(Koffee::getPrice).setCaption("Price");

        Button btnAdd = new Button("Add");
        btnAdd.addStyleName(ValoTheme.BUTTON_PRIMARY); // OTRO estilo de vaadin.
        btnAdd.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                NewKoffee koffee = new NewKoffee();
                koffee.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent e) {
                        grid.setDataProvider(provider);
                    }
                });
                getUI().addWindow(koffee);
            }
        });

        Button btnDelete = new Button("Delete");
        btnDelete.addStyleName(ValoTheme.BUTTON_DANGER); // Adiciono un estilo propio de vaadin.
        btnDelete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Set<Koffee> selectedItems = grid.getSelectedItems();
                selectedItems.forEach(new Consumer<Koffee>() {
                    @Override
                    public void accept(Koffee koffee) {
                        Grails.get(KoffeeService.class).remove(koffee);
                    }
                });
                grid.setDataProvider(provider);
            }
        });


        Button btnEdit = new Button("Edit");
        btnEdit.addStyleName("buttonEdit"); // Estilo propio creando en el theme
        btnEdit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                List<Koffee> selectedItems = new ArrayList<>(grid.getSelectedItems());
                if (selectedItems.size() == 1) {
                    NewKoffee koffee = new NewKoffee(selectedItems.get(0));
                    koffee.addCloseListener(new Window.CloseListener() {
                        @Override
                        public void windowClose(Window.CloseEvent e) {
                            grid.setDataProvider(provider);
                        }
                    });
                    getUI().addWindow(koffee);
                }
            }
        });
        addComponent(btnEdit);
        addComponent(btnDelete);
        addComponent(btnAdd);
        addComponent(grid);

    }
}

