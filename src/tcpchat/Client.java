package tcpchat;

import java.net.Socket;

public class Client {

	private String username;
	
	public Client() throws Exception {
		Socket socket = new Socket("localhost", 2020);
		
		System.out.println("welcome user");
		
		WriteThread writeThread = new WriteThread(socket, this);
		ReadThread readThread = new ReadThread(socket, this, writeThread);

		Thread read = new Thread(readThread);
		Thread write = new Thread(writeThread);
		
		write.start();
		read.start();
		
	}
	
	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
