module quiz {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens Quiz.ClientSide;
    opens Quiz.ServerSide;
}