open module Shared {
    requires javafx.controls;
    requires annotations;
//    opens Project.Models;
    exports Project.Models to Server, Client;
    exports Project.Utils;
    exports Project.Models.Cities;
    exports Project.Enums;
    exports Project.Models.Tiles;
    exports Project.Models.Terrains;
    exports Project.Models.Buildings;
    exports Project.Models.Units to Server, Client;
    exports Project.Models.Notifications to Server, Client;
//    opens Project.Models.Notifications;
//    opens Project.Utils to com.google.gson;
}