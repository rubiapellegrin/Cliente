/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contacorrenteconcorrente;

/**
 *
 * @author elder
 */
public class Correntista implements Runnable {
    
    Conta conta;
    int id;
    Boolean tipo;

    public Correntista(Conta conta, int id, Boolean tipo) {
        this.conta = conta;
        this.id = id;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        
        for( int i=0; i<1000; i++ )
        {
            if( tipo ){
                conta.deposita(i);
            }else
                conta.saque(i);
        }
  
    }    
}
