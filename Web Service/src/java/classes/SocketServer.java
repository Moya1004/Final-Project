package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer implements Runnable
{
    private String serverName;
    private boolean isRunning;
    private String ipAddress;
private ServerSocket serverSocket;
private Socket clientSocket;

public SocketServer(String name, int port)
{
    try 
    {
        this.serverName = name;
        this.serverSocket = new ServerSocket(port);
        this.isRunning = true;
        new Thread(this).start();
    } 
    catch (IOException e) 
    {
        e.printStackTrace();
    }
}

private BufferedReader recv;



public void run()
{
    while(isRunning)
    {
        try 
        {
            ipAddress = clientSocket.getInetAddress().getHostAddress() ;
            clientSocket = serverSocket.accept();
            System.out.println("Client Connected from " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());

            recv = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Data Recieved: " + recv.readLine());

            //clientSocket.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
}