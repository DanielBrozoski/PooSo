package br.edu.ifsc.main;

import java.io.IOException;

import br.edu.ifsc.exception.ErroCarregamentoInterfaceException;
import br.edu.ifsc.model.Buffer;
import br.edu.ifsc.model.Consumidor;
import br.edu.ifsc.model.Impressora;
import br.edu.ifsc.model.LeitorBuffer;
import br.edu.ifsc.model.Produtor;
import br.edu.ifsc.view.ControladorRootMenu;
import br.edu.ifsc.view.ControladorRootInterface;
import br.edu.ifsc.view.ControladorRootInterfaceChart;
import br.edu.ifsc.view.ControladorRootLayout;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    private Stage stage;
    private BorderPane rootLayout;
    private ControladorRootLayout controladorRootLayout;
    private ControladorRootMenu controladorRootMenu;
    private ControladorRootInterface controladorRootInterface;
    private ControladorRootInterfaceChart controladorRootInterfaceChart;

    private Thread[] threads;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.stage.setTitle("Produtor / Consumidor");
        this.stage.getIcons().add(new Image("file:resources/images/icon4.png"));

        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        interromperExecucao();
                        System.exit(0);
                    }
                });

            }
        });

        iniciarRootLayout();
        iniciarRootMenu();
    }

    public void iniciarRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootLayout.fxml").toURI().toURL());//Main.class.getResource("../view/RootLayout.fxml")
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.rootLayout = (BorderPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }

        Scene scene = new Scene(this.rootLayout);
        this.stage.setScene(scene);

        this.controladorRootLayout = loader.getController();

        this.controladorRootLayout.iniciar(this);
        this.stage.show();
    }

    public void iniciarRootMenu() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootMenu.fxml").toURI().toURL());//Main.class.getResource("../view/RootMenu.fxml")
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane anchorPane = null;
        try {
            anchorPane = (AnchorPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }

        this.rootLayout.setCenter(anchorPane);

        this.controladorRootMenu = loader.getController();

        this.controladorRootMenu.iniciar(this);
    }

    public void iniciarRootInterface() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootInterface.fxml").toURI().toURL());//Main.class.getResource("../view/RootInterface.fxml")
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane anchorPane = null;
        try {
            anchorPane = (AnchorPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }

        this.rootLayout.setRight(anchorPane);

        this.controladorRootInterfaceChart = loader.getController();

        this.controladorRootInterfaceChart.iniciar(this, tamanhoBuffer);
    }

    public void iniciarExecucao(int tamanhoBuffer, int qtdProdutoresConsumidores) {
        Buffer buffer = new Buffer(tamanhoBuffer);
        Consumidor consumidor;
        Produtor produtor;
        Impressora impressora = new Impressora(this.controladorRootInterface, this.controladorRootInterfaceChart);
        LeitorBuffer leitorBuffer = new LeitorBuffer(buffer, impressora);
        threads = new Thread[(qtdProdutoresConsumidores * 2) + 1];
        int indiceThread = 1;

        threads[0] = new Thread(leitorBuffer);

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
            for (int i = 0; i < threads.length; i++) {
                threads[i].interrupt();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
