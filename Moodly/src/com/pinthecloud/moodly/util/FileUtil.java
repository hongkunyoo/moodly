package com.pinthecloud.moodly.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;

import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.exception.MoException;

public class FileUtil {

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	public static final int MEDIA_TYPE_GALLERY = 1;
	public static final int MEDIA_TYPE_CAMERA = 2;


	// Create a file Uri for saving an image or video
	public static Uri getOutputMediaFileUri(int type){
		return Uri.fromFile(getOutputMediaFile(type));
	}


	// Create a File for saving an image or video
	public static File getOutputMediaFile(int type){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), MoGlobalVariable.APP_NAME);

		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.
		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()){
			if (!mediaStorageDir.mkdirs()){
				return null;
			}
		}

		// Create a media file name
		//		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		Time time = new Time();
		time.setToNow();
		String timeStamp = time.format("%Y%m%d_%H%M%S");
		File mediaFile = null;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_"+ timeStamp + ".jpg");
		} else if(type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"VID_"+ timeStamp + ".mp4");
		} else {
			return null;
		}
		return mediaFile;
	}


	public static void clearFile(Context context, String name) {
		context.deleteFile(name);
	}


	public static void clearAllFilesExceptSomeFiles(Context context, String[] names) {
		File dir = context.getFilesDir();
		if(!dir.isDirectory()){
			throw new MoException("File remove Error");
		}

		for (File file : dir.listFiles()) {
			if(!file.isFile()){
				throw new MoException("File remove Error");
			}

			if(names != null && Arrays.asList(names).contains(file.getName())){
				continue;
			}

			if (!file.delete()) {
				throw new MoException("File remove Error");
			}
		}
	}


	public static File saveBitmapToFile(Context context, Uri uri, Bitmap bitmap){
		File file = null;
		try {
			file = new File(uri.getPath());
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			throw new MoException("FileNotFoundException");
		} catch (IOException e) {
			throw new MoException("IOException");
		}
		return file;
	}


	public static void saveBitmapToInternalStorage(Context context, String name, Bitmap image) {
		try {
			// Use the compress method on the Bitmap object to write image to the OutputStream
			// Writing the bitmap to the output stream
			FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
			image.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			throw new MoException("FileNotFoundException");
		} catch (IOException e) {
			throw new MoException("IOException");
		}
	}


	public static Uri getLastCaptureBitmapUri(Context context){
		Uri uri =null;
		String[] IMAGE_PROJECTION = {
				MediaStore.Images.ImageColumns.DATA, 
				MediaStore.Images.ImageColumns._ID,
		};
		Cursor cursorImages = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_PROJECTION, null, null,null);
		if (cursorImages != null && cursorImages.moveToLast()) {
			uri = Uri.parse(cursorImages.getString(0)); //경로
			cursorImages.close(); // 커서 사용이 끝나면 꼭 닫아준다.
		}
		return uri;  
	}


	public static Bitmap getBitmapFromInternalStorage(Context context, String fileName) {
		if (context == null) {
			return null;
		}
		return BitmapFactory.decodeFile(context.getFilesDir() + "/" + fileName);
	}
}
