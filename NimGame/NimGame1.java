import java.util.Scanner;

public class NimGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // --- 初期設定 ---
        System.out.print("山の数を入力してください: ");
        int n = sc.nextInt();

        int[] piles = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("山 " + (i + 1) + " の石の数を入力してください: ");
            piles[i] = sc.nextInt();
        }

        System.out.println("\n--- ニムゲーム開始！ ---");
        printPiles(piles);

        boolean playerTurn = true;

        // --- ゲームループ ---
        while (true) {
            // 勝敗判定
            if (isGameOver(piles)) {
                if (playerTurn) {
                    System.out.println("コンピュータの勝ちです！");
                } else {
                    System.out.println("あなたの勝ちです！");
                }
                break;
            }

            if (playerTurn) {
                // --- プレイヤーの番 ---
                System.out.println("\nあなたの番です。");
                System.out.print("どの山から取りますか（1〜" + n + "）: ");
                int pile = sc.nextInt() - 1;

                System.out.print("いくつ取りますか（1〜" + piles[pile] + "）: ");
                int remove = sc.nextInt();

                if (pile < 0 || pile >= n || remove <= 0 || remove > piles[pile]) {
                    System.out.println("無効な入力です。もう一度。");
                    continue;
                }

                piles[pile] -= remove;
            } else {
                // --- コンピュータの番 ---
                System.out.println("\nコンピュータの番です。");

                int xorSum = 0;
                for (int p : piles) xorSum ^= p;

                int chosenPile = -1;
                int removeCount = 0;

                // 必勝法（XORが0にならないように調整）
                for (int i = 0; i < n; i++) {
                    int target = piles[i] ^ xorSum;
                    if (target < piles[i]) {
                        chosenPile = i;
                        removeCount = piles[i] - target;
                        break;
                    }
                }

                // もし必勝手がない場合、適当に取る
                if (chosenPile == -1) {
                    for (int i = 0; i < n; i++) {
                        if (piles[i] > 0) {
                            chosenPile = i;
                            removeCount = 1;
                            break;
                        }
                    }
                }

                System.out.println("山 " + (chosenPile + 1) + " から " + removeCount + " 個取ります。");
                piles[chosenPile] -= removeCount;
            }

            printPiles(piles);
            playerTurn = !playerTurn;
        }

        sc.close();
    }

    // --- 残りの石を表示 ---
    static void printPiles(int[] piles) {
        System.out.println("\n現在の山の状態:");
        for (int i = 0; i < piles.length; i++) {
            System.out.println("山 " + (i + 1) + ": " + piles[i] + " 個");
        }
    }

    // --- 終了判定 ---
    static boolean isGameOver(int[] piles) {
        for (int p : piles) {
            if (p > 0) return false;
        }
        return true;
    }
}
