package com.pinthecloud.moodly.interfaces;

import java.util.List;

public interface MoListCallback<E> {
	public void onCompleted(List<E> list, int count);
}
