package kr.co.plasticcity.nemo.core;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.container.NemoContainer;

/**
 * Created by JongsunYu on 2017-01-08.
 */

public interface NemoViewCreater
{
	class Creator
	{
		public static NemoViewCreater create(@NonNull NemoContainer container)
		{
			return new NemoViewCreaterImpl(container);
		}
	}
}