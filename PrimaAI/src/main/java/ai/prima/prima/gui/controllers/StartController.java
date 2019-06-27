package ai.prima.prima.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    private AnchorPane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent page = FXMLLoader.load(getClass().getClassLoader().getResource("scenes/startscene/Projects.fxml"));
            content.getChildren().add(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
