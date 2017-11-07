package br.edu.ifsc.main;

import java.io.IOException;

import br.edu.ifsc.exception.ErroCarregamentoInterfaceException;
import br.edu.ifsc.model.AddToQueue;
import br.edu.ifsc.model.Buffer;
import br.edu.ifsc.model.Consumidor;
import br.edu.ifsc.model.Impressora;
import br.edu.ifsc.model.Produtor;
import br.edu.ifsc.control.ControladorRootMenu;
import br.edu.ifsc.control.ControladorRootInterface;
import br.edu.ifsc.control.ControladorRootInterfaceChart;
import br.edu.ifsc.control.ControladorRootLayout;
import br.edu.ifsc.control.ControladorRootMenuBar;
import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    private ControladorRootLayout controladorRootLayout;
    private ControladorRootInterface controladorRootInterface;
    private ControladorRootInterfaceChart controladorRootInterfaceChart;

    private Thread[] threads;
    private Buffer buffer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Produtor / Consumidor");
        primaryStage.getIcons().add(new Image("file:resources/images/icone-64.png"));

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.runLater(() -> {
                interromperExecucao();
                System.exit(0);
            });
        });

        iniciarRootLayout(primaryStage);
        iniciarRootMenuBar();
        iniciarRootMenu();
        primaryStage.show();
    }

    public void iniciarRootLayout(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootLayout.fxml").toURI().toURL());
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
        }
        BorderPane borderPane = null;
        try {
            borderPane = (BorderPane) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }
        
        this.controladorRootLayout = loader.getController();

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
    }
    
    public void iniciarRootMenuBar() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootMenuBar.fxml").toURI().toURL());
        } catch (MalformedURLException ex) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + ex.getMessage());
        }
        MenuBar menuBar = null;
        try {
            menuBar = (MenuBar) loader.load();
        } catch (IOException e) {
            new ErroCarregamentoInterfaceException()
                .alertarErro("Erro no carregamento da interface:\n" + e.getMessage());
        }

        this.controladorRootLayout.colocarEmCima(menuBar);

        ControladorRootMenuBar controladorRootMenuBar = loader.getController();
        controladorRootMenuBar.iniciar(this);
    }

    public void iniciarRootMenu() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootMenu.fxml").toURI().toURL());
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

        this.controladorRootLayout.colocarNoCentro(anchorPane);

        ControladorRootMenu controladorRootMenu = loader.getController();
        controladorRootMenu.iniciar(this);
    }

    public void iniciarRootInterface() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootInterface.fxml").toURI().toURL());
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

        this.controladorRootLayout.colocarNoCentro(anchorPane);
        this.controladorRootInterface = loader.getController();
        this.controladorRootInterface.iniciar(this);
    }

    public void iniciarRootInterfaceChart(int tamanhoBuffer) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/br/edu/ifsc/view/RootInterfaceChart.fxml").toURI().toURL());
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
        this.controladorRootLayout.colocarNaDireita(anchorPane);
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
            this.threads = null;
        }
    }
    
    public void reiniciar() {
        interromperExecucao();
        this.buffer = null;
        this.controladorRootInterface = null;
        this.controladorRootInterfaceChart = null;
        this.threads = null;
        this.controladorRootLayout.limparNodes();
        iniciarRootMenuBar();
        iniciarRootMenu();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
