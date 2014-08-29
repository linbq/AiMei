package com.aimei.ui;

import roboguice.inject.InjectView;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayVideoAcitvity extends BaseActivity {
	@InjectView(R.id.btn) Button btn;
	@InjectView(R.id.video) VideoView video;
	private int currentTime;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playvideo);
		//video.setVideoURI(Uri.parse("android.resource://com.aimei.ui/"+R.raw.little));
		MediaController mediaController = new MediaController(this); 
		video.setMediaController(mediaController); 
		video.start();
		video.setOnCompletionListener(new OnCompletionListener() {
			
			@Override 
			public void onCompletion(MediaPlayer arg0) {
				video.start();
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				video.stopPlayback();
				currentTime = video.getCurrentPosition();
				Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_video);
				animation.setFillAfter(true);
				animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation arg0) {
						//video.set;
					}
				});
				video.startAnimation(animation);
			}
		});
	}
}
