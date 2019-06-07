package balanceador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.JComboBox;

/**
 *
 * @author Diego EG
 */
public class Balanceador {

    private int turno;

    public static void main(String[] args) {
        
        Balanceador b1 = new Balanceador(9000);
        
    }
    
    public Balanceador(int pto){
        turno = 0;
        try{
          ServerSocketChannel s = ServerSocketChannel.open();
          s.configureBlocking(false);
          s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
          s.socket().bind(new InetSocketAddress(pto));
          Selector sel = Selector.open();
          s.register(sel,SelectionKey.OP_ACCEPT);
           System.out.println("Servicio iniciado..esperando clientes..");
          while(true){
              sel.select();
              Iterator<SelectionKey>it= sel.selectedKeys().iterator();
              while(it.hasNext()){
                  if(turno>2){turno = 0;}
                  SelectionKey k = (SelectionKey)it.next();
                  it.remove();
                  if(k.isAcceptable()){
                      SocketChannel cl = s.accept();
                      System.out.println("Cliente conectado desde->"+cl.socket().getInetAddress().getHostAddress()+":"+cl.socket().getPort());
                      cl.configureBlocking(false);
                      cl.register(sel,SelectionKey.OP_READ);
                      Servidor servidor = new Servidor(cl,turno);
                      ByteBuffer b = servidor.atenderSolicitud();
                      turno++;
                      cl.write(b);
                      cl.close();
                      continue;
                  }//if
                    if(k.isReadable()){
                      SocketChannel ch = (SocketChannel)k.channel();
                      
                      
                      
                      continue;
                    }
              }//while
          }//while
       }catch(Exception e){
           e.printStackTrace();
       }//catch
            
    }
    
    
//    public void procesarConexiones(){
//        System.out.println("Tam "+conexiones.size());
//        conexiones.forEach((i,c)->{
//            String respuestahttp = "";
//            respuestahttp += "HTTP/1.0 200 OK\n";   
//            respuestahttp += "Date: " + new Date()+"\n";
//            respuestahttp +="Content-Type: text/html \n";
//            respuestahttp +="\n";
//            
//             if(turno==i){
//                  respuestahttp +="<html><head><title>Respuesta</title></head><body><h1>Atendiendo tu solicitud</h1></body></html>";
//                 //Atender la peticion
//             }else{
//                  respuestahttp +="<html><head><title>Respuesta</title></head><body><h1>Espera tu turno</h1></body></html>";
//             }
//            
//            try{
//                String msj =respuestahttp;
//                ByteBuffer b = ByteBuffer.wrap(msj.getBytes());
//                c.write(b);
//                //c.close();
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//      
//        });
//        }
}
