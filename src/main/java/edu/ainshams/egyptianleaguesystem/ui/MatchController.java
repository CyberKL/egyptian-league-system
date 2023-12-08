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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class MatchController {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private Button backBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField homeTeamField;
    @FXML
    private TextField awayTeamField;
    @FXML
    private TextField idField;
    @FXML
    private TextField refField;
    @FXML
    private TextField stadField;
    @FXML
    private Label scoreLabel;
    @FXML
    private TextField scoreField;
    @FXML
    private TextField idLookupField;
    @FXML
    private GridPane infoGrid;
    @FXML
    private VBox labelsBox;
    @FXML
    private AnchorPane root;


    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }
    public void blueBack(){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }
    public void switchMatchMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("matchesMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private void removeNodeById(AnchorPane container, String nodeId) {
        container.getChildren().stream()
                .filter(node -> node.getId() != null && node.getId().equals(nodeId))
                .findFirst()
                .ifPresent(node -> node.setVisible(false));
    }

    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");
    Alert matchNotFound = new Alert(Alert.AlertType.WARNING, "Match not found!");

    public void createMatch(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Match created successfully!");
        if (idField.getText().isBlank() || datePicker.getValue() == null || homeTeamField.getText().isBlank() || awayTeamField.getText().isBlank() || refField.getText().isBlank() || stadField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            if (datePicker.getValue().isBefore(LocalDate.now())){
                scoreField.setDisable(false);
                scoreLabel.setDisable(false);
                if (scoreField.getText().isBlank()){
                    missingDataAlert.show();
                }
                else {
                    Team homeTeam = null;
                    Team awayTeam = null;
                    Referee ref = null;
                    Stadium stad = null;
                    int matchId = Integer.parseInt(idField.getText());
                    LocalDate matchDate = datePicker.getValue();
                    String homeTeamName = homeTeamField.getText();
                    String awayTeamName = awayTeamField.getText();
                    String refName = refField.getText();
                    String stadName = stadField.getText();
                    String matchScore = scoreField.getText();
                    if (validateData(matchId, homeTeamName, awayTeamName, refName, stadName, Optional.of(matchScore))){
                        for (Team team : Logic.getTeams()){
                            if (team.getName().equalsIgnoreCase(homeTeamName)){
                                homeTeam = team;
                            }
                            else if (team.getName().equalsIgnoreCase(awayTeamName)){
                                awayTeam = team;
                            }
                        }
                        for (Referee referee : Logic.getReferees()){
                            if (referee.getName().equalsIgnoreCase(refName)){
                                ref = referee;
                                break;
                            }
                        }
                        for (Stadium stadium : Logic.getStadiums()){
                            if (stadium.getName().equalsIgnoreCase(stadName)){
                                stad = stadium;
                                break;
                            }
                        }
                        Match match = new Match(matchId, matchDate, homeTeam, awayTeam, ref, stad);
                        Score score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));
                        match.setScore(score);
                        Match.result(match);
                        Logic.addMatch(match);
                        updateEntitiesCreate(match, homeTeam, awayTeam, ref, stad, matchDate);
                        success.show();
                    }
                }
            }
            else {
                Team homeTeam = null;
                Team awayTeam = null;
                Referee ref = null;
                Stadium stad = null;
                int matchId = Integer.parseInt(idField.getText());
                LocalDate matchDate = datePicker.getValue();
                String homeTeamName = homeTeamField.getText();
                String awayTeamName = awayTeamField.getText();
                String refName = refField.getText();
                String stadName = stadField.getText();
                if (validateData(matchId, homeTeamName, awayTeamName, refName, stadName, Optional.empty())){
                    for (Team team : Logic.getTeams()){
                        if (team.getName().equalsIgnoreCase(homeTeamName)){
                            homeTeam = team;
                        }
                        else if (team.getName().equalsIgnoreCase(awayTeamName)){
                            awayTeam = team;
                        }
                    }
                    for (Referee referee : Logic.getReferees()){
                        if (referee.getName().equalsIgnoreCase(refName)){
                            ref = referee;
                            break;
                        }
                    }
                    for (Stadium stadium : Logic.getStadiums()){
                        if (stadium.getName().equalsIgnoreCase(stadName)){
                            stad = stadium;
                            break;
                        }
                    }
                    Match match = new Match(matchId, matchDate, homeTeam, awayTeam, ref, stad);
                    Logic.addMatch(match);
                    updateEntitiesCreate(match, homeTeam, awayTeam, ref, stad, matchDate);
                    success.show();
                }

            }
        }
    }
    private boolean validateData(int id, String homeName, String awayName, String refereeName, String stadiumName, Optional<String> score){
        boolean isIdDuplicate = false;
        boolean homeTeam = false;
        boolean awayTeam = false;
        boolean ref = false;
        boolean stad = false;
        boolean validScore;
        boolean validMatch = false;
        for (Match match : Logic.getMatches()){
            if (match.getMatchId()==id){
                isIdDuplicate = true;
                Alert alert = new Alert(Alert.AlertType.WARNING, "There is already a match with this ID!");
                alert.show();
                break;
            }
        }
        for (Team team : Logic.getTeams()){
            if (team.getName().equalsIgnoreCase(homeName)){
                homeTeam = true;
            }
            else if (team.getName().equalsIgnoreCase(awayName)){
                awayTeam = true;
            }
        }
        if (!homeTeam){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Home team not found");
            alert.show();
        }
        else if (!awayTeam){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Away team not found");
            alert.show();
        }
        for (Referee referee : Logic.getReferees()){
            if (referee.getName().equalsIgnoreCase(refereeName)){
                ref = true;
                break;
            }
        }
        if (!ref){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Referee not found");
            alert.show();
        }
        for (Stadium stadium : Logic.getStadiums()){
            if (stadium.getName().equalsIgnoreCase(stadiumName)){
                stad = true;
                break;
            }
        }
        if (!stad){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Stadium not found");
            alert.show();
        }
        if (score.isPresent()){
            String matchScore = score.get();
            validScore = matchScore.matches("\\d+-\\d+");
            if (!validScore){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Enter a valid score (home-away)");
                alert.show();
            }
            if (!isIdDuplicate && homeTeam && awayTeam && ref && stad && validScore){
                validMatch = true;
            }
        }
        else {
            if (!isIdDuplicate && homeTeam && awayTeam && ref && stad){
                validMatch = true;
            }
        }
        return validMatch;
    }
    private void updateEntitiesCreate(Match match, Team homeTeam, Team awayTeam, Referee referee, Stadium stadium, LocalDate date){
        homeTeam.addMatch(match);
        awayTeam.addMatch(match);
        if (date.isBefore(LocalDate.now())){
            referee.setMatchesRefereed(referee.getMatchesRefereed()+1);
            homeTeam.setMatchesPlayed(homeTeam.getMatchesPlayed()+1);
            awayTeam.setMatchesPlayed(awayTeam.getMatchesPlayed()+1);
            stadium.setMatchesPlayedOn(stadium.getMatchesPlayedOn()+1);
        }
        else {
            stadium.addUpcomingMatch(match);
        }
    }

    public void deleteMatch(){
        boolean found = false;
        if (idLookupField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            for (Match match : Logic.getMatches()){
                int matchId = Integer.parseInt(idLookupField.getText());
                if (match.getMatchId() == matchId){
                    found = true;
                    Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this match: "+match.matchHeader());
                    Optional<ButtonType> result = deleteAlert.showAndWait();
                    if (result.isPresent() && result.get()==ButtonType.OK){
                        updateEntitiesDelete(match);
                        Logic.removeMatch(match);
                        Alert success = new Alert(Alert.AlertType.INFORMATION, "Match deleted successfully!");
                        success.show();
                        break;
                    }
                }
            }
            if (!found){
                matchNotFound.show();
            }
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

    public void displayMatchInfo(){
        boolean found = false;
        Match currentMatch;
        if (idLookupField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            int id = Integer.parseInt(idField.getText());
            for (Match match : Logic.getMatches()){
                if (match.getMatchId()==id){
                    found = true;
                    currentMatch = match;
                    removeNodeById(root, "idBox");
                    matchInfo(currentMatch);
                    break;
                }
            }
            if (!found){
                matchNotFound.show();
            }
        }
    }
    private void matchInfo(Match match){
        Label id = new Label(Integer.toString(match.getMatchId()));
        Label date = new Label(match.getDate().toString());
        Label homeTeam = new Label(match.getHomeTeam().getName());
        Label awayTeam = new Label(match.getAwayTeam().getName());
        Label referee = new Label(match.getReferee().getName());
        Label stadium = new Label(match.getStadium().getName());
        VBox infoList = new VBox(id, date, homeTeam, awayTeam, referee, stadium);
        if (match.getDate().isBefore(LocalDate.now())){
            Label scoreLabel = new Label("Score:");
            labelsBox.getChildren().add(scoreLabel);
            Label score = new Label(match.getScore().toString());
            infoList.getChildren().add(score);
        }
        infoGrid.add(infoList , 1, 0);
        infoGrid.setVisible(true);
    }

}
