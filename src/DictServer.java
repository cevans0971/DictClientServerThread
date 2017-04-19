/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class DictServer {
    private ServerSocket server;
    private Socket client; 
    private int portNumber;
    public DictServer(int portNumber)
    {
        server = null;
        client = null;
        this.portNumber = portNumber;
    }
    public void start() throws IOException
    {
        server = new ServerSocket();
    }
    public void createConnection() throws IOException
    {
        client = new Socket();
        ServerConnectionHandler sch = new ServerConnectionHandler(client);
        sch.start();
    }
    public void terminate()
    {
        try {
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(DictServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args)
    {
        
   
       
        
        int portNumber = Integer.parseInt(args[0]);
        DictServer server = new DictServer(portNumber);
        
        try
        {  
            //Make the server listen on the given port 
            server.start();
            
            while (true)
            {
                //Wait until a client connects
                server.createConnection();     

            }
            
        }
         catch (IOException e)
         {

             System.out.println("Unable to establish "
                        + "server connection");
         }
        finally
        {
            server.terminate();
        }
        
    }

    }

