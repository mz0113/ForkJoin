package test;

public class Data {
    //一千万个元素
    public static int[] longs = new int[10];
    {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = i;
        }
    }
}
