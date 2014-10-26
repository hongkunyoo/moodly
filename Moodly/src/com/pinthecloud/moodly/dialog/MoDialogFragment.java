package com.pinthecloud.moodly.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.activity.MoActivity;
import com.pinthecloud.moodly.fragment.MoFragment;

public class MoDialogFragment extends DialogFragment {

	protected MoDialogFragment thisFragment;
	protected Context context;
	protected MoActivity activity;
	protected String simpleClassName;
	private boolean isShowing = false;


	public MoDialogFragment() {
		super();
		this.thisFragment = this;
		this.simpleClassName =  thisFragment.getClass().getSimpleName();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = getActivity();
		activity = (MoActivity) context;
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onStart() {
		super.onStart();
	}


	@Override
	public void onStop() {
		super.onStop();
	}


	@Override
	public void show(FragmentManager manager, String tag) {
		if(isShowing){
			return;
		}else{
			super.show(manager, tag);
			isShowing = true;
		}
	}


	@Override
	public void onDismiss(DialogInterface dialog) {
		isShowing = false;
		super.onDismiss(dialog);
	}


	public boolean isShowing(){
		return isShowing;
	}


	public void Log(MoFragment fragment, Object... params){
		if(MoGlobalVariable.DEBUG_MODE){
			Log.e("ERROR", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Log.e("ERROR", "[ "+fragment.getClass().getName() + " ]");
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
