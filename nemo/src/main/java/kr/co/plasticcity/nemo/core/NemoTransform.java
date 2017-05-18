package kr.co.plasticcity.nemo.core;

import kr.co.plasticcity.nemo.linear.NemoMat4;

/**
 * Created by JongsunYu on 2017-04-15.
 */

public interface NemoTransform
{
	class Creator
	{
		public static NemoTransform create()
		{
			return new NemoTransformImpl();
		}
	}
	
	NemoMat4 getTransformMat4();
	
	NemoTransform setAnchorPos(final float anchorPosX, final float anchorPosY);
	
	NemoTransform moveTo(final float x, final float y, final float z);
	
	NemoTransform moveAs(final float x, final float y, final float z);
	
	NemoTransform scaleTo(final float x, final float y);
	
	NemoTransform scaleAs(final float x, final float y);
	
	NemoTransform rotateTo(final float degree);
	
	NemoTransform rotateAs(final float degree);
}