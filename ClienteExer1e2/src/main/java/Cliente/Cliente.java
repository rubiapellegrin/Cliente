/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Status;
import util.Mensagem;

/**
 *
 * @author rubia
 */
public class Cliente {

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            String mensagem;
            System.out.println("Estabelecendo conexao..");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexao estabelecida..");
            Scanner ler = new Scanner(System.in);
            String retorno = "continue"; //inicializa

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            while (!retorno.equals("pare")) {

                System.out.println("Informe uma mensagem: ");
                mensagem = ler.next();

                Mensagem m = new Mensagem(mensagem);

                // System.out.println("\nAQUI...... ");
                output.writeObject(m);
                output.flush();

                m = (Mensagem) input.readObject();
                retorno = "" + m;
                System.out.println(" - Resposta do servidor: " + retorno + " - ");
            }

            input.close();
            output.close();
            socket.close();

        } catch (IOException ex) {
            System.out.println("Erro no cliente.");
            System.out.println("Erro: " + ex.getMessage());
        }
        /*
        catch(ClassNotFoundException ex){
            System.out.println("Erro no cast: " + ex.getMessage());
        
        }
         */

    }
}
