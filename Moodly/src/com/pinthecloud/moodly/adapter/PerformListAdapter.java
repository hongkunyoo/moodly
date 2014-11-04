package com.pinthecloud.moodly.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinthecloud.moodly.MoApplication;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.activity.MoActivity;
import com.pinthecloud.moodly.activity.PerformActivity;
import com.pinthecloud.moodly.fragment.MoFragment;
import com.pinthecloud.moodly.helper.CachedBlobStorageHelper;
import com.pinthecloud.moodly.model.Perform;
import com.pinthecloud.moodly.util.ActivityAnimator;

public class PerformListAdapter extends RecyclerView.Adapter<PerformListAdapter.ViewHolder> {

	private Context context;
	private MoActivity activity;
	private MoFragment frag;
	private List<Perform> performList;

	private CachedBlobStorageHelper blobStorageHelper;


	public PerformListAdapter(Context context, MoFragment frag, List<Perform> performList) {
		this.context = context;
		this.activity = (MoActivity)context;
		this.frag = frag;
		this.performList = performList;
		this.blobStorageHelper = MoApplication.getInstance().getBlobStorageHelper();
	}


	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View view;
		public TextView tag;
		public ImageView poster;
		public TextView performName;
		public TextView theaterAddress;

		public ViewHolder(View view) {
			super(view);
			this.view = view;
			this.tag = (TextView)view.findViewById(R.id.row_perform_list_tag);
			this.poster = (ImageView)view.findViewById(R.id.row_perform_list_poster);
			this.performName = (TextView)view.findViewById(R.id.row_perform_list_perform_name);
			this.theaterAddress = (TextView)view.findViewById(R.id.row_perform_list_theater_address);
		}
	}


	@Override
	public PerformListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_perform_list, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}


	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Perform perform = performList.get(position);
		setTextView(holder, perform);
		//		blobStorageHelper.setImageViewAsync(frag, BlobStorageHelper.PERFORM_POSTER, 
		//				perform.getPosterUrl(), null, holder.poster, false);
		setClickEvent(holder, perform);
	}


	@Override
	public int getItemCount() {
		return this.performList.size();
	}


	private void setTextView(final ViewHolder holder, final Perform perform){
//		List<String> moodList = perform.getMood();
//		String mood = null;
//		for(int i=0 ; i<moodList.size() ; i++){
//			mood += moodList.get(i);
//		}
//		holder.tag.setText(mood);
		holder.performName.setText(perform.getPerformName());
		holder.theaterAddress.setText(perform.getPlaceAddress02());
	}


	private void setClickEvent(final ViewHolder holder, final Perform perform){
		holder.view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PerformActivity.class);
				intent.putExtra(MoGlobalVariable.PERFORM_KEY, perform);
				context.startActivity(intent);
				ActivityAnimator.PullRightPushLeft(activity);
			}
		});
		holder.poster.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Show big poster
			}
		});
	}
}
