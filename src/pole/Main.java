package pole;

import java.util.HashMap;
import java.util.Scanner;

public class Main {


    public static class Data {
        public class Dot {
            private int i0;
            private int j0;
            private int iN;
            private int jN;
            private int plots;

            public Dot(int i, int j) {
                i0 = iN = i;
                j0 = jN = j;
            }


            public void setIJ(int i, int j) {
                if (i < i0) i0 = i;
                if (i > iN) iN = i;
                if (j < j0) j0 = j;
                if (j > jN) jN = j;
            }

            public void setPlots(int plots) {
                this.plots = plots;
            }

            public int getPlots() {
                return plots;
            }

            public int getSize() {
                return (iN - i0 + 1) * (jN - j0 + 1);
            }

            public double getEfficiency() {
                return ((double) plots / (double) getSize());
            }

            @Override
            public String toString() {
                return "[" + i0 + " " + j0 + "][" + +iN + " " + jN + "]:" + plots + "/" + getSize() + " = " + getEfficiency();
            }


        }

        private int n;
        private int m;
        private int[][] array;
        private HashMap<Integer, Dot> resultList;

        Data() {
            Scanner in = new Scanner(System.in);
            m = in.nextInt();
            n = in.nextInt();
            array = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    array[i][j] = in.nextInt();
                }
            }
            resultList = new HashMap<>();
        }


        @Override
        public String toString() {
            String result = m + " " + n;
            for (int i = 0; i < n; i++) {
                result += "\n";
                for (int j = 0; j < m; j++) {
                    result += array[i][j] + " ";
                }
            }
            return result;
        }

        private void setOtherPlots(int index) {
            int count = 0;
            for (int i = resultList.get(index).i0; i <= resultList.get(index).iN; i++) {
                for (int j = resultList.get(index).j0; j <= resultList.get(index).jN; j++) {
                    if (array[i][j] != 0) {
                        count++;
                    }
                }
            }
            resultList.get(index).setPlots(count);
        }

        public int getResult() {
            int index = 2;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (array[i][j] == 1) {
                        resultList.put(index, new Dot(i, j));
                        int s = recursionSet(i, j, index);
                        if (s == 1) {
                            array[i][j] = 1;
                            resultList.remove(index);
                            index--;
                        } else {
//                            System.out.println(resultList);
                        }
                        index++;
                    }
                }
            }


            resultList.forEach((k, v) -> setOtherPlots(k));

            //  resultList.forEach((k, v) -> System.out.println(k + "=" + v));

//            double efficiency = 0.0;
            int resultPlots = 0;
            int resultSize = 0;


            for (HashMap.Entry<Integer, Dot> entry : resultList.entrySet()) {
                int currentPlots = entry.getValue().getPlots();
                int currentSize = entry.getValue().getSize();

                // double currentEfficiency = entry.getValue().getEfficiency();

                if (resultSize * currentPlots > currentSize * resultPlots) {
                    resultPlots = currentPlots;
                    resultSize = currentSize;
                } else if (resultSize * currentPlots == currentSize * resultPlots) {
                    if (resultSize < currentSize) {
                        resultPlots = currentPlots;
                        resultSize = currentSize;
                    }
                } else if (resultSize == 0) {
                    resultPlots = currentPlots;
                    resultSize = currentSize;
                }


//                if (currentEfficiency > efficiency) {
//                    efficiency = currentEfficiency;
//                    result = entry.getValue().getSize();
//                } else if (Math.abs(currentEfficiency - efficiency) < 1e-8 && result < entry.getValue().getSize()) {
//                    result = entry.getValue().getSize();
//                }
            }
            return resultSize;
        }

        public int recursionSet(int i, int j, int index) {
            if (i >= 0 && i < n && j >= 0 && j < m && array[i][j] == 1) {
                array[i][j] = index;
                resultList.get(index).setIJ(i, j);
                return 1 +
                        recursionSet(i - 1, j - 1, index) +
                        recursionSet(i - 1, j, index) +
                        recursionSet(i - 1, j + 1, index) +

                        recursionSet(i, j - 1, index) +
                        recursionSet(i, j + 1, index) +

                        recursionSet(i + 1, j - 1, index) +
                        recursionSet(i + 1, j, index) +
                        recursionSet(i + 1, j + 1, index);
            }
            return 0;
        }
    }


    public static void main(String[] args) {
        //new Generate(10);
        Data date = new Data();
        System.out.println(date.getResult());
        //  System.out.println(date);
    }
}