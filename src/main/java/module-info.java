module {pkg}{
        requires javafx.controls;
        requires javafx.graphics;
        requires javafx.fxml;

        exports{pkg of Application class};

        opens{pkg};
        }