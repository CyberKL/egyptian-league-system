package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerInfoController {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private GridPane infoGrid;

    @FXML
    private Button backBtn;


    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }
    public void blueBack(MouseEvent event){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }
    public void switchManagerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("managersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    public void managerInfo(Manager manager){
        Label nameLabel = new Label(manager.getName());
        Label idLabel = new Label(Integer.toString(manager.getManagerId()));
        Label ageLabel = new Label(Integer.toString(manager.getAge()));
        Label nationalityLabel = new Label(manager.getNationality());
        Label teamLabel = new Label(manager.getTeam().getName());
        Label numOfTrophies = new Label(Integer.toString(manager.getTrophies()));
        String formerPlayer;
        if (manager.isWasPlayer()){
            formerPlayer = "Yes";
        }else {
            formerPlayer = "No";
        }
        Label formerPlayerLabel = new Label(formerPlayer);
        Label yellowCardLabel = new Label(Integer.toString(manager.getYellowCards()));
        Label redCardLabel = new Label(Integer.toString(manager.getRedCards()));
        VBox labelsBox = new VBox(nameLabel, idLabel, ageLabel, nationalityLabel, teamLabel, numOfTrophies, formerPlayerLabel, yellowCardLabel, redCardLabel);
        infoGrid.add(labelsBox, 1, 0);
    }

}
