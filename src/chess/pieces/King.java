package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color) {
        super(board, color);
    }

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves =  new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position newPosition = new Position(0, 0);
        newPosition.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        newPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
            possibleMoves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            Position positionRook1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(positionRook1)) {
                Position position1 = new Position(position.getRow(), position.getColumn() + 1);
                Position position2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null) {
                    possibleMoves[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            Position positionRook2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(positionRook2)) {
                Position position1 = new Position(position.getRow(), position.getColumn() - 1);
                Position position2 = new Position(position.getRow(), position.getColumn() - 2);
                Position position3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null && getBoard().piece(position3) == null) {
                    possibleMoves[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
        return possibleMoves;
    }
}
