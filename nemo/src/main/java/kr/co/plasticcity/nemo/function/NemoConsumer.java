package kr.co.plasticcity.nemo.function;

import android.support.annotation.NonNull;

/**
 * Created by JongsunYu on 2016-12-21.
 */

@FunctionalInterface
public interface NemoConsumer<T>
{
	void accept(@NonNull final T t);
}