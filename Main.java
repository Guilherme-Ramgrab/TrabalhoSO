import java.util.Scanner;
import java.util.Random;

public class Main {

    static int MAXIMO_TEMPO_EXECUCAO = 65535;

    static int n_processos = 3;  
    int[] id = new int[n_processos];
    
     
    public static void main(String[] args) {

     int[] tempo_execucao = new int[n_processos];
     int[] tempo_chegada = new int[n_processos];
     int[] prioridade = new int[n_processos];
     int[] tempo_espera = new int[n_processos];
     int[] tempo_restante = new int[n_processos];
     
      
      Scanner teclado = new Scanner (System.in);
      
      
      popular_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
      
      imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
      
      //Escolher algoritmo
      int alg;
      
      while(true) {
        System.out.println("\n >>>>>ESCOLHA O ALGORITIMO <<<<<<<< \n");
        System.out.println("1 - FCFS");
        System.out.println("2 - SJF Preemptivo");
        System.out.println("3 - SJF Não Preemptivo");
        System.out.println("4 - Prioridade Preemptivo");
        System.out.println("5 - Prioridade Não Preemptivo");
        System.out.println("6 - Round_Robin");
        System.out.println("7 - Imprime lista de processos");
        System.out.println("8 - Popular processos novamente");
        System.out.println("9 - Sair");
        
        alg =  teclado.nextInt();
        
        
        if (alg == 1) { //FCFS
            FCFS(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
        }
        else if (alg == 2) { //SJF PREEMPTIVO
            SJF(true, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
        }
        else if (alg == 3) { //SJF NÃO PREEMPTIVO
            SJF(false, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
            
        }
        else if (alg == 4) { //PRIORIDADE PREEMPTIVO
            PRIORIDADE(true, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
        }
        else if (alg == 5) { //PRIORIDADE NÃO PREEMPTIVO
        	PRIORIDADE(false, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
            
        }
        else if (alg == 6) { //Round_Robin
        	Round_Robin(tempo_execucao, tempo_espera, tempo_restante);
            
        }
        else if (alg == 7) { //IMPRIME CONTEÚDO INICIAL DOS PROCESSOS
        	imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
        }
        else if (alg == 8) { //REATRIBUI VALORES INICIAIS
            popular_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
            imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
        }
        else if (alg == 9) {
            break;
            
        }
    }
              
    }

    public static void popular_processos(int[] tempo_execucao, int[] tempo_espera, int[] tempo_restante, int[] tempo_chegada,  int [] prioridade ){
        Random random = new Random();
        Scanner teclado = new Scanner (System.in);
        int aleatorio;

        System.out.print("Será aleatório?:  ");
        aleatorio =  teclado.nextInt();

        for (int i = 0; i < n_processos; i++) {
            //Popular  Processos Aleatorio
            if (aleatorio == 1){
                tempo_execucao[i] = random.nextInt(10)+1;
                tempo_chegada[i] = random.nextInt(10)+1;
                prioridade[i] = random.nextInt(15)+1;
            }
            //Popular Processos Manual
            else {
                System.out.print("Digite o tempo de execução do processo["+i+"]:  ");
                tempo_execucao[i] = teclado.nextInt();
                System.out.print("Digite o tempo de chegada do processo["+i+"]:  ");
                tempo_chegada[i] = teclado.nextInt();
                System.out.print("Digite a prioridade do processo["+i+"]:  ");
                prioridade[i] = teclado.nextInt();
            }
            tempo_restante[i] = tempo_execucao[i];
    
          }
    }

    

    public static void imprime_processos(int[] tempo_execucao, int[] tempo_espera, int[] tempo_restante, int[] tempo_chegada,  int []prioridade){
        //Imprime lista de processos
      System.out.println(" ");
      for (int i = 0; i < n_processos; i++) {
        System.out.println("Processo["+i+"]: tempo_execucao="+ tempo_execucao[i] + " tempo_restante="+tempo_restante[i] + " tempo_chegada=" + tempo_chegada[i] + " prioridade =" +prioridade[i]);
    }
    }

    public static void imprime_stats (int[] espera) {
      int[] tempo_espera = espera.clone();
      int tempos = 0;
      double media;
        //Implementar o calculo e impressão de estatisticas

      System.out.println(" ");
      for (int i = 0; i < n_processos; i++) {
        System.out.println("Processo["+i+"]: tempo_espera ="+tempo_espera[i]);

        tempos += tempo_espera[i];
      }
      media = (double) tempos;
      media = media/n_processos;
      
      System.out.printf("Tempo médio de espera: " + "%.1f \n", media);
        
    }
    
    public static void FCFS(int[] execucao, int[] espera, int[] restante, int[] chegada){
      int[] tempo_execucao = execucao.clone();
      int[] tempo_espera = espera.clone();
      int[] tempo_restante = restante.clone();
      int[] tempo_chegada = chegada.clone();
      int processo = 0;

      System.out.println(" ");
      for(int i=1; i<999999; i++) { 
        System.out.println("tempo["+i+"]: processo["+processo+"] restante = " + (tempo_restante[processo]-1));
        
        if(tempo_restante[processo] == 1){ //VERIFICA O TEMPO RESTANTE DO PROCESSO
          if(processo == n_processos-1){
            break; // AO FINAL DA EXECUÇÃO DO ULTIMO PROCESSO, ENCERRA 
          } else {
            processo++; // TRANSIÇÃO DE PROCESSOS
            tempo_espera[processo] = i;
          }
        }else {
          tempo_restante[processo]--;
        }
      }
        
        imprime_stats(tempo_espera);
    }
    
    public static void SJF(boolean preemptivo, int[] execucao, int[] espera, int[] restante, int[] chegada){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        int[] tempo_chegada = chegada.clone();

        int menorTempo= 999999;
        int processo = -1;
        int cont = 0;

        for (int i=1; i<= 999999; i++) {
            if ((preemptivo) || ((processo == -1))) { // VERIFICA SE É PREEMPTIVO OU SE NÃO, SE HÁ PROCESSO EM EXECUÇÃO
                for (int a=0; a<n_processos; a++) { // VERIFICA SE TEM PROCESSO DISPONIVEL
                    if ((tempo_restante[a] != 0) && (tempo_chegada[a] <= i)) { // VERIFICA SE JA DEU INICIO NA EXECUÇÃO / TEMPO CHEGADA MENOR QUE O ATUAL
                        if (tempo_restante[a] < menorTempo) {
                            menorTempo = tempo_restante[a];
                            processo = a;
                        }
                    }
                }
            }

            //VERIFICA SE HÁ ALGUM PROCESSO PRONTO
            if (processo == -1)
                System.out.println("tempo["+i+"]: nenhum processo está pronto");
            else {
                if (tempo_restante[processo] == tempo_execucao[processo]) {
                  tempo_espera[processo] = i - tempo_chegada[processo]; // REGISTRO DO TEMPO DE ESPERA
                }  
                tempo_restante[processo]--;
                System.out.println("tempo["+i+"]: processo["+processo+"] restante="+(tempo_restante[processo]));

               // VERIFICA SE O PROCESSO JA TERMINOU, SE SIM, RESETA OS CONTROLES PARA RETORNO AO TOPO DO LAÇO E PULA PARA O PROXIMO PROCESSO
                if (tempo_restante[processo] == 0) {
                    processo = -1;
                    menorTempo = 999999;
                    cont++;
                    //VERIFICA SE TODOS OS PROCESSOS FORAM EXECUTADOS, SE SIM, FINALIZA A FUNÇÃO
                    if (cont == n_processos)
                        break;
                }
            }
        }
        
      
        imprime_stats(tempo_espera);
      
    }

    public static void PRIORIDADE(boolean preemptivo, int[] execucao, int[] espera, int[] restante, int[] chegada, int[] prioridade){
    	int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        int[] tempo_chegada = chegada.clone();
        int[] prioridade_temp = prioridade.clone();

        int maiorPrioridade = 0;
        int processo = -1;
        int cont = 0;

        for (int i=1; i<= 999999; i++) {
            if ((preemptivo) || ((processo == -1))) { // VERIFICA SE É PREEMPTIVO OU SE NÃO, SE HÁ PROCESSO EM EXECUÇÃO
                for (int a=0; a<n_processos; a++) { // VERIFICA SE TEM PROCESSO DISPONIVEL
                    if ((tempo_restante[a] != 0) && (tempo_chegada[a] <= i)) { // VERIFICA SE JA DEU INICIO NA EXECUÇÃO / PRIORIDADE MAIOR QUE O ATUAL
                        if (prioridade_temp[a] > maiorPrioridade) {
                            maiorPrioridade = prioridade_temp[a];
                            processo = a;
                        }
                    }
                }
            }

            //VERIFICA SE HÁ ALGUM PROCESSO PRONTO
            if (processo == -1)
                System.out.println("tempo["+i+"]: nenhum processo está pronto");
            else {
                if (tempo_restante[processo] == tempo_execucao[processo]) {
                  tempo_espera[processo] = i - tempo_chegada[processo]; // REGISTRO DO TEMPO DE ESPERA
                }  
                tempo_restante[processo]--;
                System.out.println("tempo["+i+"]: processo["+processo+"] restante="+(tempo_restante[processo]));

               // VERIFICA SE O PROCESSO JA TERMINOU, SE SIM, RESETA OS CONTROLES PARA RETORNO AO TOPO DO LAÇO E PULA PARA O PROXIMO PROCESSO
                if (tempo_restante[processo] == 0) {
                    processo = -1;
                    maiorPrioridade = 0;
                    cont++;
                    //VERIFICA SE TODOS OS PROCESSOS FORAM EXECUTADOS, SE SIM, FINALIZA A FUNÇÃO
                    if (cont == n_processos)
                        break;
                }
            }
        }

          imprime_stats(tempo_espera);
      
    }
    
    public static void Round_Robin(int[] execucao, int[] espera, int[] restante){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();

        int quantum;
        int processosTerminados = 0;
        int processo = 0;
        int menorTempo = 999999;
        Scanner teclado = new Scanner (System.in);
      

        System.out.println("Escolha o tempo do Quantum: ");
        int quatum =  teclado.nextInt();
      
        for (int i=0; i < 1; i++) {
          System.out.println("tempo["+i+"]: processo["+processo+"] restante="+(tempo_restante[processo]));
          tempo_restante[processo]--;
          if(tempo_execucao[processo] == 0) {
            processo++;
          }
        }
        
        imprime_stats(tempo_espera);
    }
}