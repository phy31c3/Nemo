package kr.co.plasticcity.nemo;

import android.support.annotation.NonNull;
import android.view.Choreographer;

import kr.co.plasticcity.jmata.JMata;

/**
 * Created by JongsunYu on 2016-12-08.
 */

final class NemoChoreographer implements Choreographer.FrameCallback
{
	public synchronized static NemoChoreographer create(NemoRenderer renderer)
	{
		final NemoChoreographer choreographer = new NemoChoreographer(renderer);
		
		JMata.buildMachine(choreographer, builder ->
		{
			builder.ifPresentThenIgnoreThis(definer ->
			{
				definer.defineStartState(Pause.class)
				       .apply()
				
				       .defineState(Play.class)
				       .apply()
				
				       .buildAndRun();
			});
		});
		
		return choreographer;
	}
	
	public void input(final Object signal)
	{
		JMata.inputTo(this, signal);
	}
	
	private NemoRenderer renderer;
	
	private NemoChoreographer(@NonNull final NemoRenderer renderer)
	{
		this.renderer = renderer;
	}
	
	@Override
	public void doFrame(final long frameTimeNanos)
	{
		
	}
	
	private class Play
	{
		
	}
	
	private class Pause
	{
		
	}
}