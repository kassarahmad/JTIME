package it.unicam.cs.mpgc.jtime125132.view;

import it.unicam.cs.mpgc.jtime125132.controller.ControllerAttivita;
import it.unicam.cs.mpgc.jtime125132.controller.ControllerPianificazione;
import it.unicam.cs.mpgc.jtime125132.model.Attivita;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.function.Consumer;

public final class VistaPianificazione {

    private final ControllerAttivita ctrlAttivita;
    private final ControllerPianificazione ctrlPian;
    private final Consumer<String> status;
    private final BorderPane root = new BorderPane();

    private final ListView<Attivita> listaNonComp = new ListView<>();
    private final ListView<Attivita> listaData = new ListView<>();
    private final DatePicker data = new DatePicker(LocalDate.now());

    public VistaPianificazione(ControllerAttivita ctrlAttivita, ControllerPianificazione ctrlPian, Consumer<String> status) {
        this.ctrlAttivita = ctrlAttivita;
        this.ctrlPian = ctrlPian;
        this.status = status;
    }

    public Node creaVista() {
        root.getStyleClass().add("card");
        root.setPadding(new Insets(12));

        Button pianifica = new Button("Pianifica →");
        pianifica.getStyleClass().add("btn-primary");

        Button rimuovi = new Button("← Rimuovi");
        rimuovi.getStyleClass().add("btn-danger");

        HBox top = new HBox(10, new Label("Data:"), data, pianifica, rimuovi);
        root.setTop(top);
        BorderPane.setMargin(top, new Insets(0,0,12,0));

        VBox left = new VBox(8, new Label("Attività non completate"), listaNonComp);
        VBox right = new VBox(8, new Label("Pianificate per la data"), listaData);

        SplitPane split = new SplitPane(left, right);
        split.setDividerPositions(0.5);
        root.setCenter(split);

        pianifica.setOnAction(e -> {
            Attivita a = listaNonComp.getSelectionModel().getSelectedItem();
            if (a == null) return;
            try {
                ctrlPian.pianifica(data.getValue(), a.id());
                refresh();
                status.accept("Pianificata: " + a.titolo());
            } catch (Exception ex) { alertErr(ex.getMessage()); }
        });

        rimuovi.setOnAction(e -> {
            Attivita a = listaData.getSelectionModel().getSelectedItem();
            if (a == null) return;
            try {
                ctrlPian.rimuovi(data.getValue(), a.id());
                refresh();
                status.accept("Rimossa pianificazione: " + a.titolo());
            } catch (Exception ex) { alertErr(ex.getMessage()); }
        });

        data.setOnAction(e -> refresh());

        refresh();
        return root;
    }

    public Region getRoot() { return root; }

    public void refresh() {
        var nonCompletate = ctrlAttivita.listaTutte().stream().filter(a -> !a.completata()).toList();
        listaNonComp.setItems(FXCollections.observableArrayList(nonCompletate));
        listaData.setItems(FXCollections.observableArrayList(ctrlPian.pianificate(data.getValue())));
    }

    private void alertErr(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}