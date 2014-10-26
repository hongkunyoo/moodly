package com.pinthecloud.moodly.fragment;

import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pinthecloud.moodly.MoGlobalVariable;
import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.activity.PerformActivity;
import com.pinthecloud.moodly.adapter.PerformListAdapter;
import com.pinthecloud.moodly.interfaces.MoListCallback;
import com.pinthecloud.moodly.model.Perform;

public class PerformListFragment extends MoFragment{

	private ProgressBar progressBar;
	private PullToRefreshListView pullToRefreshListView;
	private ListView performListView;
	private PerformListAdapter performListAdapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_perform_list, container, false);
		setActionBar();
		findComponent(view);
		setList();
		return view;
	}


	@Override
	public void onStart() {
		super.onStart();
		updatePerformList();
	}


	private void setActionBar(){
		ActionBar actionBar = activity.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}


	private void findComponent(View view){
		progressBar = (ProgressBar)view.findViewById(R.id.perform_list_frag_progress_bar);
		pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.perform_list_frag_list);
		performListView = pullToRefreshListView.getRefreshableView();
		registerForContextMenu(performListView);
	}


	private void setList(){
		performListAdapter = new PerformListAdapter(context);
		performListView.setAdapter(performListAdapter);
		performListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Perform perform = performListAdapter.getItem(--position);
				Intent intent = new Intent(context, PerformActivity.class);
				intent.putExtra(MoGlobalVariable.PERFORM_KEY, perform);
				startActivity(intent);
			}
		});
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				updatePerformList();
			}
		});
	}


	private void updatePerformList(){
		performHelper.getAllPerformListAsync(thisFragment, new MoListCallback<Perform>() {

			@Override
			public void onCompleted(List<Perform> list, int count) {
				progressBar.setVisibility(View.GONE);
				pullToRefreshListView.onRefreshComplete();

				performListAdapter.clear();
				performListAdapter.addAll(list);
			}
		});
	}
}
