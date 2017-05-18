package kr.co.plasticcity.nemo.function;

import android.support.annotation.NonNull;

/**
 * Created by JongsunYu on 2017-01-08.
 */

@FunctionalInterface
public interface NemoFunction<T, R>
{
	@NonNull
	R apply(@NonNull final T t);
}