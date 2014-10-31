package com.pinthecloud.moodly.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.common.collect.Lists;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.adapter.PerformVideoListAdapter;
import com.pinthecloud.moodly.model.Musician;
import com.pinthecloud.moodly.model.Perform;

public class PerformFragment extends MoFragment{

	private static final int REQ_START_STANDALONE_PLAYER = 1;

	private Perform perform;

	private LinearLayout layout;
	private TextView performName;
	private TextView placeName;
	private TextView placeAddress01;
	private TextView placeAddress02;
	private TextView price;

	private RecyclerView performVideoList;
	private RecyclerView.Adapter<PerformVideoListAdapter.ViewHolder> performVideoListAdapter;
	private RecyclerView.LayoutManager performVideoListLayoutManager;


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

		// setMusicianVideoList
		performVideoList = new RecyclerView(context);
		performVideoList.setHasFixedSize(true);

		performVideoListLayoutManager = new LinearLayoutManager(context);
		performVideoList.setLayoutManager(performVideoListLayoutManager);

		performVideoListAdapter = new PerformVideoListAdapter(context, thisFragment, lineup);
		performVideoList.setAdapter(performVideoListAdapter);

		layout.addView(performVideoList);
	}
}
