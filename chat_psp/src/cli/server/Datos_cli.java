package cli.server;

import cli.server.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Datos_cli {
    private Socket socket;
    private Thread hilo;
    private String nick=""; 
    private int id;
    
    
    public Datos_cli(Socket socket,Thread hilo,int id){
        this.socket=socket;
        this.hilo=hilo;
        this.id=id;
    }
    public void set_nick(String nick)
    {
        this.nick=nick;
    }
    
    public Socket get_socket()
    {
        return this.socket;
    }
    public DataOutputStream get_Dos() throws IOException
    {
       return (DataOutputStream) this.socket.getOutputStream();
    }
    public DataInputStream get_Dis() throws IOException
    {
       return (DataInputStream) this.socket.getInputStream();
    }
    public InetAddress get_url()
    {
        return this.socket.getInetAddress();
    }
    public int get_puerto()
    {
        return this.socket.getPort();
    }
    public String get_nick()
    {
        return this.nick;
    }
    public Thread get_hilo()
    {
        return this.hilo;
    }
    public int get_id()
    {
        return this.id;
    }

}