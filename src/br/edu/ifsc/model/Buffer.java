package br.edu.ifsc.model;

public class Buffer {
	private int pos_vec;
	private int[] buffer;

	public Buffer(int pos_vec) {
		this.pos_vec = pos_vec;
		this.buffer = new int[pos_vec];

	}

	public int buscaIndice() {
		for (int i = 0; i < pos_vec; i++) {
			if (buffer[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	public int insereIndice(int pid, int indice) {
		if (buffer[indice] == 0) {
			buffer[indice] = pid;
			return 1;
		}
		return -1;

	}

	public int removeIndice(int pid) {
		for (int i = 0; i < pos_vec; i++) {
			if (buffer[i] == pid) {
				buffer[i] = 0;
				return i;
			}
		}
		return -1;
	}

	public void batata() {
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] == 0)
				System.out.println("-" + this.buffer[i]);
		}
	}

	public void resume() {
		System.out.println("----------------------------------");
		for (int i : buffer) {
			System.out.println(i);
		}
		System.out.println("----------------------------------");
	}
}
