module Shared {
    requires javafx.controls;
    requires Server;
    requires Client;
    opens Project.Models;
    exports Project.Models to Server, Client;
    exports Project.Utils;
    exports Project.Models.Cities;
    exports Project.Enums;
    exports Project.Models.Tiles;
    exports Project.Models.Buildings;
    exports Project.Models.Units;
    exports Project.Models.Notifications to Server, Client;
    opens Project.Models.Notifications;
}