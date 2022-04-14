/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Mensagem;

/**
 *
 * @author rubia
 */
public class Cliente1 {

    public static void main(String args[]) throws ClassNotFoundException {

        
        try {
            System.out.println("Estabelecendo conexao..");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexao estabelecida..");
            
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream( socket.getInputStream());
            System.out.println("Enviando mensagem... ");
            
            
            
            
            
            Mensagem m = new Mensagem("LOGIN");
            m.setParam("user","Aluno");
            m.setParam("pass","senha");
            
            output.writeObject(m);
            output.flush();
            
            m = (Mensagem) input.readObject();
            System.out.println("\n");
            System.out.println("Resposta: \n"+ m);
            
            
            

            
            
            m = new Mensagem("SAIR");
            //envia ao servidor
            output.writeObject(m);
            output.flush();

            
            
            
            ///finalizar
            

            
            input.close();
            output.close();
            socket.close();
            
        } catch (IOException ex) {
             System.out.println("Erro no cliente: " + ex);
        }
        

    }
}
