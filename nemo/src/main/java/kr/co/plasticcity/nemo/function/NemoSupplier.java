package kr.co.plasticcity.nemo.function;

import android.support.annotation.NonNull;

/**
 * Created by JongsunYu on 2017-01-02.
 */

@FunctionalInterface
public interface NemoSupplier<T>
{
	@NonNull
	T get();
}