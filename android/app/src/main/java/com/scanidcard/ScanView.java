package com.scanidcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.ym.idcard.reg.engine.OcrEngine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mmjbds999 on 2017/6/5.
 */
public class ScanView extends FrameLayout implements Camera.PictureCallback{

    private CameraPreview cameraPreview;//相机预览组件

    private View idCardView;

    private static final String TAG = "ScanView";

    private static boolean isStop = false;

    /**
     * 默认构造函数
     * @param context
     */
    public ScanView(Context context) {
        super(context);

        cameraPreview = new CameraPreview(context, this);
        idCardView = new IdCardView(context);

        this.addView(cameraPreview);
        this.addView(idCardView);
    }

    public void onResume() {
        cameraPreview.startCamera();
    }

    public void onPause() {
        cameraPreview.stopCamera();
    }

    public void setCameraType(String cameraType) {
        cameraPreview.setCameraType(cameraType);
    }

    public void setFlash(boolean flag) {
        cameraPreview.setFlash(flag);
    }

    public void stopCamera() {
        cameraPreview.stopCamera();
    }

    /** 保存方法 */
    public void saveBitmap(Bitmap bm) {
        Log.e(TAG, "保存图片");
        getContext().getExternalFilesDir(null).getAbsolutePath();
        File f = new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/files/test.jpg");
        try {
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if(isStop){
            cameraPreview.stopCamera();
            isStop = false;
            return;
        }
        camera.startPreview();
        try {
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

            Matrix m = new Matrix();
            //图片高宽压缩一半
            //m.setScale(0.5f, 0.5f);
            //bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);

            //旋转90度
            if(bmp.getWidth()>bmp.getHeight()){
                m.setRotate(90, (float) bmp.getWidth(), (float) bmp.getHeight());
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
            }

            bmp = ImageFilter.curtImage(bmp, 0.30);//截取中间部分

            ByteArrayOutputStream cropStream = new ByteArrayOutputStream();
//            // 参数0，30
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, cropStream);

            //保存下来看看
//            saveBitmap(bmp);

            /*身份证扫描核心代码*/
            OcrEngine ocrEngine = new OcrEngine();
            com.ym.idcard.reg.bean.IDCard idCard = ocrEngine.recognize(getContext(), 2, cropStream.toByteArray(), null);

            if(idCard.getCardNo()!=null && idCard.getCardNo().length()==18 && IDCard.IDCardValidate(idCard.getCardNo()).equals("")
                    && !idCard.getName().isEmpty() && validateName(idCard.getName())){
                isStop = true;
                WritableMap event = Arguments.createMap();
                event.putString("name", idCard.getName());
                event.putString("id", idCard.getCardNo());
                event.putInt("age", getAge(idCard.getCardNo()));
                event.putString("sex", getSex(idCard.getCardNo()));

                ReactContext reactContext = (ReactContext)getContext();
                sendEvent(reactContext, "scanCallBack", event);
            }

        }catch (Exception e){
            Log.e(TAG, e.toString(), e);
        }
    }

    /**
     * 校验姓名是否是中文
     * @return
     */
    public boolean validateName(String str){
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]{2,5}(?:·[\\u4E00-\\u9FA5]{2,5})*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 根据身份证获取性别
     * @param IdNO
     * @return
     * @throws Exception
     */
    public String getSex(String IdNO)
            throws Exception {
        String sex;
        if (Integer.parseInt(IdNO.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }

    /**
     * 根据身份证获取年龄
     * @param IdNO
     * @return
     */
    public int getAge(String IdNO){
        int leh = IdNO.length();
        String dates="";
        if (leh == 18) {
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else{
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }

    }

    /**
     * 发送事件
     * @param reactContext
     * @param eventName
     * @param params
     */
    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

}
