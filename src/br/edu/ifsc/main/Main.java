/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.edu.ifsc.control.Escalonador;
import br.edu.ifsc.model.Buffer;
import br.edu.ifsc.model.Consumidor;
import br.edu.ifsc.model.Produtor;

public class Main {

	public static void main(String[] args) {
		Buffer b = new Buffer(10);
		Produtor[] p = new Produtor[10];
		Consumidor[] c = new Consumidor[10];
		Escalonador escalonador;

		for (int i = 0; i < 10; i++) {
			c[i] = new Consumidor(i + 1, b);
			p[i] = new Produtor(i + 1, b, c[i]);
		}
		escalonador = new Escalonador(p, c);
		escalonador.run();
	}

}
