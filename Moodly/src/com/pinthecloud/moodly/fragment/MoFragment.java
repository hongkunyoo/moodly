package com.pinthecloud.moodly.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.pinthecloud.moodly.MoApplication;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.activity.MoActivity;
import com.pinthecloud.moodly.dialog.MoAlertDialog;
import com.pinthecloud.moodly.exception.ExceptionManager;
import com.pinthecloud.moodly.exception.MoException;
import com.pinthecloud.moodly.helper.PerformHelper;
import com.pinthecloud.moodly.helper.UserHelper;
import com.pinthecloud.moodly.interfaces.MoDialogCallback;

public class MoFragment extends Fragment implements ExceptionManager.Handler{

	protected MoApplication app;
	protected MoActivity activity;
	protected MoFragment thisFragment;

	protected UserHelper userHelper;
	protected PerformHelper performHelper;


	public MoFragment(){
		app = MoApplication.getInstance();
		thisFragment = this;
		userHelper = app.getUserHelper();
		performHelper = app.getPerformHelper();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (MoActivity) getActivity();
		ExceptionManager.setHandler(thisFragment);
	}


	@Override
	public void handleException(final MoException ex) {
		String title = null;
		String message = null;
		if(ex.getType().equals(MoException.TYPE.INTERNET_NOT_CONNECTED)){
			title = null;
			message = getResources().getString(R.string.internet_not_connected_message);
		} else{
			title = ex.getType().toString();
			message = ex.toString();
		}

		MoAlertDialog exceptionDialog = new MoAlertDialog(title, message, null, null, false, new MoDialogCallback() {
			@Override
			public void doPositiveThing(Bundle bundle) {
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			}
			@Override
			public void doNegativeThing(Bundle bundle) {
				// Do nothing
			}
		}); 
		exceptionDialog.show(getFragmentManager(), MoGlobalVariable.DIALOG_KEY);
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
