package dataStructure;

public class SparseArrayDemo {
    /**
     * 稀疏数组
     *
     * @param args
     */
    public static void main(String[] args) {
        //普通二维数组
        //这是一个11*11的棋盘，0表示没有旗子，1表示黑子，2表示白子
        int[][] normal = new int[11][11];
        normal[1][2] = 1;
        normal[2][3] = 2;
        //格式化便利出数组
        for (int[] row : normal) {
            for (int i : row) {
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }

        //普通二维数组转为稀疏数组
        //1.首先便利获取sum
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (normal[i][j] != 0) {
                    sum++;
                }
            }
        }
        //2.创建对应的稀疏数组
        int sparseArr[][] = new int[sum + 1][3];
        //给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //用于记录是第几个非0数据
        int count = 1;
        //将非0的数据放入稀疏数组
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (normal[i][j] != 0) {
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = normal[i][j];
                    count++;
                }
            }
        }
        //输出稀疏数组的形式
        for (int[] rows : sparseArr) {
            for (int i : rows) {
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }

        //将稀疏数组恢复为普通二维数组
        int[][] normalArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        for (int i = 1; i < sparseArr.length; i++) {
            normalArr[sparseArr[i][0]][sparseArr[i][1]]=sparseArr[i][2];
        }
        for (int[] rows : normalArr) {
            for (int i : rows) {
                System.out.printf("%s\t",i);
            }
            System.out.println();
        }
    }


}
