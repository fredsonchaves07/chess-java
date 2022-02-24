package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position newPosition = new Position(0, 0);
        newPosition.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            newPosition.setRow(newPosition.getRow() - 1);
        }
        if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            newPosition.setColumn(newPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            newPosition.setColumn(newPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
            newPosition.setRow(newPosition.getRow() + 1);
        }
        if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        return possibleMoves;
    }
}
