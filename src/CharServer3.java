import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CharServer3 implements Runnable{
	
	private ServerSocket server = null;
	private Thread  thread = null;
	private ChatServerThread client = null;
	
	//constructor
	public CharServer3(int port){
		try{
			System.out.println("Binding to port... " + port );
	         server = new ServerSocket(port);  
	         System.out.println("Server started: " + server);
	         start();
	     }
	      catch(IOException ioe){
	    	  	System.out.println(ioe); 
	    	 }
	}
	//run()
	public void run(){
		while(thread!=null){
			try{
				System.out.println("Waiting for a cilent...");
				addThread(server.accept());
			}catch(IOException ioe){
				System.out.println(ioe); 
			}
		}
	}
	
	
	private void addThread(Socket socket) {
		// TODO Auto-generated method stub
		System.out.println("Client accepted: "+socket);
		client = new ChatServerThread(this,socket);
		try{
			client.open();
			client.start();	
		}catch(IOException ioe){
			System.out.println(ioe); 
		}
		
	}
	
	public void start(){
		if(thread==null){
			thread = new Thread(this);
			thread.start();
		}
	}
	

	public void stop(){
		if(thread!=null){
			thread.interrupt();
			thread = null;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 6789;
		ChatServer2 server = new ChatServer2(port);
	}

}
