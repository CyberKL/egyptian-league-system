package edu.ainshams.egyptianleaguesystem.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MenuController {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    private Stage stage;

    @FXML
    private Button backBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private Button teamBtn;
    @FXML
    private Button matchBtn;
    @FXML
    private Button playerBtn;
    @FXML
    private Button mngrBtn;
    @FXML
    private Button refBtn;
    @FXML
    private Button stadBtn;
    @FXML
    private Button statsBtn;

    public void redQuit(MouseEvent event){
        quitBtn.setStyle("-fx-background-color: red;");
    }
    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }

    public void hoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3);");
    }

    public void blueBack(MouseEvent event){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }

    public void switchTeamMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamsMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchMatchMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("matchesMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    public void switchPlayerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playersMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchStandingsMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("standings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchManagerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("managersMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchRefereeMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("refereesMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchStadiumMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stadiumsMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchStatsMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stats.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

}
