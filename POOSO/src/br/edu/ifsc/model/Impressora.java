package br.edu.ifsc.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import br.edu.ifsc.interfaces.DriverImpressora;
import br.edu.ifsc.view.ControladorRootInterface;

public class Impressora implements DriverImpressora {

    private ControladorRootInterface controladorRootInterface;
    private Lock lock;

    public Impressora(ControladorRootInterface controladorRootInterface) {
        this.controladorRootInterface = controladorRootInterface;
        this.lock = new ReentrantLock();
    }

    @Override
    public void imprimirProducao(String texto) {
        this.lock.lock();
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            return;
        }
        try {
            this.controladorRootInterface.printarProducao(texto);
        } finally {
            this.lock.unlock();
        }
    }
}
