package com.insightsurface.lib.utils;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private PropertiesUtil() {

    }

    private static volatile Properties instance = null;

    public static Properties getInstance(Context context) {
        if (instance == null) {
            //线程锁定
            synchronized (PropertiesUtil.class) {
                //双重锁定
                if (instance == null) {
                    instance = getProperties(context);
                }
            }
        }
        return instance;
    }

    private static Properties getProperties(Context c) {
        Properties urlProps;
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            //注意这地方的参数appConfig在eclipse中应该是appConfig.properties才对,但在studio中不用写后缀
            //InputStream in = c.getAssets().open("appConfig.properties");
            InputStream in = c.getAssets().open("appConfig");
            //方法二：通过class获取setting.properties的FileInputStream
            //InputStream in = PropertiesUtill.class.getResourceAsStream("/assets/  setting.properties "));
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        urlProps = props;
        return urlProps;
    }
}
