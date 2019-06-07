/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escomarket;

import java.io.Serializable;

/**
 *
 * @author irvingmac
 */
public class Carrito implements Serializable{
    
    private int Producto;
    private int cantidad;

    public Carrito(int Producto, int cantidad) {
        this.Producto = Producto;
        this.cantidad = cantidad;
    }
    
    public Carrito(){
        this.Producto = 0;
        this.cantidad = 0;
    }

    public int getProducto() {
        return Producto;
    }

    public void setProducto(int Producto) {
        this.Producto = Producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
}
