package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Manager;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerInfoController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private GridPane infoGrid;
    @FXML
    private AnchorPane subMenuRoot;

    private MFXButton createButton(String icon, String text, EventHandler action) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
        MFXButton button = new MFXButton(text, wrapper);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(action);
        return button;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXButton back = createButton("fas-left-long", "Back", event -> {
            try {
                switchManagerMenu((ActionEvent) event);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        });
        subMenuRoot.getChildren().add(back);
        AnchorPane.setRightAnchor(back, 0.5);
        AnchorPane.setTopAnchor(back, 25.0);
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
        Label teamLabel;
        if (manager.getTeam() != null) {
            teamLabel = new Label(manager.getTeam().getName());
        }
        else {
            teamLabel = new Label("N/A");
        }
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
        nameLabel.getStyleClass().add("info-label");
        idLabel.getStyleClass().add("info-label");
        ageLabel.getStyleClass().add("info-label");
        nationalityLabel.getStyleClass().add("info-label");
        teamLabel.getStyleClass().add("info-label");
        numOfTrophies.getStyleClass().add("info-label");
        formerPlayerLabel.getStyleClass().add("info-label");
        yellowCardLabel.getStyleClass().add("info-label");
        redCardLabel.getStyleClass().add("info-label");
        VBox labelsBox = new VBox(nameLabel, idLabel, ageLabel, nationalityLabel, teamLabel, numOfTrophies, formerPlayerLabel, yellowCardLabel, redCardLabel);
        labelsBox.setAlignment(Pos.CENTER);
        infoGrid.add(labelsBox, 1, 0);
    }

}
