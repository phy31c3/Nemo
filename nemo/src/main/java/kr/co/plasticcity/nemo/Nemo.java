package kr.co.plasticcity.nemo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.TextureView;

import kr.co.plasticcity.nemo.container.NemoSpace;
import kr.co.plasticcity.nemo.function.NemoConsumer;

/**
 * Created by JongsunYu on 2016-11-03.
 */

public final class Nemo extends TextureView implements TextureView.SurfaceTextureListener
{
	private NemoRenderer renderer;
	private NemoChoreographer choreographer;
	private NemoSpace space;
	
	public Nemo(Context context)
	{
		super(context);
		init();
	}
	
	public Nemo(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
	
	public Nemo(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init()
	{
		this.renderer = NemoRenderer.create();
		this.choreographer = NemoChoreographer.create(renderer);
		this.space = NemoSpace.Creator.create();
		this.setSurfaceTextureListener(this);
	}
	
	public void build(@NonNull NemoConsumer<NemoSpace> layout)
	{
		layout.accept(this.space);
	}
	
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
	{
		renderer.input(surface);
	}
	
	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
	{
		renderer.input(surface);
	}
	
	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
	{
		renderer.input(NemoRenderer.S.ON_DESTROY);
		return true;
	}
	
	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface)
	{
			/* do nothing */
	}
}