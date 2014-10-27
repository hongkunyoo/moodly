package com.pinthecloud.moodly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinthecloud.moodly.MoApplication;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.fragment.MoFragment;
import com.pinthecloud.moodly.helper.BlobStorageHelper;
import com.pinthecloud.moodly.helper.CachedBlobStorageHelper;
import com.pinthecloud.moodly.model.Perform;

public class PerformListAdapter extends ArrayAdapter<Perform>{

	private Context context;
	private MoFragment frag;
	
	private TextView tag;
	private ImageView poster;
	private TextView performName;
	private TextView musician;
	private TextView beginDay;
	private TextView finishDay;
	private TextView theaterAddress;
	
	private CachedBlobStorageHelper blobStorageHelper;


	public PerformListAdapter(Context context, MoFragment frag) {
		super(context, 0);
		this.context = context;
		this.frag = frag;
		this.blobStorageHelper = MoApplication.getInstance().getBlobStorageHelper();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_perform_list, parent, false);
		}

		Perform perform = getItem(position);
		if (perform != null) {
			findComponent(view);
			setTextView(perform);
			blobStorageHelper.setImageViewAsync(frag, BlobStorageHelper.PERFORM_POSTER, 
					perform.getPosterUrl(), null, poster, false);
		}
		return view;
	}


	private void findComponent(View view){
		tag = (TextView)view.findViewById(R.id.row_perform_list_tag);
		poster = (ImageView)view.findViewById(R.id.row_perform_list_poster);
		performName = (TextView)view.findViewById(R.id.row_perform_list_perform_name);
		musician = (TextView)view.findViewById(R.id.row_perform_list_musician);
		beginDay = (TextView)view.findViewById(R.id.row_perform_list_begin_day);
		finishDay = (TextView)view.findViewById(R.id.row_perform_list_finish_day);
		theaterAddress = (TextView)view.findViewById(R.id.row_perform_list_theater_address);
	}


	private void setTextView(Perform perform){
		tag.setText(perform.getPerformName());
		performName.setText(perform.getPerformName());
		musician.setText(perform.getPerformName());
		beginDay.setText(perform.getBeginDay());
		finishDay.setText(perform.getFinishDay());
		theaterAddress.setText(perform.getTheaterAddress());
	}
}
