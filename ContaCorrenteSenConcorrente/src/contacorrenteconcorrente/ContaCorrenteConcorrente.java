/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contacorrenteconcorrente;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author elder
 */
public class ContaCorrenteConcorrente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Conta conta = new Conta(0);
        ArrayList<Thread> threads = new ArrayList<>();
        System.out.println("Saldo inicial= "+ conta.getSaldo());
        for( int i=0; i<200; i++ ){
            Thread th = new Thread( new Correntista(conta, i, Boolean.TRUE) );
            threads.add(th);
            th.start();
            th = new Thread( new Correntista(conta, i, false) );
            threads.add(th);
            th.start();
        }
        
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ContaCorrenteConcorrente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        
        System.out.println("Saldo final= "+ conta.getSaldo());
        
    }
    
}
