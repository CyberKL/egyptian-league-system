package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerInfoController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private GridPane infoGrid;
    @FXML
    private VBox labelsBox;
    @FXML
    private VBox infoBox;
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
                switchPlayersMenu((ActionEvent) event);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        });
        subMenuRoot.getChildren().add(back);
        AnchorPane.setRightAnchor(back, 0.5);
        AnchorPane.setTopAnchor(back, 25.0);
    }
    public void switchPlayersMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    public void playerInfo(Player player){
        Label nameLabel = new Label(player.getName());
        nameLabel.getStyleClass().add("info-label");

        Label idLabel = new Label(Integer.toString(player.getPlayerId()));
        idLabel.getStyleClass().add("info-label");

        Label ageLabel = new Label(Integer.toString(player.getAge()));
        ageLabel.getStyleClass().add("info-label");

        Label nationalityLabel = new Label(player.getNationality());
        nationalityLabel.getStyleClass().add("info-label");

        Label teamLabel;
        if (player.getTeam() != null) {
            teamLabel = new Label(player.getTeam().getName());
        } else {
            teamLabel = new Label("N/A");
        }
        teamLabel.getStyleClass().add("info-label");

        Label numLabel = new Label(Integer.toString(player.getNumber()));
        numLabel.getStyleClass().add("info-label");

        Label positionLabel = new Label(player.getPosition());
        positionLabel.getStyleClass().add("info-label");

        Label heightLabel = new Label(Integer.toString(player.getHeight()));
        heightLabel.getStyleClass().add("info-label");

        Label weightLabel = new Label(Integer.toString(player.getWeight()));
        weightLabel.getStyleClass().add("info-label");

        Label preferredFootLabel = new Label(player.getPreferredFoot());
        preferredFootLabel.getStyleClass().add("info-label");

        Label yellowCardLabel = new Label(Integer.toString(player.getYellowCards()));
        yellowCardLabel.getStyleClass().add("info-label");

        Label redCardLabel = new Label(Integer.toString(player.getRedCards()));
        redCardLabel.getStyleClass().add("info-label");
        infoBox.getChildren().addAll(nameLabel, idLabel, ageLabel, nationalityLabel, teamLabel, numLabel, positionLabel, heightLabel, weightLabel, preferredFootLabel, yellowCardLabel, redCardLabel);
        Label goalsScoredLabel = new Label("Goals Scored:");
        goalsScoredLabel.getStyleClass().add("info-label");

        Label assistsLabel = new Label("Assists:");
        assistsLabel.getStyleClass().add("info-label");

        Label shotsOnTargetLabel = new Label("Shots on Target:");
        shotsOnTargetLabel.getStyleClass().add("info-label");

        Label conversionRateLabel = new Label("Conversion Rate:");
        conversionRateLabel.getStyleClass().add("info-label");

        Label xGLabel = new Label("Expected Goals:");
        xGLabel.getStyleClass().add("info-label");

        Label interceptionsLabel = new Label("Interceptions:");
        interceptionsLabel.getStyleClass().add("info-label");

        Label keyPassesLabel = new Label("Key Passes:");
        keyPassesLabel.getStyleClass().add("info-label");

        Label tackelsWonLabel = new Label("Tackles Won:");
        tackelsWonLabel.getStyleClass().add("info-label");

        Label cleanSheetsLabel = new Label("Clean Sheets:");
        cleanSheetsLabel.getStyleClass().add("info-label");

        Label savesLabel = new Label("Saves:");
        savesLabel.getStyleClass().add("info-label");
        switch (player.getPosition()){
            case "Forward": {
                Forward forward = (Forward) player;
                labelsBox.getChildren().addAll(goalsScoredLabel, assistsLabel, shotsOnTargetLabel, conversionRateLabel, xGLabel);
                Label goalsScored = new Label(String.valueOf(forward.getGoalsScored().orElse(0)));
                goalsScored.getStyleClass().add("info-label");

                Label assists = new Label(String.valueOf(forward.getAssists().orElse(0)));
                assists.getStyleClass().add("info-label");

                Label shotsOnTarget = new Label(Integer.toString(forward.getShotsOnTarget()));
                shotsOnTarget.getStyleClass().add("info-label");

                Label conversionRate = new Label(Double.toString(forward.getConversionRate()));
                conversionRate.getStyleClass().add("info-label");

                Label xG = new Label(Double.toString(forward.getExpectedGoals()));
                xG.getStyleClass().add("info-label");
                infoBox.getChildren().addAll(goalsScored, assists, shotsOnTarget, conversionRate, xG);
                break;
            }
            case "Midfielder": {
                Midfielder midfielder = (Midfielder) player;
                labelsBox.getChildren().addAll(goalsScoredLabel, assistsLabel, keyPassesLabel, interceptionsLabel);
                Label goalsScored = new Label(String.valueOf(midfielder.getGoalsScored().orElse(0)));
                goalsScored.getStyleClass().add("info-label");

                Label assists = new Label(String.valueOf(midfielder.getAssists().orElse(0)));
                assists.getStyleClass().add("info-label");

                Label keyPasses = new Label(Integer.toString(midfielder.getKeyPasses()));
                keyPasses.getStyleClass().add("info-label");

                Label interceptions = new Label(Integer.toString(midfielder.getInterceptions()));
                interceptions.getStyleClass().add("info-label");

                infoBox.getChildren().addAll(goalsScored, assists, keyPasses, interceptions);
                break;
            }
            case "Defender":{
                Defender defender = (Defender) player;
                labelsBox.getChildren().addAll(goalsScoredLabel, assistsLabel, tackelsWonLabel, cleanSheetsLabel);
                Label goalsScored = new Label(String.valueOf(defender.getGoalsScored().orElse(0)));
                goalsScored.getStyleClass().add("info-label");

                Label assists = new Label(String.valueOf(defender.getAssists().orElse(0)));
                assists.getStyleClass().add("info-label");

                Label tacklesWon = new Label(Integer.toString(defender.getTacklesWon()));
                tacklesWon.getStyleClass().add("info-label");

                Label cleanSheets = new Label(Integer.toString(defender.getCleanSheets()));
                cleanSheets.getStyleClass().add("info-label");
                infoBox.getChildren().addAll(goalsScored, assists, tacklesWon, cleanSheets);
                break;
            }
            case "Goalkeeper":{
                Goalkeeper goalkeeper = (Goalkeeper) player;
                labelsBox.getChildren().addAll(cleanSheetsLabel, savesLabel);
                Label cleanSheets = new Label(Integer.toString(goalkeeper.getCleanSheets()));
                cleanSheets.getStyleClass().add("info-label");

                Label saves = new Label(Integer.toString(goalkeeper.getSaves()));
                saves.getStyleClass().add("info-label");
                infoBox.getChildren().addAll(cleanSheets, saves);
                break;
            }
        }
    }
}
