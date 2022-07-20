open module Shared {
    requires javafx.controls;
    requires annotations;
    requires com.google.gson;
    exports Project.Models to Server, Client;
    exports Project.Utils;
    exports Project.Models.Cities;
    exports Project.Enums;
    exports Project.Models.Tiles;
    exports Project.Models.Terrains;
    exports Project.Models.Buildings;
    exports Project.Models.Units to Server, Client;
    exports Project.Models.Notifications to Server, Client;
}