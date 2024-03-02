module gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens gui to javafx.fxml;
    exports gui;

    opens controller to javafx.fxml;
    exports controller;

    opens exceptions to javafx.fxml;
    exports exceptions;

    opens model to javafx.fxml;
    exports model;

    opens repository to javafx.fxml;
    exports repository;

    opens model.adt to javafx.fxml;
    exports model.adt;

    opens model.expressions to javafx.fxml;
    exports model.expressions;

    opens model.statements to javafx.fxml;
    exports model.statements;

    opens model.types to javafx.fxml;
    exports model.types;

    opens model.values to javafx.fxml;
    exports model.values;
}