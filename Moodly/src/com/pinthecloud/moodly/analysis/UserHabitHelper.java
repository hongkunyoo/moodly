package com.pinthecloud.moodly.analysis;

import io.userhabit.service.Userhabit;
import android.app.Activity;

import com.pinthecloud.moodly.MoGlobalVariable;

public class UserHabitHelper {

	private final String USER_HABIT_KEY = "a36335b6e9587949219d3f9e4b9ac9d7fd242d0c";


	public void activityStart(Activity activity){
		if(!MoGlobalVariable.DEBUG_MODE){
			Userhabit.activityStart(activity, USER_HABIT_KEY);
		}
	}


	public void activityStop(Activity activity){
		if(!MoGlobalVariable.DEBUG_MODE){
			Userhabit.activityStop(activity);
		}
	}
}
