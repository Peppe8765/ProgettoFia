package module;

import java.util.Scanner;

public class AIModuleInvoker {
    public static void main(String[] args){
        final int row = 3, col = 5;
        Scanner s = new Scanner(System.in);


        int[][] mat = new int[row][col]; //Creazione matrice degli appuntamenti
        
        //mat = new int[][]{{1, 2, 3, 4, 5}, {5, 4, 3, 2, 1}, {2, 3, 2, 5, 1}}; //Input di prova fatto
        
        //mat = new int[][]{{1, 2, 3, 4, 5}, {5, 4, 3, 2, 1}, {1, 1, 1, 1, 1}}; //Input di prova quello buono perfetto
        
        //mat = new int[][]{{1, 2, 3, 4, 5}, {5, 4, 3, 2, 1}, {5, 4, 5, 4, 4}}; //Input di prova ottimo fatto
        
        //mat = new int[][]{  {5, 5, 4, 4, 5}, {5, 4, 3, 2, 1}, {3, 3, 3, 2, 1}}; //Input di prova che si rompe senza mettere il margine(1) fatto
       
        //mat = new int[][]{{2, 2, 3, 1, 4}, {5, 4, 3, 4, 1}, {1, 4, 2, 4, 3}}; //Input di prova da vedere se aggiungere
        
        //mat = new int[][]{  {0, 0, 4, 5, 0}, {0, 4, 3, 2, 1}, {0, 3, 3, 0, 1}}; //da usare nella criticità dello 0
        
        mat = new int[][]{{1, 2, 3, 4, 5}, {5, 4, 3, 0, 1}, {5, 4, 5, 4, 0}};
        
        
        //AIModule.populateMatrix(mat, row, col, s); //Inserimento delle difficoltà
        s.close();
        AIModule.setUpperBound(mat,row, col);
        AIModule.setBoundModifier(0.5); //Modifica margine di errore (Non è detto che il risultato non superi il valore di limite + margine aggiuntivo)

        AIModule.printUpperBound();
        AIModule.printBoundModifier();

        int[][] app = new int[row][col]; //Creiamo una nuova matrice delle stesse dimensioni di quella data in input
        AIModule.initializeMatrix(app, row, col); //Inizializzata a 0

        AIModule.matrixToList(mat, row, col); //Genero la lista degli appuntamenti
        AIModule.printList();

        if(!AIModule.schedule(mat, row, col, app)) { //Scheduling degli appuntamenti
            System.out.println("Errore di schedulazione");
            return;
        }
        AIModule.printCalendario(app, row, col, AIModule.getMediaArray(app, row, col)); //Stampa a video dei risultati
        
        AIModule.printNumIns();//stampa il numero di tentativi per l'inserimento dell'appuntamento
    }
}
