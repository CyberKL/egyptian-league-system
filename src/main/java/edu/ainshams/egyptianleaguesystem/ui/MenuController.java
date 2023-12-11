package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Manager;
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
    public void switchToNewTeam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newTeam.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToDeleteTeam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("deleteTeam.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
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
    public void switchToNewStadium(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newStadium.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToDeleteStadium(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("deleteStadium.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
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
    public void switchToNewMatch(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newMatch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void switchToDeleteMatch(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("deleteMatch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TextDialog.fxml"));
        Parent root = loader.load();

        // Access the controller and set the stage for the dialog
        TextDialogController dialogController = loader.getController();
        Stage dialogStage = new Stage();
        dialogController.setDialogStage(dialogStage);

        // Show the text dialog and wait for it to be closed
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();

        // Check if the user entered a valid ID
        String enteredId = dialogController.getEnteredId();
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
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                alert.show();
            }
        }
        // If the user canceled or closed the dialog, do nothing
    }

    public void switchToEditManager(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TextDialog.fxml"));
        Parent root = loader.load();

        // Access the controller and set the stage for the dialog
        TextDialogController dialogController = loader.getController();
        Stage dialogStage = new Stage();
        dialogController.setDialogStage(dialogStage);

        // Show the text dialog and wait for it to be closed
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();

        // Check if the user entered a valid ID
        String enteredId = dialogController.getEnteredId();
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
        // If the user canceled or closed the dialog, do nothing
    }


    public void deleteManager (){
        boolean found = false;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Manager");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the Manager ID:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            int id = result.map(Integer::parseInt).orElse(0);
            for (Manager manager : Logic.getManagers()) {
                if (manager.getManagerId() == id) {
                    found = true;
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + manager.getName());
                    Optional<ButtonType> choice = alert.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        Logic.removeManager(manager);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Manager deleted successfully!");
                        success.showAndWait();
                    }
                    break;
                }
            }
            if (!found) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                alert.show();
            }
        }
    }

}
