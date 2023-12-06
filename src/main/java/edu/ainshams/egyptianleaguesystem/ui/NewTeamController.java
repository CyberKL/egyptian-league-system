package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class NewTeamController {

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    private Stage stage;
    @FXML
    private Button backBtn;
    @FXML
    private Button createBtn;
    @FXML
    private TextField teamName;
    @FXML
    private TextField teamId;
    @FXML
    private Label deleteLabel;
    @FXML
    private TextField deleteText;
    @FXML
    private Button deleteBtn;

    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }

    public void blueBack(MouseEvent event){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }

    public void switchTeamMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamsMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    public void createTeam(ActionEvent event){
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

    public void deleteTeam(ActionEvent event){
        boolean teamFound = false;
        int id = Integer.parseInt(deleteText.getText());
        for (Team team : Logic.getTeams()){
            if (team.getTeamId()==id){
                teamFound = true;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete "+team.getName());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    System.out.println("OK");
                    Logic.removeTeam(team);
                    Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                    deleteAlert.setContentText(team.getName()+" deleted successfully!");
                    deleteAlert.show();
                    break;
                }
            }
        }
        if (!teamFound){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Team not found!");
            alert.show();
        }
        System.out.println("Available teams:");
        Logic.displayTotalTeams();
    }

}
