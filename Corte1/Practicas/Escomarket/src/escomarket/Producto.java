/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escomarket;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author irvingmac
 */
class Producto implements Serializable{
    int ID;
    String Nombre;
    String Marca;
    String Descripcion;
    int Precio;
    int Existencias;
    File Imagen;
    //Ubicación de las imagenes de los productos
    //Rutas WIN
    //private final String pathImg = "F:\\ESCOM\\7\\Redes2\\Practica 1\\FilesServer\\src\\cloud";

    //Rutas MAC
    //private final String pathImg = "/Volumes/BLUE/ESCOM/7/Redes2/Practica 2 - Tienda/Escomarket/src/imgServidor";
    
    private final String pathImg = "F:\\ESCOM\\7\\Redes2\\Practica 2 - Tienda\\Escomarket\\src\\imgServidor";

    public Producto(int ID, String Nombre, String Marca, String Descripcion, int Precio, int Existencias, String Imagen) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.Marca = Marca;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
        this.Existencias = Existencias;
        this.Imagen = new File(pathImg, Imagen);
    }
    
    public Producto() {
        
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int Precio) {
        this.Precio = Precio;
    }

    public int getExistencias() {
        return Existencias;
    }

    public void setExistencias(int Existencias) {
        this.Existencias = Existencias;
    }

    public File getImagen() {
        return Imagen;
    }

    public void setImagen(File Imagen) {
        this.Imagen = Imagen;
    }
    
    
    
    
}
