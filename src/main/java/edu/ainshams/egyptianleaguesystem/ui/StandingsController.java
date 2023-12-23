package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Team;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StandingsController implements Initializable {

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @FXML
    private BorderPane subMenuRoot;
    @FXML
    private TableView<Team> standingsTable;

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
        subMenuRoot.setTop(back);
        BorderPane.setAlignment(back, Pos.TOP_RIGHT);
        back.setPrefWidth(128);  // Set your desired width
        back.setPrefHeight(50);  // Set your desired height

// Ensure the back button doesn't grow beyond the preferred size
        back.setMaxSize(back.getPrefWidth(), back.getPrefHeight());
        back.setMinSize(back.getPrefWidth(), back.getPrefHeight());
        BorderPane.setMargin(back, new Insets(25, 5, 0, 0));
        setupTable();
    }

    private void setupTable() {

        TableColumn<Team, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setSortable(false);  // Assuming you don't want this column to be sortable.
        positionColumn.setCellValueFactory(data -> {
            Team team = data.getValue();
            int position = standingsTable.getItems().indexOf(team) + 1; // +1 because positions start from 1, not 0.
            return new SimpleStringProperty(String.valueOf(position));
        });

        TableColumn<Team, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Team, Integer> matchesPlayedColumn = new TableColumn<>("MP");
        matchesPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("matchesPlayed"));

        TableColumn<Team, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<Team, Integer> drawsColumn = new TableColumn<>("Draws");
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));

        TableColumn<Team, Integer> lossesColumn = new TableColumn<>("Losses");
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));

        TableColumn<Team, Integer> goalsForColumn = new TableColumn<>("GF");
        goalsForColumn.setCellValueFactory(new PropertyValueFactory<>("goalsFor"));

        TableColumn<Team, Integer> goalsAgainstColumn = new TableColumn<>("GA");
        goalsAgainstColumn.setCellValueFactory(new PropertyValueFactory<>("goalsAgainst"));

        TableColumn<Team, Integer> goalDifferenceColumn = new TableColumn<>("GD");
        goalDifferenceColumn.setCellValueFactory(new PropertyValueFactory<>("goalDifference"));

        TableColumn<Team, Integer> totalScoreColumn = new TableColumn<>("Pts");
        totalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
        positionColumn.setPrefWidth(80); // Set preferred width to 80 pixels
        positionColumn.setResizable(false); // Make it non-resizable by the user


        standingsTable.getColumns().addAll(positionColumn, nameColumn, matchesPlayedColumn, winsColumn, drawsColumn, lossesColumn, goalsForColumn, goalsAgainstColumn, goalDifferenceColumn, totalScoreColumn);

        for (TableColumn<Team, ?> column : standingsTable.getColumns()) {
            column.setResizable(false);
            column.setSortable(false);
            column.setEditable(false);
            column.setReorderable(false);
            if(column.equals(nameColumn)){
                column.setPrefWidth(200);
            }
            else{
                column.setPrefWidth(80);
            }
        }
        ObservableList<Team> observableTeams = FXCollections.observableArrayList(Logic.getTeams());
        standingsTable.setItems(observableTeams);
        int rowHeight = 50;
        double preferredHeight = (Team.getNumOfTeams() + 1) * rowHeight;
        standingsTable.setPrefHeight(preferredHeight);
    }


    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
}
