package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.*;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();
        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScrean();
                UI.printMatch(chessMatch, capturedPieces);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(scanner);
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScrean();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);
                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if (capturedPiece != null) {
                    capturedPieces.add(capturedPiece);
                }
                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    String type = scanner.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && ! type.equals("Q")){
                        System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
                        type = scanner.nextLine().toUpperCase();
                    }
                    chessMatch.replacePrmotedPiece(type);
                }
            } catch (ChessException | InputMismatchException exception) {
                System.out.println(exception.getMessage());
                System.out.print("Please any key..");
                scanner.nextLine();
            }
        }
        UI.clearScrean();
        UI.printMatch(chessMatch, capturedPieces);
    }
}
