package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves =  new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position newPosition = new Position(0, 0);
        newPosition.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() - 2, position.getColumn() - 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        return possibleMoves;
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public String toString() {
        return "N";
    }
}
