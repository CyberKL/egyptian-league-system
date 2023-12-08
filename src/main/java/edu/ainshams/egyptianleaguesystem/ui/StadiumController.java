package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Match;
import edu.ainshams.egyptianleaguesystem.model.Player;
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
import java.util.Optional;
import java.util.concurrent.Flow;

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
    private TextField deleteField;
    @FXML
    private VBox nameBox;
    @FXML
    private TextField nameLookUpField;
    @FXML
    private GridPane infoGrid;
    @FXML
    private AnchorPane root;

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
    public void blueBack(MouseEvent event){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }
    private void removeNodeById(AnchorPane container, String nodeId) {
        container.getChildren().stream()
                .filter(node -> node.getId() != null && node.getId().equals(nodeId))
                .findFirst()
                .ifPresent(node -> node.setVisible(false));
    }

    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");
    Alert stadiumNotFoundAlert = new Alert(Alert.AlertType.WARNING, "Stadium not found!");
    public void createStadium(){
        if (nameField.getText().isBlank() || capacityField.getText().isBlank() || cityField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            boolean duplicateStadium = false;
            String name = nameField.getText();
            int capacity = Integer.parseInt(capacityField.getText());
            String city = cityField.getText();
            for (Stadium stadium : Logic.getStadiums()){
                if (stadium.getName().equalsIgnoreCase(name)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("There is already a stadium with this name!");
                    alert.show();
                    duplicateStadium = true;
                    break;
                }
            }
            if (!duplicateStadium){
                Stadium stadium = new Stadium(name, capacity, city);
                Logic.addStadium(stadium);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Stadium created!");
                alert.show();
            }
            System.out.println(Logic.getStadiums().getFirst().getName());
        }
    }

    public void deleteStadium(){
        if (deleteField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            String name = deleteField.getText();
            boolean stadiumFound = false;
            for (Stadium stadium : Logic.getStadiums()){
                if (stadium.getName().equalsIgnoreCase(name)){
                    stadiumFound = true;
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete "+stadium.getName()+"?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get()==ButtonType.OK){
                        Logic.removeStadium(stadium);
                        Alert success = new Alert(Alert.AlertType.INFORMATION, stadium.getName()+" deleted successfully!");
                        success.show();
                        stadiumFound = true;
                        break;
                    }
                }
            }
            if (!stadiumFound){
                stadiumNotFoundAlert.show();
            }
            System.out.println(Logic.getStadiums().getFirst().getName());
        }

    }

    public void displayStadiumInfo(){
        boolean stadiumFound = false;
        Stadium currentStadium;
        if (nameLookUpField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            String stadiumName = nameLookUpField.getText();
            for (Stadium stadium : Logic.getStadiums()){
                if (stadium.getName().equalsIgnoreCase(stadiumName)){
                    stadiumFound = true;
                    currentStadium = stadium;
                    removeNodeById(root, "nameBox");
                    stadiumInfo(currentStadium);
                    break;
                }
            }
            if (!stadiumFound){
                stadiumNotFoundAlert.show();
            }
        }
    }
    private void stadiumInfo(Stadium stadium){
        Label stadiumName = new Label(stadium.getName());
        Label stadiumCity = new Label(stadium.getCity());
        Label stadiumCapacity = new Label(Integer.toString(stadium.getCapacity()));
        Label stadiumNumOfMatches = new Label(Integer.toString(stadium.getMatchesPlayedOn()));
        VBox infoList = new VBox(stadiumName, stadiumCity, stadiumCapacity, stadiumNumOfMatches);
        infoGrid.add(infoList, 1, 0);
        infoGrid.setVisible(true);

    }

    public  void displayStadiumUpcomingMatches(){
        boolean stadiumFound = false;
        Stadium currentStadium;
        if (nameLookUpField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            String stadiumName = nameLookUpField.getText();
            for (Stadium stadium : Logic.getStadiums()){
                if (stadium.getName().equalsIgnoreCase(stadiumName)){
                    stadiumFound = true;
                    currentStadium = stadium;
                    removeNodeById(root, "nameBox");
                    stadiumUpcomingMatches(currentStadium);
                    break;
                }
            }
            if (!stadiumFound){
                stadiumNotFoundAlert.show();
            }
        }
    }
    private void stadiumUpcomingMatches(Stadium stadium){
        FlowPane matchesPane = new FlowPane();
        for (Match match : stadium.getUpcomingMatches()){
            Label label = new Label(match.matchHeader());
            matchesPane.getChildren().add(label);
        }
        root.getChildren().add(matchesPane);
    }

}
