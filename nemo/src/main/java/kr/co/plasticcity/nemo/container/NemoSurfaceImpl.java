package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.core.NemoViewCreater;
import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2017-01-08.
 */

final class NemoSurfaceImpl extends NemoContainerImpl implements NemoSurface
{
	@Override
	public void addView(@NonNull final NemoConsumer<NemoViewCreater> layout)
	{
		final NemoViewCreater creator = NemoViewCreater.Creator.create(this);
		layout.accept(creator);
	}
	
	@Override
	public void draw()
	{
		/* TODO */
	}
}