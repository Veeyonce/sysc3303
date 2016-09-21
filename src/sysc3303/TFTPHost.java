package sysc3303;

//Host.java
//This class is the parent class of TFTPClient, TFTPSim, TFTPServer containing 
//the function to print information , common to children.
//Created by Lisa Martini
//September 17th, 2016

import java.net.*;
import java.util.Arrays;

public class TFTPHost {
	
	private String infoText[][];
	
	 public TFTPHost (){
		//nothing special
		infoText=new String [][]{{"To","Sending packet :"},{"From","Packet received."}};
		
	 }
	 
	 protected void printInformation(DatagramPacket s, String who, String host, int action){
		 
		// Process the received datagram.
		 
		 System.out.println("\n"+who+": "+infoText[action][1]);
    System.out.println(infoText[action][0]+ host+": " + s.getAddress());
    System.out.println(host+" port: " + s.getPort());
    int len = s.getLength();
    System.out.println("Length: " + len);
    System.out.print("Containing: string:");
    
 // Form a String from the byte array.
    System.out.println(new String(s.getData(),0,len));
    System.out.print(" or bytes : ");
    System.out.println(Arrays.toString(Arrays.copyOf(s.getData(),len)));
	 }
	 
	 protected void terminate(DatagramSocket s[]){
		 for (int i=0;i<s.length;i++){
			 s[i].close();
		 }
		 
	 }
}
