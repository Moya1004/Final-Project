using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Web;

namespace WebApplication1.Controllers
{
    class SocketClient
    {
    private TcpClient Client;
    private NetworkStream Stream;

    private Byte[] Data;

    public SocketClient(string address, int port)
    {
        Client = new TcpClient();
        Client.Connect(address, port);

        Stream = Client.GetStream();


        while (Client.Connected)
        {

        }
    }

    public void SendData(string message)
    {
        Data = System.Text.Encoding.ASCII.GetBytes(message);
        Stream.Write(Data, 0, Data.Length);
    }
}

}