import java.sql.SQLOutput;

/**
 * 二位数组转稀疏数组
 * */
public class SparseArray {
    public static void main(String [] args) {
        // 棋盘 0: 代表未下子, 1: 白子, 2: 黑子
        int array1[][] = new int[11][11];
        array1[1][1] = 1;
        array1[2][1] = 2;
        array1[1][2] = 1;
        // 原始二位数组
        for(int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.printf("%d\t", array1[i][j]);
            }
            System.out.println("\n");
        }
        // 原始二维数组转稀疏数组
        int sum = 0; // 记录有效数据总个数
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if(array1[i][j] != 0) {
                    sum++;
                }
            }
        }
        // 打印非0个数
        System.out.println("sum= " + sum);
        // 定义稀疏数组
        int sparse[][]= new int[sum + 1][3];
        sparse[0][0] = 11;
        sparse[0][1] = 11;
        sparse[0][2] = sum;
        int count = 0; // 记录是第几个数据
        // 稀疏数组存值
        for(int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if(array1[i][j] != 0) {
                    count ++;
                    sparse[count][0] = i;
                    sparse[count][1] = j;
                    sparse[count][2] = array1[i][j];
                }
            }
        }
        // 查看生成的稀疏数组
        for (int i = 0; i <= count; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d\t", sparse[i][j]);
            }
            System.out.println();
        }
        // 通过稀疏数组重新生成二位数组
        int array2[][] = new int[sparse[0][0]][sparse[0][1]];
        for (int i = 1; i <=count; i++) {
            array2[sparse[i][0]][sparse[i][1]] = sparse[i][2];
        }
        System.out.println("打印生成的二位数组:");
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.printf("%d\t", array2[i][j]);
            }
            System.out.println();
        }
    }
}

