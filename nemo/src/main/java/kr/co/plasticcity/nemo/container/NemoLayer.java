package kr.co.plasticcity.nemo.container;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.core.NemoDrawable;
import kr.co.plasticcity.nemo.core.NemoViewCreater;
import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2016-12-17.
 */

public interface NemoLayer extends NemoDrawable
{
	void addView(@NonNull NemoConsumer<NemoViewCreater> consumer);
}