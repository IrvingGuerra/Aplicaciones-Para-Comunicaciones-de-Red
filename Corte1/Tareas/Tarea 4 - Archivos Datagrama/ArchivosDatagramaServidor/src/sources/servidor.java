/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author irving-mac
 */
public class servidor {
    public static void main(String[] args) {
        try{
            int pto=1234;
            DatagramSocket s=new DatagramSocket(pto);
            System.out.println("[OK] Esperando al cliente...");
            int i=0;
            int cont=1;
            DataOutputStream dos = null;
            for(;;){
                DatagramPacket p=new DatagramPacket(new byte[65535],65535);
                s.receive(p);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
                datos archivo=(datos) ois.readObject();
                if(i==0) {
                    dos=new DataOutputStream(new FileOutputStream(archivo.nombre));
                }
                if(archivo.tam>60000) {
                    if(cont==archivo.id){
                    if(archivo.id <(int)(archivo.tam/60000)+1) {
                        dos.write(archivo.b);
                    }
                    else {
                        for(int j=0;j<(archivo.tam%60000);j++) {
                            dos.write(archivo.b[j]);
                        }
                    }
                    s.send(p);
                    cont++;
                    }
                    if(archivo.id == (int)(archivo.tam/60000)+1) {
                        if(dos.size()==archivo.tam) {
                            System.out.println("[OK]  Archivo recibido completo y en orden");
                            dos.flush();
                            dos.close();
                            
                        }
                    }   
                }
                else {
                    dos.write(archivo.b,0,(int)archivo.tam);
                    if(archivo.tam == archivo.n){
                        System.out.println("[OK] El archivo se recibio completo");
                    }
                    if(archivo.id==i+1){
                        System.out.println("[OK] El archivo se recibio en orden");
                    }
                    dos.flush();
                }
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
