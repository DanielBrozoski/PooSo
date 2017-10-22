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

			if (indiceBuffer != -1) {
				System.out.println("O produtor " + pid + " inseriu na posição " + indiceBuffer + " do buffer");
				consumidor.notifica();
				System.out.println("\n    Produtor");
//				buffer.resume();
			} else {
				System.err.println("Buffer lotado, filho da puta");
			}
			try {
				Thread.sleep(500);
			}catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
}