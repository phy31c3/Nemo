package kr.co.plasticcity.nemo.core;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.container.NemoContainer;

/**
 * Created by JongsunYu on 2017-01-18.
 */

final class NemoViewCreaterImpl implements NemoViewCreater
{
	@NonNull
	private final NemoContainer container;
	
	NemoViewCreaterImpl(@NonNull NemoContainer container)
	{
		this.container = container;
	}
}