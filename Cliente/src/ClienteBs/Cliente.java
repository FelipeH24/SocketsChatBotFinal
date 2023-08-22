package ClienteBs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente extends Thread{
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String adress;
	private int port;

	public Cliente(Socket socket, ObjectOutputStream out, ObjectInputStream in, String adress, int port) {
		this.socket = socket;
		this.out = out;
		this.in = in;
		this.adress = adress;
		this.port = port;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
