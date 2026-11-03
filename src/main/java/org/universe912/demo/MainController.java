package org.universe912.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public final class MainController {

    @FXML
    private Label statusLabel;

    @FXML
    private void handleStart() {
        statusLabel.setText("JavaFX 事件处理正常，可以从棋盘模型开始开发。");
    }
}
