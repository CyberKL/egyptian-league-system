package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Match;
import edu.ainshams.egyptianleaguesystem.model.Player;
import edu.ainshams.egyptianleaguesystem.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
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
import java.util.ArrayList;

public class TeamController {

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private Button backBtn;
    @FXML
    private TextField teamName;
    @FXML
    private TextField teamId;
    @FXML
    private GridPane editGrid;
    @FXML
    private TextField idField;
    @FXML
    private AnchorPane root;
    @FXML
    private Pane confirmPane;
    @FXML
    private HBox choiceBox;
    @FXML
    private GridPane infoGrid;
    @FXML
    private TextField nameField;

    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }

    public void blueBack(){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }

    public void switchTeamMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamsMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    public void createTeam(){
        boolean duplicateName = false;
        boolean duplicateId = false;
        String name = teamName.getText();
        int id = Integer.parseInt(teamId.getText());
        for (Team team : Logic.getTeams()){
            if (team.getName().equalsIgnoreCase(name)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("There is already a team with this name!");
                alert.show();
                duplicateName = true;
            }
            if (team.getTeamId()==id){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("There is already a team with this id!");
                alert.show();
                duplicateId = true;
            }
        }
        if (!duplicateName && !duplicateId) {
            Team team = new Team(name, id);
            Logic.addTeam(team);
        }
        System.out.println("Available teams:");
        Logic.displayTotalTeams();
    }



    private void removeNodeById(AnchorPane container, String nodeId) {
        container.getChildren().stream()
                .filter(node -> node.getId() != null && node.getId().equals(nodeId))
                .findFirst()
                .ifPresent(node -> node.setVisible(false));
    }


    private int id;
    private boolean isIdAvailable = false;

    public void displayTeamInfo(){
        Team currentTeam;
        boolean found = false;
        String name = nameField.getText();
        for (Team team: Logic.getTeams()){
            if (team.getName().equalsIgnoreCase(name)){
                found = true;
                currentTeam = team;
                removeNodeById(root, "idBox");
                teamInfo(currentTeam);
                isIdAvailable = true;
                break;
            }
        }
        if (!found){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Team not found!");
            alert.show();
        }
    }

    private void teamInfo(Team team){
        Label teamName = new Label(team.getName());
        Label teamId = new Label(Integer.toString(team.getTeamId()));
        Label teamManager = new Label(team.getManager().getName());
        Label teamCaptain = new Label(team.getCaptain().getName());
        Label matchesPlayed = new Label(Integer.toString(team.getMatchesPlayed()));
        Label teamWins = new Label(Integer.toString(team.getWins()));
        Label teamDraws = new Label(Integer.toString(team.getDraws()));
        Label teamLosses = new Label(Integer.toString(team.getLosses()));
        Label teamGoalsFor = new Label(Integer.toString(team.getGoalsFor()));
        Label teamGoalsAgainst = new Label(Integer.toString(team.getGoalsAgainst()));
        Label teamGoalDifference = new Label(Integer.toString(team.getGoalDifference()));
        Label teamTotalScore = new Label(Integer.toString(team.getTotalScore()));
        VBox infoList = new VBox(teamName, teamId, teamManager, teamCaptain, matchesPlayed, teamWins, teamDraws, teamLosses, teamGoalsFor, teamGoalsAgainst, teamGoalDifference, teamTotalScore);
        infoGrid.add(infoList, 1, 0);
        infoGrid.setVisible(true);
    }
    public void displayTeamMatches(){
        Team currentTeam;
        boolean found = false;
        String name = nameField.getText();
        for (Team team: Logic.getTeams()){
            if (team.getName().equalsIgnoreCase(name)){
                found = true;
                currentTeam = team;
                removeNodeById(root, "idBox");
                teamMatches(currentTeam);
                isIdAvailable = true;
                break;
            }
        }
        if (!found){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Team not found!");
            alert.show();
        }
    }
    private void teamMatches(Team team){
        FlowPane matchesPane = new FlowPane();
        for (Match match : team.getMatches()){
            Label label = new Label(match.matchHeader());
            label.setFont(new Font(20));
            label.setTextFill(Color.WHITE);
            matchesPane.getChildren().add(label);
        }
        root.getChildren().add(matchesPane);
    }
    public void displayTeamPlayers(){
        Team currentTeam;
        boolean found = false;
        String name = nameField.getText();
        for (Team team: Logic.getTeams()){
            if (team.getName().equalsIgnoreCase(name)){
                found = true;
                currentTeam = team;
                removeNodeById(root, "idBox");
                teamPlayers(currentTeam);
                isIdAvailable = true;
                break;
            }
        }
        if (!found){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Team not found!");
            alert.show();
        }
    }
    private   void teamPlayers(Team team){
        FlowPane playersPane = new FlowPane();
        playersPane.setOrientation(Orientation.VERTICAL);
        for (Player player : team.getPlayers()){
            Label label = new Label(player.getNumber()+"  "+player.getName());
            playersPane.getChildren().add(label);
        }
    }

    public void determineTeamId(){
        boolean found = false;
        int id = Integer.parseInt(idField.getText());
        for (Team team: Logic.getTeams()){
            if (team.getTeamId()==id){
                this.id = id;
                found = true;
                removeNodeById(root, "idBox");
                Label chooseLabel = new Label("Please select a choice");
                chooseLabel.setFont(new Font(50));
                chooseLabel.setTextFill(Color.WHITE);
                chooseLabel.setId("chooseLabel");
                AnchorPane.setLeftAnchor(chooseLabel, 700.0);
                AnchorPane.setBottomAnchor(chooseLabel, 400.0);
                root.getChildren().add(chooseLabel);
                isIdAvailable = true;
                break;
            }
        }
        if (!found){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Team not found!");
            alert.show();
        }
    }

    public void editName(){
        if (!isIdAvailable){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter team id first!");
            alert.show();
        }
        else {
            removeNodeById(root, "chooseLabel");
            Label label = new Label("Enter new team name: ");
            label.setTextFill(Color.WHITE);
            label.setFont(new Font(40));
            TextField textField = new TextField();
            textField.setFont(new Font(15));
            Button button = new Button("Confirm");
            button.setFont(new Font(15));
            choiceBox.getChildren().clear();
            editGrid.getChildren().clear();
            confirmPane.getChildren().clear();
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setHalignment(textField, HPos.CENTER);
            GridPane.setHalignment(button, HPos.CENTER);
            GridPane.setValignment(label, VPos.TOP);
            GridPane.setValignment(textField, VPos.CENTER);
            GridPane.setValignment(button, VPos.BOTTOM);
            editGrid.getChildren().addAll(label, textField);
            confirmPane.getChildren().add(button);
            button.setOnAction(actionEvent -> {
                boolean duplicateTeam = false;
                String name = textField.getText();
                if(!name.isBlank()) {
                    for (Team team : Logic.getTeams()) {
                        if (team.getName().equalsIgnoreCase(name)) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("There is already a team with this name!");
                            alert.show();
                            duplicateTeam = true;
                            break;
                        }
                    }
                    if (!duplicateTeam) {
                        for (Team team : Logic.getTeams()) {
                            if (team.getTeamId() == id) {
                                team.setName(name);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Team name changed successfully");
                                alert.show();
                                break;
                            }
                        }
                    }
                    System.out.println("Available teams:");
                    Logic.displayTotalTeams();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please enter a name!");
                    alert.show();
                }
            });
        }
    }
    public void editId(){
        if (!isIdAvailable){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter team id first!");
            alert.show();
        }
        else {
            removeNodeById(root, "chooseLabel");
            Label label = new Label("Enter new team Id: ");
            label.setTextFill(Color.WHITE);
            label.setFont(new Font(40));
            TextField textField = new TextField();
            textField.setFont(new Font(15));
            Button button = new Button("Confirm");
            button.setFont(new Font(15));
            choiceBox.getChildren().clear();
            editGrid.getChildren().clear();
            confirmPane.getChildren().clear();
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setHalignment(textField, HPos.CENTER);
            GridPane.setHalignment(button, HPos.CENTER);
            GridPane.setValignment(label, VPos.TOP);
            GridPane.setValignment(textField, VPos.CENTER);
            GridPane.setValignment(button, VPos.BOTTOM);
            editGrid.getChildren().addAll(label, textField);
            confirmPane.getChildren().add(button);
            button.setOnAction(actionEvent -> {
                boolean duplicateTeam = false;
                int newId = Integer.parseInt(textField.getText());
                if(!textField.getText().isBlank()) {
                    for (Team team : Logic.getTeams()) {
                        if (team.getTeamId()==newId) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("There is already a team with this id!");
                            alert.show();
                            duplicateTeam = true;
                            break;
                        }
                    }
                    if (!duplicateTeam) {
                        for (Team team : Logic.getTeams()) {
                            if (team.getTeamId() == id) {
                                team.setTeamId(newId);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Team id changed successfully");
                                alert.show();
                                break;
                            }
                        }
                    }
                    System.out.println("Available teams:");
                    ArrayList<Team> teamList=Logic.getTeams();
                    System.out.println(teamList.get(0).getTeamId());
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please enter an Id!");
                    alert.show();
                }
            });

        }

    }
    public void editPlayer(){
        if (!isIdAvailable){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter team id first!");
            alert.show();
        }
        else {
            removeNodeById(root, "chooseLabel");
            editGrid.getChildren().clear();
            confirmPane.getChildren().clear();
            choiceBox.getChildren().clear();
            choiceBox.setSpacing(10);
            AnchorPane.setLeftAnchor(choiceBox, 1000.0);
            AnchorPane.setBottomAnchor(choiceBox, 800.0);
            ToggleGroup choiceToggle = new ToggleGroup();
            ToggleButton addBtn = new ToggleButton("Add");
            addBtn.setToggleGroup(choiceToggle);
            addBtn.setFont(new Font(30));
            ToggleButton removeBtn = new ToggleButton("Remove");
            removeBtn.setToggleGroup(choiceToggle);
            removeBtn.setFont(new Font(30));
            choiceBox.getChildren().addAll(addBtn,removeBtn);
            Label nameLabel = new Label("Enter player name:");
            Label idLabel = new Label("Enter player id: ");
            nameLabel.setFont(new Font(40));
            nameLabel.setTextFill(Color.WHITE);
            idLabel.setFont(new Font(40));
            idLabel.setTextFill(Color.WHITE);
            TextField nameText = new TextField();
            TextField idText = new TextField();
            nameText.setFont(new Font(15));
            idText.setFont(new Font(15));
            Button confirm = new Button("Confirm");
            GridPane.setHalignment(nameLabel, HPos.CENTER);
            GridPane.setHalignment(idLabel, HPos.CENTER);
            GridPane.setHalignment(nameText, HPos.CENTER);
            GridPane.setHalignment(idText, HPos.CENTER);
            GridPane.setValignment(nameLabel, VPos.TOP);
            GridPane.setValignment(idLabel, VPos.TOP);
            GridPane.setValignment(nameText, VPos.CENTER);
            GridPane.setValignment(idText, VPos.CENTER);
            editGrid.setHgap(30);
            addBtn.setOnAction(actionEvent ->{
                confirmPane.getChildren().add(confirm);
                editGrid.add(nameLabel, 0, 0);
                editGrid.add(nameText, 0, 0);
                editGrid.add(idLabel, 1, 0);
                editGrid.add(idText, 1, 0);
                confirm.setOnAction(confirmEvent -> {
                    if (nameText.getText().isBlank() || idText.getText().isBlank()){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please enter the required data!");
                        alert.show();
                    }
                    else {
                        boolean playerFound = false;
                        String playerName = nameText.getText();
                        int playerId = Integer.parseInt(idText.getText());
                        for (Player player:Logic.getPlayers()){
                            if (player.getName().equalsIgnoreCase(playerName)&&player.getPlayerId()==playerId){
                                playerFound = true;
                                for (Team team : Logic.getTeams()){
                                    if (team.getTeamId()==id){
                                        if (player.getTeam()!=null){
                                            player.getTeam().deletePlayer(player);
                                        }
                                        team.addPlayer(player);
                                        player.setTeam(team);
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setContentText("Player added to "+team.getName()+" successfully!");
                                        alert.show();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (!playerFound){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Player not found!");
                            alert.show();
                        }
                    }
                });
            });
            removeBtn.setOnAction(actionEvent -> {
                confirmPane.getChildren().add(confirm);
                editGrid.add(nameLabel, 0, 0);
                editGrid.add(nameText, 0, 0);
                editGrid.add(idLabel, 1, 0);
                editGrid.add(idText, 1, 0);
                confirm.setOnAction(confirmEvent -> {
                    if (nameText.getText().isBlank() || idText.getText().isBlank()){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please enter the required data!");
                        alert.show();
                    }
                    else {
                        boolean playerFound = false;
                        String playerName = nameText.getText();
                        int playerId = Integer.parseInt(idText.getText());
                        for (Team team : Logic.getTeams()){
                            if (team.getTeamId()==id){
                                for (Player player : team.getPlayers()){
                                    if (player.getName().equalsIgnoreCase(playerName) && player.getPlayerId()==playerId){
                                        playerFound = true;
                                        team.deletePlayer(player);
                                        player.setTeam(null);
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setContentText("Player removed from "+team.getName()+" successfully!");
                                        alert.show();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (!playerFound){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Player not found!");
                            alert.show();
                        }
                    }
                });
            });

        }
    }

    public void editCaptain(){
        if (!isIdAvailable){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter team id first!");
            alert.show();
        }
        else {
            removeNodeById(root, "chooseLabel");
            editGrid.getChildren().clear();
            confirmPane.getChildren().clear();
            choiceBox.getChildren().clear();
            Label nameLabel = new Label("Enter player name:");
            Label idLabel = new Label("Enter player id: ");
            nameLabel.setFont(new Font(40));
            nameLabel.setTextFill(Color.WHITE);
            idLabel.setFont(new Font(40));
            idLabel.setTextFill(Color.WHITE);
            TextField nameText = new TextField();
            TextField idText = new TextField();
            nameText.setFont(new Font(15));
            idText.setFont(new Font(15));
            Button confirm = new Button("Confirm");
            GridPane.setHalignment(nameLabel, HPos.CENTER);
            GridPane.setHalignment(idLabel, HPos.CENTER);
            GridPane.setHalignment(nameText, HPos.CENTER);
            GridPane.setHalignment(idText, HPos.CENTER);
            GridPane.setValignment(nameLabel, VPos.TOP);
            GridPane.setValignment(idLabel, VPos.TOP);
            GridPane.setValignment(nameText, VPos.CENTER);
            GridPane.setValignment(idText, VPos.CENTER);
            editGrid.setHgap(30);
            confirmPane.getChildren().add(confirm);
            editGrid.add(nameLabel, 0, 0);
            editGrid.add(nameText, 0, 0);
            editGrid.add(idLabel, 1, 0);
            editGrid.add(idText, 1, 0);
            confirm.setOnAction(confirmEvent -> {
                boolean playerFound = false;
                if (nameText.getText().isBlank() || idText.getText().isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please enter the required data!");
                    alert.show();
                } else {
                    String playerName = nameText.getText();
                    int playerId = Integer.parseInt(idText.getText());
                    for (Team team : Logic.getTeams()){
                        if (team.getTeamId()==id){
                            if (team.getCaptain()!=null && playerName.equalsIgnoreCase(team.getCaptain().getName()) && playerId==team.getCaptain().getPlayerId()){
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setContentText("This player is already the team's captain!");
                                alert.show();
                                break;
                            }
                            else {
                                for (Player player : team.getPlayers()) {
                                    if (player.getName().equalsIgnoreCase(playerName) && player.getPlayerId() == playerId) {
                                        playerFound = true;
                                       team.setCaptain(player);
                                       break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                    if (!playerFound){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Player not found!");
                        alert.show();
                    }
                }
            });
        }
    }
}
