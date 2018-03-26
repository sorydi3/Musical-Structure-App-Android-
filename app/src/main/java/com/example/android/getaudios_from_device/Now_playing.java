package com.example.android.getaudios_from_device;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class Now_playing extends AppCompatActivity {
    private int mPOSITION;
    private  TextView mTitle;
    private TextView mNameArtist;
    private MediaPlayer mMediaPlayerre;
    private ImageView mPlayButton;

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private ArrayList<songs> mArrayList;
    //methode OnaudiofocusListener
    private AudioManager.OnAudioFocusChangeListener mAfChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume. We'll treat
                        // both cases the same way because our app is playing short sound files.


                        // Pause playback and reset player to the start of the file. That way, we can
                        // play the word from the beginning when we resume playback.
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMediaPlayer.start();
                    }
                }
            };
    //methode onClickLister
    private MediaPlayer.OnCompletionListener mOnCompletionListener= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            mMediaPlayerre.reset();
           mPOSITION++;
            if (mPOSITION<mArrayList.size()) {
                try {
                    mMediaPlayerre.setDataSource(mArrayList.get(mPOSITION).getPathSong());

                    mMediaPlayerre.prepare();
                } catch (Exception ex) {
                    // report a crash
                }
            } else {
                // done with media player
                mMediaPlayerre.release();
                mMediaPlayerre = null;
                mPOSITION=0;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        ///////////////////////////////////////////////////////
        Bundle bundle= new Bundle();
        bundle=this.getIntent().getExtras();
        mArrayList=bundle.getParcelableArrayList("ArrayList");
        mPOSITION=bundle.getInt("position");
        /////////////////////////////////////
        mTitle=(TextView)findViewById(R.id.title) ;
        mTitle.setText(mArrayList.get(mPOSITION).getSongTitle());
        mNameArtist=(TextView)findViewById(R.id.Name) ;
        mNameArtist.setText(mArrayList.get(mPOSITION).getSongArtist());
        ////////////////////////////////////////////////////////////
        mPlayButton=(ImageView)findViewById(R.id.play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
                releaseMediaPlayer();
                // // Create and setup the {@link AudioManager} to request audio focus
                mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                //this is a listener to changes when a request occur to the request audio focus
                //this methode(with 3 arguments :the private listener on top ,the type of audio we want to play,and the lenght of the audio) request permition wether to play or not in case of call ,notification etc

                int  result= mAudioManager.requestAudioFocus(mAfChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                    if(result==mAudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                           try {
                               mMediaPlayer = new MediaPlayer();
                               mMediaPlayer.setDataSource(mArrayList.get(mPOSITION).getPathSong());
                               mMediaPlayer.prepare();
                               mMediaPlayer.start();
                               mMediaPlayerre=new MediaPlayer();
                               mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                               mMediaPlayerre.start();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                    }
            }
        });

        ImageView next=(ImageView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPOSITION<mArrayList.size()) {
                    mPOSITION++;
                    mTitle.setText(mArrayList.get(mPOSITION).getSongTitle());
                    mNameArtist.setText(mArrayList.get(mPOSITION).getSongArtist());
                    mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    int result = mAudioManager.requestAudioFocus(mAfChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                    if (result == mAudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        releaseMediaPlayer();
                        mMediaPlayer = new MediaPlayer();
                        try {
                            mMediaPlayer.setDataSource(mArrayList.get(mPOSITION).getPathSong());
                            mMediaPlayer.prepare();
                            mMediaPlayer.start();
                            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                            mMediaPlayerre.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mPOSITION = 0;
                    }
                }
            }
        });
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          if(mPOSITION>=0){
              mPOSITION--;
              mTitle.setText(mArrayList.get(mPOSITION).getSongTitle());
              mNameArtist.setText(mArrayList.get(mPOSITION).getSongArtist());
              mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
              int result = mAudioManager.requestAudioFocus(mAfChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
              if (result == mAudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                  releaseMediaPlayer();
                  mMediaPlayer = new MediaPlayer();
                  try {
                      mMediaPlayer.setDataSource(mArrayList.get(mPOSITION).getPathSong());
                      mMediaPlayer.prepare();
                      mMediaPlayer.start();
                      mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                      mMediaPlayerre.start();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }

          }
            }
        });

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.floati_ac_buton_now_playing);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Now_playing.this);
                builder.setMessage(getString(R.string.info_now_palying))
                        .setPositiveButton(getString(R.string.positiveButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setNegativeButton("", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

ImageView payment=(ImageView) findViewById(R.id.paymentss);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goApayment=new Intent(Now_playing.this,PaymentActivity.class);
                startActivity(goApayment);
            }
        });
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            //cansel the request to reprodution once done
            mAudioManager.abandonAudioFocus(mAfChangeListener);
        }
    }

}
