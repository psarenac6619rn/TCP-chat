package tcpchat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

	private ArrayList<String> usernames; 
	private ArrayList<ServerThread> userThreads;
	
	public ServerMain() throws Exception {
		usernames = new ArrayList<String>();
		userThreads = new ArrayList<ServerThread>();
		ServerSocket ss = new ServerSocket(2020);
		
		System.out.println("server");
		while(true) {
			Socket socket = ss.accept();
			ServerThread userThread = new ServerThread(socket, this);
			userThreads.add(userThread);
			Thread thread = new Thread(userThread);
			thread.start();
		}
	}
	
	public static void main(String[] args) {
		try {
			new ServerMain();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMsgs(String msg, ServerThread user) {
		for(ServerThread receiver: userThreads) {
			if(receiver != user) {
				receiver.sendMsg(msg);
			}
		}
	}
	
	public void removeUser(String username, ServerThread userThread) {
		usernames.remove(username);
		userThreads.remove(userThread);
	}
	
	public void sendMsg(String msg, String receivingUser) {
		for(int i = 0; i < usernames.size(); i++) {
			if(receivingUser.equalsIgnoreCase(usernames.get(i))) {
				userThreads.get(i).sendMsg(msg);
			}
		}
	}
	
	public void addUsername(String username) {
		usernames.add(username);
	}
	
	public ArrayList<String> getUsernames() {
		return usernames;
	}


	public void setUsernames(ArrayList<String> usernames) {
		this.usernames = usernames;
	}


	public ArrayList<ServerThread> getUserThreads() {
		return userThreads;
	}


	public void setUserThreads(ArrayList<ServerThread> userThreads) {
		this.userThreads = userThreads;
	}
	
}
