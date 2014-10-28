package com.pinthecloud.moodly.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.pinthecloud.moodly.exception.ExceptionManager;
import com.pinthecloud.moodly.exception.MoException;
import com.pinthecloud.moodly.fragment.MoFragment;
import com.pinthecloud.moodly.interfaces.MoEntityCallback;
import com.pinthecloud.moodly.util.AsyncChainer;

public class BlobStorageHelper {

	private static final String storageConnectionString = 
			"DefaultEndpointsProtocol=http;AccountName=athere;AccountKey=ldhgydlWndSIl7XfiaAQ+sibsNtVZ1Psebba1RpBKxMbyFVYUCMvvuQir0Ty7f0+8TnNLfFKc9yFlYpP6ZSuQQ==";

	public static final String PERFORM_POSTER = "performposter";

	protected CloudBlobClient blobClient;


	public BlobStorageHelper() {
		CloudStorageAccount account = null;
		try {
			account = CloudStorageAccount.parse(storageConnectionString);
		} catch (InvalidKeyException e) {
			ExceptionManager.fireException(new MoException(null, "BlobStorageHelper", MoException.TYPE.BLOB_STORAGE_ERROR));
		} catch (URISyntaxException e) {
			ExceptionManager.fireException(new MoException(null, "BlobStorageHelper", MoException.TYPE.BLOB_STORAGE_ERROR));
		}

		// Create a blob service client
		blobClient = account.createCloudBlobClient();
	}


	public String uploadBitmapSync(final MoFragment frag, String containerName, String id, Bitmap bitmap) {
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		try {
			container = blobClient.getContainerReference(containerName);
			blob = container.getBlockBlobReference(id);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
			blob.upload(new ByteArrayInputStream(baos.toByteArray()), baos.size());
			baos.close();
		} catch (URISyntaxException e) {
			ExceptionManager.fireException(new MoException(frag, "uploadBitmapSync", MoException.TYPE.BLOB_STORAGE_ERROR));
		} catch (StorageException e) {
			ExceptionManager.fireException(new MoException(frag, "uploadBitmapSync", MoException.TYPE.BLOB_STORAGE_ERROR));
		} catch (IOException e) {
			ExceptionManager.fireException(new MoException(frag, "uploadBitmapSync", MoException.TYPE.BLOB_STORAGE_ERROR));
		}
		return id;
	}


	public Bitmap downloadBitmapSync(final MoFragment frag, String containerName, String id) {
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		Bitmap bm = null;
		try {
			container = blobClient.getContainerReference(containerName);
			blob = container.getBlockBlobReference(id);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			blob.download(baos);
			bm = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
		} catch (URISyntaxException e) {
			// Do nothing
		} catch (StorageException e) {
			// Do noghing
		}
		return bm;
	}


	public String downloadToFileSync(final MoFragment frag, String containerName, String id, String path) {
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		try {
			container = blobClient.getContainerReference(containerName);
			blob = container.getBlockBlobReference(id);
			blob.downloadToFile(frag.getActivity().getFilesDir() + "/" + path);
		} catch (URISyntaxException e) {
			ExceptionManager.fireException(new MoException(frag, "downloadToFileSync", MoException.TYPE.BLOB_STORAGE_ERROR));
		} catch (StorageException e) {
			ExceptionManager.fireException(new MoException(frag, "downloadToFileSync", MoException.TYPE.BLOB_STORAGE_ERROR));
		} catch (IOException e) {
			ExceptionManager.fireException(new MoException(frag, "downloadToFileSync", MoException.TYPE.BLOB_STORAGE_ERROR));
		}
		return frag.getActivity().getFilesDir() + "/" + path;
	}


	public boolean deleteBitmapSync(final MoFragment frag, String containerName, String id) {
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		try {
			container = blobClient.getContainerReference(containerName);
			blob = container.getBlockBlobReference(id);
			blob.delete();
		} catch (URISyntaxException e) {
			ExceptionManager.fireException(new MoException(frag, "deleteBitmapSync", MoException.TYPE.BLOB_STORAGE_ERROR));
		} catch (StorageException e) {
			return false;
		}
		return true;
	}


	public void uploadBitmapAsync(final MoFragment frag, final String containerName, String id, final Bitmap bitmap, final MoEntityCallback<String> callback) {
		(new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				String id = params[0];
				return uploadBitmapSync(frag, containerName, id, bitmap);
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (callback != null){
					callback.onCompleted(result);
				}
				AsyncChainer.notifyNext(frag);
			}
		}).execute(id);
	}


	public void downloadBitmapAsync(final MoFragment frag, final String containerName, String id, final MoEntityCallback<Bitmap> callback) {
		(new AsyncTask<String, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(String... params) {
				String id = params[0];
				return downloadBitmapSync(frag, containerName, id);
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				super.onPostExecute(result);
				if (callback != null){
					callback.onCompleted(result);
				}
				AsyncChainer.notifyNext(frag);
			}
		}).execute(id);
	}


	public void downloadToFileAsync(final MoFragment frag, final String containerName, String id, final String path, final MoEntityCallback<String> callback) {
		(new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				String id = params[0];
				return downloadToFileSync(frag, containerName, id, path);
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (callback != null){
					callback.onCompleted(result);
				}
				AsyncChainer.notifyNext(frag);
			}
		}).execute(id);
	}


	public void deleteBitmapAsync(final MoFragment frag, final String containerName, String id, final MoEntityCallback<Boolean> callback) {
		(new AsyncTask<String, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(String... params) {
				String id = params[0];
				return deleteBitmapSync(frag, containerName, id);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (callback != null){
					callback.onCompleted(result);
				}
				AsyncChainer.notifyNext(frag);
			}
		}).execute(id);
	}
}
