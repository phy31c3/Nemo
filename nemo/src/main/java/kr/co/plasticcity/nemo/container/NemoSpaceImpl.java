package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2017-01-08.
 */

final class NemoSpaceImpl extends NemoContainerImpl implements NemoSpace
{
	@Override
	public void addCube(@NonNull final NemoConsumer<NemoCube> layout)
	{
		final NemoCube cube = new NemoCubeImpl();
		super.add(cube);
		layout.accept(cube);
	}
	
	@Override
	public void addLayer(@NonNull final NemoConsumer<NemoLayer> layout)
	{
		final NemoLayer layer = new NemoLayerImpl();
		super.add(layer);
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