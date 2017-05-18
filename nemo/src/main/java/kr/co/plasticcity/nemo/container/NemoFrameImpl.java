package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.core.NemoViewCreater;
import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2017-01-08.
 */

final class NemoFrameImpl extends NemoContainerImpl implements NemoFrame
{
	NemoFrameImpl()
	{
	}
	
	@Override
	public void addView(@NonNull final NemoConsumer<NemoViewCreater> layout)
	{
		final NemoViewCreater viewAdder = NemoViewCreater.Creator.create(this);
		layout.accept(viewAdder);
	}
	
	@Override
	public void draw()
	{
		/* TODO */
	}
}