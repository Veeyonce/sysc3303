 package sysc3303;

//TFTPServer.java
//This class is the server side of a multithreaded TFTP server based on
//UDP/IP. The server create a listener process to listen on the port 69
 //and wait for a file transfer. This server wait the shutdown command 
 //and change the status of the listener


import java.io.*; 
import java.net.*;
import java.util.*;

public class TFTPServer extends TFTPHost {

	boolean shutdown;
	TFTPServerListener L;

	public TFTPServer()
	{
		shutdown=false;
		L=new TFTPServerListener();
		L.Listen();
	}

	public void waitForTerminate(){
		
		Scanner sc= new Scanner(System.in);
		do{
			System.out.println("Shutdown server (y/n) ?");
			//try {
			String rep= sc.nextLine();
			char c=rep.charAt(0);
			/*}catch(){
				
			}*/
			if (c=='y' || c=='Y'){
				shutdown=true;
				L.setStatus(true);
			}
			
		}while (!shutdown);
		
		System.out.println("wait for the listener to shutdown");
		//should wait for the listener before ending
		
		
	}
	
	public static void main( String args[] ) throws Exception
	{
	   TFTPServer c = new TFTPServer(); 
	   c.waitForTerminate();
	   
	}
}


