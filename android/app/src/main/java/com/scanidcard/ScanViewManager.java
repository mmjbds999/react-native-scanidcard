package com.scanidcard;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nullable;

/**
 * Created by mmjbds999 on 2017/6/2.
 */
public class ScanViewManager extends ViewGroupManager<ScanView> implements LifecycleEventListener {

    private static final String REACT_CLASS = "RCTScanView";

    private static final String DEFAULT_TORCH_MODE = "off";
    private static final String DEFAULT_CAMERA_TYPE = "back";

    private ScanView scanView;
    private boolean scanViewVisible;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(name = "cameraType")
    public void setCameraType(ScanView view, @Nullable String cameraType) {
        if (cameraType != null) {
            view.setCameraType(cameraType);
        }
    }

    @ReactProp(name = "torchMode")
    public void setTorchMode(ScanView view, @Nullable String torchMode) {
        if (torchMode != null) {
            view.setFlash(torchMode.equals("on"));
        }
    }

    @Override
    protected ScanView createViewInstance(ThemedReactContext reactContext) {
        reactContext.addLifecycleEventListener(this);
        scanView = new ScanView(reactContext);
        scanView.setCameraType(DEFAULT_CAMERA_TYPE);
        scanView.setFlash(DEFAULT_TORCH_MODE.equals("on"));
        scanViewVisible = true;
        return scanView;
    }

    @Override
    public void onHostResume() {
        scanView.onResume();
    }

    @Override
    public void onHostPause() {
        scanView.onPause();
    }

    @Override
    public void onHostDestroy() {
        scanView.stopCamera();
    }

    @Override
    public void addView(ScanView parent, View child, int index) {
        parent.addView(child, index + 1);   // index 0 for camera preview reserved
    }

}
