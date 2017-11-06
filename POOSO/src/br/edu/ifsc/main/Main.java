package br.edu.ifsc.main;

import java.io.IOException;

import br.edu.ifsc.exception.ErroCarregamentoInterfaceException;
import br.edu.ifsc.model.AddToQueue;
import br.edu.ifsc.model.Buffer;
import br.edu.ifsc.model.Consumidor;
import br.edu.ifsc.model.Impressora;
import br.edu.ifsc.model.Produtor;
import br.edu.ifsc.view.ControladorRootMenu;
import br.edu.ifsc.view.ControladorRootInterface;
import br.edu.ifsc.view.ControladorRootInterfaceChart;
import br.edu.ifsc.view.ControladorRootLayout;
import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    private BorderPane rootLayout;
    private ControladorRootInterface controladorRootInterface;
    private ControladorRootInterfaceChart controladorRootInterfaceChart;

    private Thread[] threads;
    private Buffer buffer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Produtor / Consumidor");
        primaryStage.getIcons().add(new Image("file:resources/images/icon4.png"));

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.runLater(() -> {
                interromperExecucao();
                System.exit(0);
            });
        });

        iniciarRootLayout(primaryStage);
        iniciarRootMenu();
    }

    public void iniciarRootLayout(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootLayout.fxml").toURI().toURL());//Main.class.getResource("../view/RootLayout.fxml")
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
        }

        try {
            this.rootLayout = (BorderPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }

        Scene scene = new Scene(this.rootLayout);
        primaryStage.setScene(scene);

        ControladorRootLayout controladorRootLayout = loader.getController();
        controladorRootLayout.iniciar(this);
        primaryStage.show();
    }

    public void iniciarRootMenu() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootMenu.fxml").toURI().toURL());//Main.class.getResource("../view/RootMenu.fxml")
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
        }
        AnchorPane anchorPane = null;
        try {
            anchorPane = (AnchorPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }

        this.rootLayout.setCenter(anchorPane);

        ControladorRootMenu controladorRootMenu = loader.getController();
        controladorRootMenu.iniciar(this);
    }

    public void iniciarRootInterface() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootInterface.fxml").toURI().toURL());//Main.class.getResource("../view/RootInterface.fxml")
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
        }
        AnchorPane anchorPane = null;
        try {
            anchorPane = (AnchorPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }

        this.rootLayout.setCenter(anchorPane);
        this.controladorRootInterface = loader.getController();
        this.controladorRootInterface.iniciar(this);
    }

    public void iniciarRootInterfaceChart(int tamanhoBuffer) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootInterfaceChart.fxml").toURI().toURL());//Main.class.getResource("../view/RootInterfaceChart.fxml")
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
        }
        AnchorPane anchorPane = null;
        try {
            anchorPane = (AnchorPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }
        
        this.buffer = new Buffer(tamanhoBuffer);
        this.rootLayout.setRight(anchorPane);
        ControladorRootInterfaceChart controladorRootInterfaceChart = loader.getController();
        controladorRootInterfaceChart.iniciar(this, tamanhoBuffer, new AddToQueue(buffer));
        
        this.controladorRootInterfaceChart = controladorRootInterfaceChart;
    }

    public void iniciarExecucao(int tamanhoBuffer, int qtdProdutoresConsumidores) {
        Consumidor consumidor;
        Produtor produtor;
        Impressora impressora = new Impressora(this.controladorRootInterface);
        threads = new Thread[(qtdProdutoresConsumidores * 2)];
        int indiceThread = 0;

        for (int i = 0; i < qtdProdutoresConsumidores; i++) {
            consumidor = new Consumidor(i + 1, buffer, impressora);
            produtor = new Produtor(i + 1, buffer, consumidor, impressora);
            threads[indiceThread] = new Thread(produtor);
            indiceThread++;
            threads[indiceThread] = new Thread(consumidor);
            indiceThread++;
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void interromperExecucao() {
        if (threads != null) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
            if(this.controladorRootInterfaceChart != null) {
                this.controladorRootInterfaceChart.pararExecucao();
            }
        }
    }
    
    public void reiniciar() {
        interromperExecucao();
        this.buffer = null;
        this.controladorRootInterface = null;
        this.threads = null;
        try{
            this.rootLayout.getChildren().remove(this.rootLayout.lookup('.anchorPane'));
        } catch(IndexOutOfBoundsException e) {
//            Não existe 2º filho
            System.out.println("Não existe 2º filho");
        }
        iniciarRootMenu();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
