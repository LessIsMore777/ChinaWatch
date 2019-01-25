package com.waterworld.watch.location.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.waterworld.watch.R;
import com.waterworld.watch.location.activity.ElectronicFenceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationFragment extends Fragment {

    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.iv_refresh)
    ImageView iv_refresh;

    @BindView(R.id.iv_navigation)
    ImageView iv_navigation;

    @BindView(R.id.iv_electronic_fence)
    ImageView iv_electronic_fence;

    @BindView(R.id.iv_change)
    ImageView iv_change;

    @BindView(R.id.iv_track)
    ImageView iv_track;

    private AMap aMap = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_position,container,false);
        ButterKnife.bind(this,view);
        mMapView.onCreate(savedInstanceState);
        if(aMap == null){
            aMap = mMapView.getMap();
        }

        LatLng latLng = new LatLng(22.4988589253,113.9208197594);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
        return view;
    }


    @OnClick(R.id.iv_electronic_fence)
    void onElectonicFence(){
        startActivity(new Intent(this.getContext(), ElectronicFenceActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
