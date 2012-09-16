package pl.com.mgx.wiimote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class PlayingActivity extends Activity implements OnClickListener {

	public static final String KEY_IP = "pl.com.mgx.wiimote.ip";
	public static final String KEY_PORT = "pl.com.mgx.wiimote.port";
	private int port;
	private InetAddress address;
	DatagramSocket socket;
	
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
        
        findViewById(R.id.buttonA).setOnClickListener(this);
        findViewById(R.id.buttonB).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		ByteBuffer bb = ByteBuffer.allocate(7);
		bb.put((byte) 0xde);
		bb.put((byte) 0);
		bb.put((byte)UDPConsts.PACKET_BUTTONS);
		bb.order(ByteOrder.BIG_ENDIAN);
		switch( v.getId() ){
			case R.id.buttonA:
				bb.putInt(UDPConsts.UDPWM_BA );
				break;
			case R.id.buttonB:
				bb.putInt(UDPConsts.UDPWM_BB );
				break;
			default:
				return;
		}
		DatagramPacket packet = new DatagramPacket(bb.array(), bb.array().length, address, port);
		try {
			socket.send(packet);
			Log.v("asd", "packet send");
		} catch (IOException e) {
			Log.e("asd",e.getLocalizedMessage());
		}
	}

    
/*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_playing, menu);
        return true;
    }*/
}
