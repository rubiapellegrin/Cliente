package Multi;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rubia De Pellegrin
 * @version 1.00
 */
public class ServidorDeChat extends Thread {


    public static String auxiliar;
    public static int contAuxiliar;
    public static int contCli1;
    public static int contCli2;
    public static String auxUser1;
    public static int jogadas1;
    public static int jogadas2;
    public static int empate1;
    public static int empate2;
    public static int resultadoVitoria1;
    public static int resultadoVitoria2;
    public static int numeroCliente;
    public static int contNome;
    public static int contNome2;
    private static String nomeUser1;
    private static String nomeUser2;
    
    private static List<Cliente> clientes;

    private Cliente cliente;

    private Socket conexao;

    private String nomeCliente;

    /**
     * Metodo para receber objetos do tipo cliente
    */ 
    public ServidorDeChat(Cliente c) {
        cliente = c;
    }

    /**
     * Classe de execução da thread e de gerenciadores das jogadas
     * numeroCliente - verificação do numero de cliente 
     * nomeCliente - guarda o nome do cliente
     * nomeUser1 - variavel auxiliar que guardar o nome do primeiro jogador na primeira partida
     * nomeUser2 - variavel auxiliar que guardar o nome do segundo jogador na primeira partida
     * linha - variavel que recebe a jogada
     * Faz a verificcação da linha se diferente das jogadas retorna para a leitura da linha
     * Caso na leitura o jogador colocar a informação sair na linha então mostra o resultado e finaliza
     * Caso a linha seja aceita ela entra num while para receber as outras jogadas depois.
     * Faz a chamada da função que compara as jogadas.
     * auxiliar - guarda a jogada do primeiro jogador para comparar depois com a jogada do segundo jogador
     * Nova leitura da  variavel nomeUser1 - variavel auxiliar que guardar o nome do primeiro jogador depois da primeira partida
     * Nova leitura da  variavel nomeUser2 - variavel auxiliar que guardar o nome do segundo jogador depois da primeira partida
     * caso duas jogadas sejam feitas respectivamente do primeiro e segundo jogador reinicia as fariaveis controladoras e entra numa função
     * que guarda o resultado das jogadas e mostra o resultado em caso de encerramento.
     * Apos isso recebe uma nova jogada enquanto numero de partidas for menor que 10 ou que enquanto um dos jogadores não atingir 10 pontos.
     * Caso na leitura o jogador colocar a informação sair na linha então mostra o resultado e finaliza
    */
    public void run() {
        try {

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getSocket().getInputStream()));
            PrintStream saida = new PrintStream(cliente.getSocket().getOutputStream());
            cliente.setSaida(saida);

            if (numeroCliente == 4) {
                System.out.println("Excesso de usuários. Fim do programa. ");    
                System.exit(0);                                                                 
                clientes.remove(saida);
                conexao.close();
            }

            nomeCliente = entrada.readLine();

            if (nomeCliente == null) {
                return;
            }

            cliente.setNome(nomeCliente);

            contNome++;
            if (contNome == 1) {
                nomeUser1 = cliente.getNome();
            }
            if (contNome == 2) {
                nomeUser2 = cliente.getNome();
            }

            jogadas1 = 1;
            jogadas2 = 1;
  
            String linha = entrada.readLine();

            if (!linha.equals("papel") && !linha.equals("pedra") && !linha.equals("tesoura") && !linha.equals("sair")) {
                FuncRetorno(saida);
                linha = entrada.readLine();
            }   

            if (linha.equals("sair")) {
                System.out.println("Fim do jogo.");
                FuncSair(saida);
                sleep(1000);
                System.exit(0);
                clientes.remove(saida);
                conexao.close();
            }

            while (linha != null && !(linha.trim().equals(""))) {

                FuncCompara(saida, linha, auxiliar);

                auxiliar = linha;

                contNome2++;
                if (contNome2 == 1) {
                    nomeUser1 = cliente.getNome();
                }
                if (contNome2 == 2) {
                    nomeUser2 = cliente.getNome();
                }

                auxUser1 = cliente.getNome();

                if (contAuxiliar == 2) {
                    contAuxiliar = 0;
                    auxiliar = null;
                    auxUser1 = null;
                    FuncVerificação(saida, linha, auxiliar);
                }

                linha = entrada.readLine();

                if (!linha.equals("papel") && !linha.equals("pedra") && !linha.equals("tesoura") && !linha.equals("sair")) {
                    FuncRetorno(saida);
                    linha = entrada.readLine();

                }

                if (linha.equals("sair")) {
                    FuncSair(saida);
                    sleep(1000);
                    System.exit(0);
                    clientes.remove(saida);
                    conexao.close();
                }

            }
        } catch (IOException e) {

            System.out.println("Jogo finalizado. ");
        } catch (InterruptedException ex) {
            Logger.getLogger(ServidorDeChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * 
     * Metodo que mostrar uma mensagem ao cliente
     * @author Professor Roberto Wiest
    */
    public void sendToAll(PrintStream saida, String acao,
            String linha) throws IOException {

        Iterator<Cliente> iter = clientes.iterator();
        while (iter.hasNext()) {
            Cliente outroCliente = iter.next();
            
            PrintStream chat = (PrintStream) outroCliente.getSaida();

            if (chat != saida) {
                chat.println(cliente.getNome() + " com IP: " + cliente.getSocket().getRemoteSocketAddress() + acao + linha);
            }
        }
    }
    
    /**
     * Metodo que recebe as jogadas e as compara e guarda o vencedor da partida e em caso de empate uma variavel empate recebe o valor 1
     * @author Rubia De Pellegrin
     * @param saida - recebe a saida da tela
     * @param linha - a jogada do segundo jogador
     * @param auxiliar - que possui a jogada do primeiro jogador
    */
    public void FuncCompara(PrintStream saida, String linha, String auxiliar) throws IOException { 

        Iterator<Cliente> iter = clientes.iterator();
        while (iter.hasNext()) {
            Cliente outroCliente = iter.next();

            PrintStream chat = (PrintStream) outroCliente.getSaida();

            if (auxiliar != null) {
                contAuxiliar++;

                if (auxiliar.trim().equals("papel") && linha.trim().equals("pedra")) {
                    contCli1++;
                    chat.println("---------------------------------------------------------------------------\n"
                            + "Resultado : O usuario " + auxUser1 + " venceu essa partida! Pois papel embrulha pedra...\n"
                            + "---------------------------------------------------------------------------");
                }

                if (auxiliar.trim().equals("papel") && linha.trim().equals("tesoura")) {
                    contCli2++;
                    chat.println("---------------------------------------------------------------------------\n"
                            + "Resultado: O usuario " + cliente.getNome() + " venceu esta partida! Pois tesoura corta papel... \n"
                            + "---------------------------------------------------------------------------");
                }

                if (auxiliar.trim().equals("pedra") && linha.trim().equals("tesoura")) {
                    contCli1++;
                    chat.println("---------------------------------------------------------------------------\n"
                            + "Resultado: o usuario " + auxUser1 + " venceu essa partida! Pois pedra amassa e quebra a tesoura...\n"
                            + "---------------------------------------------------------------------------");
                }

                if (auxiliar.trim().equals("pedra") && linha.trim().equals("papel")) {
                    contCli2++;
                    chat.println("---------------------------------------------------------------------------\n"
                            + "Resultado: O usuario " + cliente.getNome() + " venceu esta partida! Pois papel embrulha pedra... \n"
                            + "---------------------------------------------------------------------------");
                }

                if (auxiliar.trim().equals("tesoura") && linha.trim().equals("papel")) {
                    contCli1++;
                    chat.println("---------------------------------------------------------------------------\n"
                            + "Resultado: O usuario " + auxUser1 + " venceu essa partida! Pois tesoura corta papel... \n"
                            + "---------------------------------------------------------------------------");
                }

                if (auxiliar.trim().equals("tesoura") && linha.trim().equals("pedra")) {
                    contCli2++;
                    chat.println("---------------------------------------------------------------------------\n"
                            + "Resultado: O usuario " + cliente.getNome() + " venceu esta partida! Pois pedra amassa e quebra a tesoura.. \n"
                            + "---------------------------------------------------------------------------");
                }

                if (auxiliar.trim().equals(linha.trim())) {
                    empate1 = 1;
                    empate2 = 1;
                    chat.println("---------------------------------------------------------------------------\n"
                            + "Erro! Empate jogue novamente. Partida desconsiderada. \n"
                            + "-------------------------------------------------------------------------");
                }
            }
        }
    }

    /**
     * Metodo que recebe as partidas vencidas e o numero de partidas, empates e em caso de encerramento mostra os resultados e finaliza.
     * @author Rubia De Pellegrin
     * @param saida - recebe a saida da tela
     * @param linha - a jogada do segundo jogador
     * @param auxiliar - que possui a jogada do primeiro jogador
    */
    public void FuncVerificação(PrintStream saida, String linha, String auxiliar) throws IOException, InterruptedException { 

        Iterator<Cliente> iter = clientes.iterator();
        while (iter.hasNext()) {
            Cliente outroCliente = iter.next();
            PrintStream chat = (PrintStream) outroCliente.getSaida();

            resultadoVitoria1 = contCli1 / 2;
            resultadoVitoria2 = contCli2 / 2;

            if (chat != saida) {

                sleep(1000);

                if (empate1 == 0) {
                    jogadas1++;
                }

                if (empate1 == 1) {
                    empate1 = 0;
                }

                if (jogadas1 == 11) {

                    if (resultadoVitoria2 != resultadoVitoria1 || resultadoVitoria1 == 10 || resultadoVitoria2 == 10) {

                        chat.println("Fim de jogo. Limite de jogadas atingido..  \n"
                                + "Partidas vencidas pelo usuario " + nomeUser1 + " : " + resultadoVitoria1 + " | "
                                + " Partidas vencidas pelo usuario " + nomeUser2 + " : " + resultadoVitoria2);
                    }

                    if (resultadoVitoria1 > resultadoVitoria2 || resultadoVitoria1 == 10) {
                        chat.println("Vencedor : " + nomeUser1);
                    }

                    if (resultadoVitoria2 > resultadoVitoria1 || resultadoVitoria2 == 10) {
                        chat.println("Vencedor : " + nomeUser2);
                    }

                    if (resultadoVitoria2 == resultadoVitoria1) {
                        jogadas1--;
                    }
                }

                if (jogadas2 > 11) {
                    sleep(2000);
                    chat.println("Fim... ");
                    System.exit(0);
                    clientes.remove(saida);
                    conexao.close();
                }

                if (jogadas1 < 11) {
                    chat.println("Partida " + jogadas1 + ": Informe a sua proxima jogada : ");
                }
            }

            if (chat == saida) {

                sleep(1000);

                if (empate2 == 0) {
                    jogadas2++;
                }

                if (empate2 == 1) {
                    empate2 = 0;
                }

                if (jogadas2 == 11) {

                    if (resultadoVitoria2 != resultadoVitoria1) {

                        chat.println("Fim de jogo. Limite de jogadas atingido.. \n"
                                + "Partidas vencidas pelo usuario " + nomeUser1 + " : " + resultadoVitoria1 + " | "
                                + " Partidas vencidas pelo usuario " + nomeUser2 + " : " + resultadoVitoria2);
                    }

                    if (resultadoVitoria1 > resultadoVitoria2 || resultadoVitoria1 == 10) {
                        chat.println("Vencedor : " + nomeUser1);
                    }

                    if (resultadoVitoria2 > resultadoVitoria1  || resultadoVitoria2 == 10) {
                        chat.println("Vencedor : " + nomeUser2 );
                    }

                    if (resultadoVitoria2 == resultadoVitoria1) {
                        jogadas2--;
                    }
                }

                if (jogadas2 > 11) {
                    sleep(2000);
                    chat.println("Fim... ");
                    System.exit(0);
                    clientes.remove(saida);
                    conexao.close();
                }

                if (jogadas2 < 11) {
                    chat.println("Aguarde o outro jogador jogar.. ");
                    sleep(6000);
                    chat.println("Partida " + jogadas2 + ": Informa a sua proxima jogada : ");
                }

            }
        }
    }


    
    /**
     * Metodo que mostra o resulatdo final em caso de encerramento do jogo
     * @author Rubia De Pellegrin
    */
    public void FuncSair(PrintStream saida) throws IOException {

        Iterator<Cliente> iter = clientes.iterator();
        while (iter.hasNext()) {
            Cliente outroCliente = iter.next();

            PrintStream chat = (PrintStream) outroCliente.getSaida();

            chat.println("-->Um dos usuarios saiu do jogo. Fim de jogo. \n"
                    + "Partidas vencidas pelo usuario " + nomeUser1 + " : " + resultadoVitoria1 + " | "
                    + " Partidas vencidas pelo usuario " + nomeUser2 + " : " + resultadoVitoria2);

            if (resultadoVitoria1 > resultadoVitoria2  || resultadoVitoria1 == 10) {
                chat.println("Vencedor : " + nomeUser1);
            }

            if (resultadoVitoria2 > resultadoVitoria1  || resultadoVitoria2 == 10) {
                chat.println("Vencedor : " + nomeUser2);
            }

            if (resultadoVitoria2 == resultadoVitoria1) {
                chat.println("Nenhum vencedor ou empate. ");
            }
        }
    }
    
    /**
     * Metodo de impressão, caso o usuario informa algo diferente de pedra, papel, tesoura ou sair.
     * @author Rubia De Pellegrin
    */
    public void FuncRetorno(PrintStream saida) throws IOException {

        Iterator<Cliente> iter = clientes.iterator();
        while (iter.hasNext()) {
            Cliente outroCliente = iter.next();
            
            PrintStream chat = (PrintStream) outroCliente.getSaida();
            
            if (chat == saida) {
                chat.println("--->Sintaxe não aceita! Aceitas: pedra , papel e tesoura. Informe a jogada : ");
            }
        }
    }
    
    /**
     * Recebe e guarda as conexões dos clientes.
     * @author Professor Roberto Wiest
    */
    public static void main(String args[]) {
        ServerSocket s = null;
        Socket conexao = null;

        clientes = new ArrayList<Cliente>();

        try {

            s = new ServerSocket(2222);

            while (true) {

                numeroCliente++;

                if (numeroCliente == 1 || numeroCliente == 2) {
                    System.out.print("Esperando alguem se conectar: ");
                }
                conexao = s.accept();
                Cliente cliente = new Cliente();
                cliente.setId(conexao.getRemoteSocketAddress().toString());
                cliente.setIp(conexao.getRemoteSocketAddress().toString());
                cliente.setSocket(conexao);
                clientes.add(cliente);

                if (numeroCliente == 1 || numeroCliente == 2) {
                    System.out.println("O usuario com ip " + conexao.getRemoteSocketAddress() + " se Conectou! ");
                }

                Thread t = new ServidorDeChat(cliente);
                t.start();
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

    }
}
