package com.pinthecloud.moodly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.activity.PerformListActivity;

public class GuideFragment extends MoFragment{

	private Button startButton;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_guide, container, false);
		findComponent(view);
		setButtonEvent();
		return view;
	}


	private void findComponent(View view){
		startButton = (Button)view.findViewById(R.id.guide_frag_start_button);
	}


	private void setButtonEvent(){
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userHelper.setLoggedInUser(true);
				Intent intent = new Intent(activity, PerformListActivity.class);
				startActivity(intent);
				activity.finish();
			}
		});
	}
}
