package sources;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irving-mac
 */
public class cliente {
    public static void main(String[] args) throws SocketException, FileNotFoundException, IOException, InterruptedException {
        String host= "127.0.0.1";
        JFileChooser jf= new JFileChooser();
        int puerto=1234;
        DatagramSocket cl = new DatagramSocket();
        int r= jf.showOpenDialog(null);
        jf.requestFocus();
        if(r==JFileChooser.APPROVE_OPTION){
            File f= jf.getSelectedFile();
            String nombre= f.getName();
            String ruta=f.getAbsolutePath();
            DataInputStream dis = new DataInputStream(new FileInputStream(ruta));
            long tam=dis.available();
            long enviados=0;
            int i=0, n=0;
                while(enviados<tam) {
                    byte[] buffer = new byte[60000];
                    boolean ciclo=true;
                    n=dis.read(buffer);
                    datos d = new datos(++i,buffer,nombre,tam,n);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(d);
                    oos.flush();
                    byte[] msj = baos.toByteArray();
                    DatagramPacket p = new DatagramPacket(msj, msj.length,InetAddress.getByName(host),puerto);
                    cl.send(p);
                    enviados+=n;
                    System.out.println("[OK] Paquetes enviados: " + i + " de " + (int)((tam/60000)+1));
                    while(ciclo) {
                        cl.receive(p);
                        if(Arrays.equals(p.getData(), msj)) {
                            ciclo=false;
                        }        
                    }
                    if(i==(int)((tam/60000)+1)) {
                        System.out.println("[OK]  Archivo enviado al servidor");
                    }
                }
        }
    }
}
