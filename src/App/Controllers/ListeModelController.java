package App.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import java.io.File;

public class ListeModelController {
    @FXML
    public ListView<String> indice;
    @FXML
    private ListView<String> indiceS;

    @FXML
    public Button sup;

    public void initialize() {
        indice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sup.setDisable(true);

        indice.setOnMouseClicked(event -> {
            String selectedModel = indice.getSelectionModel().getSelectedItem();
            if (selectedModel != null) {
                indiceS.getItems().add(selectedModel);
                indice.getItems().remove(selectedModel);
                sup.setDisable(false); // Active le bouton "supprimer"
            }
        });

        String dir = "resources/models";
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                indice.getItems().add(f.getName());
            }
        }
    }

    public void SupprimerModels(ActionEvent event) {
        // Supprime tous les éléments de la liste indiceS
        indiceS.getItems().forEach(item -> {
            File file = new File("resources/models/" + item);
            file.delete();
        });
        indiceS.getItems().clear();
        // Désactive le bouton "supprimer"
        sup.setDisable(true);
    }
}
