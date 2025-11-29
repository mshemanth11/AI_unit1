import java.util.*;

class HanoiState {
    List<String> pegs; 
    HanoiState parent;
    String move;

    public HanoiState(List<String> pegs, HanoiState parent, String move) {
        this.pegs = pegs;
        this.parent = parent;
        this.move = move;
    }

    public boolean isGoal() {
        return pegs.get(0).equals("") && pegs.get(1).equals("") && pegs.get(2).equals("321");
    }

    public String toString() {
        return "A: " + pegs.get(0) + "  B: " + pegs.get(1) + "  C: " + pegs.get(2);
    }
}

public class BFS_TowerOfHanoi_Simple {

    public static void main(String[] args) {

        Queue<HanoiState> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        List<String> start = new ArrayList<>(Arrays.asList("321", "", ""));
        queue.add(new HanoiState(start, null, null));

        while (!queue.isEmpty()) {

            HanoiState current = queue.poll();

            if (visited.contains(current.toString()))
                continue;

            visited.add(current.toString());

            if (current.isGoal()) {
                System.out.println("\n--- BFS Tower of Hanoi Solution ---\n");

                List<String> result = new ArrayList<>();
                while (current.move != null) {
                    result.add(current.move + " → " + current.toString());
                    current = current.parent;
                }

                Collections.reverse(result);
                for (String step : result)
                    System.out.println(step);

                return;
            }

            // Try moving disk from each peg to another
            for (int from = 0; from < 3; from++) {
                if (current.pegs.get(from).isEmpty()) continue;

                char disk = current.pegs.get(from).charAt(current.pegs.get(from).length() - 1);

                for (int to = 0; to < 3; to++) {
                    if (from == to) continue;

                    String target = current.pegs.get(to);

                    if (target.isEmpty() || target.charAt(target.length() - 1) > disk) {

                        List<String> newPegs = new ArrayList<>(current.pegs);

                        newPegs.set(from, newPegs.get(from).substring(0, newPegs.get(from).length() - 1));
                        newPegs.set(to, newPegs.get(to) + disk);

                        queue.add(new HanoiState(
                                newPegs,
                                current,
                                "Move Disk " + disk + " from " + getPegName(from) + " to " + getPegName(to)
                        ));
                    }
                }
            }
        }
    }

    static String getPegName(int index) {
        return switch (index) {
            case 0 -> "A";
            case 1 -> "B";
            default -> "C";
        };
    }
}

