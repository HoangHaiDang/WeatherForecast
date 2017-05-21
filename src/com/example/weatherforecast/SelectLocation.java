package com.example.weatherforecast;

import java.util.ArrayList;
import java.util.List;

import model.Location;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SelectLocation extends Activity {
	ImageButton btnSearchAddress;
	EditText edtAddress;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location);
		btnSearchAddress = (ImageButton)findViewById(R.id.btnSearchAddress);
		edtAddress = (EditText)findViewById(R.id.edtAddress);
		context = this.getApplicationContext();
		final Geocoder geocoder = new Geocoder(this.getApplicationContext());
		btnSearchAddress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					List<Address> lstAdress = new ArrayList<Address>();
					lstAdress = geocoder.getFromLocationName(edtAddress.getText().toString(), 1);
					if(lstAdress == null){
						Toast.makeText(context,"Your address is incorect! please enter other address.",Toast.LENGTH_LONG).show();
					}else{
						
						Address address = lstAdress.get(0);
						Toast.makeText(context,address.getCountryName(),Toast.LENGTH_LONG).show();
						Location local = new Location();
						local.setCoutryCode(address.getCountryCode());
						local.setLat(address.getLatitude());
						local.setLon(address.getLongitude());
						Toast.makeText(context,"Lat: "+local.getLat(),Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_location, menu);
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
