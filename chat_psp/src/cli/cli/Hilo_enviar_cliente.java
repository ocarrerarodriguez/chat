package cli.cli;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hilo_enviar_cliente extends Thread
{
    // boolean que pondremos a false cuando queramos parar el hilo
    private boolean continuar = true;
    private Scanner sc ;
    private String texto="";
    private String direcion;
    private int puerto;
    private Cliente cliente;
    private Socket socket;
    private DataOutputStream Dos;

    public Hilo_enviar_cliente(String direcion,int puerto,Cliente cliente)
    {
       this.direcion=direcion;
       this.puerto=puerto;
       this.sc=new Scanner(System.in);
       this.cliente=cliente;
       
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
            socket = new Socket (this.direcion, this.puerto);
        } catch (IOException ex) {
            Logger.getLogger(Hilo_enviar_cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println ("enviando datos al servidor");
        
        try {
            this.Dos = new DataOutputStream (this.socket.getOutputStream()); //salida mando un mensaje al servidor
            System.out.println("indica el nick con el que se escribiran los mensajes en el chat");
            this.texto= this.sc.nextLine();
            Dos.writeUTF(this.texto);
            //System.out.println(this.texto);
        } catch (IOException ex) {
            Logger.getLogger(Hilo_enviar_cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        // mientras continuar ...
        while (continuar)
        {
             this.texto= this.sc.nextLine();
             if(this.texto.equalsIgnoreCase("/salir"))
             {
                 this.cliente.terminar();
             }
            try {
                //System.out.println(this.texto);
                Dos.writeUTF(this.texto);
            } catch (IOException ex) {
                Logger.getLogger(Hilo_enviar_cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
} 