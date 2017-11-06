package br.edu.ifsc.view;

import br.edu.ifsc.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControladorRootInterface {

    @FXML
    private TextArea txtConsolePrint;

    private int contadorLinhas;
    private Main main;

    public void iniciar(Main main) {
        this.contadorLinhas = 0;
        this.main = main;
    }

    public void printarProducao(String texto) {
        if (this.contadorLinhas < 100) {
            this.txtConsolePrint.appendText(texto);
        } else {
            this.txtConsolePrint.setText(texto);
            this.contadorLinhas = 0;
        }
        this.contadorLinhas++;
    }
}
