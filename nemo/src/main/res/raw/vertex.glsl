uniform mat4 uMVPMatrix ;
attribute vec4 aVertex ;
attribute vec2 aTexCoord ;
varying vec2 vTexcoord ;

void main( void )
{
    gl_Position = uMVPMatrix * aVertex ;
    vTexcoord = aTexCoord ;
}