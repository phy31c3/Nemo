package kr.co.plasticcity.nemo.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import kr.co.plasticcity.jmata.JMata;
import kr.co.plasticcity.nemo.Nemo;

public class MainActivity extends AppCompatActivity
{
	private DevTimeChecker devTimeChecker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		JMata.initialize(log -> Log.d("TraceJMata", log));

		/*============================================== init time checker >> ==============================================*/
		TextView txvTimer = (TextView)findViewById(R.id.txv_timer);
		devTimeChecker = DevTimeChecker.create(txvTimer);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
		{
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
		}
		else
		{
			devTimeChecker.input("start");
		}
		/*============================================== << init time checker ==============================================*/

		/*============================================== build nemo >> ==============================================*/
		Nemo nemo = (Nemo)findViewById(R.id.nemo);
		nemo.build(space ->
		{
			space.addCube(cube ->
			{
				cube.addFrame(frame ->
				{
					frame.addView(creator ->
					{
						/* sample */
					});
				});
			});
		});
		/*============================================== << build nemo ==============================================*/
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		JMata.release();
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			devTimeChecker.input("start");
		}
		else
		{
			finish();
		}
	}
}