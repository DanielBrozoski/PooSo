package br.edu.ifsc.control;

import br.edu.ifsc.main.Main;
import br.edu.ifsc.model.AddToQueue;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;

public class ControladorRootInterfaceChart {

    @FXML
    private AreaChart<String, Number> chartBuffer;
    
    @FXML
    private Button btnStop;

    private NumberAxis xAxis;
    private NumberAxis yAxis;
    
    private AnimationTimer animationTimer;
    private Series series;
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private SimpleDateFormat horaFormatada;
    private Date data;
    
    private int contadorLinhas;
    private Main main;

    public void iniciar(Main main, int tamanhoBuffer, AddToQueue addToQueue) {//, AddToQueue addToQueue
        this.chartBuffer.setAnimated(false);
        this.main = main;
        
        this.xAxis = new NumberAxis(1, 20, 1);
        this.xAxis.setTickLabelsVisible(false);
        this.yAxis = new NumberAxis(1, tamanhoBuffer, tamanhoBuffer/5);
        this.yAxis.setForceZeroInRange(true);
        this.yAxis.setAutoRanging(false);
        
        this.series = new AreaChart.Series();
        this.series.setName("Buffer");
        this.chartBuffer.getData().add(series);
        this.executor = Executors.newCachedThreadPool();
        this.addToQueue = addToQueue;
        this.addToQueue.setExecutorService(executor);
        
        this.horaFormatada = new SimpleDateFormat("HH:mm:ss");
        this.data = Calendar.getInstance().getTime();
        
        this.executor.execute(this.addToQueue);
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        };
        this.animationTimer.start();
    }

    @FXML
    void parar() {
        this.btnStop.setDisable(true);
        this.main.interromperExecucao();
    }
    
    public void pararExecucao() {
        this.addToQueue.paraDeExecutar();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Saiu"+ex.getMessage());
        }
        this.executor.shutdown();

        if (!executor.isTerminated()) {
            executor.shutdownNow();
        }
        this.animationTimer.stop();
        this.addToQueue = null;
        this.btnStop = null;
        this.chartBuffer = null;
        this.contadorLinhas = 0;
        this.data = null;
        this.executor = null;
        this.horaFormatada = null;
        this.main = null;
        this.series = null;
        this.xAxis = null;
        this.yAxis = null;
    }

    public void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            if (this.addToQueue.filaVazia()) break;
            this.series.getData().add(new AreaChart.Data(this.horaFormatada.format(data), this.addToQueue.removeDado()));
            data = Calendar.getInstance().getTime();
        }
        if (this.series.getData().size() > 20) {
            this.series.getData().remove(0, series.getData().size() - 20);
        }
    }
}