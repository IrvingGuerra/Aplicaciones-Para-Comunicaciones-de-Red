/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import ventanas.Interfaz;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.System.console;
import java.net.Socket;
import java.util.List;
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Sir-M
 */
public class DragListener implements DropTargetListener {
    
    int puerto = 1234;
    String host="127.0.0.1";
    //Socket
    Socket cl;
    
    public DragListener(){
        
    }
    

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        //Aceptamos el elemento arrastrado
        dtde.acceptDrop(DnDConstants.ACTION_COPY);
        //Obetenemos los items
        Transferable t = dtde.getTransferable();
        //Obetenemos Formatos del item
        DataFlavor[] df = t.getTransferDataFlavors();
        
        for(DataFlavor f:df){
            try{
                if(f.isFlavorJavaFileListType()){
                   //Obetenemos list del item
                   List<File> files=(List<File>) t.getTransferData(f);
                   
                   for(File file:files){

                        String name = file.getName();
                        long tam = file.length();
                        String path = file.getAbsolutePath();
                        loadFile(name,tam,path);    
                   }
                   
                }
            }catch(Exception ex){
                
            }
        }
        
        
    }
    
            
    private void loadFile(String name, long tam, String path) {
        connectWithServer();
        if(cl != null){
            try {
                System.out.println("[ OK ] Enviando archivo: " + path + " que mide " + tam + " bytes\n");
                DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                //Enviando el archvio
                dos.writeUTF(name); //Envia el nombre
                dos.flush();
                dos.writeLong(tam); //Envia el tamaño
                
                byte[] b =new byte[1500];
                long enviados = 0;
                int porciento = 0, n=0;

                while(enviados < tam){
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush(); //se envian
                    enviados+=n;
                    porciento = (int)((enviados*100)/tam);
                    System.out.println("\r [ SEND ] Trasmitido el " + porciento + "%");
                }
                dis.close();
                dos.close();
                cl.close();
                System.out.println("[ OK ] Archvio enviado con exito");
                showMessageDialog(null, "Archivo enviado con exito");
            } catch (IOException ex) {
                showMessageDialog(null, "Ocurrio un problema al subir el archvio");
            }
        }

    }
    
    private void connectWithServer() {
        //Nos conectamos con els ervidor local.
        try{
            cl = new Socket (host, puerto); //socket bloquante
            System.out.println("[ OK ] Conectado!");
        }catch(Exception e){
            showMessageDialog(null, "Ocurrio un problema con el servidor");
        }
            
    }
    
}
