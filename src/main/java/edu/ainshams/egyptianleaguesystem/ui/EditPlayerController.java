package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class EditPlayerController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private ToggleGroup choice;

    @FXML
    private Label currentInfo;

    @FXML
    private Label currentInfoLabel;

    @FXML
    private BorderPane editForm;

    @FXML
    private HBox footBox;

    @FXML
    private ToggleGroup footGroup;

    @FXML
    private MFXDatePicker newDate;

    @FXML
    private TextField newInfoField;

    @FXML
    private Label newInfoLabel;

    @FXML
    private Spinner<Integer> newNumSpinner;

    @FXML
    private MFXComboBox<String> newTeamChoice;

    @FXML
    private Label playerNameLabel;

    @FXML
    private VBox choicesPane;

    @FXML
    private AnchorPane subMenuRoot;
    @FXML
    private MFXRectangleToggleNode goalsScored;
    @FXML
    private MFXRectangleToggleNode assists;
    @FXML
    private MFXRectangleToggleNode shotsOnTarget;
    @FXML
    private MFXRectangleToggleNode keyPasses;
    @FXML
    private MFXRectangleToggleNode interceptions;
    @FXML
    private MFXRectangleToggleNode cleanSheets;
    @FXML
    private MFXRectangleToggleNode tacklesWon;
    @FXML
    private MFXRectangleToggleNode saves;
    private Player currentPlayer;


    public void switchPlayersMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    private MFXButton createButton(String icon, String text, EventHandler action) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
        MFXButton button = new MFXButton(text, wrapper);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(action);
        return button;
    }
    int currentValue;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Team team : Logic.getTeams()){
             newTeamChoice.getItems().add(team.getName());
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99);
        valueFactory.setValue(1);
        newNumSpinner.setValueFactory(valueFactory);
        currentValue = newNumSpinner.getValue();
        newNumSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentValue = newNumSpinner.getValue();
            }
        });
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
        if(choice!=null) {
            choice.selectedToggleProperty().addListener(((observableValue, toggle, t1) -> {
                if (t1 == null) {
                    editForm.setVisible(false);
                }
            }));
        }
    }
    private String getSelectedButtonText() {
        Toggle selectedToggle = choice.getSelectedToggle();

        if (selectedToggle != null) {
            return ((ToggleButton) selectedToggle).getText();
        } else {
            return null;
        }
    }
     public void initialization (Player player){
         playerNameLabel.setText(player.getName());
         dynamicMenu(player.getPosition());
         choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue != null) {
                 showNodes(player);
                 currentPlayer = player;
             }
         });
     }

     private void dynamicMenu (String position){
        switch (position){
            case "Forward": {
                choicesPane.getChildren().removeAll(goalsScored, assists, shotsOnTarget, keyPasses, interceptions, cleanSheets, tacklesWon, saves);
                choicesPane.getChildren().addAll(goalsScored, assists, shotsOnTarget);
                break;
            }
            case "Midfielder": {
                choicesPane.getChildren().removeAll(goalsScored, assists, shotsOnTarget, keyPasses, interceptions, cleanSheets, tacklesWon, saves);
                choicesPane.getChildren().addAll(goalsScored, assists, keyPasses, interceptions);
                break;
            }
            case "Defender": {
                choicesPane.getChildren().removeAll(goalsScored, assists, shotsOnTarget, keyPasses, interceptions, cleanSheets, tacklesWon, saves);
                choicesPane.getChildren().addAll(goalsScored, assists, cleanSheets, tacklesWon);
                break;
            }
            case "Goalkeeper": {
                choicesPane.getChildren().removeAll(goalsScored, assists, shotsOnTarget, keyPasses, interceptions, cleanSheets, tacklesWon, saves);
                choicesPane.getChildren().addAll(cleanSheets, saves);
                break;
            }
        }
     }

     private void showNodes (Player player) {
        String editing = getSelectedButtonText();
        if (player != null && editing != null) {
            Forward forward = null;
            Midfielder midfielder = null;
            Defender defender = null;
            Goalkeeper goalkeeper = null;
            switch (player.getPosition()){
                case "Forward":{
                    forward = (Forward) player;
                    break;
                }
                case "Midfielder":{
                    midfielder = (Midfielder) player;
                    break;
                }
                case "Defender":{
                    defender = (Defender) player;
                    break;
                }
                case "Goalkeeper":{
                    goalkeeper = (Goalkeeper) player;
                    break;
                }
            }
          if (editing.equalsIgnoreCase("name")){
              currentInfoLabel.setText("Current name:");
              currentInfo.setText(player.getName());
              newInfoLabel.setText("New name:");
          }
          else if (editing.equalsIgnoreCase("id")){
              currentInfoLabel.setText("Current ID:");
              currentInfo.setText(Integer.toString(player.getPlayerId()));
              newInfoLabel.setText("New ID:");
          }
          else if (editing.equalsIgnoreCase("date of birth")){
              currentInfoLabel.setText("Current "+editing+":");
              currentInfo.setText(player.getDateOfBirth().toString());
              newInfoLabel.setText("New "+editing+":");
          }
          else if (editing.equalsIgnoreCase("team")){
              currentInfoLabel.setText("Current "+editing+":");
              if (player.getTeam() != null) {
                  currentInfo.setText(player.getTeam().getName());
              }
              else {
                  currentInfo.setText("N/A");
              }
              newInfoLabel.setText("New "+editing+":");
          }
          else if (editing.equalsIgnoreCase("number")) {
              currentInfoLabel.setText("Current number:");
              currentInfo.setText(String.valueOf(player.getNumber()));
              newInfoLabel.setText("New number:");
          }
          else if (editing.equalsIgnoreCase("Nationality")){
              currentInfoLabel.setText("Current "+editing+":");
              currentInfo.setText(player.getNationality());
              newInfoLabel.setText("New "+editing+":");
          }
          else if(editing.equalsIgnoreCase("height")){
              currentInfoLabel.setText("Current height:");
              currentInfo.setText(Integer.toString(player.getHeight()));
              newInfoLabel.setText("New height:");
          }
          else if (editing.equalsIgnoreCase("weight")){
              currentInfoLabel.setText("Current weight:");
              currentInfo.setText(Integer.toString(player.getWeight()));
              newInfoLabel.setText("New weight:");
          }
          else if (editing.equalsIgnoreCase("preferred foot")){
              currentInfoLabel.setText("Current preferred foot:");
              currentInfo.setText(player.getPreferredFoot());
              newInfoLabel.setText("Change "+editing+":");
          }
          else if (editing.equalsIgnoreCase("yellow cards")){
              currentInfoLabel.setText("Current number of "+editing+":");
              currentInfo.setText(String.valueOf(player.getYellowCards()));
              newInfoLabel.setText("New number of "+editing+":");
          }
          else if (editing.equalsIgnoreCase("red cards")) {
              currentInfoLabel.setText("Current number of "+editing+":");
              currentInfo.setText(String.valueOf(player.getRedCards()));
              newInfoLabel.setText("New number of "+editing+":");
          }

          else if (editing.equalsIgnoreCase("goals scored")){
              currentInfoLabel.setText("Current number of goals scored:");
              switch (player.getPosition()){
                  case "Forward":{
                      currentInfo.setText(String.valueOf(forward.getGoalsScored().orElse(0)));
                      break;
                  }
                  case "Midfielder":{
                      currentInfo.setText(String.valueOf(midfielder.getGoalsScored().orElse(0)));
                      break;
                  }
                  case "Defender":{
                      currentInfo.setText(String.valueOf(defender.getGoalsScored().orElse(0)));
                      break;
                  }
              }
              newInfoLabel.setText("New number of goals scored:");
          }
          else if (editing.equalsIgnoreCase("Assists")){
              currentInfoLabel.setText("Current number of assists:");
              switch (player.getPosition()){
                  case "Forward":{
                      currentInfo.setText(String.valueOf(forward.getAssists().orElse(0)));
                      break;
                  }
                  case "Midfielder":{
                      currentInfo.setText(String.valueOf(midfielder.getAssists().orElse(0)));
                      break;
                  }
                  case "Defender":{
                      currentInfo.setText(String.valueOf(defender.getAssists().orElse(0)));
                      break;
                  }
              }
              newInfoLabel.setText("New number of assists:");
          }
          else if (editing.equalsIgnoreCase("shots on target")){
              currentInfoLabel.setText("Current number of shots on target:");
              currentInfo.setText(Integer.toString(forward.getShotsOnTarget()));
              newInfoLabel.setText("New number of shots on target:");
          }
          else if (editing.equalsIgnoreCase("Interceptions")){
              currentInfoLabel.setText("Current number of interceptions:");
              currentInfo.setText(Integer.toString(midfielder.getInterceptions()));
              newInfoLabel.setText("New number of interceptions:");
          }
          else if (editing.equalsIgnoreCase("Key passes")){
              currentInfoLabel.setText("Current number of key passes:");
              currentInfo.setText(Integer.toString(midfielder.getKeyPasses()));
              newInfoLabel.setText("New number of key passes:");
          }
          else if (editing.equalsIgnoreCase("clean sheets")) {
              currentInfoLabel.setText("Current number of clean sheets:");
              switch (player.getPosition()){
                  case "Defender": {
                      currentInfo.setText(Integer.toString(defender.getCleanSheets()));
                      break;
                  }
                  case "Goalkeeper":{
                      currentInfo.setText(Integer.toString(goalkeeper.getCleanSheets()));
                      break;
                  }
              }
              newInfoLabel.setText("New number of clean sheets:");
          }
          else if (editing.equalsIgnoreCase("saves")){
              currentInfoLabel.setText("Current number of saves:");
              currentInfo.setText(Integer.toString(goalkeeper.getSaves()));
              newInfoLabel.setText("New number of saves:");
          }
          else if (editing.equalsIgnoreCase("Tackles won")){
              currentInfoLabel.setText("Current number of Tackles won:");
              currentInfo.setText(Integer.toString(defender.getTacklesWon()));
              newInfoLabel.setText("New number of tackles won:");
          }


          if (editing.equalsIgnoreCase("date of birth")){
              newDate.setVisible(true);
              newInfoField.setVisible(false);
              newTeamChoice.setVisible(false);
              newNumSpinner.setVisible(false);
              footBox.setVisible(false);
          }
          else if (editing.equalsIgnoreCase("preferred foot")){
              newDate.setVisible(false);
              newInfoField.setVisible(false);
              newTeamChoice.setVisible(false);
              newNumSpinner.setVisible(false);
              footBox.setVisible(true);
          }
          else if (editing.equalsIgnoreCase("team")){
              newDate.setVisible(false);
              newInfoField.setVisible(false);
              newTeamChoice.setVisible(true);
              newNumSpinner.setVisible(false);
              footBox.setVisible(false);
          }
          else if (editing.equalsIgnoreCase("number")){
              newDate.setVisible(false);
              newInfoField.setVisible(false);
              newTeamChoice.setVisible(false);
              newNumSpinner.setVisible(true);
              footBox.setVisible(false);
          }
          else {
              newDate.setVisible(false);
              newInfoField.setVisible(true);
              newTeamChoice.setVisible(false);
              newNumSpinner.setVisible(false);
              footBox.setVisible(false);
          }
          editForm.setVisible(true);
        }
     }

     public void editPlayerInfo () {
         Alert success = new Alert(Alert.AlertType.INFORMATION, "Player updated successfully!");
         Player player = currentPlayer;
         String editing = getSelectedButtonText();
         if (player != null && editing != null){
             try {
                 if (editing.equalsIgnoreCase("name")) {
                     String name = newInfoField.getText();
                     player.setName(name);
                     playerNameLabel.setText(player.getName());
                     success.show();
                 } else if (editing.equalsIgnoreCase("id")) {
                     int id = Integer.parseInt(newInfoField.getText());
                     boolean isIdDuplicate = false;
                     for (Player element : Logic.getPlayers()) {
                         if (element.getPlayerId() == id) {
                             Alert alert = new Alert(Alert.AlertType.WARNING, "This id is already taken by another player!");
                             alert.show();
                             isIdDuplicate = true;
                             break;
                         }
                     }
                     if (!isIdDuplicate) {
                         player.setPlayerId(id);
                         success.show();
                     }
                 } else if (editing.equalsIgnoreCase("date of birth")) {
                     LocalDate dob = newDate.getValue();
                     Period period = Period.between(dob, LocalDate.now());
                     if (period.getYears() < 16) {
                         Alert alert = new Alert(Alert.AlertType.WARNING, "Player can't be younger than 16 years");
                         alert.show();
                     } else {
                         player.setDateOfBirth(dob);
                         success.show();
                     }
                 } else if (editing.equalsIgnoreCase("nationality")) {
                     String nationality = newInfoField.getText();
                     player.setNationality(nationality);
                     success.show();
                 } else if (editing.equalsIgnoreCase("number")) {
                     int num = newNumSpinner.getValue();
                     player.setNumber(num);
                     success.show();
                 } else if (editing.equalsIgnoreCase("team")) {
                     String teamName = newTeamChoice.getValue();
                     if (player.getTeam() != null) {
                         player.getTeam().removePlayer(player);
                         if (player.getTeam().getCaptain().getPlayerId() == player.getPlayerId()) {
                             player.getTeam().setCaptain(null);
                         }
                     }
                     for (Team team : Logic.getTeams()) {
                         if (team.getName().equalsIgnoreCase(teamName)) {
                             player.setTeam(team);
                             team.addPlayer(player);
                             success.show();
                             break;
                         }
                     }
                 } else if (editing.equalsIgnoreCase("height")) {
                     int height = Integer.parseInt(newInfoField.getText());
                     player.setHeight(height);
                     success.show();
                 } else if (editing.equalsIgnoreCase("weight")) {
                     int weight = Integer.parseInt(newInfoField.getText());
                     player.setWeight(weight);
                     success.show();
                 } else if (editing.equalsIgnoreCase("preferred foot")) {
                     String preferredFoot = footGroup.getSelectedToggle().toString();
                     player.setPreferredFoot(preferredFoot);
                     success.show();
                 } else if (editing.equalsIgnoreCase("yellow cards")) {
                     int yellowCards = Integer.parseInt(newInfoField.getText());
                     player.setYellowCards(yellowCards);
                     success.show();
                 } else if (editing.equalsIgnoreCase("red cards")) {
                     int redCards = Integer.parseInt(newInfoField.getText());
                     player.setRedCards(redCards);
                     success.show();
                 } else if (editing.equalsIgnoreCase("goals scored")) {
                     int goalsScored = Integer.parseInt(newInfoField.getText());
                     switch (player.getPosition()) {
                         case "Forward": {
                             Forward forward = (Forward) player;
                             forward.setGoalsScored(goalsScored);
                             forward.updateStats();
                             break;
                         }
                         case "Midfielder": {
                             Midfielder midfielder = (Midfielder) player;
                             midfielder.setGoalsScored(goalsScored);
                             break;
                         }
                         case "Defender": {
                             Defender defender = (Defender) player;
                             defender.setGoalsScored(goalsScored);
                             break;
                         }
                     }
                     success.show();
                 } else if (editing.equalsIgnoreCase("assists")) {
                     int assists = Integer.parseInt(newInfoField.getText());
                     switch (player.getPosition()) {
                         case "Forward": {
                             Forward forward = (Forward) player;
                             forward.setGoalsScored(assists);
                             break;
                         }
                         case "Midfielder": {
                             Midfielder midfielder = (Midfielder) player;
                             midfielder.setGoalsScored(assists);
                             break;
                         }
                         case "Defender": {
                             Defender defender = (Defender) player;
                             defender.setAssists(assists);
                             break;
                         }
                     }
                     success.show();
                 } else if (editing.equalsIgnoreCase("clean sheets")) {
                     int cleanSheets = Integer.parseInt(newInfoField.getText());
                     switch (player.getPosition()) {
                         case "Defender": {
                             Defender defender = (Defender) player;
                             defender.setCleanSheets(cleanSheets);
                             break;
                         }
                         case "Goalkeeper": {
                             Goalkeeper goalkeeper = (Goalkeeper) player;
                             goalkeeper.setCleanSheets(cleanSheets);
                         }
                     }
                     success.show();
                 } else if (editing.equalsIgnoreCase("shots on target")) {
                     int shotsOnTarget = Integer.parseInt(newInfoField.getText());
                     Forward forward = (Forward) player;
                     forward.setShotsOnTarget(shotsOnTarget);
                     forward.updateStats();
                     success.show();
                 } else if (editing.equalsIgnoreCase("interceptions")) {
                     int interceptions = Integer.parseInt(newInfoField.getText());
                     Midfielder midfielder = (Midfielder) player;
                     midfielder.setInterceptions(interceptions);
                     success.show();
                 } else if (editing.equalsIgnoreCase("key passes")) {
                     int keyPasses = Integer.parseInt(newInfoField.getText());
                     Midfielder midfielder = (Midfielder) player;
                     midfielder.setInterceptions(keyPasses);
                     success.show();
                 } else if (editing.equalsIgnoreCase("Tackles won")) {
                     int tacklesWon = Integer.parseInt(newInfoField.getText());
                     Defender defender = (Defender) player;
                     defender.setTacklesWon(tacklesWon);
                     success.show();
                 } else if (editing.equalsIgnoreCase("saves")) {
                     int saves = Integer.parseInt(newInfoField.getText());
                     Goalkeeper goalkeeper = (Goalkeeper) player;
                     goalkeeper.setSaves(saves);
                     success.show();
                 }
                 newInfoField.clear();
                 newDate.setValue(null);
                 newTeamChoice.setValue(null);
                 footGroup.selectToggle(null);
                 newNumSpinner.getValueFactory().setValue(newNumSpinner.getValueFactory().getConverter().fromString("1"));
                 choice.selectToggle(null);
                 editForm.setVisible(false);
             }catch (NumberFormatException nfe){
                 Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input, please try again");
                 alert.show();
             }
             catch (Exception e){
                 Alert alert = new Alert(Alert.AlertType.ERROR, "An error has occurred");
                 alert.show();
             }
         }
     }
}
