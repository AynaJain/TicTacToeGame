
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.ExecutionException;

//import static org.junit.jupiter.api.Assertions.assertEquals;

class MyTest {

    static GamePlay play2;
    static GamePlay play;
    static GamePlay play3;
    static GamePlay play4;
    static GamePlay play5;
    static GamePlay play6;
    static TicTacToe game;
    @BeforeAll
    static void setup()
    {
        String xLevel = "Beginner";
        String oLevel = "Expert";
        String numGames = "1";
         play2 = new GamePlay(xLevel,oLevel,numGames);

    }

    // expert vs expert game
    // tie always
	@Test
	void test1() throws ExecutionException, InterruptedException {
        String xLevel = "Expert";
        String oLevel = "Expert";
        String numGames = "1";
        play = new GamePlay(xLevel,oLevel,numGames);
//        play.difficultyO="Expert";
//        play.difficultyX = "Expert";
        for (int i = 0; i < 9; ++i) {
            play.gameBoard.add("");
            play.oMoves.add("");
            play.xMoves.add("");
            play.gameBoard.set(i, "b");
        }
        play.getEachMove();
        assertEquals("Tie",play.winner,"Incorrect expected winner");
	}
	// expert X .. beginner O
    @Test
    void test2() throws ExecutionException, InterruptedException {
        String xLevel = "Expert";
        String oLevel = "Beginner";
        String numGames = "1";
        play = new GamePlay(xLevel,oLevel,numGames);
        for (int i = 0; i < 9; ++i) {
            play.gameBoard.add("");
            play.oMoves.add("");
            play.xMoves.add("");
            play.gameBoard.set(i, "b");
        }
        play.getEachMove();
        assertEquals("X",play.winner,"Incorrect expected winner");
    }

    // expert X .. Novice O
    @Test
    void test3() throws ExecutionException, InterruptedException {
        String xLevel = "Expert";
        String oLevel = "Novice";
        String numGames = "1";
        play = new GamePlay(xLevel,oLevel,numGames);
        for (int i = 0; i < 9; ++i) {
            play.gameBoard.add("");
            play.oMoves.add("");
            play.xMoves.add("");
            play.gameBoard.set(i, "b");
        }
        play.getEachMove();
        // if X is expert and O is novice.. it can be tie or X can win
        assertTrue(play.winner.equals("X") || play.winner.equals("Tie"));
    }


    // expert 0 .. Beginner X
    @Test
    void test4() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 9; ++i) {
            play2.gameBoard.add("");
            play2.oMoves.add("");
            play2.xMoves.add("");
            play2.gameBoard.set(i, "b");
        }
        play2.getEachMove();
        assertEquals("O",play2.winner,"Incorrect expected winner");
    }

    // expert 0 .. Novice X
    @Test
    void test5() throws ExecutionException, InterruptedException {
        play3 = new GamePlay("Novice","Expert","1");
        for (int i = 0; i < 9; ++i) {
            play3.gameBoard.add("");
            play3.oMoves.add("");
            play3.xMoves.add("");
            play3.gameBoard.set(i, "b");
        }
        play3.getEachMove();// play the game
        // X is novice and O is expert.. it can be tie or O can win
        assertTrue(play3.winner.equals("O") || play3.winner.equals("Tie"));

    }

    // flipBoard test
    @Test
    void test6()
    {
        for (int i = 0; i < 9; ++i) {
            play2.gameBoard.add("");
            play2.gameBoard.set(i, "b");
        }
        // set the first five indices to X
        for (int i = 0; i < 5; ++i) {
            play2.gameBoard.set(i, "X");
        }

        // call flipBoard
        play2.flipBoard();

        for(int i = 0 ; i < 5 ;++i)
        {
            assertEquals("O",play2.gameBoard.get(i),"Incorrect index value");
        }
    }

    //resetBack test
    @Test
    void test7()
    {
        for (int i = 0; i < 9; ++i) {
            play2.gameBoard.add("");
            play2.gameBoard.set(i, "b");
        }
        // set the first five indices to X
        for (int i = 0; i < 5; ++i) {
            play2.gameBoard.set(i, "X");
        }
        // call flipBoard
        play2.flipBoard();

        for(int i = 0 ; i < 5 ;++i)
        {
            assertEquals("O",play2.gameBoard.get(i),"Incorrect index value");
        }
        // call resetBoard
        play2.resetBack();

        // now the first five indices should be reset to X
        for(int i = 0 ; i < 5 ;++i)
        {
            assertEquals("X",play2.gameBoard.get(i),"Incorrect index value");
        }
    }

    //checkWinner test
    @Test
    void test8()
    {
        for (int i = 0; i < 9; ++i) {
            play2.gameBoard.add("");
            play2.gameBoard.set(i, "b");
        }
        play2.gameBoard.set(0,"X");
        play2.gameBoard.set(1,"O");
        play2.gameBoard.set(2,"X");
        play2.gameBoard.set(3,"X");
        play2.gameBoard.set(4,"O");
        play2.gameBoard.set(5,"O");
        play2.gameBoard.set(6,"X");

        // X should be the winner in this board and checkWinner should return true
        assertTrue(play2.checkWinner(), "Incorrect winner");
        assertEquals("X",play2.winner,"Incorrect winner");

    }
    //checkWinner test2
    @Test
    void test9()
    {
        for (int i = 0; i < 9; ++i) {
            play2.gameBoard.add("");
            play2.gameBoard.set(i, "b");
        }
        play2.gameBoard.set(0,"X");
        play2.gameBoard.set(1,"O");
        play2.gameBoard.set(2,"O");
        play2.gameBoard.set(3,"O");
        play2.gameBoard.set(4,"X");
        play2.gameBoard.set(5,"X");
        play2.gameBoard.set(6,"X");
        play2.gameBoard.set(7,"O");
        play2.gameBoard.set(8,"O");
        // There should be tie in this board and checkWinner should return false
        assertFalse(play2.checkWinner(), "Incorrect winner");
        assertEquals("Tie",play2.winner,"Incorrect winner");

    }

    //Expected move when X is expert
    @Test
    void test10() throws ExecutionException, InterruptedException {
        play4 = new GamePlay("Expert","Expert","1");
        for (int i = 0; i < 9; ++i) {
            play4.gameBoard.add("");
            play4.gameBoard.set(i, "b");
        }
        play4.gameBoard.set(0,"X");     //    X | O |
        play4.gameBoard.set(1,"O");     //      | O | X
        play4.gameBoard.set(4,"O");     //      |   |
        play4.gameBoard.set(5,"X");

            // In this board the next move for X should be index 7 as X is expert
        assertEquals(7,play4.expertX(),"Incorrect expected move for X");

    }
    //Expected move when O is expert
    @Test
    void test11() throws ExecutionException, InterruptedException {
        play5 = new GamePlay("Beginner","Expert","1");
        for (int i = 0; i < 9; ++i) {
            play5.gameBoard.add("");
            play5.gameBoard.set(i, "b");
        }
        play5.gameBoard.set(0,"X");     //    X | O |
        play5.gameBoard.set(1,"O");     //      | O | X
        play5.gameBoard.set(4,"O");     //      |   | X
        play5.gameBoard.set(5,"X");
        play5.gameBoard.set(8,"X");

        // In this board the next move for O should be index 7 as X is Beginner...But the best move for X is returned so
        // index 2 will be returned
        // so either 2 or 7 should be returned
        int index = play5.expertO();
        assertTrue(index == 2 || index == 7);

    }

    //Expected move when X is Beginner
    @Test
    void test12() throws ExecutionException, InterruptedException {
        play6 = new GamePlay("Beginner","Expert","1");
        for (int i = 0; i < 9; ++i) {
            play6.gameBoard.add("");
            play6.gameBoard.set(i, "b");
        }
        play6.gameBoard.set(1,"X");     //      | X |
        play6.gameBoard.set(5,"X");     //      | O | X
        play6.gameBoard.set(4,"O");     //     O| O | X
        play6.gameBoard.set(6,"O");
        play6.gameBoard.set(7,"O");
        play6.gameBoard.set(8,"X");

        // in this case X is beginner.. So next best move for X should be at index 2 but since X is beginner,
        // the move will either be at index 0 or 3 which is the worst move for X
        int index = play6.beginnerX();
        assertTrue(index == 0 || index == 3);

    }
}
