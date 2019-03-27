package cli.server;


import cli.server.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hilo_recibir_servidor extends Thread
{
    // boolean que pondremos a false cuando queramos parar el hilo
    private boolean continuar = true;
    Server_X_Client servidor;
    Socket cliente;
    int id;
    String nick;
    DataInputStream Dis;
    String salida="";
    Mensaje mensaje;
    String men;

    public Hilo_recibir_servidor(Socket cliente,int id,Server_X_Client servidor,Mensaje mensaje) throws IOException
    {
       
       this.cliente=cliente;
       this.id=id;
       this.servidor=servidor;
       this.mensaje=mensaje;//lista con los mensajes recibidos del clinte
        //abro el canal de escucha en el servidor
       this.Dis=new DataInputStream (cliente.getInputStream());
       //recojo el nick del cliente conectado
       System.out.println("intento leer el nick");
       this.nick=this.Dis.readUTF();
       System.out.println(this.nick);
       
    }
    // metodo para terminar el bucle infinito
    public void terminar()
    {
        this.continuar=false;
    }

    // Metodo del hilo
    public void run()
    {
        while (continuar)
        {
            try {
                //leer el mensaje
                try {
                    this.men = this.Dis.readUTF();
                } catch (IOException ex) {
                    Logger.getLogger(Hilo_recibir_servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (this.men.equalsIgnoreCase("/by"))
                {
                    try {
                        servidor.terminar(this.id);
                    } catch (IOException ex) {
                        Logger.getLogger(Hilo_recibir_servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.men ="el usuario : "+this.nick+"se ha desconectado " ;
                    this.nick="servidor";
                    
                }
                //componer el mensaje
                this.salida=this.nick+" : "+this.men;
                
                //enviaro al almacen
                System.out.println(this.salida);
                this.mensaje.escribir(this.salida);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Hilo_recibir_servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}