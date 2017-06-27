
package com.ym.idcard.reg.engine;

import com.ym.idcard.reg.NativeImage;

public class ImageEngine
{
    public static final int IMG_COMPONENT_GRAY = 1;
    public static final int IMG_COMPONENT_RGB = 3;
    public static final int IMG_FMT_BMP = 1;
    public static final int IMG_FMT_JPG = 2;
    public static final int IMG_FMT_UNK = 0;
    public static final int RET_ERR_ARG = -2;
    public static final int RET_ERR_MEM = -3;
    public static final int RET_ERR_PTR = -1;
    public static final int RET_ERR_UNK = 0;
    public static final int RET_OK = 1;
    protected long mEngine = 0L;
    protected NativeImage mNativeImage = null;
    
    ImageEngine(){
    	mNativeImage = new NativeImage();
    	mEngine = mNativeImage.createEngine();
    }

    public void finalize()
    {
        if ((this.mNativeImage == null) || (this.mEngine == 0L))
            return;
        this.mNativeImage.freeImage(this.mEngine);
        this.mNativeImage.closeEngine(this.mEngine);
        this.mEngine = 0L;
    }

    public int getComponent()
    {
        if (this.mNativeImage != null)
        	return mNativeImage.getImageComponent(mEngine);
        return 0;
    }

    public long getDataEx()
    {
        long data = 0L;
        if (this.mNativeImage != null)
            data = this.mNativeImage.getImageDataEx(this.mEngine);
        return data;
    }

    public int getHeight()
    {
        if (this.mNativeImage != null)
            return mNativeImage.getImageHeight(this.mEngine);
        return 0;
    }

    public int getWidth()
    {
        if (this.mNativeImage != null)
        	return mNativeImage.getImageWidth(this.mEngine);
        return 0;
    }

    public boolean init(int components, int quality)
    {
        if ((this.mNativeImage != null)
                && (this.mNativeImage.initImage(this.mEngine, components, quality) == 1))
        {
            return true;
        }
        return false;
    }

    public boolean load(byte[] imgbuffer)
    {
        if ((this.mNativeImage != null)
                && (this.mNativeImage.loadmemjpg(this.mEngine, imgbuffer,
                        imgbuffer.length) == 1))
            return true;
        return false;
    }
}

/*
 * Location:
 * D:\document\bsd_work\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar
 * Qualified Name: com.yunmai.android.engine.ImageEngine JD-Core Version: 0.5.4
 */
