package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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
            if (position.getRow() == 3) {
                Position positionLeft = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(positionLeft) && isThereOpponentPiece(positionLeft) && getBoard().piece(positionLeft) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[positionLeft.getRow() - 1][positionLeft.getColumn()] = true;
                }
                Position positionRight = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(positionRight) && isThereOpponentPiece(positionRight) && getBoard().piece(positionRight) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[positionRight.getRow() - 1][positionRight.getColumn()] = true;
                }
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
            if (position.getRow() == 4) {
                Position positionLeft = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(positionLeft) && isThereOpponentPiece(positionLeft) && getBoard().piece(positionLeft) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[positionLeft.getRow() + 1][positionLeft.getColumn()] = true;
                }
                Position positionRight = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(positionRight) && isThereOpponentPiece(positionRight) && getBoard().piece(positionRight) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[positionRight.getRow() + 1][positionRight.getColumn()] = true;
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public String toString() {
        return "P";
    }
}
