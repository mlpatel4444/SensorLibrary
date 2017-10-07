package mansi.com.sensorlibrary;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sphere76 on 16/6/17.
 */
public class AvailableSensorActivity extends AppCompatActivity{

    TextView sensoritot,sensoridisponibili;
    SensorManager sensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_avilable);

        sensoritot = (TextView)findViewById(R.id.sensoritot);
        sensoridisponibili = (TextView)findViewById(R.id.sensoridisponibili);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //List<Sensor> sensorList = sensorManager.getSensorList(SensorManager.SENSOR_ALL);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        sensoritot.setText(sensorList.size()+" "+this.getString(R.string.sensors));

        String ssensorlist = new String("");
        Sensor tmp;
        int x,i;
        for(i=0;i<sensorList.size();i++)
        {
            tmp = sensorList.get(i);
            ssensorlist = "\n"+ssensorlist+tmp.getName()+"\n\n";
        }

        if(i>0)
        {
            ssensorlist = getString(R.string.sensors)+":"+ssensorlist;
            sensoridisponibili.setText(ssensorlist);
        }

    }
}
