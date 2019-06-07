/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro;

import com.sun.awt.AWTUtilities;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


/**
 *
 * @author alumno
 */
public class foro extends javax.swing.JFrame {
    
    int x,y;
    
    /******** Rutas MAC ******/
    String pathlogoESCOM = "file:/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/img/logoESCOM.png";
    String pathTemas = "/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temasClient";
    String pathTemasServer = "/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temas/";
    /******** Rutas WIN ******/
    //String pathlogoESCOM = "file:F:/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/img/logoESCOM.png";
    //String pathTemas = "F:/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temasClient";
    //String pathTemasServer = "F:/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temas";
    

    /******** Creamos comunicacion UNICAST Y MULTICAST ********/
    ClientMulticast ClientMulticast = new ClientMulticast(null,null,this);
    ClientUnicast ClientUnicast = new ClientUnicast(null,null,this);
    
    /******** Persona loggeada *********/
    String username;
    
    /******** Mensaje a enviar a x TEMA*********/
    String mensajeToSend = "";
    
    String palabraABuscar = "";
    String buscarPadre = "";
    
    /******** Variables de arbol para visualizacion de TEMAS *********/
    private DefaultMutableTreeNode rootTemas;
    private DefaultTreeModel treeModelTemas;
    private JTree treeTemas;
    private String temaSeleccionado = "";
    
    /******** Numero de imagenes que tiene un Tema seleccionado *********/
    int imgNumber = 1;
    
    
    public foro(String name) {

        initComponents();
        AWTUtilities.setWindowOpaque(this, false);
        this.setLocationRelativeTo(null);
        /******** Se asigna el username *********/
        username = name;
        lblTitleName.setText("Hola: "+username);

        txtTemaSelected.setContentType("text/html");
        txtTemaSelected.setEditable(false);
        txtTemaSelected.setText("");
        
        txtComment.setContentType("text/html");
        txtComment.setEditable(false);
        txtComment.setText("");
        
        ClientMulticast.start();
        ClientUnicast.start();
        
        txtTemaSelected.setText(
        "Bienvenido<br><br>"
        + "<img src='"+pathlogoESCOM+"' width='140' height='100'></img>"
        );
        /******** Se piden todos los temas *********/
        ClientMulticast.send("3");
        
    }
    
    public foro() {
        initComponents();
        AWTUtilities.setWindowOpaque(this, false);
        this.setLocationRelativeTo(null);
        /******** Se asigna el username *********/
        username = "Irving";
        lblTitleName.setText("Hola: "+username);

        txtTemaSelected.setContentType("text/html");
        txtTemaSelected.setEditable(false);
        txtTemaSelected.setText("");
        
        txtComment.setContentType("text/html");
        txtComment.setEditable(false);
        txtComment.setText("");
        
        ClientMulticast.start();
        ClientUnicast.start();
        
        txtTemaSelected.setText(
        "Bienvenido<br><br>"
        + "<img src='"+pathlogoESCOM+"' width='140' height='100'></img>"
        );
        /******** Se piden todos los temas *********/
        ClientMulticast.send("3");
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanaTemas = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTemaSelected = new javax.swing.JEditorPane();
        lblTitle = new javax.swing.JLabel();
        lblTitleName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComment = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        lblSendComment = new javax.swing.JButton();
        btnUploadImg = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        txtAddTema = new javax.swing.JTextField();
        btnAddTema = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ventanaTemas.setVisible(true);
        getContentPane().add(ventanaTemas, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 230, 300));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit2.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, -1, -1));

        jScrollPane1.setViewportView(txtTemaSelected);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 520, 280));

        lblTitle.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        lblTitle.setText("FORO-ESCOM");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 350, 50));

        lblTitleName.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        lblTitleName.setText("Hola: Usuario");
        getContentPane().add(lblTitleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, -1, -1));

        txtComment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCommentKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCommentKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCommentKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtComment);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 510, 80));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel2.setText("Agregar comentario");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, -1, -1));

        lblSendComment.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        lblSendComment.setText(">");
        lblSendComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblSendCommentActionPerformed(evt);
            }
        });
        getContentPane().add(lblSendComment, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 60, 80));

        btnUploadImg.setText("Seleccionar imagen");
        btnUploadImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadImgActionPerformed(evt);
            }
        });
        getContentPane().add(btnUploadImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 200, 30));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 210, 30));
        getContentPane().add(txtAddTema, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 430, 160, 20));

        btnAddTema.setText("+");
        btnAddTema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTemaActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddTema, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 420, 40, 40));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/wall4.jpg"))); // NOI18N
        lblFondo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblFondoMouseDragged(evt);
            }
        });
        lblFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblFondoMousePressed(evt);
            }
        });
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 810, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblFondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFondoMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_lblFondoMousePressed

    private void lblFondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFondoMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_lblFondoMouseDragged

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        this.dispose();
        new login().setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void lblSendCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblSendCommentActionPerformed
        /**********************BTN ENVIAR COMENTARIO******************************/
        if(temaSeleccionado != ""){
            //Obtenemos el mensaje del Area de texto
            String editMsj = mensajeToSend;
            //Se limpia el mensaje de todo dato HTML
            
            //Necesitamos obtener el numero de imagenes que hay guardadas
            int numeroImages = 0;
            File f = new File(temaSeleccionado.replace("temas", "temasClient")+"/img");
            File[] files = f.listFiles();
            try{
               int tam = files.length;
               if(files.length!=0){
                    for (int i = 0; i < files.length; i++) {
                       numeroImages++;
                    }
                }
            }catch(Exception e){
                System.out.println("No hay imagenes");
            }
            
            System.out.println("EXISTEN ...> "+numeroImages+" NUMERO DE IMAGENES");
            //Dividimos el mensaje por "
            String pts[] = editMsj.split("'");
            int j=0;
            for(int i=0;i<pts.length;i++){
                System.out.println(pts[i]);
                if(pts[i].contains("file:")){
                   j++;
                }
            }
            String images[] = new String[j];
            j=0;
            String conversacionImages = "";
            for(int i=0;i<pts.length;i++){
                if(pts[i].contains("file:")){
                   numeroImages++;
                   //Primero en el mensaje cambiamos todo por imgClient0
                   System.out.println(pts[i]);
                   editMsj = editMsj.replace("<img src='"+pts[i]+"' width='40' height='40'>", "imgClient"+numeroImages);
                   //Generamos lo que va al final de la conversascion
                   conversacionImages+=":"+temaSeleccionado.replace("temas", "temasClient")+"/img/img"+numeroImages+".jpeg";
                   //Generamos array de imagenes a pedir
                    System.out.println("Iamges: "+ images[j]);
                   images[j]=pts[i].replace("file:", "").replace("/Volumes/BLUE/ESCOM/7/Redes2/Corte%201/Examen%20-%20Foro/Foro/build/classes/", "/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/");
                   //Iremos guardando las imagenes al servidor
                   ClientUnicast.send("5<>"+images[j]+"<>"+temaSeleccionado);
                   j++;
                }
            }
            String mensajeSave = editMsj.trim()+conversacionImages;
          
            System.out.println(mensajeSave);
            ClientMulticast.send("5<>"+temaSeleccionado+"<>"+username+"<>"+mensajeSave);
            txtComment.setText("");
            
        }else{
            showMessageDialog(null, "Abra una discucion para enviar un mensaje");
        }
    }//GEN-LAST:event_lblSendCommentActionPerformed

    private void btnUploadImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadImgActionPerformed
        /**********************BTN SUBIR IMAGEN******************************/
        if(temaSeleccionado != ""){
            JFileChooser jf = new JFileChooser(); 
            //jf.setMultiSelectionEnabled(true);//seleccionar uno o mas
            int r = jf.showOpenDialog(null);
               jf.requestFocus();
            if (r  == JFileChooser.APPROVE_OPTION){
                File f = jf.getSelectedFile(); //me permite escoger un archivo
                String path = f.getAbsolutePath();
                mensajeToSend+="<img src='file:"+path+"' width='40' height='40'></img>";
                txtComment.setText(mensajeToSend);
                mensajeToSend = mensajeToSend.replaceAll("\"", "'");
                System.out.println("Mensaje a enviar: "+mensajeToSend);
            }
        }else{
            showMessageDialog(null, "Abra una discucion para seleccionar una imagen");
        }
    }//GEN-LAST:event_btnUploadImgActionPerformed

    private void txtCommentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCommentKeyTyped
 
    }//GEN-LAST:event_txtCommentKeyTyped

    private void txtCommentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCommentKeyPressed

    }//GEN-LAST:event_txtCommentKeyPressed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        palabraABuscar = txtBuscar.getText();
        System.out.println("Palabra a buscar: "+palabraABuscar);
        if(palabraABuscar.equals("") || palabraABuscar.equals(" ") ){
            System.out.println("Carga todos");
            loadTemasByDefault();
        }else{
            if(validateDateFormat(palabraABuscar)){
                System.out.println("Busca fecha");
                browseDate();
            }else{
                System.out.println("Busca Tema");
                browseTema();
            }
            
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnAddTemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTemaActionPerformed
        String tema = txtAddTema.getText();
        if(tema.length()>4){
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String temaNuevo = dateFormat.format(date)+"/"+tema;
            ClientMulticast.send("8<>"+temaNuevo);
        }else{
            showMessageDialog(null, "Tema muy corto");
        }
    }//GEN-LAST:event_btnAddTemaActionPerformed

    private void txtCommentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCommentKeyReleased
        mensajeToSend = txtComment.getText();
        mensajeToSend = mensajeToSend.replace("\n", ""); 
        mensajeToSend = mensajeToSend.replace("<html>", "").replace("</html>", "");
        mensajeToSend = mensajeToSend.replace("<head>", "").replace("</head>", "");
        mensajeToSend = mensajeToSend.replace("<body>", "").replace("</body>", "");
        mensajeToSend = mensajeToSend.replace("<p style=\"margin-top: 0\">", "").replace("</p>", "");
        mensajeToSend = mensajeToSend.trim();
        mensajeToSend = mensajeToSend.replaceAll("\"", "'");
        System.out.println("Mensaje a enviar: "+mensajeToSend);
        
    }//GEN-LAST:event_txtCommentKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new foro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTema;
    private javax.swing.JButton btnUploadImg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JButton lblSendComment;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleName;
    private javax.swing.JTextField txtAddTema;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JEditorPane txtComment;
    private javax.swing.JEditorPane txtTemaSelected;
    private javax.swing.JInternalFrame ventanaTemas;
    // End of variables declaration//GEN-END:variables
    
    void loadTemasByDefault() {
        File fileRoot = new File(pathTemas);
        rootTemas = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModelTemas = new DefaultTreeModel(rootTemas);
        treeTemas = new JTree(treeModelTemas);
        treeTemas.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(treeTemas);
        ventanaTemas.getContentPane().removeAll();
        ventanaTemas.add(scrollPane);
        ventanaTemas.setSize(400, 240);
        ventanaTemas.setVisible(true);
        CreateChildNodes ccn = new CreateChildNodes(fileRoot, rootTemas);
        new Thread(ccn).start();
        treeTemas.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                /**********************AL ABRIR UN TEMA******************************/
                temaSeleccionado = "";
                Object[] paths = treeTemas.getSelectionPath().getPath();
                for (int i=0; i<paths.length; i++) {
                    temaSeleccionado += paths[i];
                    if (i+1 <paths.length ) {
                        temaSeleccionado += File.separator;
                    }
                }
                temaSeleccionado = temaSeleccionado.replace("temasClient/", "");
                temaSeleccionado = temaSeleccionado.replace("temasClient", "");
                //Si es un date; NO LO ABRE
                boolean date = validateDateFormat(temaSeleccionado);
                temaSeleccionado = pathTemasServer + temaSeleccionado;
                if(date == false){
                    openTemaInViewer(temaSeleccionado);
                }
            }

            
        });
    }

    void loadTemasByDefault(String paths){
        String[] directory = paths.split(", ");
        for(int i=0;i<directory.length;i++){
            directory[i] = directory[i].replace("temas", "temasClient");
            new File(directory[i]).mkdirs();
        }
        loadTemasByDefault();
    }

    void updateTemaIfYouAreIn(String tema){
        if(temaSeleccionado.equals(tema)){
           openTemaInViewer(temaSeleccionado);
        }
    }
    
    private void openTemaInViewer(String temaSeleccionado) {
        
        /**********************EMPEZA A ABRIR EL TEMA******************************/
        //Reinicia numero de imagenes
        imgNumber = 1;
        //Instruccion 4 pide file de toda la discucion
        ClientUnicast.send("4<>"+temaSeleccionado+"/discucion/discucion.txt"); 
        //Tambien se pediran todas las imagenes de la discucion
        File folder = new File(temaSeleccionado+"/img");
        for (final File fileEntry : folder.listFiles()) {
            ClientUnicast.send("4<>"+fileEntry.getAbsolutePath()); 
        }
        /***********Empezamos con el listado************/
        FileReader fileReader;
        String discucion = "";
        try {
            fileReader = new FileReader(temaSeleccionado+"/discucion/discucion.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            discucion = bufferedReader.readLine();
        } catch (FileNotFoundException ex) {
            showMessageDialog(null, "No se pudo leer la discucion");
        } catch (IOException ex) {
            showMessageDialog(null, "No se pudo leer la linea");
        }
        
        /*Una vez teniendo el texto*/
        String[] partesDiscucion = discucion.split("<>");
        int numero = Integer.valueOf(partesDiscucion[0]);
        String partesMensaje [];
        String nombre = "";
        String mensaje = "";
        String ptImagen [];
        txtTemaSelected.setText("");
        String allTemeMensajes = "<table width='100%' border='1' style='margin-bottom:10px'>"
                                    + "<tr> <td width='100%' colspan='2'> FORO DE DISCUCION: <strong> "+temaSeleccionado.replace("/Volumes/BLUE/ESCOM/7/Redes2/Corte 1/Examen - Foro/Foro/src/temas/", mensaje)+"</strong> </td></tr>"
                              +"</table>";
        int i = 1;
        int j = 1;
        for(i=1 ; i <= numero ; i++){
            partesMensaje = partesDiscucion[i].split("º");
            nombre = "<strong>"+partesMensaje[0]+"<strong> dijo:<br>";
            mensaje = partesMensaje[1];
            //Ahora del mensaje tenemos que ver si tiene imagenes
            ptImagen = mensaje.split(":");
            for(j=1 ; j<ptImagen.length ; j++){
                String img = "imgClient"+imgNumber;
                imgNumber++;
                ptImagen[0] = ptImagen[0].replace(img,"<img src='file:"+ptImagen[j]+"' width='140' height='100'></img>");
            }
            allTemeMensajes+="<table width='100%' border='1' style='margin-bottom:10px'>"
                + "<tr> <td width='20%'>"+nombre+"</td> <td width='80%'>"+ptImagen[0]+"</td> </tr>"
          +"</table>";
        }
        txtTemaSelected.setText(allTemeMensajes);
        txtComment.setEditable(true);
    }


    private void browseTema() {
        File fileRoot = new File(pathTemas);
        rootTemas = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModelTemas = new DefaultTreeModel(rootTemas);
        treeTemas = new JTree(treeModelTemas);
        treeTemas.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(treeTemas);
        ventanaTemas.getContentPane().removeAll();
        ventanaTemas.add(scrollPane);
        ventanaTemas.setSize(400, 240);
        ventanaTemas.setVisible(true);
        CreateChildNodesByName ccn = new CreateChildNodesByName(fileRoot, rootTemas);
        new Thread(ccn).start();
        treeTemas.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                /**********************AL ABRIR UN TEMA******************************/
                temaSeleccionado = "";
                Object[] paths = treeTemas.getSelectionPath().getPath();
                for (int i=0; i<paths.length; i++) {
                    temaSeleccionado += paths[i];
                    if (i+1 <paths.length ) {
                        temaSeleccionado += File.separator;
                    }
                }
                temaSeleccionado = temaSeleccionado.replace("temasClient/", "");
                temaSeleccionado = temaSeleccionado.replace("temasClient", "");
                //Si es un date; NO LO ABRE
                boolean date = validateDateFormat(temaSeleccionado);
                temaSeleccionado = pathTemasServer + temaSeleccionado;
                if(date == false){
                    openTemaInViewer(temaSeleccionado);
                }
            }

            
        });
    }
    private void browseDate() {
        File fileRoot = new File(pathTemas);
        rootTemas = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModelTemas = new DefaultTreeModel(rootTemas);
        treeTemas = new JTree(treeModelTemas);
        treeTemas.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(treeTemas);
        ventanaTemas.getContentPane().removeAll();
        ventanaTemas.add(scrollPane);
        ventanaTemas.setSize(400, 240);
        ventanaTemas.setVisible(true);
        CreateChildNodesByDate ccn = new CreateChildNodesByDate(fileRoot, rootTemas);
        new Thread(ccn).start();
        treeTemas.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                /**********************AL ABRIR UN TEMA******************************/
                temaSeleccionado = "";
                Object[] paths = treeTemas.getSelectionPath().getPath();
                for (int i=0; i<paths.length; i++) {
                    temaSeleccionado += paths[i];
                    if (i+1 <paths.length ) {
                        temaSeleccionado += File.separator;
                    }
                }
                temaSeleccionado = temaSeleccionado.replace("temasClient/", "");
                temaSeleccionado = temaSeleccionado.replace("temasClient", "");
                //Si es un date; NO LO ABRE
                boolean date = validateDateFormat(temaSeleccionado);
                temaSeleccionado = pathTemasServer + temaSeleccionado;
                if(date == false){
                    openTemaInViewer(temaSeleccionado);
                }
            }

            
        });
    }

    public class CreateChildNodesByDate implements Runnable {
        private DefaultMutableTreeNode root;
        private File fileRoot;
        public CreateChildNodesByDate(File fileRoot, 
                DefaultMutableTreeNode root) {
            this.fileRoot = fileRoot;
            this.root = root;
        }
        @Override
        public void run() {
            createChildren(fileRoot, root);
        }
        private void createChildren(File fileRoot, 
                DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files == null) return;

            for (File file : files) {
                System.out.println(file.getName());
                if(!file.getName().equals("discucion")){
                    if(!file.getName().equals("img")){
                        if(file.getName().equals(palabraABuscar)){
                            buscarPadre = palabraABuscar;
                            DefaultMutableTreeNode childNode = 
                                    new DefaultMutableTreeNode(new FileNode(file));
                            node.add(childNode);
                            if (file.isDirectory()) {
                                createChildrenNormal(file, childNode);
                            }
                        }
                            

                    }
                        
                }
                
            }
        }
        private void createChildrenNormal(File fileRoot, 
                DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files == null) return;

            for (File file : files) {
                System.out.println(file.getName());
                if(!file.getName().equals("discucion")){
                    if(!file.getName().equals("img")){
                        DefaultMutableTreeNode childNode = 
                                new DefaultMutableTreeNode(new FileNode(file));
                        node.add(childNode);
                        if (file.isDirectory()) {
                            createChildrenNormal(file, childNode);
                        }
                        
                    }
                        
                }
                
            }
        }

    }
    public class CreateChildNodesByName implements Runnable {
        private DefaultMutableTreeNode root;
        private File fileRoot;
        public CreateChildNodesByName(File fileRoot, 
                DefaultMutableTreeNode root) {
            this.fileRoot = fileRoot;
            this.root = root;
        }
        @Override
        public void run() {
            createChildren(fileRoot, root);
        }
        private void createChildren(File fileRoot, 
                DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files == null) return;

            for (File file : files) {
                if(!file.getName().equals("discucion")){
                    if(!file.getName().equals("img")){
                        if(validateDateFormat(file.getName()) || file.getName().contains(palabraABuscar)){
                            DefaultMutableTreeNode childNode = 
                                    new DefaultMutableTreeNode(new FileNode(file));
                            node.add(childNode);
                            if (file.isDirectory()) {
                                createChildren(file, childNode);
                            }
                        }
                            

                    }
                        
                }
                
            }
        }

    }
    public class CreateChildNodes implements Runnable {
        private DefaultMutableTreeNode root;
        private File fileRoot;
        public CreateChildNodes(File fileRoot, 
                DefaultMutableTreeNode root) {
            this.fileRoot = fileRoot;
            this.root = root;
        }
        @Override
        public void run() {
            createChildren(fileRoot, root);
        }
        private void createChildren(File fileRoot, 
                DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files == null) return;

            for (File file : files) {
                if(!file.getName().equals("discucion")){
                    if(!file.getName().equals("img")){
                        DefaultMutableTreeNode childNode = 
                                new DefaultMutableTreeNode(new FileNode(file));
                        node.add(childNode);
                        if (file.isDirectory()) {
                            createChildren(file, childNode);
                        }
                    }
                        
                }
                
            }
        }

    }

    public class FileNode {
        private File file;
        public FileNode(File file) {
            this.file = file;
        }
        @Override
        public String toString() {
            String name = file.getName();
            if (name.equals("")) {
                return file.getAbsolutePath();
            } else {
                return name;
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
            if(strDate.contains("/")){
                return false;
            }else{
                return true;
            }
	    
	}
   }
   
}
