package salary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static int result;
    private static int s;
    private static BufferedReader bufferedReader = null;


    public static void main(String[] args) throws Exception {
        init();
        run();
    }

    private static void init() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    private static void run() throws IOException {
        String[] firstLine = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]); //высота стопки
        int m = Integer.parseInt(firstLine[1]);//высота стопки
        s = Integer.parseInt(firstLine[2]); // Маша устанавливает значение s максимальной суммы зарплат и
        int length = Math.max(n, m);
        int[] firstDeque = new int[n];
        int[] secondDeque = new int[m];


        //складываем в стопки
        for (int i = 0; i < length; i++) {
            String[] line = bufferedReader.readLine().split(" ");
            String firstPoint = line[0];
            String secondPoint = line[1];
            if (!firstPoint.equals("-")) {
                firstDeque[i] = Integer.parseInt(firstPoint);
//                firstDeque.addLast(Integer.parseInt(firstPoint));
            }
            if (!secondPoint.equals("-")) {
                secondDeque[i] = Integer.parseInt(secondPoint);
//                secondDeque.addLast(Integer.parseInt(secondPoint));
            }
        }

        result = 0;
        // сначала идем по первой а потом по второй и смотрим где больше тиков
        getMeResultForCycle(firstDeque, secondDeque);
        getMeResultForCycle(secondDeque, firstDeque);
        System.out.println(result);
    }

    public static void getMeResultForCycle(int[] first, int[] second) {
        int firstSumStack = 0;
        int secondSumStack = 0;
        int count = 0;
        int resultSumSalary = 0;
        for (int i = 0; i < first.length; i++) {
            int firstStack = first[i];
            firstSumStack = firstSumStack + firstStack;
            if (firstSumStack > s) return;
            count++;
            if (count > result) result = count;

            for (int z = 0; z < second.length; z++) {
                int secondStack = second[z];
                secondSumStack = secondSumStack + secondStack;
                if (secondSumStack > s) break;
                resultSumSalary = secondSumStack + firstSumStack;
                if (resultSumSalary > s) break;
                count++;
                if (count > result) {
                    result = count;
                }
                if (resultSumSalary == s) break;
            }
            resultSumSalary = firstSumStack;
            secondSumStack = 0;
            count = i + 1;
        }
    }
}
/*

3 3 3
9 1
- 1
- 1

1 2 10
9 9
- 1


1 2 10
9 9
- 1

1 1 22
1 9

6 4 10
4 2
2 1
4 8
6 5
1 -
7 -

5 5 10
5 1
1 3
1 3
1 3
1 3

3 4 11
1 1
2 2
3 3
- 4

4 5 75
1 70
2 1
3 1
70 1
- 1

 */