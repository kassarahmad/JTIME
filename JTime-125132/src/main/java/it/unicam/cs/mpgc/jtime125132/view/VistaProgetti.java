package it.unicam.cs.mpgc.jtime125132.view;

import it.unicam.cs.mpgc.jtime125132.controller.ControllerProgetti;
import it.unicam.cs.mpgc.jtime125132.model.Progetto;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.Consumer;

public final class VistaProgetti {

    private final ControllerProgetti ctrl;
    private final Consumer<String> status;
    private final BorderPane root = new BorderPane();

    private final ListView<Progetto> lista = new ListView<>();

    public VistaProgetti(ControllerProgetti ctrl, Consumer<String> status) {
        this.ctrl = ctrl;
        this.status = status;
    }

    public Node creaVista() {
        root.getStyleClass().add("card");
        root.setPadding(new Insets(12));

        // Form crea progetto
        TextField nome = new TextField();
        nome.setPromptText("Nome progetto…");

        Button crea = new Button("Crea");
        crea.getStyleClass().add("btn-primary");

        HBox top = new HBox(10, new Label("Nuovo progetto:"), nome, crea);
        root.setTop(top);
        BorderPane.setMargin(top, new Insets(0,0,12,0));

        // Lista + azioni
        lista.setPrefWidth(420);
        lista.setPlaceholder(new Label("Nessun progetto. Creane uno sopra."));
        root.setLeft(lista);
        BorderPane.setMargin(lista, new Insets(0,12,0,0));

        VBox actions = new VBox(10);
        TextField rinomina = new TextField();
        rinomina.setPromptText("Nuovo nome…");

        Button bRinomina = new Button("Rinomina");
        bRinomina.getStyleClass().add("btn-primary");

        Button bChiudi = new Button("Chiudi progetto");
        bChiudi.getStyleClass().add("btn-primary");

        Button bElimina = new Button("Elimina progetto");
        bElimina.getStyleClass().add("btn-danger");

        actions.getChildren().addAll(new Label("Azioni sul progetto selezionato:"), rinomina, bRinomina, bChiudi, new Separator(), bElimina);
        root.setCenter(actions);

        crea.setOnAction(e -> {
            try {
                ctrl.creaProgetto(nome.getText());
                nome.clear();
                refresh();
                status.accept("Progetto creato.");
            } catch (Exception ex) { alertErr(ex.getMessage()); }
        });

        bRinomina.setOnAction(e -> {
            Progetto p = lista.getSelectionModel().getSelectedItem();
            if (p == null) return;
            try {
                ctrl.rinomina(p.id(), rinomina.getText());
                rinomina.clear();
                refresh();
                status.accept("Progetto rinominato.");
            } catch (Exception ex) { alertErr(ex.getMessage()); }
        });

        bChiudi.setOnAction(e -> {
            Progetto p = lista.getSelectionModel().getSelectedItem();
            if (p == null) return;
            try {
                ctrl.chiudi(p.id());
                refresh();
                status.accept("Progetto chiuso.");
            } catch (Exception ex) { alertErr(ex.getMessage()); }
        });

        bElimina.setOnAction(e -> {
            Progetto p = lista.getSelectionModel().getSelectedItem();
            if (p == null) return;

            Alert c = new Alert(Alert.AlertType.CONFIRMATION);
            c.setTitle("Conferma eliminazione");
            c.setHeaderText("Eliminare il progetto?");
            c.setContentText("Verranno eliminate anche le attività collegate e le pianificazioni.");
            if (c.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

            try {
                ctrl.eliminaProgetto(p.id());
                refresh();
                status.accept("Progetto eliminato.");
            } catch (Exception ex) { alertErr(ex.getMessage()); }
        });

        refresh();
        return root;
    }

    public Region getRoot() { return root; }

    public void refresh() {
        lista.setItems(FXCollections.observableArrayList(ctrl.lista()));
    }

    private void alertErr(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}