package balanceador;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego EG
 */
public class Servidor {
    
    private SocketChannel sc1 ;
    private int dirigirA;
    private ArrayList<String> respuestas = new ArrayList();
 

    public SocketChannel getS1() {
        return sc1;
    }

       
        
    public Servidor(SocketChannel sc, int atenderEn){
        dirigirA = atenderEn;
        this.sc1 = sc;
        respuestas.add("<html><head><title>Servidor 1</title></head><body bgcolor=red><center><h1>Petcion uno</h1></center></body></html>");
        respuestas.add("<html><head><title>Servidor 2</title></head><body bgcolor=blue><center><h1>Petcion dos</h1></center></body></html>");
        respuestas.add("<html><head><title>Servidor 3</title></head><body bgcolor=green><center><h1>Petcion tres</h1></center></body></html>");
        System.out.println("Servidor iniciado ");
    }
    
    public ByteBuffer atenderSolicitud(){
        ByteBuffer b = ByteBuffer.allocate(5000);
        
        String respuesta = "";
        respuesta += "HTTP/1.0 200 OK\n";   
        respuesta += "Date: " + new Date()+"\n";
        respuesta += "Content-Type: text/html \n";
        respuesta += "\n"; 
        respuesta += respuestas.get(dirigirA);
        b = ByteBuffer.wrap(respuesta.getBytes());
        return b;
    }
    
}
