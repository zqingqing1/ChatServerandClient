import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer4 implements Runnable{
	private ChatServerThread clients[] = new ChatServerThread[50];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount=0;
	
	
	public ChatServer4(int port){
		try
	      {  System.out.println("Binding to port " + port + ", please wait  ...");
	         server = new ServerSocket(port);  
	         System.out.println("Server started: " + server);
	         start(); }
	      catch(IOException ioe)
	      {  System.out.println("Can not bind to port " + port + ": " + ioe.getMessage()); }
	}
	
	public void run(){
		while(thread!=null){
			try
	         {  System.out.println("Waiting for a client ..."); 
	            addThread(server.accept()); }
	         catch(IOException ioe)
	         {  System.out.println("Server accept error: " + ioe); stop(); }
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
	
	private int findClient(int Id){
		for(int i=0;i<clientCount;i++){
			if(clients[i].getID()==Id)
				return i;
		}
		return -1;
	}
	
	public synchronized void handle(int id,String input){
		if(!input.equals(",bye")){
			for(int i=0;i<clientCount;i++)
				clients[i].send(id+": "+input);
		}else{
			clients[findClient(id)].send(".bye");
		}
	}
	
	public synchronized void remove(int id){
		int pos=findClient(id);
		if(pos>=0){
			ChatServerThread c = clients[pos];
			System.out.println("Removing client thread: "+id+" at "+pos);
			if(pos<clientCount-1){
				for(int i=pos+1;i<clientCount;i++){
					clients[i-1]=clients[i];
				}
			}
			clientCount--;
			try{
				c.close();
			}catch(IOException e){
				System.out.println(e);
			}
			c.interrupt();
		}
	}
	
	private void addThread(Socket socket) {
		// TODO Auto-generated method stub
		if(clientCount<clients.length){
			System.out.println("Client accepted: "+socket);
			clients[clientCount] = new ChatServerThread(this,socket);
			try{
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			}catch(IOException ioe){
				System.out.println(ioe); 
			}
		}else
			System.out.println("Client refused since server is busy!");
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 6789;
		ChatServer4 server = new ChatServer4(port);
	}
	
}
