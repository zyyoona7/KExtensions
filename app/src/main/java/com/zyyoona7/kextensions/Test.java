package com.zyyoona7.kextensions;

import com.zyyoona7.encrypts.Encrypts;

/**
 * @author zyyoona7
 * @version v1.0
 * @since 2018/9/26.
 */
public class Test {

    public static String md5(String receiver){
        return Encrypts.md5(receiver);
    }
}
