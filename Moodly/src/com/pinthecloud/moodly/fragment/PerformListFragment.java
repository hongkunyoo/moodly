package com.pinthecloud.moodly.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.common.collect.Lists;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.adapter.PerformListAdapter;
import com.pinthecloud.moodly.interfaces.MoListCallback;
import com.pinthecloud.moodly.model.Perform;

public class PerformListFragment extends MoFragment{

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
		performListView = (RecyclerView)view.findViewById(R.id.perform_list_frag_list);
		progressBar = (ProgressBar)view.findViewById(R.id.perform_list_frag_progress_bar);
	}


	private void setPerformList(){
		performListView.setHasFixedSize(true);

		performListLayoutManager = new LinearLayoutManager(activity);
		performListView.setLayoutManager(performListLayoutManager);

		performList = Lists.newArrayList();
		performListAdapter = new PerformListAdapter(activity, thisFragment, performList);
		performListView.setAdapter(performListAdapter);
	}


	private void updatePerformList(){
		performHelper.getAllPerformListAsync(thisFragment, new MoListCallback<Perform>() {

			@Override
			public void onCompleted(List<Perform> list, int count) {
				progressBar.setVisibility(View.GONE);
				performList.clear();
				performList.addAll(list);
				performListAdapter.notifyDataSetChanged();
			}
		});
	}
}
