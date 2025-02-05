package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import static org.game.enums.Color.WHITE;
import static org.game.enums.Color.nextPlayer;
import static org.game.enums.Column.columFromValue;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        Color currentPlayer = WHITE;

        while (true) {
            game.board.print();

            System.out.println(currentPlayer + " make a move ...");

            String input = System.console().readLine();

            Column fromColumn = columFromValue(String.valueOf(input.charAt(0)));
            Row fromRow = Row.rowFromValue(Character.getNumericValue(input.charAt(1)));
            Column toColumn = columFromValue(String.valueOf(input.charAt(2)));
            Row toRow = Row.rowFromValue(Character.getNumericValue(input.charAt(3)));

            String error = game.move(currentPlayer, fromColumn, fromRow, toColumn, toRow);

            if (!error.isEmpty())
                System.out.println("Error: " + error + " Try again");
            else
                currentPlayer = nextPlayer(currentPlayer);
        }

    }
}