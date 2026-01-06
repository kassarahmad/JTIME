package it.unicam.cs.mpgc.jtime125132.view;

import it.unicam.cs.mpgc.jtime125132.controller.*;
import it.unicam.cs.mpgc.jtime125132.persistence.*;
import it.unicam.cs.mpgc.jtime125132.service.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

public final class VistaPrincipale {

    private final GestorePersistenza persistenza = new GestorePersistenza();
    private final ArchivioDati archivio = persistenza.archivio();

    private final RepositoryProgetti repoProgetti = new RepositoryProgetti(archivio, persistenza);
    private final RepositoryAttivita repoAttivita = new RepositoryAttivita(archivio, persistenza);

    private final ServizioTracciamentoTempo srvTempo = new ServizioTracciamentoTempo();
    private final ServizioPianificazione srvPian = new ServizioPianificazione(archivio.pianificazione(), repoAttivita);
    private final ServizioReportistica srvReport = new ServizioReportistica(archivio);

    private final ControllerProgetti ctrlProgetti = new ControllerProgetti(archivio, repoProgetti, repoAttivita, srvPian);
    private final ControllerAttivita ctrlAttivita = new ControllerAttivita(repoProgetti, repoAttivita, srvTempo);
    private final ControllerPianificazione ctrlPian = new ControllerPianificazione(srvPian, repoAttivita);
    private final ControllerReport ctrlReport = new ControllerReport(srvReport);

    private final Label status = new Label("Pronto.");

    public Parent creaVista() {
        BorderPane root = new BorderPane();
        root.getStyleClass().addAll("app-root");
        root.setPadding(new Insets(14));

        // Header
        HBox header = new HBox(12);
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/it/unicam/cs/mpgc/jtime125132/view/images/logo-clock.png")
        )));
        logo.setFitWidth(30); logo.setFitHeight(30); logo.setPreserveRatio(true);

        VBox titles = new VBox(2);
        Label t = new Label("JTime • Gestione del Tempo");
        t.getStyleClass().add("h-title");
        Label s = new Label("Progetti, attività, pianificazione e report — tutto in un'unica app.");
        s.getStyleClass().add("h-sub");
        titles.getChildren().addAll(t, s);

        ImageView hero = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/it/unicam/cs/mpgc/jtime125132/view/images/hero-clock.gif")
        )));
        hero.setFitWidth(46); hero.setFitHeight(46); hero.setPreserveRatio(true);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(logo, titles, spacer, hero);

        // Sidebar
        VBox nav = new VBox(10);
        nav.getStyleClass().add("sidebar");
        nav.setPrefWidth(230);

        Button bProgetti = navBtn("Progetti", "/it/unicam/cs/mpgc/jtime125132/view/images/logo-clock.png");
        Button bAttivita = navBtn("Attività", "/it/unicam/cs/mpgc/jtime125132/view/images/logo-hourglass.png");
        Button bPian     = navBtn("Pianificazione", "/it/unicam/cs/mpgc/jtime125132/view/images/logo-clock.png");
        Button bReport   = navBtn("Report", "/it/unicam/cs/mpgc/jtime125132/view/images/logo-hourglass.png");

        nav.getChildren().addAll(bProgetti, bAttivita, bPian, bReport);

        // Center views
        StackPane center = new StackPane();
        center.setPadding(new Insets(0, 0, 0, 12));

        VistaProgetti vProgetti = new VistaProgetti(ctrlProgetti, this::setStatus);
        VistaAttivita vAttivita = new VistaAttivita(ctrlProgetti, ctrlAttivita, this::setStatus);
        VistaPianificazione vPian = new VistaPianificazione(ctrlAttivita, ctrlPian, this::setStatus);
        VistaReport vReport = new VistaReport(ctrlProgetti, ctrlReport, this::setStatus);

        center.getChildren().addAll(vProgetti.creaVista(), vAttivita.creaVista(), vPian.creaVista(), vReport.creaVista());
        vAttivita.getRoot().setVisible(false);
        vPian.getRoot().setVisible(false);
        vReport.getRoot().setVisible(false);

        bProgetti.setOnAction(e -> { showOnly(vProgetti.getRoot(), center); vProgetti.refresh(); setStatus("Sezione Progetti"); });
        bAttivita.setOnAction(e -> { showOnly(vAttivita.getRoot(), center); vAttivita.refresh(); setStatus("Sezione Attività"); });
        bPian.setOnAction(e -> { showOnly(vPian.getRoot(), center); vPian.refresh(); setStatus("Sezione Pianificazione"); });
        bReport.setOnAction(e -> { showOnly(vReport.getRoot(), center); vReport.refresh(); setStatus("Sezione Report"); });

        // Status bar
        HBox statusBar = new HBox(status);
        statusBar.getStyleClass().add("statusbar");
        statusBar.setPadding(new Insets(10, 12, 10, 12));

        root.setTop(header);
        BorderPane.setMargin(header, new Insets(0, 0, 12, 0));
        root.setLeft(nav);
        root.setCenter(center);
        root.setBottom(statusBar);
        BorderPane.setMargin(statusBar, new Insets(12, 0, 0, 0));

        return root;
    }

    private void showOnly(Region r, StackPane stack) {
        for (var n : stack.getChildren()) n.setVisible(false);
        r.setVisible(true);
    }

    private Button navBtn(String text, String iconPath) {
        Button b = new Button(text);
        b.getStyleClass().add("nav-btn");
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath))));
        iv.setFitWidth(18); iv.setFitHeight(18); iv.setPreserveRatio(true);
        b.setGraphic(iv);
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    private void setStatus(String s) { status.setText(s); }
}