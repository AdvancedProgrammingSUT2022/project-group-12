package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.Cities.City;
import Project.Models.Units.Unit;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CitiesPanelView implements ViewController {
    public VBox availableCities;
    public Text selectedCity;
    private ArrayList<City> cities;
    private City selected;

    public void initialize() {
        cities = DatabaseQuerier.getCurrentCivCities();
        initBox();
        selected = null;
    }

    private void initBox() {
        for (City city : cities) {
            Text text = new Text(city.getName());
            text.setOnMouseEntered(mouseEvent -> text.setCursor(Cursor.HAND));
            text.setOnMouseClicked(mouseEvent -> {
                selectedCity.setText(city.getName());
                selected = city;
            });
            availableCities.getChildren().add(text);
        }
    }

    public void gotoCityPage(ActionEvent event) {
        if (selected == null)
            return;
        MenuStack.getInstance().getCookies().setSelectedCity(selected);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("CityPanelPage"));
    }
}
