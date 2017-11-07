package br.edu.ifsc.control;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class ControladorRootLayout {
    
    @FXML
    private BorderPane borderPane;
    
    public void colocarEmCima(Node node) {
        this.borderPane.setTop(node);
    }
    
    public void colocarNaDireita(Node node) {
        this.borderPane.setRight(node);
    }
    
    public void colocarNoCentro(Node node) {
        this.borderPane.setCenter(node);
    }
    
    public void limparNodes() {
        this.borderPane.getChildren().clear();
    }
}
