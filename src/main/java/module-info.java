module org.universe912.demo {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.universe912.demo;
    opens org.universe912.demo to javafx.fxml;
}
