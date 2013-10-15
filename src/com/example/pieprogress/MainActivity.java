package com.example.pieprogress;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	PieProgress pieProgress;
	Button btn1, btn2;
	EditText edit;
	Handler h;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		h = new Handler();
		pieProgress = (PieProgress) findViewById(R.id.pieProgress1);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		edit = (EditText) findViewById(R.id.editText1);
		btn1.setOnClickListener(btn1_click);
		btn2.setOnClickListener(btn2_Click);
	}

	
	
	private OnClickListener btn1_click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (Integer.parseInt(edit.getText().toString()) > 100) {
				Toast.makeText(MainActivity.this, "Input a value between 0-100", Toast.LENGTH_LONG).show();
				edit.setText("");
				return;
			} else {
				pieProgress.setProgress(Integer.parseInt(edit.getText().toString()));
			}
		}
		
	};
	
	private OnClickListener btn2_Click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (pieProgress.getIndeterminate()) {
				pieProgress.setIndeterminate(false);
			} else {
				pieProgress.setIndeterminate(true);
			}
		}
		
	};

}
