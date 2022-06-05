module project {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires com.google.gson;
    requires annotations;

    opens Project to javafx.fxml;
    exports Project;
    exports Project.Views;
    opens Project.Views to javafx.fxml;
    exports Project.Models;
    opens Project.Models;
}