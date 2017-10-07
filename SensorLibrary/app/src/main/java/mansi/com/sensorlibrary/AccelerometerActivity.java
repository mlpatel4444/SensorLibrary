package mansi.com.sensorlibrary;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sphere76 on 16/6/17.
 */

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    Sensor accelerometer;
    TextView x;
    TextView y;
    TextView z;
    TextView textView1;
    String sx, sy, sz;
    double dsx,dsy,dsz;
    SensorEvent sevent;
    int stepsCount = 0;
    private boolean mInitialized;
    private final float NOISE = (float) 2.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        textView1 = (TextView)findViewById(R.id.txtCount);
        x = (TextView) findViewById (R.id.textView2);
        y = (TextView) findViewById (R.id.textView3);
        z = (TextView) findViewById (R.id.textView4);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(accelerometer != null)
        {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor
                    (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            textView1.setText("Accelerometer Sensor is not Available");
        }

//        sensorManager.registerListener(this, sensorManager.getDefaultSensor
//                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            double xVal = event.values[0];
            double yVal = event.values[1];
            double zVal = event.values[2];

            final double alpha = 0.8;
            double[] gravity = {0,0,0};

            gravity[0] = alpha * gravity[0] + (1-alpha) * event.values[0];
            gravity[1] = alpha * gravity[0] + (1-alpha) * event.values[1];
            gravity[2] = alpha * gravity[0] + (1-alpha) * event.values[2];

            xVal = event.values[0] - gravity[0];
            yVal = event.values[1] - gravity[1];
            zVal = event.values[2] - gravity[2];

            if(!mInitialized)
            {
                dsx = xVal;
                dsy = yVal;
                dsz = zVal;
                mInitialized = true;
            }
            else
            {
                double deltaX = Math.abs(dsx - xVal);
                double deltaY = Math.abs(dsy - yVal);
                double deltaZ = Math.abs(dsz - zVal);

                if(deltaX < NOISE)
                {
                    deltaX = (float)0.0;
                }
                if(deltaY < NOISE)
                {
                    deltaY = (float)0.0;
                }
                if(deltaZ < NOISE)
                {
                    deltaZ = (float)0.0;
                }
                dsx = xVal;
                dsy = yVal;
                dsz = zVal;

                if (deltaX > deltaY) {
                    // Horizontal shake
                    // do something here if you like

                    Toast.makeText(getApplicationContext(),"x > y",Toast.LENGTH_SHORT).show();

                } else if (deltaY > deltaX) {
                    Toast.makeText(getApplicationContext(),"Y > X",Toast.LENGTH_SHORT).show();
                    // Vertical shake
                    // do something here if you like

                } else if ((deltaZ > deltaX) && (deltaZ > deltaY)) {
                    // Z shake
                     stepsCount = stepsCount + 1;
                    if (stepsCount > 0) {
                        textView1.setText(String.valueOf(stepsCount));
                    }

// Just for indication purpose, I have added vibrate function
                    // whenever our count moves past multiple of 10
                    if ((stepsCount % 10) == 0) {
                        //Util.Vibrate(this, 100);
                    }
                } else {
                    // no shake detected
                }
            }

            sx = "X Value : <font color = '#800080'> " + xVal + "</font>";
            sy = "Y Value : <font color = '#800080'> " + yVal + "</font>";
            sz = "Z Value : <font color = '#800080'> " + zVal + "</font>";

            x.setText(Html.fromHtml(sx));
            y.setText(Html.fromHtml(sy));
            z.setText(Html.fromHtml(sz));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
