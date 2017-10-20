package br.edu.ifsc.model;

public class Consumidor implements Runnable {
	private int pidProdutor;
	private boolean dormindo = true;
	private Buffer buffer;

	public Consumidor(int pidProdutor, Buffer buffer) {
		this.pidProdutor = pidProdutor;
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
				 System.out.println("    I'm sleeping " + pidProdutor + " " + dormindo);
			}

			int indiceVetor = buffer.removeIndice(this.pidProdutor);
			if (indiceVetor != -1) {
				this.notifica();
				System.out.println("    Eu " + pidProdutor + " li da posicao " + indiceVetor + " do buffer! " + dormindo);
				System.out.println("\n    Consumidor");
//				buffer.resume();
			} else {
				System.err.println("I'm sleeping " + pidProdutor + ", me enganaram");
				buffer.resume();
				System.exit(1);
			}
		}
	}

}
