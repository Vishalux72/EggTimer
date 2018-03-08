package android.vishal.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;

    }

    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft/60;
        //progress = total no. of seconds;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);


        if (seconds <= 9){
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }
    public void controlTimer(View view){
        //The addition of 100 to timeSeekbar helps to deplets the time of the delay
        // that we get while we approach to the end of the countdown
        //It stops user to set the timing before it goes off .Thus user cannot change the timing
        // when it is already started.

        //This should happen only when the entire process is to start run.
        if (counterIsActive == false) {
            counterIsActive = true;
            //It will stop the seekbar to ever getting to start before it goes of completely
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");
           countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    timerTextView.setText("0:00");
                    Log.i("finished", "timer done");

                    //after the countdown is done we want to play the media sound
                    //You use getApplicationContext when you are inside the method's method
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    mediaPlayer.start();
                }
            }.start();
        }
        else {
            resetTimer();
           }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         timerSeekBar = findViewById(R.id.timer_seek_bar);
        timerTextView = findViewById(R.id.countdown_text_view);
        controllerButton = findViewById(R.id.button);



        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);



        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress , boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
