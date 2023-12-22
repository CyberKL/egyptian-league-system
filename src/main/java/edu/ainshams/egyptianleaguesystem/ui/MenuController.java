package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

    public void redQuit(){
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

    public void blueBack(){
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
    private Team findTeamById(int enteredId) {
        for (Team team : Logic.getTeams()) {
            if (team.getTeamId() == enteredId) {
                return team;
            }
        }
        return null;
    }
    public void deleteTeam(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Team");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");

            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Team existingTeam = findTeamById(enteredId);
                if (existingTeam != null) {
                    Alert delete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingTeam.getName());
                    Optional<ButtonType> choice = delete.showAndWait();
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
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Team not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
    }
    public void switchToEditTeam(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Edit Team");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Team existingTeam = findTeamById(enteredId);
                if (existingTeam != null) {
                    // Proceed to switch to the EditManager scene with the existing manager
                    FXMLLoader editTeamLoader = new FXMLLoader(getClass().getResource("editTeam.fxml"));
                    Parent editTeamRoot = editTeamLoader.load();
                    TeamController teamController = editTeamLoader.getController();
                    teamController.initialize(existingTeam);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(editTeamRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                } else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Team not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToTeamInfo(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Team Info");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Team existingTeam = findTeamById(enteredId);
                if (existingTeam != null) {
                    FXMLLoader teamInfoLoader = new FXMLLoader(getClass().getResource("teamInfo.fxml"));
                    Parent teamInfoRoot = teamInfoLoader.load();
                    TeamController teamController = teamInfoLoader.getController();
                    teamController.teamInfo(existingTeam);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(teamInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                } else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Team not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToTeamMatches(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Team Matches");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Team existingTeam = findTeamById(enteredId);
                if (existingTeam != null) {
                    if (!existingTeam.getMatches().isEmpty()) {
                        FXMLLoader teamInfoLoader = new FXMLLoader(getClass().getResource("teamMatches.fxml"));
                        Parent teamInfoRoot = teamInfoLoader.load();
                        TeamController teamController = teamInfoLoader.getController();
                        teamController.teamMatches(existingTeam);

                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene startMenu = new Scene(teamInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                        stage.setScene(startMenu);
                        stage.show();
                    }
                    else {
                        Alert notFound = new Alert(Alert.AlertType.WARNING, "This team doesn't have matches!");
                        notFound.show();
                    }
                } else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Team not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToTeamPlayers(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Team Players");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Team existingTeam = findTeamById(enteredId);
                if (existingTeam != null) {
                    if (!existingTeam.getPlayers().isEmpty()) {
                        FXMLLoader teamInfoLoader = new FXMLLoader(getClass().getResource("teamPlayers.fxml"));
                        Parent teamInfoRoot = teamInfoLoader.load();
                        TeamController teamController = teamInfoLoader.getController();
                        teamController.teamPlayers(existingTeam);

                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene startMenu = new Scene(teamInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                        stage.setScene(startMenu);
                        stage.show();
                    }
                    else {
                        Alert notFound = new Alert(Alert.AlertType.WARNING, "This team doesn't have players!");
                        notFound.show();
                    }
                } else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Team not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }

    //Stadium menu methods
    public void switchToNewStadium(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newStadium.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Stadium findStadiumById(int enteredId) {
        for (Stadium stadium : Logic.getStadiums()) {
            if (stadium.getId() == enteredId) {
                return stadium;
            }
        }
        return null; // Manager not found
    }
    public void deleteStadium(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Stadium");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Stadium existingStadium = findStadiumById(enteredId);
                if (existingStadium != null) {
                    Alert delete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingStadium.getName());
                    Optional<ButtonType> choice = delete.showAndWait();
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
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Stadium not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
    }
    public void switchToStadiumInfo(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Stadium Info");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Stadium existingStadium = findStadiumById(enteredId);
                if (existingStadium != null) {
                    FXMLLoader stadiumInfoLoader = new FXMLLoader(getClass().getResource("stadiumInfo.fxml"));
                    Parent stadiumInfoRoot = stadiumInfoLoader.load();
                    StadiumController stadiumController = stadiumInfoLoader.getController();
                    stadiumController.stadiumInfo(existingStadium);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(stadiumInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                } else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Stadium not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToStadiumMatches(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Stadium upcoming matches");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Stadium existingStadium = findStadiumById(enteredId);
                if (existingStadium != null) {
                    if (!existingStadium.getUpcomingMatches().isEmpty()) {
                        FXMLLoader stadiumMatchesLoader = new FXMLLoader(getClass().getResource("stadiumMatches.fxml"));
                        Parent stadiumMatchesRoot = stadiumMatchesLoader.load();
                        StadiumController stadiumController = stadiumMatchesLoader.getController();
                        stadiumController.stadiumUpcomingMatches(existingStadium);

                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene startMenu = new Scene(stadiumMatchesRoot, screenSize.getWidth(), screenSize.getHeight());
                        stage.setScene(startMenu);
                        stage.show();
                    }
                    else {
                        Alert noMatches = new Alert(Alert.AlertType.ERROR, "There is no upcoming matches on this stadium");
                        noMatches.show();
                    }
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Stadium not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe) {
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }

    //Match menu methods
    public void switchToNewMatch(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newMatch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Match findMatchById(int enteredId) {
        for (Match match : Logic.getMatches()) {
            if (match.getMatchId() == enteredId) {
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

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Match existingMatch = findMatchById(enteredId);
                if (existingMatch != null) {
                    Alert delete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingMatch.matchHeader());
                    Optional<ButtonType> choice = delete.showAndWait();
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
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Stadium not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
    }
    public void switchToMatchInfo(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Match Info");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Match existingMatch = findMatchById(enteredId);
                if (existingMatch != null) {
                    FXMLLoader matchInfoLoader = new FXMLLoader(getClass().getResource("matchInfo.fxml"));
                    Parent matchInfoRoot = matchInfoLoader.load();
                    MatchController matchController = matchInfoLoader.getController();
                    matchController.matchInfo(existingMatch);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(matchInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                } else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Match not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToEditMatch(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Edit Match");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Match existingMatch = findMatchById(enteredId);
                if (existingMatch != null) {
                    // Proceed to switch to the EditManager scene with the existing manager
                    FXMLLoader editMatchLoader = new FXMLLoader(getClass().getResource("editMatch.fxml"));
                    Parent editMatchRoot = editMatchLoader.load();
                    MatchController matchController = editMatchLoader.getController();
                    matchController.initialize(existingMatch);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(editMatchRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                } else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Match not found!");
                    notFound.show();
                }
            } catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }

    //Manager menu methods
    public void switchToNewManager(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Manager findManagerById(int enteredId) {
        for (Manager manager : Logic.getManagers()) {
            if (manager.getManagerId()==enteredId) {
                return manager;
            }
        }
        return null; // Manager not found
    }
    public void switchToManagerInfo(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Manager Info");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
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
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToEditManager(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Edit Manager");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try{
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
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
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void deleteManager(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Manager");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Manager existingManager = findManagerById(enteredId);
                if (existingManager != null) {
                    Alert delete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingManager.getName());
                    Optional<ButtonType> choice = delete.showAndWait();
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
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
    }

    //Referee menu methods
    public void switchToNewReferee(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newReferee.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Referee findRefereeById(int enteredId) {
        for (Referee referee : Logic.getReferees()) {
            if (referee.getRefereeId()==enteredId) {
                return referee;
            }
        }
        return null; // Manager not found
    }
    public void switchToEditReferee(ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Edit Referee");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try{
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Referee existingReferee = findRefereeById(enteredId);
                if (existingReferee != null) {
                    // Proceed to switch to the EditManager scene with the existing manager
                    FXMLLoader editRefereeLoader = new FXMLLoader(getClass().getResource("editReferee.fxml"));
                    Parent editRefereeRoot = editRefereeLoader.load();
                    RefereeController refereeController = editRefereeLoader.getController();
                    refereeController.initialize(existingReferee);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(editRefereeRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Referee not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void switchToRefereeInfo(ActionEvent event) throws IOException{
        TextDialogController textDialogController = openTextDialog(event, "Referee Information");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try{
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Referee existingReferee = findRefereeById(enteredId);
                if (existingReferee != null) {
                    // Proceed to switch to the EditManager scene with the existing manager
                    FXMLLoader refereeInfoLoader = new FXMLLoader(getClass().getResource("refereeInfo.fxml"));
                    Parent refereeRInfoRoot = refereeInfoLoader.load();
                    RefereeController refereeController = refereeInfoLoader.getController();
                    refereeController.refereeInfo(existingReferee);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(refereeRInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Referee not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }
    public void deleteReferee(ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete Referee");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Referee existingReferee = findRefereeById(enteredId);
                if (existingReferee != null) {
                    Alert delete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingReferee.getName());
                    Optional<ButtonType> choice = delete.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        Logic.removeReferee(existingReferee);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Referee deleted successfully!");
                        success.showAndWait();
                    }
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Referee not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
    }


    //Player related methods
    public void switchToNewPlayer (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newPlayer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private Player findPlayerById(int enteredId) {
        for (Player player : Logic.getPlayers()) {
            if (player.getPlayerId()==enteredId) {
                return player;
            }
        }
        return null; // Manager not found
    }
    public void switchToEditPlayer (ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Edit Player");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try{
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Player existingPlayer = findPlayerById(enteredId);
                if (existingPlayer != null) {
                    FXMLLoader editPlayerLoader = new FXMLLoader(getClass().getResource("editPlayer.fxml"));
                    Parent editPlayerRoot = editPlayerLoader.load();
                    EditPlayerController editPlayerController = editPlayerLoader.getController();
                    editPlayerController.initialization(existingPlayer);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(editPlayerRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Player not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }

    public void switchToPlayerInfo (ActionEvent event) throws IOException {
        TextDialogController textDialogController = openTextDialog(event, "Player Info");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            // Check if the user entered a valid ID
            try{
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Player existingPlayer = findPlayerById(enteredId);
                if (existingPlayer != null) {
                    FXMLLoader playerInfoLoader = new FXMLLoader(getClass().getResource("playerInfo.fxml"));
                    Parent playerInfoRoot = playerInfoLoader.load();
                    PlayerInfoController playerInfoController = playerInfoLoader.getController();
                    playerInfoController.playerInfo(existingPlayer);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene startMenu = new Scene(playerInfoRoot, screenSize.getWidth(), screenSize.getHeight());
                    stage.setScene(startMenu);
                    stage.show();
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Player not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }

    public void deletePlayer (ActionEvent event) {
        TextDialogController textDialogController = openTextDialog(event, "Delete player");

        if (textDialogController != null && textDialogController.getEnteredId() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only!");
            try {
                int enteredId = Integer.parseInt(textDialogController.getEnteredId());
                Player existingPlayer = findPlayerById(enteredId);
                if (existingPlayer != null) {
                    Alert delete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + existingPlayer.getName());
                    Optional<ButtonType> choice = delete.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        Logic.removePlayer(existingPlayer);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Player deleted successfully!");
                        success.showAndWait();
                    }
                }
                else {
                    Alert notFound = new Alert(Alert.AlertType.WARNING, "Player not found!");
                    notFound.show();
                }
            }catch (NumberFormatException nfe){
                alert.show();
            }
        }
    }


}
