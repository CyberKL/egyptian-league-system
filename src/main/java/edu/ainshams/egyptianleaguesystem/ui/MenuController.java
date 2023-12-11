package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class MenuController {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    private Stage stage;

    @FXML
    private Button backBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private GridPane managerInfoGrid;

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

    private TextDialogController openTextDialog(ActionEvent event, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("textDialog.fxml"));
            Parent root = loader.load();

            // Access the controller and set the stage for the dialog
            TextDialogController dialogController = loader.getController();
            Stage dialogStage = new Stage();
            dialogController.setDialogStage(dialogStage);

            // Set title and content text
            dialogController.setDialogTitle(title);

            // Show the text dialog and wait for it to be closed
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            return dialogController;
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }

    //Main menu methods
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

    //Team menu methods
    public void switchToNewTeam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newTeam.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Team findTeamById(String enteredId) {
        for (Team team : Logic.getTeams()) {
            if (team.getTeamId()==Integer.parseInt(enteredId)) {
                return team;
            }
        }
        return null; // Manager not found
    }
    public void deleteTeam(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Team");

        if (textDialogController != null) {
            String enteredId = textDialogController.getEnteredId();

            if (enteredId != null && !enteredId.isEmpty()) {
                Team existingTeam = findTeamById(enteredId);
                if (existingTeam != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingTeam.getName());
                    Optional<ButtonType> choice = alert.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        Logic.removeTeam(existingTeam);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Team deleted successfully!");
                        success.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Team not found!");
                    alert.show();
                }
            }
        }
    }
    public void switchToEditTeam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("editTeam.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToTeamInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamInfo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToTeamMatches(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamMatches.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToTeamPlayers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamPlayers.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    //Stadium menu methods
    public void switchToNewStadium(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newStadium.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Stadium findStadiumById(String enteredId) {
        for (Stadium stadium : Logic.getStadiums()) {
            if (stadium.getId() == Integer.parseInt(enteredId)) {
                return stadium;
            }
        }
        return null; // Manager not found
    }
    public void deleteStadium(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Stadium");

        if (textDialogController != null) {
            String enteredId = textDialogController.getEnteredId();

            if (enteredId != null && !enteredId.isEmpty()) {
                Stadium existingStadium = findStadiumById(enteredId);
                if (existingStadium != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingStadium.getName());
                    Optional<ButtonType> choice = alert.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        Logic.removeStadium(existingStadium);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Stadium deleted successfully!");
                        success.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Stadium not found!");
                    alert.show();
                }
            }
        }
    }
    public void switchToStadiumInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stadiumInfo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToStadiumMatches(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stadiumMatches.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    //Match menu methods
    public void switchToNewMatch(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newMatch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Match findMatchById(String enteredId) {
        for (Match match : Logic.getMatches()) {
            if (match.getMatchId() == Integer.parseInt(enteredId)) {
                return match;
            }
        }
        return null; // Manager not found
    }
    private void updateResultDelete(Match match){
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();
        Score score = match.getScore();
        homeTeam.setGoalsFor(homeTeam.getGoalsFor() - score.getHomeTeam());
        homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst()-score.getAwayTeam());
        homeTeam.calcGoalDiff();
        awayTeam.setGoalsFor(awayTeam.getGoalsFor() - score.getAwayTeam());
        awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst()-score.getHomeTeam());
        awayTeam.calcGoalDiff();
        if (match.getWinner().equalsIgnoreCase(homeTeam.getName())){
            homeTeam.setWins(homeTeam.getWins()-1);
            homeTeam.setTotalScore(homeTeam.getTotalScore()-3);
            awayTeam.setLosses(awayTeam.getLosses()-1);
        } else if (match.getWinner().equalsIgnoreCase(awayTeam.getName())){
            awayTeam.setWins(awayTeam.getWins()-1);
            awayTeam.setTotalScore(awayTeam.getTotalScore()-3);
            homeTeam.setLosses(homeTeam.getLosses()-1);
        }
        else {
            homeTeam.setDraws(homeTeam.getDraws()-1);
            homeTeam.setTotalScore(homeTeam.getTotalScore()-1);
            awayTeam.setDraws(awayTeam.getDraws()-1);
            awayTeam.setTotalScore(awayTeam.getTotalScore()-1);
        }
    }
    private void updateEntitiesDelete(Match match){
        match.getHomeTeam().removeMatch(match);
        match.getAwayTeam().removeMatch(match);
        if (match.getDate().isBefore(LocalDate.now())){
            match.getReferee().setMatchesRefereed(match.getReferee().getMatchesRefereed()-1);
            match.getHomeTeam().setMatchesPlayed(match.getHomeTeam().getMatchesPlayed()-1);
            match.getAwayTeam().setMatchesPlayed(match.getAwayTeam().getMatchesPlayed()-1);
            match.getStadium().setMatchesPlayedOn(match.getStadium().getMatchesPlayedOn()-1);
            updateResultDelete(match);
        }
        else {
            match.getStadium().getUpcomingMatches().remove(match);
        }
    }
    public void deleteMatch(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Match");

        if (textDialogController != null) {
            String enteredId = textDialogController.getEnteredId();

            if (enteredId != null && !enteredId.isEmpty()) {
                Match existingMatch = findMatchById(enteredId);
                if (existingMatch != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingMatch.matchHeader());
                    Optional<ButtonType> choice = alert.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        updateEntitiesDelete(existingMatch);
                        Logic.removeMatch(existingMatch);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Stadium deleted successfully!");
                        success.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Stadium not found!");
                    alert.show();
                }
            }
        }
    }
    public void switchToMatchInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("matchInfo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToEditMatch(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("editMatch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    //Manager menu methods
    public void switchToNewManager(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Manager findManagerById(String enteredId) {
        for (Manager manager : Logic.getManagers()) {
            if (manager.getManagerId()==Integer.parseInt(enteredId)) {
                return manager;
            }
        }
        return null; // Manager not found
    }
    public void switchToManagerInfo(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Manager Info");

        if (textDialogController != null) {
            // Check if the user entered a valid ID
            String enteredId = textDialogController.getEnteredId();
            if (enteredId != null && !enteredId.isEmpty()) {
                Manager existingManager = findManagerById(enteredId);
                if (existingManager != null) {
                    FXMLLoader managerInfoLoader = new FXMLLoader(getClass().getResource("managerInfo.fxml"));
                    Parent managerInfoRoot = managerInfoLoader.load();
                    ManagerInfoController managerInfoController = managerInfoLoader.getController();
                    managerInfoController.managerInfo(existingManager);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(managerInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                    alert.show();
                }
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToEditManager(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Edit Manager");

        if (textDialogController != null) {
            // Check if the user entered a valid ID
            String enteredId = textDialogController.getEnteredId();
            if (enteredId != null && !enteredId.isEmpty()) {
                Manager existingManager = findManagerById(enteredId);
                if (existingManager != null) {
                    // Proceed to switch to the EditManager scene with the existing manager
                    FXMLLoader editManagerLoader = new FXMLLoader(getClass().getResource("editManager.fxml"));
                    Parent editManagerRoot = editManagerLoader.load();
                    EditManagerController editManagerController = editManagerLoader.getController();
                    editManagerController.initialize(existingManager);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(editManagerRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                    alert.show();
                }
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void deleteManager(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Manager");

        if (textDialogController != null) {
            String enteredId = textDialogController.getEnteredId();

            if (enteredId != null && !enteredId.isEmpty()) {
                Manager existingManager = findManagerById(enteredId);
                if (existingManager != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingManager.getName());
                    Optional<ButtonType> choice = alert.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        Logic.removeManager(existingManager);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Manager deleted successfully!");
                        success.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                    alert.show();
                }
            }
        }
    }



}
