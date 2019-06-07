/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflujo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author irving-mac
 */
public class EcoFlujoServer {

    
    public static void main(String[] args) {
        int puerto = 1234;
        String host = "127.0.0.1";
        ServerSocket s;
        Socket cl;
        try {
            s = new ServerSocket(puerto);
            s.setReuseAddress(true);
            System.out.println("[ OK ] Servicio Iniciado, esperando cliente...");
            while(true){
                cl = s.accept();
                System.out.println("[ OK ] Cliente conectado desde: "+ cl.getInetAddress()+":"+ cl.getPort());
                PrintWriter out = new PrintWriter(cl.getOutputStream(),true); 
                BufferedReader in = new BufferedReader(new InputStreamReader( cl.getInputStream())); 
                    
                String mensaje; 
                while ((mensaje = in.readLine()) != null) 
                    { 
                     System.out.println ("[ OK ] Mensaje recibido: " + mensaje); 
                     out.println(mensaje); 

                     if (mensaje.equals("salir")) 
                         break; 
                    } 
                out.close(); 
                in.close(); 
                cl.close(); 
                s.close();

            }
        } catch (IOException ex) {
            System.out.println("[FAIL] Ocurrio un error");
        }
        
    }
    
}
