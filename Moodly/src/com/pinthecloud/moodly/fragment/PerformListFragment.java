package com.pinthecloud.moodly.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.common.collect.Lists;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.adapter.PerformListAdapter;
import com.pinthecloud.moodly.interfaces.MoListCallback;
import com.pinthecloud.moodly.model.Perform;

public class PerformListFragment extends MoFragment{

	private RelativeLayout layout;
	private ProgressBar progressBar;

	private RecyclerView performListView;
	private PerformListAdapter performListAdapter;
	private RecyclerView.LayoutManager performListLayoutManager;
	private List<Perform> performList;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_perform_list, container, false);
		findComponent(view);
		setPerformList();
		updatePerformList();
		return view;
	}


	private void findComponent(View view){
		layout = (RelativeLayout)view.findViewById(R.id.perform_list_frag_layout);
		progressBar = (ProgressBar)view.findViewById(R.id.perform_list_frag_progress_bar);
	}


	private void setPerformList(){
		performListView = new RecyclerView(context);
		performListView.setHasFixedSize(true);

		performListLayoutManager = new LinearLayoutManager(context);
		performListView.setLayoutManager(performListLayoutManager);

		performList = Lists.newArrayList();
		performListAdapter = new PerformListAdapter(context, thisFragment, performList);
		performListView.setAdapter(performListAdapter);
		
		layout.addView(performListView);
	}


	private void updatePerformList(){
		performHelper.getAllPerformListAsync(thisFragment, new MoListCallback<Perform>() {

			@Override
			public void onCompleted(List<Perform> list, int count) {
				progressBar.setVisibility(View.GONE);
				performList.clear();
				performList.addAll(list);
			}
		});
	}
}
