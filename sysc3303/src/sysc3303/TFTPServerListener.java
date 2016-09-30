package sysc3303;


//TFTPServerListener.java
//This class is the listener process of the server. The Listener use only one socket (69) and wait for any request.
//Once a request is received the listener create a new thread to handle the request.
//Before waiting again a new request the listener check the shutdown command(status)
//If no request is received during tOut, the listener check the shutdown command.
 



/*error code : 05 opcode : 01=filenotfound/02=access violation /03=disk full  or allocation exceed/04=file already exists
 * + force to happen no error sim , opcode +errorcode + usefull message +0 byte and stop*/

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TFTPServerListener extends TFTPHost {

	// UDP datagram packets and sockets used to send / receive
	private DatagramPacket receivePacket;
	private DatagramSocket receiveSocket;
	
	//timeout
	private final int tOut=300;//10 sec
	
	//status (shutdown or not)
	private boolean status;
	
	public TFTPServerListener()
	{
		super();
	   try {
	      // Construct a datagram socket and bind it to port 69
	      // on the local host machine. This socket will be used to
	      // receive UDP Datagram packets.
	      receiveSocket = new DatagramSocket(69);
	   } catch(SocketException se) {
	      se.printStackTrace();
	      System.exit(1);
	   }
	   try {
		   receiveSocket.setSoTimeout(tOut);
	   }catch (SocketException set){
		   set.printStackTrace();
		   System.exit(1);
	   }
	   
	   status=false;
	   
	}
	
	public void Listen(){
		byte[] data;
		
		for (;;){
			
			data = new byte[100];
			receivePacket = new DatagramPacket(data, data.length);

		      System.out.println("ServerListener: Waiting for packet.");
		      // Block until a datagram packet is received from receiveSocket or the timeout expires
		      try {
		         receiveSocket.receive(receivePacket);
		      } catch (InterruptedIOException t){
		    	  //no request is received during tOut, the listener check the shutdown command.
		    	  System.out.println("No file transfer requested");
		    	  checkStat();
		      }catch (IOException e) {
		         e.printStackTrace();
		         System.exit(1);
		      } 
		      
		    // create a new thread to handle the file transfer request
		      Thread handler=new Thread(new TFTPServerHandler(receivePacket));
		      handler.start();
		      
		    //Before waiting again a new request the listener check the shutdown command(status)
		     
		      checkStat();
		}
		
	}
	
	public void checkStat(){
		
		if (shutdown){
			System.out.println("ServerListener shutdown , no more file transfer request possible");
			//maybe need to wait all the thread to end? 
			System.exit(1);
		}
		
	}
	
	public static void main( String args[] ) throws Exception
	{
	   TFTPServerListener c = new TFTPServerListener(); 
	   c.Listen();
	   
	}
	
	
	
}
