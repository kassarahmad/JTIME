package it.unicam.cs.mpgc.jtime125132.view;

import it.unicam.cs.mpgc.jtime125132.controller.ControllerProgetti;
import it.unicam.cs.mpgc.jtime125132.controller.ControllerReport;
import it.unicam.cs.mpgc.jtime125132.model.IntervalloTemporale;
import it.unicam.cs.mpgc.jtime125132.model.Progetto;
import it.unicam.cs.mpgc.jtime125132.model.Report;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Consumer;

public final class VistaReport {

    private final ControllerProgetti ctrlProgetti;
    private final ControllerReport ctrlReport;
    private final Consumer<String> status;
    private final BorderPane root = new BorderPane();

    private final ComboBox<Progetto> combo = new ComboBox<>();
    private final TextArea area = new TextArea();

    private final DatePicker da = new DatePicker(LocalDate.now().minusDays(7));
    private final DatePicker a  = new DatePicker(LocalDate.now());

    public VistaReport(ControllerProgetti ctrlProgetti, ControllerReport ctrlReport, Consumer<String> status) {
        this.ctrlProgetti = ctrlProgetti;
        this.ctrlReport = ctrlReport;
        this.status = status;
    }

    public Node creaVista() {
        root.getStyleClass().add("card");
        root.setPadding(new Insets(12));

        combo.setPrefWidth(360);

        Button repProj = new Button("Report progetto");
        repProj.getStyleClass().add("btn-primary");

        Button repPian = new Button("Report intervallo");
        repPian.getStyleClass().add("btn-primary");

        Button export = new Button("Export TXT");
        export.getStyleClass().add("btn-primary");

        HBox r1 = new HBox(10, new Label("Progetto:"), combo, repProj, export);
        HBox r2 = new HBox(10, new Label("Da:"), da, new Label("A:"), a, repPian);

        VBox top = new VBox(10, r1, r2);
        root.setTop(top);
        BorderPane.setMargin(top, new Insets(0, 0, 12, 0));

        area.setEditable(false);
        area.setWrapText(true);
        root.setCenter(area);

        repProj.setOnAction(e -> {
            Progetto p = combo.getSelectionModel().getSelectedItem();
            if (p == null) return;
            try {
                Report r = ctrlReport.reportProgetto(p.id());
                area.setText(r.contenuto());
                status.accept("Report progetto generato.");
            } catch (Exception ex) {
                alertErr(ex.getMessage());
            }
        });

        repPian.setOnAction(e -> {
            try {
                Report r = ctrlReport.reportPianificazione(new IntervalloTemporale(da.getValue(), a.getValue()));
                area.setText(r.contenuto());
                status.accept("Report intervallo generato.");
            } catch (Exception ex) {
                alertErr(ex.getMessage());
            }
        });

        export.setOnAction(e -> {
            try {
                String txt = area.getText();
                if (txt == null || txt.isBlank()) {
                    alertErr("Niente da esportare. Genera prima un report.");
                    return;
                }
                Path out = Path.of(System.getProperty("user.home"), ".jtime125132", "report.txt");
                Files.createDirectories(out.getParent());
                Files.writeString(out, txt);

                status.accept("Export completato: " + out);
                new Alert(Alert.AlertType.INFORMATION, "Salvato in: " + out, ButtonType.OK).showAndWait();
            } catch (Exception ex) {
                alertErr(ex.getMessage());
            }
        });

        refresh();
        return root;
    }

    public Region getRoot() { return root; }

    public void refresh() {
        combo.setItems(FXCollections.observableArrayList(ctrlProgetti.lista()));
        if (combo.getSelectionModel().isEmpty() && !combo.getItems().isEmpty())
            combo.getSelectionModel().select(0);

        if (combo.getItems().isEmpty())
            area.setText("Crea almeno un progetto per generare report.");
    }

    private void alertErr(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}