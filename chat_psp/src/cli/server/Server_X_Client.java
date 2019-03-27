package cli.server;

import cli.server.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server_X_Client {
    ServerSocket servidor=null;
    Socket cliente =null;
    int maximoConexiones = 10; // Maximo de conexiones simultaneas
    int usuarios_actuales=0;//numero de conexiones instantaneas
    DataOutputStream Dis;
    String nick;
    Mensaje mensaje;
    LinkedList<Datos_cli> arrayClientes=new LinkedList<Datos_cli>();
    Hilo_recibir_servidor  hrs;
    Hilo_enviar_servidor  hes;
    

    public Server_X_Client(int puerto)
    {
        
        try {
            this.mensaje=new Mensaje();
            servidor = new ServerSocket (puerto,this.maximoConexiones);
            //this.hes=new Hilo_enviar_servidor(this,mensaje);
            System.out.println("Servidor Levantado");
            // genero una serie de hilos atraves de los cuales gestionare las conexiones entrantes
        } catch (IOException ex) {
            Logger.getLogger(Server_X_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        while(true){
        
            try {
                this.cliente = servidor.accept();
                //cliente.setSoLinger (true, 10);
                System.out.println("conexion establcida"+cliente.getInetAddress()+cliente.getPort());
                
                hrs=new Hilo_recibir_servidor(cliente,(usuarios_actuales+1),this,mensaje);
                //ojo el +1 es poque despues vamos a setear el tamño de usuarios actuales
                arrayClientes.add(new Datos_cli(cliente,hrs,(usuarios_actuales+1)));
                usuarios_actuales=arrayClientes.size();
                hrs.start();
                System.out.println("hilo lanzado");
            } catch (IOException ex) {
                Logger.getLogger(Server_X_Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    public void terminar(int id ) throws IOException
    {

        //busco en el LinkedList el id pasado por parametros

        for (int i = 0; i <= arrayClientes.size() - 1; i++) 
        {
            if (arrayClientes.get(i).get_id()==id) 
            {
                //cierro el soket asociado a la conexion
                Socket socket=this.arrayClientes.get(i).get_socket();
                socket.close();
                //coloco en hilo una refencia a si mismo
                //con ello ejecuto el metodo terminar del mismo
                Hilo_recibir_servidor hilo;
                hilo = (Hilo_recibir_servidor) this.arrayClientes.get(i).get_hilo();
                //termino el hilo que esta contenido en el objeto
                hilo.terminar();
                //quito el objeto datos_cli del LinkedList
                this.arrayClientes.remove(i);

            }
        }
    }

    public LinkedList getarrayClientes()
    {
        return arrayClientes;
    }
 

    public static void main (String [] args){
         Server_X_Client serv= new Server_X_Client(5558);
     }
}