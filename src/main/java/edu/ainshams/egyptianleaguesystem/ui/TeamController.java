package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeamController implements Initializable {

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @FXML
    private TextField teamName;
    @FXML
    private TextField teamId;
    @FXML
    private GridPane infoGrid;
    @FXML
    private ToggleGroup choice;
    @FXML
    private Label teamNameLabel;
    @FXML
    private Label currentInfoLabel;
    @FXML
    private Label currentInfo;
    @FXML
    private Label newInfoLabel;
    @FXML
    private TextField newInfoField;
    @FXML
    private BorderPane editForm;
    @FXML
    private HBox playerChoiceBox;
    @FXML
    private ToggleGroup playerChoice;
    @FXML
    private AnchorPane subMenuRoot;
    @FXML
    private VBox infoBox;
    @FXML
    private Label teamNameMatchesLabel;
    @FXML
    private VBox defendersBox;

    @FXML
    private VBox forwardsBox;

    @FXML
    private VBox goalkeepersBox;

    @FXML
    private VBox midfieldersBox;
    @FXML
    private Label teamNamePlayersLabel;
    private Team currentTeam;


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
                switchTeamMenu((ActionEvent) event);
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


    public void switchTeamMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamsMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    public void createTeam(){
        try {
            Alert success = new Alert(Alert.AlertType.INFORMATION, "Team created successfully!");
            boolean duplicateName = false;
            boolean duplicateId = false;
            String name = teamName.getText();
            int id = Integer.parseInt(teamId.getText());
            for (Team team : Logic.getTeams()) {
                if (team.getName().equalsIgnoreCase(name)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("There is already a team with this name!");
                    alert.show();
                    duplicateName = true;
                }
                if (team.getTeamId() == id) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("There is already a team with this id!");
                    alert.show();
                    duplicateId = true;
                }
            }
            if (!duplicateName && !duplicateId) {
                Team team = new Team(name, id);
                Logic.addTeam(team);
                success.show();
            }
            System.out.println("Available teams:");
            Logic.displayTotalTeams();
            teamId.clear();
            teamName.clear();
        }catch (NumberFormatException nfe){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only in id field");
            alert.show();
        }
    }


    public void teamInfo(Team team){
        Label teamName = new Label(team.getName());
        teamName.getStyleClass().add("info-label");

        Label teamId = new Label(Integer.toString(team.getTeamId()));
        teamId.getStyleClass().add("info-label");

        Label teamManager;
        if (team.getManager() != null) {
            teamManager = new Label(team.getManager().getName());
        } else {
            teamManager = new Label("N/A");
        }
        teamManager.getStyleClass().add("info-label");

        Label teamCaptain;
        if (team.getCaptain() != null) {
            teamCaptain = new Label(team.getCaptain().getName());
        } else {
            teamCaptain = new Label("N/A");
        }
        teamCaptain.getStyleClass().add("info-label");

        Label matchesPlayed = new Label(Integer.toString(team.getMatchesPlayed()));
        matchesPlayed.getStyleClass().add("info-label");

        Label teamWins = new Label(Integer.toString(team.getWins()));
        teamWins.getStyleClass().add("info-label");

        Label teamDraws = new Label(Integer.toString(team.getDraws()));
        teamDraws.getStyleClass().add("info-label");

        Label teamLosses = new Label(Integer.toString(team.getLosses()));
        teamLosses.getStyleClass().add("info-label");

        Label teamGoalsFor = new Label(Integer.toString(team.getGoalsFor()));
        teamGoalsFor.getStyleClass().add("info-label");

        Label teamGoalsAgainst = new Label(Integer.toString(team.getGoalsAgainst()));
        teamGoalsAgainst.getStyleClass().add("info-label");

        Label teamGoalDifference = new Label(Integer.toString(team.getGoalDifference()));
        teamGoalDifference.getStyleClass().add("info-label");

        Label teamTotalScore = new Label(Integer.toString(team.getTotalScore()));
        teamTotalScore.getStyleClass().add("info-label");
        VBox infoList = new VBox(teamName, teamId, teamManager, teamCaptain, matchesPlayed, teamWins, teamDraws, teamLosses, teamGoalsFor, teamGoalsAgainst, teamGoalDifference, teamTotalScore);
        infoList.setAlignment(Pos.CENTER);
        infoGrid.add(infoList, 1, 0);
    }
    public void teamMatches(Team team){
        teamNameMatchesLabel.setText(team.getName());
        for (Match match : team.getMatches()){
            Label label = new Label(match.matchHeader());
            label.setFont(new Font(30));
            label.setTextFill(Color.WHITE);
            HBox hBox = new HBox(label);
            hBox.setPrefWidth(565);
            infoBox.getChildren().add(hBox);
        }
    }
    public void teamPlayers(Team team){
        teamNamePlayersLabel.setText(team.getName());
        for (Player player : team.getPlayers()){
            Label label = new Label(player.getNumber()+"  "+player.getName());
            label.setFont(new Font(25));
            label.setTextFill(Color.WHITE);
            switch (player.getPosition()){
                case "Forward": {
                    forwardsBox.getChildren().add(label);
                    break;
                }
                case "Midfielder": {
                    label.setStyle("-fx-margin: 0 0 0 20;");
                    midfieldersBox.getChildren().add(label);
                    break;
                }
                case "Defender": {
                    label.setStyle("-fx-margin: 0 0 0 20;");
                    defendersBox.getChildren().add(label);
                    break;
                }
                case "Goalkeeper": {
                    label.setStyle("-fx-margin: 0 0 0 20;");
                    goalkeepersBox.getChildren().add(label);
                    break;
                }
            }
        }
    }


    private String getSelectedButtonText() {
        Toggle selectedToggle = choice.getSelectedToggle();

        if (selectedToggle != null) {
            return ((ToggleButton) selectedToggle).getText();
        } else {
            return "No toggle selected";
        }
    }
    public void showNodes(Team currentTeam){
        String editing = getSelectedButtonText();
        if (currentTeam != null){
            playerChoiceBox.setVisible(false);
            if (editing.equalsIgnoreCase("name")){
                currentInfoLabel.setText("Current team name:");
                currentInfo.setText(currentTeam.getName());
                newInfoLabel.setText("New team name:");
            }
            else if (editing.equalsIgnoreCase("id")) {
                currentInfoLabel.setText("Current team ID:");
                currentInfo.setText(Integer.toString(currentTeam.getTeamId()));
                newInfoLabel.setText("New team ID:");
            }
            else if (editing.equalsIgnoreCase("players")){
                currentInfoLabel.setText("Add or Remove player from team");
                currentInfo.setText("");
                newInfoField.setText("Enter Player ID:");
                playerChoiceBox.setVisible(true);
            }
            else if (editing.equalsIgnoreCase("captain")){
                currentInfoLabel.setText("Current team captain:");
                if (currentTeam.getCaptain() != null) {
                    currentInfo.setText(currentTeam.getCaptain().getName());
                }
                else {
                    currentInfo.setText("N/A");
                }
                newInfoLabel.setText("New team captain:");
            }
            else {
                currentInfoLabel.setText("Current team manager:");
                if (currentTeam.getManager() != null) {
                    currentInfo.setText(currentTeam.getManager().getName());
                }
                else {
                    currentInfo.setText("N/A");
                }
                newInfoLabel.setText("New team manager:");
            }
            editForm.setVisible(true);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error occurred!");
            alert.show();
        }
    }
    public void initialize(Team team) {
        teamNameLabel.setText(team.getName());
        choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showNodes(team);
                currentTeam = team;
            }
        });
    }
    public void editTeamInfo(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Team updated successfully!");
        Team team = currentTeam;
        String editing = getSelectedButtonText();
        if (currentTeam != null && editing != null){
            try {
                if (editing.equalsIgnoreCase("name")) {
                    boolean isNameTaken = false;
                    String teamName = newInfoField.getText();
                    for (Team element : Logic.getTeams()) {
                        if (element.getName().equalsIgnoreCase(teamName)) {
                            Alert nameTaken = new Alert(Alert.AlertType.WARNING, "This name is already taken");
                            nameTaken.show();
                            isNameTaken = true;
                        }
                    }
                    if (!isNameTaken) {
                        team.setName(teamName);
                        success.show();
                        teamNameLabel.setText(team.getName());
                    }
                } else if (editing.equalsIgnoreCase("id")) {
                    boolean isIdTaken = false;
                    int id = Integer.parseInt(newInfoField.getText());
                    for (Team element : Logic.getTeams()) {
                        if (element.getTeamId() == id) {
                            Alert idTaken = new Alert(Alert.AlertType.WARNING, "This id is already taken");
                            idTaken.show();
                            isIdTaken = true;
                        }
                    }
                    if (!isIdTaken) {
                        team.setTeamId(id);
                        success.show();
                    }
                } else if (editing.equalsIgnoreCase("players")) {
                    int id = Integer.parseInt(newInfoField.getText());
                    for (Player player : Logic.getPlayers()) {
                        if (player.getPlayerId() == id) {
                            Toggle selectedToggle = playerChoice.getSelectedToggle();
                            String operation = ((ToggleButton) selectedToggle).getText();
                            switch (operation) {
                                case "Add": {
                                    if (!team.getPlayers().contains(player)) {
                                        team.addPlayer(player);
                                        success.show();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.WARNING, "This player is already a part of this team!");
                                        alert.show();
                                    }
                                }
                                case "Remove": {
                                    if (team.getPlayers().contains(player)) {
                                        team.removePlayer(player);
                                        success.show();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.WARNING, "The player entered is not a part of this team!");
                                        alert.show();
                                    }
                                }
                            }
                        }
                    }
                } else if (editing.equalsIgnoreCase("captain")) {
                    boolean captainFound = false;
                    boolean playerFound = false;
                    int id = Integer.parseInt(newInfoField.getText());
                    for (Player player : team.getPlayers()) {
                        if (player.getPlayerId() == id && team.getCaptain().getPlayerId() != id) {
                            team.setCaptain(player);
                            success.show();
                            captainFound = true;
                            break;
                        }
                    }
                    if (!captainFound) {
                        if (team.getCaptain().getPlayerId() == id) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Captain entered is already the captain of this team");
                            alert.show();
                        }
                        for (Player player : Logic.getPlayers()) {
                            if (player.getPlayerId() == id) {
                                playerFound = true;
                                break;
                            }
                        }
                        if (!playerFound) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Player not found!");
                            alert.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Captain entered is not a current player in this team");
                            alert.show();
                        }
                    }
                } else if (editing.equalsIgnoreCase("manager")) {
                    int id = Integer.parseInt(newInfoField.getText());
                    if (team.getManager().getManagerId() == id) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "This is already the team manager");
                        alert.show();
                    } else {
                        boolean managerFound = false;
                        for (Manager manager : Logic.getManagers()) {
                            if (manager.getManagerId() == id) {
                                managerFound = true;
                                if (manager.getTeam() != null) {
                                    manager.getTeam().setManager(null);
                                }
                                team.setManager(manager);
                                manager.setTeam(team);
                                success.show();
                                break;
                            }
                        }
                        if (!managerFound) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                            alert.show();
                        }
                    }
                }
                newInfoField.setText("");
                choice.selectToggle(null);
                editForm.setVisible(false);
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter number only");
                alert.show();
            }
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR, "An error occurred please try again");
            error.show();
        }
    }


}
