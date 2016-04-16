package com.mitu.musicbuzz;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlaylistActivity extends ActionBarActivity {

	ListView lv;
	String[] items;

	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playlist);

		lv = (ListView) findViewById(R.id.lv);

		final ArrayList<File> songs = getSongList(Environment
				.getExternalStorageDirectory());
		
		
		items = new String[songs.size()];
		
		for(int i=0;i<songs.size();i++){
			items[i] = songs.get(i).getName().toString().replace(".mp3","").replace(".amr","");
		}
		
		ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),SongplayActivity.class);
                i.putExtra("position",position);
                i.putExtra("list",songs);
                startActivity(i);

            }
        });
	}

	public ArrayList<File> getSongList(File root) {
		// TODO Auto-generated method stub
		ArrayList<File> al = new ArrayList<>();
		File[] files = root.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory() && !files[i].isHidden()) {
				al.addAll(getSongList(files[i]));
			} else if (files[i].getName().endsWith(".mp3")
					|| files[i].getName().endsWith(".amr")) {
				al.add(files[i]);
			}
		}
		return al;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playlist, menu);
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
