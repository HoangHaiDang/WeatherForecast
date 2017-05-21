package com.example.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;



public class MainActivity extends Activity implements LocationListener{
	ImageButton btnLocalWeather, btnSearchWeather;
	ListView lvsweathers;
	EditText edtHomeAddress;
	Context context;
	Activity activity;
	// Location
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	private model.Location location;
	private boolean useCurrentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLocalWeather = (ImageButton)findViewById(R.id.btnLocalWeather);
        btnSearchWeather = (ImageButton)findViewById(R.id.btnSearchWeather);
        lvsweathers = (ListView)findViewById(R.id.lvsweathers);
        edtHomeAddress = (EditText)findViewById(R.id.edtHomeAddress);
        context = this.getApplicationContext();
        activity = this;
        //
        location = new model.Location();
        useCurrentPosition = true;
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        
        
        btnLocalWeather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,LocalWeather.class);
				startActivity(intent);
			}
		});
        btnSearchWeather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String city = edtHomeAddress.getText().toString();
				if(city.isEmpty()){
					Toast.makeText(context,"Your City's name cannot be blank!",Toast.LENGTH_LONG ).show();
				}else{
					city = city.replaceAll("\\s+","");
					useCurrentPosition = false;
					try {
						ShowCurrentWeathers weather = new ShowCurrentWeathers(context,activity);
				        weather.execute(new String[]{"forecast/daily?q="+city+"&mode=json&units=metric&cnt=7","showListWeather"});
					} catch (Exception e) {
						Toast.makeText(context,"Your City's name is incorrect!",Toast.LENGTH_LONG ).show();
					}
				}
			}
		});
        showAllWeather();
    }
    public void showAllWeather(){
    	ShowCurrentWeathers weather = new ShowCurrentWeathers(context,activity);
        weather.execute(new String[]{"forecast/daily?lat="+location.getLat()+"&lon="+location.getLon()+"&mode=json&units=metric&cnt=7","showListWeather"});
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
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if(useCurrentPosition){
			this.location.setLat(location.getLatitude());
			this.location.setLon(location.getLongitude());
		}		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d("Latitude","status");
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("Latitude","enable");
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("Latitude","disable");
	}
}
