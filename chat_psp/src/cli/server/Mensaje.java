package cli.server;

import cli.server.*;
import java.util.LinkedList;

public class Mensaje
{
    LinkedList<String> Mensajes=new LinkedList<String> ();
    

    //lista de mensajes
    //metodos sincronizados para leeer y escribir
    public synchronized void escribir(String cadena) throws InterruptedException
    {
        synchronized(this.Mensajes)
        {
           this.Mensajes.addLast(cadena);
           this.Mensajes.notify(); 
        } 
    }
    public synchronized String leer() throws InterruptedException
    {
        synchronized(this.Mensajes)
        {
            if (this.Mensajes.size()==0){wait();}
            String mensaje=this.Mensajes.getFirst();
            this.Mensajes.removeFirst();
            return mensaje;
        }
    } 
}