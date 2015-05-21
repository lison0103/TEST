package com.example.fm;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.NumberPicker;

public class NumberPickerDialog extends Dialog {

	private NumberPicker myfirstPicker;
	private NumberPicker mysecondPicker;

	public NumberPickerDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
//		super(context);  
//        this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_picker_dialog);
		
		myfirstPicker =(NumberPicker) findViewById(R.id.channelfirstpicker);
		mysecondPicker =(NumberPicker) findViewById(R.id.channelsecondpicker);
		
		myfirstPicker.setMaxValue(108);
		myfirstPicker.setMinValue(76);
		myfirstPicker.setValue(96);
		myfirstPicker.setDescendantFocusability(myfirstPicker.FOCUS_BLOCK_DESCENDANTS); //关闭数字可编辑
		
		mysecondPicker.setMaxValue(9);
		mysecondPicker.setMinValue(0);
		mysecondPicker.setValue(5);
		mysecondPicker.setDescendantFocusability(mysecondPicker.FOCUS_BLOCK_DESCENDANTS); 
	}
}
