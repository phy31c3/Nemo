package kr.co.plasticcity.nemo.linear;

/**
 * Created by JongsunYu on 2017-01-05.
 */

final public class NemoMat4
{
	private static final float PI = 3.14159265f;
	
	private static final float[] IDENTITY = new float[] {
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
	};
	
	private static final float[] ZERO = new float[] {
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f
	};
	
	static final int _1_1 = 0x0;
	static final int _2_1 = 0x1;
	static final int _3_1 = 0x2;
	static final int _4_1 = 0x3;
	static final int _1_2 = 0x4;
	static final int _2_2 = 0x5;
	static final int _3_2 = 0x6;
	static final int _4_2 = 0x7;
	static final int _1_3 = 0x8;
	static final int _2_3 = 0x9;
	static final int _3_3 = 0xA;
	static final int _4_3 = 0xB;
	static final int _1_4 = 0xC;
	static final int _2_4 = 0xD;
	static final int _3_4 = 0xE;
	static final int _4_4 = 0xF;
	
	float[] m = new float[16];
	
	/**
	 * 단위행열로 초기화
	 */
	public NemoMat4()
	{
		setIdentity();
	}
	
	public NemoMat4(final NemoMat4 mat4)
	{
		System.arraycopy(mat4.m, 0, m, 0, m.length);
	}
	
	/*=========================================================================================================================
	 basic >>
	 =========================================================================================================================*/
	public NemoMat4 setIdentity()
	{
		System.arraycopy(IDENTITY, 0, m, 0, m.length);
		return this;
	}
	
	public NemoMat4 setZero()
	{
		System.arraycopy(ZERO, 0, m, 0, m.length);
		return this;
	}
	/*=========================================================================================================================
	 << basic
	 =========================================================================================================================*/
	
	/*=========================================================================================================================
	 multiply >>
	 =========================================================================================================================*/
	public float[] multiply(final NemoMat4 rhs)
	{
		float[] rst = new float[16];
		
		rst[_1_1] = m[_1_1] * rhs.m[_1_1] + m[_1_2] * rhs.m[_2_1] + m[_1_3] * rhs.m[_3_1] + m[_1_4] * rhs.m[_4_1];
		rst[_2_1] = m[_2_1] * rhs.m[_1_1] + m[_2_2] * rhs.m[_2_1] + m[_2_3] * rhs.m[_3_1] + m[_2_4] * rhs.m[_4_1];
		rst[_3_1] = m[_3_1] * rhs.m[_1_1] + m[_3_2] * rhs.m[_2_1] + m[_3_3] * rhs.m[_3_1] + m[_3_4] * rhs.m[_4_1];
		rst[_4_1] = m[_4_1] * rhs.m[_1_1] + m[_4_2] * rhs.m[_2_1] + m[_4_3] * rhs.m[_3_1] + m[_4_4] * rhs.m[_4_1];
		rst[_1_2] = m[_1_1] * rhs.m[_1_2] + m[_1_2] * rhs.m[_2_2] + m[_1_3] * rhs.m[_3_2] + m[_1_4] * rhs.m[_4_2];
		rst[_2_2] = m[_2_1] * rhs.m[_1_2] + m[_2_2] * rhs.m[_2_2] + m[_2_3] * rhs.m[_3_2] + m[_2_4] * rhs.m[_4_2];
		rst[_3_2] = m[_3_1] * rhs.m[_1_2] + m[_3_2] * rhs.m[_2_2] + m[_3_3] * rhs.m[_3_2] + m[_3_4] * rhs.m[_4_2];
		rst[_4_2] = m[_4_1] * rhs.m[_1_2] + m[_4_2] * rhs.m[_2_2] + m[_4_3] * rhs.m[_3_2] + m[_4_4] * rhs.m[_4_2];
		rst[_1_3] = m[_1_1] * rhs.m[_1_3] + m[_1_2] * rhs.m[_2_3] + m[_1_3] * rhs.m[_3_3] + m[_1_4] * rhs.m[_4_3];
		rst[_2_3] = m[_2_1] * rhs.m[_1_3] + m[_2_2] * rhs.m[_2_3] + m[_2_3] * rhs.m[_3_3] + m[_2_4] * rhs.m[_4_3];
		rst[_3_3] = m[_3_1] * rhs.m[_1_3] + m[_3_2] * rhs.m[_2_3] + m[_3_3] * rhs.m[_3_3] + m[_3_4] * rhs.m[_4_3];
		rst[_4_3] = m[_4_1] * rhs.m[_1_3] + m[_4_2] * rhs.m[_2_3] + m[_4_3] * rhs.m[_3_3] + m[_4_4] * rhs.m[_4_3];
		rst[_1_4] = m[_1_1] * rhs.m[_1_4] + m[_1_2] * rhs.m[_2_4] + m[_1_3] * rhs.m[_3_4] + m[_1_4] * rhs.m[_4_4];
		rst[_2_4] = m[_2_1] * rhs.m[_1_4] + m[_2_2] * rhs.m[_2_4] + m[_2_3] * rhs.m[_3_4] + m[_2_4] * rhs.m[_4_4];
		rst[_3_4] = m[_3_1] * rhs.m[_1_4] + m[_3_2] * rhs.m[_2_4] + m[_3_3] * rhs.m[_3_4] + m[_3_4] * rhs.m[_4_4];
		rst[_4_4] = m[_4_1] * rhs.m[_1_4] + m[_4_2] * rhs.m[_2_4] + m[_4_3] * rhs.m[_3_4] + m[_4_4] * rhs.m[_4_4];
		
		return rst;
	}
	
	public NemoMat4 rMultiply(final NemoMat4 rhs)
	{
		m = multiply(rhs);
		return this;
	}
	
	public NemoMat4 lMultiply(final NemoMat4 lhs)
	{
		m = lhs.multiply(this);
		return this;
	}
	
	public NemoVec4 rMultiply(final NemoVec4 rhs)
	{
		rhs.lMultiply(this);
		return rhs;
	}
	/*=========================================================================================================================
	 << multiply
	 =========================================================================================================================*/
	
	/*=========================================================================================================================
	 model transform >>
	 =========================================================================================================================*/
	public NemoMat4 setTranslate(final float x, final float y, final float z)
	{
		setIdentity();
		m[_1_4] = x;
		m[_2_4] = y;
		m[_3_4] = z;
		return this;
	}
	
	public NemoMat4 setTranslate(final NemoVec4 v)
	{
		return setTranslate(v.x(), v.y(), v.z());
	}
	
	public NemoMat4 setTranslateX(final float x)
	{
		setIdentity();
		m[_1_4] = x;
		return this;
	}
	
	public NemoMat4 setTranslateY(final float y)
	{
		setIdentity();
		m[_2_4] = y;
		return this;
	}
	
	public NemoMat4 setTranslateZ(final float z)
	{
		setIdentity();
		m[_3_4] = z;
		return this;
	}
	
	public NemoMat4 rTranslate(final float x, final float y, final float z)
	{
		m[_1_4] += m[_1_1] * x + m[_1_2] * y + m[_1_3] * z;
		m[_2_4] += m[_2_1] * x + m[_2_2] * y + m[_2_3] * z;
		m[_3_4] += m[_3_1] * x + m[_3_2] * y + m[_3_3] * z;
		m[_4_4] += m[_4_1] * x + m[_4_2] * y + m[_4_3] * z;
		return this;
	}
	
	public NemoMat4 rTranslate(final NemoVec4 v)
	{
		return rTranslate(v.x(), v.y(), v.z());
	}
	
	public NemoMat4 rTranslateX(final float x)
	{
		m[_1_4] += m[_1_1] * x;
		m[_2_4] += m[_2_1] * x;
		m[_3_4] += m[_3_1] * x;
		m[_4_4] += m[_4_1] * x;
		return this;
	}
	
	public NemoMat4 rTranslateY(final float y)
	{
		m[_1_4] += m[_1_2] * y;
		m[_2_4] += m[_2_2] * y;
		m[_3_4] += m[_3_2] * y;
		m[_4_4] += m[_4_2] * y;
		return this;
	}
	
	public NemoMat4 rTranslateZ(final float z)
	{
		m[_1_4] += m[_1_3] * z;
		m[_2_4] += m[_2_3] * z;
		m[_3_4] += m[_3_3] * z;
		m[_4_4] += m[_4_3] * z;
		return this;
	}
	
	public NemoMat4 lTranslate(final float x, final float y, final float z)
	{
		m[_1_1] += m[_4_1] * x;
		m[_2_1] += m[_4_1] * y;
		m[_3_1] += m[_4_1] * z;
		m[_1_2] += m[_4_2] * x;
		m[_2_2] += m[_4_2] * y;
		m[_3_2] += m[_4_2] * z;
		m[_1_3] += m[_4_3] * x;
		m[_2_3] += m[_4_3] * y;
		m[_3_3] += m[_4_3] * z;
		m[_1_4] += m[_4_4] * x;
		m[_2_4] += m[_4_4] * y;
		m[_3_4] += m[_4_4] * z;
		return this;
	}
	
	public NemoMat4 lTranslate(final NemoVec4 v)
	{
		return lTranslate(v.x(), v.y(), v.z());
	}
	
	public NemoMat4 lTranslateX(final float x)
	{
		m[_1_1] += m[_4_1] * x;
		m[_1_2] += m[_4_2] * x;
		m[_1_3] += m[_4_3] * x;
		m[_1_4] += m[_4_4] * x;
		return this;
	}
	
	public NemoMat4 lTranslateY(final float y)
	{
		m[_2_1] += m[_4_1] * y;
		m[_2_2] += m[_4_2] * y;
		m[_2_3] += m[_4_3] * y;
		m[_2_4] += m[_4_4] * y;
		return this;
	}
	
	public NemoMat4 lTranslateZ(final float z)
	{
		m[_3_1] += m[_4_1] * z;
		m[_3_2] += m[_4_2] * z;
		m[_3_3] += m[_4_3] * z;
		m[_3_4] += m[_4_4] * z;
		return this;
	}
	
	public NemoMat4 setRotate(float x, float y, float z, final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		final float n = (float)Math.sqrt(x * x + y * y + z * z);
		if (n != 0.0f && n != 1.0f)
		{
			x /= n;
			y /= n;
			z /= n;
		}
		
		final float ncos = 1.0f - cos;
		final float xsin = x * sin;
		final float ysin = y * sin;
		final float zsin = z * sin;
		final float xy = x * y;
		final float yz = y * z;
		final float xz = x * z;
		
		setZero();
		m[_1_1] = x * x * ncos + cos;
		m[_2_1] = xy * ncos + zsin;
		m[_3_1] = xz * ncos - ysin;
		m[_1_2] = xy * ncos - zsin;
		m[_2_2] = y * y * ncos + cos;
		m[_3_2] = yz * ncos + xsin;
		m[_1_3] = xz * ncos + ysin;
		m[_2_3] = yz * ncos - xsin;
		m[_3_3] = z * z * ncos + cos;
		m[_4_4] = 1.0f;
		
		return this;
	}
	
	public NemoMat4 setRotateX(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		setZero();
		m[_1_1] = 1.0f;
		m[_2_2] = cos;
		m[_3_2] = sin;
		m[_2_3] = -sin;
		m[_3_3] = cos;
		m[_4_4] = 1.0f;
		
		return this;
	}
	
	public NemoMat4 setRotateY(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		setZero();
		m[_1_1] = cos;
		m[_3_1] = -sin;
		m[_2_2] = 1.0f;
		m[_1_3] = sin;
		m[_3_3] = cos;
		m[_4_4] = 1.0f;
		
		return this;
	}
	
	public NemoMat4 setRotateZ(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		setZero();
		m[_1_1] = cos;
		m[_2_1] = sin;
		m[_1_2] = -sin;
		m[_2_2] = cos;
		m[_3_3] = 1.0f;
		m[_4_4] = 1.0f;
		
		return this;
	}
	
	public NemoMat4 rRotate(final float x, final float y, final float z, final float degree)
	{
		final NemoMat4 rhs = setRotate(x, y, z, degree);
		final float[] rst = new float[16];
		
		rst[_1_1] = m[_1_1] * rhs.m[_1_1] + m[_1_2] * rhs.m[_2_1] + m[_1_3] * rhs.m[_3_1];
		rst[_2_1] = m[_2_1] * rhs.m[_1_1] + m[_2_2] * rhs.m[_2_1] + m[_2_3] * rhs.m[_3_1];
		rst[_3_1] = m[_3_1] * rhs.m[_1_1] + m[_3_2] * rhs.m[_2_1] + m[_3_3] * rhs.m[_3_1];
		rst[_4_1] = m[_4_1] * rhs.m[_1_1] + m[_4_2] * rhs.m[_2_1] + m[_4_3] * rhs.m[_3_1];
		rst[_1_2] = m[_1_1] * rhs.m[_1_2] + m[_1_2] * rhs.m[_2_2] + m[_1_3] * rhs.m[_3_2];
		rst[_2_2] = m[_2_1] * rhs.m[_1_2] + m[_2_2] * rhs.m[_2_2] + m[_2_3] * rhs.m[_3_2];
		rst[_3_2] = m[_3_1] * rhs.m[_1_2] + m[_3_2] * rhs.m[_2_2] + m[_3_3] * rhs.m[_3_2];
		rst[_4_2] = m[_4_1] * rhs.m[_1_2] + m[_4_2] * rhs.m[_2_2] + m[_4_3] * rhs.m[_3_2];
		rst[_1_3] = m[_1_1] * rhs.m[_1_3] + m[_1_2] * rhs.m[_2_3] + m[_1_3] * rhs.m[_3_3];
		rst[_2_3] = m[_2_1] * rhs.m[_1_3] + m[_2_2] * rhs.m[_2_3] + m[_2_3] * rhs.m[_3_3];
		rst[_3_3] = m[_3_1] * rhs.m[_1_3] + m[_3_2] * rhs.m[_2_3] + m[_3_3] * rhs.m[_3_3];
		rst[_4_3] = m[_4_1] * rhs.m[_1_3] + m[_4_2] * rhs.m[_2_3] + m[_4_3] * rhs.m[_3_3];
		rst[_1_4] = m[_1_4];
		rst[_2_4] = m[_2_4];
		rst[_3_4] = m[_3_4];
		rst[_4_4] = m[_4_4];
		
		m = rst;
		return this;
	}
	
	public NemoMat4 rRotateX(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		final float[] col2 = new float[4];
		col2[0] = m[_1_2];
		col2[1] = m[_2_2];
		col2[2] = m[_3_2];
		col2[3] = m[_4_2];
		
		m[_1_2] = m[_1_3] * sin + col2[0] * cos;
		m[_1_3] = m[_1_3] * cos + col2[0] * -sin;
		m[_2_2] = m[_2_3] * sin + col2[1] * cos;
		m[_2_3] = m[_2_3] * cos + col2[1] * -sin;
		m[_3_2] = m[_3_3] * sin + col2[2] * cos;
		m[_3_3] = m[_3_3] * cos + col2[2] * -sin;
		m[_4_2] = m[_4_3] * sin + col2[3] * cos;
		m[_4_3] = m[_4_3] * cos + col2[3] * -sin;
		
		return this;
	}
	
	public NemoMat4 rRotateY(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		final float[] col1 = new float[4];
		col1[0] = m[_1_1];
		col1[1] = m[_2_1];
		col1[2] = m[_3_1];
		col1[3] = m[_4_1];
		
		m[_1_1] = col1[0] * cos + m[_1_3] * -sin;
		m[_1_3] = col1[0] * sin + m[_1_3] * cos;
		m[_2_1] = col1[1] * cos + m[_2_3] * -sin;
		m[_2_3] = col1[1] * sin + m[_2_3] * cos;
		m[_3_1] = col1[2] * cos + m[_3_3] * -sin;
		m[_3_3] = col1[2] * sin + m[_3_3] * cos;
		m[_4_1] = col1[3] * cos + m[_4_3] * -sin;
		m[_4_3] = col1[3] * sin + m[_4_3] * cos;
		
		return this;
	}
	
	public NemoMat4 rRotateZ(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		final float[] col1 = new float[4];
		col1[0] = m[_1_1];
		col1[1] = m[_2_1];
		col1[2] = m[_3_1];
		col1[3] = m[_4_1];
		
		m[_1_1] = m[_1_2] * sin + col1[0] * cos;
		m[_1_2] = m[_1_2] * cos + col1[0] * -sin;
		m[_2_1] = m[_2_2] * sin + col1[1] * cos;
		m[_2_2] = m[_2_2] * cos + col1[1] * -sin;
		m[_3_1] = m[_3_2] * sin + col1[2] * cos;
		m[_3_2] = m[_3_2] * cos + col1[2] * -sin;
		m[_4_1] = m[_4_2] * sin + col1[3] * cos;
		m[_4_2] = m[_4_2] * cos + col1[3] * -sin;
		
		return this;
	}
	
	public NemoMat4 lRotate(final float x, final float y, final float z, final float degree)
	{
		final NemoMat4 lhs = setRotate(x, y, z, degree);
		final float[] rst = new float[16];
		
		rst[_1_1] = lhs.m[_1_1] * m[_1_1] + lhs.m[_1_2] * m[_2_1] + lhs.m[_1_3] * m[_3_1];
		rst[_2_1] = lhs.m[_2_1] * m[_1_1] + lhs.m[_2_2] * m[_2_1] + lhs.m[_2_3] * m[_3_1];
		rst[_3_1] = lhs.m[_3_1] * m[_1_1] + lhs.m[_3_2] * m[_2_1] + lhs.m[_3_3] * m[_3_1];
		rst[_4_1] = m[_4_1];
		rst[_1_2] = lhs.m[_1_1] * m[_1_2] + lhs.m[_1_2] * m[_2_2] + lhs.m[_1_3] * m[_3_2];
		rst[_2_2] = lhs.m[_2_1] * m[_1_2] + lhs.m[_2_2] * m[_2_2] + lhs.m[_2_3] * m[_3_2];
		rst[_3_2] = lhs.m[_3_1] * m[_1_2] + lhs.m[_3_2] * m[_2_2] + lhs.m[_3_3] * m[_3_2];
		rst[_4_2] = m[_4_2];
		rst[_1_3] = lhs.m[_1_1] * m[_1_3] + lhs.m[_1_2] * m[_2_3] + lhs.m[_1_3] * m[_3_3];
		rst[_2_3] = lhs.m[_2_1] * m[_1_3] + lhs.m[_2_2] * m[_2_3] + lhs.m[_2_3] * m[_3_3];
		rst[_3_3] = lhs.m[_3_1] * m[_1_3] + lhs.m[_3_2] * m[_2_3] + lhs.m[_3_3] * m[_3_3];
		rst[_4_3] = m[_4_3];
		rst[_1_4] = lhs.m[_1_1] * m[_1_4] + lhs.m[_1_2] * m[_2_4] + lhs.m[_1_3] * m[_3_4];
		rst[_2_4] = lhs.m[_2_1] * m[_1_4] + lhs.m[_2_2] * m[_2_4] + lhs.m[_2_3] * m[_3_4];
		rst[_3_4] = lhs.m[_3_1] * m[_1_4] + lhs.m[_3_2] * m[_2_4] + lhs.m[_3_3] * m[_3_4];
		rst[_4_4] = m[_4_4];
		
		m = rst;
		return this;
	}
	
	public NemoMat4 lRotateX(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		float tmp;
		m[_2_1] = (tmp = m[_2_1]) * cos + m[_3_1] * -sin;
		m[_3_1] = tmp * sin + m[_3_1] * cos;
		m[_2_2] = (tmp = m[_2_2]) * cos + m[_3_2] * -sin;
		m[_3_2] = tmp * sin + m[_3_2] * cos;
		m[_2_3] = (tmp = m[_2_3]) * cos + m[_3_3] * -sin;
		m[_3_3] = tmp * sin + m[_3_3] * cos;
		m[_2_4] = (tmp = m[_2_4]) * cos + m[_3_4] * -sin;
		m[_3_4] = tmp * sin + m[_3_4] * cos;
		
		return this;
	}
	
	public NemoMat4 lRotateY(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		float tmp;
		m[_1_1] = (tmp = m[_1_1]) * cos + m[_3_1] * sin;
		m[_3_1] = tmp * -sin + m[_3_1] * cos;
		m[_1_2] = (tmp = m[_1_2]) * cos + m[_3_2] * sin;
		m[_3_2] = tmp * -sin + m[_3_2] * cos;
		m[_1_3] = (tmp = m[_1_3]) * cos + m[_3_3] * sin;
		m[_3_3] = tmp * -sin + m[_3_3] * cos;
		m[_1_4] = (tmp = m[_1_4]) * cos + m[_3_4] * sin;
		m[_3_4] = tmp * -sin + m[_3_4] * cos;
		
		return this;
	}
	
	public NemoMat4 lRotateZ(final float degree)
	{
		final float radian = degree * PI / 180.0f;
		final float sin = (float)Math.sin(radian);
		final float cos = (float)Math.cos(radian);
		
		float tmp;
		m[_1_1] = (tmp = m[_1_1]) * cos + m[_2_1] * -sin;
		m[_2_1] = tmp * sin + m[_2_1] * cos;
		m[_1_2] = (tmp = m[_1_2]) * cos + m[_2_2] * -sin;
		m[_2_2] = tmp * sin + m[_2_2] * cos;
		m[_1_3] = (tmp = m[_1_3]) * cos + m[_2_3] * -sin;
		m[_2_3] = tmp * sin + m[_2_3] * cos;
		m[_1_4] = (tmp = m[_1_4]) * cos + m[_2_4] * -sin;
		m[_2_4] = tmp * sin + m[_2_4] * cos;
		
		return this;
	}
	
	public NemoMat4 setScale(final float x, final float y, final float z)
	{
		setZero();
		m[_1_1] = x;
		m[_2_2] = y;
		m[_3_3] = z;
		m[_4_4] = 1.0f;
		return this;
	}
	
	public NemoMat4 setScale(final NemoVec4 v)
	{
		return setScale(v.x(), v.y(), v.z());
	}
	
	public NemoMat4 setScaleX(final float x)
	{
		setZero();
		m[_1_1] = x;
		m[_2_2] = m[_3_3] = m[_4_4] = 1.0f;
		return this;
	}
	
	public NemoMat4 setScaleY(final float y)
	{
		setZero();
		m[_2_2] = y;
		m[_1_1] = m[_3_3] = m[_4_4] = 1.0f;
		return this;
	}
	
	public NemoMat4 setScaleZ(final float z)
	{
		setZero();
		m[_3_3] = z;
		m[_1_1] = m[_2_2] = m[_4_4] = 1.0f;
		return this;
	}
	
	public NemoMat4 setScale2d(final float r)
	{
		setZero();
		m[_1_1] = m[_2_2] = r;
		m[_3_3] = m[_4_4] = 1.0f;
		return this;
	}
	
	public NemoMat4 setScale3d(final float r)
	{
		setZero();
		m[_1_1] = m[_2_2] = m[_3_3] = r;
		m[_4_4] = 1.0f;
		return this;
	}
	
	public NemoMat4 rScale(final float x, final float y, final float z)
	{
		m[_1_1] *= x;
		m[_2_1] *= x;
		m[_3_1] *= x;
		m[_4_1] *= x;
		m[_1_2] *= y;
		m[_2_2] *= y;
		m[_3_2] *= y;
		m[_4_2] *= y;
		m[_1_3] *= z;
		m[_2_3] *= z;
		m[_3_3] *= z;
		m[_4_3] *= z;
		return this;
	}
	
	public NemoMat4 rScale(final NemoVec4 v)
	{
		return rScale(v.x(), v.y(), v.z());
	}
	
	public NemoMat4 rScaleX(final float x)
	{
		m[_1_1] *= x;
		m[_2_1] *= x;
		m[_3_1] *= x;
		m[_4_1] *= x;
		return this;
	}
	
	public NemoMat4 rScaleY(final float y)
	{
		m[_1_2] *= y;
		m[_2_2] *= y;
		m[_3_2] *= y;
		m[_4_2] *= y;
		return this;
	}
	
	public NemoMat4 rScaleZ(final float z)
	{
		m[_1_3] *= z;
		m[_2_3] *= z;
		m[_3_3] *= z;
		m[_4_3] *= z;
		return this;
	}
	
	public NemoMat4 rScale2d(final float r)
	{
		m[_1_1] *= r;
		m[_2_1] *= r;
		m[_3_1] *= r;
		m[_4_1] *= r;
		m[_1_2] *= r;
		m[_2_2] *= r;
		m[_3_2] *= r;
		m[_4_2] *= r;
		return this;
	}
	
	public NemoMat4 rScale3d(final float r)
	{
		m[_1_1] *= r;
		m[_2_1] *= r;
		m[_3_1] *= r;
		m[_4_1] *= r;
		m[_1_2] *= r;
		m[_2_2] *= r;
		m[_3_2] *= r;
		m[_4_2] *= r;
		m[_1_3] *= r;
		m[_2_3] *= r;
		m[_3_3] *= r;
		m[_4_3] *= r;
		return this;
	}
	
	public NemoMat4 lScale(final float x, final float y, final float z)
	{
		m[_1_1] *= x;
		m[_2_1] *= y;
		m[_3_1] *= z;
		m[_1_2] *= x;
		m[_2_2] *= y;
		m[_3_2] *= z;
		m[_1_3] *= x;
		m[_2_3] *= y;
		m[_3_3] *= z;
		m[_1_4] *= x;
		m[_2_4] *= y;
		m[_3_4] *= z;
		return this;
	}
	
	public NemoMat4 lScale(final NemoVec4 v)
	{
		return lScale(v.x(), v.y(), v.z());
	}
	
	public NemoMat4 lScaleX(final float x)
	{
		m[_1_1] *= x;
		m[_1_2] *= x;
		m[_1_3] *= x;
		m[_1_4] *= x;
		return this;
	}
	
	public NemoMat4 lScaleY(final float y)
	{
		m[_2_1] *= y;
		m[_2_2] *= y;
		m[_2_3] *= y;
		m[_2_4] *= y;
		return this;
	}
	
	public NemoMat4 lScaleZ(final float z)
	{
		m[_3_1] *= z;
		m[_3_2] *= z;
		m[_3_3] *= z;
		m[_3_4] *= z;
		return this;
	}
	
	public NemoMat4 lScale2d(final float r)
	{
		m[_1_1] *= r;
		m[_2_1] *= r;
		m[_1_2] *= r;
		m[_2_2] *= r;
		m[_1_3] *= r;
		m[_2_3] *= r;
		m[_1_4] *= r;
		m[_2_4] *= r;
		return this;
	}
	
	public NemoMat4 lScale3d(final float r)
	{
		m[_1_1] *= r;
		m[_2_1] *= r;
		m[_3_1] *= r;
		m[_1_2] *= r;
		m[_2_2] *= r;
		m[_3_2] *= r;
		m[_1_3] *= r;
		m[_2_3] *= r;
		m[_3_3] *= r;
		m[_1_4] *= r;
		m[_2_4] *= r;
		m[_3_4] *= r;
		return this;
	}
	
	public NemoMat4 setSkewX(final float y, final float z)
	{
		setIdentity();
		m[_2_1] = y;
		m[_3_1] = z;
		return this;
	}
	
	public NemoMat4 setSkewY(final float x, final float z)
	{
		setIdentity();
		m[_1_2] = x;
		m[_3_2] = z;
		return this;
	}
	
	public NemoMat4 setSkewZ(final float x, final float y)
	{
		setIdentity();
		m[_1_3] = x;
		m[_2_3] = y;
		return this;
	}
	
	public NemoMat4 setSkewX2d(final float y)
	{
		setIdentity();
		m[_2_1] = y;
		return this;
	}
	
	public NemoMat4 setSkewY2d(final float x)
	{
		setIdentity();
		m[_1_2] = x;
		return this;
	}
	
	public NemoMat4 rSkewX(final float y, final float z)
	{
		m[_1_1] += m[_1_2] * y + m[_1_3] * z;
		m[_2_1] += m[_2_2] * y + m[_2_3] * z;
		m[_3_1] += m[_3_2] * y + m[_3_3] * z;
		m[_4_1] += m[_4_2] * y + m[_4_3] * z;
		return this;
	}
	
	public NemoMat4 rSkewY(final float x, final float z)
	{
		m[_1_2] += m[_1_1] * x + m[_1_3] * z;
		m[_2_2] += m[_2_1] * x + m[_2_3] * z;
		m[_3_2] += m[_3_1] * x + m[_3_3] * z;
		m[_4_2] += m[_4_1] * x + m[_4_3] * z;
		return this;
	}
	
	public NemoMat4 rSkewZ(final float x, final float y)
	{
		m[_1_3] += m[_1_1] * x + m[_1_2] * y;
		m[_2_3] += m[_2_1] * x + m[_2_2] * y;
		m[_3_3] += m[_3_1] * x + m[_3_2] * y;
		m[_4_3] += m[_4_1] * x + m[_4_2] * y;
		return this;
	}
	
	public NemoMat4 rSkewX2d(final float y)
	{
		m[_1_1] += m[_1_2] * y;
		m[_2_1] += m[_2_2] * y;
		m[_3_1] += m[_3_2] * y;
		m[_4_1] += m[_4_2] * y;
		return this;
	}
	
	public NemoMat4 rSkewY2d(final float x)
	{
		m[_1_2] += m[_1_1] * x;
		m[_2_2] += m[_2_1] * x;
		m[_3_2] += m[_3_1] * x;
		m[_4_2] += m[_4_1] * x;
		return this;
	}
	
	public NemoMat4 lSkewX(final float y, final float z)
	{
		m[_2_1] += m[_1_1] * y;
		m[_3_1] += m[_1_1] * z;
		m[_2_2] += m[_1_2] * y;
		m[_3_2] += m[_1_2] * z;
		m[_2_3] += m[_1_3] * y;
		m[_3_3] += m[_1_3] * z;
		m[_2_4] += m[_1_4] * y;
		m[_3_4] += m[_1_4] * z;
		return this;
	}
	
	public NemoMat4 lSkewY(final float x, final float z)
	{
		m[_1_1] += m[_2_1] * x;
		m[_3_1] += m[_2_1] * z;
		m[_1_2] += m[_2_2] * x;
		m[_3_2] += m[_2_2] * z;
		m[_1_3] += m[_2_3] * x;
		m[_3_3] += m[_2_3] * z;
		m[_1_4] += m[_2_4] * x;
		m[_3_4] += m[_2_4] * z;
		return this;
	}
	
	public NemoMat4 lSkewZ(final float x, final float y)
	{
		m[_1_1] += m[_3_1] * x;
		m[_2_1] += m[_3_1] * y;
		m[_1_2] += m[_3_2] * x;
		m[_2_2] += m[_3_2] * y;
		m[_1_3] += m[_3_3] * x;
		m[_2_3] += m[_3_3] * y;
		m[_1_4] += m[_3_4] * x;
		m[_2_4] += m[_3_4] * y;
		return this;
	}
	
	public NemoMat4 lSkewX2d(final float y)
	{
		m[_2_1] += m[_1_1] * y;
		m[_2_2] += m[_1_2] * y;
		m[_2_3] += m[_1_3] * y;
		m[_2_4] += m[_1_4] * y;
		return this;
	}
	
	public NemoMat4 lSkewY2d(final float x)
	{
		m[_1_1] += m[_2_1] * x;
		m[_1_2] += m[_2_2] * x;
		m[_1_3] += m[_2_3] * x;
		m[_1_4] += m[_2_4] * x;
		return this;
	}
	/*=========================================================================================================================
	 << model transform
	 =========================================================================================================================*/
	
	/*=========================================================================================================================
	 view & projection transform >>
	 =========================================================================================================================*/
	public NemoMat4 setLookAt(final float eyeX, final float eyeY, final float eyeZ,
			final float centerX, final float centerY, final float centerZ,
			final float upX, final float upY, final float upZ)
	{
		float fx = centerX - eyeX;
		float fy = centerY - eyeY;
		float fz = centerZ - eyeZ;
		
		float n = (float)Math.sqrt(fx * fx + fy * fy + fz * fz);
		if (n != 0.0f && n != 1.0f)
		{
			fx /= n;
			fy /= n;
			fz /= n;
		}
		
		float sx = fy * upZ - fz * upY;
		float sy = fz * upX - fx * upZ;
		float sz = fx * upY - fy * upX;
		
		n = (float)Math.sqrt(sx * sx + sy * sy + sz * sz);
		if (n != 0.0f && n != 1.0f)
		{
			sx /= n;
			sy /= n;
			sz /= n;
		}
		
		final float ux = sy * fz - sz * fy;
		final float uy = sz * fx - sx * fz;
		final float uz = sx * fy - sy * fx;
		
		setZero();
		m[_1_1] = sx;
		m[_2_1] = ux;
		m[_3_1] = -fx;
		m[_1_2] = sy;
		m[_2_2] = uy;
		m[_3_2] = -fy;
		m[_1_3] = sz;
		m[_2_3] = uz;
		m[_3_3] = -fz;
		m[_1_4] = sx * -eyeX + sy * -eyeY + sz * -eyeZ;
		m[_2_4] = ux * -eyeX + uy * -eyeY + uz * -eyeZ;
		m[_3_4] = fx * eyeX + fy * eyeY + fz * eyeZ;
		m[_4_4] = 1.0f;
		
		return this;
	}
	
	public NemoMat4 setOrtho(final float left, final float right, final float bottom, final float top, final float near, final float far)
	{
		setZero();
		m[_1_1] = 2.0f / (right - left);
		m[_2_2] = 2.0f / (top - bottom);
		m[_3_3] = 2.0f / (near - far);
		m[_1_4] = (right + left) / (left - right);
		m[_2_4] = (top + bottom) / (bottom - top);
		m[_3_4] = (far + near) / (near - far);
		m[_4_4] = 1.0f;
		return this;
	}
	
	public NemoMat4 setFrustum(final float left, final float right, final float bottom, final float top, final float near, final float far)
	{
		setZero();
		m[_1_1] = 2.0f * near / (right - left);
		m[_2_2] = 2.0f * near / (top - bottom);
		m[_1_3] = (right + left) / (right - left);
		m[_2_3] = (top + bottom) / (top - bottom);
		m[_3_3] = (far + near) / (near - far);
		m[_4_3] = -1.0f;
		m[_3_4] = 2.0f * far * near / (near - far);
		return this;
	}
	
	public NemoMat4 setPerspective(final float fov, final float aspect, final float near, final float far)
	{
		final float top = near * (float)Math.tan(fov * PI / 360.0f);
		final float right = top * aspect;
		
		setZero();
		m[_1_1] = near / right;
		m[_2_2] = near / top;
		m[_3_3] = (far + near) / (near - far);
		m[_4_3] = -1.0f;
		m[_3_4] = 2.0f * far * near / (near - far);
		
		return this;
	}
	
	public NemoMat4 rLookAt(final float eyeX, final float eyeY, final float eyeZ,
			final float centerX, final float centerY, final float centerZ,
			final float upX, final float upY, final float upZ)
	{
		final NemoMat4 rhs = setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		final float[] rst = new float[16];
		
		rst[_1_1] = m[_1_1] * rhs.m[_1_1] + m[_1_2] * rhs.m[_2_1] + m[_1_3] * rhs.m[_3_1];
		rst[_2_1] = m[_2_1] * rhs.m[_1_1] + m[_2_2] * rhs.m[_2_1] + m[_2_3] * rhs.m[_3_1];
		rst[_3_1] = m[_3_1] * rhs.m[_1_1] + m[_3_2] * rhs.m[_2_1] + m[_3_3] * rhs.m[_3_1];
		rst[_4_1] = m[_4_1] * rhs.m[_1_1] + m[_4_2] * rhs.m[_2_1] + m[_4_3] * rhs.m[_3_1];
		rst[_1_2] = m[_1_1] * rhs.m[_1_2] + m[_1_2] * rhs.m[_2_2] + m[_1_3] * rhs.m[_3_2];
		rst[_2_2] = m[_2_1] * rhs.m[_1_2] + m[_2_2] * rhs.m[_2_2] + m[_2_3] * rhs.m[_3_2];
		rst[_3_2] = m[_3_1] * rhs.m[_1_2] + m[_3_2] * rhs.m[_2_2] + m[_3_3] * rhs.m[_3_2];
		rst[_4_2] = m[_4_1] * rhs.m[_1_2] + m[_4_2] * rhs.m[_2_2] + m[_4_3] * rhs.m[_3_2];
		rst[_1_3] = m[_1_1] * rhs.m[_1_3] + m[_1_2] * rhs.m[_2_3] + m[_1_3] * rhs.m[_3_3];
		rst[_2_3] = m[_2_1] * rhs.m[_1_3] + m[_2_2] * rhs.m[_2_3] + m[_2_3] * rhs.m[_3_3];
		rst[_3_3] = m[_3_1] * rhs.m[_1_3] + m[_3_2] * rhs.m[_2_3] + m[_3_3] * rhs.m[_3_3];
		rst[_4_3] = m[_4_1] * rhs.m[_1_3] + m[_4_2] * rhs.m[_2_3] + m[_4_3] * rhs.m[_3_3];
		rst[_1_4] = m[_1_1] * rhs.m[_1_4] + m[_1_2] * rhs.m[_2_4] + m[_1_3] * rhs.m[_3_4] + m[_1_4];
		rst[_2_4] = m[_2_1] * rhs.m[_1_4] + m[_2_2] * rhs.m[_2_4] + m[_2_3] * rhs.m[_3_4] + m[_2_4];
		rst[_3_4] = m[_3_1] * rhs.m[_1_4] + m[_3_2] * rhs.m[_2_4] + m[_3_3] * rhs.m[_3_4] + m[_3_4];
		rst[_4_4] = m[_4_1] * rhs.m[_1_4] + m[_4_2] * rhs.m[_2_4] + m[_4_3] * rhs.m[_3_4] + m[_4_4];
		
		m = rst;
		return this;
	}
	
	public NemoMat4 rOrtho(final float left, final float right, final float bottom, final float top, final float near, final float far)
	{
		NemoMat4 rhs = setOrtho(left, right, bottom, top, near, far);
		
		m[_1_4] += m[_1_1] * rhs.m[_1_4] + m[_1_2] * rhs.m[_2_4] + m[_1_3] * rhs.m[_3_4];
		m[_2_4] += m[_2_1] * rhs.m[_1_4] + m[_2_2] * rhs.m[_2_4] + m[_2_3] * rhs.m[_3_4];
		m[_3_4] += m[_3_1] * rhs.m[_1_4] + m[_3_2] * rhs.m[_2_4] + m[_3_3] * rhs.m[_3_4];
		m[_4_4] += m[_4_1] * rhs.m[_1_4] + m[_4_2] * rhs.m[_2_4] + m[_4_3] * rhs.m[_3_4];
		m[_1_1] = m[_1_1] * rhs.m[_1_1];
		m[_2_1] = m[_2_1] * rhs.m[_1_1];
		m[_3_1] = m[_3_1] * rhs.m[_1_1];
		m[_4_1] = m[_4_1] * rhs.m[_1_1];
		m[_1_2] = m[_1_2] * rhs.m[_2_2];
		m[_2_2] = m[_2_2] * rhs.m[_2_2];
		m[_3_2] = m[_3_2] * rhs.m[_2_2];
		m[_4_2] = m[_4_2] * rhs.m[_2_2];
		m[_1_3] = m[_1_3] * rhs.m[_3_3];
		m[_2_3] = m[_2_3] * rhs.m[_3_3];
		m[_3_3] = m[_3_3] * rhs.m[_3_3];
		m[_4_3] = m[_4_3] * rhs.m[_3_3];
		
		return this;
	}
	
	public NemoMat4 rFrustum(final float left, final float right, final float bottom, final float top, final float near, final float far)
	{
		final NemoMat4 rhs = setFrustum(left, right, bottom, top, near, far);
		final float[] col3 = new float[4];
		col3[0] = m[_1_3];
		col3[1] = m[_2_3];
		col3[2] = m[_3_3];
		col3[3] = m[_4_3];
		
		m[_1_3] = m[_1_1] * rhs.m[_1_3] + m[_1_2] * rhs.m[_2_3] + m[_1_3] * rhs.m[_3_3] - m[_1_4];
		m[_2_3] = m[_2_1] * rhs.m[_1_3] + m[_2_2] * rhs.m[_2_3] + m[_2_3] * rhs.m[_3_3] - m[_2_4];
		m[_3_3] = m[_3_1] * rhs.m[_1_3] + m[_3_2] * rhs.m[_2_3] + m[_3_3] * rhs.m[_3_3] - m[_3_4];
		m[_4_3] = m[_4_1] * rhs.m[_1_3] + m[_4_2] * rhs.m[_2_3] + m[_4_3] * rhs.m[_3_3] - m[_4_4];
		m[_1_1] = m[_1_1] * rhs.m[_1_1];
		m[_2_1] = m[_2_1] * rhs.m[_1_1];
		m[_3_1] = m[_3_1] * rhs.m[_1_1];
		m[_4_1] = m[_4_1] * rhs.m[_1_1];
		m[_1_2] = m[_1_2] * rhs.m[_2_2];
		m[_2_2] = m[_2_2] * rhs.m[_2_2];
		m[_3_2] = m[_3_2] * rhs.m[_2_2];
		m[_4_2] = m[_4_2] * rhs.m[_2_2];
		m[_1_4] = col3[0] * rhs.m[_3_4];
		m[_2_4] = col3[1] * rhs.m[_3_4];
		m[_3_4] = col3[2] * rhs.m[_3_4];
		m[_4_4] = col3[3] * rhs.m[_3_4];
		
		return this;
	}
	
	public NemoMat4 rPerspective(final float fov, final float aspect, final float near, final float far)
	{
		final NemoMat4 rhs = setPerspective(fov, aspect, near, far);
		final float[] col3 = new float[4];
		col3[0] = m[_1_3];
		col3[1] = m[_2_3];
		col3[2] = m[_3_3];
		col3[3] = m[_4_3];
		
		m[_1_1] = m[_1_1] * rhs.m[_1_1];
		m[_2_1] = m[_2_1] * rhs.m[_1_1];
		m[_3_1] = m[_3_1] * rhs.m[_1_1];
		m[_4_1] = m[_4_1] * rhs.m[_1_1];
		m[_1_2] = m[_1_2] * rhs.m[_2_2];
		m[_2_2] = m[_2_2] * rhs.m[_2_2];
		m[_3_2] = m[_3_2] * rhs.m[_2_2];
		m[_4_2] = m[_4_2] * rhs.m[_2_2];
		m[_1_3] = m[_1_3] * rhs.m[_3_3] - m[_1_4];
		m[_2_3] = m[_2_3] * rhs.m[_3_3] - m[_2_4];
		m[_3_3] = m[_3_3] * rhs.m[_3_3] - m[_3_4];
		m[_4_3] = m[_4_3] * rhs.m[_3_3] - m[_4_4];
		m[_1_4] = col3[0] * rhs.m[_3_4];
		m[_2_4] = col3[1] * rhs.m[_3_4];
		m[_3_4] = col3[2] * rhs.m[_3_4];
		m[_4_4] = col3[3] * rhs.m[_3_4];
		
		return this;
	}
	
	public NemoMat4 lLookAt(final float eyeX, final float eyeY, final float eyeZ,
			final float centerX, final float centerY, final float centerZ,
			final float upX, final float upY, final float upZ)
	{
		final NemoMat4 lhs = setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		final float[] rst = new float[16];
		
		rst[_1_1] = lhs.m[_1_1] * m[_1_1] + lhs.m[_1_2] * m[_2_1] + lhs.m[_1_3] * m[_3_1] + lhs.m[_1_4] * m[_4_1];
		rst[_2_1] = lhs.m[_2_1] * m[_1_1] + lhs.m[_2_2] * m[_2_1] + lhs.m[_2_3] * m[_3_1] + lhs.m[_2_4] * m[_4_1];
		rst[_3_1] = lhs.m[_3_1] * m[_1_1] + lhs.m[_3_2] * m[_2_1] + lhs.m[_3_3] * m[_3_1] + lhs.m[_3_4] * m[_4_1];
		rst[_4_1] = m[_4_1];
		rst[_1_2] = lhs.m[_1_1] * m[_1_2] + lhs.m[_1_2] * m[_2_2] + lhs.m[_1_3] * m[_3_2] + lhs.m[_1_4] * m[_4_2];
		rst[_2_2] = lhs.m[_2_1] * m[_1_2] + lhs.m[_2_2] * m[_2_2] + lhs.m[_2_3] * m[_3_2] + lhs.m[_2_4] * m[_4_2];
		rst[_3_2] = lhs.m[_3_1] * m[_1_2] + lhs.m[_3_2] * m[_2_2] + lhs.m[_3_3] * m[_3_2] + lhs.m[_3_4] * m[_4_2];
		rst[_4_2] = m[_4_2];
		rst[_1_3] = lhs.m[_1_1] * m[_1_3] + lhs.m[_1_2] * m[_2_3] + lhs.m[_1_3] * m[_3_3] + lhs.m[_1_4] * m[_4_3];
		rst[_2_3] = lhs.m[_2_1] * m[_1_3] + lhs.m[_2_2] * m[_2_3] + lhs.m[_2_3] * m[_3_3] + lhs.m[_2_4] * m[_4_3];
		rst[_3_3] = lhs.m[_3_1] * m[_1_3] + lhs.m[_3_2] * m[_2_3] + lhs.m[_3_3] * m[_3_3] + lhs.m[_3_4] * m[_4_3];
		rst[_4_3] = m[_4_3];
		rst[_1_4] = lhs.m[_1_1] * m[_1_4] + lhs.m[_1_2] * m[_2_4] + lhs.m[_1_3] * m[_3_4] + lhs.m[_1_4] * m[_4_4];
		rst[_2_4] = lhs.m[_2_1] * m[_1_4] + lhs.m[_2_2] * m[_2_4] + lhs.m[_2_3] * m[_3_4] + lhs.m[_2_4] * m[_4_4];
		rst[_3_4] = lhs.m[_3_1] * m[_1_4] + lhs.m[_3_2] * m[_2_4] + lhs.m[_3_3] * m[_3_4] + lhs.m[_3_4] * m[_4_4];
		rst[_4_4] = m[_4_4];
		
		m = rst;
		return this;
	}
	
	public NemoMat4 lOrtho(final float left, final float right, final float bottom, final float top, final float near, final float far)
	{
		final NemoMat4 lhs = setOrtho(left, right, bottom, top, near, far);
		
		m[_1_1] = lhs.m[_1_1] * m[_1_1] + lhs.m[_1_4] * m[_4_1];
		m[_2_1] = lhs.m[_2_2] * m[_2_1] + lhs.m[_2_4] * m[_4_1];
		m[_3_1] = lhs.m[_3_3] * m[_3_1] + lhs.m[_3_4] * m[_4_1];
		m[_1_2] = lhs.m[_1_1] * m[_1_2] + lhs.m[_1_4] * m[_4_2];
		m[_2_2] = lhs.m[_2_2] * m[_2_2] + lhs.m[_2_4] * m[_4_2];
		m[_3_2] = lhs.m[_3_3] * m[_3_2] + lhs.m[_3_4] * m[_4_2];
		m[_1_3] = lhs.m[_1_1] * m[_1_3] + lhs.m[_1_4] * m[_4_3];
		m[_2_3] = lhs.m[_2_2] * m[_2_3] + lhs.m[_2_4] * m[_4_3];
		m[_3_3] = lhs.m[_3_3] * m[_3_3] + lhs.m[_3_4] * m[_4_3];
		m[_1_4] = lhs.m[_1_1] * m[_1_4] + lhs.m[_1_4] * m[_4_4];
		m[_2_4] = lhs.m[_2_2] * m[_2_4] + lhs.m[_2_4] * m[_4_4];
		m[_3_4] = lhs.m[_3_3] * m[_3_4] + lhs.m[_3_4] * m[_4_4];
		
		return this;
	}
	
	public NemoMat4 lFrustum(final float left, final float right, final float bottom, final float top, final float near, final float far)
	{
		final NemoMat4 lhs = setFrustum(left, right, bottom, top, near, far);
		
		float tmp;
		m[_1_1] = lhs.m[_1_1] * m[_1_1] + lhs.m[_1_3] * m[_3_1];
		m[_2_1] = lhs.m[_2_2] * m[_2_1] + lhs.m[_2_3] * m[_3_1];
		m[_3_1] = lhs.m[_3_3] * (tmp = m[_3_1]) + lhs.m[_3_4] * m[_4_1];
		m[_4_1] = -tmp;
		m[_1_2] = lhs.m[_1_1] * m[_1_2] + lhs.m[_1_3] * m[_3_2];
		m[_2_2] = lhs.m[_2_2] * m[_2_2] + lhs.m[_2_3] * m[_3_2];
		m[_3_2] = lhs.m[_3_3] * (tmp = m[_3_2]) + lhs.m[_3_4] * m[_4_2];
		m[_4_2] = -tmp;
		m[_1_3] = lhs.m[_1_1] * m[_1_3] + lhs.m[_1_3] * m[_3_3];
		m[_2_3] = lhs.m[_2_2] * m[_2_3] + lhs.m[_2_3] * m[_3_3];
		m[_3_3] = lhs.m[_3_3] * (tmp = m[_3_3]) + lhs.m[_3_4] * m[_4_3];
		m[_4_3] = -tmp;
		m[_1_4] = lhs.m[_1_1] * m[_1_4] + lhs.m[_1_3] * m[_3_4];
		m[_2_4] = lhs.m[_2_2] * m[_2_4] + lhs.m[_2_3] * m[_3_4];
		m[_3_4] = lhs.m[_3_3] * (tmp = m[_3_4]) + lhs.m[_3_4] * m[_4_4];
		m[_4_4] = -tmp;
		
		return this;
	}
	
	public NemoMat4 lPerspective(final float fov, final float aspect, final float near, final float far)
	{
		final NemoMat4 lhs = setPerspective(fov, aspect, near, far);
		
		float tmp;
		m[_1_1] = lhs.m[_1_1] * m[_1_1];
		m[_2_1] = lhs.m[_2_2] * m[_2_1];
		m[_3_1] = lhs.m[_3_3] * (tmp = m[_3_1]) + lhs.m[_3_4] * m[_4_1];
		m[_4_1] = -tmp;
		m[_1_2] = lhs.m[_1_1] * m[_1_2];
		m[_2_2] = lhs.m[_2_2] * m[_2_2];
		m[_3_2] = lhs.m[_3_3] * (tmp = m[_3_2]) + lhs.m[_3_4] * m[_4_2];
		m[_4_2] = -tmp;
		m[_1_3] = lhs.m[_1_1] * m[_1_3];
		m[_2_3] = lhs.m[_2_2] * m[_2_3];
		m[_3_3] = lhs.m[_3_3] * (tmp = m[_3_3]) + lhs.m[_3_4] * m[_4_3];
		m[_4_3] = -tmp;
		m[_1_4] = lhs.m[_1_1] * m[_1_4];
		m[_2_4] = lhs.m[_2_2] * m[_2_4];
		m[_3_4] = lhs.m[_3_3] * (tmp = m[_3_4]) + lhs.m[_3_4] * m[_4_4];
		m[_4_4] = -tmp;
		
		return this;
	}
	/*=========================================================================================================================
	 << view & projection transform
	 =========================================================================================================================*/
	
	/*=========================================================================================================================
	 etc >>
	 =========================================================================================================================*/
	public boolean equals(final NemoMat4 rhs)
	{
		for (int i = 0 ; i < m.length ; ++i)
		{
			if (m[i] != rhs.m[i])
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		return String.format("[%f, %f, %f, %f\n %f, %f, %f, %f\n %f, %f, %f, %f\n %f, %f, %f, %f]",
				m[_1_1], m[_1_2], m[_1_3], m[_1_4],
				m[_2_1], m[_2_2], m[_2_3], m[_2_4],
				m[_3_1], m[_3_2], m[_3_3], m[_3_4],
				m[_4_1], m[_4_2], m[_4_3], m[_4_4]);
	}
	/*=========================================================================================================================
	 << etc
	 =========================================================================================================================*/
}