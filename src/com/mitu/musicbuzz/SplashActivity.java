package com.mitu.musicbuzz;




import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class SplashActivity extends ActionBarActivity {

	ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		pb =(ProgressBar) findViewById(R.id.progressplash);
		pb.setMax(5500);
		Thread thread = new Thread(){
			public void run(){
			try{
				int cur = 100;
                int end = 5500;
				sleep(5500);
				while (cur<end) {
                    sleep(1000);
                    cur +=1000 ;
                    pb.setProgress(cur);
                }
				
				Intent intent = new Intent(SplashActivity.this,PlaylistActivity.class);
				startActivity(intent);
				finish();
			}catch(Exception ex){
				 ex.printStackTrace();
			}
			
			}
		};
		thread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
