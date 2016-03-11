package com.naivor.app.data.cache.spf;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * SpfManager 用来管理SharePrefrences的类
 * <p>
 * Created by tianlai on 16-3-10.
 */
public class SpfManager {

    /**
     * 保存的类型，通过添加保存类型来增加更多的数据类型
     */
    public static enum Type {
        LOGIN("login_data");

        private String mValue;

        Type(String value) {
            this.mValue = value;
        }
    }

    private Context context;

    //    保存的模式
    //    Context.MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，如果想把
    //                          新写入的内容追加到原文件中。可以使用Context.MODE_APPEND
    //    Context.MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
    //    Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件。
    //    MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
    private int saveMode;

    private SharedPreferences spf;
    private SharedPreferences.Editor editor;

    private Type type ;

    private boolean isChanged = false;

    public SpfManager(Context context) {

        this(context, Context.MODE_PRIVATE);
    }

    public SpfManager(Context context, int saveMode) {
        this(context,saveMode,Type.LOGIN);
    }

    public SpfManager(Context context, int saveMode, Type dataType) {
        this.context = context;
        this.saveMode = saveMode;
        this.type = dataType;

        initSpf();

    }

    public String getString(String name) {
        if (isChanged || spf == null) {
            initSpf();
        }

        return spf.getString(name, null);
    }

    public float getFloat(String name) {
        if (isChanged || spf == null) {
            initSpf();
        }

        return spf.getFloat(name, 0.0f);
    }

    public int getInt(String name) {
        if (isChanged || spf == null) {
            initSpf();
        }

        return spf.getInt(name, 0);
    }

    public long getLong(String name) {
        if (isChanged || spf == null) {
            initSpf();
        }

        return spf.getLong(name, 0);
    }

    public Set<String> getStringSet(String name) {
        if (isChanged || spf == null) {
            initSpf();
        }

        return spf.getStringSet(name, null);
    }

    public void saveString(String name, String value) {
        if (isChanged || spf == null) {
            initSpf();
        }

        editor.putString(name, value);
        editor.apply();
    }

    public void saveFloat(String name, float value) {
        if (isChanged || spf == null) {
            initSpf();
        }

        editor.putFloat(name, value);
        editor.apply();
    }

    public void saveInt(String name, int value) {
        if (isChanged || spf == null) {
            initSpf();
        }

        editor.putInt(name, value);
        editor.apply();
    }

    public void saveLong(String name, long value) {
        if (isChanged || spf == null) {
            initSpf();
        }

        editor.putLong(name, value);
        editor.apply();
    }

    public void saveStringSet(String name, Set<String> value) {
        if (isChanged || spf == null) {
            initSpf();
        }

        editor.putStringSet(name, value);
        editor.apply();
    }


    /**
     * 设置保存的模式
     *
     * @param saveMode
     */
    public void setSaveMode(int saveMode) {
        this.saveMode = saveMode;

        //设置了新的模式，需要改变
        isChanged = true;
    }

    /**
     * 设置保存的数据类型（用途）
     *
     * @param type
     */
    public void setType(Type type) {
        this.type = type;

        //设置了新的类型，需要改变
        isChanged = true;
    }

    /**
     * 初始化SharePrefrences
     */
    private void initSpf() {
        if (context == null) throw new NullPointerException("context 不能为Null");

        spf = context.getSharedPreferences(type.mValue, saveMode);
        editor = spf.edit();
    }
}
