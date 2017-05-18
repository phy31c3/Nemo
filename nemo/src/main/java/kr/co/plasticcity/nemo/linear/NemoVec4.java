package kr.co.plasticcity.nemo.linear;

/**
 * Created by Jongsunv[1]u on 2017-01-05.
 */

final public class NemoVec4
{
	private float[] v = new float[4];
	
	/**
	 * w == 1.0f 로 초기화
	 */
	public NemoVec4()
	{
		v[3] = 1.0f;
	}
	
	public NemoVec4(final float w)
	{
		v[3] = w;
	}
	
	public NemoVec4(final float x, final float y, final float z, final float w)
	{
		v[0] = x;
		v[1] = y;
		v[2] = z;
		v[3] = w;
	}
	
	public NemoVec4(final NemoVec4 vec4)
	{
		System.arraycopy(vec4.v, 0, v, 0, v.length);
	}
	
	public float x()
	{
		return v[0];
	}
	
	public float y()
	{
		return v[1];
	}
	
	public float z()
	{
		return v[2];
	}
	
	public float w()
	{
		return v[3];
	}
	
	public float px()
	{
		return v[3] != 0.0f ? (v[3] == 1.0f ? v[0] : v[0] / v[3]) : 0.0f;
	}
	
	public float py()
	{
		return v[3] != 0.0f ? (v[3] == 1.0f ? v[1] : v[1] / v[3]) : 0.0f;
	}
	
	public float pz()
	{
		return v[3] != 0.0f ? (v[3] == 1.0f ? v[2] : v[2] / v[3]) : 0.0f;
	}
	
	public boolean isPoint()
	{
		return v[3] != 0.0f;
	}
	
	public NemoVec4 lMultiply(final NemoMat4 lhs)
	{
		float[] rst = new float[4];
		
		rst[0] = lhs.m[NemoMat4._1_1] * v[0] + lhs.m[NemoMat4._1_2] * v[1] + lhs.m[NemoMat4._1_3] * v[2] + lhs.m[NemoMat4._1_4] * v[3];
		rst[1] = lhs.m[NemoMat4._2_1] * v[0] + lhs.m[NemoMat4._2_2] * v[1] + lhs.m[NemoMat4._2_3] * v[2] + lhs.m[NemoMat4._2_4] * v[3];
		rst[2] = lhs.m[NemoMat4._3_1] * v[0] + lhs.m[NemoMat4._3_2] * v[1] + lhs.m[NemoMat4._3_3] * v[2] + lhs.m[NemoMat4._3_4] * v[3];
		rst[3] = lhs.m[NemoMat4._4_1] * v[0] + lhs.m[NemoMat4._4_2] * v[1] + lhs.m[NemoMat4._4_3] * v[2] + lhs.m[NemoMat4._4_4] * v[3];
		
		v = rst;
		return this;
	}
	
	public boolean equals(final NemoVec4 rhs)
	{
		for (int i = 0 ; i < v.length ; ++i)
		{
			if (v[i] != rhs.v[i])
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		return String.format("[%f, %f, %f, %f]", v[0], v[1], v[2], v[3]);
	}
}