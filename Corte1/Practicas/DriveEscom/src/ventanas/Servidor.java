/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import static javax.swing.JOptionPane.showMessageDialog;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;

/**
 *
 * @author irving-mac
 */
public class Servidor extends javax.swing.JFrame {

    int puerto = 1234;
    String host = "127.0.0.1";
    String listaFiles = "";
    
    ServerSocket s;
    Socket cl;
    
    int i = 0;
    
    //Rutas WIN
    String cloud = "F:\\ESCOM\\7\\Redes2\\Practica 1\\FilesServer\\src\\cloud";
    String pathLocal = "F:\\ESCOM\\7\\Redes2\\Practica 1\\EscomDrive\\src\\local\\";
    String containsDownload = "FilesServer\\src\\cloud";
    
    //Rutas MAC
    //String cloud = "/Volumes/BLUE/ESCOM/7/Redes2/Practica 1/FilesServer/src/cloud";
    //String pathLocal = "/Volumes/BLUE/ESCOM/7/Redes2/Practica 1/EscomDrive/src/local/";
    //String containsDownload = "FilesServer/src/cloud";
    
    public Servidor() {
        initComponents();
        this.setLocationRelativeTo(null);
        startService();
        esperaFileOrFolder();   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
    }
    
     private void esperaFileOrFolder() {
        try{
            for(;;){
                //Acepta al cliente
                cl = s.accept();
                System.out.println("[ OK ] Cliente conectado desde: "+ cl.getInetAddress()+":"+ cl.getPort());
                //Leemos la entrada
                
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                String nombre = (String) dis.readUTF();
                
                //Si el nombre contiene la palabra "cloud" Significa que nos pide un archivo
                if(nombre.contains(containsDownload)){
                    //Nos pide un archivo o folder
                    if(nombre.contains(".")){
                        System.out.println("[ OK ] Transfieriendo archivo..."); 
                        File f = new File(nombre);
                        String name = f.getName();
                        long tam = f.length();
                        String path = f.getAbsolutePath();
                        System.out.println("[ OK ] Enviando archivo: " + name + " que mide " + tam + " bytes\n");
                        DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                        DataInputStream disFile = new DataInputStream(new FileInputStream(path));
                        //Enviando el archvio
                        dos.writeUTF(name); //Envia el nombre
                        dos.flush();
                        dos.writeLong(tam); //Envia el tamaño
                        byte[] b =new byte[1500];
                        long enviados = 0;
                        int porciento = 0, n=0;
                        while(enviados < tam){
                            n = disFile.read(b);
                            dos.write(b, 0, n);
                            dos.flush(); //se envian
                            enviados+=n;
                            porciento = (int)((enviados*100)/tam);
                            System.out.println("[ SEND ] Trasmitido el " + porciento + "%");
                        }
                        disFile.close();
                        dos.close();
                        cl.close();
                        System.out.println("[ OK ] Archvio enviado con exito");
                        
                        dis.close();
                        cl.close();  
                    }else{
                        //Creamos el rar
                        System.out.println("[ OK ] Transfieriendo carpeta en rar..."); 
                        //NOS PIDEN UN ZIP
                        String[] sect = nombre.split("\\\\");
                        String justName = sect[sect.length-1];
                        
                        FileOutputStream fos = new FileOutputStream(pathLocal+"\\"+justName+".zip");
                        ZipOutputStream zos = new ZipOutputStream(fos);
                        
                        byte[] b =new byte[1500];   
                        
                        addFilesRar(nombre,zos,b);//Carpeta que necesitamos comprimir
                        
                        zos.close();
                        dis.close();
                        cl.close();  
                    }
                        
                    
                }else{
                    //Nos pide almacenar un archivo
                    System.out.println("[ OK ] Recibiendo archivo..."); 
                    //en el nombre se recibe el path entero.
                    //1 Eliminaremos el path del user, para dejar las posibles carpetas.
                    nombre = nombre.replace(pathLocal, ""); //Aqui ya solo tiene la ultima ruta.
                    //Eliminaremos el nombre del archivo. Y crearemos ese directorio.
                    String[] sect = nombre.split("\\\\");
                    String justName = sect[sect.length-1];
                    System.out.println(justName);
                    String directoryToCreate = nombre.replace(justName, "");
                    System.out.println(directoryToCreate);
                    System.out.println(directoryToCreate);
                    File theDir = new File(cloud+"\\"+directoryToCreate);
                    // if the directory does not exist, create it
                    if (!theDir.exists()) {
                        System.out.println("[ OK ] Creando directorio: " + theDir.getName());
                        boolean result = false;
                        try{
                            theDir.mkdirs();
                            result = true;
                        } 
                        catch(SecurityException se){
                            System.out.println("[ FAIL ] Error al crear el directorio..."); 
                        }        
                        if(result) {    
                            System.out.println("[ OK ] Directorio creado");  
                        }
                    }

                    long tam = (long) dis.readLong();
                    DataOutputStream dos = new DataOutputStream (new FileOutputStream(cloud+"\\"+directoryToCreate+"\\"+justName));
                    byte [] b = new byte [1500];
                    long recibidos = 0;
                    int porciento_recibido = 0, n=0;
                    while (recibidos < tam){
                        n = dis.read(b);
                        dos.write(b, 0, n);
                        dos.flush();
                        recibidos +=n;
                        porciento_recibido = (int)((recibidos*100)/tam);
                        System.out.println("[ OK ] Recibido el " + porciento_recibido + "%");
                    }
                    dos.close();
                    dis.close();
                    cl.close();  
                    System.out.println("[ OK ] Archivo recibido");
                }
            } 
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void startService() {
        try{
            s = new ServerSocket(puerto);
            s.setReuseAddress(true);
            System.out.println("[ OK ] Servicio Iniciado, esperando cliente...");
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    private void addFilesRar(String nombre, ZipOutputStream zos, byte[] b) {
        File dir = new File(nombre); 
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isFile()){
                try {
                    System.out.println("[ OK ] Añadiendo archivo a rar: " + files[i].getName());
                    FileInputStream fis = new FileInputStream(files[i]);
                    zos.putNextEntry(new ZipEntry(files[i].getName()));
                    int length;
                    while ((length = fis.read(b)) > 0) {
                        zos.write(b, 0, length);
                    }
                    zos.closeEntry();
                    fis.close();
                } catch (IOException ioe) {
                    System.out.println("[ FAIL ] Error al crear el zip...");
                }
                   
            }else if(files[i].isDirectory()){
                addFilesRar(files[i].getAbsolutePath(), zos, b);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
