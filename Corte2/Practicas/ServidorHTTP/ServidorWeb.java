import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorWeb
{
	public static final int PUERTO=8080;
	ServerSocket ss;

		class Manejador extends Thread{
			protected Socket socket;
			protected PrintWriter pw;
			protected BufferedOutputStream bos;
			protected BufferedReader br;
			protected String FileName;
			protected String type;
			String line="";

			public Manejador(Socket _socket) throws Exception
			{
				this.socket=_socket;
			}

			public void run()
			{
				try{
					int i=0;
					bos=new BufferedOutputStream(socket.getOutputStream());
					pw=new PrintWriter(new OutputStreamWriter(bos));
					File dir = new File("/Users/JorgeEnrique/Documents/Jorge/4semestre/Aplicaciones para la red/2Parcial/ServidorWeb");
					String[] ficheros = dir.list();
					int n = 0;
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					byte [] b = new byte[2000];

					n=dis.read(b);
						line = line + new String(b,0,n);
						System.out.println(line);


					if(line==null)
					{
						pw.print("<html><head><title>Servidor WEB");
						pw.print("</title><body bgcolor=\"#AACCFF\"<br>Linea Vacia</br>");
						pw.print("</body></html>");
						socket.close();
						return;
					}
					System.out.println("\nCliente Conectado desde: "+socket.getInetAddress());
					System.out.println("Por el puerto: "+socket.getPort());
					System.out.println("Datos: "+line+"\r\n\r\n");
					if(line.indexOf("?")==-1)
					{
						getArch(line);
						if(FileName.compareTo("")==0)
						{
							SendA("index.htm");
							System.out.println("Datos: "+FileName+"\r\n\r\n");
						}
						else if(FileName.compareTo("recursos")==0){
							pw.println("HTTP/1.0 200 Okay");
							pw.flush();
							pw.println();
							pw.flush();
							pw.print("<html><head><title>SERVIDOR WEB");
							pw.flush();
							pw.print("</title></head><body bgcolor=\"#AACCFF\"><h1><br>Recursos Dentro Del Servidor</br></h1>");
							pw.flush();
							pw.print("<ul>");
							pw.flush();
							if (ficheros == null)
								pw.print("<p>No hay ficheros en el directorio especificado</p>");
							else {
							  for (int x=0;x<ficheros.length;x++){
									if (!ficheros[x].equals(".DS_Store")) {
										System.out.println(ficheros[x]);
										pw.print("<li type=square>"+ficheros[x]+"</li>");
									}
								}
								pw.print("</ul></body></html>");
								pw.flush();
							}
						}
						else
						{
							if(line.toUpperCase().startsWith("DELETE"))
							{

								File eliminated_file = new File(FileName);
								Checktype(FileName);
								 String sb = "";
								if (eliminated_file.delete()){
									pw.print("\nHTTP/1.0 202 - OK\n");
									pw.flush();
									pw.print("Server:  Server \n");
									pw.flush();
									pw.print("Date: " + new Date()+" \n");
									pw.flush();
									pw.print("Content-Type: "+type+ " \n\n");
									pw.flush();
									pw.print("<html><body><h4>Archivo Eliminado</h4></body></html>");
									pw.flush();

									}
								else{
									 pw.print("\nHTTP/1.0 404 - NOT FOUND\n");
									 pw.flush();
									 pw.print("Server:  Server \n");
									 pw.flush();
									 pw.print("Date: " + new Date()+" \n");
									 pw.flush();
									 pw.print("Content-Type: "+type+ " \n\n");
									 pw.flush();
									 pw.print("<html><body><h4>Archivo no  Eliminado</h4></body></html>");
	 								bos.flush();
									pw.flush();
								 }
							}
							else {
									SendA(FileName);
									System.out.println("Datos: "+FileName+"\r\n\r\n");

							}
						}
						System.out.println(FileName);


					}
					else if(line.toUpperCase().startsWith("GET"))
					{
						StringTokenizer tokens=new StringTokenizer(line,"?");
						String req_a=tokens.nextToken();
						String req=tokens.nextToken();
						System.out.println("Token1: "+req_a+"\r\n\r\n");
						System.out.println("Token2: "+req+"\r\n\r\n");
						pw.println("HTTP/1.0 200 Okay");
						pw.flush();
						pw.println();
						pw.flush();
						pw.print("<html><head><title>SERVIDOR WEB");
						pw.flush();
						pw.print("</title></head><body bgcolor=\"#AACCFF\"><center><h1><br>Parametros Obtenidos..</br></h1>");
						pw.flush();
						pw.print("<h3><b>"+req+"</b></h3>");
						pw.flush();
						pw.print("</center></body></html>");
						pw.flush();
					}
					else if(line.toUpperCase().startsWith("POST")){
						StringTokenizer tokens=new StringTokenizer(line,"?");
						String req_a=tokens.nextToken();
						String req=tokens.nextToken();
						System.out.println("Token1: "+req_a+"\r\n\r\n");
						System.out.println("Token2: "+req+"\r\n\r\n");
						pw.println("HTTP/1.0 200 Okay");
						pw.flush();
						pw.println();
						pw.flush();
						pw.print("<html><head><title>SERVIDOR WEB");
						pw.flush();
						pw.print("</title></head><body bgcolor=\"#AACCFF\"><center><h1><br>Parametros Obtenidos..</br></h1>");
						pw.flush();
						pw.print("<h3><b>"+req+"</b></h3>");
						pw.flush();
						pw.print("</center></body></html>");
						pw.flush();
					}
					else
					{
						pw.println("HTTP/1.0 501 Not Implemented");
						pw.println();
					}
					pw.flush();
					bos.flush();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				try{
					socket.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			public void getArch(String line)
			{
				int i;
				int f;
				if(line.toUpperCase().startsWith("GET"))
				{
					i=line.indexOf("/");
					f=line.indexOf(" ",i);
					FileName=line.substring(i+1,f);
				}
				if(line.toUpperCase().startsWith("POST"))
				{
					i=line.indexOf("/");
					f=line.indexOf(" ",i);
					FileName=line.substring(i+1,f);
				}
				if(line.toUpperCase().startsWith("DELETE"))
				{
					i=line.indexOf("/");
					f=line.indexOf(" ",i);
					FileName=line.substring(i+1,f);
				}
				if(line.toUpperCase().startsWith("HEAD"))
				{
					i=line.indexOf("/");
					f=line.indexOf(" ",i);
					FileName=line.substring(i+1,f);
				}
			}
			public void Checktype(String fileName){
				if (fileName.contains(".jpg")) {
					 type = "image/jpeg";
				}
				else if (fileName.contains(".pdf")) {
					type = "Application/pdf";
				}
				else{
					 type = "text/html";
				}
			}
			public void SendA(String fileName,Socket sc)
			{
				//System.out.println(fileName);
				int fSize = 0;
				byte[] buffer = new byte[4096];
				try{
					DataOutputStream out =new DataOutputStream(sc.getOutputStream());

					//sendHeader();
					FileInputStream f = new FileInputStream(fileName);
					Checktype(fileName);
					int x = 0;
					while((x = f.read(buffer))>0){
						out.write(buffer,0,x);
					}
					out.flush();
					f.close();
				}catch(FileNotFoundException e){
				}catch(IOException e){
				}

			}
			public void SendA(String arg)
			{
				try{
					 int b_leidos=0;
					 String sb = "";
					 BufferedInputStream bis2=new BufferedInputStream(new FileInputStream(arg));
                     byte[] buf=new byte[1024];
                     int tam_bloque=0;
                     if(bis2.available()>=1024)
                     {
                        tam_bloque=1024;
                     }
                     else
                     {
                        bis2.available();
                     }
										 Checktype(FileName);
                     int tam_archivo=bis2.available();
										 if (line.toUpperCase().startsWith("HEAD")) {
 											sb = sb+"HTTP/1.0 200 ok\n";
 										        sb = sb +"Server:  Server \n";
 											sb = sb +"Date: " + new Date()+" \n";
 											sb = sb +"Content-Type: "+type+ " \n";
 											sb = sb +"Content-Length: "+tam_archivo+" \n";
 											sb = sb +"\n";
											bos.write(sb.getBytes());
											bos.flush();
										 }
										 else{
											sb = sb+"HTTP/1.0 200 ok\n";
										        sb = sb +"Server:  Server \n";
											sb = sb +"Date: " + new Date()+" \n";
											sb = sb +"Content-Type: "+type+ " \n";
											sb = sb +"Content-Length: "+tam_archivo+" \n";
											sb = sb +"\n";
											bos.write(sb.getBytes());
											bos.flush();
	 										while((b_leidos=bis2.read(buf,0,buf.length))!=-1){
														bos.write(buf,0,b_leidos);
							 				}
										 bos.flush();
										bis2.close();
										}
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}

			}
		}
		public ServidorWeb() throws Exception
		{
			System.out.println("Iniciando Servidor.......");
			this.ss=new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado:---OK");
			System.out.println("Esperando por Cliente....");
			for(;;)
			{
				Socket accept=ss.accept();
				new Manejador(accept).start();
			}
		}



		public static void main(String[] args) throws Exception{
			ServidorWeb sWEB=new ServidorWeb();
		}

}
