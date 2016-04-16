package com.mitu.musicbuzz;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class SongplayActivity extends ActionBarActivity implements
		View.OnClickListener {

	Button btPlay, btFF, btBW, btPv, btNxt;
	TextView songtitle;
	SeekBar sb;
	int Check=1;
	String[] itemsm;
	static MediaPlayer mp;
	Uri uri;
	ArrayList<File> songs;
	int position;
	Thread updateSeekBar;
	int TotalDuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_songplay);
		sb = (SeekBar) findViewById(R.id.seekBar1);
		songtitle = (TextView) findViewById(R.id.songtitle);

		btPlay = (Button) findViewById(R.id.buttonPlay);
		btFF = (Button) findViewById(R.id.forwardbtn);
		btBW = (Button) findViewById(R.id.backwordbtn);
		btPv = (Button) findViewById(R.id.previousbtn);
		btNxt = (Button) findViewById(R.id.btnNext);

		btPlay.setOnClickListener(this);
		btFF.setOnClickListener(this);
		btBW.setOnClickListener(this);
		btPv.setOnClickListener(this);
		btNxt.setOnClickListener(this);

		if (mp != null) {
			// mp.stop();
			/* mp.release(); */
			mp.pause();
			mp.seekTo(0);
		}

		Intent i = getIntent();
		Bundle b = i.getExtras();
		songs = (ArrayList) b.getParcelableArrayList("list");
		position = b.getInt("position", 0);

		// /start
		itemsm = new String[songs.size()];

		for (int j = 0; j < songs.size(); j++) {
			itemsm[j] = songs.get(j).getName().toString().replace(".mp3","").replace(".amr","");
		}
		// /end
		
		
		
		uri = Uri.parse(songs.get(position).toString());
		mp = MediaPlayer.create(getApplicationContext(), uri);
		mp.start();
		
		//start
		songtitle.setText("         "+itemsm[position]);
		//end

		TotalDuration = mp.getDuration();
		sb.setMax(TotalDuration);

		updateSeekBar = new Thread() {
			@Override
			public void run() {
				try {
					TotalDuration = mp.getDuration();
					int CurrentPosition = 0;
					while (CurrentPosition < TotalDuration) {
						sleep(500);
						CurrentPosition = mp.getCurrentPosition();
						sb.setProgress(CurrentPosition);
					}
					

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		updateSeekBar.start();

		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mp.seekTo(seekBar.getProgress());
			}
		}); 
		
	
		
		
/*	if(Check==1){
	 
			position++;
			
			songtitle.setText("         "+itemsm[position]);
			 uri = Uri.parse(songs.get(position).toString());
			mp = MediaPlayer.create(getApplicationContext(), uri);
			mp.start();
			TotalDuration = mp.getDuration();
			sb.setMax(TotalDuration);
			
		/*	for(; position<songs.size();position++){
				songtitle.setText("         "+itemsm[position]);
			 uri = Uri.parse(songs.get(position).toString());
			mp = MediaPlayer.create(getApplicationContext(), uri);
			mp.start();
			TotalDuration = mp.getDuration();
			sb.setMax(TotalDuration);
			 
			 } */
			
		//} */
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.buttonPlay:
			if (mp.isPlaying()) {
				mp.pause();

				btPlay.setBackgroundResource(R.drawable.play);
			} else {
				mp.start();
				btPlay.setBackgroundResource(R.drawable.pause);
			}
			break;
		case R.id.forwardbtn:
			mp.seekTo(mp.getCurrentPosition() + 5000);
			break;
		case R.id.backwordbtn:
			mp.seekTo(mp.getCurrentPosition() - 5000);
			break;
		case R.id.btnNext:
			mp.pause();
			mp.seekTo(0);

			if (position < songs.size() - 1) {
				position++;
				songtitle.setText("         "+itemsm[position]);
			} else if (position == songs.size() - 1) {
				position = 0;
				songtitle.setText("         "+itemsm[position]);
			}

			uri = Uri.parse(songs.get(position).toString());
			mp = MediaPlayer.create(getApplicationContext(), uri);
			mp.start();
			TotalDuration = mp.getDuration();
			sb.setMax(TotalDuration);
			//start
			Check =0;
			//end
			break;
		case R.id.previousbtn:
			mp.pause();
			mp.seekTo(0);

			if (position > 0) {
				position--;
				
				songtitle.setText("         "+itemsm[position]);
			} else if (position == 0) {
				position = songs.size() - 1;
				songtitle.setText("         "+itemsm[position]);
			}

			uri = Uri.parse(songs.get(position).toString());
			mp = MediaPlayer.create(getApplicationContext(), uri);
			mp.start();
			TotalDuration = mp.getDuration();
			sb.setMax(TotalDuration);
			//start
			Check =0;
			//end
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.songplay, menu);
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
