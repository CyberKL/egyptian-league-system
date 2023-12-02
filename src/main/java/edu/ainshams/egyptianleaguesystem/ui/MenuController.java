package edu.ainshams.egyptianleaguesystem.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML
    private Button quitBtn;
    @FXML
    private Button teamBtn;
    @FXML
    private Button matchBtn;
    @FXML
    private Button playerBtn;
    @FXML
    private Button mngrBtn;
    @FXML
    private Button refBtn;
    @FXML
    private Button stadBtn;

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

}
