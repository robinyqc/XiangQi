package org.universe912.demo.board;

public record Position(int x, int y) {
    public Position {
        if (x < 0 || x >= Board.ROW_COUNT
                || y < 0 || y >= Board.COLUMN_COUNT) {
            throw new IllegalArgumentException(
                    "Position must be inside the 10 x 9 board: [x=%d, y=%d]"
                            .formatted(x, y));
        }
    }

    static Position of(int x, int y) {
        return new Position(x, y);
    }

    @Override
    public String toString() {
        return "Position [%d, %d]".formatted(x, y);
    }
}
