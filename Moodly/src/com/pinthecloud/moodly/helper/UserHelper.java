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
import com.pinthecloud.moodly.model.MoUser;
import com.pinthecloud.moodly.util.AsyncChainer;

public class UserHelper {

	private final String USER_ID_KEY = "USER_ID_KEY";
	private final String REGISTRATION_ID_KEY = "REGISTRATION_ID_KEY";
	private final String MO_ID_KEY = "MO_ID_KEY";
	private final String NICK_NAME_KEY = "NICK_NAME_KEY";
	private final String IS_LOGGED_IN_USER_KEY = "IS_LOGGED_IN_USER_KEY";

	private MoApplication app;
	private PreferenceHelper pref;

	private MobileServiceTable<MoUser> userTable;
	private MobileServiceClient mClient;


	public UserHelper() {
		super();
		this.app = MoApplication.getInstance();
		this.pref = PreferenceHelper.getInstance();
		this.mClient = app.getmClient();
		this.userTable = mClient.getTable(MoUser.class);
	}


	/*
	 * Preference
	 */
	public boolean isLoggedInUser() {
		return pref.getBoolean(IS_LOGGED_IN_USER_KEY);
	}
	public UserHelper setLoggedInUser(boolean loggedIn) {
		pref.putBoolean(IS_LOGGED_IN_USER_KEY, loggedIn);
		return this;
	}
	public UserHelper setMyId(String id) {
		pref.putString(USER_ID_KEY, id);
		return this;
	}
	public boolean hasRegistrationId() {
		return !pref.getString(REGISTRATION_ID_KEY).equals(PreferenceHelper.DEFAULT_STRING);
	}
	public UserHelper setMyRegistrationId(String id) {
		pref.putString(REGISTRATION_ID_KEY, id);
		return this;
	}
	public UserHelper setMyMoId(String moId) {
		pref.putString(MO_ID_KEY, moId);
		return this;
	}
	public UserHelper setMyNickName(String nickName) {
		pref.putString(NICK_NAME_KEY, nickName);
		return this;
	}


	/*
	 * Methods
	 */
	public void addUserAsync(final MoFragment frag, MoUser user, final MoEntityCallback<MoUser> callback) throws MoException {
		if (!app.isOnline()) {
			ExceptionManager.fireException(new MoException(frag, "addUserAsync", MoException.TYPE.INTERNET_NOT_CONNECTED));
			return;
		}

		userTable.insert(user, new TableOperationCallback<MoUser>() {

			@Override
			public void onCompleted(MoUser entity, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
					if (callback != null){
						callback.onCompleted(entity);
					}
					AsyncChainer.notifyNext(frag);
				} else {
					if (response.getContent().contains(MoException.TYPE.DUPLICATED_NICK_NAME.toString())) { 
						ExceptionManager.fireException(new MoException(frag, "addUserAsync", MoException.TYPE.DUPLICATED_NICK_NAME));
					} else {
						ExceptionManager.fireException(new MoException(frag, "addUserAsync", MoException.TYPE.SERVER_ERROR));
					}
				}
			}
		});
	}


	public void getUserAsync(final MoFragment frag, String id, final MoEntityCallback<MoUser> callback) {
		if (!app.isOnline()) {
			ExceptionManager.fireException(new MoException(frag, "getUserAsync", MoException.TYPE.INTERNET_NOT_CONNECTED));
			return;
		}

		userTable.where().field("id").eq(id).execute(new TableQueryCallback<MoUser>() {

			@Override
			public void onCompleted(List<MoUser> result, int count, Exception exception,
					ServiceFilterResponse reponse) {
				if (exception == null) {
					if (callback != null){
						callback.onCompleted(result.get(0));
					}
					AsyncChainer.notifyNext(frag);
				} else {
					ExceptionManager.fireException(new MoException(frag, "getUserAsync", MoException.TYPE.SERVER_ERROR));
				}
			}
		});
	}

	public void updateUserAsync(final MoFragment frag, MoUser user, final MoEntityCallback<MoUser> callback){
		if (!app.isOnline()) {
			ExceptionManager.fireException(new MoException(frag, "updateUserAsync", MoException.TYPE.INTERNET_NOT_CONNECTED));
			return;
		}

		userTable.update(user, new TableOperationCallback<MoUser>() {

			@Override
			public void onCompleted(MoUser entity, Exception exception,
					ServiceFilterResponse response) {
				if (exception == null) {
					if (callback != null){
						callback.onCompleted(entity);
					}
					AsyncChainer.notifyNext(frag);
				} else {
					ExceptionManager.fireException(new MoException(frag, "updateUserAsync", MoException.TYPE.SERVER_ERROR));
				}
			}
		});
	}


	public MoUser getMyUserInfo() {
		String id = pref.getString(USER_ID_KEY);
		if(id.equals(PreferenceHelper.DEFAULT_STRING)){
			id = null;
		}
		String registrationId = pref.getString(REGISTRATION_ID_KEY);
		String moId = pref.getString(MO_ID_KEY);
		String nickName = pref.getString(NICK_NAME_KEY);

		MoUser user = new MoUser();
		user.setId(id);
		user.setRegistrationId(registrationId);
		user.setMoId(moId);
		user.setNickName(nickName);
		return user;
	}

	public void removeMyUserInfo() {
		pref.removePref(MO_ID_KEY);
		pref.removePref(NICK_NAME_KEY);
		pref.removePref(IS_LOGGED_IN_USER_KEY);
	}

	public boolean isMyUser(MoUser user){
		return user.getId().equals(getMyUserInfo().getId());
	}
}
