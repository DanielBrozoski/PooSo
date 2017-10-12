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
		this.dormindo = !dormindo;
	}

	public void run() {
		while (true) {
			
				while(dormindo) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				System.out.println("I'm sleeping" + pid);
				}
			
				//wait(3332);
			
			int indiceVetor = buffer.removeIndice(this.pid);
			if (indiceVetor != -1) {
				System.out.println("Eu " + pid + " li da posicao " + indiceVetor + " do vetor!");
				this.notifica();
			} else {
				System.out.println("I'm sleeping" + pid + "q");
			}}
		}

}


