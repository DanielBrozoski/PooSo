package br.edu.ifsc.model;

public class Produtor implements Runnable {

    private int pid;
    private Buffer buffer;
    private Consumidor consumidor;
    private Impressora impressora;

    public Produtor(int pid, Buffer buffer, Consumidor consumidor, Impressora impressora) {
        this.pid = pid;
        this.buffer = buffer;
        this.consumidor = consumidor;
        this.impressora = impressora;
    }

    public void run() {
        while (true) {
            if (this.consumidor.dormindo()) {
                int indiceBuffer = buffer.insereProduto(pid);

                if (indiceBuffer != -1) {
                    this.impressora.imprimirProducao("# Produtor: " + pid + " inseriu na posição " + indiceBuffer + " do buffer.\n");
                    this.consumidor.notifica();
                } else {
                    this.impressora.imprimirProducao("# Produtor: " + pid + "BUFFER LOTADO.\n");
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    this.impressora.imprimirProducao("# Produtor: " + pid + "INTERROMPERAM MEU SONO.\n");
                    return;
                }
            }
        }
    }
}