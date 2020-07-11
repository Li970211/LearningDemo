package main.java.algo.array;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author 李嘉
 * @create 2020-07-05-13:38
 * 稀疏数组
 * ==============================
 * 二维数组的概述：
 * 二维数组其实就是一个元素为一维数组的数组；
 * ==============================
 * 格式1：
 * 数据类型[][] 变量名=new 数据类型[m][n];
 * m表示这个二维数组有多少个数组
 * n表示每一个一维数组的元素个数
 * 举例：
 * int[][] arr=new int[3][2];
 * 定义了一个二维数组arr
 * 这个二维数组有3个一维数组，名称是ar[0],arr[1],arr[2]
 * 每个一维数组有2个元素，可以通过arr[m][n]来获取
 * ==============================
 * 格式2：
 * 数据类型[][] 变量名=new 数据类型[m][];
 * m表示这个二维数组有多少个数组
 * 这一次没有直接给出一维数组的元素个数，可以动态的给出
 * 举例：
 * int[][] arr=new int[3][];
 * arr[0] = new int[2];
 * arr[1]= new int[3];
 * arr[2]=new int[1];
 * ==============================
 * 格式3：
 * 数据类型[][] 变量名=new 数据类型[][]{{元素...},{元素...},{元素...}};
 * 也可以是：
 * 数据类型[][] 变量名={{元素...},{元素...},{元素...}};
 * 举例：int[][] arr={{1,2,3},{4,6},{6}}
 */
public class SparseArray {

    public static void main(String[] args) {
        String path = "D:\\code\\Algo\\src\\main\\java\\array\\data.text";
        int[][] arry = new int[11][11];
        arry[1][2] = 1;
        arry[2][3] = 2;
        System.out.println("===========初始化二维数组=================");
        printArry(arry);

        int sparseArrySize = getSparseArrySize(arry);
        int[][] sparseArray = new int[sparseArrySize][3];
        sparseArray[0] = new int[]{arry.length, arry.length, sparseArrySize - 1};

        updateSparseArry(arry, sparseArray);
        System.out.println("===========遍历稀缺数组=================");
        printArry(sparseArray);

        try {
            writeFile(path,sparseArray);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("写入文件失败");
        }


        int[][] newSparseArray = new int[0][];
        try {
            newSparseArray = getSparseArrayByReadFile(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取文件失败");
        }
        System.out.println("===========稀缺数组转化二维数组=================");
        printArry(newSparseArray);
        int[][] newArray = new int[newSparseArray[0][0]][newSparseArray[0][0]];
        updateArry(newArray, newSparseArray);
        printArry(newArray);


    }

    /**
     * 更新二维数组
     *
     * @param arry
     * @param sparseArray
     * @return
     */
    private static int[][] updateArry(int[][] arry, int[][] sparseArray) {
        for (int i = 1; i < sparseArray.length; i++) {
            arry[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return arry;
    }


    /**
     * 更新稀缺数组
     *
     * @param arry
     * @param sparseArray
     * @return
     */
    private static int[][] updateSparseArry(int[][] arry, int[][] sparseArray) {
        int sparseArrayCout = 0;
        for (int i = 0; i < arry.length; i++) {
            for (int j = 0; j < arry[i].length; j++) {
                if (arry[i][j] != 0) {
                    sparseArray[++sparseArrayCout] = new int[]{i, j, arry[i][j]};
                }
            }
        }
        return sparseArray;
    }


    /**
     * 获取稀疏数组的一维数组的大小=有效数字+1
     *
     * @param arry
     * @return
     */
    private static int getSparseArrySize(int[][] arry) {
        int size = 1;
        for (int i = 0; i < arry.length; i++) {
            for (int j = 0; j < arry[i].length; j++) {
                if (arry[i][j] != 0) {
                    size++;
                }
            }
        }
        return size;
    }

    /**
     * 打印二维数组
     *
     * @param arry
     */
    private static void printArry(int[][] arry) {
        for (int i = 0; i < arry.length; i++) {
            for (int j = 0; j < arry[i].length; j++) {
                System.out.printf("%d\t",arry[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 将数组写入文件中
     * @param filePath
     * @param arry
     * @throws IOException
     */
    public static void writeFile(String filePath,int[][] arry) throws IOException {
        System.out.println("===================将数组写入文件====================");
        Path path = Paths.get(filePath);
        StringBuffer content = new StringBuffer();
        for (int[] ints : arry) {
            for (int item : ints) {
                content.append(item+" ");
            }
            content.append("\n");
        }
        Files.write(path, content.toString().getBytes("UTF-8"), StandardOpenOption.APPEND, StandardOpenOption.CREATE);

    }


    /**
     * 通过文件获取数组
     * @param filePath
     * @return
     * @throws IOException
     */
    public static int[][]  getSparseArrayByReadFile(String filePath) throws IOException {
        System.out.println("===================从文件中读取数组====================");
        List<String> list = Files.readAllLines(Paths.get(filePath));
        int size = list.size();
        int[][] newSparseArray = new int[size][3];
        String[] split = null;
        for (int i = 0; i < size; i++) {
            split = list.get(i).split(" ");
            for (int j = 0; j < split.length; j++) {
                newSparseArray[i][j] = Integer.valueOf(split[j]);
            }
        }
        return newSparseArray;
    }
}
