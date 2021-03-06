package com.pinthecloud.moodly.helper;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.pinthecloud.moodly.MoApplication;
import com.pinthecloud.moodly.fragment.MoFragment;
import com.pinthecloud.moodly.util.AsyncChainer;
import com.pinthecloud.moodly.util.FileUtil;

public class CachedBlobStorageHelper extends BlobStorageHelper {

	private MoApplication app;
	private LruCache<String, Bitmap> mMemoryCache;


	public CachedBlobStorageHelper() {
		super();
		this.app = MoApplication.getInstance();

		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return bitmap.getByteCount() / 1024;
			}
		};
	}


	public void setImageViewAsync(MoFragment frag, String containerName, String id, int placeHolderId, ImageView imageView, boolean isSave) {
		Bitmap placeHolder = null;
		if(placeHolderId != 0){
			placeHolder = BitmapFactory.decodeResource(frag.getResources(), placeHolderId);
		}
		setImageViewAsync(frag, containerName, id, placeHolder, imageView, isSave);
	}


	public void setImageViewAsync(MoFragment frag, String containerName, String id, Bitmap placeHolder, ImageView imageView, boolean isSave) {
		if (cancelPotentialWork(id, imageView)) {
			// Check from cache
			Bitmap bitmap = getBitmapFromMemCache(id);
			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
				AsyncChainer.notifyNext(frag);
				return;
			}

			// Check from Disk
			bitmap = FileUtil.getBitmapFromInternalStorage(frag.getActivity(), id);
			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
				addBitmapToMemoryCache(id, bitmap);
				AsyncChainer.notifyNext(frag);
				return;
			}

			BitmapWorkerTask task = new BitmapWorkerTask(frag, imageView, isSave);
			AsyncDrawable asyncDrawable = new AsyncDrawable(frag.getResources(), placeHolder, task);
			imageView.setImageDrawable(asyncDrawable);
			task.execute(containerName, id);
		} else{
			AsyncChainer.notifyNext(frag);
		}
	}


	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private MoFragment frag;
		private WeakReference<ImageView> imageViewReference;
		private String id = null;
		private boolean isSave;

		public BitmapWorkerTask(MoFragment frag, ImageView imageView, boolean isSave) {
			// Use a WeakReference to ensure the ImageView can be garbage collected
			this.frag = frag;
			this.imageViewReference = new WeakReference<ImageView>(imageView);
			this.isSave = isSave;
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(String... params) {
			String containerName = params[0];
			this.id = params[1];

			Bitmap bitmap = downloadBitmapSync(frag, containerName, id);
			if (bitmap == null) return null;

			if(isSave){
				FileUtil.saveBitmapToInternalStorage(app, id, bitmap);
			}
			addBitmapToMemoryCache(id, bitmap);

			return bitmap;
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}
			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
				if (this == bitmapWorkerTask && imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
			AsyncChainer.notifyNext(frag);
		}
	}


	private class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
			super(res, bitmap);
			bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}
	}


	private boolean cancelPotentialWork(String id, ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			String bitmapData = bitmapWorkerTask.id;
			// If bitmapData is not yet set or it differs from the new data
			if (bitmapData == null || !bitmapData.equals(id)) {
				// Cancel previous task
				bitmapWorkerTask.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was cancelled
		return true;
	}


	private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}


	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (key == null || bitmap == null) return;
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}


	private Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	public void clearCache(String id) {
		mMemoryCache.remove(id);
	}

	public void clearAllCache() {
		mMemoryCache.evictAll();
	}
}
