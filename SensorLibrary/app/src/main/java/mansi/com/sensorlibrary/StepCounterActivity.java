package mansi.com.sensorlibrary;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sphere76 on 21/6/17.
 */
public class StepCounterActivity extends AppCompatActivity{

    TextView step_counter,step_detector;
    Sensor step_counter_sensor,step_detector_sensor;
    Button start,stop;

    private long time_stamp;
    private Thread thread;
    private Handler handler;
    private boolean isRunning = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        step_counter = (TextView)findViewById(R.id.step_counter);
        step_detector = (TextView)findViewById(R.id.step_detector);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForSensorEvents();
                setUpthread();
            }
        });


    }

    private void setUpthread() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                step_detector.setText(DateUtils.getRelativeTimeSpanString(time_stamp));
            }
        };

        thread = new Thread()
        {
            @Override
            public void run()
            {
              while (isRunning)
              {
                  try
                  {
                      Thread.sleep(5000);
                      handler.sendEmptyMessage(0);
                  }
                  catch (InterruptedException e)
                  {
                      e.printStackTrace();
                  }
              }
            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        thread.interrupt();
    }

    private void registerForSensorEvents() {
        SensorManager sensormanager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        step_counter_sensor = sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        step_detector_sensor = sensormanager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);


        if(step_counter_sensor != null) {
            sensormanager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float steps = event.values[0];
                    step_counter.setText("Steps Count:" + steps);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Step Counter Sensor not Available",Toast.LENGTH_SHORT).show();
        }

        if(step_detector_sensor != null) {
            sensormanager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    time_stamp = event.timestamp / 1000000;
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, sensormanager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Step Detector Sensor not Available",Toast.LENGTH_SHORT).show();
        }
    }
}
