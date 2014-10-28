package com.pinthecloud.moodly.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pinthecloud.moodly.R;
import com.pinthecloud.moodly.model.Perform;

public class HongkunActivity extends MoActivity {
	TextView view;
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hongkun);
		
		view = (TextView)findViewById(R.id.textView1);
		btn = (Button)findViewById(R.id.button1);
		
		Log(this, Perform.newPerform().toJson());
	}
}
