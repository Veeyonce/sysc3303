SYSC 3303 - Iteration 1
Group 17 Project

Group members:
    1. Maria Veronica Drilon - 100977322
    2. Lisa Martini - 101057695
    3. Rohil Dubey - 100969189
    4. Saketh Gubbala - 100830684
    
How to run the project:
             1. Open TFTPServerListener.java and run
             2. Open TFTPSim.java and run
             3. Open TFTPClient.java and run
             4. * * Will be asked for prompts * *
                - Enter [R] - for a read request
                - Enter [W] - for a write request
                - Enter [O] - to be prompted for other options for modes
                       - Enter [Y]: run in verbose, [N]: run in quiet
                       - Enter [Y]: run in test mode, [N]: run in normal mode
                - Enter [Q] - to quit
             5. Enter your file name

File descriptions:
      * TFTPClient.java
             * Includes code for the client
             * Uses one port for both sending read and write requests
             * Uses the same port for receiving responses        
      * TFTPHost.java
             * Parent class for printing the information
      * TFTPServerHandler.java
             * Uses threads to handle file transfer for the server
             * Receives requests, either read or write and sends back appropriate response.
      * TFTPServerListener.java
             * Listener process of the server using only one socket with port 69
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
                3. Test a normal file with exactly 512 bytes
                4. Test a large file greater than 255 bytes

Responsibility breakdown:
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
