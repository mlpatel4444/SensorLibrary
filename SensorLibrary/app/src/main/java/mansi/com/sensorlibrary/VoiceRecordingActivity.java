package mansi.com.sensorlibrary;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.wave.Wave;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by sphere76 on 17/6/17.
 */
public class VoiceRecordingActivity extends AppCompatActivity{

    Button record,stop,play,match;
    MediaRecorder mediaRecorder;
    String voicestoragepath;
    String AB = "abcdefghijklmnopqrstuvwxyz";
    String number = "123456";
    Random rnd = new Random();
    MediaPlayer mediaplayer;
    int YOUR_REQUEST_CODE = 200;
    TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recording);

        hasSDCard();

        record = (Button)findViewById(R.id.record);
        stop = (Button)findViewById(R.id.stop);
        play = (Button)findViewById(R.id.play);
        match = (Button)findViewById(R.id.match);
        result = (TextView)findViewById(R.id.result);

        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchaudiofiles();
            }
        });

        voicestoragepath = Environment.getExternalStorageDirectory().getAbsolutePath();
       // voicestoragepath = "Directory browsing/SD card";
        File audioVoice = new File(voicestoragepath+File.separator+"voices");
        if(!audioVoice.exists())
        {
            audioVoice.mkdir();
        }

       // voicestoragepath = voicestoragepath+File.separator+"voices/"+generateVoiceFileName(6)+".3gpp";
        voicestoragepath = voicestoragepath+File.separator+"voices/"+"path"+generateVoiceFileName(1)+".wav";
        System.out.println("Audio File Path:"+voicestoragepath);
        Toast.makeText(getApplicationContext(),"Audio File Path:"+voicestoragepath,Toast.LENGTH_SHORT).show();

        stop.setEnabled(false);
        play.setEnabled(false);
       // match.setEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) //check if permission request is necessary
        {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, YOUR_REQUEST_CODE);
        }

//        initializeMediaRecord();

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaRecorder == null)
                {
                    initializeMediaRecord();
                }
                startAudioRecording();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudioRecording();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playLastStoredAudioMusic();
                mediaPlayerPlaying();
            }
        });
    }

    private void matchaudiofiles() {
        Wave wave1 = new Wave(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"voices/"+"0595.wav");
        Wave wave2 = new Wave(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"voices/"+"0897.wav");
//        Wave wave2 = new Wave(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"voices/"+"0595.wav");
//        Wave wave1 = new Wave(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"media/audio/notifications"+"path1.facebook_ringtone_pop.m4a");
//        Wave wave2 = new Wave(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Notifications(1)/"+"Avicii.mp3");
        FingerprintSimilarity fingerprintsimilarity = wave1.getFingerprintSimilarity(wave2);
        float score = fingerprintsimilarity.getScore();
        float similarity = fingerprintsimilarity.getSimilarity();
        result.setText("Similar Sound->Score:"+score+"\nSimilarity:"+similarity);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(requestCode == YOUR_REQUEST_CODE)
            {
                Log.d("TAG", "onRequestPermissionsResult: permitted");
                initializeMediaRecord();
            }
            else
            {
                Log.d("TAG", "onRequestPermissionsResult: not permitted");

            }
        }
    }

    private void mediaPlayerPlaying() {
        if(!mediaplayer.isPlaying())
        {
            stopAudioPlay();
        }
    }

    private void stopAudioPlay() {
        if(mediaplayer != null)
        {
            mediaplayer.stop();
            mediaplayer.release();
            mediaplayer = null;
        }
    }

    private void playLastStoredAudioMusic() {
        mediaplayer = new MediaPlayer();
        try
        {
            mediaplayer.setDataSource(voicestoragepath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaplayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaplayer.start();
        record.setEnabled(true);
        play.setEnabled(false);
    }

    private void stopAudioRecording() {
        if(mediaRecorder != null)
        {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"something is wrong",Toast.LENGTH_SHORT).show();
        }
        stop.setEnabled(false);
        play.setEnabled(true);
    }

    private void startAudioRecording() {
        try
        {
            mediaRecorder.prepare();
            mediaRecorder.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        record.setEnabled(false);
        stop.setEnabled(true);
    }

    private void initializeMediaRecord() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(voicestoragepath);
    }

//    private String generateVoiceFileName(int ln) {
//        StringBuilder sb = new StringBuilder(ln);
//        for(int i = 0; i < ln;i++) {
//            sb.append(AB.charAt(rnd.nextInt(AB.length())));
//        }
//            return sb.toString();
//
//    }

    private String generateVoiceFileName(int ln) {
        StringBuilder sb = new StringBuilder(ln);
        for(int i = 0; i < ln;i++) {
            sb.append(number.charAt(rnd.nextInt(number.length())));
        }
        return sb.toString();

    }

    private void hasSDCard() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDPresent)
        {
            System.out.println("SD Card Available");
        }
        else
        {
            System.out.println("SD Card Unavailable");
        }
    }
}
