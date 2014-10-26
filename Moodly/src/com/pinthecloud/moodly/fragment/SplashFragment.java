package com.pinthecloud.moodly.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.activity.GuideActivity;
import com.pinthecloud.moodly.activity.PerformListActivity;
import com.pinthecloud.moodly.dialog.MoAlertDialog;
import com.pinthecloud.moodly.exception.MoException;
import com.pinthecloud.moodly.interfaces.MoDialogCallback;

public class SplashFragment extends MoFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_splash, container, false);
		removeNotification();
		runApp();
		return view;
	}


	@Override
	public void handleException(MoException ex) {
		if (ex.getType().equals(MoException.TYPE.GCM_REGISTRATION_FAIL)) {
			String message = getResources().getString(R.string.google_play_services_not_existed_message);
			MoAlertDialog exceptionDialog = new MoAlertDialog(null, message, null, null, true, new MoDialogCallback() {

				@Override
				public void doPositiveThing(Bundle bundle) {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("market://details?id=" + MoGlobalVariable.GOOGLE_PLAY_SERVICE_APP_ID));
					startActivity(intent);
					activity.finish();
				}
				@Override
				public void doNegativeThing(Bundle bundle) {
					activity.finish();
				}
			});
			exceptionDialog.show(getFragmentManager(), MoGlobalVariable.DIALOG_KEY);
		} else{
			super.handleException(ex);
		}
	}


	private void removeNotification(){
		NotificationManager mNotificationManager = 
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(1);
	}


	private void runApp() {
		goToNextActivity();
	}


	private void goToNextActivity() {
		if(thisFragment.isAdded()){
			Intent intent = new Intent();
			if (!userHelper.isLoggedInUser()){
				intent.setClass(context, GuideActivity.class);
			} else{
				intent.setClass(context, PerformListActivity.class);
			}
			startActivity(intent);
		}
	}
}
