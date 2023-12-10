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
import javafx.scene.layout.Pane;
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
    @FXML
    private Pane startPane;
    @FXML
    private TextField idEditField;
    @FXML
    private Label matchHeaderLabel;
    @FXML
    private Label currentInfoLabel;
    @FXML
    private Label currentInfo;
    @FXML
    private Label newInfoLabel;
    @FXML
    private TextField newInfoField;
    @FXML
    private DatePicker newDate;
    @FXML
    private Button confirmBtn;
    @FXML
    private ToggleGroup choice;


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
                    if (validateData(matchId, matchDate, homeTeamName, awayTeamName, refName, stadName, Optional.of(matchScore))){
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
                if (validateData(matchId, matchDate, homeTeamName, awayTeamName, refName, stadName, Optional.empty())){
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
    private boolean validateData(int id, LocalDate date, String homeName, String awayName, String refereeName, String stadiumName, Optional<String> score){
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
        boolean stadiumNotAvailable = false;
        for (Stadium stadium : Logic.getStadiums()){
            if (stadium.getName().equalsIgnoreCase(stadiumName)){
                for (Match match : stadium.getUpcomingMatches()){
                    if (match.getDate().equals(date)){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Stdium not available on "+date);
                        alert.show();
                        stadiumNotAvailable = true;
                        break;
                    }
                }
                if (!stadiumNotAvailable) {
                    stad = true;
                    break;
                }
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
    public void dateChecker(){
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate!=null && selectedDate.isBefore(LocalDate.now())){
            scoreLabel.setDisable(false);
            scoreField.setDisable(false);
        }
        else {
            scoreLabel.setDisable(true);
            scoreField.setDisable(true);
            scoreField.clear();
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

    private Match determineMatchId(){
        int id = Integer.parseInt(idEditField.getText());
        Match currentMatch = null;
        boolean matchFound = false;
        for (Match match : Logic.getMatches()){
            if (match.getMatchId()==id){
                currentMatch = match;
                matchFound = true;
            }
        }
        if (!matchFound){
            matchNotFound.show();
        }
        else {
            startPane.setVisible(false);
        }
        return currentMatch;
    }
    private String getSelectedButtonText() {
        Toggle selectedToggle = choice.getSelectedToggle();

        if (selectedToggle != null) {
            // Assuming ToggleButton is used in the ToggleGroup
            return ((ToggleButton) selectedToggle).getText();
        } else {
            // Handle the case where no toggle is selected, return an appropriate value
            return "No toggle selected";
        }
    }
    public void showNodes(){
        String editing = getSelectedButtonText();
        Match currentMatch = determineMatchId();
        if (currentMatch != null){
            matchHeaderLabel.setText(currentMatch.matchHeader());
            currentInfoLabel.setText("Current "+editing+":");
            currentInfo.setText(currentMatch.getDate().toString());
            newInfoLabel.setText("New "+editing+":");
            matchHeaderLabel.setVisible(true);
            currentInfoLabel.setVisible(true);
            currentInfo.setVisible(true);
            newInfoLabel.setVisible(true);
            if (editing.equalsIgnoreCase("date")){
                newDate.setVisible(true);
            }else {
                newInfoField.setVisible(true);
            }
            confirmBtn.setVisible(true);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error occurred!");
            alert.show();
        }
    }
    public void editMatchInfo(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Match updated successfully!");
        Match currentMatch = determineMatchId();
        if (currentMatch != null) {
            String editing = getSelectedButtonText();
            if (editing.equalsIgnoreCase("date")) {
                LocalDate newDate = this.newDate.getValue();
                if (newDate.isBefore(LocalDate.now())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Date must be upcoming!");
                    alert.show();
                } else {
                    Referee referee = currentMatch.getReferee();
                    Team homeTeam = currentMatch.getHomeTeam();
                    Team awayTeam = currentMatch.getAwayTeam();
                    Stadium stadium = currentMatch.getStadium();
                    if (currentMatch.getDate().isBefore(LocalDate.now())){
                        referee.setMatchesRefereed(referee.getMatchesRefereed()-1);
                        homeTeam.setMatchesPlayed(homeTeam.getMatchesPlayed()-1);
                        awayTeam.setMatchesPlayed(awayTeam.getMatchesPlayed()-1);
                        stadium.setMatchesPlayedOn(stadium.getMatchesPlayedOn()-1);
                        stadium.addUpcomingMatch(currentMatch);
                        updateResultDelete(currentMatch);
                    }
                    currentMatch.setDate(newDate);
                }
            }
            else if (editing.equalsIgnoreCase("home team")){
                String newTeam = newInfoField.getText();
                boolean done = false;
                if (currentMatch.getDate().isBefore(LocalDate.now())){
                    for (Team team : Logic.getTeams()){
                        if (team.getName().equalsIgnoreCase(newTeam) && !currentMatch.getHomeTeam().getName().equalsIgnoreCase(newTeam) && !currentMatch.getAwayTeam().getName().equalsIgnoreCase(newTeam)){
                            updateTeam(currentMatch.getHomeTeam(), currentMatch);
                            currentMatch.setHomeTeam(team);
                            currentMatch.getHomeTeam().addMatch(currentMatch);
                            currentMatch.getHomeTeam().setMatchesPlayed(currentMatch.getHomeTeam().getMatchesPlayed()+1);
                            Match.result(currentMatch);
                            done = true;
                            success.show();
                            break;
                        }
                    }
                    if (!done){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Team already a side of this match or team not found!");
                        alert.show();
                    }
                }
                else {
                    for (Team team : Logic.getTeams()){
                        if (team.getName().equalsIgnoreCase(newTeam) && !currentMatch.getHomeTeam().getName().equalsIgnoreCase(newTeam) && !currentMatch.getAwayTeam().getName().equalsIgnoreCase(newTeam)){
                            currentMatch.getHomeTeam().removeMatch(currentMatch);
                            currentMatch.setHomeTeam(team);
                            currentMatch.getHomeTeam().setMatchesPlayed(currentMatch.getHomeTeam().getMatchesPlayed()+1);
                            done = true;
                            success.show();
                            break;
                        }
                    }
                    if (!done){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Team already a side of this match or team not found!");
                        alert.show();
                    }

                }
            }
            else if (editing.equalsIgnoreCase("away team")){
                String newTeam = newInfoField.getText();
                boolean done = false;
                if (currentMatch.getDate().isBefore(LocalDate.now())){
                    for (Team team : Logic.getTeams()){
                        if (team.getName().equalsIgnoreCase(newTeam) && !currentMatch.getHomeTeam().getName().equalsIgnoreCase(newTeam) && !currentMatch.getAwayTeam().getName().equalsIgnoreCase(newTeam)){
                            updateTeam(currentMatch.getAwayTeam(), currentMatch);
                            currentMatch.setAwayTeam(team);
                            currentMatch.getAwayTeam().addMatch(currentMatch);
                            currentMatch.getAwayTeam().setMatchesPlayed(currentMatch.getAwayTeam().getMatchesPlayed()+1);
                            Match.result(currentMatch);
                            done = true;
                            success.show();
                            break;
                        }
                    }
                    if (!done){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Team already a side of this match or team not found!");
                        alert.show();
                    }
                }
                else {
                    for (Team team : Logic.getTeams()){
                        if (team.getName().equalsIgnoreCase(newTeam) && !currentMatch.getHomeTeam().getName().equalsIgnoreCase(newTeam) && !currentMatch.getAwayTeam().getName().equalsIgnoreCase(newTeam)){
                            currentMatch.getAwayTeam().removeMatch(currentMatch);
                            currentMatch.setAwayTeam(team);
                            currentMatch.getAwayTeam().setMatchesPlayed(currentMatch.getAwayTeam().getMatchesPlayed()+1);
                            done = true;
                            success.show();
                            break;
                        }
                    }
                    if (!done){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Team already a side of this match or team not found!");
                        alert.show();
                    }
                }
            }
            else if (editing.equalsIgnoreCase("referee")){
                String refName = newInfoField.getText();
                boolean found = false;
                for (Referee referee : Logic.getReferees()){
                    if (referee.getName().equalsIgnoreCase(refName)){
                        currentMatch.getReferee().setMatchesRefereed(currentMatch.getReferee().getMatchesRefereed()-1);
                        currentMatch.setReferee(referee);
                        currentMatch.getReferee().setMatchesRefereed(currentMatch.getReferee().getMatchesRefereed()+1);
                        success.show();
                        break;
                    }
                }
                if (!found){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Referee not found");
                    alert.show();
                }
            }
            else if (editing.equalsIgnoreCase("stadium")) {
                String stadiumName = newInfoField.getText();
                boolean found = false;
                for (Stadium stadium : Logic.getStadiums()){
                    if (stadium.getName().equalsIgnoreCase(stadiumName)){
                        found = true;
                        for (Match match : stadium.getUpcomingMatches()){
                            if (match.getDate().equals(currentMatch.getDate())){
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Stadium is not available on "+currentMatch.getDate());
                                alert.show();
                            }
                            else {
                                if (currentMatch.getDate().isBefore(LocalDate.now())){
                                    currentMatch.getStadium().setMatchesPlayedOn(currentMatch.getStadium().getMatchesPlayedOn()-1);
                                    currentMatch.setStadium(stadium);
                                    currentMatch.getStadium().setMatchesPlayedOn(currentMatch.getStadium().getMatchesPlayedOn()+1);
                                }
                                else {
                                    currentMatch.getStadium().removeUpcomingMatch(currentMatch);
                                    currentMatch.setStadium(stadium);
                                    currentMatch.getStadium().addUpcomingMatch(currentMatch);
                                }
                                success.show();
                            }
                            break;
                        }
                    }
                }
                if (!found){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Stadium not found");
                    alert.show();
                }
            }
            else {
                if (currentMatch.getDate().isBefore(LocalDate.now())) {
                    String matchScore = newInfoField.getText();
                    if (isValidScore(matchScore)) {
                        Score score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));
                        currentMatch.getHomeTeam().setGoalsFor(currentMatch.getHomeTeam().getGoalsFor() - currentMatch.getScore().getHomeTeam());
                        currentMatch.getHomeTeam().setGoalsAgainst(currentMatch.getHomeTeam().getGoalsAgainst() - currentMatch.getScore().getAwayTeam());
                        currentMatch.getAwayTeam().setGoalsFor(currentMatch.getAwayTeam().getGoalsFor() - currentMatch.getScore().getAwayTeam());
                        currentMatch.getAwayTeam().setGoalsAgainst(currentMatch.getAwayTeam().getGoalsAgainst() - currentMatch.getScore().getHomeTeam());
                        currentMatch.setScore(score);
                        Match.result(currentMatch);
                        currentMatch.getHomeTeam().setGoalsFor(currentMatch.getHomeTeam().getGoalsFor() + currentMatch.getScore().getHomeTeam());
                        currentMatch.getHomeTeam().setGoalsAgainst(currentMatch.getHomeTeam().getGoalsAgainst() + currentMatch.getScore().getAwayTeam());
                        currentMatch.getAwayTeam().setGoalsFor(currentMatch.getAwayTeam().getGoalsFor() + currentMatch.getScore().getAwayTeam());
                        currentMatch.getAwayTeam().setGoalsAgainst(currentMatch.getAwayTeam().getGoalsAgainst() + currentMatch.getScore().getHomeTeam());
                        currentMatch.getHomeTeam().calcGoalDiff();
                        currentMatch.getHomeTeam().calcTotalScore();
                        currentMatch.getAwayTeam().calcGoalDiff();
                        currentMatch.getAwayTeam().calcTotalScore();
                        success.show();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a valid score!");
                        alert.show();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Match not played yet!");
                    alert.show();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error occurred!");
            alert.show();
        }
    }
    private void updateTeam (Team team, Match match){
        Score score = match.getScore();
        team.setMatchesPlayed(team.getMatchesPlayed()-1);
        team.setGoalsFor(team.getGoalsFor()-score.getHomeTeam());
        team.setGoalsAgainst(team.getGoalsAgainst()-score.getAwayTeam());
        team.calcGoalDiff();
        if (match.getWinner().equalsIgnoreCase(team.getName())){
            team.setWins(team.getWins()-1);
        }
        else if (match.getWinner().equalsIgnoreCase("draw")){
            team.setDraws(team.getDraws()-1);
        }
        else {
            team.setLosses(team.getLosses()-1);
        }
        team.calcTotalScore();
        team.removeMatch(match);
    }
    private boolean isValidScore(String matchScore) {
        return matchScore.matches("\\d+-\\d+");
    }

}
