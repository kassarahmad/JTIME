package it.unicam.cs.mpgc.jtime125132.view;

import it.unicam.cs.mpgc.jtime125132.controller.ControllerAttivita;
import it.unicam.cs.mpgc.jtime125132.controller.ControllerProgetti;
import it.unicam.cs.mpgc.jtime125132.model.Attivita;
import it.unicam.cs.mpgc.jtime125132.model.Progetto;
import it.unicam.cs.mpgc.jtime125132.util.GestoreDate;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.Consumer;

public final class VistaAttivita {

    private final ControllerProgetti ctrlProgetti;
    private final ControllerAttivita ctrlAttivita;
    private final Consumer<String> status;
    private final BorderPane root = new BorderPane();

    private final ComboBox<Progetto> comboProgetti = new ComboBox<>();
    private final TableView<Attivita> tabella = new TableView<>();

    public VistaAttivita(ControllerProgetti ctrlProgetti, ControllerAttivita ctrlAttivita, Consumer<String> status) {
        this.ctrlProgetti = ctrlProgetti;
        this.ctrlAttivita = ctrlAttivita;
        this.status = status;
    }

    public Node creaVista() {
        root.getStyleClass().add("card");
        root.setPadding(new Insets(12));

        // ---- FORM (GridPane: più bello e responsive) ----
        comboProgetti.setPrefWidth(320);

        TextField titolo = new TextField();
        titolo.setPromptText("Titolo attività…");

        TextField descr = new TextField();
        descr.setPromptText("Descrizione (opzionale)…");

        Spinner<Integer> stimati = new Spinner<>(1, 24 * 60, 60, 15);
        stimati.setEditable(true);

        Button crea = new Button("Aggiungi attività");
        crea.getStyleClass().add("btn-primary");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(90);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setMinWidth(110);

        form.getColumnConstraints().addAll(c1, c2, c3);

        form.add(new Label("Progetto:"), 0, 0);
        form.add(comboProgetti, 1, 0, 2, 1);

        form.add(new Label("Titolo:"), 0, 1);
        form.add(titolo, 1, 1, 2, 1);

        form.add(new Label("Descrizione:"), 0, 2);
        form.add(descr, 1, 2, 2, 1);

        form.add(new Label("Min stimati:"), 0, 3);
        form.add(stimati, 1, 3);
        form.add(crea, 2, 3);

        root.setTop(form);
        BorderPane.setMargin(form, new Insets(0, 0, 12, 0));

        // ---- TABELLA ----
        TableColumn<Attivita, String> cTitolo = new TableColumn<>("Titolo");
        cTitolo.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().titolo()));
        cTitolo.setPrefWidth(240);

        TableColumn<Attivita, String> cStato = new TableColumn<>("Stato");
        cStato.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().stato().toString()));
        cStato.setPrefWidth(110);

        TableColumn<Attivita, String> cStima = new TableColumn<>("Stimata");
        cStima.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(
                GestoreDate.format(v.getValue().stima().stimata())
        ));
        cStima.setPrefWidth(120);

        TableColumn<Attivita, String> cEff = new TableColumn<>("Effettiva");
        cEff.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(
                v.getValue().stima().effettiva() == null ? "-" : GestoreDate.format(v.getValue().stima().effettiva())
        ));
        cEff.setPrefWidth(120);

        tabella.getColumns().setAll(cTitolo, cStato, cStima, cEff);
        tabella.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        root.setCenter(tabella);

        // ---- AZIONI (completa/elimina) ----
        Spinner<Integer> eff = new Spinner<>(1, 24 * 60, 60, 15);
        eff.setEditable(true);

        Button completa = new Button("Completa");
        completa.getStyleClass().add("btn-primary");

        Button elimina = new Button("Elimina attività");
        elimina.getStyleClass().add("btn-danger");

        HBox bottom = new HBox(10, new Label("Min effettivi:"), eff, completa, new Separator(), elimina);
        bottom.setPadding(new Insets(12, 0, 0, 0));
        root.setBottom(bottom);

        // ---- HANDLERS ----
        crea.setOnAction(e -> {
            Progetto p = comboProgetti.getSelectionModel().getSelectedItem();
            if (p == null) { alertErr("Seleziona un progetto."); return; }
            try {
                ctrlAttivita.creaAttivita(p.id(), titolo.getText(), descr.getText(), stimati.getValue());
                titolo.clear();
                descr.clear();
                refresh();
                status.accept("Attività creata.");
            } catch (Exception ex) {
                alertErr(ex.getMessage());
            }
        });

        completa.setOnAction(e -> {
            Attivita aSel = tabella.getSelectionModel().getSelectedItem();
            if (aSel == null) return;
            try {
                ctrlAttivita.completa(aSel.id(), eff.getValue());
                refresh();
                status.accept("Attività completata.");
            } catch (Exception ex) {
                alertErr(ex.getMessage());
            }
        });

        elimina.setOnAction(e -> {
            Attivita aSel = tabella.getSelectionModel().getSelectedItem();
            if (aSel == null) return;

            Alert c = new Alert(Alert.AlertType.CONFIRMATION, "Eliminare l'attività selezionata?", ButtonType.OK, ButtonType.CANCEL);
            if (c.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

            try {
                ctrlAttivita.elimina(aSel.id());
                refresh();
                status.accept("Attività eliminata.");
            } catch (Exception ex) {
                alertErr(ex.getMessage());
            }
        });

        comboProgetti.setOnAction(e -> refresh());

        refresh();
        return root;
    }

    public Region getRoot() { return root; }

    public void refresh() {
        comboProgetti.setItems(FXCollections.observableArrayList(ctrlProgetti.lista()));
        if (comboProgetti.getSelectionModel().isEmpty() && !comboProgetti.getItems().isEmpty())
            comboProgetti.getSelectionModel().select(0);

        Progetto p = comboProgetti.getSelectionModel().getSelectedItem();
        tabella.setItems(FXCollections.observableArrayList(
                p == null ? java.util.List.of() : ctrlAttivita.perProgetto(p.id())
        ));
    }

    private void alertErr(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}