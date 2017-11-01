package br.edu.ifsc.exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class ErroCarregamentoInterfaceException {

	public void alertarErro(String texto) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Produtor - Consumidor");
		alert.setHeaderText("Erro no carregamento da interface.");
		alert.setContentText(texto);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
		
		System.exit(1);
	}
}
