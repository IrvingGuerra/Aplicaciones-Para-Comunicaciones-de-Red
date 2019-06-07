/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecodatagrama;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author irving-mac
 */
public class EcoDatagramClient {
    private static int buffer=255; //Define tamaño maximo de cada parte
    public static void main(String[] args) {
        try {
            DatagramSocket s=new DatagramSocket();
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String msj;
            int paquetes,restante,i,j,in=0;
            byte[] bytesMensaje;
            do{
                System.out.print("[OK] Escribe tu mensaje: ");
                msj=br.readLine();
                bytesMensaje=msj.getBytes();
                paquetes=bytesMensaje.length/buffer;
                restante=bytesMensaje.length%buffer;
                System.out.println("[INFO] Paquetes: "+paquetes+" Restante: "+ restante);
                for(i=0,j=0; j<paquetes;j++,i=i+buffer){
                    DatagramPacket p=new DatagramPacket(bytesMensaje,i,buffer,InetAddress.getByName("localhost"),7000);
                    s.send(p);
                    DatagramPacket respuesta=new DatagramPacket(new byte[buffer],buffer);
                    s.receive(respuesta);
                    System.out.println("[OK] Server Echo: " + new String(respuesta.getData(),0,respuesta.getLength()));
                    in=i;
                }
                if(restante!=0){
                    if(paquetes!=0)
                        in=in+buffer;
                    else
                        in=0;
                    DatagramPacket prest=new DatagramPacket(bytesMensaje,in,restante,InetAddress.getByName("localhost"),7000);
                    s.send(prest);
                    DatagramPacket respuesta=new DatagramPacket(new byte[buffer],buffer);
                    s.receive(respuesta);
                    System.out.println("[OK] Server Echo: " + new String(respuesta.getData(),0,respuesta.getLength()));
                }
               
            }while(msj.compareTo("salir")!=0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
