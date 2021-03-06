/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.List;
import view.TelaPrincipal;

/**
 *
 * @author André
 */
public class EscalonadorUsuario implements Runnable{
    private static EscalonadorUsuario instancia;
    
    private static List<Processo> filaFeed1 = new ArrayList<Processo>();
    private static List<Processo> filaFeed2 = new ArrayList<Processo>();
    private static List<Processo> filaFeed3 = new ArrayList<Processo>();
    private Processo processoAtual;
    private int quantum = 2;
    
    private EscalonadorUsuario(){
        
    }
    /* CASO QUEIRAMOS IMPLEMENTAR A FUNÇÃO DE LIMPAR O PROGRAMA PARA INSERIR OUTRO ARQUIVO TEREMOS QUE USAR ALGO DESTE TIPO
    public static void meLimpe(){
        filaFeed1 = new ArrayList<Processo>();
        filaFeed2 = new ArrayList<Processo>();
        filaFeed3 = new ArrayList<Processo>();
        Processo processoAtual = null;
    }
    */
    public static synchronized EscalonadorUsuario getInstance(){
        if(instancia==null){
            instancia = new EscalonadorUsuario();
        }
        return instancia;
    }
    
    public void run(){
        System.out.println(TelaPrincipal.momentoAtual);
       if(EscalonadorTempoReal.filaFCFS.size()==0){//só executa os processos de FEEDBACK caso nenhum de TEMPO REAL esteja na fila de FCFS
           
           if(filaFeed1.size() != 0){
               CPU cpuAtual = null;
               int NumCpuAtual = proximaCPULivre();
               if(NumCpuAtual == -1){
                  return;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
               }else{
                   //o numero era pra ser CPU 1 
                   //mas a funcao retornou CPU 0 , que esta ocupada com outro processo, PQ?
                   System.out.println("CPU NUMERO "+NumCpuAtual);
                   //System.out.println(Maquina.getInstance().listaCPU.get(NumCpuAtual).ProcessoExecutando.getNumero());
                   
                   cpuAtual= Maquina.getInstance().listaCPU.get(NumCpuAtual);
                   int contador = 0;
                   while((cpuAtual != null) && filaFeed1.size() != 0){//se ainda tem CPU e ainda tem processos na fila 1
                        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+filaFeed1.get(contador).getNumero()+" removido da fila Feed 1");
                        cpuAtual.setProcessoExecutando(filaFeed1.get(contador), NumCpuAtual);
                        cpuAtual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        cpuAtual.ProcessoExecutando.setQuantumRestante((int) Math.pow(quantum,1));
                        filaFeed1.remove(0);
                        TelaPrincipal.setTextoFilaFeed(TelaPrincipal.listToString(filaFeed1));
                        
                        NumCpuAtual = proximaCPULivre();
                        if(NumCpuAtual == -1){
                            break;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
                        }
                        cpuAtual= Maquina.getInstance().listaCPU.get(NumCpuAtual);
                   }
                   if(NumCpuAtual == -1){
                       //saiu porque não tinha mais CPU's livres, logo não tem mais nada para o escalonador fazer
                       return;
                   }
               }
           }
           if(filaFeed2.size() != 0){
               CPU cpuAtual = null;
               int NumCpuAtual = proximaCPULivre();
               if(NumCpuAtual == -1){
                  return;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
               }else{
                   cpuAtual= Maquina.getInstance().listaCPU.get(NumCpuAtual);
                   int contador = 0;
                   while((cpuAtual != null) && filaFeed2.size() != 0){//se ainda tem CPU e ainda tem processos na fila 1
                        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+filaFeed2.get(contador).getNumero()+" removido da fila Feed 2");
                        cpuAtual.setProcessoExecutando(filaFeed2.get(contador), NumCpuAtual);
                        cpuAtual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        cpuAtual.ProcessoExecutando.setQuantumRestante((int) Math.pow(quantum,2));
                        filaFeed2.remove(0);
                        TelaPrincipal.setTextoFilaFeed2(TelaPrincipal.listToString(filaFeed2));
                        
                        NumCpuAtual = proximaCPULivre();
                        if(NumCpuAtual == -1){
                            break;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
                        }
                        cpuAtual= Maquina.getInstance().listaCPU.get(NumCpuAtual);
                   }
                   if(NumCpuAtual == -1){
                       //saiu porque não tinha mais CPU's livres, logo não tem mais nada para o escalonador fazer
                       return;
                   }
               }
           }
           if(filaFeed3.size() != 0){
               CPU cpuAtual = null;
               int NumCpuAtual = proximaCPULivre();
               if(NumCpuAtual == -1){
                  return;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
               }else{
                   cpuAtual= Maquina.getInstance().listaCPU.get(NumCpuAtual);
                   int contador = 0;
                   while((cpuAtual != null) && filaFeed3.size() != 0){//se ainda tem CPU e ainda tem processos na fila 1
                        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+filaFeed3.get(contador).getNumero()+" removido da fila Feed 3");
                        cpuAtual.setProcessoExecutando(filaFeed3.get(contador), NumCpuAtual);
                        cpuAtual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        cpuAtual.ProcessoExecutando.setQuantumRestante((int) Math.pow(quantum,3));
                        filaFeed3.remove(0);
                        TelaPrincipal.setTextoFilaFeed3(TelaPrincipal.listToString(filaFeed3));
                        NumCpuAtual = proximaCPULivre();
                        if(NumCpuAtual == -1){
                            break;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
                        }
                        cpuAtual= Maquina.getInstance().listaCPU.get(NumCpuAtual);
                   }
                   if(NumCpuAtual == -1){
                       //saiu porque não tinha mais CPU's livres, logo não tem mais nada para o escalonador fazer
                       return;
                   }
               }
           }
           
           
       }
    }
    
    public static void insereProcesso(Processo processo){
        switch(processo.getPrioridadeSimbolica()){
            case 1: filaFeed1.add(processo);
                    TelaPrincipal.setTextoFilaFeed(TelaPrincipal.listToString(filaFeed1));
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+processo.getNumero()+" inserido na fila Feed 1");
                break;
            case 2: filaFeed2.add(processo);
                    TelaPrincipal.setTextoFilaFeed2(TelaPrincipal.listToString(filaFeed2));
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+processo.getNumero()+" inserido na fila Feed 2");
                break;
            case 3: filaFeed3.add(processo);
                    TelaPrincipal.setTextoFilaFeed3(TelaPrincipal.listToString(filaFeed3));
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+processo.getNumero()+" inserido na fila Feed 3");
                break;
            
        }
    }
    
    private static int checaMemoriaFila(List<Processo> fila){
        int qtdBlocos = 0;
        for(int i=0;i<fila.size();i++){
            qtdBlocos += fila.get(i).getIndices().length;
        }
        return qtdBlocos*GerenciadorMemoria.tamanhoBloco;
    }
    
    //checa a memoria que pode estar disponivel ao processo baseada em sua prioridade
    private static boolean checaMemoria(Processo processo){
        int sum = 0;
        switch(processo.getPrioridade()){
            case 0:
                sum += GerenciadorMemoria.memoriaDisponivel(Despachante.listaBlocos);
                sum += checaMemoriaFila(filaFeed3);
                sum += checaMemoriaFila(filaFeed2);
                sum += checaMemoriaFila(filaFeed1);
                if(processo.getMemoria() <= sum)
                    return true;
                break;
            case 1:
                sum += GerenciadorMemoria.memoriaDisponivel(Despachante.listaBlocos);
                sum += checaMemoriaFila(filaFeed3);
                sum += checaMemoriaFila(filaFeed2);
                if(processo.getMemoria() <= sum)
                    return true;
                break;
            case 2:
                sum += GerenciadorMemoria.memoriaDisponivel(Despachante.listaBlocos);
                sum += checaMemoriaFila(filaFeed3);
                if(processo.getMemoria() <= sum)
                    return true;
                break;
            /*case 3:  a funcao nao e chamada para processos de prioridade 3
                sum += GerenciadorMemoria.memoriaDisponivel(Despachante.listaBlocos);
                if(processo.getMemoria() <= sum)
                    return true;
                break;
            */
        }
        return false;
    }
    
    private static int abreEspacoRotina(int blocos,List<Processo> fila){
        while((blocos>0)&&(fila.size()!=0)){
            Processo p = fila.get(fila.size()-1); //pega o ultimo processo da fila
            blocos = blocos - p.getIndices().length;
                
            fila.remove(p);
            GerenciadorMemoria.removeProcesso(p, Despachante.listaBlocos);
            Maquina.getInstance().scannerDisp += p.getScanner();
            Maquina.getInstance().impressoraDisp += p.getImpressora();
            Maquina.getInstance().modemDisp += p.getModem();
            Maquina.getInstance().cdDriverDisp += p.getCdDriver();
                    
            Despachante.listaSuspensos.add(p);
            TelaPrincipal.setTextoFilaSuspesos(TelaPrincipal.listToString(Despachante.listaSuspensos));
            TelaPrincipal.setTextoFilaFeed(TelaPrincipal.listToString(filaFeed1));
            TelaPrincipal.setTextoFilaFeed2(TelaPrincipal.listToString(filaFeed2));
            TelaPrincipal.setTextoFilaFeed3(TelaPrincipal.listToString(filaFeed3));
            
            TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+p.getNumero()+" inserido na fila de suspensos");
        }
        return blocos;
    }
    
    public static int abreEspacoMemoria(Processo processo){
        //retorna 0 caso seja um sucesso
        //retorna 1 caso nao consiga liberar memoria
        int tamanhoProcesso = processo.getMemoria();
        int qtdBlocos = tamanhoProcesso/GerenciadorMemoria.tamanhoBloco; 
        if(tamanhoProcesso%GerenciadorMemoria.tamanhoBloco != 0){
            qtdBlocos++;//Vai ter um bloco que não vai ficar cheio
        }
        
        int t = qtdBlocos;
        switch(processo.getPrioridade()){
            case 0:
                if(checaMemoria(processo)){
                    t = abreEspacoRotina(t,filaFeed3);
                    if(t>0) 
                        t = abreEspacoRotina(t,filaFeed2);
                    if(t>0) 
                        t = abreEspacoRotina(t,filaFeed1);
                    if(t>0){
                        return -1; //nao conseguiu memoria 
                    }
                }
                else{
                    return -1; //nao ha memoria disponivel mesmo retirando processos
                }
                break;
            case 1:
                if(checaMemoria(processo)){
                    t = abreEspacoRotina(t,filaFeed3);
                    if(t>0) 
                        t = abreEspacoRotina(t,filaFeed2);
                    if(t>0){
                        return -1; //nao conseguiu memoria 
                    }
                }
                else{
                    return -1;
                }
                break;
            case 2:
                if(checaMemoria(processo)){
                    t = abreEspacoRotina(t,filaFeed3);
                    if(t>0){
                        return -1; //nao conseguiu memoria 
                    }
                }
                else{
                    return -1;
                }
                break;
            case 3:
                return -1;
        }
        return 0;
    }
    
    private int proximaCPULivre(){//se retorna -1 nenhuma CPU está livre
        int i=0;
        while(i<Maquina.getInstance().listaCPU.size()){
            CPU atual = Maquina.getInstance().listaCPU.get(i);
            if(atual.ProcessoExecutando == null){
                return i;
            }
            i++;
        }
        return -1;
    }

    
}
