package br.edu.ifsc.view;

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
		this.main.iniciarRootInterfaceChart(Integer.parseInt(this.txtTamanhoBuffer.getText()));
		this.main.iniciarRootInterface();
		this.main.iniciarExecucao(Integer.parseInt(this.txtTamanhoBuffer.getText()),
				Integer.parseInt(this.txtQtdProdutoresConsumidores.getText()));
	}
        
        /*public boolean validarDados*/
}
