package com.pinthecloud.moodly.exception;

import java.util.HashMap;
import java.util.Map;

import com.pinthecloud.moodly.fragment.MoFragment;

public class ExceptionManager {

	private static Map<String, Handler> map = new HashMap<String, ExceptionManager.Handler>();

	public static void setHandler(MoFragment frag) {
		map.put(frag.getClass().getName(), frag);
	}

	public static void fireException(MoException ex) {
		Class<?> clazz = null;
		if (ex.fromWho() == null) {
			clazz = MoFragment.class;
		} else {
			clazz = ex.fromWho().getClass();
		}
		Handler handler = map.get(clazz.getName());
		if (handler != null){
			handler.handleException(ex);
		}
	}

	public static interface Handler {
		public void handleException(MoException ex);
	}
}
