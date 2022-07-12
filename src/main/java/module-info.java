module project {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.gson;

//    opens Project to javafx.fxml;
    exports Project;
//    exports Project.Views;
    opens Project.Views to javafx.fxml;
//    exports Project.Models;
//    opens Project.Models;
//    opens Project.Enums;
//    exports Project.Enums;
//    exports Project.Utils;
//    opens Project.Utils to javafx.fxml;
}