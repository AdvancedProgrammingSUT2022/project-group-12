package Project.Client;

import Project.Enums.BuildingEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class asgartest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        TabPane tabPane = new TabPane();
//        Tab tab = new Tab("tab 1");
//        Tab tab1 = new Tab("tab 2");
//        Button button = new Button("button");
//        button.setOnAction(actionEvent -> {
//            tabPane.getSelectionModel().select(tab);
//        });
//        tab1.setContent(button);
//        AnchorPane anchorPane = new AnchorPane(); anchorPane.setPrefWidth(200); anchorPane.setPrefHeight(200);
//        tab.setContent(anchorPane);
//        tabPane.getTabs().add(tab);
//        tabPane.getTabs().add(tab1);
//        tabPane.getSelectionModel().select(tab);
        Image image = new Image(App.class.getResource("/images/avatars/Alexander.png").toExternalForm());
        Gson gson = new Gson();
        BuildingEnum buildingEnum = BuildingEnum.ARMORY;
        String json = gson.toJson(buildingEnum);
        System.out.println("json = " + json);
        BuildingEnum image1 = gson.fromJson(json, (Type) BuildingEnum.class);
        BorderPane parent = new BorderPane();
        parent.getChildren().add(new ImageView(image1.getImage()));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
//        generic<Integer,String> generic = new generic<>(4,"ali");
//        // creating a list of Strings
//        List<String> words = Arrays.asList("GFG", "Geeks", "for",
//                "GeeksQuiz", "GeeksforGeeks");
//
//        // The lambda expression passed to
//        // reduce() method takes two Strings
//        // and returns the longer String.
//        // The result of the reduce() method is
//        // an Optional because the list on which
//        // reduce() is called may be empty.
//        Optional<String> longestString = words.stream()
//                .reduce((word1, word2)
//                        -> word1.length() > word2.length()
//                        ? word1 : word2);
//
//        // Displaying the longest String
//        longestString.ifPresent(System.out::println);
    launch();


    }
}
class  generic <T,V>{
 T ti;
 V v;
 T that;

    public generic(T ti, V v) {
        this.ti = ti;
        this.v = v;
        this.that = ti;
        System.out.println(that);
    }
    public T toStrings() {
        return that;
    }

    public void setTi(T ti) {
        this.ti = ti;
    }
}

