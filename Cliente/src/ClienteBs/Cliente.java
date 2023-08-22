package ClienteBs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String adress;
	private int port;

	public Cliente(String adress, int port) {
		this.socket = null;
		this.out = null;
		this.in = null;
		this.adress = adress;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			this.socket = new Socket(this.adress, this.port);
			System.out.println("Conectando...");

			this.out = new ObjectOutputStream(socket.getOutputStream());

			String mensaje = "Hola server, dame opciones.";
			this.out.writeUTF(mensaje);
			this.out.flush();

			this.in = new ObjectInputStream(socket.getInputStream());

			List<String> preguntas = new ArrayList<>();

			while (true) {
				String pregunta = in.readUTF();
				if (pregunta.equals("FIN.")) {
					break;
				}
				preguntas.add(pregunta);
			}

			System.out.println("Preguntas del servidor: ");
			for (int i = 0; i < preguntas.size(); i++) {
				System.out.println((i + 1) + ". " + preguntas.get(i));
			}

			while (true) {
				Scanner sc = new Scanner(System.in);
				System.out.println("Elige el numero de la pregunta que desees o 0 para salir:");

				int option = sc.nextInt();

				if (option == 0) {
					System.out.println("Cerrando la conexion, saliendo...");
					break;
				} else if (option >= 1 && option <= preguntas.size()) {
					this.out.writeInt(option);
					this.out.flush();

					String rta = in.readUTF();
					System.out.println("Respuesta del server: " + rta);
				} else {
					System.out.println("Error, opcion invalida.");
				}
			}

			this.out.close();
			this.in.close();
			this.socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
