package servidorBs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class Servidor extends Thread {
	
	private Socket socket;
	private ServerSocket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int port;
	
	public Servidor(int port) {
		super();
		this.socket = null;
		this.server = null;
		this.in = null;
		this.out = null;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			this.server = new ServerSocket(this.port);
			System.out.println("Inicializando Servidor");
			System.out.println("Esperando a un Cliente...");
			this.socket = server.accept();
			System.out.println(this.socket.getPort());
			System.out.println("Cliente Aceptado");
			
			this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			this.out = new ObjectOutputStream(socket.getOutputStream());
			
			List<String> preguntas = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader("preguntas.txt"))){
				String linea;
				while ((linea = br.readLine()) != null) {
					preguntas.add(linea);
				}		
			}catch (IOException e) {
				System.out.println("Error al leer las preguntas");
				return;
			}
			for (String pregunta : preguntas) {
				this.out.writeUTF(pregunta);
				this.out.flush();	
			}
			this.out.writeUTF("END");
			this.out.flush();
			
			int eleccion = in.readInt();//lee lo que el cliente elije
			
			List<String> respuestas = new ArrayList<>();
			try(BufferedReader br = new BufferedReader(new FileReader("respuestas.txt"))) {
				String linea;
				while ((linea = br.readLine()) != null) {
					respuestas.add(linea);
				}
			} catch (IOException e) {
				System.out.println("Error al leer las respuestas del bot");
				return;
			}
			
			if (eleccion >= 1 && eleccion <= respuestas.size()) {
				String respuesta = respuestas.get(eleccion -1);
				this.out.writeUTF(respuesta);
				this.out.flush();
			} else {
				this.out.writeUTF("No hay respuestas para tu pregunta.");
				this.out.flush();
			}
			this.in.close();
			this.out.close();
			this.server.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		

		
	}
	
	public static void main(String[] args) {
	}

}
