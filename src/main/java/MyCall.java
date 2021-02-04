import java.util.ArrayList;
import java.util.concurrent.Callable;

class MyCall implements Callable<ArrayList<Nodes>> {
    ArrayList<String> board2;
    Character move;
    ArrayList<Integer> test;
    ArrayList<Nodes> movesList;


    MyCall( ArrayList<String> board2) {
        this.board2 = board2;
    }

    @Override
    public ArrayList<Nodes> call() throws Exception {

        String[] array = new String[board2.size()];
        // convert arraylist to string array in order to pass it to AI_MinMax
        for(int j =0;j<board2.size();j++){
            array[j] = board2.get(j);
        }
        AI_MinMax possibleMoves = new AI_MinMax(array);

        movesList =  possibleMoves.getBestMoves();

        return movesList;
    }
}
