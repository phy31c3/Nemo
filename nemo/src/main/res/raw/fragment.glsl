#ifdef GL_FRAGMENT_PRECISION_HIGH
   precision highp float ;
#else
   precision mediump float ;
#endif

uniform sampler2D uSampler ;
uniform float uAlpha ;
uniform vec4 uColor ;
varying vec2 vTexcoord ;

void main( void )
{
	vec4 texColor = texture2D(uSampler, vTexcoord) ;
	texColor.a *= uAlpha ;

	if (uColor.a > 0.0)
	{
		gl_FragColor = vec4(
			texColor.r * (1.0 - uColor.a) + uColor.r * uColor.a,
			texColor.g * (1.0 - uColor.a) + uColor.g * uColor.a,
			texColor.b * (1.0 - uColor.a) + uColor.b * uColor.a,
			texColor.a) ;
	}
	else
	{
    	gl_FragColor = texColor ;
    }
}