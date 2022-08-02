# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /opt/adt-bundle/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#####混淆保护基础部分部分代码#####

# 指定代码的压缩级别 0-7
-optimizationpasses 5

# 包明不混合大小写
-dontusemixedcaseclassnames

# 不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

# 优化不优化输入的类文件
-dontoptimize

# 预校验
-dontpreverify

# 混淆时是否记录日志
-verbose

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 忽略警告
-ignorewarning

# 将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile

# 把混淆类中的方法名也混淆了
-useuniqueclassmembernames

# 优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

### 日志输出路径

    # apk 包内所有 class 的内部结构
    -dump class_files.txt
    # 未混淆的类和成员
    -printseeds seeds.txt
    # 列出从 apk 中删除的代码
    -printusage unused.txt
    # 混淆前后的映射
    -printmapping mapping.txt

### Android
    # 保持哪些类不被混淆
    -keep public class * extends android.app.Fragment
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class com.android.vending.licensing.ILicensingService

    # 如果有引用v4包可以添加下面这行
    -keep public class * extends android.support.v4.app.Fragment

    #如果引用了v4或者v7包
    -dontwarn android.support.**
    # Keep the support library
    -keep class android.support.** { *; }
    -keep interface android.support.** { *; }

    # WebView
    #保护WebView对HTML页面的API不被混淆
    -keep class **.Webview2JsInterface { *; }
    #如果你的项目中用到了webview的复杂操作 ，最好加入
    -keepclassmembers class * extends android.webkit.WebViewClient {
         public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
         public boolean *(android.webkit.WebView,java.lang.String);
    }
    #如果你的项目中用到了webview的复杂操作 ，最好加入
    -keepclassmembers class * extends android.webkit.WebChromeClient {
         public void *(android.webkit.WebView,java.lang.String);
    }

    # 自定义view不混淆
    -keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
    }

    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }

    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }

### 其他一般混淆

    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }

    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable

    #保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }

    #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }

    # enum类
      -keepclassmembers enum * {
       public static **[] values();
       public static ** valueOf(java.lang.String);
    }

    #过滤泛型（不写可能会出现类型转换错误，一般情况把这个加上就是了）
    -keepattributes Signature

    # 保护注解
    -keepattributes *Annotation*




#####混淆保护引用的第三方jar包library#####

### Icepick
    -dontwarn icepick.**
    -keep class icepick.** { *; }
    -keep class **$$Icepick { *; }
    -keepclasseswithmembernames class * {
        @icepick.* <fields>;
    }

### Fresco
    # Keep our interfaces so they can be used by other ProGuard rules.
    # See http://sourceforge.net/p/proguard/bugs/466/
    -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

    # Do not strip any method/class that is annotated with @DoNotStrip
    -keep @com.facebook.common.internal.DoNotStrip class *
    -keepclassmembers class * {
        @com.facebook.common.internal.DoNotStrip *;
    }
    # Works around a bug in the animated GIF module which will be fixed in 0.12.0
    -keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
        public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
    }
    # Keep native methods
    -keepclassmembers class * {
        native <methods>;
    }

    -dontwarn okio.**
    -dontwarn com.squareup.okhttp.**
    -dontwarn okhttp3.**
    -dontwarn javax.annotation.**
    -dontwarn com.android.volley.toolbox.**

### Okhttp
    -keepattributes Signature
    -keepattributes *Annotation*
    -keep class com.squareup.okhttp.** { *; }
    -keep interface com.squareup.okhttp.** { *; }
    -dontwarn com.squareup.okhttp.**

### OkHttp 3
    -keepattributes Signature
    -keepattributes *Annotation*
    -keep,includedescriptorclasses class okhttp3.** { *; }
    -keep,includedescriptorclasses interface okhttp3.** { *; }
    -dontwarn okhttp3.**

### Okio
    -keep class sun.misc.Unsafe { *; }
    -dontwarn java.nio.file.*
    -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
    -dontwarn okio.**

### Retrofit 2.X
    # https://square.github.io/retrofit/ ##
    -dontwarn retrofit2.**
    -keep,includedescriptorclasses class retrofit2.** { *; }
    -keepattributes Signature
    -keepattributes Exceptions

    -keepclasseswithmembers class * {
        @retrofit2.http.* <methods>;
    }

### Gson
    #-libraryjars libs/gson-2.2.2.jar
    -keepattributes Signature
    # Gson specific classes
    -keep class sun.misc.Unsafe { *; }
    # Application classes that will be serialized/deserialized over Gson
    -keep class com.google.gson.examples.android.model.** { *; }
    # gson && protobuf
    -dontwarn com.google.**
    -keep class com.google.gson.** {*;}
    -keep class com.google.protobuf.** {*;}

### Butterknife

    -keep class butterknife.** { *; }
    -dontwarn butterknife.internal.**
    -keep class **$$ViewBinder { *; }
    -keepclasseswithmembernames class * {
       @butterknife.* <fields>;
    }
    -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
    }

### RxJava
    -dontwarn sun.misc.**
    -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }


### Jpush
    -dontoptimize
    -dontpreverify

    -dontwarn cn.jpush.**
    -keep class cn.jpush.** { *; }


### Umeng
    -keepclassmembers class * {
        public <init>(org.json.JSONObject);
    }
    -keep public class [com.em.student].R$*{
        public static final int *;
    }
    -keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
    }

### ShareSDK
    -keep class cn.sharesdk.**{*;}
    -keep class com.sina.**{*;}
    -keep class com.mob.**{*;}
    -dontwarn com.mob.**
    -dontwarn cn.sharesdk.**

### GreenDao
    -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
    }
    -keep class **$Properties

    # If you do not use SQLCipher:
    -dontwarn org.greenrobot.greendao.database.**
    # If you do not use Rx:
    -dontwarn rx.**

### Bugtags
    -keepattributes LineNumberTable,SourceFile

    -keep class com.bugtags.library.** {*;}
    -dontwarn org.apache.http.**
    -dontwarn android.net.http.AndroidHttpClient
    -dontwarn com.bugtags.library.**



#####混淆保护自己项目的部分代码#####

### Data类不混淆

    -keepclassmembers class com.naivor.android.app.embedder.repo.remote.responce.**{
        <fields>;
    }