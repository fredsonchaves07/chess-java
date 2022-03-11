package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position newPosition = new Position(0, 0);
        if (getColor() == Color.WHITE) {
            newPosition.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition)) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
            newPosition.setValues(position.getRow() - 2, position.getColumn());
            Position position2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition) && getBoard().positionExists(position2) && !getBoard().thereIsAPiece(position2) && getMoveCount() == 0) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
            newPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
            newPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
        } else {
            newPosition.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition)) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
            newPosition.setValues(position.getRow() + 2, position.getColumn());
            Position position2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition) && getBoard().positionExists(position2) && !getBoard().thereIsAPiece(position2) && getMoveCount() == 0) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
            newPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
            newPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
                possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
        }
        return possibleMoves;
    }

    @Override
    public String toString() {
        return "P";
    }
}
