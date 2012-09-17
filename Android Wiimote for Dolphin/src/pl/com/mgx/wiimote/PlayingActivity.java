package pl.com.mgx.wiimote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class PlayingActivity extends Activity implements OnTouchListener, SensorEventListener {

	public static final String KEY_IP = "pl.com.mgx.wiimote.ip";
	public static final String KEY_PORT = "pl.com.mgx.wiimote.port";
	private int port;
	private InetAddress address;
	DatagramSocket socket;
	private int loopDelay = 50;
	
	private int buttonMask = 0;
	
	private float acc_x=0;
	private float acc_y=0;
	private float acc_z=0;
	private SensorManager mSensorManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        port = this.getIntent().getIntExtra(PlayingActivity.KEY_PORT, 0);
        try {
            address = InetAddress.getByName(this.getIntent().getStringExtra(PlayingActivity.KEY_IP));
			socket = new DatagramSocket();
		} catch (Exception e) {
			Log.e(getString(R.string.app_name), e.getLocalizedMessage(), e);
			this.finish();
		}
        
        findViewById(R.id.buttonA).setOnTouchListener(this);
        findViewById(R.id.buttonB).setOnTouchListener(this);
        
        Timer mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                   sendPacket();
                 }
            }, 0, loopDelay);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        
    }
/*
	@Override
	public void onClick(View v) {
		
	}
*/
    
    public void sendPacket() {
		ByteBuffer bb = ByteBuffer.allocate(64);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.put((byte) 0xde);
		bb.put((byte) 0);
		bb.put((byte)(UDPConsts.PACKET_BUTTONS|UDPConsts.PACKET_ACCEL));
		
		bb.putInt( (int)(acc_x*1024*1024) );
		bb.putInt( (int)(acc_y*1024*1024) );
		bb.putInt( (int)(acc_z*1024*1024) );
		bb.putInt( buttonMask );
		DatagramPacket packet = new DatagramPacket(bb.array(), bb.remaining(), address, port);
		try {
			socket.send(packet);
			//Log.v("asd", "packet send");
		} catch (IOException e) {
			Log.e("asd",e.getLocalizedMessage());
		}
    }
/*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_playing, menu);
        return true;
    }*/

	public boolean onTouch(View v, MotionEvent event) {
		if( event.getAction() == MotionEvent.ACTION_DOWN ) {
			switch( v.getId() ){
			case R.id.buttonA:
				buttonMask |= UDPConsts.UDPWM_BA;
				break;
			case R.id.buttonB:
				buttonMask |= UDPConsts.UDPWM_BB;
				break;
			}
		}
		else if( event.getAction() == MotionEvent.ACTION_UP ) {
			switch( v.getId() ){
			case R.id.buttonA:
				buttonMask &= ~UDPConsts.UDPWM_BA;
				break;
			case R.id.buttonB:
				buttonMask &= ~UDPConsts.UDPWM_BB;
				break;
			}
		}
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
        		acc_x=event.values[0];
                acc_y=event.values[1];
                acc_z=event.values[2];
            }
	}
}
