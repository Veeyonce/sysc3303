SYSC 3303 - Iteration 2
Group 17 Project

Group members:
    1. Maria Veronica Drilon - 100977322
    2. Lisa Martini - 101057695
    3. Rohil Dubey - 100969189
    4. Saketh Gubbala - 100830684
    
How to run the project:
             1. Open TFTPServer.java and run
                * * Will be asked for prompts * *
		- Enter [Y] - to shutdown the server/ and server listener
             2. Open TFTPSim.java and run
             3. Open TFTPClient.java and run
		* * Will be asked for prompts * *
	     a. Enter the file directory for the client to read and write file
             b. 
                - Enter [R] - for a read request
                - Enter [W] - for a write request
                - Enter [O] - to be prompted for other options for modes
                       - Enter [Y]: run in verbose, [N]: run in quiet
                       - Enter [Y]: run in test mode, [N]: run in normal mode
                - Enter [Q] - to quit
             c. Enter your file name


Implementations choices :
	1. The server waits until the user running the server enter [y] to shut down server and serverListener
	2. We don't allow the client to specify a default directory in the server side because we consider that 
the implementation of the server's structure should be handled by the server operator.
	3. The server creates a directory called ServerFile (if the directory doesn't already exist)in the current user.dir
  to store every files that a client sends or asks.
        4. We don't allow to override on the server side, that way we can handle the error 6 (file already exists). 
It means a client can not write in a same file twice, and another client can not access files already created by other client.
Consequently the server doesn't change the name of the filename and doesn't create a copy.
	
	5. The client can specify a directory in which the files will be created or searched. Otherwise the default path is 
user.dir\files\
	6. We don't allow to override on the client side, that way we can handle the error 6(file already exists).
The client change the name of the writing file by adding copy to the filename. That way when a read request is sent the data
sent by the server or stored in a copy in the client side. If the "copy" file already exists a error6 is raised.


File descriptions:
      * TFTPClient.java
             * Includes code for the client
             * Uses one port for both sending read and write requests
             * Uses the same port for receiving responses        
      * TFTPHost.java
             * Parent class for printing the information
	     * read and write function for client and server
      * TFTPServer.java
             * Includes code for the server.
   	     * Create a Listener thread to receive the requests from the client
             * Wait for shutting down
      * TFTPServerHandler.java
             * Uses threads to handle file transfer for the server
             * Receives requests, either read or write and sends back appropriate response.
      * TFTPServerListener.java
             * Listener thread of the server using only one socket with port 69
             * Creates a new thread for every request received to handle it
             * Checks for shutdown status before every request
      * TFTPSim.java
             * Includes code for the error simulator
             * Receives read and write requests from the client and just sends them to the server
             * Also receives responses from the server and sends them to the client
             * Doesn’t do any real error checking
             
Test instructions:
                1. Test an empty file
                2. Test a small file with less than 512 bytes
                3. Test a normal file with exactly a mutiple of 512 bytes
                4. Test a large file greater than 512 bytes


Responsibility breakdown for iteration 2:
      1. Maria Veronica (Veronica) Drilon
            - Handling error 1, 2, 6
     	    - Test errors.
      2. Lisa Martini
	    - Fixing previous error in IT1 = file transfer actually works now, server is shutting down properly
            - Creating Timing diagrams.
            - Updating UCM Diagram.
            - Updating ReadMe
      3. Rohil Dubey
            - Updating UML class diagram
	    - Dealing with Sim.java port in case of test Mode.(feedback IT1)
            - Checking/testing errors.
      4. Saketh Gubbala
            - Implemented default directory set



Responsibility breakdown for iteration 1:
      1. Maria Veronica (Veronica) Drilon
            - Created UCM diagrams
            - Created UML diagram
            - Created README.txt
      2. Lisa Martini
            - Implemented printing out the information through out the programs
            - Implemented multithreading for every request
            - Implemented verify/validate packets
            - Implemented verbose mode
      3. Rohil Dubey
            - Implemented shutdown processes for the client and server
            - Implemented read function to be used for client and server
            - Implemented packet types: RRQ, WRQ, DATA, ACK, ERROR
            - Implemented prompting for modes: VERBOSE, QUIET
      4. Saketh Gubbala
            - Implemented default directory set
            - Implemented prompting client for overrides
            - Implemented prompting for modes: NORMAL, TEST
