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
import util.Status;
import util.Mensagem;

/**
 *
 * @author rubia
 */
public class Cliente {

    public static void main(String[] args) throws ClassNotFoundException {
        String titulo = null, descricao = null, prioridade = null, ope = null, usuario, senha;
        int cont = 0;
        Double valorPago;
        try {

            System.out.println("Estabelecendo conexao..");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexao estabelecida..");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Scanner ler = new Scanner(System.in);
            System.out.println("Enviando mensagem..");

            while (cont == 0) {

                Mensagem m;
                int contOpe = 0;
                System.out.println("\n------------------------Informe------------------------------ \nLOGIN - Para logar\nENVIAR - Para enviar anuncio"
                        + "\nCONFIRMAR - Para confirmar anuncio\nEXCLUIR - Para excluir anuncio"
                        + "\nBUSCA - Para buscar anuncio\nCOMPRA - Para comprar anuncio"
                        + "\nLISTAR - Para listar anuncios"
                        + "\nLANCES - Para listar lances"
                        + "\nCONFIRMLANCE - Para confirmar lances"
                        + "\nCANCELANCE - Para cancelar lances\nLOGOUT - Para logout"
                        + "\nSAIR - Para sair\n--------------------------------------------------------------\n");
                while (contOpe != 1) {
                    System.out.println("Informe a operacao: ");
                    ope = ler.nextLine();
                    if (!ope.isEmpty()) {
                        contOpe = 1;  //verifica se não é nulo
                    }
                }
                ope = ope.toUpperCase();//caixa alta

                switch (ope) {

                    case "LOGIN":
                        m = new Mensagem(ope);

                        System.out.println("\nInforme o nome de usuario: ");
                        usuario = ler.next();

                        m.setParam("user", usuario);

                        System.out.println("\nInforme a senha: ");
                        senha = ler.next();

                        m.setParam("pass", senha);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");

                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "ENVIAR":
                        m = new Mensagem(ope);

                        int conTitulo = 0;
                        while (conTitulo != 1) {
                            System.out.println("\nInforme o titulo: ");
                            titulo = ler.nextLine();
                            if (!titulo.isEmpty()) {
                                conTitulo = 1;
                            }
                        }
                        m.setParam("titulo", titulo);

                        int contDesc = 0;
                        while (contDesc != 1) {
                            System.out.println("\nInforme o descricao: ");
                            descricao = ler.nextLine();
                            if (!descricao.isEmpty()) {
                                contDesc = 1;
                            }
                        }
                        m.setParam("descricao", descricao);

                        int contPri = 0;
                        while (contPri != 1) {
                            System.out.println("\nInforme prioridade(alta, media e baixa): ");
                            prioridade = ler.nextLine();
                            if (prioridade.equals("alta") || prioridade.equals("baixa") || prioridade.equals("media") && !"".equals(prioridade)) {
                                contPri = 1;
                            }
                        }
                        m.setParam("prioridade", prioridade);

                        output.writeObject(m);
                        output.flush();

                        System.out.println("\n");

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "LISTAR":
                        m = new Mensagem(ope);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "LANCES":
                        m = new Mensagem(ope);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "BUSCA":
                        m = new Mensagem(ope);

                        System.out.println("\nInforme o titulo: ");
                        titulo = ler.nextLine();

                        m.setParam("titulo", titulo);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "CONFIRMLANCE":
                        m = new Mensagem(ope);

                        System.out.println("\nInforme o titulo: ");
                        titulo = ler.nextLine();

                        m.setParam("titulo", titulo);

                        System.out.println("\nInforme o valor: ");
                        valorPago = ler.nextDouble();

                        m.setParam("valorPago", valorPago);
                        
                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "CANCELANCE":
                        m = new Mensagem(ope);

                        System.out.println("\nInforme o titulo: ");
                        titulo = ler.nextLine();

                        m.setParam("titulo", titulo);

                        System.out.println("\nInforme o valor: ");
                        valorPago = ler.nextDouble();

                        m.setParam("valorPago", valorPago);
                        
                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "EXCLUIR":
                        m = new Mensagem(ope);

                        System.out.println("\nInforme o titulo: ");
                        titulo = ler.nextLine();

                        m.setParam("titulo", titulo);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "CONFIRMAR":
                        m = new Mensagem(ope);

                        System.out.println("\nInforme o titulo: ");
                        titulo = ler.nextLine();

                        m.setParam("titulo", titulo);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "COMPRA":
                        m = new Mensagem(ope);

                        System.out.println("\nInforme o titulo: ");
                        titulo = ler.nextLine();

                        m.setParam("titulo", titulo);

                        System.out.println("\nInforme o valor: ");
                        valorPago = ler.nextDouble();

                        m.setParam("valorPago", valorPago);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    case "SAIR":
                        m = new Mensagem(ope);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        cont = 1;
                        break;
                    case "LOGOUT":
                        m = new Mensagem(ope);

                        output.writeObject(m);
                        output.flush();

                        m = (Mensagem) input.readObject();
                        System.out.println("\n");
                        System.out.println("\n-------------------------------------------------------------\n");
                        System.out.println("Resposta: \n" + m);
                        System.out.println("\n-------------------------------------------------------------\n");
                        break;
                    default:
                        System.out.println("\n");
                        System.out.println("\nOperação não encontrada! Informe novamente.\n");
                        break;
                }
            }
            input.close();
            output.close();
            socket.close();

        } catch (IOException ex) {
            System.out.println("Erro no cliente.");
            System.out.println("Erro: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro no cast: " + ex.getMessage());

        }
    }
}
