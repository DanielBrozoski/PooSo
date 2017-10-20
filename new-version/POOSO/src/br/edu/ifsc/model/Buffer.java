package br.edu.ifsc.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	private int pos_vec;
	private int[] buffer;
	private Lock lock;

	public Buffer(int pos_vec) {
		this.pos_vec = pos_vec;
		this.buffer = new int[pos_vec];
		this.lock = new ReentrantLock();
	}

	public int insereIndice(int pid) {
		this.lock.lock();
		int index = -1;

		try {
			for (int i = 0; i < pos_vec; i++) {
				if (buffer[i] == 0) {
					buffer[i] = pid;
					index = i;
					break;
				}
			}
		} finally {
			this.lock.unlock();
		}
		return index;
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
		this.lock.lock();
		try {
			System.out.println("----------------------------------");
			for (int i : buffer) {
				System.out.println(i);
			}
			System.out.println("----------------------------------");
		} finally {
			this.lock.unlock();
		}
	}
}
