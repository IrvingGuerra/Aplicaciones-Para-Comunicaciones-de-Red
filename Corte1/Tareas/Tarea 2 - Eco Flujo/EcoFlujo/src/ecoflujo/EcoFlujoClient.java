/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflujo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author irving-mac
 */
public class EcoFlujoClient {
    public static void main(String[] args) {
        int puerto = 1234;
        String host = "127.0.0.1";
        Socket s;
        try {
            s = new Socket(host,puerto);
            System.out.println("[OK] Servicio conectado.");
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            String mensaje;
            
            System.out.print ("[OK] Escribe un mensaje: ");
            while ((mensaje = stdIn.readLine()) != null) {
                out.println(mensaje);
                System.out.println("[OK] Respuesta server: " + in.readLine());
                System.out.print ("[OK] Escribe un mensaje: ");
            }

            out.close();
            in.close();
            stdIn.close();
            s.close();
            
        } catch (IOException ex) {
            System.out.println("[FAIL] Ocurrio un error");
        }
    }
}
