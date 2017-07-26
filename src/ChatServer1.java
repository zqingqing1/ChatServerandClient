import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer1 {
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream Instream = null;
	
	public ChatServer1(int port){
		try{
			System.out.println("Binding to port"+port);
			server = new ServerSocket(port);
			System.out.println("Server started..."+server);
			System.out.println("Waiting for a client...");
			socket = server.accept();
			System.out.println("client accepted:"+socket);
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
	
	public static void main(String args[]){
		int port = 6789;
		ChatServer1 serve = new ChatServer1(port);
//		if(args.length != 1){
//			System.out.println("Usage: java ChatServer1 portnumber");
//		}else{
//			serve = new ChatServer1(Integer.parseInt(args[0]));
//		}
		
	}
}
