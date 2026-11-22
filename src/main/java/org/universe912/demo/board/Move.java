package org.universe912.demo.board;

import java.util.Optional;

public final class Move {
    private static final int PALACE_MIN_ROW = 0;
    private static final int PALACE_MAX_ROW = 2;
    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;

    private Move() {
    }

    public static Board move(Board board, Displacement displacement) {
        if (board == null || displacement == null) {
            throw new IllegalArgumentException("Board and displacement must not be null");
        }

        Position start = displacement.start();
        Position target = displacement.target();

        Piece movingPiece = board.pieceAt(start).orElseThrow(
                () -> new IllegalArgumentException("No piece at the starting position"));
        if (movingPiece.pieceSide != board.currentSide) {
            throw new IllegalArgumentException(
                    "Not able to move the %s side piece when it's %s turn"
                            .formatted(movingPiece.pieceSide.name(), board.currentSide.name()));
        }

        Optional<Piece> capturedPiece = board.pieceAt(target);
        if (capturedPiece.isPresent()
                && capturedPiece.get().pieceSide == movingPiece.pieceSide) {
            throw new IllegalArgumentException("A piece cannot capture a piece from its own side");
        }
        if (!checkPieceMove(board, movingPiece, displacement)) {
            throw illegalMove(movingPiece, displacement);
        }

        Board movedBoard = board.copy();
        movedBoard.setPieceAt(start, null);
        movedBoard.setPieceAt(target, movingPiece);

        if (isGeneralInCheck(movedBoard, movingPiece.pieceSide)) {
            throw new IllegalArgumentException(
                    "Illegal move: the %s general would be in check"
                            .formatted(movingPiece.pieceSide.name()));
        }

        movedBoard.switchCurrentSide();
        return movedBoard;
    }

    static boolean checkChariotMove(Board board, Displacement displacement) {
        return hasValidEndpoints(board, displacement)
                && displacement.isOrthogonal()
                && countPiecesBetween(board, displacement) == 0;
    }

    static boolean checkHorseMove(Board board, Displacement displacement) {
        if (!hasValidEndpoints(board, displacement)) {
            return false;
        }

        int deltaX = displacement.deltaX();
        int deltaY = displacement.deltaY();
        int absoluteX = Math.abs(deltaX);
        int absoluteY = Math.abs(deltaY);
        if (!((absoluteX == 2 && absoluteY == 1)
                || (absoluteX == 1 && absoluteY == 2))) {
            return false;
        }

        Position start = displacement.start();
        int legX = start.x();
        int legY = start.y();
        if (absoluteX == 2) {
            legX += Integer.signum(deltaX);
        } else {
            legY += Integer.signum(deltaY);
        }
        return board.pieceAt(Position.of(legX, legY)).isEmpty();
    }

    static boolean checkCannonMove(Board board, Displacement displacement) {
        if (!hasValidEndpoints(board, displacement)
                || !displacement.isOrthogonal()) {
            return false;
        }

        int screens = countPiecesBetween(board, displacement);
        boolean isCapture = board.pieceAt(displacement.target()).isPresent();
        return isCapture ? screens == 1 : screens == 0;
    }

    static boolean checkSoldierMove(Board board, Displacement displacement) {
        if (!hasValidEndpoints(board, displacement)) {
            return false;
        }

        Piece soldier = board.pieceAt(displacement.start()).orElse(null);
        if (soldier == null) {
            return false;
        }

        int deltaX = displacement.deltaX();
        int deltaY = displacement.deltaY();
        int forward = soldier.pieceSide == Side.RED ? -1 : 1;
        if (deltaX == forward && deltaY == 0) {
            return true;
        }

        boolean hasCrossedRiver = soldier.pieceSide == Side.RED
                ? displacement.start().x() <= 4
                : displacement.start().x() >= 5;
        return hasCrossedRiver && deltaX == 0 && Math.abs(deltaY) == 1;
    }

    static boolean checkAdvisorMove(Board board, Displacement displacement) {
        if (!hasValidEndpoints(board, displacement)) {
            return false;
        }

        Piece advisor = board.pieceAt(displacement.start()).orElse(null);
        if (advisor == null) {
            return false;
        }

        int absoluteX = Math.abs(displacement.deltaX());
        int absoluteY = Math.abs(displacement.deltaY());
        return absoluteX == 1
                && absoluteY == 1
                && isInPalace(advisor.pieceSide, displacement.start())
                && isInPalace(advisor.pieceSide, displacement.target());
    }

    static boolean checkElephantMove(Board board, Displacement displacement) {
        if (!hasValidEndpoints(board, displacement)) {
            return false;
        }

        Position start = displacement.start();
        Position target = displacement.target();
        Piece elephant = board.pieceAt(start).orElse(null);
        if (elephant == null) {
            return false;
        }

        int deltaX = displacement.deltaX();
        int deltaY = displacement.deltaY();
        if (Math.abs(deltaX) != 2 || Math.abs(deltaY) != 2) {
            return false;
        }

        Position eye = Position.of(
                start.x() + deltaX / 2, start.y() + deltaY / 2);
        if (board.pieceAt(eye).isPresent()) {
            return false;
        }

        return elephant.pieceSide == Side.RED
                ? start.x() >= 5 && target.x() >= 5
                : start.x() <= 4 && target.x() <= 4;
    }

    static boolean checkGeneralMove(Board board, Displacement displacement) {
        if (!hasValidEndpoints(board, displacement)) {
            return false;
        }

        Position start = displacement.start();
        Position target = displacement.target();
        Piece general = board.pieceAt(start).orElse(null);
        if (general == null) {
            return false;
        }

        int deltaX = Math.abs(displacement.deltaX());
        int deltaY = Math.abs(displacement.deltaY());
        if (deltaX + deltaY == 1
                && isInPalace(general.pieceSide, start)
                && isInPalace(general.pieceSide, target)) {
            return true;
        }

        Optional<Piece> targetPiece = board.pieceAt(target);
        boolean isFlyingGeneralCapture = targetPiece.isPresent()
                && targetPiece.get().pieceType == PieceType.GENERAL
                && targetPiece.get().pieceSide != general.pieceSide
                && start.y() == target.y()
                && countPiecesBetween(board, displacement) == 0;
        return isFlyingGeneralCapture;
    }

    private static boolean checkPieceMove(
            Board board, Piece piece, Displacement displacement) {
        return switch (piece.pieceType) {
            case CHARIOT -> checkChariotMove(board, displacement);
            case HORSE -> checkHorseMove(board, displacement);
            case CANNON -> checkCannonMove(board, displacement);
            case SOLDIER -> checkSoldierMove(board, displacement);
            case ADVISOR -> checkAdvisorMove(board, displacement);
            case ELEPHANT -> checkElephantMove(board, displacement);
            case GENERAL -> checkGeneralMove(board, displacement);
        };
    }

    private static boolean isGeneralInCheck(Board board, Side side) {
        Optional<Position> general = findGeneral(board, side);
        if (general.isEmpty()) {
            return false;
        }

        for (int row = 0; row < Board.ROW_COUNT; row++) {
            for (int column = 0; column < Board.COLUMN_COUNT; column++) {
                Position source = Position.of(row, column);
                Piece attacker = board.pieceAt(source).orElse(null);
                if (attacker == null || attacker.pieceSide == side) {
                    continue;
                }

                Displacement attack = new Displacement(
                        row, column, general.get().x(), general.get().y());
                if (checkPieceMove(board, attacker, attack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Optional<Position> findGeneral(Board board, Side side) {
        for (int row = 0; row < Board.ROW_COUNT; row++) {
            for (int column = 0; column < Board.COLUMN_COUNT; column++) {
                Position position = Position.of(row, column);
                Piece piece = board.pieceAt(position).orElse(null);
                if (piece != null
                        && piece.pieceType == PieceType.GENERAL
                        && piece.pieceSide == side) {
                    return Optional.of(position);
                }
            }
        }
        return Optional.empty();
    }

    private static int countPiecesBetween(
            Board board, Displacement displacement) {
        int stepX = Integer.compare(
                displacement.target().x(), displacement.start().x());
        int stepY = Integer.compare(
                displacement.target().y(), displacement.start().y());
        int x = displacement.start().x() + stepX;
        int y = displacement.start().y() + stepY;
        int count = 0;

        while (x != displacement.target().x()
                || y != displacement.target().y()) {
            if (board.pieceAt(x, y).isPresent()) {
                count++;
            }
            x += stepX;
            y += stepY;
        }
        return count;
    }

    private static boolean hasValidEndpoints(
            Board board, Displacement displacement) {
        if (board == null || displacement == null) {
            return false;
        }
        return board.pieceAt(displacement.start()).isPresent();
    }

    private static boolean isInPalace(Side side, Position position) {
        int minimumRow = side == Side.RED
                ? Board.ROW_COUNT - 1 - PALACE_MAX_ROW : PALACE_MIN_ROW;
        int maximumRow = side == Side.RED
                ? Board.ROW_COUNT - 1 - PALACE_MIN_ROW : PALACE_MAX_ROW;
        return position.x() >= minimumRow && position.x() <= maximumRow
                && position.y() >= PALACE_MIN_COLUMN
                && position.y() <= PALACE_MAX_COLUMN;
    }

    private static IllegalArgumentException illegalMove(
            Piece piece, Displacement displacement) {
        return new IllegalArgumentException(
                "Illegal move of piece %s to move: %s"
                        .formatted(piece.pieceType.name(), displacement));
    }
}
