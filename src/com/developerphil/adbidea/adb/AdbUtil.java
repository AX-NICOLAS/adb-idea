package com.developerphil.adbidea.adb;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.developerphil.adbidea.adb.command.receiver.GenericReceiver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AdbUtil {
    public static boolean isAppInstalled(IDevice device, String packageName) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        GenericReceiver receiver = new GenericReceiver();
        // "pm list packages com.my.package" will return one line per package installed that corresponds to this package.
        // if this list is empty, we know for sure that the app is not installed
        device.executeShellCommand("pm list packages " + packageName, receiver, 5L, TimeUnit.MINUTES);

        //TODO make sure that it is the exact package name and not a subset.
        // e.g. if our app is called com.example but there is another app called com.example.another.app, it will match and return a false positive
        return !receiver.getAdbOutputLines().isEmpty();
    }
}
