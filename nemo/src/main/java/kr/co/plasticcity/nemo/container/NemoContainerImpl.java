package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import java.util.Set;
import java.util.TreeSet;

import kr.co.plasticcity.nemo.core.NemoDrawable;
import kr.co.plasticcity.nemo.core.NemoDrawableImpl;
import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2017-04-02.
 */

abstract class NemoContainerImpl extends NemoDrawableImpl implements NemoContainer
{
	@NonNull
	private final Set<NemoDrawable> childs;
	
	NemoContainerImpl()
	{
		this.childs = new TreeSet<>();
	}
	
	@Override
	public final void add(@NonNull final NemoDrawable child)
	{
		childs.add(child);
	}
	
	void each(@NonNull final NemoConsumer<NemoDrawable> operation)
	{
		for (NemoDrawable child : childs)
		{
			operation.accept(child);
		}
	}
}