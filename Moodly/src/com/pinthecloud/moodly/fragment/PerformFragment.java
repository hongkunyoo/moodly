package com.pinthecloud.moodly.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
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
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.model.Musician;
import com.pinthecloud.moodly.model.Perform;

public class PerformFragment extends MoFragment{

	private static final int REQ_START_STANDALONE_PLAYER = 1;
	private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

	private Perform perform;

	private LinearLayout layout;
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
		addMusicianVideoLayout(perform);
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
		ActionBar actionBar = activity.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(perform.getPerformName());
	}


	private void findComponent(View view){
		layout = (LinearLayout)view.findViewById(R.id.perform_frag_layout);
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


	private void addMusicianVideoLayout(final Perform perform){
		// Mock data
		ArrayList<Musician> mList = new ArrayList<Musician>();

		Musician m = new Musician();
		m.setName("S White");
		m.setVideoLink("QuNOnQJEDGg");
		mList.add(m);

		m = new Musician();
		m.setName("구글");
		m.setVideoLink("nCgQDjiotG0");
		mList.add(m);

		perform.setLineup(mList);

		for(int i=0; i<perform.getLineup().size() ; i++){
			final Musician musician = perform.getLineup().get(i);

			LinearLayout musicianLayout = new LinearLayout(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			musicianLayout.setLayoutParams(params);
			musicianLayout.setOrientation(LinearLayout.VERTICAL);

			TextView musicianName = new TextView(context);
			musicianName.setText(musician.getName());

			final ProgressBar progressBar = new ProgressBar(context);

			YouTubeThumbnailView thumbnailView = new YouTubeThumbnailView(context);
			thumbnailView.initialize(MoGlobalVariable.GOOGLE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {

				@Override
				public void onInitializationSuccess(YouTubeThumbnailView thumbnailView, 
						YouTubeThumbnailLoader thumbnailLoader) {
					thumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailListener(progressBar));
					thumbnailLoader.setVideo(musician.getVideoLink());
				}

				@Override
				public void onInitializationFailure(YouTubeThumbnailView thumbnailView,
						YouTubeInitializationResult error) {
					// Do nothing
				}
			});

			musicianLayout.addView(musicianName);
			musicianLayout.addView(progressBar);
			musicianLayout.addView(thumbnailView);
			layout.addView(musicianLayout);
		}
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
	}
}
