package br.edu.ifsc.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int tamanhoVetor;
    private int[] buffer;
    private Lock lock;

    public Buffer(int tamanhoVetor) {
        this.tamanhoVetor = tamanhoVetor;
        this.buffer = new int[tamanhoVetor];
        this.lock = new ReentrantLock();
    }

    public int insereProduto(int pid) {
        this.lock.lock();
        int index = -1;

        try {
            for (int i = 0; i < tamanhoVetor; i++) {
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

    public int consomeProduto(int pid) {
        for (int i = 0; i < tamanhoVetor; i++) {
            if (buffer[i] == pid) {
                buffer[i] = 0;
                return i;
            }
        }
        return -1;
    }

    public int getUsoBuffer() {
        this.lock.lock();
        int armazenamentoBuffer = 0;
        try {
            for (int i : buffer) {
                if(i != 0) {
                    armazenamentoBuffer++;
                }
            }
        } finally {
            this.lock.unlock();
        }
        return armazenamentoBuffer;
    }
}
