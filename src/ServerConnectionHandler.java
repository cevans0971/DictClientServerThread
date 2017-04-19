import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Steve
 */
public class ServerConnectionHandler extends Thread
{
    private Socket connection;
    private InputStream clientInput;
    private OutputStream clientOutput;
    private Scanner scanner;
    private OutputStreamWriter osw;
    private static Dictionary[] dictionary;
    public ServerConnectionHandler(Socket conn)
    {
        connection = conn;
        
        try
        {
        
           dictionary = new Dictionary[10];
       dictionary[0] = new Dictionary("bibble", "to drink often; to eat and/or drink noisily");
       dictionary[1] = new Dictionary("doodle sack", "old English word for bagpipe");
       dictionary[2] = new Dictionary("erinaceous", "of, pertaining to, or resembling a hedgehog");
       dictionary[3] = new Dictionary("gabelle","a tax on salt");
       dictionary[4] = new Dictionary("impignorate","to pawn or mortgage something");
       dictionary[5] = new Dictionary("jentacular","pertaining to breakfast");
       dictionary[6] = new Dictionary("kakorrhaphiophobia","fear of failure");
       dictionary[7] = new Dictionary("macrosmatic","having a good sense of smell");
       dictionary[8] = new Dictionary("quire","two dozen sheets of paper");
       dictionary[9] = new Dictionary("xertz","to gulp down quickly and greedily");
            clientInput = connection.getInputStream();
            clientOutput = connection.getOutputStream();
            scanner = new Scanner(clientInput);
            osw = new OutputStreamWriter(clientOutput);
        }
        catch(IOException e)
        {
            System.out.println("Error reading/writing from/to client");
        }
            
    }
    
    public void closeConnection() throws IOException
    {
         osw.close();
         clientInput.close();
         connection.close();
    }
    
    @Override
    public void run()
    {
        
        try
        {
            osw.write("Welcome to Server\r\n");
            osw.flush();
                    
                    
      
            while( true )
            {
                String message = scanner.nextLine();
                if (!( message.equals("Exit")))
                {
                    osw.write(message + "\r\n");
                    osw.flush();
                     for (int i =0; i < 10; i++)
                            {
                                if(message.equals(dictionary[i].getWord()))
                                {
                                    
                                    osw.write(dictionary[i].getDefinition() + "\r\n" );
                                    osw.flush();
                                    break;
                                }
                            }
                }
                else
                {
                    //If the "Exit" keyword is read
                    //then close this client's connection
                    closeConnection();
                    break;
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error reading/writing from/to client");
        }
    }
            
    
}
