package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private final Board board;
    private int turn;
    private boolean check;
    private boolean checkMate;
    private ChessPiece promoted;
    private ChessPiece enPassantVulnerable;
    private Color currentPlayer;
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> caputredPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] chessPieces = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i ++) {
            for (int j = 0; j < board.getColumns(); j ++) {
                chessPieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return chessPieces;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        ChessPiece movedPiece = (ChessPiece) board.piece(target);
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promoted = (ChessPiece) board.piece(target);
                promoted = replacePrmotedPiece("Q");
            }
        }
        check = testCheck(opponent(currentPlayer));
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() +2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }
        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) throw new ChessException("There is not piece on source position");
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) throw new ChessException("The chosen piece is not yours");
        if (!board.piece(position).isThereAnyPossibleMove()) throw new ChessException("There is not possible moves");
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position");
    }

    public ChessPiece replacePrmotedPiece(String type) {
        if (promoted == null)
            throw new IllegalStateException("There is no piece to be promoted");
        if (!type.equals("B") && !type.equals("N") && !type.equals("R") && ! type.equals("Q"))
            return promoted;
        Position position = promoted.getChessPosition().toPosition();
        Piece piece = board.removePiece(position);
        piecesOnTheBoard.remove(piece);
        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, position);
        piecesOnTheBoard.add(newPiece);
        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("B")) return new Bishop(board, color);
        if (type.equals("N")) return new Knight(board, color);
        if (type.equals("Q")) return new Queen(board, color);
        return new Rook(board, color);
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece removedPiece = (ChessPiece) board.removePiece(source);
        removedPiece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(removedPiece, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            caputredPieces.add(capturedPiece);
        }
        if (removedPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
            Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();
        }
        if (removedPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
            Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();
        }
        if (removedPiece instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawPosition;
                if (removedPiece.getColor() == Color.WHITE) {
                    pawPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawPosition);
                caputredPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece pieceRemoved = (ChessPiece) board.removePiece(target);
        board.placePiece(pieceRemoved, source);
        pieceRemoved.decreaseMoveCount();
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            caputredPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
        if (pieceRemoved instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
            Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.increaseMoveCount();
        }
        if (pieceRemoved instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
            Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.increaseMoveCount();
        }
        if (pieceRemoved instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawPosition;
                if (pieceRemoved.getColor() == Color.WHITE) {
                    pawPosition = new Position(3, target.getColumn());
                } else {
                    pawPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawPosition);
            }
        }
    }

    private void nextTurn() {
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color).toList();
        for (Piece piece : list) {
            if (piece instanceof King) return (ChessPiece) piece;
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == opponent(color)).toList();
        for (Piece piece : opponentPieces) {
            boolean[][] mat = piece.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) return true;
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) return false;
        List<Piece> colorPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color).toList();
        for (Piece piece : colorPieces) {
            boolean[][] mat = piece.possibleMoves();
            for (int i = 0; i < board.getRows(); i ++) {
                for (int j = 0; j < board.getColumns(); j ++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
