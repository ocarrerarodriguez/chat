package cli.server;


import cli.server.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hilo_enviar_servidor extends Thread
{
    // boolean que pondremos a false cuando queramos parar el hilo
    private boolean continuar = true;
    Server_X_Client servidor;
    Socket cliente;
    LinkedList<Datos_cli> arrayClientes;
    Mensaje mensaje;
    DataOutputStream Dos;
    String salida="";
    Iterator iterador;
    
    

    public Hilo_enviar_servidor(Server_X_Client servidor,Mensaje mensaje)
    {
      
       this.servidor=servidor;
       this.mensaje=mensaje;//lista con los mensajes recibidos del los clintes
       //sockets id ,hilos,nick de los clientes
       this.arrayClientes=this.servidor.getarrayClientes(); 
       
       
    }
    // metodo para terminar el bucle infinito
    public void terminar()
    {
        this.continuar=false;
    }
    public void generar_Dos() throws IOException
    {
        //abro el canal de escritura en el servidor
       this.Dos=new DataOutputStream (this.cliente.getOutputStream());
    }

    // Metodo del hilo
    public void run()
    {
        while (continuar)
        {       
            try {
                //recoger el mensaje del almacen
                this.salida=this.mensaje.leer();
                 this.iterador  = arrayClientes.iterator();
                 System.out.println(salida);
            } catch (InterruptedException ex) {
                Logger.getLogger(Hilo_enviar_servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            //bucle para enviar mensajes
             while (this.iterador.hasNext())
             {
                try {
                    //cambiar el datos cli por el siguiente
                    this.cliente=((Datos_cli)iterador.next()).get_socket();
                    this.generar_Dos();
                    // abrir conexion con el n esimo socket para enviar el mensaje
                    this.Dos.writeUTF(this.salida);
                } catch (IOException ex) {
                    Logger.getLogger(Hilo_enviar_servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}