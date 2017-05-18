package kr.co.plasticcity.nemo.core;

import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JongsunYu on 2017-04-02.
 */

public abstract class NemoDrawableImpl implements NemoDrawable
{
	private static final AtomicInteger ORDER_ID = new AtomicInteger(0);
	
	/**
	 * 모든 Drawable은 정렬을 위한 고유한 id를 지닌다.<br>
	 * (z값이 같은 두 Drawable이 겹친 경우, 먼저 정의 된 Drawable이 뒤에 정의 된 Drawable에 의해 가려짐)
	 */
	private final int orderId;
	
	protected NemoDrawableImpl()
	{
		this.orderId = ORDER_ID.getAndIncrement();
	}
	
	@Override
	public int compareTo(@NonNull final NemoDrawableImpl another)
	{
		return this.orderId - another.orderId;
	}
}