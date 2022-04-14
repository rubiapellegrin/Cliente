/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contacorrenteconcorrente;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        for( int i=0; i<10; i++ )
        {
            if( tipo ){
                try {
                    conta.deposita(i);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Correntista.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
                try {
                    conta.saque(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Correntista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
  
    }    
}
