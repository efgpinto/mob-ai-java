import multipaint.Action;
import multipaint.Board;
import multipaint.Runner;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class Follower implements multipaint.Bot {
    private String playerId;
    Random r;

    static String[] ActionTypes = new String[]{"shoot", "walk"};
    static int[][] ActionDirections = new int[][]{
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0}, {1, 0},
            {-1, 1}, {0, 1}, {1, 1},
    };

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
        this.r = new Random();
    }

    public Action nextMove(Board state) {

        System.err.println("\n\n#############################\nTurns left:" + state.turns_left);
        Action a = new Action();
        int[] currentPos = state.player_positions.get(playerId);

        a.type = ActionTypes[1];
        String closestOpponent = findClosestOpponent(state.player_positions);
        a.direction = moveCloserToOpponent(state.player_positions.get(closestOpponent), currentPos);

        System.err.println("Play: " + actionToString(a));

        return a;
    }

    private String findClosestOpponent(Map<String, int[]> players) {
        return players.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(playerId))
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), distance(entry.getValue(), players.get(playerId))))
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    private int[] moveCloserToOpponent(int[] opponentPos, int[] currentPos) {
        return Stream.of(ActionDirections)
                .map(c -> new AbstractMap.SimpleEntry<>(new int[]{c[0], c[1]}, distance(calculateNewPos(currentPos, c), opponentPos)))
                .min(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    private int[] calculateNewPos(int[] current, int[] dir) {
        return new int[]{current[0] + dir[0], current[1] + dir[1]};
    }

    private double distance(int[] a, int[] b) {
        return Math.hypot(a[0] - b[0], a[1] - b[1]);
    }

    private String dirToString(int[] dir) {

        if (posEquals(dir, ActionDirections[0]))
            return "↖";
        if (posEquals(dir, ActionDirections[1]))
            return "←";
        if (posEquals(dir, ActionDirections[2]))
            return "↙";
        if (posEquals(dir, ActionDirections[3]))
            return "↑";
        if (posEquals(dir, ActionDirections[4]))
            return "↓";
        if (posEquals(dir, ActionDirections[5]))
            return "↗";
        if (posEquals(dir, ActionDirections[6]))
            return "→";
        if (posEquals(dir, ActionDirections[7]))
            return "↘";

        return "error";
    }

    private String actionToString(Action a) {
        return a.type + " " + dirToString(a.direction);
    }

    private boolean posEquals(int[] a, int[] b) {
        return a[0] == b[0] && a[1] == b[1];
    }

    public static void main(String[] args) {
        Runner.run(new Follower());
    }
}
