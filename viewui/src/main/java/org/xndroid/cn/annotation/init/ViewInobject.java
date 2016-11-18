package org.xndroid.cn.annotation.init;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.xndroid.cn.annotation.DynamicTrackProxyHandler;
import org.xndroid.cn.annotation.Layout;
import org.xndroid.cn.annotation.ViewCilck;
import org.xndroid.cn.annotation.ViewIn;
import org.xndroid.cn.annotation.ViewInflate;
import org.xndroid.cn.utils.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class ViewInobject {

    public static View initLayoutViews(Object obj, Context context) {
        Layout layout = (Layout) obj.getClass().getAnnotation(Layout.class);
        if (layout != null) {
            View parent = View.inflate(context, layout.value(), (ViewGroup) null);
            injectViewField2Obj(obj, parent);
            injectEvent2Obj(obj, parent);
            return parent;
        } else {
            throw new RuntimeException("LAYOUT_RES_ID NOT CORRECT");
        }
    }

    public static View initAdapterLayoutViews(Object obj, Context context, ViewGroup viewGroup) {
        Layout layout = (Layout) obj.getClass().getAnnotation(Layout.class);
        if (layout != null) {
            return View.inflate(context, layout.value(), viewGroup);
        } else {
            throw new RuntimeException("LAYOUT_RES_ID NOT CORRECT");
        }
    }

    private static void injectEvent2Obj(Object obj, View parent) {
        for (Method method : Reflection.getAllDeclaredMethods(obj.getClass())) {
            method.setAccessible(true);
            ViewCilck click = method.getAnnotation(ViewCilck.class);
            if (click != null) {
                Integer viewId = click.value();
                String listenerSetter = click.listenerSetter();
                Class listenerType = click.listenerType();
                DynamicTrackProxyHandler handler = new DynamicTrackProxyHandler();
                handler.addMethod("onClick", method);
                handler.addHandler("onClick", obj);
                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                View view = parent.findViewById(viewId);
                if (view == null) {
                    throw new IllegalArgumentException(method.getName() + "() have a invalid id with event component");
                }
                try {
                    Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, new Class[]{listenerType});
                    setEventListenerMethod.invoke(view, new Object[]{listener});
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
//        Observable.from(Reflection.getAllDeclaredMethods(obj.getClass())).subscribe(method -> {
//            method.setAccessible(true);
//            ViewCilck click = method.getAnnotation(ViewCilck.class);
//            if (click != null) {
//                Integer viewId = click.value();
//                String listenerSetter = click.listenerSetter();
//                Class listenerType = click.listenerType();
//                DynamicTrackProxyHandler handler = new DynamicTrackProxyHandler();
//                handler.addMethod("onClick", method);
//                handler.addHandler("onClick", obj);
//                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
//                View view = parent.findViewById(viewId);
//                if (view == null) {
//                    throw new IllegalArgumentException(method.getName() + "() have a invalid id with event component");
//                }
//                try {
//                    Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, new Class[]{listenerType});
//                    setEventListenerMethod.invoke(view, new Object[]{listener});
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
    }

    private static void injectViewField2Obj(Object obj, View parent) {
        Field[] all = Reflection.getAllDeclaredFields(obj.getClass());
        for (Field field : all) {
            ViewIn vin = field.getAnnotation(ViewIn.class);
            field.setAccessible(true);
            if (vin != null) {
                View view = parent.findViewById(vin.value());
                try {
                    field.set(obj, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            ViewInflate inflate = field.getAnnotation(ViewInflate.class);
            field.setAccessible(true);
            if (inflate != null) {
                View view = View.inflate(parent.getContext(), inflate.value(), (ViewGroup) null);
                try {
                    field.set(obj, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
//        Observable.from(Reflection.getAllDeclaredFields(obj.getClass())).subscribe(field -> {
//            ViewIn vin = field.getAnnotation(ViewIn.class);
//            field.setAccessible(true);
//            if (vin != null) {
//                View view = parent.findViewById(vin.value());
//                try {
//                    field.set(obj, view);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//            ViewInflate inflate = field.getAnnotation(ViewInflate.class);
//            field.setAccessible(true);
//            if (inflate != null) {
//                View view = View.inflate(parent.getContext(), inflate.value(), (ViewGroup) null);
//                try {
//                    field.set(obj, view);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
    }
}
