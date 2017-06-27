
package com.ym.idcard.reg.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class FileUtil
{
    public static boolean copyFile(String src, String dst)
    {
        boolean bresult = false;
        try
        {
            File in = new File(src);
            File out = new File(dst);
            FileInputStream inFile = new FileInputStream(in);
            FileOutputStream outFile = new FileOutputStream(out);
            byte[] buffer = new byte[1024];

            int len = 0;
            while ((len = inFile.read(buffer)) != -1) {
                outFile.write(buffer, 0, len);
            }
            inFile.close();
            outFile.close();
            bresult = true;

        } catch (IOException localIOException)
        {
            bresult = false;
        }
        return bresult;
    }

    public static boolean deleteFile(String filepath)
    {
        File file = new File(filepath);
        if (!file.exists())
            return true;
        return file.delete();
    }

    public static boolean exist(String filepath)
    {
        if (filepath == null)
            return false;
        return new File(filepath).exists();
    }

    public static void generateOtherImg(String imagePath)
    {
    }

    public static byte[] getBytesFromFile(File file)
            throws IOException
    {
        FileInputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE)
        {
            is.close();
            throw new IOException("File is to large " + file.getName());
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        try
        {
            while ((numRead = is.read(bytes, offset, bytes.length - offset)) > 0) {
                offset += numRead;
            }
            if (offset < length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
        } catch (Exception localException)
        {
        } finally
        {
            is.close();
        }
        return bytes;
    }

    public static byte[] getBytesFromFile(String path)
            throws IOException
    {
        return getBytesFromFile(new File(path));
    }

    public static int getFileLength(String filepath)
    {
        File file = new File(filepath);
        if (!file.exists())
            return -1;
        return (int) file.length();
    }

    public static String getStrFromFile(File file)
            throws IOException
    {
        String result = "";
        FileInputStream is = new FileInputStream(file);
        if (file.length() > Integer.MAX_VALUE)
        {
            is.close();
            throw new IOException("File is too large " + file.getName());
        }
        StringBuffer localStringBuffer = new StringBuffer();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(
                is, "GBK"));
        String str1 = null;
        while ((str1 = localBufferedReader.readLine()) != null)
        {
            localStringBuffer.append(str1);
            localStringBuffer.append("\n");
        }
        result = localStringBuffer.toString();
        is.close();
        return result;
    }

    public static boolean isDirectory(String filepath)
    {
        return new File(filepath).isDirectory();
    }

    public static boolean makeSureDirExist(String dirpath)
    {
        boolean bool = true;
        File file = new File(dirpath);
        if (!file.exists())
            bool = file.mkdir();
        return bool;
    }

    public static boolean makeSureFileExist(String filepath)
    {
        boolean result = false;
        File localFile = new File(filepath);
        if (localFile.exists())
            return true;
        try
        {
            result = localFile.createNewFile();
        } catch (IOException localIOException)
        {
            result = false;
        }
        return result;
    }

    public static int makeSureFileExistEx(String filepath)
    {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return 0;
                }
            } catch (IOException e) {
            }
            return -1;
        }
        return (int) file.length();
    }

    public static String newImageName()
    {
        return UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
    }

    public void finalize()
    {
    }
}

/*
 * Location:
 * D:\document\bsd_work\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar
 * Qualified Name: com.yunmai.android.other.FileUtil JD-Core Version: 0.5.4
 */
