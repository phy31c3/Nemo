package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2017-01-08.
 */

final class NemoCubeImpl extends NemoContainerImpl implements NemoCube
{
	@Override
	public void addLayer(@NonNull final NemoConsumer<NemoLayer> layout)
	{
		final NemoLayer layer = new NemoLayerImpl();
		add(layer);
		layout.accept(layer);
	}
	
	@Override
	public void addFrame(@NonNull final NemoConsumer<NemoFrame> layout)
	{
		final NemoFrame frame = new NemoFrameImpl();
		super.add(frame);
		layout.accept(frame);
	}
	
	@Override
	public void draw()
	{
		/* TODO */
	}
}