package br.edu.ifsc.main;

import br.edu.ifsc.model.Buffer;
import br.edu.ifsc.model.Consumidor;
import br.edu.ifsc.model.Produtor;

public class Main {

	public static void main(String[] args) {
		Buffer b = new Buffer(20);
		Consumidor consumidor;
		Produtor produtor;

		for (int i = 0; i < 10; i++) {
			consumidor = new Consumidor(i + 1, b);
			produtor = new Produtor(i + 1, b, consumidor);
			new Thread(produtor).start();
			new Thread(consumidor).start();
		}

		System.out.println("######## Foi a MAIN #########");

		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.resume();
		}
	}

}
