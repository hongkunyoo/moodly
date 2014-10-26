package com.pinthecloud.moodly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.model.Perform;

public class PerformListAdapter extends ArrayAdapter<Perform>{

	private Context context;


	public PerformListAdapter(Context context) {
		super(context, 0);
		this.context = context;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_perform_list, parent, false);
		}

		Perform square = getItem(position);
		if (square != null) {
			// TODO Do nothing
		}
		return view;
	}
}
