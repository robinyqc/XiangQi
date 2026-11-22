package org.universe912.demo.board;

public final class Displacement {
    private final Position start;
    private final Position target;

    public Displacement(int xInit, int yInit, int xFinal, int yFinal) {
        this(Position.of(xInit, yInit), Position.of(xFinal, yFinal));
    }

    public Displacement(Position start, Position target) {
        if (start == null || target == null) {
            throw new IllegalArgumentException("Move positions must not be null");
        }
        if (start.equals(target)) {
            throw new IllegalArgumentException(
                    "A piece must move to a different position");
        }

        this.start = start;
        this.target = target;
    }

    public Displacement rollback() {
        return new Displacement(target.x(), target.y(), start.x(), start.y());
    }

    Position start() {
        return start;
    }

    Position target() {
        return target;
    }

    int deltaX() {
        return target.x() - start.x();
    }

    int deltaY() {
        return target.y() - start.y();
    }

    boolean isOrthogonal() {
        return start.x() == target.x() || start.y() == target.y();
    }

    @Override
    public String toString() {
        return "Displacement [start=" + start + ", target=" + target + "]";
    }

}
