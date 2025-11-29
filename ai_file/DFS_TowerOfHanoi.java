public class DFS_TowerOfHanoi {

    static int moveCount = 0;

    static void towerOfHanoi(int n, char from_rod, char to_rod, char aux_rod) {
        if (n == 0)
            return;

        towerOfHanoi(n - 1, from_rod, aux_rod, to_rod);
        
        System.out.println("Move " + (++moveCount) + ": Disk " + n + " from " + from_rod + " to " + to_rod);

        towerOfHanoi(n - 1, aux_rod, to_rod, from_rod);
    }

    public static void main(String[] args) {
        int n = 3; // number of disks
        System.out.println("--- DFS Tower of Hanoi Solution ---");
        towerOfHanoi(n, 'A', 'C', 'B');
    }
}

