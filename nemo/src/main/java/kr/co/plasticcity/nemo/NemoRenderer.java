package kr.co.plasticcity.nemo;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

import kr.co.plasticcity.jmata.JMata;
import kr.co.plasticcity.jmata.annotation.StateFunc;
import kr.co.plasticcity.nemo.container.NemoSpace;
import kr.co.plasticcity.nemo.core.NemoDrawable;

/**
 * Created by JongsunYu on 2016-11-06.
 */

final class NemoRenderer
{
	private static final String TAG = NemoRenderer.class.getSimpleName();
	
	/*=========================================================================================================================
	 static >>
	 =========================================================================================================================*/
	public synchronized static NemoRenderer create()
	{
		final NemoRenderer renderer = new NemoRenderer();
		
		JMata.buildMachine(renderer, builder ->
		{
			builder.ifPresentThenIgnoreThis(definer ->
			{
				final Init init = renderer.new Init();
				final Rendering rendering = renderer.new Rendering();
				
				definer.defineStartState(Init.class)
				       .whenEnter(init::initEGL)
				       .whenInput(SurfaceTexture.class).doThis(init::initEGLSurface).switchToSelf()
				       .whenInput(S.ON_INIT).switchTo(Idle.class)
				       .apply()
				
				       .defineState(Idle.class)
				       .whenInput(Nemo.class).switchTo(Rendering.class)
				       .whenInput(SurfaceTexture.class).switchTo(SurfaceRefresh.class)
				       .whenInput(S.ON_DESTROY).switchTo(Destory.class)
				       .apply()
				
				       .defineState(Rendering.class)
				       .whenEnterFrom(NemoSpace.class).doThis(rendering::drawFrame)
				       .whenInput(S.ON_FINISH).switchTo(Idle.class)
				       .whenInput(SurfaceTexture.class).switchTo(SurfaceRefresh.class)
				       .whenInput(S.ON_DESTROY).switchTo(Destory.class)
				       .apply()
				
				       .defineState(SurfaceRefresh.class)
				       .whenInput(S.ON_REFRESH).switchTo(Idle.class)
				       .whenInput(S.ON_DESTROY).switchTo(Destory.class)
				       .apply()
				
				       .defineState(Destory.class)
				       .apply()
				
				       .buildAndRun();
			});
		});
		
		return renderer;
	}
	/*=========================================================================================================================
	 << static
	 =========================================================================================================================*/
	
	/*=========================================================================================================================
	 member >>
	 =========================================================================================================================*/
	public void input(final Object signal)
	{
		JMata.inputTo(this, signal);
	}
	
	private EGL10 egl;
	private EGLDisplay eglDisplay;
	private EGLConfig eglConfig;
	private EGLContext eglContext;
	private EGLSurface eglSurface;
	
	private NemoRenderer()
	{
		/* do nothing */
	}
	/*=========================================================================================================================
	 << member
	 =========================================================================================================================*/
	
	/*=========================================================================================================================
	 signals >>
	 =========================================================================================================================*/
	enum S
	{
		ON_INIT,
		ON_FINISH,
		ON_REFRESH,
		ON_DESTROY,
	}
	/*=========================================================================================================================
	 << signals
	 =========================================================================================================================*/
	
	/*=========================================================================================================================
	 states >>
	 =========================================================================================================================*/
	private class Init
	{
		@StateFunc
		void initEGL()
		{
			egl = (EGL10)EGLContext.getEGL();
			
			eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
			if (eglDisplay == EGL10.EGL_NO_DISPLAY)
			{
				Log.e(TAG, "egl init error : no display");
			}
			
			if (!egl.eglInitialize(eglDisplay, new int[2]))
			{
				Log.e(TAG, "egl init error : can't initialize");
			}
			
			EGLConfig[] configs = new EGLConfig[32];
			int[] numConfig = new int[1];
			int[] attribs = {
					EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
					EGL10.EGL_RED_SIZE, 8,
					EGL10.EGL_GREEN_SIZE, 8,
					EGL10.EGL_BLUE_SIZE, 8,
					EGL10.EGL_ALPHA_SIZE, 8,
					EGL10.EGL_DEPTH_SIZE, 16,
					EGL10.EGL_STENCIL_SIZE, 8,
					EGL10.EGL_NONE
			};
			if (!egl.eglChooseConfig(eglDisplay, attribs, configs, configs.length, numConfig))
			{
				Log.e(TAG, "egl init error : can't choose config");
			}
			
			for (int i = 0 ; i < numConfig[0] ; ++i)
			{
				int[] val = new int[1];
				int r, g, b, a, d, s;
				egl.eglGetConfigAttrib(eglDisplay, configs[i], EGL10.EGL_RED_SIZE, val);
				r = val[0];
				egl.eglGetConfigAttrib(eglDisplay, configs[i], EGL10.EGL_GREEN_SIZE, val);
				g = val[0];
				egl.eglGetConfigAttrib(eglDisplay, configs[i], EGL10.EGL_BLUE_SIZE, val);
				b = val[0];
				egl.eglGetConfigAttrib(eglDisplay, configs[i], EGL10.EGL_ALPHA_SIZE, val);
				a = val[0];
				egl.eglGetConfigAttrib(eglDisplay, configs[i], EGL10.EGL_DEPTH_SIZE, val);
				d = val[0];
				egl.eglGetConfigAttrib(eglDisplay, configs[i], EGL10.EGL_STENCIL_SIZE, val);
				s = val[0];
				
				if (r == 8 && g == 8 && b == 8 && a == 8 && d >= 16 && s >= 8)
				{
					eglConfig = configs[i];
					break;
				}
			}
			if (eglConfig == null)
			{
				Log.e(TAG, "egl init error : eglConfig is null");
			}
			
			eglContext = egl.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, new int[] {0x3098, 2, EGL10.EGL_NONE});
			if (eglContext == EGL10.EGL_NO_CONTEXT)
			{
				Log.e(TAG, "egl init error : can't create eglContext");
			}
		}
		
		@StateFunc
		void initEGLSurface(final SurfaceTexture surfaceTexture)
		{
			eglSurface = egl.eglCreateWindowSurface(eglDisplay, eglConfig, surfaceTexture, null);
			if (eglSurface == EGL10.EGL_NO_SURFACE)
			{
				Log.e(TAG, "egl init surface error : no surface");
			}
		}
	}
	
	private class Idle
	{
		/* nothing */
	}
	
	private class Rendering
	{
		@StateFunc
		void drawFrame(final NemoDrawable root)
		{
			root.draw();
		}
	}
	
	private class SurfaceRefresh
	{
		/* nothing */
	}
	
	private class Destory
	{
		/* nothing */
	}
	/*=========================================================================================================================
	 << states
	 =========================================================================================================================*/
}