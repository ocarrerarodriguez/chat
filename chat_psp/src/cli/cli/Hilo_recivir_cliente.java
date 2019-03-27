package cli.cli;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hilo_recivir_cliente extends Thread
{
    // boolean que pondremos a false cuando queramos parar el hilo
    private boolean continuar = true;
    //private Scanner sc new Scanner(System.in);
    private String texto="";
    //direcion del servidor
    private String direcion;
    private int puerto;
    private Socket socket;
    private DataInputStream Dis;
    

    public Hilo_recivir_cliente(String direcion,int puerto)
    {
       this.direcion=direcion;
       this.puerto=puerto;
    }

    // metodo para poner el boolean a false.
    public void terminar()
    {
        this.continuar=false;
    }

    // Metodo del hilo
    public void run()
    {
        try {
            this.socket = new Socket (this.direcion, this.puerto);
        } catch (IOException ex) {
            Logger.getLogger(Hilo_recivir_cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println ("reciviendo datos del servidor");
        try {
            this.Dis=new DataInputStream (socket.getInputStream());//entrada recivo un mensaje del servidor
        } catch (IOException ex) {
            Logger.getLogger(Hilo_recivir_cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        // mientras continuar ...
        while (continuar)
        {
            try {
                this.texto=Dis.readUTF();
                System.out.println("conexion establcida"+socket.getInetAddress()+socket.getPort());
                System.out.println(this.texto);
            } catch (IOException ex) {
                Logger.getLogger(Hilo_recivir_cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(this.texto);
            
        }
    }
} 