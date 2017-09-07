package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;


    AudioManager.OnAudioFocusChangeListener mAudiofocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Permanent loss of audio focus
                        // Pause playback immediately

                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "minto wuksus"));
        words.add(new Word("What is your name?", "tinnә oyaase'nә"));
        words.add(new Word("My name is...", "oyaaset..."));
        words.add(new Word("How are you feeling?", "michәksәs?"));
        words.add(new Word("I’m feeling good.", "kuchi achit"));
        words.add(new Word("Are you coming?", "әәnәs'aa?"));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm"));
        words.add(new Word("I’m coming.", "әәnәm"));
        words.add(new Word("Let’s go.", "yoowutis"));
        words.add(new Word("Come here.", "әnni'nem"));


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mAudiofocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback

                    if (position == 0)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_where_are_you_going);
                    else if (position == 1)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_what_is_your_name);
                    else if (position == 2)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_my_name_is);
                    else if (position == 3)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_how_are_you_feeling);
                    else if (position == 4)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_im_feeling_good);
                    else if (position == 5)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_are_you_coming);
                    else if (position == 6)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_yes_im_coming);
                    else if (position == 7)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_im_coming);
                    else if (position == 8)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_lets_go);
                    else if (position == 9)
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.phrase_come_here);


                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }

        });

    }

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
            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mAudiofocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
