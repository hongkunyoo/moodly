package com.pinthecloud.moodly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.activity.GuideActivity;
import com.pinthecloud.moodly.activity.PerformListActivity;

public class SplashFragment extends MoFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_splash, container, false);
		runApp();
		return view;
	}


	private void runApp() {
		goToNextActivity();
	}


	private void goToNextActivity() {
		if(thisFragment.isAdded()){
			Intent intent = new Intent();
			//			intent.setClass(context, HongkunActivity.class);
			if (!userHelper.isLoggedInUser()){
				intent.setClass(context, GuideActivity.class);
			} else{
				intent.setClass(context, PerformListActivity.class);
			}
			startActivity(intent);
		}
	}
}
