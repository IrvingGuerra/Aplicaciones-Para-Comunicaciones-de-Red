/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author irving-mac
 */
public class ClientMulticast extends Thread{
    
    public static final String MCAST_ADDR  = "227.0.0.1";
    public static final int MCAST_PORT = 9014;
    public static final int DGRAM_BUF_LEN=1024;
    
    InetAddress group =null;
    
    registro frameRegistro;
    login frameLogin;
    foro frameForo;
    
    String mensajeEnviado = "";
    

    public ClientMulticast(registro reg, login log, foro forof){
        frameRegistro = reg;
        frameLogin = log;
        frameForo = forof;
        System.out.println("Cliente Multicast iniciado.");
        try{
            group = InetAddress.getByName(MCAST_ADDR);
        }catch(UnknownHostException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public Boolean send(String msg){
        try{
            mensajeEnviado = msg;
            MulticastSocket socketEnvio = new MulticastSocket(MCAST_PORT);
            socketEnvio.joinGroup(group); // se configura para escuchar el paquete
            DatagramPacket packet = new DatagramPacket(msg.getBytes(),msg.length(),group,MCAST_PORT);
            System.out.println("Instruccion por enviar: "+msg);
            socketEnvio.send(packet);
            socketEnvio.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public void run(){
        try{
            group = InetAddress.getByName(MCAST_ADDR);//intenta resolver la direccion
        }catch(UnknownHostException e){
            e.printStackTrace();
            System.exit(1);
        }
        try{
            MulticastSocket socket = new MulticastSocket(MCAST_PORT); //socket tipo multicast
            socket.joinGroup(group);//se une al grupo
            String mensaje="";
            while(true){
                byte[] buf = new byte[DGRAM_BUF_LEN];//crea arreglo de bytes 
                DatagramPacket recv = new DatagramPacket(buf,buf.length);//crea el datagram packet a recibir
                socket.receive(recv);// ya se tiene el datagram packet
                byte [] data = recv.getData();
                mensaje = new String(data);
                mensaje = mensaje.trim();
                if(!mensaje.equals(mensajeEnviado)){
                    String partes[] = mensaje.split("<>");
                    String instruccion = partes[0];
                    System.out.println("Instruccion recibida: "+instruccion);
                    switch(instruccion){
                        case "6": //String con todos los Temas
                            frameForo.loadTemasByDefault(partes[1].substring(1,partes[1].length()-1));
                            break;
                        case "7":
                            String tema = partes[1];
                            frameForo.updateTemaIfYouAreIn(tema); 
                         break;
                        case "10":
                            tema = partes[1];
                            frameForo.loadTemasByDefault(partes[1].substring(1,partes[1].length()-1));
                    
                            
                        break;
                    }
                }
            }        
        }catch(IOException e){
            e.printStackTrace();
            System.exit(2);
        }
    }
    
}
