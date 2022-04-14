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
public class Conta {
    private Integer saldo;

    public Conta(Integer saldo) {
        this.saldo = saldo;
    }
    
    public void deposita(Integer valor) throws InterruptedException{
        System.out.println("Calculando");
        Thread.sleep(10000);
        System.out.println("fim dos calculos");
        this.saldo += valor;
       
    }
    
    public Integer saque( Integer valor ) throws InterruptedException
    {
        while( saldo < valor  ){
        }
        Thread.sleep(1000);
        System.out.println("Fazendo saque");
            saldo -= valor;
            return valor;
    }

    public Integer getSaldo() {
        return saldo;
    }
}
