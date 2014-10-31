package com.pinthecloud.moodly.adapter;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.activity.MoActivity;
import com.pinthecloud.moodly.fragment.MoFragment;
import com.pinthecloud.moodly.model.Musician;

public class PerformVideoListAdapter extends RecyclerView.Adapter<PerformVideoListAdapter.ViewHolder> {

	private static final int RECOVERY_DIALOG_REQUEST = 1;
	private static final int REQ_START_STANDALONE_PLAYER = 1;
	private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

	private Context context;
	private MoActivity activity;
	private MoFragment frag;
	private List<Musician> musicianList;
	private Dialog errorDialog;


	// Provide a suitable constructor (depends on the kind of dataset)
	public PerformVideoListAdapter(Context context, MoFragment frag, List<Musician> musicianList) {
		this.context = context;
		this.activity = (MoActivity)context;
		this.frag = frag;
		this.musicianList = musicianList;
	}


	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View view;
		public TextView musicianName;
		public ProgressBar progressBar;
		public YouTubeThumbnailView thumbnailView;

		public ViewHolder(View view) {
			super(view);
			this.view = view;
			this.musicianName = (TextView)view.findViewById(R.id.row_perform_video_list_musician_name);
			this.progressBar = (ProgressBar)view.findViewById(R.id.row_perform_video_list_progress_bar);
			this.thumbnailView = (YouTubeThumbnailView)view.findViewById(R.id.row_perform_video_list_video_thumbnail);
		}
	}


	// Create new views (invoked by the layout manager)
	@Override
	public PerformVideoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_perform_video_list, parent, false);

		// set the view's size, margins, paddings and layout parameters

		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}


	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Musician musician = musicianList.get(position);
		setComponent(holder, musician);
	}


	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return this.musicianList.size();
	}


	private void setComponent(final ViewHolder holder, final Musician musician){
		holder.musicianName.setText(musician.getKorName());
		holder.thumbnailView.initialize(MoGlobalVariable.GOOGLE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {

			@Override
			public void onInitializationSuccess(YouTubeThumbnailView thumbnailView, 
					YouTubeThumbnailLoader thumbnailLoader) {
				thumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailListener(holder.progressBar));
				thumbnailLoader.setVideo(musician.getVideoLink());
			}

			@Override
			public void onInitializationFailure(YouTubeThumbnailView thumbnailView, 
					YouTubeInitializationResult errorReason) {
				if (errorReason.isUserRecoverableError()) {
					if (errorDialog == null || !errorDialog.isShowing()) {
						errorDialog = errorReason.getErrorDialog(activity, RECOVERY_DIALOG_REQUEST);
						errorDialog.show();
					}
				} else {
					String errorMessage =
							String.format(context.getString(R.string.error_thumbnail_view), errorReason.toString());
					Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
				}
			}
		});
	}


	private boolean canResolveIntent(Intent intent) {
		List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentActivities(intent, 0);
		return resolveInfo != null && !resolveInfo.isEmpty();
	}


	/**
	 * An internal listener which listens to thumbnail loading events from the
	 * {@link YouTubeThumbnailView}.
	 */
	private final class ThumbnailListener implements YouTubeThumbnailLoader.OnThumbnailLoadedListener {
		private ProgressBar progressBar;

		public ThumbnailListener(ProgressBar progressBar) {
			super();
			this.progressBar = progressBar;
		}

		@Override
		public void onThumbnailLoaded(YouTubeThumbnailView thumbnail, final String videoId) {
			progressBar.setVisibility(View.GONE);

			thumbnail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = YouTubeStandalonePlayer.createVideoIntent(
							activity, MoGlobalVariable.GOOGLE_API_KEY, videoId, 0, true, false);

					if (canResolveIntent(intent)) {
						frag.startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
					} else {
						// Could not resolve the intent - must need to install or update the YouTube API service.
						YouTubeInitializationResult.SERVICE_MISSING.getErrorDialog(
								activity, REQ_RESOLVE_SERVICE_MISSING).show();
					}
				}
			});
		}

		@Override
		public void onThumbnailError(YouTubeThumbnailView thumbnail,
				YouTubeThumbnailLoader.ErrorReason reason) {
			// Do nothing
		}
	}
}
