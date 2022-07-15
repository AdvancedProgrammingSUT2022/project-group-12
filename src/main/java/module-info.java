module project {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.gson;
    requires xstream;

//    opens Project to javafx.fxml;
//    exports Project;
//    exports Project.Views;
    opens Project.Client.Views to javafx.fxml;
    opens Project.Models to com.google.gson;
    exports Project.Client;
    exports Project.Server;
    exports Project.Client.Utils;
//    exports Project.Models;
//    opens Project.Models;
//    opens Project.Enums;
//    exports Project.Enums;
//    exports Project.Utils;
//    opens Project.Utils to javafx.fxml;
}