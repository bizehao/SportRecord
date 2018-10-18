package com.bzh.sportrecord.module.home.homeNews;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;

public class NewsFragment extends BaseFragment {

    private static final String TAG = "NewsFragment";

    /*@BindView(R.id.mapView)
    MapView mMapView;*/


    /*private FeatureLayer mLayer;*/


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //setupMap();
    }

    @Override
    protected void inject() {
    }

    /*private void setupMap() {
        if (mMapView != null) {
            String url = "http://192.168.1.100:6080/arcgis/rest/services/XTMap/MapServer";
            String myLayerId = "d2ff12395aeb45998c1b15";
            Portal portalService = new Portal(url, false);
            PortalItem portalItemLayer = new PortalItem(portalService, myLayerId);
            ArcGISMap map = new ArcGISMap(portalItemLayer);
            *//*float latitude = 37.276f;
            float longitude = 114.806f;
            mMapView.setScaleX(latitude);
            mMapView.setScaleY(longitude);*//*
            mMapView.setMap(map);
        }
    }*/

    /*private void showWebMap() {
        String itemId = "41281c51f9de45edaf1c8ed44bb10e30";
        String url = "http://192.168.1.100:6080/arcgis/rest/services/XTMap/MapServer?f=jsapi";
        ArcGISMap map = new ArcGISMap(url);
        mMapView.setMap(map);
    }

    //添加图层
    private void addTrailheadsLayer() {
        String url = "http://192.168.1.100:6080/arcgis/rest/services/XTMap/MapServer";
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        ArcGISMap map = mMapView.getMap();
        map.getOperationalLayers().add(featureLayer);
    }

    private void addLayer(final ArcGISMap map) {
        Portal portal = new Portal("http://www.arcgis.com");
        final PortalItem portalItem = new PortalItem(portal, "883cedb8c9fe4524b64d47666ed234a7");
        mLayer = new FeatureLayer(portalItem, 0);
        mLayer.loadAsync();
        mLayer.addDoneLoadingListener(new Runnable() {
            // *** ADD ***
            @Override
            public void run() {
                if (mLayer.getLoadStatus() == LoadStatus.LOADED) {
                    Viewpoint viewpoint = new Viewpoint(mLayer.getFullExtent());
                    map.setInitialViewpoint(viewpoint);
                    // *** ADD ***
                    map.getOperationalLayers().add(mLayer);
                    mMapView.setMap(map);
                }
            }
        });
    }*/

    /*@Override
    public void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    public void onDestroy() {
        mMapView.dispose();
        super.onDestroy();
    }*/

}
