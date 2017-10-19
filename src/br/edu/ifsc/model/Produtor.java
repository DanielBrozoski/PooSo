package br.edu.ifsc.model;

public class Produtor implements Runnable {

	private int pid;
	private boolean dormindo;
	private Buffer buffer;
	private Consumidor consumidor;

	public Produtor(int pid, Buffer buffer, Consumidor consumidor) {
		this.pid = pid;
		this.dormindo = true;
		this.buffer = buffer;
		this.consumidor = consumidor;
	}

	public void run() {
		while (true) {
			while(dormindo) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int indiceBuffer = buffer.buscaIndice();

			if (indiceBuffer != -1) {
				indiceBuffer = buffer.insereIndice(pid, indiceBuffer);

				if (indiceBuffer != -1) {

					System.out.println("O produtor " + pid + " inseriu na posição " + indiceBuffer + " do buffer");
					consumidor.notifica();
					buffer.resume();
				} else {
					System.err.println("Buffer foi furtado, filho da puta");
				}
			} else {
				System.err.println("Buffer lotado, filho da puta");
			}

		}

	}
}