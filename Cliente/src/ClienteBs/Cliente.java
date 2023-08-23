package ClienteBs;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente extends Thread {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String address;
    private int port;

    public Cliente(String address, int port) {
        this.socket = null;
        this.out = null;
        this.in = null;
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(this.address, this.port);
            System.out.println("Conectado");

            this.out = new ObjectOutputStream(socket.getOutputStream());

            String mensaje = "Hola servidor, dame opciones.";
            this.out.writeUTF(mensaje);
            this.out.flush();

            this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            List<String> preguntas = new ArrayList<>();
            while (true) {
                String pregunta = in.readUTF();
                if (pregunta.equals("FIN")) {
                    break;
                }
                preguntas.add(pregunta);
            }

            System.out.println("Preguntas del servidor:");
            for (int i = 0; i < preguntas.size(); i++) {
                System.out.println((i + 1) + ". " + preguntas.get(i));
            }

            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Elige el número de la pregunta a responder (0 para salir):");
                int eleccion = scanner.nextInt();

                if (eleccion == 0) {
                    System.out.println("Cerrando la conexión y saliendo del cliente.");
                    break; // Salir del ciclo si se elige 0
                } else if (eleccion >= 1 && eleccion <= preguntas.size()) {
                    this.out.writeInt(eleccion);
                    this.out.flush();

                    String respuesta = in.readUTF();
                    System.out.println("Respuesta del servidor: " + respuesta);
                } else {
                    System.out.println("Elección inválida.");
                }
            }

            this.out.close();
            this.in.close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        Cliente client = new Cliente("127.0.0.1", 8000);
        client.start();
    }
}