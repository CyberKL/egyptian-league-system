package edu.ainshams.egyptianleaguesystem.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    private ImageView leagueLogo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Fade transition
        FadeTransition fade = new FadeTransition();
        fade.setNode(leagueLogo);
        fade.setDuration(Duration.millis(2500));
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

    }

}
