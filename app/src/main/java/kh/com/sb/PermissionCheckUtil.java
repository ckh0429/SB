package kh.com.sb;

import android.content.Context;
import android.os.Build;

public class PermissionCheckUtil {
    public static boolean isNeedRuntimePermission(Context context) {
        if (context.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.M) {
            return false;
        } else {
            return true;
        }
    }
}
