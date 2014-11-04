package com.pinthecloud.moodly.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.pinthecloud.moodly.MoApplication;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.analysis.UserHabitHelper;

public class MoActivity extends ActionBarActivity{

	protected MoApplication app;
	protected MoActivity thisActivity;
	protected UserHabitHelper userHabitHelper;


	public MoActivity(){
		thisActivity = this;
		app = MoApplication.getInstance();
		userHabitHelper = app.getUserHabitHelper();
	}


	@Override
	protected void onStart() {
		super.onStart();
		userHabitHelper.activityStart(thisActivity);
	}


	@Override
	protected void onStop() {
		super.onStop();
		userHabitHelper.activityStop(thisActivity);
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
