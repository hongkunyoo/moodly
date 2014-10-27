package com.pinthecloud.moodly.helper;

import java.util.List;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
import com.pinthecloud.moodly.MoApplication;
import com.pinthecloud.moodly.exception.ExceptionManager;
import com.pinthecloud.moodly.exception.MoException;
import com.pinthecloud.moodly.fragment.MoFragment;
import com.pinthecloud.moodly.interfaces.MoEntityCallback;
import com.pinthecloud.moodly.interfaces.MoListCallback;
import com.pinthecloud.moodly.model.Perform;
import com.pinthecloud.moodly.util.AsyncChainer;

public class PerformHelper {

	private MoApplication app;

	private MobileServiceTable<Perform> performTable;
	private MobileServiceClient mClient;


	public PerformHelper() {
		super();
		this.app = MoApplication.getInstance();
		this.mClient = app.getmClient();
		this.performTable = mClient.getTable(Perform.class);
	}


	/*
	 * Methods
	 */
	public void addPerformAsync(final MoFragment frag, Perform perform, final MoEntityCallback<Perform> callback) throws MoException {
		if (!app.isOnline()) {
			ExceptionManager.fireException(new MoException(frag, "addPerformAsync", MoException.TYPE.INTERNET_NOT_CONNECTED));
			return;
		}

		performTable.insert(perform, new TableOperationCallback<Perform>() {

			@Override
			public void onCompleted(Perform entity, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
					if (callback != null){
						callback.onCompleted(entity);
					}
					AsyncChainer.notifyNext(frag);
				} else {
					ExceptionManager.fireException(new MoException(frag, "addPerformAsync", MoException.TYPE.SERVER_ERROR));
				}
			}
		});
	}


	public void getPerformAsync(final MoFragment frag, String id, final MoEntityCallback<Perform> callback) {
		if (!app.isOnline()) {
			ExceptionManager.fireException(new MoException(frag, "getPerformAsync", MoException.TYPE.INTERNET_NOT_CONNECTED));
			return;
		}

		performTable.where().field("id").eq(id).execute(new TableQueryCallback<Perform>() {

			@Override
			public void onCompleted(List<Perform> result, int count, Exception exception,
					ServiceFilterResponse reponse) {
				if (exception == null) {
					if (callback != null){
						callback.onCompleted(result.get(0));
					}
					AsyncChainer.notifyNext(frag);
				} else {
					ExceptionManager.fireException(new MoException(frag, "getPerformAsync", MoException.TYPE.SERVER_ERROR));
				}
			}
		});
	}


	public void updatePerformAsync(final MoFragment frag, Perform perform, final MoEntityCallback<Perform> callback){
		if (!app.isOnline()) {
			ExceptionManager.fireException(new MoException(frag, "updatePerformAsync", MoException.TYPE.INTERNET_NOT_CONNECTED));
			return;
		}

		performTable.update(perform, new TableOperationCallback<Perform>() {

			@Override
			public void onCompleted(Perform entity, Exception exception,
					ServiceFilterResponse response) {
				if (exception == null) {
					if (callback != null){
						callback.onCompleted(entity);
					}
					AsyncChainer.notifyNext(frag);
				} else {
					ExceptionManager.fireException(new MoException(frag, "updatePerformAsync", MoException.TYPE.SERVER_ERROR));
				}
			}
		});
	}


	public void getAllPerformListAsync(final MoFragment frag, final MoListCallback<Perform> callback) throws MoException{
		if (!app.isOnline()) {
			ExceptionManager.fireException(new MoException(frag, "getAllPerformListAsync", MoException.TYPE.INTERNET_NOT_CONNECTED));
			return;
		}

		performTable.where().execute(new TableQueryCallback<Perform>() {

			@Override
			public void onCompleted(List<Perform> result, int count, Exception exception,
					ServiceFilterResponse reponse) {
				if (exception == null) {
					if (callback != null){
						callback.onCompleted(result, result.size());
					}
					AsyncChainer.notifyNext(frag);
				} else {
					ExceptionManager.fireException(new MoException(frag, "getPerformListAsync", MoException.TYPE.SERVER_ERROR));
				}
			}
		});
	}
}
