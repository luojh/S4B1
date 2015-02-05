package com.example.music;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;



public class MainActivityMusic extends Activity {

	ListView listView;
	Button btnList;
	Button btnPlay;
	Button btnNext;
	ImageView imgView;
	
	//MediaPlay对象
	public MediaPlayer mMediaPlayer = null;
	
	//播放列表
	private List<String> mMusicList = new ArrayList<String>();
	
	//当前播放音乐的索引
	private int currentListItem = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music); 	
		
		//更新播放列表
		//musicList();
		System.out.println(getSdCardPath());
		
		//初始化MediaPlayer对象
		mMediaPlayer = new MediaPlayer();
		
		listView = (ListView)findViewById(R.id.listMusic);
		btnPlay = (Button)findViewById(R.id.btnPlay);
		btnNext = (Button)findViewById(R.id.btnNext);
		btnList = (Button)findViewById(R.id.btnList);
		
		btnList.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				System.out.println("+++++++++++++++");
				musicList();
				System.out.println("+++++++++++++++");
				
			}
		});
		
		btnPlay.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Log.i(getSdCardPath() + mMusicList.get(currentListItem),"000000000000");
				System.out.println(getSdCardPath() + mMusicList.get(currentListItem));
				System.out.println(mMusicList);
				playMusic(getSdCardPath() + mMusicList.get(currentListItem));	
				Log.i("9999999999999999","99999999999999999");
				System.out.println("999999999999999999999999999999999999999999999999999");
			}
			
		});
		btnNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				nextMusic();
			}	
		});		
	}
	
	public void musicList(){
		try{
			if("".equals(getSdCardPath())) {
				new AlertDialog.Builder(MainActivityMusic.this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("提示")
				.setMessage("请检查SD卡是否正常")
				.show();			
			}else {
				File path = new File(getSdCardPath());
				if(path.listFiles(new MusicFilter()).length > 0) {
					for(File file : path.listFiles(new MusicFilter())) {
						mMusicList.add(file.getName());
					}
					ArrayAdapter<String> mList = new ArrayAdapter<String>(MainActivityMusic.this,android.R.layout.simple_list_item_1,mMusicList);
					listView.setAdapter(mList);
				}
			}
		}catch(NullPointerException npe) {}
	}

	//判断SD卡是否存在
	public static boolean isSdCardExist() {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	//获取SD卡根目录
	public static String getSdCardPath() {
		boolean exist = isSdCardExist();
		File sdDir = null;
		String sdStr = null;
		
		if(exist) {
			sdDir = Environment.getExternalStorageDirectory();
			sdStr = sdDir.toString() + "/";
			return sdStr;
		}else {
			return "";
		}
	}
	
	public void playMusic(String path) {
		try{
			System.out.println("1111111111111111111111111");
			Log.i("11111111111111111","111111111111111111");
			//重置MediaPlayer
			mMediaPlayer.reset();
			System.out.println("2222222222222222222222222");
			Log.i("22222222222222222","222222222222222222");
			
			System.out.println("3333333333333333333333333");
			Log.i("33333333333333333","333333333333333333");
			//设置要播放的文件路径
			mMediaPlayer.setDataSource(path);
			System.out.println("4444444444444444444444444");
			Log.i("44444444444444444","444444444444444444");
			System.out.println("5555555555555555555555555");
			Log.i("55555555555555555","555555555555555555");
			//准备播放
			mMediaPlayer.release();
			System.out.println("6666666666666666666666666");
			Log.i("66666666666666666","666666666666666666");
			System.out.println("7777777777777777777777777");
			Log.i("77777777777777777","777777777777777777");
			//开始播放
			mMediaPlayer.start();
			System.out.println("8888888888888888888888888");
			Log.i("88888888888888888","888888888888888888");
			/*
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener(){
				public void onCompletion(MediaPlayer mp) {
					//播放完一首之后自动播放下一首
					nextMusic();
				}
				
			})*/		
		}catch(IOException io){io.printStackTrace();}
		catch(IllegalStateException ise) {ise.printStackTrace();}
	}
	
	public void nextMusic() {
		if(++currentListItem >= mMusicList.size()) {
			currentListItem = 0;
		}else{
			playMusic(getSdCardPath() + mMusicList.get(currentListItem));
		}
	}
	
	
	class MusicFilter implements FilenameFilter {
		public boolean accept(File dir,String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
	
}



