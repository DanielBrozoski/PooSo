package br.edu.ifsc.model;

public class Consumidor implements Runnable {
	private int pid;
	private boolean dormindo = true;
	private Buffer buffer;

	public Consumidor(int pid, Buffer buffer) {
		this.pid = pid;
		this.buffer = buffer;
	}

	public void notifica() {
		this.dormindo = !(this.dormindo);
	}

	public void run() {
		while (true) {

			while (dormindo) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 System.out.println("I'm sleeping " + pid + " " + dormindo);
			}

			int indiceVetor = buffer.removeIndice(this.pid);
			if (indiceVetor != -1) {
				this.notifica();
				System.out.println("Eu " + pid + " li da posicao " + indiceVetor + " do buffer! " + dormindo);
			} else {
				System.err.println("I'm sleeping " + pid + ", me enganaram");
				buffer.resume();
				System.exit(1);
			}
		}
	}

}
