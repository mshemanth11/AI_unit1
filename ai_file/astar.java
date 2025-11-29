import java.util.*;

class Node {
    public int x, y;
    public int gCost, hCost, fCost;
    public Node parent;
    public boolean isWall;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void calculateCosts(Node endNode) {
        if (endNode != null) {
            this.hCost = Math.abs(endNode.x - this.x) + Math.abs(endNode.y - this.y);
        }
        this.fCost = this.gCost + this.hCost;
    }
}

public class astar {

    private static final int ROWS = 6;
    private static final int COLS = 6;

    private static Node[][] grid = new Node[ROWS][COLS];

    public static List<Node> findPath(Node start, Node end) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
        Set<Node> closedList = new HashSet<>();

        start.calculateCosts(end);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closedList.add(current);

            if (current == end) {
                return buildPath(end);
            }

            for (Node neighbor : getNeighbors(current)) {
                if (neighbor.isWall || closedList.contains(neighbor)) continue;

                int tentativeG = current.gCost + 1;

                if (tentativeG < neighbor.gCost || !openList.contains(neighbor)) {
                    neighbor.gCost = tentativeG;
                    neighbor.calculateCosts(end);
                    neighbor.parent = current;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    private static List<Node> buildPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        int[][] directions = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0} // 4-directional movement
        };

        for (int[] d : directions) {
            int nx = node.x + d[0];
            int ny = node.y + d[1];

            if (nx >= 0 && nx < ROWS && ny >= 0 && ny < COLS) {
                neighbors.add(grid[nx][ny]);
            }
        }

        return neighbors;
    }

    public static void main(String[] args) {

        // Create grid nodes
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Node(i, j);
            }
        }

        // Set walls (obstacles)
        grid[1][1].isWall = true;
        grid[1][2].isWall = true;
        grid[2][2].isWall = true;
        grid[3][3].isWall = true;

        Node start = grid[0][0];
        Node end = grid[5][5];

        List<Node> path = findPath(start, end);

        if (path != null) {
            System.out.println("Path found:");
            for (Node n : path) {
                System.out.println("(" + n.x + ", " + n.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}
