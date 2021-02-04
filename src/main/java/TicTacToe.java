import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;


// Author : Tirth Patel
// Tic Tac game played between two computers using java executor class.

public class TicTacToe extends Application implements Initializable {

	public ComboBox difficultyX;
	public ComboBox numGames;
	public ComboBox difficultyO;
	public GridPane board;
	public DialogPane Result;
	public Button exit;
	public Button resultButton;
	public Button Rules;
	public Label label;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TicTacToe.fxml"));
		primaryStage.setTitle("Welcome to TIC TAC TOE!");

		Scene scene = new Scene(root, 600,600);
		primaryStage.setScene(scene);
		primaryStage.show();

		// change the scene to scene to game play scene
		changeScene(primaryStage);
	}

	private void changeScene(Stage primaryStage) {
		PauseTransition delay = new PauseTransition(Duration.seconds(5));

		delay.setOnFinished( event -> {
			try {
				// close the primary stage
				primaryStage.close();
				Stage stage = new Stage();
				gameScene(stage);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		delay.play();
	}

	public void gameScene(Stage stage) throws IOException {

		Parent gameScene = FXMLLoader.load(getClass().getResource("GamePlay.fxml"));
		Scene newScene = new Scene(gameScene, 600,600);
		stage.setTitle("Tic Tac Toe Game Scene");

		stage.setScene(newScene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// on clicking start
	public void createBoard(ActionEvent actionEvent) throws ExecutionException, InterruptedException, IOException {

		ObservableList<Node> children;

		{
			assert board != null;
			children = board.getChildren();
		}
		String xLevel = difficultyX.getValue().toString();
		String oLevel = difficultyO.getValue().toString();
		String numGamesValue = numGames.getValue().toString();

		for(int i = 0 ; i < children.size() -1 ; ++i)
		{
			Text move = (Text) children.get(i);
			if(!move.getText().equals(""))
			{
				move.setText("");
			}
		}

		// start new game
		GamePlay play = new GamePlay(xLevel, oLevel, numGamesValue);
		play.gameBoard.clear();
		play.xMoves.clear();
		play.oMoves.clear();

			// initially set the board to all "b"
			for (int i = 0; i < children.size() - 1; ++i) {

				Text move = (Text) children.get(i);
				board.setHalignment(move, HPos.CENTER);
				move.setFill(Color.YELLOW);
				move.setFont(Font.font("Verdana", 25));
				play.gameBoard.add(move.getText());
				play.oMoves.add("");
				play.xMoves.add("");
				play.gameBoard.set(i, "b");
			}

			play.getEachMove();
			for (int i = 0; i < play.xMoves.size(); ++i) {
				System.out.print((i));
				System.out.print(play.xMoves.get(i));
				System.out.print("\n");
			}
			System.out.println("\n");
			for (int i = 0; i < play.oMoves.size(); ++i) {
				System.out.print((i));
				System.out.print(play.oMoves.get(i));
				System.out.print("\n");
			}
			System.out.println("\n");

			for (int i = 0; i < play.gameBoard.size(); ++i) {
				System.out.print((i));
				System.out.print(play.gameBoard.get(i));
				System.out.print("\n");
			}
		ArrayList<Integer> xMovesIdx = new ArrayList<Integer>(9);
		ArrayList<Integer> oMovesIdx = new ArrayList<Integer>(9);
		for(int i = 0 ; i < play.xMoves.size() ; ++i)
		{
			if(play.xMoves.get(i).equals("X"))
			{
				xMovesIdx.add(i);
			}
		}
		for(int i = 0 ; i < play.oMoves.size() ; ++i)
		{
			if(play.oMoves.get(i).equals("O"))
			{
				oMovesIdx.add(i);
			}
		}
		for (int i = 0; i < xMovesIdx.size(); ++i) {
			System.out.print(xMovesIdx.get(i));
			System.out.print("\n");
		}
		System.out.println("\n");
		for (int i = 0; i < oMovesIdx.size(); ++i) {
			System.out.print(oMovesIdx.get(i));
			System.out.print("\n");
		}
		System.out.println("\n");

//
//		PauseTransition p2 = new PauseTransition(Duration.seconds(4));
//		p2.setOnFinished(event -> {
//			if(xMovesIdx.get(1) != null) {
//				Text move3 = (Text) children.get(xMovesIdx.get(1));
//				move3.setText("X");
//			}
//		});

			final int[] i = {0};
			final int[] x = {0};

	AtomicBoolean flag = new AtomicBoolean(false);
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					i[0] = i[0] + 1;
					if (i[0] == 9) {
						t.cancel();
					}

					Platform.runLater(() -> {

						if (play.xMoves.get(x[0]).equals("X")) {
							Text move = (Text) children.get(x[0]);
							move.setText(play.xMoves.get(x[0]));

						}
						else if (play.oMoves.get(x[0]).equals("O")) {
							Text move = (Text) children.get(x[0]);
							move.setText("O");

						}
//						else
//							x[0]++;
						x[0]++;
					});
				}
			}, 0, 1250);

			System.out.print(play.winner);
		PauseTransition delay2 = new PauseTransition(Duration.seconds(12));
		delay2.setOnFinished( event -> {
			try {

				Stage stage = new Stage();
				 changeToResultScene(stage,play);

			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		delay2.play();

		PauseTransition delay = new PauseTransition(Duration.seconds(15));
		delay.setOnFinished(event->{
			if(Integer.parseInt(numGamesValue) > 1)
			{
				try {
					playNextGame(play,Integer.parseInt(numGamesValue),2);
				} catch (ExecutionException | InterruptedException e) {
					e.printStackTrace();
				}
			}

		});
	delay.play();
		}

	public void playNextGame(GamePlay play, int numGame,int gameNumber) throws ExecutionException, InterruptedException {

		while(gameNumber<= numGame)
		{

			ObservableList<Node> children;

			{
				assert board != null;
				children = board.getChildren();
			}

			for(int i = 0 ; i < children.size() -1 ; ++i)
			{
				Text move = (Text) children.get(i);
				if(!move.getText().equals(""))
				{
					move.setText("");
				}
			}

			play.gameBoard.clear();
			play.xMoves.clear();
			play.oMoves.clear();

			// initially set the board to all "b"
			for (int i = 0; i < children.size() - 1; ++i) {

				Text move = (Text) children.get(i);
				board.setHalignment(move, HPos.CENTER);
//				move.setText("");
				move.setFill(Color.YELLOW);
				move.setFont(Font.font("Verdana", 25));
//				board.setStyle("-fx-grid-lines-visible: false;");
				play.gameBoard.add(move.getText());
				play.oMoves.add("");
				play.xMoves.add("");
				play.gameBoard.set(i, "b");
			}

			play.getEachMove();
			for (int i = 0; i < play.xMoves.size(); ++i) {
				System.out.print((i));
				System.out.print(play.xMoves.get(i));
				System.out.print("\n");
			}
			System.out.println("\n");
			for (int i = 0; i < play.oMoves.size(); ++i) {
				System.out.print((i));
				System.out.print(play.oMoves.get(i));
				System.out.print("\n");
			}
			System.out.println("\n");

			for (int i = 0; i < play.gameBoard.size(); ++i) {
				System.out.print((i));
				System.out.print(play.gameBoard.get(i));
				System.out.print("\n");
			}
			ArrayList<Integer> xMovesIdx = new ArrayList<Integer>(9);
			ArrayList<Integer> oMovesIdx = new ArrayList<Integer>(9);
			for(int i = 0 ; i < play.xMoves.size() ; ++i)
			{
				if(play.xMoves.get(i).equals("X"))
				{
					xMovesIdx.add(i);
				}
			}
			for(int i = 0 ; i < play.oMoves.size() ; ++i)
			{
				if(play.oMoves.get(i).equals("O"))
				{
					oMovesIdx.add(i);
				}
			}
			for (int i = 0; i < xMovesIdx.size(); ++i) {
//						System.out.print((i));
				System.out.print(xMovesIdx.get(i));
				System.out.print("\n");
			}
			System.out.println("\n");
			for (int i = 0; i < oMovesIdx.size(); ++i) {
//						System.out.print((i));
				System.out.print(oMovesIdx.get(i));
				System.out.print("\n");
			}
			System.out.println("\n");
			final int[] i = {0};
			final int[] x = {0};
			AtomicBoolean flag = new AtomicBoolean(false);
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					i[0] = i[0] + 1;
					if (i[0] == 9) {
						t.cancel();
					}
					Platform.runLater(() -> {
						if (play.xMoves.get(x[0]).equals("X") ) {
							Text move = (Text) children.get(x[0]);
							move.setText(play.xMoves.get(x[0]));

						}
						else if (play.oMoves.get(x[0]).equals("O") ) {
							Text move = (Text) children.get(x[0]);
							move.setText("O");
						}
//						else
//							x[0]++;
						x[0]++;

					});
				}
			}, 0, 1250);


			PauseTransition delay2 = new PauseTransition(Duration.seconds(12));
			delay2.setOnFinished( event -> {
				try {

					Stage stage = new Stage();
					changeToResultScene(stage,play);

				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			delay2.play();
			gameNumber++;
			PauseTransition delay = new PauseTransition(Duration.seconds(17));
			int finalJ = gameNumber;
			delay.setOnFinished(event -> {
				try {
					playNextGame(play, numGame,finalJ);
				} catch (ExecutionException | InterruptedException e) {
					e.printStackTrace();
				}
			});
			delay.play();
			System.out.println(play.winner);
		}
	}

	public void changeToResultScene(Stage stage,  GamePlay play) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Result.fxml"));
		Parent root = loader.load();
		Result scene2Controller = loader.getController();
		scene2Controller.displayResult(play.winner);
		Scene newScene = new Scene(root, 600,600);
		stage.setTitle("Result Scene");
		stage.setScene(newScene);
		stage.show();
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished(event -> {
			stage.close();
		});
		delay.play();
	}

	public void exit(ActionEvent actionEvent) {
		Stage stage = (Stage) exit.getScene().getWindow(); stage.close();
	}

	public void displayGameRules(ActionEvent actionEvent) {

		String rules = "1) Select the difficulty for X and O \n" +
				      "2) Select the number of games to play for selected difficulty levels.\n" +
				      "3) Click Start. Watch the Game. Wait until selected number of games are played. \n" +
				      "4) Result  will be displayed after each round on a new scene for 5 seconds and the next game will be played if any. ";

		ButtonType confirm = new ButtonType("Understood", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,rules,confirm);
		alert.setTitle("Rules of the game");

		alert.show();
	}
}
