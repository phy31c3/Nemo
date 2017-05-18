package kr.co.plasticcity.nemo.demo;

import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import kr.co.plasticcity.jmata.JMata;
import kr.co.plasticcity.jmata.annotation.StateFunc;

/**
 * Created by JongsunYu on 2016-12-12.
 */

public class DevTimeChecker
{
	private static final String TAG = "DevTimeChecker";
	private static final String FILENAME = "nemo.devtime";
	
	public static DevTimeChecker create(TextView textView)
	{
		DevTimeChecker machine = new DevTimeChecker(textView);
		
		JMata.buildMachine(textView, builder ->
		{
			Start start = machine.new Start();
			Advance advance = machine.new Advance();
			
			builder.ifPresentThenReplaceWithThis(definer ->
			{
				definer.defineStartState(Start.class)
				       .whenInput("start").doThis(start::loadAndInit).switchTo(Advance.class)
				       .apply()
				
				       .defineState(Advance.class)
				       .whenEnterFrom(String.class).doThis(advance::startTimer)
				       .whenInput("second").doThis(advance::advanceSecond).switchToSelf()
				       .apply()
				
				       .defineTerminateWork(machine::release)
				
				       .buildAndRun();
			});
		});
		
		return machine;
	}
	
	private Timer timer;
	private TextView textView;
	private int devTime;
	
	public DevTimeChecker(TextView textView)
	{
		this.textView = textView;
	}
	
	public void input(String s)
	{
		JMata.inputTo(textView, s);
	}
	
	private void release()
	{
		if (timer != null)
		{
			timer.cancel();
			timer = null;
		}
	}
	
	private void save()
	{
		try
		{
			File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
			DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file));
			outputStream.writeInt(devTime);
			outputStream.close();
			Log.d(TAG, "write : " + devTime);
		}
		catch (IOException e)
		{
			Log.e(TAG, "IOException : write");
			e.printStackTrace();
		}
	}
	
	private void refreshText()
	{
		textView.post(() -> textView.setText(String.format("%d시간 %d분 %d초", devTime / 3600, devTime % 3600 / 60, devTime % 60)));
	}
	
	private class Start
	{
		@StateFunc
		void loadAndInit(String s)
		{
			final File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
			try
			{
				if (!file.exists())
				{
					file.createNewFile();
					Log.e(TAG, "create new file");
				}
				DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
				devTime = inputStream.readInt();
				inputStream.close();
				Log.d(TAG, "read : " + devTime);
				input("start");
			}
			catch (IOException e)
			{
				
				textView.post(() -> textView.setText("can't read file"));
				Log.e(TAG, "IOException : read");
				e.printStackTrace();
			}
		}
	}
	
	private class Advance
	{
		@StateFunc
		void startTimer(String s)
		{
			if ("start".equals(s))
			{
				timer = new Timer();
				timer.schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						input("second");
					}
				}, 0, 1000);
			}
		}
		
		@StateFunc
		void advanceSecond(String s)
		{
			++devTime;
			save();
			refreshText();
		}
	}
}