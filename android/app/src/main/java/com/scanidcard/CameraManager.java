package com.scanidcard;

import android.hardware.Camera;
import android.view.View;

import java.util.List;

/**
 * 相机管理
 * Created by mmjbds999 on 2017/6/5.
 */
public class CameraManager {

    private int id = -1;//相机ID
    private Camera camera;//相机对象

    /**
     * 默认构造函数，当创建对象的时候，返回一个ID为-1的相机实例
     */
    public CameraManager(){
        camera = getInstance();
    }

    /**
     * 获取相机实例
     * @return
     */
    public Camera getCamera(){
        if (camera == null){
            camera = getInstance();
        }
        return camera;
    }

    /**
     * 根据相机类型获取实例（前置/后置）
     * @param cameraType
     * @return
     */
    public Camera getCamera(String cameraType){
        if (camera == null){
            camera = getInstance(cameraType);
        }
        return camera;
    }

    /**
     * 默认实例，ID为-1
     * @return
     */
    public Camera getInstance() {
        return getInstance(-1);
    }

    /**
     * 释放相机
     */
    public void releaseCamera(){
        if(camera != null){
            camera.release();
            camera = null;
        }
    }

    /**
     * 根据相机类型获取ID并创建实例（前置/后置）
     * @param cameraType
     * @return
     */
    public Camera getInstance(String cameraType) {
        id = -1;

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for (int cameraId = 0; cameraId < Camera.getNumberOfCameras(); cameraId++) {
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (cameraType.equals("back") && cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                id = cameraId;
                break;
            }
            if (cameraType.equals("front") && cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                id = cameraId;
                break;
            }
        }

        return getInstance(id);
    }

    /**
     * 根据相机ID创建实例
     * @param id
     * @return
     */
    public Camera getInstance(int id){
        Camera c = null;
        try{
            if(id == -1){
                c = Camera.open();//获取相机的实例
            }else{
                c = Camera.open(id);
            }
        }catch (Exception e){
            //未找到相机对象，正在使用或者没有相机
        }
        return c;
    }

    /**
     * 判断相机是否有Flash支持
     * @param camera
     * @return
     */
    public boolean isFlashSupported(Camera camera){
        if(camera != null){
            Camera.Parameters parameters = camera.getParameters();
            if(parameters.getFlashMode() == null){
                return false;
            }

            List<String> supportedFlashModes = parameters.getSupportedFlashModes();
            if(supportedFlashModes == null || supportedFlashModes.isEmpty() || supportedFlashModes.size() == 1 && supportedFlashModes.get(0).equals(Camera.Parameters.FLASH_MODE_OFF)){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

}
