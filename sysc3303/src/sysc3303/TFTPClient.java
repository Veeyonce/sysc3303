package sysc3303;
//TFTPClient.java
//This class is the client side for a very simple assignment based on TFTP on
//UDP/IP. The client uses one port and sends a read or write request and gets 
//the appropriate response from the server.  No actual file transfer takes place.
//based on SampleSolution for assignment1 given the Sept 19th,2016

import java.io.*;
import java.net.*;
import java.util.*;

public class TFTPClient extends TFTPHost{

    private DatagramSocket sendReceiveSocket;
    public static final int READ= 1; 
    public static final int WRITE = 2;

    private int sendPort;
    private Mode run;
    byte requestFormatRead = 1;
    //we can run in normal (send directly to server) or test
    //(send to simulator) mode
    public static enum Mode { NORMAL, TEST};

    public TFTPClient()
    {
        super();
        try {

            // Construct a datagram socket and bind it to any available
            // port on the local host machine. This socket will be used to
            // send and receive UDP Datagram packets.
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException se) {   // Can't create the socket.
            se.printStackTrace();
            System.exit(1);
        }
        run=Mode.NORMAL;
    }

    public void sendAndReceive(int type)
    {
        byte[] msg = new byte[100], // message we send
        fn, // filename as an array of bytes
        md, // mode as an array of bytes
        data; // reply as array of bytes
        String filename, mode="Octet"; // filename and mode as Strings
        int j, len;

        if (run==Mode.NORMAL) 
            sendPort = 69;
        else
            sendPort = 23;

        sc.reset();
        System.out.println("Please enter a filename");
        filename=sc.next();

         
        fn=filename.getBytes();
        md=mode.getBytes();

        //form a request with fileame, format, and message type

        msg=formatRequest(fn,md,type);

        len = fn.length+md.length+4; // length of the message
        // length of filename + length of mode + opcode (2) + two 0s (2)

        // Construct a datagram packet that is to be sent to a specified port
        // on a specified host.
        // The arguments are:
        //  msg - the message contained in the packet (the byte array)
        //  the length we care about - k+1
        //  InetAddress.getLocalHost() - the Internet address of the
        //     destination host.
        //     In this example, we want the destination to be the same as
        //     the source (i.e., we want to run the client and server on the
        //     same computer). InetAddress.getLocalHost() returns the Internet
        //     address of the local host.
        //  69 - the destination port number on the destination host.
        try {
            sendPacket = new DatagramPacket(msg, len,
                InetAddress.getLocalHost(), sendPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }

        printOutgoingInfo(sendPacket, "Client",verbose);

        // Send the datagram packet to the server via the send/receive socket.
        try {
            sendReceiveSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Client: Packet sent.");

       
        
        // Process the received datagram.
        while(!shutdown){
            if (type==WRITE) {
                try {
                    byte[] resp = new byte[4];
                    receivePacket = new DatagramPacket(resp,4);
                    while (timeout) {
                        timeout = false;
                        try {
                            sendReceiveSocket.setSoTimeout(300);
                            sendReceiveSocket.receive(receivePacket);
                        } catch (SocketTimeoutException e) {
                            timeout = true;
                            if (shutdown) {
                                System.exit(0);
                            }
                        }
                    }
                    if (resp[0]==(byte)0 && resp[1]==(byte)4 && resp[2]==(byte)0 && resp[3]==(byte)0){
                        //ACK 0 received 
                        	sendPort = receivePacket.getPort();
                            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
                            read(in,sendReceiveSocket,sendPort, (byte)requestFormatRead);
                            in.close();
                    }
                    else {//Server didn't answer correctly
                    	System.out.println("First Ack invalid, shutdown");
                    	System.exit(0);
                    }
                    

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (type==READ) {
                filename = "copy".concat(filename); //avoid overwriting the existing file
                try {
                	
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
                    write(out,sendReceiveSocket);
                    out.close();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            printIncomingInfo(receivePacket,"Client",verbose);

            System.out.println();

        } // end of loop

        // We're finished, so close the socket.
        sendReceiveSocket.close();
    }

    /*
     * formatRequest takes a filename and a format and an opcode (which corresponds to read or write)
     * and formats them into a correctly formatted request
     */
    public byte[] formatRequest(byte[] filename, byte[] format, int opcode) {
        int lf = filename.length,lm=format.length;

        byte [] result=new byte[lf+4+lm];

        result[0] =(byte) 0;
        result[1] = (byte) opcode;

        System.arraycopy(filename,0,result,2,lf);

        result[lf+2] = 0;

        System.arraycopy(format,0,result,3+lf,lm);

        result[lf+3+lm] = 0;

        return result;
    }

    public void promptUser(){

        String x;

        do{
            System.out.println("(R)ead, (w)rite, (o)ptions, or (q)uit?");
            x = sc.next();
            if (x.contains("R")||x.contains("r")) {
                sc.reset();
                this.sendAndReceive(READ);
                //System.exit(0);
            }
            else if (x.contains("w")||x.contains("W")) {
                sc.reset();
                this.sendAndReceive(WRITE);
                //System.exit(0);
            }
            else if (x.contains("q")||x.contains("Q")) {
                this.sendReceiveSocket.close();
                System.exit(0);
            }
            else if (x.contains("o")||x.contains("O")) {
                System.out.println("Would you like to turn off verbose mode? Y/N");
                x = sc.next();
                sc.reset();
                if (x.contains("y")||x.contains("Y")) {
                    this.verbose = false;
                }
                System.out.println("Would you like to turn on test mode?");
                x = sc.next();
                sc.reset();
                if (x.contains("y")||x.contains("Y")) {
                    this.run=Mode.TEST;
                }

                System.out.println("(R)ead or (w)rite?");
                sc.reset();
            }
            else {
                sc.reset(); //clear scanner
            }
        }while(sc.hasNext()) ;
        sc.close();

    }

    public static void main(String args[])
    {
        TFTPClient c = new TFTPClient();
        c.promptUser();

    }
}

