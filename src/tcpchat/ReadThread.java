package tcpchat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread implements Runnable{

	private BufferedReader in;
	private Socket socket;
	private Client client;
	private WriteThread writeThread;
	
	public ReadThread(Socket socket, Client client, WriteThread writeThread) {
		this.socket = socket;
		this.client = client;
		this.writeThread = writeThread;
	}
	
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				String answer = in.readLine();
				if(answer.equalsIgnoreCase("exit")) break;
				System.out.println(answer);
				if(answer.equalsIgnoreCase("this user doesn't exist")) writeThread.setFlag(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
