package kh.com.sb;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import kh.com.sb.module.PermissionCheckUtil;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("kh.com.sb", appContext.getPackageName());
    }

    @Test
    public void isNeedRuntimePermission() {
        boolean isNeed = PermissionCheckUtil.isNeedRuntimePermission(InstrumentationRegistry.getTargetContext());
        // test device need runtime permission check
        assertEquals(true, isNeed);
    }
}

