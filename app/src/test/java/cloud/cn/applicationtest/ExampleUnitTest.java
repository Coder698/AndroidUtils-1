package cloud.cn.applicationtest;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.test.InstrumentationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.xutils.common.util.LogUtil;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends InstrumentationTestCase {
    @Test
    public void testLocation() throws Exception {
        LocationManager locationManager = (LocationManager)getInstrumentation().getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setCostAllowed(true);
        String provider = locationManager.getBestProvider(criteria, true);
        LogUtil.d(provider);
    }
}