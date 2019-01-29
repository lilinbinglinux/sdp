/*
 * 文件名：ThreadPoolUtil.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年6月2日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ThreadPoolUtil {
    /**长时间线程池的最大线程数量*/
    private static final int MAX_THREAD = 2;

    /**长时间线程的超时时间,如果线程执行时间超过这个时间会自动释放*/
    private static final int LONG_TIMEOUT_TIME = 60000;

    /**短时间线程的超时时间,如果线程执行时间超过这个时间会自动释放*/
    private static final int SORT_TIMEOUT_TIME = 12000;

    /**长时间线程池,用于执行短时间较长的线程,需要设置最大线程数量*/
    private static ExecutorService longTimeThreadPool = Executors.newFixedThreadPool(MAX_THREAD);

    /**短时间线程池，用于存放执行时间较短的线程,无需设置线程的最大数量*/
    private static ExecutorService sortTimeThreadPool = Executors.newCachedThreadPool();

    /** 
     * 创建一个线程,将线程放到短时间线程池中,并且启动一个守护线程监控线程超时 
     *  
     *  
     * @param runnable:需要运行的线程 
     *  
     * @throws Exception 
     * */
    public static synchronized void getSortTimeOutThread(Runnable runnable) {
        //将线程提交到短时间线程池中,然后会得到一个Future  
        Future<String> future = (Future<String>)sortTimeThreadPool.submit(runnable);
        //启动一个守护线程，这个线程将计算超时  
        new ThreadPoolUtil().monitorThreadTime(SORT_TIMEOUT_TIME, future);
    }

    /**
     * Description: <br>
     *  放到长线程池中
     * 
     * @param runnable 
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @see
     */
    public static synchronized boolean getLongThread(Callable<Boolean> task) throws InterruptedException, ExecutionException {
        //将线程提交到长时间线程池中,然后会得到一个Future  
        Future<Boolean> future = longTimeThreadPool.submit(task);
        System.out.println("----------线程后代码");
        return future.get();
    }

    /** 
     * 创建一个线程,将线程放到长时间线程池中,并且启动一个守护线程监控线程超时 
     *  
     *  
     * @param runnable:需要运行的线程 
     *  
     * @throws Exception 
     * */
    public static synchronized void getLongTimeOutThread(Runnable runnable)
        throws Exception {
        //将线程提交到短时间线程池中,然后会得到一个Future  
        Future<String> future = (Future<String>)longTimeThreadPool.submit(runnable);
        //启动一个守护线程，这个线程将计算超时  
        new ThreadPoolUtil().monitorThreadTime(LONG_TIMEOUT_TIME, future);
    }

    /** 
     * 创建一个线程,将线程放到长时间线程池中,并且启动一个守护线程监控线程超时 
     *  
     *  
     * @param runnable:需要运行的线程 
     * @param timeOutTime:自定义的超时时间 
     *  
     * @throws Exception 
     * */
    public static synchronized void getLongTimeOutThread(Runnable runnable, int timeOutTime)
        throws Exception {
        //将线程提交到短时间线程池中,然后会得到一个Future  
        Future<String> future = (Future<String>)longTimeThreadPool.submit(runnable);
        //启动一个守护线程，这个线程将计算超时  
        new ThreadPoolUtil().monitorThreadTime(timeOutTime, future);
    }

    /** 
     * 监控线程超时守护线程,到超时时间去查看一下线程状态，如果正在执行中则停止线程（这段有问题） 
     *  
     * @param timeOutTime:超时时间 
     * @param future:执行结果表单，将用来计算超时 
     *  
     *  
     * */
    private void monitorThreadTime(final int timeOutTime, final Future<String> future) {
        //执行守护线程  
        new Thread(new Runnable() {
            @Override
            public void run() {
                //等待一段时间，然后调用执行结果，如果没有执行完，则试着去结束  
                try {
                    Thread.sleep(timeOutTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block  
                    e.printStackTrace();
                }
                //判断future是否执行完成,如果超时未完成，则试着结束线程  
                if (!future.isDone()) {
                    //false表示不允许线程执行时中断,true表示允许  
                    future.cancel(true);
                }
            }
        }).start();
    }
}
