/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.model;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author user
 */
public class AddToQueue implements Runnable {
    
    private ConcurrentLinkedQueue<Number> dataQueue;
    private ExecutorService executor;
    private int data;
    
    public AddToQueue(ExecutorService executor) {
        this.dataQueue = new ConcurrentLinkedQueue<Number>();
        this.executor = executor;
    }
    
    public void run() {
        try{
            this.dataQueue.add(Math.random());//this.data
            Thread.sleep(50);
            this.executor.execute(this);
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void addData(int data) {
        this.data = data;
    }

    public ConcurrentLinkedQueue<Number> getDataQueue() {
        return dataQueue;
    }
}
