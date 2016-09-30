SYSC 3303 - Iteration 1
Group 17 Project

Group members:
    1. Maria Veronica (Veronica) Drilon - 100977322
    2. Lisa Martini - 101057695
    3. Rohil Dubey - 100969189
    4. Saketh Gubbala - 100830684
    
How to run the project: //TODO ***

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
             
Test instructions: // TODO ***

Responsibility breakdown:
      1. Maria Veronica (Veronica) Drilon
            - Created UCM diagrams
            - Created UML diagram
            - Created README.txt
      2. Lisa Martini
            - //TODO ***
      3. Rohil Dubey
            - //TODO ***
      4. Saketh Gubbala
            - //TODO **
