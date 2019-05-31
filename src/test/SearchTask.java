package test;

import java.util.concurrent.RecursiveAction;

public class SearchTask extends RecursiveAction {
    Data data;
    int startIndex;
    int endIndex;
    //要查找的元素
    int key;

    public SearchTask(int key,Data data,int startIndex,int endIndex){
        this.key=key;
        this.data = data;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected void compute() {
        if (endIndex-startIndex<=5) {
            //如果下标跨度在十万以内，就可以直接搜索并返回
            for (int i = startIndex; i < endIndex; i++) {
                if(Result.atomicInteger.get()==-1){
                    //如果仍没找到，则继续搜索
                    if (data.longs[i]==key) {
                        //这个set其实是原子操作，我们说i++不是原子操作，是因为i++分get变量和set变量两步
                        //而set,get操作本身是原子性的，除非是进行getAndSet操作才需要额外的线程同步机制
                        Result.atomicInteger.set(data.longs[i]);
                        System.out.println(String.format("线程%s,找到元素，下标%s,startIndex = %s,endIndex = %s",Thread.currentThread().getName(),i,startIndex,endIndex));
                        return;
                    }
                }else{
                    return;
                }
            }
            System.out.println(String.format("线程%s,startIndex = %s,endIndex = %s 未找到元素",Thread.currentThread().getName(),startIndex,endIndex));
        }else{
            //拆分10个子任务
            int taskCount = 2;
            int step = (endIndex-startIndex)/taskCount;
            for (int i = 0; i < taskCount; i++) {
                SearchTask searchTask = null;
                if(i==taskCount-1){
                    //如果是最后一项，为了解决10/3=3.3333的问题。
                    //最后一个任务就多执行点把。
                    searchTask = new SearchTask(key,data,startIndex+step*i,endIndex);
                }else{
                    searchTask = new SearchTask(key,data,startIndex+step*i,startIndex+step*(i+1));
                }
                searchTask.fork();
            }
        }
    }
}
