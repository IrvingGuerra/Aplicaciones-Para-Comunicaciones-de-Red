

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author irving-mac
 */
public class cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String cadena = new String();
        String cadena = new String();
        ArrayList <Character> adivinadas = new ArrayList();




        try{
            int puerto = 9000;
            String host = "localhost";
            Socket cl = new Socket(host,puerto);
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            dos.writeBytes("1");
            dos.flush();
            int pto = 9000;
            DataInputStream dis = new DataInputStream(cl.getInputStream());
            int i=0,n=0;
            while(i==0){
            byte[] b = new byte[15];
            n = dis.read(b);
            System.out.println("Cadena recibida");
            System.out.println("Longitud: " + n);
            cadena = new String(b);
            cadena = cadena.split("\0")[0];
            if (n!=0) {
              i=1;
            }

            }
        }catch(Exception e){
            e.printStackTrace();
        }


        boolean adivinada = false;
        int oportunidades = 0;
        int muertes=0;
        int vidas = 3;
        while(adivinada==false){
            muertes=0;
            vidas=2;
            if (oportunidades!=0) {
                for (int i = 0; i < adivinadas.size(); i++) {
                    if (!cadena.contains(adivinadas.get(i)+"")) {
                        muertes++;
                    }
                }
                vidas=vidas-muertes;
                if (vidas<0) {
                    System.out.println("Perdiste :(");
                    break;
                }
            }
            System.out.println("Tienes "+(vidas+1)+" oportunidades");
            int bandera=0;
            for (int i = 0; i < cadena.length(); i++) {
                if (adivinadas.contains(cadena.charAt(i))) {
                    System.out.print(cadena.charAt(i));
                    System.out.print("    ");
                    bandera+=1;
                }
                else{
                    System.out.print("-");
                    System.out.print("    ");
                }
            }




            if (bandera==cadena.length()) {
                System.out.println("¡Victoria!");
                adivinada=true;
            }
            else{
                System.out.println("");
                System.out.println("Ingresa una letra: ");
                Scanner sc = new Scanner(System.in);
                String caract;
                caract = sc.nextLine();
                char caracter = caract.charAt(0);
                if (caract.length()==1) {
                    adivinadas.add(caracter);
                }
                else{
                    System.out.println("Solo se tomará en cuenta la letra: "+caracter);
                }
            }
            oportunidades++;
        }

    }

}
