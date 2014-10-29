package com.pinthecloud.moodly.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.pinthecloud.moodly.MoApplication;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.analysis.FiveRocksHelper;
import com.pinthecloud.moodly.analysis.FlurryHelper;
import com.pinthecloud.moodly.analysis.UserHabitHelper;

public class MoActivity extends ActionBarActivity{

	protected MoApplication app;
	protected MoActivity thisActivity;
	protected FiveRocksHelper fiveRocksHelper;
	protected UserHabitHelper userHabitHelper;
	protected FlurryHelper flurryHelper;


	public MoActivity(){
		thisActivity = this;
		app = MoApplication.getInstance();
		fiveRocksHelper = app.getFiveRocksHelper();
		userHabitHelper = app.getUserHabitHelper();
		flurryHelper = app.getFlurryHelper();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fiveRocksHelper.initFiveRocks(thisActivity);
	}


	@Override
	protected void onStart() {
		super.onStart();
		fiveRocksHelper.onActivityStart(thisActivity);
		userHabitHelper.activityStart(thisActivity);
		flurryHelper.onStartSession(thisActivity);
	}


	@Override
	protected void onStop() {
		super.onStop();
		fiveRocksHelper.onActivityStop(thisActivity);
		userHabitHelper.activityStop(thisActivity);
		flurryHelper.onEndSession(thisActivity);
	}


	public void Log(Activity activity, Object... params){
		if(MoGlobalVariable.DEBUG_MODE){
			Log.e("ERROR", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Log.e("ERROR", "[ "+activity.getClass().getName() + " ]");
			for(Object str : params) {
				if (str == null) {
					Log.e("ERROR", "null");
					continue;
				}
				Log.e("ERROR", str.toString());
			}
			Log.e("ERROR", "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
}
