package com.scanidcard;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;

public class ImageFilter {

    /**
     * 截取中间35%的图像
     * @param bitmap
     * @return
     */
    public static Bitmap curtImage(Bitmap bitmap, double pt){
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = (int)(h * pt);// 裁切后所取的正方形区域边长

        int retX = 0;// 基于原图，取截图左上角x坐标
        int retY = (int)(h * (1 - pt))/2;

        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, w, wh, null, false);
        return bmp;
    }

    /**
     * 获取姓名区域
     * @param bitmap
     * @return
     */
    public static Bitmap getName(Bitmap bitmap){
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int cw = (int)(w * 0.4);
        int ch = (int)(h * 0.25);// 裁切后所取的区域边长

        int retX = 0;// 基于原图，取截图左上角x坐标
        int retY = 0;

        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, cw, ch, null, false);
        return bmp;
    }

    /**
     * 获取身份证区域
     * @param bitmap
     * @return
     */
    public static Bitmap getID(Bitmap bitmap){
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int ch = (int)(h * 0.25);// 裁切后所取的正方形区域边长

        int retX = 0;// 基于原图，取截图左上角x坐标
        int retY = (int)(h * (0.75));

        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, w, ch, null, false);
        return bmp;
    }

    // 图像灰度化
    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        // 得到图片的长和宽  
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像  
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 创建画布  
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    // 图像灰度化  
    public static Bitmap grayScaleImage(Bitmap src) {
        // constant factors

        final double GS_RED = 0.2973;
        final double GS_GREEN = 0.6274;
        final double GS_BLUE = 0.0753;

//        final double GS_RED = 0.299;
//        final double GS_GREEN = 0.587;
//        final double GS_BLUE = 0.114;

//      final double GS_RED = 0.235;  
//      final double GS_GREEN = 0.589;  
//      final double GS_BLUE = 0.119;  

        // create output bitmap  
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information  
        int A, R, G, B;
        int pixel;

        // get image size  
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel  
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image  
        return bmOut;
    }

    // 对图像进行线性灰度变化  
    public static Bitmap lineGrey(Bitmap image) {
        // 得到图像的宽度和长度  
        int width = image.getWidth();
        int height = image.getHeight();
        // 创建线性拉升灰度图像  
        Bitmap linegray = null;
        linegray = image.copy(Config.ARGB_8888, true);
        // 依次循环对图像的像素进行处理  
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 得到每点的像素值  
                int col = image.getPixel(i, j);
                int alpha = col & 0xFF000000;
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 增加了图像的亮度  
                red = (int) (1.1 * red + 30);
                green = (int) (1.1 * green + 30);
                blue = (int) (1.1 * blue + 30);
                // 对图像像素越界进行处理  
                if (red >= 255) {
                    red = 255;
                }

                if (green >= 255) {
                    green = 255;
                }

                if (blue >= 255) {
                    blue = 255;
                }
                // 新的ARGB  
                int newColor = alpha | (red << 16) | (green << 8) | blue;
                // 设置新图像的RGB值  
                linegray.setPixel(i, j, newColor);
            }
        }
        return linegray;
    }

    // 该函数实现对图像进行二值化处理  
    public static Bitmap gray2Binary(Bitmap graymap) {
        // 得到图形的宽度和长度  
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        // 创建二值化图像  
        Bitmap binarymap = null;
        binarymap = graymap.copy(Config.ARGB_8888, true);
        // 依次循环，对图像的像素进行处理  
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 得到当前像素的值  
                int col = binarymap.getPixel(i, j);
                // 得到alpha通道的值  
                int alpha = col & 0xFF000000;
                // 得到图像的像素RGB的值  
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB  
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                // 对图像进行二值化处理  
                if (gray <= 95) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 新的ARGB  
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                // 设置新图像的当前像素值  
                binarymap.setPixel(i, j, newColor);
            }
        }
        return binarymap;
    }

    /**
     * 将彩色图转换为黑白图  
     *
     * @param bmp 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽  
        int height = bmp.getHeight(); // 获取位图的高  
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }

    /**
     * 图片锐化（拉普拉斯变换）  
     *
     * @param bmp
     * @return
     */
    public static Bitmap sharpenImageAmeliorate(Bitmap bmp) {
        // 拉普拉斯矩阵  
        int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int idx = 0;
        float alpha = 0.3F;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + n) * width + k + m];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);
                        newR = newR + (int) (pixR * laplacian[idx] * alpha);
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);
                        idx++;
                    }
                }
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
} 