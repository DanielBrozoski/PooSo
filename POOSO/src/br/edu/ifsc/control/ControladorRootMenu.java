package br.edu.ifsc.control;

import br.edu.ifsc.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControladorRootMenu {

    @FXML
    private TextField txtTamanhoBuffer;

    @FXML
    private TextField txtQtdProdutoresConsumidores;

    private Main main;

    public void iniciar(Main main) {
        this.main = main;
    }

    @FXML
    void iniciarExecucao() {
        if (validaDados()) {
            this.main.iniciarRootInterfaceChart(Integer.parseInt(this.txtTamanhoBuffer.getText()));
            this.main.iniciarRootInterface();
            this.main.iniciarExecucao(Integer.parseInt(this.txtTamanhoBuffer.getText()),
                Integer.parseInt(this.txtQtdProdutoresConsumidores.getText()));
        }
    }

    public boolean validaDados() {
        int aux1, aux2;
        try{
            aux1 = Integer.parseInt(this.txtTamanhoBuffer.getText());
            aux2 = Integer.parseInt(this.txtQtdProdutoresConsumidores.getText());
        } catch(NumberFormatException e) {
            return false;
        }
        if (aux1 <= 0 || aux2 <= 0) {
            return false;
        }
        return true;
    }
}
