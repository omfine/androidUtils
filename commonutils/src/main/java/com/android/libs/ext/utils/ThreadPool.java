package com.android.libs.ext.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池。
 * @author E
 */
public class ThreadPool {
	
	private static ExecutorService pool;  
	 
    static {  
        pool = Executors.newFixedThreadPool(30);
    }  
	
    public static void add(Runnable runnable){
    	try {
    		//execute让执行更加快速，不用等待
//    		pool.execute(runnable);
    		//submit可以让程序更加稳定
    		pool.submit(runnable);
		} catch (Exception e) {
			pool.shutdown();
		    e.printStackTrace();
		}
    }
}
