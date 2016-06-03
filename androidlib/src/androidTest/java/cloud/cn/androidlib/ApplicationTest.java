package cloud.cn.androidlib;

import android.app.Application;
import android.os.Environment;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import org.xutils.common.util.LogUtil;

import java.io.File;

import cloud.cn.androidlib.utils.FileUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testUnZip() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        boolean success = FileUtils.unZipFiles(new File(path, "WebInfos.zip"));
        Assert.assertEquals(success, true);
    }
}