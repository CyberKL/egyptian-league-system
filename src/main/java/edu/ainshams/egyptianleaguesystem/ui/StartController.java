package edu.ainshams.egyptianleaguesystem.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    //get screen size of monitor
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    private Stage stage;

    @FXML
    private ImageView leagueLogo;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Fade transition
        FadeTransition fade = new FadeTransition();
        fade.setNode(leagueLogo);
        fade.setDuration(Duration.millis(1500));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        //Scale transition
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(leagueLogo);
        scale.setDuration(Duration.millis(2000));
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(3.0);
        scale.setByY(3.0);
        scale.setDelay(Duration.millis(500));
        scale.play();

        //Switching
        scale.setOnFinished(event -> {
            try {
                switchToStartMenu(event);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });


    }

    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
    }

}
