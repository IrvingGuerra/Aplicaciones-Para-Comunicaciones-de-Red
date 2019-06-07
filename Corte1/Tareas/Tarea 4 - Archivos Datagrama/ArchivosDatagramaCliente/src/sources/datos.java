package sources;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irving-mac
 */
public class datos implements Serializable {
    int id;
    byte[] b;
    String nombre;
    long tam;
    int n;

    public datos(int id, byte[] b, String nombre, long tam, int n) {
        this.id = id;
        this.b = b;
        this.nombre = nombre;
        this.tam = tam;
        this.n = n;
    }

    public int getId() {
        return id;
    }

    public byte[] getB() {
        return b;
    }

    public String getNombre() {
        return nombre;
    }

    public long getTam() {
        return tam;
    }

    public int getN() {
        return n;
    }
    
    
}
