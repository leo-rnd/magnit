package com.magnit;

import java.io.File;

/**
 * Created by Oleg Letunovskij on 13.08.2016.
 */
public final class Utils {

    public static void createDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
    };
}
