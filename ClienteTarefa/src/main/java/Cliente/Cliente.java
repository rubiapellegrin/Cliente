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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rubia
 */
public class Cliente {
    public static void main(String[] args){
        
        try {
            System.out.println("Estabelecendo conexao..");
            Socket socket =  new Socket("localhost",5555);
            System.out.println("Conexao estabelecida..");
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
  
            System.out.println("Enviando mensagem..");
            String msg = "Hello";
            output.writeUTF(msg);
            output.flush();
            System.out.println("Mensagem " + msg + " enviada. ");
            
            msg = input.readUTF();
            System.out.println("Resposta: "+ msg);
            
            input.close();
            output.close();
            socket.close();
            
        } catch (IOException ex) {
             System.out.println("Erro no cliente.");
             System.out.println("Erro: " + ex.getMessage());
        }
        
        
    }
}
