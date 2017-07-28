import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatServerThread extends Thread{
	private Socket socket = null;
	private ChatServer4 server = null;
	private int id = -1;
	private DataInputStream Instream = null;
	private DataOutputStream Outstream = null;
	
	public ChatServerThread(ChatServer4 chatServer4, Socket socket) {
		// TODO Auto-generated constructor stub
		super();
		server= chatServer4;
		this.socket = socket;
		id = socket.getPort();
	}
	
	public void run(){

			System.out.println("Server Thread "+ id+ " is running.");
			while(true){
				try{
					String s = Instream.readUTF();
					server.handle(id, s);
					//System.out.println(s);
					//done = s.equals(".bye");
				}catch(IOException e){
					System.err.println(e);
					server.remove(id);
					interrupt();
				}
			}
		
	}
	
	public void open() throws IOException{
		// TODO Auto-generated method stub
		Instream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		Outstream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	
	public void close() throws IOException{
		// TODO Auto-generated method stub
		if(socket!=null) socket.close();
		if(Instream!=null) Instream.close();
		if(Outstream!=null) Outstream.close();
		System.out.println("Socket and Stream closed");
	}

	public void send(String string) {
		// TODO Auto-generated method stub
		try{
			Outstream.writeUTF(string);
			Outstream.flush();
		}catch(IOException e){
			System.out.println(id+" error sending"+e);
			server.remove(id);
			interrupt();
		}
	}
	
	public int getID(){
		return id;
	}

}
