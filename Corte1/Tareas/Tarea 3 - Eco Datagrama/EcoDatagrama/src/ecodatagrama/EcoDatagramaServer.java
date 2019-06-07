/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecodatagrama;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author irving-mac
 */
public class EcoDatagramaServer {

    /**
     * @param args the command line arguments
     */
    public static int buffer=10;
    public static void main(String[] args) {
        try{
            int pto=7000;
            DatagramSocket s=new DatagramSocket(pto);
            System.out.println("[OK] Esperando al cliente...");
            for(;;){
                DatagramPacket p=new DatagramPacket(new byte[buffer],buffer);
                s.receive(p);
                System.out.println("[OK] Mensaje recibido: "+new String(p.getData(),0,p.getLength()));
                s.send(p);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
