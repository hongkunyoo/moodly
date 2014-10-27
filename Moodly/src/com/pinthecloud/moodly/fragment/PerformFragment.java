package com.pinthecloud.moodly.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.model.Perform;

public class PerformFragment extends MoFragment{

	private Perform perform;

	private TextView performName;
	private TextView placeName;
	private TextView placeAddress01;
	private TextView placeAddress02;
	private TextView startDate;
	private TextView endDate;
	private TextView performTime;
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
		return view;
	}


	private void setActionBar(){
		ActionBar actionBar = activity.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(perform.getPerformName());
	}


	private void findComponent(View view){
		performName = (TextView)view.findViewById(R.id.perform_frag_perform_name);
		placeName = (TextView)view.findViewById(R.id.perform_frag_theater_name);
		placeAddress02 = (TextView)view.findViewById(R.id.perform_frag_theater_address);
		placeAddress01 = (TextView)view.findViewById(R.id.perform_frag_theater_city);
		startDate = (TextView)view.findViewById(R.id.perform_frag_begin_day);
		endDate = (TextView)view.findViewById(R.id.perform_frag_finish_day);
		performTime = (TextView)view.findViewById(R.id.perform_frag_perform_time);
		price = (TextView)view.findViewById(R.id.perform_frag_price);
	}


	private void setTextView(Perform perform){
		performName.setText(perform.getPerformName());
		placeName.setText(perform.getPlaceName());
		placeAddress02.setText(perform.getPlaceAddress02());
		placeAddress01.setText(perform.getPlaceAddress01());
		startDate.setText(perform.getStartDate());
		endDate.setText(perform.getEndDate());
		performTime.setText(perform.getTime());
		price.setText(String.valueOf(perform.getPrice()));
	}
}
