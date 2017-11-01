package br.edu.ifsc.model;

public class LeitorBuffer implements Runnable {

    private Buffer buffer;
    private Impressora impressora;

    public LeitorBuffer(Buffer buffer, Impressora impressora) {
        super();
        this.buffer = buffer;
        this.impressora = impressora;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                return;
            }
            this.impressora.imprimirUsoBuffer(this.buffer.getUsoBuffer());
        }
    }
}
