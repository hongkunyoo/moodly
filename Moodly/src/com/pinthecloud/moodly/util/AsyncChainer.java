package com.pinthecloud.moodly.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.pinthecloud.moodly.exception.MoException;
import com.pinthecloud.moodly.fragment.MoFragment;

public class AsyncChainer {
	private static final int NUM_OF_QUEUE = 16;
	private static Map<String, Queue<Chainable>> mapQueue = new HashMap<String, Queue<Chainable>>();


	public static void asyncChain(MoFragment frag, Chainable...chains) {
		Class<?> clazz = null;
		if (frag == null) {
			clazz = MoFragment.class;
		} else {
			clazz = frag.getClass();
		}
		Queue<Chainable> queue = mapQueue.get(clazz.getName());
		if (queue == null) {
			mapQueue.put(clazz.getName(), new ArrayBlockingQueue<Chainable>(NUM_OF_QUEUE));
			queue = mapQueue.get(clazz.getName());
		}
		for(Chainable c : chains) {
			queue.add(c);
		}
		AsyncChainer.notifyNext(frag);
	}


	public static void notifyNext(MoFragment frag) {
		Class<?> clazz = null;
		if (frag == null) {
			clazz = MoFragment.class;
		} else {
			clazz = frag.getClass();
		}
		Queue<Chainable> queue = mapQueue.get(clazz.getName());
		if (queue != null && !queue.isEmpty()) {
			Chainable c = queue.poll();
			if (c == null) throw new MoException("chain == null");
			c.doNext(frag);
		}
	}


	public static interface Chainable {
		public void doNext(MoFragment frag);
	}


	public static void clearChain(MoFragment frag) {
		Class<?> clazz = null;
		if (frag == null) {
			clazz = MoFragment.class;
		} else {
			clazz = frag.getClass();
		}
		Queue<Chainable> queue = mapQueue.get(clazz.getName());
		queue.clear();
	}
}
