package tcpchat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread implements Runnable{

	private PrintWriter out;
	private Socket socket;
	private Client client;
	int flag;
	
	public WriteThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
		flag = 0;
	}
	
	@Override
	public void run() {
		try {
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			
			Scanner sc = new Scanner(System.in);
			System.out.println("enter username:");
			
			String username = sc.nextLine();
			client.setUsername(username);
			out.println(username);
			
			String msg;
			
			while(true) {
				flag = 0;
				String user = sc.nextLine();
				if(user.equalsIgnoreCase("exit")) {
					out.println("exit");
					//Thread.sleep(100);
					break;
				}
				out.println(user);
				if(flag == 0) {
					System.out.println("enter message");
					msg = sc.nextLine();
					if(msg.equalsIgnoreCase("exit")) {
						out.println("exit");
						//Thread.sleep(100);
						break;
					}

					out.println(msg);
				}
			}
			socket.close();
			System.out.println(username + " socket closed");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	
}
