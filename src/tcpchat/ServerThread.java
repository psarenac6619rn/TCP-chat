package tcpchat;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ServerThread implements Runnable{

	private Socket socket;
	private ServerMain server;
	private BufferedReader in;
	private PrintWriter out;
	
	public ServerThread(Socket socket, ServerMain server) {
		this.socket = socket;
		this.server = server;
	}
	
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			
			String username = in.readLine();
			server.addUsername(username);
			
			String serverMsg = "new user connected: " + username;
			server.sendMsgs(serverMsg, this);
			
			String userMsg;
			String receivingUser;
			
			while(true) {
				if(server.getUsernames().size() > 1) 
					out.println("Write target user:");
				else
					out.println("no users avaliable.");
				
				receivingUser = in.readLine();
				if(receivingUser.equalsIgnoreCase("exit")) break;
				
				if(checkUser(receivingUser)) {
					
					userMsg = in.readLine();
					if(userMsg.equalsIgnoreCase("exit")) break;
					
					serverMsg = username + ":" + userMsg;
					server.sendMsg(serverMsg, receivingUser);
					
				}
			}
			
			out.println("exit");
			server.removeUser(username, this);
			
			serverMsg = username + " left the chat";
			server.sendMsgs(serverMsg, this);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkUser(String receivingUser) {
		System.out.println(server.getUsernames().size() + "ovo je broj korisnika");
		if(!server.getUsernames().contains(receivingUser)) {
			out.println("this user doesn't exist");
			return false;
		}
		return true;
	}
	
	public void sendMsg(String msg) {
		out.println(msg);
	}

}
