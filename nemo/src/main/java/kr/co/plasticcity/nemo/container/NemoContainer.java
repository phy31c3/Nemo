package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.core.NemoDrawable;

/**
 * Created by JongsunYu on 2017-03-19.
 */

public interface NemoContainer
{
	void add(@NonNull NemoDrawable child);
}