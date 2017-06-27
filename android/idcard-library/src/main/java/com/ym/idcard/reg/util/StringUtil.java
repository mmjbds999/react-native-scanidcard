package com.ym.idcard.reg.util;

import java.io.UnsupportedEncodingException;

public class StringUtil
{
  public static String convertAscIIToUnicode(byte[] array)
  {
      String str = "";
    try
    {
      byte[] text = filterAndCut(array);
      if (text != null)
      {
        str = new String(text, "ISO-8859-1");
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return str.trim();
  }

  public static String convertBig5ToUnicode(byte[] array)
  {
    String str = "";
    try
    {
      byte[] text = filterAndCut(array);
      if (text != null)
      {
        str = new String(text, "big5");
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return str.trim();
  }

  public static String convertGbkToUnicode(byte[] array)
  {
      String str = "";
      try
      {
        byte[] text = filterAndCut(array);
        if (text != null)
        {
          str = new String(text, "GBK");
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
      return str.trim();
  }

  public static byte[] convertToUnicode(String str)
  {
    byte[] result = null;
    try
    {
      byte[] res = str.getBytes("utf-8");
      result = new byte[res.length + 1];
      for(int i = 0; i<res.length; i++){
          result[i] = res[i];
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return result;
  }

  public static byte[] convertUnicodeToAscii(String str)
  {
      byte[] result = null;
      try
      {
        byte[] res = str.getBytes("US-ASCII");
        result = new byte[res.length + 1];
        for(int i = 0; i<res.length; i++){
            result[i] = res[i];
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
      return result;
  }

  public static byte[] convertUnicodeToGbk(String str)
  {
      byte[] result = null;
      try
      {
        byte[] res = str.getBytes("US-ASCII");
        result = new byte[res.length + 1];
        for(int i = 0; i<res.length; i++){
            result[i] = res[i];
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
      return result;
  }

  public static byte[] filter(byte[] array)
  {
    int len = array.length;
    byte[] filter = new byte[len];
    for(int i=0,j=0; i<len; i++){
        if(array[i] == '\r'){
            continue;
        }
        filter[j] = array[i];
        j++;
    }
    return filter;
  }

  public static byte[] filterAndCut(byte[] array)
  {
    int len = strlen(array);
    byte[] filter = new byte[len];
    for(int i=0,j=0; i<len; i++){
        if(array[i] == '\r'){
            continue;
        }
        filter[j] = array[i];
        j++;
    }
    return filter;
  }

  public static int strlen(byte[] array)
  {
    if(array == null)
        return -1;
    int len = array.length;
    int index = 0;
    for(; index<len; index++){
        if(array[index] == 0)
            break;
    }
    return index;
  }

  public void finalize()
  {
  }
}

/* Location:           D:\document\bsd_work\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar
 * Qualified Name:     com.yunmai.android.other.StringUtil
 * JD-Core Version:    0.5.4
 */