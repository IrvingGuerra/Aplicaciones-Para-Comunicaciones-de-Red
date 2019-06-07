/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro;

import static foro.ForoMulticast.MCAST_PORT;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author irving-mac
 */
public class ForoMulticast extends Thread{
    
    //Rutas WIN
    //String pathUsuarios = "F:/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/usuarios";
   // String pathTemas = "F:/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temas";
    //Rutas MAC
    String pathUsuarios = "/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/usuarios";
    String pathTemas = "/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temas";
    
    List<String> listaTemas = new ArrayList<String>();
    //Direccion clase D valida. Sera el grupo al que todos se uniran.
    public static final String MCAST_ADDR = "227.0.0.1";
    public static final int MCAST_PORT = 9014;
    public static final int DGRAM_BUF_LEN = 1024;
    InetAddress group = null;
    String hashLine = "";
    
        public void run(){
            InetAddress group = null;
            try{
                //msg=InetAddress.getLocalHost().getHostAddress();
                group = InetAddress.getByName(MCAST_ADDR); //se trata de resolver dir multicast  
                
                while(true){
                    //Espera Multicast
                    try{
                        byte[] buf = new byte[DGRAM_BUF_LEN];//crea arreglo de bytes 
                        MulticastSocket socket = new MulticastSocket(MCAST_PORT); //Creamos Socket multicast
                        socket.joinGroup(group); //Nos unimos a la direccion multicast. Se configura para escuchar el paquete
                        DatagramPacket recv = new DatagramPacket(buf,buf.length);//crea el datagram packet a recibir
                        socket.receive(recv);// ya se tiene el datagram packet
                        
                        String data = new String(recv.getData());
                        data = data.trim();
                        String partes[] = data.split("<>");
                        String instruccion = partes[0];
                        BufferedReader reader;
                        switch(instruccion){
                            case "1":
                                System.out.println("Instruccion 1 - REGISTRO");
                                try {
                                    reader = new BufferedReader(new FileReader(pathUsuarios+"/users.txt"));
                                    hashLine = reader.readLine();
                                    hashLine = hashLine.substring(1, hashLine.length()-1);
                                    Map<String, String> usuarioHash = new HashMap<String, String>();
                                    String[] pairs = hashLine.split(", ");
                                    for (int i=0;i<pairs.length;i++) {
                                        String pair = pairs[i];
                                        String[] keyValue = pair.split("=");
                                        usuarioHash.put(keyValue[0], keyValue[1]);
                                    }
                                    //Verificamos que no exista un usuario con el mimso nombre
                                    if(usuarioHash.containsKey(partes[1])){
                                        System.out.println("Usuario existe");
                                        String respuesta = "2<>Registro-FAIL";
                                        DatagramPacket packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                        socket.send(packet);
                                    }else{
                                        System.out.println("Registrando usuario");
                                        usuarioHash.put(partes[1], partes[2]);
                                        String respuesta = "1<>Registro-OK";
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(pathUsuarios+"/users.txt"));
                                        writer.write(usuarioHash.toString());
                                        writer.close();
                                        DatagramPacket packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                        socket.send(packet);
                                    }
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            break;
                            case "2":
                                System.out.println("Instruccion 2 - LOGIN");
                                try {
                                    reader = new BufferedReader(new FileReader(pathUsuarios+"/users.txt"));
                                    hashLine = reader.readLine();
                                    hashLine = hashLine.substring(1, hashLine.length()-1);
                                    Map<String, String> usuarioHash = new HashMap<String, String>();
                                    String[] pairs = hashLine.split(", ");
                                    for (int i=0;i<pairs.length;i++) {
                                        String pair = pairs[i];
                                        String[] keyValue = pair.split("=");
                                        usuarioHash.put(keyValue[0], keyValue[1]);
                                    }
                                    //Verificamos que exista un usuario con el mimso nombre
                                    if(usuarioHash.containsKey(partes[1])){
                                        System.out.println("Usuario existe");
                                        //Verificamos contraseña
                                        String passwordBD = usuarioHash.get(partes[1]).toString();
                                        if(passwordBD.equals(partes[2])){
                                            String respuesta = "3<>Login-OK:"+partes[1];
                                            DatagramPacket packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                            socket.send(packet);
                                        }else{
                                            String respuesta = "4<>Login-NO-PASS";
                                            DatagramPacket packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                            socket.send(packet);
                                        }
                                    }else{
                                        System.out.println("Usuario inexistente");
                                        String respuesta = "5<>Login-NO-EXIST";
                                        DatagramPacket packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                        socket.send(packet);
                                    }
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            break;
                            case "3":
                                System.out.println("Instruccion 3 - PIDE TODOS LOS TEMAS");
                                listaTemas.clear();
                                loadPathFolders(pathTemas);
                                String respuesta = "6<>"+listaTemas.toString();
                                DatagramPacket packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                socket.send(packet);
                            break;
                            case "4":
                                System.out.println("Instruccion 4 - PIDE UN TEMA");
                                //Primero transmitimos el file
                                System.out.println("Tema por cargar: "+partes[1]+"/discucion/discucion.txt");
                            break;
                            case "5":
                                System.out.println("Instruccion 5 - Publicacion de mensaje en tema");
                                FileReader fileReader = new FileReader(partes[1]+"/discucion/discucion.txt");
                                BufferedReader bufferedReader = new BufferedReader(fileReader);
                                String discucion;
                                try {
                                    discucion = bufferedReader.readLine();
                                    String[] partesDiscucion = discucion.split("<>");
                                    int numero = Integer.valueOf(partesDiscucion[0]);
                                    int newNum = numero;
                                    newNum++;
                                    String mensajeUpdate = newNum+"<>";
                                    for(int i=1 ; i <= numero ; i++){
                                        mensajeUpdate+=partesDiscucion[i]+"<>";
                                    }
                                    mensajeUpdate+=partes[2]+"º"+partes[3];
                                    respuesta = "7<>"+partes[1];
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(partes[1]+"/discucion/discucion.txt"));
                                    writer.write(mensajeUpdate);
                                    writer.close();
                                    packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                    socket.send(packet);

                                } catch (IOException ex) {
                                    Logger.getLogger(foro.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            break;
                            case "8":
                                String tema = partes[1];
                                System.out.println("Instruccion 8 - Publicacion de tema: "+tema);
                                
                                String newFullPathDisc = pathTemas+"/"+tema+"/discucion";
                                String newFullPathImg = pathTemas+"/"+tema+"/img";
                                //System.out.println("Crenado: "+newFullPath);
                                File theDir1 = new File(newFullPathDisc);
                                theDir1.mkdirs();
                                File theDir2 = new File(newFullPathImg);
                                theDir2.mkdirs();

                                BufferedWriter writer = new BufferedWriter(new FileWriter(newFullPathDisc+"/discucion.txt"));
                                writer.write("0<>");
                                writer.close();
                                listaTemas.clear();
                                loadPathFolders(pathTemas);
                                respuesta = "10<>"+listaTemas.toString();
                                packet = new DatagramPacket(respuesta.getBytes(),respuesta.length(),group,MCAST_PORT);
                                socket.send(packet);
                                        
                            break;
                        }
                        socket.close();    		
                    }catch(IOException e){
                        e.printStackTrace();
                        System.exit(2);
                    }
                    try{
                        Thread.sleep(1000*5);
                    }catch(InterruptedException ie){}
                }
                
            }catch(UnknownHostException e){
                e.printStackTrace();
                System.exit(1);
            }
        }

    public static void main(String[] args) {
        try{
	    ForoMulticast foro = new ForoMulticast();
	    foro.start();
            System.out.println("[OK] Servidor Multicast iniciado. Esperando clientes...");
            ForoUnicast foro2 = new ForoUnicast();
            foro2.start();
	}catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void loadPathFolders(String pathTemas) {
        File folder = new File(pathTemas);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (validateDateFormat(file.getName())) {
                loadPathFolders(pathTemas+"/"+file.getName());
            }else if(file.isDirectory()){
                listaTemas.add(file.getAbsolutePath());
            }
        }
    }
    
    public static boolean validateDateFormat(String strDate){
	if (strDate.trim().equals("")){
	    return true;
	}
	else{
	    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd-MM-yyyy");
	    sdfrmt.setLenient(false);
	    try{
	        Date javaDate = sdfrmt.parse(strDate); 
	    }
	    catch (ParseException e){
	        return false;
	    }
	    return true;
	}
   }

}

class ForoUnicast extends Thread {
    int puerto = 1234;
    String host = "127.0.0.1";
    
    ServerSocket s;
    Socket cl;
    
    //Rutas MAC
    String pathUsuarios = "/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/usuarios";
    String pathTemas = "/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temas";
    //Rutas WIN
    //String pathUsuarios = "F:/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/usuarios";
    //String pathTemas = "F:/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temas";
    
    public void run() {
        startService();
        esperaPeticionUnicast();
    }
    private void startService() {
        try{
            s = new ServerSocket(puerto);
            s.setReuseAddress(true);
            System.out.println("[OK] Servicio Unicast Iniciado, esperando cliente...");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void esperaPeticionUnicast() {
        while(true){
            try {
                cl = s.accept();
                //System.out.println("[ OK ] Cliente conectado desde: "+ cl.getInetAddress()+":"+ cl.getPort());
                //Leemos la entrada
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                String data = (String) dis.readUTF();
                //UTF TRAE LA INSTRUCCION () 1 TRANSFIERE ---  2 RECIBE
                String pts[] = data.split("<>");
                String instruccion = pts[0];
                switch(instruccion){
                    case "REGISTRO":
                        System.out.println("[OK] Instruccion REGISTRO");
                        String dataUser = (String) dis.readUTF();
                        String dataU[] = dataUser.split("<>");
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(pathUsuarios+"/users.txt"));
                            String hashLine = reader.readLine();
                            hashLine = hashLine.substring(1, hashLine.length()-1);
                            Map<String, String> usuarioHash = new HashMap<String, String>();
                            String[] pairs = hashLine.split(", ");
                            for (int i=0;i<pairs.length;i++) {
                                String pair = pairs[i];
                                String[] keyValue = pair.split("=");
                                usuarioHash.put(keyValue[0], keyValue[1]);
                            }
                            //Verificamos que no exista un usuario con el mimso nombre
                            if(usuarioHash.containsKey(dataU[0])){
                                System.out.println("Usuario existe");
                                String respuesta = "Registro-FAIL";
                                //Enviamos la respuesta
                                DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                                dos.writeUTF(respuesta); //Envia el nombre
                                dos.flush();
                            }else{
                                System.out.println("Registrando usuario");
                                usuarioHash.put(dataU[0], dataU[1]);
                                String respuesta = "Registro-OK";
                                BufferedWriter writer = new BufferedWriter(new FileWriter(pathUsuarios+"/users.txt"));
                                writer.write(usuarioHash.toString());
                                writer.close();
                                DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                                dos.writeUTF(respuesta); //Envia el nombre
                                dos.flush();
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    break;
                    case "LOGIN":
                        System.out.println("[OK] Instruccion LOGIN");
                        String dataLoginUser = (String) dis.readUTF();
                        String dataLoginU[] = dataLoginUser.split("<>");
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(pathUsuarios+"/users.txt"));
                            String hashLine = reader.readLine();
                            hashLine = hashLine.substring(1, hashLine.length()-1);
                            Map<String, String> usuarioHash = new HashMap<String, String>();
                            String[] pairs = hashLine.split(", ");
                            for (int i=0;i<pairs.length;i++) {
                                String pair = pairs[i];
                                String[] keyValue = pair.split("=");
                                usuarioHash.put(keyValue[0], keyValue[1]);
                            }
                            //Verificamos que exista un usuario con el mimso nombre
                            if(usuarioHash.containsKey(dataLoginU[0])){
                                System.out.println("Usuario existe");
                                //Verificamos contraseña
                                String passwordBD = usuarioHash.get(dataLoginU[0]).toString();
                                if(passwordBD.equals(dataLoginU[1])){
                                    String respuesta = "Login-OK:"+dataLoginU[0];
                                    //Enviamos la respuesta
                                    DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                                    dos.writeUTF(respuesta); //Envia el nombre
                                    dos.flush();
                                }else{
                                    String respuesta = "Login-NO-PASS";
                                    //Enviamos la respuesta
                                    DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                                    dos.writeUTF(respuesta); //Envia el nombre
                                    dos.flush();
                                }
                            }else{
                                System.out.println("Usuario inexistente");
                                String respuesta = "Login-NO-EXIST";
                                //Enviamos la respuesta
                                DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
                                dos.writeUTF(respuesta); //Envia el nombre
                                dos.flush();
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    break;
                    case "1":
                        String pathFile = pts[1]; 
                        //System.out.println("[ OK ] Transfieriendo archivo..."); 
                        File f = new File(pathFile);
                        String name = f.getName();
                        long tam = f.length();
                        String path = f.getAbsolutePath();
                        //System.out.println("[ OK ] Enviando archivo: " + name + " que mide " + tam + " bytes\n");
                        DataOutputStream dos = new DataOutputStream (cl.getOutputStream());
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
                        dis.close();
                        cl.close(); 
                    break;
                    case "2":
                        //Subimos el file
                        dis = new DataInputStream(cl.getInputStream());
                        //Recibimos el file.
                        String nombre = (String) dis.readUTF();
                        tam = (long) dis.readLong();
                        //Obetenemos unicamente el nombre:
                        //Necesitamos obtener el numero de imagenes que hay guardadas
                        int numeroImages = 0;
                        f = new File(pts[1]+"/img");
                        File[] files = f.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            numeroImages++;
                        }
                        //System.out.println("EXISTEN ...> "+numeroImages+" NUMERO DE IMAGENES");
                        numeroImages++;
                        DataOutputStream dosFile = new DataOutputStream (new FileOutputStream(pts[1]+"/img/img"+numeroImages+".jpeg"));
                        b = new byte [1500];
                        long recibidos = 0;
                        int porciento_recibido = 0;
                        n=0;
                        while (recibidos < tam){
                            n = dis.read(b);
                            dosFile.write(b, 0, n);
                            dosFile.flush();
                            recibidos +=n;
                            porciento_recibido = (int)((recibidos*100)/tam);
                            //System.out.println("[ OK ] Recibido el " + porciento_recibido + "%");
                        }
                        dosFile.close();
                        dis.close();
                        System.out.println("[ OK ] Archivo recibido");
                    break;
                }
                        
                
                
            } catch (IOException ex) {
                Logger.getLogger(ForoUnicast.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}
