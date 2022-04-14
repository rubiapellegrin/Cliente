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
        String mensagem, ope = "";
        Double valorDeposito;
        int cont=0;
        try {
            
            System.out.println("Estabelecendo conexao..");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexao estabelecida..");
            
            
                
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Scanner ler = new Scanner(System.in);
            System.out.println("Enviando mensagem..");
            while(cont == 0){
            Mensagem m;
          
            
                
            //System.out.println(ope);    
                
            System.out.println("\n--------Informe--------- \nPara consulta - CONSULTA\nPara deposito - DEPOSITO\nPara saque - SAQUE\n------------------------\n");
            System.out.println("Informe a operacao: ");
            ope = ler.next();

            switch (ope) {
                case "CONSULTA":
                    m = new Mensagem(ope);

                    System.out.println("\nInforme o numero da conta: ");
                    mensagem = ler.next();

                    m.setParam("Conta", mensagem);

                    output.writeObject(m);
                    output.flush();

                    System.out.println("\n");
                    System.out.println(m);


                    m = (Mensagem) input.readObject();
                    System.out.println("\n");
                    System.out.println("Resposta: \n" + m);


                    break;
                case "DEPOSITO":
                    m = new Mensagem(ope);

                    System.out.println("\nInforme o numero da conta: ");
                    mensagem = ler.next();

                    System.out.println("\nInforme o um valorC: ");
                    valorDeposito = ler.nextDouble();
                    
                    m.setParam("Conta", mensagem);
                    m.setParam("Valor", valorDeposito);

                    output.writeObject(m);
                    output.flush();

                    m = (Mensagem) input.readObject();
                    System.out.println("\n");
                    System.out.println("Resposta: \n" + m);
                    break;
                    
                   case "SAQUE":
                    m = new Mensagem(ope);

                    System.out.println("\nInforme o numero da conta: ");
                    mensagem = ler.next();

                    System.out.println("\nInforme o um valorC: ");
                    valorDeposito = ler.nextDouble();
                    
                    m.setParam("Conta", mensagem);
                    m.setParam("Valor", valorDeposito);

                    output.writeObject(m);
                    output.flush();

                    m = (Mensagem) input.readObject();
                    System.out.println("\n");
                    System.out.println("Resposta: \n" + m);
                    break;
                    case "tchau":
                        cont =1;
                        break;
                }
           }
           // input.close();
            //output.close();
           // socket.close();

        } catch (IOException ex) {
            System.out.println("Erro no cliente.");
            System.out.println("Erro: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro no cast: " + ex.getMessage());

        }
    }
}
