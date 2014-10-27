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
	private TextView theaterName;
	private TextView theaterAddress;
	private TextView theaterCity;
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
		theaterName = (TextView)view.findViewById(R.id.perform_frag_theater_name);
		theaterAddress = (TextView)view.findViewById(R.id.perform_frag_theater_address);
		theaterCity = (TextView)view.findViewById(R.id.perform_frag_theater_city);
		price = (TextView)view.findViewById(R.id.perform_frag_price);
	}


	private void setTextView(Perform perform){
		performName.setText(perform.getPerformName());
		theaterName.setText(perform.getTheaterName());
		theaterAddress.setText(perform.getTheaterAddress());
		theaterCity.setText(perform.getTheaterCity());
		price.setText(""+perform.getPrice());
	}
}
