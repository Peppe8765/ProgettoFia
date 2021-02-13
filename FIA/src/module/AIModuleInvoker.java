package module;

import java.util.Scanner;

public class AIModuleInvoker {
    public static void main(String[] args){
        final int row = 3, col = 5;
        Scanner s = new Scanner(System.in);


        int[][] mat = new int[row][col]; //Creazione matrice degli appuntamenti
        //mat = new int[][]{{1, 2, 3, 4, 5}, {5, 4, 3, 2, 1}, {2, 3, 2, 5, 1}}; //Input di prova
        //mat = new int[][]{{1, 2, 3, 4, 5}, {5, 4, 3, 2, 1}, {1, 1, 1, 1, 1}}; //Input di prova quello buono
        mat = new int[][]{{1, 2, 3, 4, 5}, {5, 4, 3, 2, 1}, {5, 4, 5, 4, 4}}; //Input di prova

        //AIModule.populateMatrix(mat, row, col, s); //Inserimento delle difficoltà
        s.close();
        AIModule.setUpperBound(mat,row, col);
        AIModule.setBoundModifier(0.0); //Modifica margine di errore (Non è detto che il risultato non superi il valore di limite + margine aggiuntivo)

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
    }
}
