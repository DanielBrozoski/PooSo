package br.edu.ifsc.view;

import br.edu.ifsc.main.Main;
import br.edu.ifsc.model.AddToQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadFactory;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;

public class ControladorRootInterfaceChart {

    @FXML
    private AreaChart<String, Double> chartBuffer;

    @FXML
    private Label lblBufferUsado;
	
    @FXML
    private Label lblProdutor;

    @FXML
    private Label lblConsumidor;

    private NumberAxis xAxis;
    private NumberAxis yAxis;
    
    private Series series;
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private int xData;
    
//    XYChart.Series<String, Double> seriesBuffer;
    private int contadorLinhas;
    private Main main;

    public void iniciar(Main main, int tamanhoBuffer) {
        this.main = main;
        this.xAxis = new NumberAxis();
        this.yAxis = new NumberAxis();//1, tamanhoBuffer, 1
         //-- add 20 numbers to the plot+
//        this.series = new AreaChart.Series<Number, Number>();
        this.series = new AreaChart.Series();
//        this.series.setName("Buffer series");
        this.chartBuffer.getData().add(series);
        this.executor = Executors.newCachedThreadPool();
//            new ThreadFactory {
//            @Override
//            public Thread newThread(Runnable runnable) {
//                Thread thread = new Thread(runnable);
//                thread.setDaemon(true);
//                return thread;
//            }
//        });
        this.addToQueue = new AddToQueue(executor);
        this.executor.execute(this.addToQueue);
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                
            }
        }.start();
//        ANTIGO (TESTE):
//        seriesBuffer = new XYChart.Series<String, Double>();
//        seriesBuffer.setName("Buffer");
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(1+"", 4.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(3+"", 10.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(6+"", 15.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(9+"", 8.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(12+"", 5.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(15+"", 18.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(18+"", 15.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(21+"", 13.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(24+"", 19.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(27+"", 21.0));
//        seriesBuffer.getData().add(new XYChart.Data<String, Double>(30+"", 21.0));
//        this.chartBuffer.getData().addAll(seriesBuffer);
    }

    @FXML
    void parar() {
        this.main.interromperExecucao();
    }

    public void atualizarUsoBuffer(int valor) {
//      this.seriesBuffer.getData().add(new XYChart.Data(this.contadorLinhas, valor));
//      this.chartBuffer.getData().add(seriesBuffer);
//	this.lblBufferUsado.setText(valor + "");
    }
    
    public void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            if (this.addToQueue.getDataQueue().isEmpty()) break;
            series.getData().add(new AreaChart.Data(xData++, this.addToQueue.getDataQueue().remove()));
        }
        if (series.getData().size() > 20) {
            series.getData().remove(0, series.getData().size() - 20);
        }
        xAxis.setLowerBound(xData-20);
        xAxis.setUpperBound(xData-1);
    }
}