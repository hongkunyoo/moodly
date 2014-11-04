package com.pinthecloud.moodly;

import java.net.MalformedURLException;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.pinthecloud.moodly.analysis.UserHabitHelper;
import com.pinthecloud.moodly.helper.CachedBlobStorageHelper;
import com.pinthecloud.moodly.helper.PerformHelper;
import com.pinthecloud.moodly.helper.UserHelper;

public class MoApplication extends Application{

	// Windows Azure Mobile Service Keys
	private final String AZURE_REAL_URL = "https://moodly.azure-mobile.net/";
	private final String AZURE_REAL_KEY = "haStEkEsxGUsGczeXMLzStdxnRyaci74";
	private final String AZURE_TEST_URL = "https://moodlytest.azure-mobile.net/";
	private final String AZURE_TEST_KEY = "XnPuEkPaETzSGmhFGRaHknfgLiWppH81";

	// Application
	private static MoApplication app;

	// Mobile Service instances
	private static MobileServiceClient mClient;

	// Helper
	private static UserHelper userHelper;
	private static PerformHelper performHelper;
	private static CachedBlobStorageHelper blobStorageHelper;

	// Analysis
	private static UserHabitHelper userHabitHelper;


	@Override
	public void onCreate() {
		super.onCreate();
		app = this;

		String AZURE_URL = null;
		String AZURE_KEY = null;
		if (MoGlobalVariable.DEBUG_MODE) {
			AZURE_URL = AZURE_TEST_URL;
			AZURE_KEY = AZURE_TEST_KEY;
		} else {
			AZURE_URL = AZURE_REAL_URL;
			AZURE_KEY = AZURE_REAL_KEY;
		}


		try {
			mClient = new MobileServiceClient(AZURE_URL, AZURE_KEY, this);
		} catch (MalformedURLException e) {
			// Do nothing
		}

		userHelper = new UserHelper();
		performHelper = new PerformHelper();
		blobStorageHelper = new CachedBlobStorageHelper();

		userHabitHelper = new UserHabitHelper();
	}


	public static MoApplication getInstance(){
		return app;
	}
	public MobileServiceClient getmClient() {
		return mClient;
	}
	public UserHelper getUserHelper() {
		return userHelper;
	}
	public PerformHelper getPerformHelper() {
		return performHelper;
	}
	public CachedBlobStorageHelper getBlobStorageHelper() {
		return blobStorageHelper;
	}
	public UserHabitHelper getUserHabitHelper() {
		return userHabitHelper;
	}


	public boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager)app.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
	}
}
