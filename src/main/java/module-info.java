module edu.ainshams.egyptianleaguesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens edu.ainshams.egyptianleaguesystem.ui to javafx.fxml;
    opens edu.ainshams.egyptianleaguesystem.model to javafx.fxml;
    exports edu.ainshams.egyptianleaguesystem.ui;
    exports edu.ainshams.egyptianleaguesystem.model;
}