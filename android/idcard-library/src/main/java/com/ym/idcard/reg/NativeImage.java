package com.ym.idcard.reg;

import android.util.Log;

public class NativeImage
{
  private static final String LIB = "imageengine";
  private static final String TAG = "NativeImage";

  static
  {
    try
    {
      System.loadLibrary("imageengine");
    }
    catch (Exception localException)
    {
      Log.e("imageengine", "", localException);
    }
  }

  public native int closeEngine(long paramLong);

  public native long createEngine();

  public void finalize()
  {
  }

  public native int freeImage(long paramLong);

  public native int getImageComponent(long paramLong);

  public native long getImageDataEx(long paramLong);

  public native int getImageHeight(long paramLong);

  public native int getImageWidth(long paramLong);

  public native int initImage(long paramLong, int paramInt1, int paramInt2);

  public native int loadmemjpg(long paramLong, byte[] paramArrayOfByte, int paramInt);
}

/* Location:           D:\document\bsd_work\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar
 * Qualified Name:     com.ym.idcard.reg.NativeImage
 * JD-Core Version:    0.5.4
 */