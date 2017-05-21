package com.example.weatherforecast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class WeatherHttpClient {
	private static String API_ENDPOINT = "http://api.openweathermap.org/data/2.5/";
	private static String IMG_URL = "http://openweathermap.org/img/w/";
	private static final String API_KEY = "8e656ea53b9e822125f2d6403ffdb754";
	//public String urlExtend;
	HttpURLConnection con ;
	InputStream in;
	public void getConnection(String url){
		con = null ;
		in = null;
		try {
			con = (HttpURLConnection) ( new URL(url)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public String getWeatherData(String urlExtend){
		try {
			//getConnection(API_ENDPOINT + urlExtend+"&appid="+API_KEY);
			//read the response
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(API_ENDPOINT+urlExtend+"&appid="+API_KEY);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			StringBuffer buffer = new StringBuffer();
			in = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = br.readLine())!= null){
				buffer.append(line + "\r\n");
			}
			// Close connection;
			in.close();
			con.disconnect();
			return buffer.toString();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { in.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}
		return null;
	}
	public byte[] getImage(String code) {
		try {
			getConnection(IMG_URL + code);
			// read the response
			in = con.getInputStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ( in.read(buffer) != -1){
				baos.write(buffer);
			}
			return baos.toByteArray();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { in.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}
		return null;
	}
}
