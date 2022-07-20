module Server {
    requires Shared;
    requires Client;
    requires com.google.gson;
    requires javafx.fxml;
    requires xstream;
    opens Server.Models to Shared, com.google.gson, xstream;
    opens Server.Controllers to Shared, javafx.fxml,javafx.graphics;
    opens Server.Views to Shared;
    opens Server.Utils to Shared;
    opens Server.Controllers.ValidateGameMenuFuncs to Shared ;
    exports Server.Controllers to Shared;
    exports Server.Models to  Shared;
    exports Server.Views to Shared;
    exports Server.Utils to Shared;
}