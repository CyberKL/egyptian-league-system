package edu.ainshams.egyptianleaguesystem.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TextDialogController {
    @FXML
    private TextField idTextField;
    private Stage dialogStage;
    private String enteredId;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
    }

    public void setDialogTitle(String title){
        dialogStage.setTitle(title);
    }

    public String getEnteredId() {
        return enteredId;
    }

    @FXML
    private void handleOKButton(ActionEvent event) {
        enteredId = idTextField.getText();
        dialogStage.close();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        enteredId = null;
        dialogStage.close();
    }
}
