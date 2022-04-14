/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rubia
 */
public class Server {
    private ServerSocket serverSocket;
    
    public void criaServerSocket(int porta) throws IOException{
        serverSocket = new ServerSocket(porta);
    }
    
    public Socket esperaConexao() throws IOException{
        Socket socket = serverSocket.accept();
        return socket;
    }
    
    public void fechaSocket(Socket socket) throws IOException{
        socket.close();
        
    }    
    
    private void tratarConexao(Socket socket) throws IOException{

        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            
            //protocolo
            System.out.println("Tratando..");
            String msg = input.readUTF();
            System.out.println("Mensagem recebida!.." + msg);
            output.writeUTF("Hello wold");
            output.flush();
            
            
           input.close();
           output.close();
        } catch (IOException ex) {
            System.out.println("Problema no tratamento da conexao com o cliente.. " + socket.getInetAddress());
            System.out.println("Erro: " + ex.getMessage());
        } finally {
                fechaSocket(socket);
        }
    }
    
    public static void main(String[] args){

        try {
            
            Server server =new Server();
            System.out.println("Aguardando conexao..");
            server.criaServerSocket(5555);
            while(true){
                Socket socket = server.esperaConexao();
                System.out.println("Cliente conectado!");
                server.tratarConexao(socket);
                System.out.println("Cliente finalizado!");
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
