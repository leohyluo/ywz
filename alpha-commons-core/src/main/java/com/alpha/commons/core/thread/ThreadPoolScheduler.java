package com.alpha.commons.core.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public final class ThreadPoolScheduler {

	private static ThreadPoolScheduler manager = new ThreadPoolScheduler();
	private ThreadPoolExecutor pool;

	private ThreadPoolScheduler() {
		pool = new ThreadPoolExecutor(100, 160, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static boolean addTask(Runnable run) {
		manager.pool.execute(run);
		return true;
	}
}