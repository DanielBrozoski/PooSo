package br.edu.ifsc.model;

public class Produtor implements Runnable {

	private int pid;
	private Buffer buffer;
	private Consumidor consumidor;

	public Produtor(int pid, Buffer buffer, Consumidor consumidor) { 
		this.pid = pid;
		this.buffer = buffer;
		this.consumidor = consumidor;
	}

	public void run() {
		while (true) {
			int indiceBuffer = buffer.insereIndice(pid);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (indiceBuffer != -1) {
				System.out.println("O produtor " + pid + "inseriu na posição " + indiceBuffer + "do buffer");
				consumidor.notifica();
			} else
				System.out.println("Buffer lotado, filho da puta");
		}

	}
}