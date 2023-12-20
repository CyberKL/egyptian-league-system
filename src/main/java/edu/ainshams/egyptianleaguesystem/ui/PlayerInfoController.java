package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Forward;
import edu.ainshams.egyptianleaguesystem.model.Goalkeeper;
import edu.ainshams.egyptianleaguesystem.model.Midfielder;
import edu.ainshams.egyptianleaguesystem.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerInfoController {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private Button backBtn;
    @FXML
    private GridPane infoGrid;
    @FXML
    private VBox labelsBox;
    @FXML
    private VBox infoBox;

    public void switchPlayersMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }
    public void blueBack(MouseEvent event){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }

    public void playerInfo(Player player){
        Label nameLabel = new Label(player.getName());
        Label idLabel = new Label(Integer.toString(player.getPlayerId()));
        Label ageLabel = new Label(Integer.toString(player.getAge()));
        Label nationalityLabel = new Label(player.getNationality());
        Label teamLabel = new Label(player.getTeam().getName());
        Label numLabel = new Label(Integer.toString(player.getNumber()));
        Label positionLabel = new Label(player.getPosition());
        Label heightLabel = new Label(Integer.toString(player.getHeight()));
        Label weightLabel = new Label(Integer.toString(player.getWeight()));
        Label preferredFootLabel = new Label(player.getPreferredFoot());
        Label yellowCardLabel = new Label(Integer.toString(player.getYellowCards()));
        Label redCardLabel = new Label(Integer.toString(player.getRedCards()));
        VBox vBox = new VBox(nameLabel, idLabel, ageLabel, nationalityLabel, teamLabel, numLabel, positionLabel, heightLabel, weightLabel, preferredFootLabel, yellowCardLabel, redCardLabel);
        infoGrid.add(vBox, 1, 0);
        Label goalsScoredLabel = new Label("Goals Scored:");
        Label assistsLabel = new Label("Assists:");
        Label shotsOnTargetLabel = new Label("Shots on Target:");
        Label conversionRateLabel = new Label("Conversion Rate:");
        Label xGLabel = new Label("Expected Goals");
        Label interceptionsLabel = new Label("Interceptions:");
        Label keyPassesLabel = new Label("Key Passes:");
        Label tackelsWonLabel = new Label("Tackles Won:");
        Label cleanSheetsLabel = new Label("Clean Sheets:");
        Label savesLabel = new Label("Saves:");
        switch (player.getPosition()){
            case "Forward": {
                Forward forward = (Forward) player;
                labelsBox.getChildren().addAll(goalsScoredLabel, assistsLabel, shotsOnTargetLabel, conversionRateLabel, xGLabel);
                Label goalsScored = new Label(forward.getGoalsScored().toString());
                Label assists = new Label(String.valueOf(forward.getAssists()));
                Label shotsOnTarget = new Label(Integer.toString(forward.getShotsOnTarget()));
                Label conversionRate = new Label(Double.toString(forward.getConversionRate()));
                Label xG = new Label(Double.toString(forward.getExpectedGoals()));
                infoBox.getChildren().addAll(goalsScored, assists, shotsOnTarget, conversionRate, xG);
                break;
            }
            case "Midfielder": {
                Midfielder midfielder = (Midfielder) player;
                labelsBox.getChildren().addAll(goalsScoredLabel, assistsLabel, keyPassesLabel, interceptionsLabel);
                Label goalsScored = new Label(midfielder.getGoalsScored().toString());
                Label assists = new Label(String.valueOf(midfielder.getAssists()));
                Label keyPasses = new Label(Integer.toString(midfielder.getKeyPasses()));
                Label interceptions = new Label(Integer.toString(midfielder.getInterceptions()));
                infoBox.getChildren().addAll(goalsScored, assists, keyPasses, interceptions);
                break;
            }
            case "Defender":{
                //to be implemented
                break;
            }
            case "Goalkeeper":{
                Goalkeeper goalkeeper = (Goalkeeper) player;
                labelsBox.getChildren().addAll(cleanSheetsLabel, savesLabel);
                Label cleanSheets = new Label(Integer.toString(goalkeeper.getCleanSheets()));
                Label saves = new Label(Integer.toString(goalkeeper.getSaves()));
                infoBox.getChildren().addAll(cleanSheets, saves);
                break;
            }
        }
    }
}
