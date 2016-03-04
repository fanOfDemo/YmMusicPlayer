/**
 * Created by wengyiming on 2015/12/20.
 */
package com.yw.musicplayer.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ResFinder {
    private static Map<ResItem, Integer> resItemIntegerMap = new HashMap();
    private static final int b = -1;
    private static Context context;
    private static String packeageName = "";

    public ResFinder() {
    }

    public static void initContext(Context context) {
        ResFinder.context = context;
        if(ResFinder.context == null) {
            throw new NullPointerException("初始化ResFinder失败，传递的Context为空.");
        } else {
            packeageName = ResFinder.context.getPackageName();
        }
    }

    public static void setPackageName(String var0) {
        packeageName = var0;
    }

    public static Context getApplicationContext() {
        return context;
    }

    private static int getId(ResFinder.ResItem resItem) {
        return resItemIntegerMap.containsKey(resItem)? resItemIntegerMap.get(resItem) :-1;
    }
    public static int getResourceId(ResFinder.ResType resType, String name) {
        ResFinder.ResItem resItem = new ResFinder.ResItem(resType, name);
        int id = getId(resItem);
        if(id != -1) {
            return id;
        } else {
            Resources resources = context.getResources();
            id = resources.getIdentifier(name, resType.toString(), packeageName);
            if(id <= 0) {
                throw new RuntimeException("获取资源ID失败:(packageName=" + packeageName + " type=" + resType + " name=" + name + ", 请确保的res/" + resType.toString() + "目录中含有该资源");
            } else {
                resItemIntegerMap.put(resItem, id);
                return id;
            }
        }
    }

    public static String getString(String name) {
        int var1 = getResourceId(ResFinder.ResType.STRING, name);
        return context.getString(var1);
    }

    public static int getLayout(String name) {
        return getResourceId(ResFinder.ResType.LAYOUT, name);
    }

    public static int getColor(String name) {
        return context.getResources().getColor(getResourceId(ResFinder.ResType.COLOR, name));
    }

    public static int getId(String name) {
        return getResourceId(ResFinder.ResType.ID, name);
    }

    public static int getStyle(String name) {
        return getResourceId(ResFinder.ResType.STYLE, name);
    }

    public static int getStyleableId(String name) {
        return getResourceId(ResFinder.ResType.STYLEABLE, name);
    }

    public static int[] getStyleableArrts(String name) {
        return getStyleAble(context, name);
    }

    public static float getDimen(String name) {
        return context.getResources().getDimension(getResourceId(ResFinder.ResType.DIMEN, name));
    }

    public static Drawable getDrawable(String name) {
        return context.getResources().getDrawable(getResourceId(ResFinder.ResType.DRAWABLE, name));
    }

    private static int[] getStyleAble(Context context, String name) {
        try {
            Log.d("finder", "name:" + name);
            Field[] fields = Class.forName(context.getPackageName() + ".R$styleable").getFields();
            Log.d("finder", "size:" + fields.length);
            for (Field field : fields) {
                Log.d("finder", "field name:" + field.getName());
                if (field.getName().equals(name)) {
                    return (int[]) field.get(null);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    public static int getStyleableIndex(String s) {
        int i = 0;
        try {
            Field[] fields = Class.forName(context.getPackageName() + ".R$styleable").getFields();

            for(int i1 = 0; i1 < fields.length; ++i1) {
                Field field = fields[i1];
                if(field.getName().equals(s)) {
                    i = i1;
                    break;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return i - 1;
    }


    /**
     * 获取 layout 布局文件
     * @param context Context
     * @param resName  layout xml 的文件名
     * @return layout
     */
    public static int getLayoutId(Context context, String resName) {
        int resId = context.getResources().getIdentifier(resName, ResType.LAYOUT.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.LAYOUT.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;
    }

    /**
     * 获取 string 值
     * @param context  Context
     * @param resName   string name的名称
     * @return string
     */
    public static int getStringId(Context context, String resName) {
        int resId = context.getResources().getIdentifier(resName,  ResType.STRING.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.STRING.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;
    }

    /**
     * 获取 drawable 布局文件 或者 图片的
     * @param context  Context
     * @param resName drawable 的名称
     * @return drawable
     */
    public static int getDrawableId(Context context, String resName) {
        int resId = context.getResources().getIdentifier(resName,  ResType.DRAWABLE.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.DRAWABLE.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;
    }


    /**
     * 获取 style
     * @param context Context
     * @param resName  style的名称
     * @return style
     */
    public static int getStyleId(Context context, String resName) {
        int resId = context.getResources().getIdentifier(resName,  ResType.STYLE.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.STYLE.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;
    }

    /**
     * 获取 styleable
     * @param context  Context
     * @param resName  styleable 的名称
     * @return styleable
     */
    public static Object getStyleableId(Context context, String resName){
        int resId = context.getResources().getIdentifier(resName,  ResType.STYLEABLE.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.STYLEABLE.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;
    }


    /**
     * 获取 anim
     * @param context  Context
     * @param resName  anim xml 文件名称
     * @return anim
     */
    public static int getAnimId(Context context, String resName) {
        int resId = context.getResources().getIdentifier(resName,  ResType.ANIM.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.ANIM.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;

    }

    /**
     * 获取 id
     * @param context Context
     * @param resName id 的名称
     * @return
     */
    public static int getId(Context context, String resName) {
        int resId = context.getResources().getIdentifier(resName,  ResType.ID.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.ID.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;
    }

    /**
     * color
     * @param context  Context
     * @param resName  color 名称
     * @return
     */
    public static int getColorId(Context context, String resName) {
        int resId = context.getResources().getIdentifier(resName,  ResType.COLOR.toString(),
                context.getPackageName());
        if(resId <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + context.getPackageName() + " type=" + ResType.COLOR.toString() + " name=" + resName + ", 请确保的res/" +  ResType.LAYOUT.toString() + "目录中含有该资源");
        }
        return resId;
    }
    public static class ResItem {
        public ResFinder.ResType mType;
        public String mName;

        public ResItem(ResFinder.ResType resType, String str) {
            this.mType = resType;
            this.mName = str;
        }

        public int hashCode() {
            byte i = 1;
            int i1 = 31 * i + (this.mName == null?0:this.mName.hashCode());
            i1 = 31 * i1 + (this.mType == null?0:this.mType.toString().hashCode());
            return i1;
        }

        public boolean equals(Object o) {
            if(this == o) {
                return true;
            } else if(o == null) {
                return false;
            } else if(this.getClass() != o.getClass()) {
                return false;
            } else {
                ResFinder.ResItem resItem = (ResFinder.ResItem)o;
                if(this.mName == null) {
                    if(resItem.mName != null) {
                        return false;
                    }
                } else if(!this.mName.equals(resItem.mName)) {
                    return false;
                }

                return this.mType == resItem.mType;
            }
        }
    }
    public enum ResType {
        LAYOUT {
            public String toString() {
                return "layout";
            }
        },
        ID {
            public String toString() {
                return "id";
            }
        },
        DRAWABLE {
            public String toString() {
                return "drawable";
            }
        },
        STYLE {
            public String toString() {
                return "style";
            }
        },
        STYLEABLE {
            public String toString() {
                return "styleable";
            }
        },
        STRING {
            public String toString() {
                return "string";
            }
        },
        COLOR {
            public String toString() {
                return "color";
            }
        },
        DIMEN {
            public String toString() {
                return "dimen";
            }
        },
        RAW {
            public String toString() {
                return "raw";
            }
        },
        ANIM {
            public String toString() {
                return "anim";
            }
        },
        ARRAY {
            public String toString() {
                return "array";
            }
        };

        ResType() {
        }
    }
}
