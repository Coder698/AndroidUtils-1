package cloud.cn.applicationtest.service;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import org.xutils.common.util.LogUtil;

import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;

/**
 * Created by Cloud on 2016/5/8 0008.
 */
public class LocationService extends Service{
    private LocationManager locationManager;
    private LocationListener locationListener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setCostAllowed(true);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, true);
        locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String msg = "accuracy " + location.getAccuracy();
            msg += " longitude " + location.getLongitude();
            msg += " latitude " + location.getLatitude();
            SmsManager smsManager = SmsManager.getDefault();
            String safeNum = PrefUtils.getString(AppConstants.PREF.SAFE_PHONE_NUM, "");
            smsManager.sendTextMessage(safeNum, null, msg, null, null);
            LogUtil.d(msg);
        }

        //可用到不可用，不可用到可用时调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        //当provider可用时调用
        @Override
        public void onProviderEnabled(String provider) {

        }

        //当provider不可用时调用
        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
