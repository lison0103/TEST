package com.example.fm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnValueChangeListener,OnScrollListener,Formatter{

	
	private Button set_button;
	private Button on_button;
//	private Button off_button;
	private EditText edit_text;
	private SharedPreferences mSharedPreferences;
	
	private NumberPicker firstPicker;
	private NumberPicker secondPicker;
	
	String TAG = "chl";
	
	private File EnableFm = new File("/sys/devices/platform/mt-i2c.1/i2c-1/1-002c/enable_qn8027");
	private File SetFmCh = new File("/sys/devices/platform/mt-i2c.1/i2c-1/1-002c/setch_qn8027");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		set_button = (Button)findViewById(R.id.set_button);
//		off_button = (Button)findViewById(R.id.close_button);
		on_button = (Button)findViewById(R.id.open_button);
		edit_text = (EditText)findViewById(R.id.edit_text);
		
		set_button.setOnClickListener(new SetListener());
//		off_button.setOnClickListener(new OffListener());
		on_button.setOnClickListener(new OnListener());
		edit_text.setOnClickListener(new EditListener());
		
		firstPicker =(NumberPicker) findViewById(R.id.channelpicker);
	    secondPicker =(NumberPicker) findViewById(R.id.channeldotpicker);
	    PickerInit();
		
        String CH = mSharedPreferences.getString("channel", "9700");  
        String OnOff = mSharedPreferences.getString("onoff", "1");
        

        edit_text.setText(CH);  
        SetFm(SetFmCh, CH);
 /*        
  		SetFm(EnableFm, OnOff);
        
        if(OnOff.equals("0")){
			on_button.setClickable(true);
			off_button.setClickable(false);
        }else{
			on_button.setClickable(false);
			off_button.setClickable(true);
        }
*/
		if(OnOff.equals("0")){
			on_button.setText("off");
			SetFm(EnableFm, "0");
			
		}else{
			on_button.setText("on");
			SetFm(EnableFm, "1");
		}
        
		edit_text.setSelectAllOnFocus(true);

//        edit_text.setCursorVisible(false);
          
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();  
        mEditor.putString("channel", CH);  
        mEditor.putString("onoff", OnOff);  
        mEditor.commit(); 

		edit_text.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							// 此处为得到焦点时的处理内容
							edit_text.setCursorVisible(true);
							Log.d(TAG,"hasfocus");

						} else {
							// 此处为失去焦点时的处理内容
							edit_text.setCursorVisible(false);
							InputMethodManager imm = 
									(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
							Log.d(TAG,"no focus");
						}
					}
				});
	}
	
	private void PickerInit() {
	    firstPicker.setFormatter(this);
	    firstPicker.setOnValueChangedListener(this);
	    firstPicker.setOnScrollListener(this);
	    firstPicker.setMaxValue(108);
	    firstPicker.setMinValue(76);
	    firstPicker.setValue(96);
	    firstPicker.setDescendantFocusability(firstPicker.FOCUS_BLOCK_DESCENDANTS); //关闭数字可编辑
	    
//	    secondPicker.setFormatter(this);
	    secondPicker.setOnValueChangedListener(this);
	    secondPicker.setOnScrollListener(this);
	    secondPicker.setMaxValue(9);
	    secondPicker.setMinValue(0);
	    secondPicker.setValue(5);
	    secondPicker.setDescendantFocusability(secondPicker.FOCUS_BLOCK_DESCENDANTS); 
	  }
	
	public void onScrollStateChange(NumberPicker view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_FLING:
			Toast.makeText(this, "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG).show();
			break;
		case OnScrollListener.SCROLL_STATE_IDLE:
			Toast.makeText(this, "不滑动", Toast.LENGTH_LONG).show();
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			Toast.makeText(this, "滑动中", Toast.LENGTH_LONG).show();
			break;
		}
	}
	
	public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }
	
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(
                this,
                "原来的值 " + oldVal + "--新值: "
                        + newVal, Toast.LENGTH_SHORT).show();
    }
	
	class EditListener implements OnClickListener{
		@Override
		public void onClick(View v) {

//			edit_text.setCursorVisible(true);
//			edit_text.setSelectAllOnFocus(true);
//			edit_text.setSelection(edit_text.getText().length());
			
		}
	}
	
	
	class SetListener implements OnClickListener{
		@Override
		public void onClick(View v) {
/*
			String EditTextStr = edit_text.getText().toString();
			
			SetFm(SetFmCh, EditTextStr);
			
//			edit_text.setCursorVisible(false);
			edit_text.clearFocus();

	        SharedPreferences.Editor mEditor = mSharedPreferences.edit();  
	        mEditor.putString("channel", EditTextStr);  
	        mEditor.commit(); 
*/
			
			new NumberPickerDialog(MainActivity.this,"95.3").show();
			
			finish();
			
//	        Toast.makeText(MainActivity.this, "FM发射频道为" + EditTextStr + "MHZ", Toast.LENGTH_SHORT).show();
		}
	}
/*
	class OffListener implements OnClickListener{
		@Override
		public void onClick(View v) {

			SetFm(EnableFm, "0");
			on_button.setClickable(true);
			off_button.setClickable(false);
			
	        SharedPreferences.Editor mEditor = mSharedPreferences.edit();  
	        mEditor.putString("onoff", "0");  
	        mEditor.commit(); 
		
		}
	}
*/	
	class OnListener implements OnClickListener{
		@Override
		public void onClick(View v) {

			String OnOff = mSharedPreferences.getString("onoff", "1");
			SharedPreferences.Editor mEditor = mSharedPreferences.edit(); 
			
			edit_text.clearFocus();
			
			if(OnOff.equals("0")){
				on_button.setText("on");
				SetFm(EnableFm, "1");
				mEditor.putString("onoff", "1"); 
				Toast.makeText(MainActivity.this, "FM发射  打开", Toast.LENGTH_SHORT).show();
				
			}else{
				on_button.setText("off");
				SetFm(EnableFm, "0");
				mEditor.putString("onoff", "0"); 
				Toast.makeText(MainActivity.this, "FM发射  关闭", Toast.LENGTH_SHORT).show();
			}
			
	        mEditor.commit(); 
	        
/*
			SetFm(EnableFm, "1");
			on_button.setClickable(false);
			off_button.setClickable(true);
			
	        SharedPreferences.Editor mEditor = mSharedPreferences.edit();  
	        mEditor.putString("onoff", "1");  
	        mEditor.commit(); 
*/		
		}
	}
	
	protected void SetFm(File file, String value){
		if(file.exists()) {
			try {
				StringBuffer strbuf = new StringBuffer("");
				strbuf.append(value);
				
				Log.d(TAG, "11111111::::::  " + strbuf);
				
				OutputStream output = null;
	                	OutputStreamWriter outputWrite = null;
        	        	PrintWriter print = null;
			
				try {
					Log.d(TAG, "aaaaaaaaaaaaaaaa");
					output = new FileOutputStream(file);
					Log.d(TAG, "vvvvvvvvvvvvvvvvvvvvvv");
                        		outputWrite = new OutputStreamWriter(output);
                        		Log.d(TAG, "000000000000");
                       			print = new PrintWriter(outputWrite);
                       			Log.d(TAG, "11111111111111");
                        		print.print(strbuf.toString());
                        		Log.d(TAG, "222222222222222");
                        		print.flush();
                        		Log.d(TAG, "3333333333333333");
                        		output.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.e(TAG, "output error");
				}
	
			}  catch (IOException e){
                                Log.e(TAG, "IO Exception");
             		}
		} else {
			Log.e(TAG, "File:" + file + "not exists");
		}
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
