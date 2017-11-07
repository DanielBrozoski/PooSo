package br.edu.ifsc.model;

public class Consumidor implements Runnable {

    private int pidProdutor;
    private boolean dormindo;
    private Buffer buffer;
    private Impressora impressora;

    public Consumidor(int pidProdutor, Buffer buffer, Impressora impressora) {
        this.pidProdutor = pidProdutor;
        this.dormindo = true;
        this.buffer = buffer;
        this.impressora = impressora;
    }

    public void notifica() {
        this.dormindo = !(this.dormindo);
    }

    public boolean dormindo() {
        return this.dormindo;
    }

    public void run() {
        while (true) {
            while (dormindo) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    this.impressora.imprimirProducao("$ Consumidor: INTERROMPERAM MEU SONO.\n");
                    return;
                }
            }

            int indiceVetor = buffer.consomeProduto(this.pidProdutor);
            if (indiceVetor != -1) {
                this.notifica();
                this.impressora.imprimirProducao(
                    "$ Consumidor: " + this.pidProdutor + " leu da posição " + indiceVetor + " do buffer.\n");
            } else {
                this.impressora.imprimirProducao("$ Consumidor: " + this.pidProdutor + "FOI FURTADO.\n");
                return;
            }
        }
    }
}
