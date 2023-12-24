package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.DuplicateException;
import edu.ainshams.egyptianleaguesystem.model.Logic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    public static void main(String[] args) throws DuplicateException {
        Logic.read();
        launch(args);
        //Logic.startCLI();
        Logic.write();
        System.out.println("exited");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //get screen size of monitor
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        FXMLLoader loader = new  FXMLLoader(getClass().getResource("start.fxml"));
        Parent root = loader.load();
        Scene start = new Scene(root, screenSize.getWidth(), screenSize.getHeight());

        Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
        StartController startController = loader.getController();
        startController.setStage(primaryStage);

        primaryStage.getIcons().add(icon);
        primaryStage.setScene(start);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
