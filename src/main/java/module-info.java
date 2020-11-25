module Quiz {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens Quiz.ClientSide;
    opens Quiz.ServerSide;
    opens Quiz.ClientSide.controllers;
}