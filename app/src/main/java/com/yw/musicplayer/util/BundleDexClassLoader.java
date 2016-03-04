package com.yw.musicplayer.util;

import dalvik.system.DexClassLoader;

/**
 * Created by wengyiming on 2016/3/4.
 */
public class BundleDexClassLoader extends DexClassLoader {

    public BundleDexClassLoader(String dexPath, String optimizedDirectory,
                                String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

}
