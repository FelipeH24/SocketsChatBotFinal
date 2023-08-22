package servidorBs;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread {
	
	private Socket socket;
	private ServerSocket server;
	private ObjectInputStream in;
	private int port;
	
	public Servidor(Socket socket, ServerSocket server, ObjectInputStream in, int port) {
		super();
		this.socket = socket;
		this.server = server;
		this.in = in;
		this.port = port;
	}
	
	@Override
	public void run() {
		
		super.run();
	}
	
	public static void main(String[] args) {
	}

}
