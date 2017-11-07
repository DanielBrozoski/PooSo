/**
 * Classe responsável por adicionar dados a fila.
 */

package br.edu.ifsc.model;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * @author user
 */
public class AddToQueue implements Runnable {
    
    private ConcurrentLinkedQueue<Number> dataQueue;
    private ExecutorService executor;
    private Buffer buffer;
    private boolean executar;
    
    public AddToQueue(Buffer buffer) {
        this.dataQueue = new ConcurrentLinkedQueue();
        this.buffer = buffer;
        this.executar = true;
    }
    
    public void run() {
        try{
            this.dataQueue.add(this.buffer.getUsoBuffer());
            Thread.sleep(998);
            if(executar){
                this.executor.execute(this);
            }
        } catch(InterruptedException e) {
            return;
        }
    }
    
    public Number removeDado() {
        return this.dataQueue.remove();
    }
    
    public boolean filaVazia() {
        return this.dataQueue.isEmpty();
    }
    
    public void paraDeExecutar() {
        this.executar = false;
    }
    
    public void setExecutorService(ExecutorService executor) {
        this.executor = executor;
    }
}
