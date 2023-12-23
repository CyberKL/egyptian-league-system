package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class StatsController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @FXML
    private AnchorPane subMenuRoot;
    @FXML
    private VBox ageBox;

    @FXML
    private VBox assistsBox;

    @FXML
    private VBox cleansheetsBox;

    @FXML
    private VBox topScorerBox;


    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXButton back = createButton("fas-left-long", "Back", event -> {
            try {
                switchToStartMenu((ActionEvent) event);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        });
        subMenuRoot.getChildren().add(back);
        AnchorPane.setRightAnchor(back, 0.5);
        AnchorPane.setTopAnchor(back, 25.0);
        addData();

    }

    public void createTopScorersTable () {
        ObservableList<Player> filteredPlayers =
                FXCollections.observableArrayList(
                        Logic.getPlayers().stream()
                                .filter(player -> !(player instanceof Goalkeeper))
                                .collect(Collectors.toList())
                );
        TableView<Player> tableView = new TableView<>();
        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> {
            Player player = cellData.getValue();
            return new SimpleStringProperty(player.getName());
        });
        TableColumn<Player, String> goalsColumn = new TableColumn<>("Goals");
        goalsColumn.setCellValueFactory(cellData -> {
            Player player = cellData.getValue();
            Optional<Integer> goalsOptional = player.getGoalsScored();

            return goalsOptional.map(goals -> new SimpleStringProperty(String.valueOf(goals)))
                    .orElse(new SimpleStringProperty("N/A"));
        });
        SortedList<Player> sortedPlayers = new SortedList<>(filteredPlayers);


        sortedPlayers.setComparator((player1, player2) -> {
            Optional<Integer> goals1Opt = player1.getGoalsScored();
            Optional<Integer> goals2Opt = player2.getGoalsScored();


            if (goals1Opt.isPresent() && goals2Opt.isPresent()) {
                return Integer.compare(goals2Opt.get(), goals1Opt.get());
            } else if (goals1Opt.isPresent()) {
                return -1;
            } else if (goals2Opt.isPresent()) {
                return 1;
            } else {
                return 0;
            }
        });
        List<Player> topScorers = sortedPlayers.subList(0, Math.min(5, sortedPlayers.size()));

        ObservableList<Player> topObservableList = FXCollections.observableArrayList(topScorers);


        nameColumn.setPrefWidth(275);
        goalsColumn.setPrefWidth(125);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(goalsColumn);

        tableView.setItems(topObservableList);
        double rowHeight = 50;
        int numberOfPlayersToShow = topScorers.size();



        double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight;
        tableView.setPrefHeight(preferredHeight);
        topScorerBox.getChildren().add(tableView);
    }
    private void createTopAssistsTable(){
        ObservableList<Player> filteredPlayers =
                FXCollections.observableArrayList(
                        Logic.getPlayers().stream()
                                .filter(player -> !(player instanceof Goalkeeper))
                                .collect(Collectors.toList())
                );
        TableView<Player> tableView = new TableView<>();
        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> {
            Player player = cellData.getValue();
            return new SimpleStringProperty(player.getName());
        });
        TableColumn<Player, String> assistsColumn = new TableColumn<>("Assists");
        assistsColumn.setCellValueFactory(cellData -> {
            Player player = cellData.getValue();
            Optional<Integer> goalsOptional = player.getAssists();

            return goalsOptional.map(assists -> new SimpleStringProperty(String.valueOf(assists)))
                    .orElse(new SimpleStringProperty("N/A"));
        });
        SortedList<Player> sortedPlayers = new SortedList<>(filteredPlayers);


        sortedPlayers.setComparator((player1, player2) -> {
            Optional<Integer> assists1Opt = player1.getAssists();
            Optional<Integer> assists2Opt = player2.getAssists();


            if (assists1Opt.isPresent() && assists2Opt.isPresent()) {
                return Integer.compare(assists2Opt.get(), assists1Opt.get());
            } else if (assists1Opt.isPresent()) {
                return -1;
            } else if (assists2Opt.isPresent()) {
                return 1;
            } else {
                return 0;
            }
        });
        List<Player> topAssists = sortedPlayers.subList(0, Math.min(5, sortedPlayers.size()));

        ObservableList<Player> topObservableList = FXCollections.observableArrayList(topAssists);


        nameColumn.setPrefWidth(275);
        assistsColumn.setPrefWidth(125);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(assistsColumn);

        tableView.setItems(topObservableList);
        double rowHeight = 50;
        int numberOfPlayersToShow = topAssists.size();



        double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight;
        tableView.setPrefHeight(preferredHeight);
        assistsBox.getChildren().add(tableView);
    }
    private void createMostCleanSheetsTable () {
        ObservableList<Goalkeeper> goalkeepers = FXCollections.observableArrayList(
                Logic.getPlayers().stream()
                        .filter(player -> player instanceof Goalkeeper)
                        .map(player -> (Goalkeeper) player)
                        .collect(Collectors.toList())
        );
        SortedList<Goalkeeper> sortedGoalkeepers = new SortedList<>(goalkeepers);
        sortedGoalkeepers.setComparator((gk1, gk2) ->
                Integer.compare(gk2.getCleanSheets(), gk1.getCleanSheets())
        );


        TableView<Goalkeeper> tableView = new TableView<>();


        TableColumn<Goalkeeper, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Goalkeeper, Integer> cleanSheetsColumn = new TableColumn<>("Clean Sheets");
        cleanSheetsColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCleanSheets())
        );


        nameColumn.setPrefWidth(275);
        cleanSheetsColumn.setPrefWidth(125);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(cleanSheetsColumn);

        tableView.setItems(sortedGoalkeepers);
        double rowHeight = 50;
        int numberOfPlayersToShow = sortedGoalkeepers.size();



        double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight;
        tableView.setPrefHeight(preferredHeight);
        cleansheetsBox.getChildren().add(tableView);
    }
    private void createTeamsByAverageAgeTable(){
        ObservableList<Team> teams = FXCollections.observableArrayList(Logic.getTeams());


        SortedList<Team> sortedTeams = new SortedList<>(teams);
        sortedTeams.setComparator(Comparator.comparingDouble(Team::getAverageAge)
        );


        TableView<Team> tableView = new TableView<>();


        TableColumn<Team, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Team, Double> averageAgeColumn = new TableColumn<>("Average age");
        averageAgeColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getAverageAge())
        );


        nameColumn.setPrefWidth(275);
        averageAgeColumn.setPrefWidth(125);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(averageAgeColumn);

        tableView.setItems(sortedTeams);
        double rowHeight = 50;
        int numberOfPlayersToShow = sortedTeams.size();



        double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight;
        tableView.setPrefHeight(preferredHeight);
        ageBox.getChildren().add(tableView);
    }
    public void addData(){
        createTopScorersTable();
        createTopAssistsTable();
        createTeamsByAverageAgeTable();
        createMostCleanSheetsTable();
    }

    /*public void showData(){
        String showing = getSelectedButtonText();
        if (showing != null) {
            switch (showing) {
                case "Top Scorers": {
                    if (!Logic.getPlayers().isEmpty()) {
                        ObservableList<Player> filteredPlayers =
                                FXCollections.observableArrayList(
                                        Logic.getPlayers().stream()
                                                .filter(player -> !(player instanceof Goalkeeper))
                                                .collect(Collectors.toList())
                                );
                        if (!filteredPlayers.isEmpty()) {
                            TableView<Player> tableView = new TableView<>();
                            TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
                            nameColumn.setCellValueFactory(cellData -> {
                                Player player = cellData.getValue();
                                return new SimpleStringProperty(player.getName());
                            });
                            TableColumn<Player, String> goalsColumn = new TableColumn<>("Goals");
                            goalsColumn.setCellValueFactory(cellData -> {
                                Player player = cellData.getValue();
                                Optional<Integer> goalsOptional = player.getGoalsScored();

                                return goalsOptional.map(goals -> new SimpleStringProperty(String.valueOf(goals)))
                                        .orElse(new SimpleStringProperty("N/A"));
                            });
                            SortedList<Player> sortedPlayers = new SortedList<>(filteredPlayers);


                            sortedPlayers.setComparator((player1, player2) -> {
                                Optional<Integer> goals1Opt = player1.getGoalsScored();
                                Optional<Integer> goals2Opt = player2.getGoalsScored();


                                if (goals1Opt.isPresent() && goals2Opt.isPresent()) {
                                    return Integer.compare(goals2Opt.get(), goals1Opt.get());
                                } else if (goals1Opt.isPresent()) {
                                    return -1;
                                } else if (goals2Opt.isPresent()) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            });
                            List<Player> topScorers = sortedPlayers.subList(0, Math.min(5, sortedPlayers.size()));

                            ObservableList<Player> topObservableList = FXCollections.observableArrayList(topScorers);


                            nameColumn.setPrefWidth(100);
                            goalsColumn.setPrefWidth(100);
                            tableView.getColumns().add(nameColumn);
                            tableView.getColumns().add(goalsColumn);


                            tableView.setItems(topObservableList);
                            double rowHeight = 50;
                            int numberOfPlayersToShow = topScorers.size();



                            double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight;
                            tableView.setPrefHeight(preferredHeight);
                            subMenuRoot.getChildren().removeIf(node -> node instanceof TableView);
                            subMenuRoot.getChildren().add(tableView);
                            AnchorPane.setLeftAnchor(tableView, 650.0);
                            AnchorPane.setBottomAnchor(tableView, 350.0);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "There are no outfield players to show");
                            alert.show();
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "There are no players to show");
                        alert.show();
                    }
                    break;
                }
                case "Top Assists": {
                    if (!Logic.getPlayers().isEmpty()) {
                        ObservableList<Player> filteredPlayers =
                                FXCollections.observableArrayList(
                                        Logic.getPlayers().stream()
                                                .filter(player -> !(player instanceof Goalkeeper))
                                                .collect(Collectors.toList())
                                );
                        if (!filteredPlayers.isEmpty()) {
                            TableView<Player> tableView = new TableView<>();
                            TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
                            nameColumn.setCellValueFactory(cellData -> {
                                Player player = cellData.getValue();
                                return new SimpleStringProperty(player.getName());
                            });
                            TableColumn<Player, String> goalsColumn = new TableColumn<>("Assists");
                            goalsColumn.setCellValueFactory(cellData -> {
                                Player player = cellData.getValue();
                                Optional<Integer> goalsOptional = player.getAssists();

                                return goalsOptional.map(assists -> new SimpleStringProperty(String.valueOf(assists)))
                                        .orElse(new SimpleStringProperty("N/A"));
                            });
                            SortedList<Player> sortedPlayers = new SortedList<>(filteredPlayers);


                            sortedPlayers.setComparator((player1, player2) -> {
                                Optional<Integer> assists1Opt = player1.getAssists();
                                Optional<Integer> assists2Opt = player2.getAssists();


                                if (assists1Opt.isPresent() && assists2Opt.isPresent()) {
                                    return Integer.compare(assists2Opt.get(), assists1Opt.get());
                                } else if (assists1Opt.isPresent()) {
                                    return -1;
                                } else if (assists2Opt.isPresent()) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            });
                            List<Player> topAssists = sortedPlayers.subList(0, Math.min(5, sortedPlayers.size()));

                            ObservableList<Player> topObservableList = FXCollections.observableArrayList(topAssists);


                            tableView.getColumns().add(nameColumn);
                            tableView.getColumns().add(goalsColumn);


                            tableView.setItems(topObservableList);
                            double rowHeight = 24;
                            int numberOfPlayersToShow = topAssists.size();


                            double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight;
                            tableView.setPrefHeight(preferredHeight);
                            subMenuRoot.getChildren().clear();
                            subMenuRoot.getChildren().add(tableView);
                            AnchorPane.setLeftAnchor(tableView, 650.0);
                            AnchorPane.setBottomAnchor(tableView, 350.0);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "There are no outfield players to show");
                            alert.show();
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "There are no players to show");
                        alert.show();
                    }
                    break;
                }
                case "Most Clean Sheets":{
                    if (!Logic.getPlayers().isEmpty()) {
                        ObservableList<Goalkeeper> goalkeepers = FXCollections.observableArrayList(
                                Logic.getPlayers().stream()
                                        .filter(player -> player instanceof Goalkeeper)
                                        .map(player -> (Goalkeeper) player)
                                        .collect(Collectors.toList())
                        );
                        if (!goalkeepers.isEmpty()) {
                            SortedList<Goalkeeper> sortedGoalkeepers = new SortedList<>(goalkeepers);
                            sortedGoalkeepers.setComparator((gk1, gk2) ->
                                    Integer.compare(gk2.getCleanSheets(), gk1.getCleanSheets())
                            );


                            TableView<Goalkeeper> tableView = new TableView<>();


                            TableColumn<Goalkeeper, String> nameColumn = new TableColumn<>("Name");
                            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

                            TableColumn<Goalkeeper, Integer> cleanSheetsColumn = new TableColumn<>("Clean Sheets");
                            cleanSheetsColumn.setCellValueFactory(cellData ->
                                    new SimpleObjectProperty<>(cellData.getValue().getCleanSheets())
                            );


                            tableView.getColumns().add(nameColumn);
                            tableView.getColumns().add(cleanSheetsColumn);


                            tableView.setItems(sortedGoalkeepers);


                            int rowHeight = 24;
                            int numberOfPlayersToShow = sortedGoalkeepers.size();


                            double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight;
                            tableView.setPrefHeight(preferredHeight);
                            subMenuRoot.getChildren().clear();
                            subMenuRoot.getChildren().add(tableView);
                            AnchorPane.setLeftAnchor(tableView, 650.0);
                            AnchorPane.setBottomAnchor(tableView, 350.0);
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "There are no goalkeepers to show");
                            alert.show();
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "There are no players to show");
                        alert.show();
                    }
                    break;
                }
                case "Average age":{
                    if (!Logic.getTeams().isEmpty()) {
                        ObservableList<Team> teams = FXCollections.observableArrayList(Logic.getTeams());


                        SortedList<Team> sortedTeams = new SortedList<>(teams);
                        sortedTeams.setComparator(Comparator.comparingDouble(Team::getAverageAge)
                        );


                        TableView<Team> tableView = new TableView<>();


                        TableColumn<Team, String> nameColumn = new TableColumn<>("Name");
                        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

                        TableColumn<Team, Double> averageAgeColumn = new TableColumn<>("Average age");
                        averageAgeColumn.setCellValueFactory(cellData ->
                                new SimpleObjectProperty<>(cellData.getValue().getAverageAge())
                        );


                        tableView.getColumns().add(nameColumn);
                        tableView.getColumns().add(averageAgeColumn);


                        tableView.setItems(sortedTeams);

                        int rowHeight = 24;
                        int numberOfPlayersToShow = sortedTeams.size();

                        double preferredHeight = (numberOfPlayersToShow + 1) * rowHeight; // +1 for header
                        tableView.setPrefHeight(preferredHeight);
                        subMenuRoot.getChildren().clear();
                        subMenuRoot.getChildren().add(tableView);
                        AnchorPane.setLeftAnchor(tableView, 650.0);
                        AnchorPane.setBottomAnchor(tableView, 350.0);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "There are no teams to show");
                        alert.show();
                    }
                    break;
                }
            }
        }
    }*/
}
