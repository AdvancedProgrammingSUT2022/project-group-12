module project {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires com.google.gson;
    requires annotations;

    opens project.Models to com.google.gson;
    exports project.Models;
    opens project.Views to javafx.fxml;
    exports project.Views;
    opens project to javafx.fxml;
    exports project;

}