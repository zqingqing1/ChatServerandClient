import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer2 implements Runnable{
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream Instream = null;
	private Thread  thread = null;
	private volatile boolean running = true;
	
	public void terminate(){
		running=false;
	}
	public ChatServer2(int port){
		try{
			System.out.println("Binding to port"+port);
			server = new ServerSocket(port);
			System.out.println("Server started..."+server);
			start();
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	public void run(){
		while(thread !=null){
			try{
				System.out.println("Waiting for a client...");
				socket = server.accept();
				System.out.println("client accepted: "+socket);
				open();
				boolean done= false;
				while(!done){
					try{
						String s = Instream.readUTF();
						System.out.println(s);
						done = s.equals(".bye");
					}catch(IOException e){
						System.err.println(e);
						done = true;
					}
				}
				close();
			}catch(IOException e){
				System.err.println(e);
			}
		}
	}
	
	public void start(){
		if(thread==null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop(){
		if(thread!=null){
			thread.stop();
			thread = null;
		}
	}
	
	private void close() throws IOException{
		// TODO Auto-generated method stub
		if(socket!=null) socket.close();
		if(Instream!=null) Instream.close();
		System.out.println("Socket and Stream closed");
	}

	private void open() throws IOException{
		// TODO Auto-generated method stub
		Instream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 6789;
		ChatServer2 server = new ChatServer2(port);
	}

}
