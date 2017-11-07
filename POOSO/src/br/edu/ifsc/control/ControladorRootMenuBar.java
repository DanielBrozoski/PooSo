package br.edu.ifsc.control;

import br.edu.ifsc.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ControladorRootMenuBar {

    private Main main;

    public void iniciar(Main main) {
        this.main = main;
    }

    @FXML
    void iniciarNovo() {
        this.main.reiniciar();
    }

    @FXML
    void sair() {
        this.main.interromperExecucao();
        System.exit(0);
    }

    @FXML
    void sobre() {
        Alert alert = new Alert(AlertType.INFORMATION);
        
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/images/icone-64.png"));

        ImageView imageView = new ImageView();
        imageView.setImage(new Image("file:resources/images/icone-128.png"));
        alert.setGraphic(imageView);

        alert.setTitle("O problema...");
        alert.setHeaderText(
            "Trabalho de POO II e SO\n\n" + "Desenvolvido por: Daniel L. Brozoski e Vinicius V. Santos");
        alert.setContentText(
            "O problema clássico do produtor/consumidor consiste de um processo que produz (escreve) dados para serem consumidos (lidos) por outro processo. "
            + "Os dados são armazenados temporariamente num buffer de tamanho limitado (variável) enquanto esperam para serem utilizados. "
            + "De acordo com o problema, o produtor nunca pode escrever dados no buffer se este estiver cheio e o consumidor nunca pode ler dados deste buffer se este estiver vazio. "
            + "As operações de escrita e leitura concorrentes neste buffer podem gerar impasses (produtor sobrescrever valores, leitor nunca conseguir ler valores, etc). "
            + "Diante deste problema, mecanismos de sincronização devem ser implementados para manipulação do buffer.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

//    Talvez exista:
//    @FXML
//    void visualizarLog() {
//          Alert alert = new Alert(AlertType.INFORMATION);
//
//          ImageView imageView = new ImageView();
//          imageView.setImage(new Image("file:resources/images/icon3.png"));
//          alert.setGraphic(imageView);
//
//          alert.setTitle("O problema...");
//          alert.setHeaderText();
//          alert.setContentText(contentText);
//    }
}
