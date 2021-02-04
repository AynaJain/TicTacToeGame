
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class GamePlay {


    Consumer<ArrayList<String>> callback;
    public GridPane board;
//    boolean xTurn = true;

   public ArrayList<String> gameBoard = new ArrayList<>();
   public ArrayList<String> xMoves = new ArrayList<>(10);
   public ArrayList<String> oMoves = new ArrayList<>(10);
    public String difficultyX;
    public String difficultyO;
    public String numGames;
    public String winner;
    public int move;
    public boolean xTurn = true;
    public int totalXWon ;
    public int totalOWon;
    int gameNumber = 0 ;
//    ObservableList<Node> childrens ;

    ExecutorService ex = Executors.newFixedThreadPool(10);
    public GamePlay(String difficultyX, String difficultyO, String numGames)
    {
        this.difficultyX = difficultyX;
        this.difficultyO = difficultyO;
        this.numGames = numGames;

    }

    // check if there is a winner or a tie
    public boolean checkWinner()
    {
        if ((gameBoard.get(0).equals("X") && gameBoard.get(1).equals("X") && gameBoard.get(2).equals("X")) ||
                (gameBoard.get(3).equals("X") && gameBoard.get(4).equals("X") && gameBoard.get(5).equals("X")) ||
                (gameBoard.get(6).equals("X") && gameBoard.get(7).equals("X") && gameBoard.get(8).equals("X")) ||
                (gameBoard.get(0).equals("X") && gameBoard.get(3).equals("X") && gameBoard.get(6).equals("X")) ||
                (gameBoard.get(1).equals("X") && gameBoard.get(4).equals("X") && gameBoard.get(7).equals("X")) ||
                (gameBoard.get(2).equals("X") && gameBoard.get(5).equals("X") && gameBoard.get(8).equals("X")) ||
                (gameBoard.get(0).equals("X") && gameBoard.get(4).equals("X") && gameBoard.get(8).equals("X")) ||
                (gameBoard.get(2).equals("X") && gameBoard.get(4).equals("X") && gameBoard.get(6).equals("X")))
        {
            winner = "X";
            return true;
        }
        if ((gameBoard.get(0).equals("O") && gameBoard.get(1).equals("O") && gameBoard.get(2).equals("O")) ||
                (gameBoard.get(3).equals("O") && gameBoard.get(4).equals("O") && gameBoard.get(5).equals("O")) ||
                (gameBoard.get(6).equals("O") && gameBoard.get(7).equals("O") && gameBoard.get(8).equals("O")) ||
                (gameBoard.get(0).equals("O") && gameBoard.get(3).equals("O") && gameBoard.get(6).equals("O")) ||
                (gameBoard.get(1).equals("O") && gameBoard.get(4).equals("O") && gameBoard.get(7).equals("O")) ||
                (gameBoard.get(2).equals("O") && gameBoard.get(5).equals("O") && gameBoard.get(8).equals("O")) ||
                (gameBoard.get(0).equals("O") && gameBoard.get(4).equals("O") && gameBoard.get(8).equals("O")) ||
                (gameBoard.get(2).equals("O") && gameBoard.get(4).equals("O") && gameBoard.get(6).equals("O")))
        {
            winner = "O";
            return true;
        }
            winner ="Tie";

        return false;
    }
    public void getEachMove() throws ExecutionException, InterruptedException {


        int i = 0 ;

        while(i < gameBoard.size())
        {
           if(checkWinner()) // if there is a winner or a tie .. stop the game
           {
               break;
           }
            int index = playGame();

//            Text move = (Text) childrens.get(index);
            if(xTurn) {
//                    move.setText("X");

                    xMoves.set(index,"X");
                    gameBoard.set(index, "X");
            }
            else {
//                    move.setText("O");
                    oMoves.set(index,"O");
                    gameBoard.set(index, "O");
            }

            xTurn =! xTurn;
            i++;
        }
//        gameNumber++;
//        if(gameNumber > Integer.parseInt(numGames))
//        {
//            ex.shutdown();
//        }
//

    }

    // X is expert
    // getMinMax priority is 10,0,-10
    public Integer expertX() throws ExecutionException, InterruptedException {

        ArrayList<Integer> moves = new ArrayList<>();
        Random rand = new Random();
            Future<ArrayList<Nodes>> future = ex.submit(new MyCall(gameBoard));

            ArrayList<Nodes> cell = future.get();

            for (Nodes nodes : cell) {
                if (  nodes.getMinMax() == 10 ) {
                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }
            }

            if(moves.isEmpty())
            {
                for (Nodes nodes : cell) {
                    if (  nodes.getMinMax() == 0 ) {
                        move = nodes.getMovedTo() - 1;
                        moves.add(move);
                    }
                }
            }
        if(moves.isEmpty())
        {
            for (Nodes nodes : cell) {
                if (  nodes.getMinMax() == -10 ) {
                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }
            }
        }
            move= moves.get(rand.nextInt(moves.size()));
        return move;

    }

    public Integer expertO() throws ExecutionException, InterruptedException {

        ArrayList<Integer> moves = new ArrayList<>();
        Random rand2 = new Random();

        flipBoard();

        Future<ArrayList<Nodes>> future2 = ex.submit(new MyCall(gameBoard));
        ArrayList<Nodes> cell2 = future2.get();

        for (Nodes nodes : cell2) {
            if (  nodes.getMinMax() == 10) {
                move = nodes.getMovedTo() - 1;
                moves.add(move);
            }
        }

        if(moves.isEmpty())
        {
            for (Nodes nodes : cell2) {
                if (  nodes.getMinMax() == 0) {
                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }
            }
        }
        if(moves.isEmpty())
        {
            for (Nodes nodes : cell2) {
                if (  nodes.getMinMax() == -10 ) {
                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }
            }
        }
        move= moves.get(rand2.nextInt(moves.size()));
        resetBack();

        return move;

    }

    public Integer beginnerO() throws ExecutionException, InterruptedException {
        ArrayList<Integer> moves = new ArrayList<>();
        Random rand3 = new Random();
        flipBoard();
        Future<ArrayList<Nodes>> future2 = ex.submit(new MyCall(gameBoard));
        ArrayList<Nodes> cell2 = future2.get();

        for (Nodes nodes : cell2) {
            if (nodes.getMinMax() == -10) {
                move = nodes.getMovedTo() - 1;
                moves.add(move);
            }

        }
        if(moves.isEmpty())
        {
            for (Nodes nodes : cell2) {
                if (nodes.getMinMax() == 0) {
                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }

            }
        }

        if(moves.isEmpty())
        {
            for (Nodes nodes : cell2) {
                if (nodes.getMinMax() == 10) {
                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }

            }
        }
//        move = cell2.get(rand3.nextInt(cell2.size())).getMovedTo()-1;
        move= moves.get(rand3.nextInt(moves.size()));
        resetBack();

        return move;

    }
    public Integer beginnerX() throws ExecutionException, InterruptedException {
        ArrayList<Integer> moves = new ArrayList<>();
        Random rand4 = new Random();

        Future<ArrayList<Nodes>> future = ex.submit(new MyCall(gameBoard));
        ArrayList<Nodes> cell = future.get();
        for (Nodes nodes : cell) {
            if (nodes.getMinMax() == -10) {

                move = nodes.getMovedTo() - 1;
                moves.add(move);
            }
        }
        if(moves.isEmpty())
        {
            for (Nodes nodes : cell) {
                if (nodes.getMinMax() == 0) {

                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }
            }
        }
        if(moves.isEmpty())
        {
            for (Nodes nodes : cell) {
                if (nodes.getMinMax() == 10) {

                    move = nodes.getMovedTo() - 1;
                    moves.add(move);
                }
            }
        }
        move= moves.get(rand4.nextInt(moves.size()));

        return move;

    }

    public Integer noviceX() throws ExecutionException, InterruptedException {

        Future<ArrayList<Nodes>> future = ex.submit(new MyCall(gameBoard));

        ArrayList<Nodes> cell = future.get();
        Random rand5 = new Random();

        move = cell.get(rand5.nextInt(cell.size())).getMovedTo()-1;
        return move;
    }
    public Integer noviceO() throws ExecutionException, InterruptedException {
        flipBoard();
        Future<ArrayList<Nodes>> future2 = ex.submit(new MyCall(gameBoard));

        ArrayList<Nodes> cell2 = future2.get();
        Random rand5 = new Random();


         move = cell2.get(rand5.nextInt(cell2.size())).getMovedTo()-1;
        resetBack();
         return move;
    }

// different combinations of levels for two players
    public Integer playGame() throws ExecutionException, InterruptedException {
        int index = 0;
        if(difficultyX.equals("Expert") && difficultyO.equals("Expert") ) {

            if(xTurn) {
                index = expertX();
            }
            else {
                index = expertO();
            }

        }
        else if (difficultyX.equals("Expert") && difficultyO.equals("Beginner"))
        {
            if(xTurn) {
                index = expertX();
            }
            else {
                index = beginnerO();
            }
        }
        else if(difficultyX.equals("Expert") && difficultyO.equals("Novice"))
        {
            if(xTurn){
                index = expertX();
            }
            else{
                index = noviceO();
            }
        }
        else if(difficultyX.equals("Beginner") && difficultyO.equals("Expert"))
        {
            if(xTurn){
                index = beginnerX();
            }
            else{
                index = expertO();
            }
        }
        else if(difficultyX.equals("Beginner") && difficultyO.equals("Novice"))
        {
            if(xTurn){
                index = beginnerX();
            }
            else{
                index = noviceO();
            }
        }
        else if(difficultyX.equals("Beginner") && difficultyO.equals("Beginner"))
        {
            if(xTurn){
                index = beginnerX();
            }
            else{
                index = beginnerO();
            }
        }
        else if(difficultyX.equals("Novice") && difficultyO.equals("Expert"))
        {
            if(xTurn){
                index = noviceX();
            }
            else{
                index = expertO();
            }
        }
        else if(difficultyX.equals("Novice") && difficultyO.equals("Beginner"))
        {
            if(xTurn){
                index = noviceX();
            }
            else{
                index = beginnerO();
            }
        }
        else if(difficultyX.equals("Novice") && difficultyO.equals("Novice"))
        {
            if(xTurn){
                index = noviceX();
            }
            else{
                index = noviceO();
            }
        }
        return index;
    }

    // reset the board back to original
    public void resetBack() {

        for (int i = 0; i < gameBoard.size(); ++i) {
            if (gameBoard.get(i).equals("O"))
                gameBoard.set(i, "X");
            else if (gameBoard.get(i).equals("X"))
                gameBoard.set(i, "O");
        }
    }
    // flip the board.. change X's to O's and vice versa
    public void flipBoard() {
        for (int i = 0; i < gameBoard.size(); ++i) {
            if (gameBoard.get(i).equals("X")) {
                gameBoard.set(i, "O");
            } else if (gameBoard.get(i).equals("O")) {
                gameBoard.set(i, "X");
            }
        }
    }
}
