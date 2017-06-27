package com.ym.idcard.reg.engine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Rect;
import android.util.Log;

import com.ym.idcard.reg.NativeOcr;
import com.ym.idcard.reg.bean.IDCard;
import com.ym.idcard.reg.util.FileUtil;
import com.ym.idcard.reg.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OcrEngine {
	private static final int BIDC_ADDRESS = 6;
	private static final int BIDC_BIRTHDAY = 5;
	private static final int BIDC_CARDNO = 3;
	private static final int BIDC_FOLK = 11;
	private static final int BIDC_ISSUE_AUTHORITY = 7;
	private static final int BIDC_MEMO = 99;
	private static final int BIDC_NAME = 1;
	private static final int BIDC_NON = 0;
	private static final int BIDC_SEX = 4;
	private static final int BIDC_VALID_PERIOD = 9;
	private static final int MIN_HEIGHT_LIMIT = 720;
	private static final int MIN_WIDTH_LIMIT = 1024;
	private static final int OCR_CODE_B5 = 2;
	private static final int OCR_CODE_GB = 1;
	private static final int OCR_CODE_GB2B5 = 3;
	private static final int OCR_CODE_NIL = 0;
	private static final int OCR_LAN_CENTEURO = 7;
	private static final int OCR_LAN_CHINESE_SIMPLE = 2;
	private static final int OCR_LAN_CHINESE_TRADITIONAL = 21;
	private static final int OCR_LAN_ENGLISH = 1;
	private static final int OCR_LAN_EUROPEAN = 3;
	private static final int OCR_LAN_JAPAN = 6;
	private static final int OCR_LAN_NIL = 0;
	private static final int OCR_LAN_RUSSIAN = 4;
	private static boolean OPT_CANCEL = false;
	public static final int RECOG_BLUR = 3;
	public static final int RECOG_BLUR_TIP = 5;
	public static final int RECOG_CANCEL = -1;
	public static final int RECOG_FAIL = -2;
	public static final int RECOG_LANGUAGE = 4;
	public static final int RECOG_NONE = 0;
	public static final int RECOG_OK = 1;
	public static final int RECOG_SMALL = 2;
	protected boolean mBeCancel = false;
	protected NativeOcr mNativeOcr = new NativeOcr();
	protected long pEngine = 0L;
	protected long pField = 0L;
	protected long pImage = 0L;
	protected long[] ppEngine = new long[1];
	protected long[] ppField = new long[1];
	protected long[] ppImage = new long[1];
	private static final String TAG = "OcrEngine";

	private void closeBCR() {
		if ((this.ppEngine == null) || (this.mNativeOcr == null))
			return;
		this.mNativeOcr.closeBCR(this.ppEngine);
		this.ppEngine[0] = 0L;
		this.pEngine = 0L;
	}

	public static void doCancel() {
		OPT_CANCEL = true;
	}

	private boolean doImageBCR() {
		this.mBeCancel = false;
		this.mNativeOcr.setoption(this.pEngine,
				StringUtil.convertToUnicode("-fmt"), null);
		int result = this.mNativeOcr.doImageBCR(this.pEngine, this.pImage,
				this.ppField);
		if (result == RECOG_OK) {
			this.pField = this.ppField[0];
			return true;
		} else if (result == RECOG_BLUR) {
			this.mBeCancel = true;
		}
		return false;
	}

	private boolean fields2Object(IDCard paramIDCard, int keyLanguage) {
		if (paramIDCard == null)
			return false;
		while (!isFieldEnd()) {
			switch (getFieldId()) {
			case BIDC_NAME:
				paramIDCard.setName(getFieldText(keyLanguage));
				break;
			case BIDC_SEX:
				paramIDCard.setSex(getFieldText(keyLanguage));
				break;
			case BIDC_CARDNO:
				paramIDCard.setCardNo(getFieldText(keyLanguage));
				break;
			case BIDC_FOLK:
				paramIDCard.setEthnicity(getFieldText(keyLanguage));
				break;
			case BIDC_BIRTHDAY:
				paramIDCard.setBirth(getFieldText(keyLanguage));
				break;
			case BIDC_ADDRESS:
				paramIDCard.setAddress(getFieldText(keyLanguage));
				break;
			case BIDC_ISSUE_AUTHORITY:
				paramIDCard.setAuthority(getFieldText(keyLanguage));
				break;
			case BIDC_VALID_PERIOD:
				paramIDCard.setPeriod(getFieldText(keyLanguage));
				break;
			case BIDC_MEMO:
				paramIDCard.setMemo(getFieldText(keyLanguage));
				break;
			}
			getNextField();
		}
		return true;

	}

	private void freeBFields() {
		if (this.mNativeOcr == null)
			return;
		this.mNativeOcr.freeBField(this.pEngine, this.ppField[0], 0);
		this.ppField[0] = 0L;
		this.pField = 0L;
	}

	private void freeImage() {
		if (this.mNativeOcr == null)
			return;
		this.mNativeOcr.freeImage(this.pEngine, this.ppImage);
		this.ppImage[0] = 0L;
		this.pImage = 0L;
	}

	private int getFieldId() {
		long l = this.pField;
		return this.mNativeOcr.getFieldId(l);
	}

	private Rect getFieldRect() {
		Rect localRect = new Rect();
		long l = this.pField;
		int[] arrayOfInt = new int[4];
		this.mNativeOcr.getFieldRect(l, arrayOfInt);
		localRect.left = arrayOfInt[0];
		localRect.top = arrayOfInt[1];
		localRect.right = arrayOfInt[2];
		localRect.bottom = arrayOfInt[3];
		return localRect;
	}

	private String getFieldText(int keyLanguage) {
		byte[] arrayOfByte = new byte[256];
		this.mNativeOcr.getFieldText(this.pField, arrayOfByte,
				arrayOfByte.length);
		if ((keyLanguage == OCR_CODE_GB2B5)) {
			this.mNativeOcr.codeConvert(this.pEngine, arrayOfByte, keyLanguage);
			return StringUtil.convertBig5ToUnicode(arrayOfByte);
		} else {
			return StringUtil.convertGbkToUnicode(arrayOfByte);
		}
	}

	private void getNextField() {
		if (isFieldEnd())
			return;
		this.pField = this.mNativeOcr.getNextField(this.pField);
	}

	private boolean isBlurImage() {
		if (mNativeOcr == null)
			return false;
		if (mNativeOcr.imageChecking(pEngine, pImage, 0) == 2) {
			return true;
		}
		return false;
	}

	private boolean isCancel() {
		return this.mBeCancel;
	}

	private boolean isFieldEnd() {
		return pField == 0;
	}

	private boolean loadImageMem(long pBuffer, int width, int height,
			int component) {
		if (pBuffer != 0) {
			this.pImage = this.mNativeOcr.loadImageMem(this.pEngine, pBuffer,
					width, height, component);
			if (this.pImage != 0L) {
				this.ppImage[0] = this.pImage;
				return true;
			}
		}
		return false;
	}

	private IDCard recognize(Context context, int ocrLanguage, String imagePath)
			throws IOException {
		return recognize(context, ocrLanguage,
				FileUtil.getBytesFromFile(new File(imagePath)), null);
	}

	public IDCard recognize(Context context, int ocrLanguage,
							byte[] idCardDataA, byte[] idCardDataB) {
		IDCard idCard = new IDCard();
		int keyLanguage = OCR_CODE_GB;
		boolean blurDetection = false;
		if (ocrLanguage == OCR_LAN_CHINESE_TRADITIONAL)
			keyLanguage = OCR_LAN_EUROPEAN;
		OPT_CANCEL = false;
		byte[] license = new byte[256];
		AssetManager localAssetManager = context.getAssets();
		try {
			InputStream localInputStream = localAssetManager
					.open("license.info");
			localInputStream.read(license);
			localInputStream.close();
		} catch (IOException localIOException) {

		}
		if (startBCR("", "", ocrLanguage, license)) {
			if (idCardDataA != null)
				idCard = recognizing(idCardDataA, blurDetection, keyLanguage);
			if (idCardDataB != null) {
				IDCard idCardB = recognizing(idCardDataB, blurDetection,
						keyLanguage);
				idCard.setAuthority(idCardB.getAuthority());
				idCard.setPeriod(idCardB.getPeriod());
			}
			closeBCR();
		}
		return idCard;
	}

	private IDCard recognizing(byte[] imgBuffer, boolean blurDetection,
			int keyLanguage) {
		IDCard idCard = new IDCard();
		ImageEngine imageEngine = new ImageEngine();
		if (imageEngine.init(1, 90) && imageEngine.load(imgBuffer)) {
			int width = imageEngine.getWidth();
			int height = imageEngine.getHeight();
			int component = imageEngine.getComponent();
			long pBuffer = imageEngine.getDataEx();
			boolean result = loadImageMem(pBuffer, width, height, component);
			imageEngine.finalize();
			if (result) {
				if (!blurDetection || !isBlurImage() || OPT_CANCEL) {
					if (doImageBCR()) {
						if (fields2Object(idCard, keyLanguage)) {
							idCard.setRecogStatus(RECOG_OK);
						}
					} else {
						if (isCancel()) {
							idCard.setRecogStatus(RECOG_CANCEL);
						}
					}
					freeBFields();
				} else {
					idCard.setRecogStatus(RECOG_BLUR);
				}
			}
			freeImage();
		}
		return idCard;
	}

	private void setProgressFunc(boolean paramBoolean) {
		if ((this.pEngine == 0L) || (this.mNativeOcr == null))
			return;
		this.mNativeOcr.setProgressFunc(this.pEngine, paramBoolean);
	}

	public String byteToHexString(byte[] b) {
		String str = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}

			str += hex;
		}
		return str;
	}

	private boolean startBCR(String cfgPath, String dataPath, int lanuage,
							 byte[] license) {
		Log.v(TAG, "before navtive startBCR");
		Log.v(TAG, "dataPath:" + dataPath);
		Log.v(TAG, "cfgPath:" + cfgPath);
		Log.v(TAG, "license:" + byteToHexString(license));

		if (this.mNativeOcr.startBCR(this.ppEngine,
				StringUtil.convertUnicodeToAscii(dataPath),
				StringUtil.convertUnicodeToAscii(cfgPath), lanuage, license) == RECOG_OK) {
			Log.v(TAG, "after navtive startBCR");
			this.pEngine = this.ppEngine[0];
			return true;
		}
		Log.v(TAG, "after navtive startBCR");
		return false;
	}

	public void finalize() {
		this.ppEngine = null;
		this.ppImage = null;
		this.ppField = null;
		this.mNativeOcr = null;
		this.pEngine = 0L;
		this.pImage = 0L;
	}

	public IDCard recognize(Context paramContext, String paramString)
			throws IOException {
		return recognize(paramContext, 2, paramString);
	}

	public IDCard recognize(Context paramContext, byte[] paramArrayOfByte) {
		return recognize(paramContext, 2, paramArrayOfByte, null);
	}

	public IDCard recognize(Context paramContext, byte[] paramArrayOfByte1,
							byte[] paramArrayOfByte2) {
		return recognize(paramContext, 2, paramArrayOfByte1, paramArrayOfByte2);
	}
}

