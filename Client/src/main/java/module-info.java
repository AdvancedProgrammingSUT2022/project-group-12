module Client {
    requires Shared;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.gson;
    opens Client to javafx.controls,javafx.fxml,javafx.graphics;
    opens Client.Views to javafx.controls,javafx.fxml,javafx.graphics;
    exports Client.Views to Shared;
}