package com.pinthecloud.moodly.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.common.collect.Lists;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.model.Musician;
import com.pinthecloud.moodly.model.Perform;

public class PerformFragment extends MoFragment{

	private static final int RECOVERY_DIALOG_REQUEST = 1;
	private static final int REQ_START_STANDALONE_PLAYER = 1;
	private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
	private Dialog errorDialog;

	private Perform perform;

	private LinearLayout videoListLayout;
	private TextView performName;
	private TextView placeName;
	private TextView placeAddress01;
	private TextView placeAddress02;
	private TextView price;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = activity.getIntent();
		perform = intent.getParcelableExtra(MoGlobalVariable.PERFORM_KEY);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_perform, container, false);
		setActionBar();
		findComponent(view);
		setTextView(perform);
		setMusicianVideoList(perform.getLineup());
		return view;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != Activity.RESULT_OK) {
			YouTubeInitializationResult errorReason = YouTubeStandalonePlayer.getReturnedInitializationResult(data);
			if (errorReason.isUserRecoverableError()) {
				errorReason.getErrorDialog(activity, 0).show();
			} else {
				String errorMessage = String.format(getString(R.string.youtube_player_error_message), errorReason.toString());
				Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
			}
		}
	}


	private void setActionBar(){
		ActionBar actionBar = activity.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(perform.getPerformName());
	}


	private void findComponent(View view){
		videoListLayout = (LinearLayout)view.findViewById(R.id.perform_frag_video_list_layout);
		performName = (TextView)view.findViewById(R.id.perform_frag_perform_name);
		placeName = (TextView)view.findViewById(R.id.perform_frag_theater_name);
		placeAddress02 = (TextView)view.findViewById(R.id.perform_frag_theater_address);
		placeAddress01 = (TextView)view.findViewById(R.id.perform_frag_theater_city);
		price = (TextView)view.findViewById(R.id.perform_frag_price);
	}


	private void setTextView(Perform perform){
		performName.setText(perform.getPerformName());
		placeName.setText(perform.getPlaceName());
		placeAddress02.setText(perform.getPlaceAddress02());
		placeAddress01.setText(perform.getPlaceAddress01());
		price.setText(String.valueOf(perform.getPrice()));
	}


	private void setMusicianVideoList(List<Musician> lineup){
		// Mock data
		lineup = Lists.newArrayList();

		Musician m = new Musician();
		m.setKorName("S White");
		m.setVideoLink("QuNOnQJEDGg");
		lineup.add(m);

		m = new Musician();
		m.setKorName("구글");
		m.setVideoLink("nCgQDjiotG0");
		lineup.add(m);

		m = new Musician();
		m.setKorName("구글");
		m.setVideoLink("nCgQDjiotG0");
		lineup.add(m);

		m = new Musician();
		m.setKorName("구글");
		m.setVideoLink("nCgQDjiotG0");
		lineup.add(m);

		m = new Musician();
		m.setKorName("구글");
		m.setVideoLink("nCgQDjiotG0");
		lineup.add(m);

		m = new Musician();
		m.setKorName("구글");
		m.setVideoLink("nCgQDjiotG0");
		lineup.add(m);

		m = new Musician();
		m.setKorName("구글");
		m.setVideoLink("nCgQDjiotG0");
		lineup.add(m);

		m = new Musician();
		m.setKorName("구글");
		m.setVideoLink("nCgQDjiotG0");
		lineup.add(m);


		// setMusicianVideoList
		for(int i=0 ; i<lineup.size() ; i++){
			ViewHolder viewHolder = onCreateViewHolder(videoListLayout);
			onBindViewHolder(viewHolder, lineup.get(i));
			videoListLayout.addView(viewHolder.view);
		}
	}


	public class ViewHolder extends View {
		public View view;
		public TextView musicianName;
		public ProgressBar progressBar;
		public YouTubeThumbnailView thumbnailView;

		public ViewHolder(View view) {
			super(context);
			this.view = view;
			this.musicianName = (TextView)view.findViewById(R.id.row_perform_video_list_musician_name);
			this.progressBar = (ProgressBar)view.findViewById(R.id.row_perform_video_list_progress_bar);
			this.thumbnailView = (YouTubeThumbnailView)view.findViewById(R.id.row_perform_video_list_video_thumbnail);
		}
	}


	public ViewHolder onCreateViewHolder(ViewGroup parent) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_perform_video_list, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}


	public void onBindViewHolder(final ViewHolder holder, final Musician musician) {
		holder.musicianName.setText(musician.getKorName());
		holder.thumbnailView.setTag(musician.getVideoLink());
		holder.thumbnailView.initialize(MoGlobalVariable.GOOGLE_API_KEY, new ThumbnailInitializedListener(holder));
	}


	private final class ThumbnailInitializedListener implements YouTubeThumbnailView.OnInitializedListener {
		private ViewHolder holder;

		public ThumbnailInitializedListener(ViewHolder holder) {
			super();
			this.holder = holder;
		}

		@Override
		public void onInitializationSuccess(YouTubeThumbnailView thumbnailView, YouTubeThumbnailLoader thumbnailLoader) {
			thumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailLoadedListener(holder.progressBar));
			thumbnailLoader.setVideo(thumbnailView.getTag().toString());
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
	}


	private final class ThumbnailLoadedListener implements YouTubeThumbnailLoader.OnThumbnailLoadedListener {
		private ProgressBar progressBar;

		public ThumbnailLoadedListener(ProgressBar progressBar) {
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
						startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
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

		private boolean canResolveIntent(Intent intent) {
			List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentActivities(intent, 0);
			return resolveInfo != null && !resolveInfo.isEmpty();
		}
	}
}
