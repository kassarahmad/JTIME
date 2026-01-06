package it.unicam.cs.mpgc.jtime125132.app;

import it.unicam.cs.mpgc.jtime125132.view.VistaPrincipale;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public final class ApplicazioneJTime extends Application {

    @Override
    public void start(Stage stage) {
        VistaPrincipale vista = new VistaPrincipale();
        Scene scene = new Scene((javafx.scene.Parent) vista.creaVista(), 1180, 760);

        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/it/unicam/cs/mpgc/jtime125132/view/app.css")
        ).toExternalForm());

        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/it/unicam/cs/mpgc/jtime125132/view/images/logo-clock.png")
        )));
        stage.setTitle("JTime 125132 • Gestione del Tempo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}