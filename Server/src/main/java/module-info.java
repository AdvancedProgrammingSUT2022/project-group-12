module Server {
    requires Shared;
    requires Client;
    opens Server.Models to Shared;
    opens Server.Controllers to Shared;
    opens Server.Views to Shared;
    opens Server.Utils to Shared;
    opens Server.Controllers.ValidateGameMenuFuncs to Shared ;
    exports Server.Controllers to Shared;
    exports Server.Models to  Shared;
    exports Server.Views to Shared;
    exports Server.Utils to Shared;
}