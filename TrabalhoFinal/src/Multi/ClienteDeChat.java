
package Multi;

import java.io.*;
import java.net.*;

/**
 * Classe que guarda os clientes do chat de comunicacao
 * 
 */
public class ClienteDeChat extends Thread {
    private static int cont;

    private static boolean done = false;

    private Socket conexao;
    
    /**
     * Metodo que guarda a conexão
     * @param socket - guarda o numero do socket 
    */
    public ClienteDeChat(Socket s) {
        conexao = s;
    }

    /**
     * Metodo que faz a execução da Thread, recebe a linha e envia ao servidor
    */
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while (true) {
               
                linha = entrada.readLine();

                if (linha == null) {
                    System.out.println("Conexão encerrada!");
                    break;
                }
                System.out.println();
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        done = true;
    }

    
    /**
     * Metodo  que recebe a conexão com o cliente e a primeira jogada
    */
    public static void main(String args[]) {
        try {

            Socket conexao = new Socket("127.0.0.1", 2222);

            PrintStream saida = new PrintStream(conexao.getOutputStream());

            BufferedReader teclado= new BufferedReader(new InputStreamReader(System.in));
            System.out.print( "Entre com o seu nome: ");
            String meuNome = teclado.readLine();
            saida.println(meuNome);

            Thread t = new ClienteDeChat(conexao);
            t.start();

            String linha;
            
            while (true) {

                cont++;
  
                if(cont== 1){
                    System.out.print("Partida 1: Informe sua jogada : "  );
                }
                
                linha = teclado.readLine();

                if (done) {
                    break;
                }

                saida.println(linha);
            }
        } catch (IOException e) {

            System.out.println("IOException: " + e);
        }
    }
}
