package kr.co.plasticcity.nemo.core;

import android.support.annotation.NonNull;

import kr.co.plasticcity.nemo.linear.NemoMat4;

/**
 * Created by JongsunYu on 2017-04-15.
 */

class NemoTransformImpl implements NemoTransform
{
	@NonNull
	private final NemoMat4 mat4;
	
	private float posX;
	private float posY;
	private float posZ;
	private float scaleX;
	private float scaleY;
	private float angle;
	private float anchorPosX;
	private float anchorPosY;
	
	private boolean updated;
	
	NemoTransformImpl()
	{
		this.mat4 = new NemoMat4();
		this.updated = false;
	}
	
	@Override
	public NemoMat4 getTransformMat4()
	{
		if (updated)
		{
			updated = false;
			if (angle != 0.0f) { mat4.setRotateZ(angle); }
			else { mat4.setIdentity(); }
			if (posX != 0.0f) { mat4.lTranslateX(posX); }
			if (posY != 0.0f) { mat4.lTranslateY(posY); }
			if (posZ != 0.0f) { mat4.lTranslateZ(posZ); }
			if (scaleX != 1.0f) { mat4.rScaleX(scaleX); }
			if (scaleY != 1.0f) { mat4.rScaleY(scaleY); }
			if (anchorPosX != 0.0f) { mat4.rTranslateX(anchorPosX); }
			if (anchorPosY != 0.0f) { mat4.rTranslateY(anchorPosY); }
		}
		return mat4;
	}
	
	@Override
	public NemoTransform setAnchorPos(final float x, final float y)
	{
		if (anchorPosX != x || anchorPosY != y)
		{
			updated = true;
			anchorPosX = x;
			anchorPosY = y;
		}
		return this;
	}
	
	@Override
	public NemoTransform moveTo(final float x, final float y, final float z)
	{
		if (x != posX || y != posY || z != posZ)
		{
			updated = true;
			posX = x;
			posY = y;
			posZ = z;
		}
		return this;
	}
	
	@Override
	public NemoTransform moveAs(final float x, final float y, final float z)
	{
		if (x != 0.0f || y != 0.0f || z != 0.0f)
		{
			updated = true;
			posX += x;
			posY += y;
			posZ += z;
		}
		return this;
	}
	
	@Override
	public NemoTransform scaleTo(final float x, final float y)
	{
		if (x != scaleX || y != scaleY)
		{
			updated = true;
			scaleX = x;
			scaleY = y;
		}
		return this;
	}
	
	@Override
	public NemoTransform scaleAs(final float x, final float y)
	{
		if (x != 1.0f || y != 1.0f)
		{
			updated = true;
			scaleX *= x;
			scaleY *= y;
		}
		return this;
	}
	
	@Override
	public NemoTransform rotateTo(final float degree)
	{
		if (degree != angle)
		{
			updated = true;
			angle = degree;
		}
		return this;
	}
	
	@Override
	public NemoTransform rotateAs(final float degree)
	{
		if (angle != 0.0f)
		{
			updated = true;
			angle += degree;
		}
		return this;
	}
}
