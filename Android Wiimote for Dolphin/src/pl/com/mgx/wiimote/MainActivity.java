package pl.com.mgx.wiimote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch( v.getId() ){
		case R.id.buttonStart:
			Intent i = new Intent(this, PlayingActivity.class);
			String address = ((EditText)findViewById(R.id.inputAddress)).getText().toString();
			int port = Integer.parseInt(((EditText)findViewById(R.id.inputPort)).getText().toString());
			i.putExtra(PlayingActivity.KEY_IP, address);
			i.putExtra(PlayingActivity.KEY_PORT, port);
			startActivity(i);
			break;
		}
	}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
}
