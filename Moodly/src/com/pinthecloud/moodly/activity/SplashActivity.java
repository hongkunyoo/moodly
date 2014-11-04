package com.pinthecloud.moodly.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.fragment.SplashFragment;

public class SplashActivity extends MoActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		SplashFragment fragment = new SplashFragment();
		fragmentTransaction.add(R.id.activity_container, fragment);
		fragmentTransaction.commit();
	}
}
