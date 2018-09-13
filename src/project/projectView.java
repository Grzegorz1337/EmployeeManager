package project;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class projectView{
    public projectView(String project)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            ResourceBundle a = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    return project;
                }
                @Override
                public Enumeration<String> getKeys() {
                    return null;
                }
            };
            fxmlLoader.setResources(a);
            fxmlLoader.setLocation(getClass().getResource("projectEdit.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 400);
            Stage stage = new Stage();
            stage.setTitle(project);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}