package com.ym.idcard.reg.bean;


import com.ym.idcard.reg.engine.OcrEngine;

public class IDCard
{
  private int recogStatus = OcrEngine.RECOG_FAIL;
  private String ymAddress;
  private String ymAuthority;
  private String ymBirth;
  private String ymCardNo;
  private String ymEthnicity;
  private String ymMemo;
  private String ymName;
  private String ymPeriod;
  private String ymSex;

  public String getAddress()
  {
    return this.ymAddress;
  }

  public String getAuthority()
  {
    return this.ymAuthority;
  }

  public String getBirth()
  {
    return this.ymBirth;
  }

  public String getCardNo()
  {
    return this.ymCardNo;
  }

  public String getEthnicity()
  {
    return this.ymEthnicity;
  }

  public String getMemo()
  {
    return this.ymMemo;
  }

  public String getName()
  {
    return this.ymName;
  }

  public String getPeriod()
  {
    return this.ymPeriod;
  }

  public int getRecogStatus()
  {
    return this.recogStatus;
  }

  public String getSex()
  {
    return this.ymSex;
  }

  public void setAddress(String paramString)
  {
    this.ymAddress = paramString;
  }

  public void setAuthority(String paramString)
  {
    this.ymAuthority = paramString;
  }

  public void setBirth(String paramString)
  {
    this.ymBirth = paramString;
  }

  public void setCardNo(String paramString)
  {
    this.ymCardNo = paramString;
  }

  public void setEthnicity(String paramString)
  {
    this.ymEthnicity = paramString;
  }

  public void setMemo(String paramString)
  {
    this.ymMemo = paramString;
  }

  public void setName(String paramString)
  {
    this.ymName = paramString;
  }

  public void setPeriod(String paramString)
  {
    this.ymPeriod = paramString;
  }

  public void setRecogStatus(int paramInt)
  {
    this.recogStatus = paramInt;
  }

  public void setSex(String paramString)
  {
    this.ymSex = paramString;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("姓名：").append(this.ymName).append("\n");
    localStringBuffer.append("身份号码：").append(this.ymCardNo).append("\n");
    localStringBuffer.append("性别：").append(this.ymSex).append("\n");
    localStringBuffer.append("民族：").append(this.ymEthnicity).append("\n");
    localStringBuffer.append("出生：").append(this.ymBirth).append("\n");
    localStringBuffer.append("住址：").append(this.ymAddress).append("\n");
    localStringBuffer.append("签发机关：").append(this.ymAuthority).append("\n");
    localStringBuffer.append("有效期限：").append(this.ymPeriod).append("\n");
    return localStringBuffer.toString();
  }
}

/* Location:           D:\document\bsd_work\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar
 * Qualified Name:     com.yunmai.android.vo.IDCard
 * JD-Core Version:    0.5.4
 */