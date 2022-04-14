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
import util.Status;
import util.Mensagem;

/**
 *
 * @author rubia
 */
public class Server {

    private ServerSocket serverSocket;

    public void criaServerSocket(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
    }

    public Socket esperaConexao() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    public void fechaSocket(Socket socket) throws IOException {
        socket.close();

    }

    private void tratarConexao(Socket socket) throws IOException, ClassNotFoundException {
        int cont = 0;
        try {
            String [] a = new String[9];
            int i=0;
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String escolhaCliente = "";

            while (!escolhaCliente.equals("tchau")) {
                
                System.out.println("Tratando..");
                Mensagem m = (Mensagem) input.readObject();
                String operacao = (String) m.getOperacao();

                System.out.println("Mensagem recebida:" + m);
                escolhaCliente = "" + m;
                
                a[i] = escolhaCliente;
                i++;
                Mensagem reply = null;
                if (escolhaCliente.equals("tchau") || !a[0].contains("oi")) {
                    reply = new Mensagem("pare");
                    output.writeObject(reply);
                    output.flush();
                    output.close();
                    input.close();
                    socket.close();
                    System.exit(0);
                } else {
                    reply = new Mensagem("continue");
                    output.writeObject(reply);
                    output.flush();
                }
            }
            

        } catch (IOException ex) {
            System.out.println("Problema no tratamento da conexao com o cliente.. " + socket.getInetAddress());
            System.out.println("Erro: " + ex.getMessage());
        } finally {
            fechaSocket(socket);
        }
    }

    public static void main(String[] args) {

        try {

            Server server = new Server();
            System.out.println("Aguardando conexao..");
            server.criaServerSocket(5555);
            while (true) {
                Socket socket = server.esperaConexao();
                System.out.println("Cliente conectado!");
                server.tratarConexao(socket);
                System.out.println("Cliente finalizado!");
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro no cast: " + ex.getMessage());
        }

    }
}
