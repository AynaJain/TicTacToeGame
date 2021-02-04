import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Result {


    public Label result;

    public void displayResult(String winnerPlayer)
    {
        result.setText(winnerPlayer);
        result.setFont(Font.font("Verdana", 50));

    }

//    public Button resultButton;
//    public DialogPane Result;
//
//    public void showResult(ActionEvent actionEvent) {
//        Result.setHeaderText("Winner: " + );
//    }
}
