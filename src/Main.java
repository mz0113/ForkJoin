import test.Data;
import test.SearchTask;

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * 并行搜索思想，结合ForkJoin框架
 */
public class Main {

    public static void main(String[] args) {
        SearchTask searchTask = new SearchTask(4,new Data(),0,Data.longs.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool(6);
        ForkJoinTask forkJoinTask = forkJoinPool.submit(searchTask);
        Scanner scanner = new Scanner(System.in);
        scanner.next();
/*        try {
            //System.out.println(forkJoinTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
  //      ffffff9890
//87678
        //342432
        //999
    }
}
