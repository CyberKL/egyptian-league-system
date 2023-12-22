package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Match;
import edu.ainshams.egyptianleaguesystem.model.Stadium;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class StadiumController {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private Button backBtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField cityField;
    @FXML
    private GridPane infoGrid;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField idField;

    public void switchStadiumMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stadiumsMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }
    public void blueBack(){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }

    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");

    public void createStadium(){
        if (nameField.getText().isBlank() || idField.getText().isBlank() || capacityField.getText().isBlank() || cityField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            try {
                boolean duplicateStadium = false;
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                int capacity = Integer.parseInt(capacityField.getText());
                String city = cityField.getText();
                for (Stadium stadium : Logic.getStadiums()) {
                    if (stadium.getName().equalsIgnoreCase(name)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("There is already a stadium with this name!");
                        alert.show();
                        duplicateStadium = true;
                        break;
                    }
                }
                for (Stadium stadium : Logic.getStadiums()) {
                    if (stadium.getId() == id) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("There is already a stadium with this name!");
                        alert.show();
                        duplicateStadium = true;
                        break;
                    }
                }
                if (!duplicateStadium) {
                    Stadium stadium = new Stadium(name, id, capacity, city);
                    Logic.addStadium(stadium);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Stadium created!");
                    alert.show();
                }
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only in id and capacity field");
                alert.show();
            }
        }
    }

    public void stadiumInfo(Stadium stadium){
        Label stadiumName = new Label(stadium.getName());
        Label stadiumId = new Label(Integer.toString(stadium.getId()));
        Label stadiumCity = new Label(stadium.getCity());
        Label stadiumCapacity = new Label(Integer.toString(stadium.getCapacity()));
        Label stadiumNumOfMatches = new Label(Integer.toString(stadium.getMatchesPlayedOn()));
        VBox infoList = new VBox(stadiumName, stadiumId, stadiumCity, stadiumCapacity, stadiumNumOfMatches);
        infoGrid.add(infoList, 1, 0);
        infoGrid.setVisible(true);
    }

    public void stadiumUpcomingMatches(Stadium stadium){
        FlowPane matchesPane = new FlowPane();
        for (Match match : stadium.getUpcomingMatches()){
            Label label = new Label(match.matchHeader());
            matchesPane.getChildren().add(label);
        }
        root.getChildren().add(matchesPane);
    }

}
