
package balanceador;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Diego EG
 */
public class RoundRobin implements Runnable{
    
    ArrayList<String> things = new ArrayList();
    

    public static void main(String[] args) {
        RoundRobin rbd = new RoundRobin();
        
    }
    
    //Primera prueba
//    public RoundRobin(){
//       Scanner teclado = new Scanner(System.in);
//        for (;  ; ) {
//            System.out.println("Ingresa una cosa");
//            String thing = teclado.nextLine();
//            things.add(thing);
//            Thread t = new Thread(this);
//            t.start();
//        }
//        
//        
//    }
    
    public RoundRobin(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingresa la cantidad de elementos para rr ");
        int elementos = teclado.nextInt();
        
        for (int i = 1; i <= elementos; i++) {
            System.out.println("Ingresa la cosaa ["+i+"]");
            String cosa = teclado.next();
            things.add(cosa);

        }
        System.out.println("Entrando a round robin ");
        Thread rr = new Thread(this);
        rr.start();
        
        
    }
    
    @Override
    public void run() {
        System.out.println("Tamaño "+ things.size());
        int j = 0 ;
//        for (int i = 0; i < things.size(); i++) {
//           
//            if((i+1)==(things.size())){
//                i = 0;
//            }
//        }
        while(j<things.size()){
            System.out.println("Elemento -> "+things.get(j));
            
            if((j+1)==things.size()){
                j=0;
            }else{
                j++;
            }
            
        }
        
    }
    
}
