module mtx.dev.tp1javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens mtx.dev.tp1javafx to javafx.fxml;
    exports mtx.dev.tp1javafx;
}