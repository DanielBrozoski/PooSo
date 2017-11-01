package br.edu.ifsc.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import br.edu.ifsc.interfaces.DriverImpressora;
import br.edu.ifsc.view.ControladorRootInterface;
import br.edu.ifsc.view.ControladorRootInterfaceChart;

public class Impressora implements DriverImpressora {

    private ControladorRootInterface controladorRootInterface;
    private ControladorRootInterfaceChart controladorRootInterfaceChart;
    private Lock lock;

    public Impressora(ControladorRootInterface controladorRootInterface,
            ControladorRootInterfaceChart controladorRootInterfaceChart) {
        this.controladorRootInterface = controladorRootInterface;
        this.controladorRootInterfaceChart = controladorRootInterfaceChart;
        this.lock = new ReentrantLock();
    }

    @Override
    public void imprimirUsoBuffer(int valor) {
        this.lock.lock();
        try {
            this.controladorRootInterfaceChart.atualizarUsoBuffer(valor);
        } finally {
            this.lock.unlock();
        }
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

    @Override
    public void imprimirEstadoBuffer(String texto) {
        this.lock.lock();

        try {
        } finally {
            this.lock.unlock();
        }
    }
}
