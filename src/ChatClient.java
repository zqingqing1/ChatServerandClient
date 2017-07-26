
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
	private Socket socket 			= null;
	//private DataInputStream  input 	= null;
	private DataOutputStream output 	= null;
	private BufferedReader input = null;
	
	
	public ChatClient(String serverName,int serverPort){
		System.out.println("Establishing connection...");
		try{
			socket = new Socket(serverName,serverPort);
			System.out.println("connected: "+socket);
			start();
		}catch(IOException e){
			System.out.println(e);
		}
		String s = "";
		while(!s.equals(".bye")){
			try{
				s = input.readLine();
				output.writeUTF(s);
				output.flush();
			}catch(IOException e){
				System.out.println(e);
			}
		}
		stop();
	}
	private void stop() {
		// TODO Auto-generated method stub
		try{
			if(input!=null) input.close();
			if(output!=null) output.close();
			if(socket!=null) socket.close();
			System.out.println("Socket and Streams closed");
		}catch(IOException e){
			System.out.println(e);
		}
	}
	public void start() throws IOException{
		//input = new DataInputStream(System.in);
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host = "localhost";
		int port = 6789 ;
		ChatClient client = new ChatClient(host,port);
	}

}
