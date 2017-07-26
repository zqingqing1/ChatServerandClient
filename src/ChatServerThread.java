import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatServerThread extends Thread{
	private Socket socket = null;
	private CharServer3 server = null;
	private int id = -1;
	private DataInputStream Instream = null;
	
	public ChatServerThread(CharServer3 charServer3, Socket socket) {
		// TODO Auto-generated constructor stub
		server= charServer3;
		this.socket = socket;
	}
	
	public void run(){
		try{
			System.out.println("Server Thread "+ id+ " is running.");
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
	
	public void open() throws IOException{
		// TODO Auto-generated method stub
		Instream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	
	private void close() throws IOException{
		// TODO Auto-generated method stub
		if(socket!=null) socket.close();
		if(Instream!=null) Instream.close();
		System.out.println("Socket and Stream closed");
	}

}
