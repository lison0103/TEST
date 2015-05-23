package com.example.fm;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class NumberPickerDialog extends Dialog implements
		android.view.View.OnClickListener, OnValueChangeListener {

	public interface OnCustomDialogListener {
		public void back(String name);
	}

	private NumberPicker myfirstPicker;
	private NumberPicker mysecondPicker;

	private OnCustomDialogListener customDialogListener;

	private Button set_button;
	private Button cancel_button;

	private SharedPreferences mSharedPreference;
	private SharedPreferences.Editor mEdit;

	private TextView mtitle_text;
	String ChannelStr;

	Context context;
	String mfmchannel;
	float channel;

	int x;
	float y;

	public NumberPickerDialog(Context context, String fmchannel,
			OnCustomDialogListener customDialogListener) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mfmchannel = fmchannel;
		this.customDialogListener = customDialogListener;

		channel = Float.parseFloat(mfmchannel);
		x = (int) channel;
		y = channel - (float) x;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_picker_dialog);

		// setTitle("FM发射频道设置");

		myfirstPicker = (NumberPicker) findViewById(R.id.channelfirstpicker);
		mysecondPicker = (NumberPicker) findViewById(R.id.channelsecondpicker);
		set_button = (Button) findViewById(R.id.enter_button);
		cancel_button = (Button) findViewById(R.id.cancel_button);
		mtitle_text = (TextView) findViewById(R.id.setchannel_title);

		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		mEdit = mSharedPreference.edit();

		if (x == 108) {
			myfirstPicker.setMaxValue(108);
			myfirstPicker.setMinValue(107);
			mysecondPicker.setMaxValue(0);
			mysecondPicker.setMinValue(0);

		} else if (x == 76) {
			myfirstPicker.setMaxValue(77);
			myfirstPicker.setMinValue(76);
			mysecondPicker.setMaxValue(9);
			mysecondPicker.setMinValue(0);

		} else {
			myfirstPicker.setMaxValue(108);
			myfirstPicker.setMinValue(76);
			mysecondPicker.setMaxValue(9);
			mysecondPicker.setMinValue(0);
		}

		myfirstPicker.setOnValueChangedListener(this);
		// myfirstPicker.setMaxValue(108);
		// myfirstPicker.setMinValue(76);
		myfirstPicker.setValue(x);
		myfirstPicker
				.setDescendantFocusability(myfirstPicker.FOCUS_BLOCK_DESCENDANTS); // 关闭数字可编辑

		mysecondPicker.setOnValueChangedListener(this);
		// mysecondPicker.setMaxValue(9);
		// mysecondPicker.setMinValue(0);
		mysecondPicker.setValue((int) (y * 10));
		mysecondPicker
				.setDescendantFocusability(mysecondPicker.FOCUS_BLOCK_DESCENDANTS);

		set_button.setOnClickListener(this);
		cancel_button.setOnClickListener(this);

		mtitle_text.setText(myfirstPicker.getValue() + "."
				+ mysecondPicker.getValue());

	}

	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

		if (picker == myfirstPicker) {
			if (newVal == 108) {
				myfirstPicker.setMaxValue(108);
				myfirstPicker.setMinValue(107);
				mysecondPicker.setMaxValue(0);
				mysecondPicker.setMinValue(0);

			} else if (newVal == 76) {
				myfirstPicker.setMaxValue(77);
				myfirstPicker.setMinValue(76);
				mysecondPicker.setMaxValue(9);
				mysecondPicker.setMinValue(0);

			} else {
				myfirstPicker.setMaxValue(108);
				myfirstPicker.setMinValue(76);
				mysecondPicker.setMaxValue(9);
				mysecondPicker.setMinValue(0);
			}
		}
		ChannelStr = myfirstPicker.getValue() + "." + mysecondPicker.getValue();
		mtitle_text.setText(ChannelStr);

		// Toast.makeText(context, "原来的值 " + oldVal + "--新值: " + newVal,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.enter_button:

			mEdit.putString("channel", ChannelStr);
			mEdit.commit();

			Toast.makeText(context, "FM" + ChannelStr + "MHZ",
					Toast.LENGTH_SHORT).show();

			customDialogListener.back(ChannelStr);
			NumberPickerDialog.this.dismiss();

			break;
		case R.id.cancel_button:
			NumberPickerDialog.this.dismiss();

			break;

		}
	}
}
