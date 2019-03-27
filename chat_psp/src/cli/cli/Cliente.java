package cli.cli;

public class Cliente {
private String direcion="localhost";
private int puerto=5558;
Hilo_enviar_cliente ecliente;
Hilo_recivir_cliente rcliente;

    public Cliente()
    {
        this.ecliente =new Hilo_enviar_cliente(direcion,puerto,this);
        this.rcliente =new Hilo_recivir_cliente(direcion,puerto);
        this.ecliente.start();
        this.rcliente.start();
    }
   
    public void terminar()
    {
        this.ecliente.terminar();
        this.rcliente.terminar();
    }
    public static void main (String [] args)
     {
         Cliente cli= new Cliente();
     }

}