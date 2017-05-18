package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.core.NemoDrawable;
import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2016-12-20.
 */

public interface NemoCube extends NemoDrawable
{
	void addLayer(@NonNull NemoConsumer<NemoLayer> consumer);
	
	void addFrame(@NonNull NemoConsumer<NemoFrame> consumer);
}