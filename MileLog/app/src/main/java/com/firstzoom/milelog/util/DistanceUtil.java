package com.firstzoom.milelog.util;

import com.firstzoom.milelog.model.LocationPath;
import com.firstzoom.milelog.room.AppDatabase;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class DistanceUtil {
    public static Double   calDistance(List<LocationPath> location)
    {

        ArrayList<LatLng> latLngs=new ArrayList();
        for (LocationPath loc :  location){
            if(loc.getLatitude()!=null && loc.getLongitude()!=null)
                latLngs.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
        }
        Double dist = SphericalUtil.computeLength(latLngs)/1000;

        return dist;

    }
}
