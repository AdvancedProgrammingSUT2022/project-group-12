module project {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires com.google.gson;
    requires annotations;

    opens Models to com.google.gson;
    exports Models;

    opens Applications to javafx.fxml;
    exports Applications;
}