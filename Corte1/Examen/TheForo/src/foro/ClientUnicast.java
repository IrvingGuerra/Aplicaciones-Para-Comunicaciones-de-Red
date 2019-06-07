package foro;

import static foro.ClientMulticast.DGRAM_BUF_LEN;
import static foro.ClientMulticast.MCAST_ADDR;
import static foro.ClientMulticast.MCAST_PORT;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author irving-mac
 */
public class ClientUnicast extends Thread{
    int puerto = 1234;
    String host = "127.0.0.1";
    
    Socket cl;
    
    registro frameRegistro;
    login frameLogin;
    foro frameForo;
    
    public ClientUnicast(registro reg, login log, foro forof){
        frameRegistro = reg;
        frameLogin = log;
        frameForo = forof;
    }
    
    public void run(){}

    void send(String instruccionData) {
        connectWithServer();
        String[] partes = instruccionData.split("<>");
        switch(partes[0]){
            case "1":
                try{
                    System.out.println("[OK] send - Registro");
                    DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                    //Enviamos la instruccion
                    dos.writeUTF("REGISTRO<>");
                    dos.flush();
                    //Enviamos los datos
                    dos.writeUTF(partes[1]+"<>"+partes[2]);
                    dos.flush();
                    //Ya enviado, esperamos la respuesta
                    DataInputStream dis = new DataInputStream(cl.getInputStream());
                    String respuesta = (String) dis.readUTF();
                    switch(respuesta){
                        case "Registro-FAIL":
                            JOptionPane.showMessageDialog(frameRegistro, "Nombre de usuario existente. Elija otro.");
                        break;
                        case "Registro-OK":
                            JOptionPane.showMessageDialog(frameRegistro, "Usuario registrado con exito.");
                        break;
                    }
                    
                } catch (IOException ex) {
                    System.out.println(ex);
                    showMessageDialog(null, "Ocurrio un problema al enviar registro");
                }
            break;
            case "2":
                try{
                    System.out.println("[OK] send - Login");
                    DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                    //Enviamos la instruccion
                    dos.writeUTF("LOGIN<>");
                    dos.flush();
                    //Enviamos los datos
                    dos.writeUTF(partes[1]+"<>"+partes[2]);
                    dos.flush();
                    //Ya enviado, esperamos la respuesta
                    DataInputStream dis = new DataInputStream(cl.getInputStream());
                    String respuesta = (String) dis.readUTF();
                    switch(respuesta){
                        case "Login-NO-EXIST":
                            JOptionPane.showMessageDialog(frameLogin, "Nombre de usuario inexistente.");
                        break;
                        case "Login-NO-PASS":
                            JOptionPane.showMessageDialog(frameLogin, "Contraseña incorrecta.");
                        break;
                        default:
                            frameLogin.openForo(respuesta);
                        break;
                    }
                    
                } catch (IOException ex) {
                    System.out.println(ex);
                    showMessageDialog(null, "Ocurrio un problema al enviar registro");
                }
            break;
            case "4":
                    try {
                        //System.out.println("[ OK ] Pidiendo archivo: " + partes[1]);
                        DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                        //Enviando el archvio
                        dos.writeUTF("1<>"+partes[1]); //Enviamos el file que queremos
                        dos.flush();
                        //Ya enviado, esperamos la respuesta
                        DataInputStream dis = new DataInputStream(cl.getInputStream());
                        //Recibimos el file.
                        String nombre = (String) dis.readUTF();
                        long tam = (long) dis.readLong();
                        //Obetenemos unicamente el nombre:
                        String[] parteRuta = partes[1].split("/");
                        String justName = parteRuta[parteRuta.length-1];
                        String myPath = partes[1].replace("temas", "temasClient").replace(justName, "");
                        //System.out.println(myPath);
                        //Verificaremos si ya existe ese path en el cliente
                        File theDir = new File(myPath);
                        if (!theDir.exists()) {
                            //System.out.println("[ OK ] Creando directorio: " + theDir.getName());
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
                        DataOutputStream dosFile = new DataOutputStream (new FileOutputStream(myPath+"/"+nombre));
                        byte [] b = new byte [1500];
                        long recibidos = 0;
                        int porciento_recibido = 0, n=0;
                        while (recibidos < tam){
                            n = dis.read(b);
                            dosFile.write(b, 0, n);
                            dosFile.flush();
                            recibidos +=n;
                            porciento_recibido = (int)((recibidos*100)/tam);
                            //System.out.println("[ OK ] Recibido el " + porciento_recibido + "%");
                        }
                        dos.close();
                        dosFile.close();
                        dis.close();
                        cl.close();
                        System.out.println("[ OK ] Archivo recibido");
                    } catch (IOException ex) {
                        System.out.println(ex);
                        showMessageDialog(null, "Ocurrio un problema al pedir el archvio");
                    }
            break;
            case "5":
                    try {
                        //System.out.println("[ OK ] Subiendo una imagen: " + partes[1]);
                        DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                        //Enviando el archvio
                        dos.writeUTF("2<>"+partes[2]); //Indicamos que subiremos un file
                        dos.flush();
                        String pathFile = partes[1]; 
                        //System.out.println("[ OK ] Transfieriendo archivo..."); 
                        File f = new File(pathFile);
                        String name = f.getName();
                        long tam = f.length();
                        String path = f.getAbsolutePath();
                        //System.out.println("[ OK ] Enviando archivo: " + name + " que mide " + tam + " bytes\n");
                        dos = new DataOutputStream (cl.getOutputStream());
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
                            //System.out.println("[ SEND ] Trasmitido el " + porciento + "%");
                        }
                        disFile.close();
                        dos.close();
                        cl.close();
                        System.out.println("[ OK ] Archvio enviado con exito");
                    } catch (IOException ex) {
                        System.out.println(ex);
                        showMessageDialog(null, "Ocurrio un problema al enviar el archvio");
                    }
            break;
        }
        
    }

    private void connectWithServer() {
        try{
            cl = new Socket (host, puerto); //socket bloquante
        }catch(Exception e){
            showMessageDialog(null, "Ocurrio un problema con el servidor");
        }
    }
}
