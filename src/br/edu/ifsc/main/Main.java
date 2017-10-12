/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
packages br.edu.ifsc.main;

import br.edu.ifsc.model.Buffer;
import br.edu.ifsc.model.Consumidor;
import br.edu.ifsc.model.Produtor;

public class Main {

	public static void main(String[] args) {
		Buffer b = new Buffer(10);
		Produtor[] p = new Produtor[10];
		Consumidor[] c = new Consumidor[10];
		Thread[] tc = new Thread[10];
		Thread[] tp = new Thread[10];

		for (int i = 0; i < 10; i++) {
			c[i] = new Consumidor(i + 1, b);
			p[i] = new Produtor(i + 1, b, c[i]);
			tc[i] = new Thread(c[i]);
			tp[i] = new Thread(p[i]);
			tc[i].start();
			
			tp[i].start();
		}

	}

}
