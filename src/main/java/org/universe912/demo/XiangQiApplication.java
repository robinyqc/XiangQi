package org.universe912.demo;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class XiangQiApplication extends Application {

    private static final double DEFAULT_WIDTH = 960;
    private static final double DEFAULT_HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {
        URL viewUrl = Objects.requireNonNull(
                XiangQiApplication.class.getResource("main-view.fxml"),
                "Missing main-view.fxml"
        );

        FXMLLoader loader = new FXMLLoader(viewUrl);
        Scene scene = new Scene(loader.load(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        URL stylesheet = Objects.requireNonNull(
                XiangQiApplication.class.getResource("styles.css"),
                "Missing styles.css"
        );
        scene.getStylesheets().add(stylesheet.toExternalForm());

        stage.setTitle("中国象棋 · XiangQi");
        stage.setMinWidth(720);
        stage.setMinHeight(560);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
