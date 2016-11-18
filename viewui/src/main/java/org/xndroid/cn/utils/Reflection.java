package org.xndroid.cn.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class Reflection {
    private Reflection() {
    }

    public static Field[] getAllDeclaredFields(Class cls) {
        Field[] f = cls.getDeclaredFields();
        Field[] sf = cls.getSuperclass().getDeclaredFields();
        Field[] all = new Field[f.length + sf.length];
        System.arraycopy(f, 0, all, 0, f.length);
        System.arraycopy(sf, 0, all, f.length, sf.length);
        return all;
    }

    public static Method[] getAllDeclaredMethods(Class cls) {
        Method[] m = cls.getDeclaredMethods();
        Method[] sm = cls.getSuperclass().getDeclaredMethods();
        Method[] all = new Method[m.length + sm.length];
        System.arraycopy(m, 0, all, 0, m.length);
        System.arraycopy(sm, 0, all, m.length, sm.length);
        return all;
    }

    public static <T> Object generateObject(Class<T> clazz) {
        Constructor e = null;
        try {
            e = clazz.getConstructor(new Class[0]);
            return e.newInstance(new Object[0]);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
