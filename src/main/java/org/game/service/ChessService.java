package org.game.service;

import org.game.Board;
import org.game.Coordinate;
import org.game.Game;
import org.game.Move;
import org.game.PieceOut;
import org.game.enums.Color;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.game.enums.Color.WHITE;
import static org.game.enums.Color.nextPlayer;
import static org.game.enums.Column.columFromValue;
import static org.game.enums.Row.rowFromValue;

@Service
public class ChessService {

    Game game = new Game(new Board());
    Color currentPlayer = WHITE;

    // Get all persons
    public List<PieceOut> move(String command) {

        System.out.println();

        Board state = game.board.clone();

        Coordinate from = new Coordinate(columFromValue(String.valueOf(command.charAt(0))), rowFromValue(Character.getNumericValue(command.charAt(1))));
        Coordinate to = new Coordinate(columFromValue(String.valueOf(command.charAt(2))), rowFromValue(Character.getNumericValue(command.charAt(3))));
        Move move = new Move(from, to);

        System.out.println(currentPlayer + " move: " + move);

        String error = game.move(currentPlayer, new Move(from, to));

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

        return game.board.buildResponse();
    }

}
