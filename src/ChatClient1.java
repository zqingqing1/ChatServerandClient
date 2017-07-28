import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient1 implements Runnable{
	   private Socket socket              = null;
	   private Thread thread              = null;
	   //private DataInputStream  console   = null;
	   private DataOutputStream streamOut = null;
	   private BufferedReader input = null;
	   private ChatClientThread client    = null;

	   public ChatClient1(String serverName, int serverPort)
	   {  System.out.println("Establishing connection. Please wait ...");
	      try
	      {  socket = new Socket(serverName, serverPort);
	         System.out.println("Connected: " + socket);
	         start();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Unexpected exception: " + ioe.getMessage()); }
	   }
	   public void run()
	   {  while (thread != null)
	      {  try
	         {  streamOut.writeUTF(input.readLine());
	            streamOut.flush();
	         }
	         catch(IOException ioe)
	         {  System.out.println("Sending error: " + ioe.getMessage());
	            stop();
	         }
	      }
	   }
	   public void handle(String msg)
	   {  if (msg.equals(".bye"))
	      {  System.out.println("Good bye. Press RETURN to exit ...");
	         stop();
	      }
	      else
	         System.out.println(msg);
	   }
	   public void start() throws IOException
	   {  input   = new BufferedReader(new InputStreamReader(System.in));
	      streamOut = new DataOutputStream(socket.getOutputStream());
	      if (thread == null)
	      {  client = new ChatClientThread(this, socket);
	         thread = new Thread(this);                   
	         thread.start();
	      }
	   }
	   public void stop()
	   {  if (thread != null)
	      {  thread.interrupt();  
	         thread = null;
	      }
	      try
	      {  if (input   != null) input.close();
	         if (streamOut != null)  streamOut.close();
	         if (socket    != null)  socket.close();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error closing ..."); }
	      client.close();  
	      client.interrupt();
	   }
	   public static void main(String args[])
	   {  ChatClient1 client = null;
	      if (args.length != 2)
	         System.out.println("Usage: java ChatClient host port");
	      else
	         client = new ChatClient1(args[0], Integer.parseInt(args[1]));
	   }
}
