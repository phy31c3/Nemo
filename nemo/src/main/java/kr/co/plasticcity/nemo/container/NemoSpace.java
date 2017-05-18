package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.core.NemoDrawable;
import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2016-12-21.
 */

public interface NemoSpace extends NemoDrawable
{
	class Creator
	{
		public static NemoSpace create()
		{
			return new NemoSpaceImpl();
		}
	}
	
	void addCube(@NonNull NemoConsumer<NemoCube> consumer);
	
	void addLayer(@NonNull NemoConsumer<NemoLayer> consumer);
	
	void addFrame(@NonNull NemoConsumer<NemoFrame> consumer);
}