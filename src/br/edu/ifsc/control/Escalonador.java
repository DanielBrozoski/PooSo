package br.edu.ifsc.control;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsc.model.Consumidor;
import br.edu.ifsc.model.Produtor;

public class Escalonador implements Runnable{
    private Produtor[] produtores;
    private Consumidor[] consumidores;

    public Escalonador(Produtor[] produtores, Consumidor[] consumidores) {
        this.produtores = produtores;
        this.consumidores = consumidores;
    }
    
    public void run() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    	Thread t;
    	List<Thread> lista = new ArrayList<>();
//		Thread[] tp = new Thread[10];
		
		for (int i = 0; i < 10; i++) {
			t = new Thread(consumidores[i]);
			lista.add(t);
			t.start();
			t = new Thread(produtores[i]);
			lista.add(t);
			t.start();
		}
		
		while(true) {
			
		}
    }
    
}
