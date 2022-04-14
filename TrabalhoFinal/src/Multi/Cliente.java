package Multi;

import java.io.PrintStream;
import java.net.Socket;


/**
 * Classe que guarda os clientes
 */
public class Cliente {
    private String id;
    private String ip;
    private String nome;
 
    private PrintStream saida;
    private Socket socket;

    
    /**
    * Metodo que retorna o nome do cliente
    * @param nome - guarda o nome
    */ 
    public String getNome() {
        return nome;
    }
    
    /**
    * Metodo que guarda o nome do cliente
    * @param nome - guarda o nome
    */ 
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
    * Metodo que retorna o socket da classe socket
    * @param socket - guarda o socket
    */ 
    public Socket getSocket() {
        return socket;
    }
    
    /**
    * Metodo que guarda o socket da classe socket
    * @param socket - guarda o socket
    */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
    * Metodo que mostra a saida da tela
    * @param saida - guarda a saida
    */
    public PrintStream getSaida() {
        return saida;
    }

    /**
    * Metodo que guarda a saida da tela
    * @param saida - guarda a saida
    */
    public void setSaida(PrintStream saida) {
        this.saida = saida;
    }    

    /**
    * Metodo que retorna o id do cliente
    * @param id - guarda a numero ou id
    */
    public String getId() {
        return id;
    }

    /**
    * Metodo que guarda o id do cliente
    * @param id - guarda a numero ou id
    */
    public void setId(String id) {
        this.id = id;
    }

    /**
    * Metodo que retorna o ip do cliente
    * @param ip - guarda a numero do ip do usuario conectado
    */
    public String getIp() {
        return ip;
    }

    /**
    * Metodo que guarda o ip do cliente
    * @param ip - guarda a numero do ip do usuario conectado
    */
    public void setIp(String ip) {
        this.ip = ip;
    }


}
