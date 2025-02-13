package org.game.service;

import org.game.Board;
import org.game.Game;
import org.game.PieceOut;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.game.enums.Color.WHITE;
import static org.game.enums.Color.nextPlayer;
import static org.game.enums.Column.columFromValue;

@Service
public class ChessService {

    Game game = new Game(new Board());
    Color currentPlayer = WHITE;

    // Get all persons
    public List<PieceOut> move(String command) {

        Board state = game.board.clone();

        Column fromColumn = columFromValue(String.valueOf(command.charAt(0)));
        Row fromRow = Row.rowFromValue(Character.getNumericValue(command.charAt(1)));
        Column toColumn = columFromValue(String.valueOf(command.charAt(2)));
        Row toRow = Row.rowFromValue(Character.getNumericValue(command.charAt(3)));

        String error = game.move(currentPlayer, fromColumn, fromRow, toColumn, toRow);

        if (!error.isEmpty()) {
            System.out.println("Error: " + error + " Try again");
        }
        else if (game.board.isPlayerUnderCheck(currentPlayer)) {
            game.board = state;
            System.out.println("Error you are under check, Try again");
        }
        else {
            currentPlayer = nextPlayer(currentPlayer);
        }

        game.board.print();

        return game.board.buildResponse();
    }

}
