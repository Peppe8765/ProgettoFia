package module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class AIModule {
    private static double upperBound;
    private static double boundModifier;
    private static ArrayList<Integer> list;

    public static boolean isSafe(int[][] mat, int row, int col, int targetRow, int targetCol, int element) {
        int[][] tempMatrix = new int[row][col];
        duplicateMatrix(mat, row, col, tempMatrix);
        tempMatrix[targetRow][targetCol] = element;

        //Se aggiungendo l'elemento alla matrice temporanea violo il vincolo, allora non completo l'inserimento
        if(getDifficoltàGiornaliera(tempMatrix, row, targetCol) > (upperBound + boundModifier)) return false;

        return true;
    }

    public static boolean schedule(int[][] source, int row, int col, int[][] dest) {
        int notZeroValuesSource = getNotZeroValues(source, row, col);
        int notZeroValuesDestination = getNotZeroValues(dest, row, col);

        if(notZeroValuesSource == notZeroValuesDestination) return true; //Non ci sono altri appuntamenti da schedulare

        int i= -1, j= -1;
        int[] indexes= getFirstZeroValueIndex(dest, row, col, i, j); //Recupero il primo indice vuoto e cerco di riempirlo
        i = indexes[0];
        j= indexes[1];

        if(i == -1 || j == -1) return true; //Nessun indice vuoto, non ho nulla da inserire

        Iterator<Integer> iter = list.iterator(); //Per evitare la ConcurrentModificationException

        try {
            while (iter.hasNext()) {
                int elem = iter.next();
                if (elem != 0 && isSafe(dest, row, col, i, j, elem)) {
                    dest[i][j] = elem;
                    iter.remove();
                    schedule(source, row, col, dest);
                }
            }
        }
        catch(Exception e){ //Per via della ricorsione, la lista viene modificata e l'iterator lancia una ConcurrentModificationException finale per via del suo stato invalido
            dest[row-1][col-1] = list.get(0); //Aggiungiamo l'ultimo elemento
            return true;
        }
        if(list.isEmpty()) return true;
        return false;
    }

    public static void matrixToList(int[][] mat, int row, int col){
        ArrayList<Integer> al= new ArrayList<Integer>();
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                al.add(mat[i][j]);
            }
        }
        list = al;
    }

    public static int[] getFirstZeroValueIndex(int[][] mat, int row, int col, int indexRow, int indexCol){
        for (indexCol = 0; indexCol < col; indexCol++) {
            for (indexRow = 0; indexRow < row; indexRow++) {
                if(mat[indexRow][indexCol] == 0){
                    int[] arr = new int[]{indexRow, indexCol};
                    return arr;
                }
            }
        }
        int[] arr = new int[]{-1, -1};
        return arr;
    }

    public static int getNotZeroValues(int[][] mat, int row, int col){
        int counter = 0;

        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                if(mat[i][j] != 0) counter++;
            }
        }
        return counter;
    }

    public static double getDifficoltàGiornaliera(int[][] mat, int row, int day){
        int sum =  0;

        for (int i = 0; i < row; i++) {
            sum += mat[i][day];
        }
        return sum/row;
    }

    public static double getDifficoltàMedia(int mat[][], int row, int col){
        double somma = 0;
        int countElement= 0;
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                somma += mat[i][j];
                countElement++;
            }
        }
        return somma/countElement;
    }

    public static double[] getMediaArray(int mat[][], int row, int col) {
        double media[] = new double[5] ;
        double somma = 0;
        double num = 0.00;
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                somma += mat[i][j];
            }
            num = (somma/3.0) *100;
            num=Math.floor(num);
            num = num/100;
            media[j] = num;
            somma = 0.00;
        }

        return media;
    }

    public static void setUpperBound(int[][] mat, int row, int col){
        upperBound= getDifficoltàMedia(mat, row, col);
    }

    public static void setBoundModifier(double modifier){
        boundModifier = modifier;
    }

    public static void populateMatrix(int[][] mat, int row, int col, Scanner s){ //Popolamento della matrice da input
        int counter = 1;
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                System.out.print("Inserire difficoltà appuntamento n." + counter + " (0 = vuoto): ");
                mat[i][j] = s.nextInt();
                counter++;
            }
        }
    }

    public static void initializeMatrix(int[][] mat, int row, int col){ //Inizializzazione della matrice a 0
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                mat[i][j] = 0;
            }
        }
    }

    public static void duplicateMatrix(int[][] source, int row, int col, int[][] dest){
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                dest[i][j] = source[i][j];
            }
        }
    }

    public static void printUpperBound(){
        System.out.println("Media delle difficoltà: " + upperBound);
    }

    public static void printBoundModifier(){
        System.out.println("Margine di errore concesso: " + boundModifier);
    }

    public static void printList(){
        System.out.print("Lista appuntamenti: ");
        for(Integer elem : list) System.out.print(elem + " ");
        System.out.print("\n");
    }

    public static void printCalendario(int mat[][], int row, int col,double media[]) {
        System.out.println("     lun       mar       mer       gio       ven");

        for (int i = 0; i < row; i++) {
            String ora = "";

            if(i == 0) {
                ora = "9:00 ";
            }else if(i == 1) {
                ora = "10:00";
            }else if(i == 2) {
                ora = "11:00";

            }

            System.out.print(ora+ " ");
            for (int j = 0; j < col; j++) {

                System.out.print(mat[i][j]+ "         ");
            }
            System.out.println("");
        }

        System.out.println("media " +media[0] +"      " + media[1]+"       " + media[2]+"      " + media[3]+"      " + media[4]);
        System.out.println("\nMedia difficoltà appuntamenti: " + getDifficoltàMedia(mat, row, col));

    }
}
