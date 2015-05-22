package com.example.fm;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NumberPickerDialog extends Dialog implements android.view.View.OnClickListener {

	private NumberPicker myfirstPicker;
	private NumberPicker mysecondPicker;
	
	private Button set_button;
	
	private SharedPreferences mSharedPreference;
	private SharedPreferences.Editor mEdit;
	
	Context context;  
    String mfmchannel;  
	float channel;
	
	int x;    
	float y;

	public NumberPickerDialog(Context context,String fmchannel) {
		super(context);
		// TODO Auto-generated constructor stub
//		super(context);  
        this.context = context;
        this.mfmchannel = fmchannel;
        channel = Float.parseFloat(mfmchannel);
        x = (int)channel;
        y = channel - (float)x;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_picker_dialog);
		
		myfirstPicker =(NumberPicker) findViewById(R.id.channelfirstpicker);
		mysecondPicker =(NumberPicker) findViewById(R.id.channelsecondpicker);
		set_button = (Button)findViewById(R.id.enter_button);
		
		mSharedPreference=PreferenceManager.getDefaultSharedPreferences(context);
		mEdit=mSharedPreference.edit();
		
		myfirstPicker.setMaxValue(108);
		myfirstPicker.setMinValue(76);
		myfirstPicker.setValue(x);
		myfirstPicker.setDescendantFocusability(myfirstPicker.FOCUS_BLOCK_DESCENDANTS); //关闭数字可编辑
		
		
		mysecondPicker.setMaxValue(9);
		mysecondPicker.setMinValue(0);
		mysecondPicker.setValue((int)(y*10));
		mysecondPicker.setDescendantFocusability(mysecondPicker.FOCUS_BLOCK_DESCENDANTS); 
		
		set_button.setOnClickListener(this);  
	}
	
	@Override  
    public void onClick(View v) {  
        switch (v.getId()) {  
        case R.id.enter_button:  
        	
			String ChannelStr = myfirstPicker.getValue() + "." + mysecondPicker.getValue();
			
//			SetFm(SetFmCh, EditTextStr);			

			
			mEdit.putString("channel", ChannelStr);  
			mEdit.commit(); 
			
			
	        Toast.makeText(context, "FM" + ChannelStr + "MHZ", Toast.LENGTH_SHORT).show();

		

            break;  

        }  
    }  
}
