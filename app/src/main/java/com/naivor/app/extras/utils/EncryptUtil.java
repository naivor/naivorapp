/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.extras.utils;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * EncryptUtil 是一个包含几种当前主流加密方式的工具类，主要包括AES，DES，DES3，MD5
 * <p>
 * Created by tianlai on 16-3-2.
 */
public class EncryptUtil {
    /***
     * MD5加密 生成32位md5码
     * <p>
     * 散列算法之一（又译哈希算法、摘要算法等），主流编程语言普遍已有MD5的实现。将数据（如一段文字）运算变为另一固
     * 定长度值，是散列算法的基础原理。对MD5算法简要的叙述可以为：MD5以512位分组来处理输入的信息，且每一分组又被划
     * 分为16个32位子分组，经过了一系列的处理后，算法的输出由四个32位分组组成，将这四个32位分组级联后将生成一个128
     * 位散列值。
     *
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr){
        MessageDigest md5 = null;//消息摘要算法类
        try {
            md5 = MessageDigest.getInstance("MD5");//可以传入一个参数获得实例（参数可以为MD2，MD5，SHA(JDK自带的)，然后也可以用bcprov里面可以带的MD4等）

            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);

            StringBuffer hexValue = new StringBuffer();

            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;//转化成为16进制的字节
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();//返回的hash
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

    }

    /***
     * SHA加密 生成40位SHA码
     * <p>
     * SHA是一种数据加密算法，该算法经过加密专家多年来的发展和改进已日益完善，现在已成为公认的最安全的散列算法之
     * 一，并被广泛使用。该算法的思想是接收一段明文，然后以一种不可逆的方式将它转换成一段（通常更小）密文，也可以
     * 简单的理解为取一串输入码（称为预映射或信息），并把它们转化为长度较短、位数固定的输出序列即散列值（也称为信
     * 息摘要或信息认证代码）的过程。散列函数值可以说是对明文的一种“指纹”或是“摘要”所以对散列值的数字签名就可以视
     * 为对此明文的数字签名。
     *
     * @param inStr 待加密字符串
     * @return 返回40位SHA码
     */
    public static String shaEncode(String inStr)  {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");

            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = sha.digest(byteArray);

            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

    }


    /**
     * BASE64加密
     * <p>
     * Base64是网络上最常见的用于传输8Bit字节代码的编码方式之一，大家可以查看RFC2045～RFC2049，上面有MIME的
     * 详细规范。Base64编码可用于在HTTP环境下传递较长的标识信息，例如，在Java Persistence系统Hibernate中，
     * 就采用了Base64来将一个较长的唯一标识符（一般为128-bit的UUID）编码为一个字符串，用作HTTP表单和HTTP GET
     * URL中的参数。在其他应用程序中，也常常需要把二进制数据编码为适合放在URL（包括隐藏表单域）中的形式。此时，
     * 采用Base64编码具有不可读性，即所编码的数据不会被人用肉眼所直接看到。
     *
     * @param inStr 需要加密内容
     * @return
     * @throws Exception
     */
    public static byte[] base64Encode(String inStr) throws Exception {

        return Base64.encode(inStr.getBytes(), Base64.DEFAULT);
    }

    /**
     * BASE64解密
     *
     * @param inByte 需要解密内容
     * @return
     * @throws Exception
     */
    public static String base64Decode(byte[] inByte) throws Exception {

        return new String(Base64.decode(inByte, Base64.DEFAULT));
    }


    /**
     * 根据参数生成 KEY
     * <p>
     * DES算法把64位的明文输入块变为64位的密文输出块，它所使用的密钥也是64位，首先，DES把输入的64位数据块按位重新
     * 组合，并把输出分为L0、R0两部分，每部分各长32位，并进行前后置换（输入的第58位换到第一位，第50位换到第2位，依
     * 此类推，最后一位是原来的第7位），最终由L0输出左32位，R0输出右32位，根据这个法则经过16次迭代运算后，得到L16
     * 、R16，将此作为输入，进行与初始置换相反的逆置换，即得到密文输出。DES算法的入口参数有三个：Key、Data、Mode。
     * 其中Key为8个字节共64位，是DES算法的工作密钥；Data也为8个字节64位，是要被加密或被解密的数据；Mode为DES的工
     * 作方式，有两种：加密或解密，如果Mode为加密，则用Key去把数据Data进行加密，生成Data的密码形式作为DES的输出结
     * 果；如Mode为解密，则用Key去把密码形式的数据Data解密，还原为Data的明码形式作为DES的输出结果。在使用DES时，
     * 双方预先约定使用的”密码”即Key，然后用Key去加密数据；接收方得到密文后使用同样的Key解密得到原数据，这样便实现
     * 了安全性较高的数据传输。
     *
     * @param strKey key的值
     * @return
     */
    private static Key getKey(String strKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(strKey.getBytes()));

            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }

    }

    /**
     * 加密以 byte[] 明文输入 ,byte[] 密文输出
     *
     * @param byteS 加密数据
     * @param key   密钥
     * @return
     */
    public static byte[] desEncode(byte[] byteS, String key) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
     *
     * @param byteD 需加密数据
     * @param key   密钥
     * @return
     */
    public static byte[] desDecode(byte[] byteD, String key) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, getKey(key));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 加密 String 明文输入 ,String 密文输出
     *
     * @param strMing 加密数据
     * @param key     密钥
     * @return
     */
    public static String desEncode(String strMing, String key) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";

        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = desEncode(byteMing, key);
            strMi = Base64.encodeToString(byteMi, Base64.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以 String 密文输入 ,String 明文输出
     *
     * @param strMi 需解密数据
     * @param key   密钥
     * @return
     */
    public static String desDecode(String strMi, String key) {
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = Base64.decode(strMi.getBytes(), Base64.DEFAULT);
            byteMing = desDecode(byteMi, key);
            strMing = new String(byteMing, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }


    /**
     * 文件 file 进行加密并保存目标文件 destFile 中
     *
     * @param filePath 要加密的文件 如 c:/test/srcFile.txt
     * @param destPath 加密后存放的文件名 如 c:/ 加密后文件 .txt
     * @param key      密钥
     * @throws Exception
     */
    public static void desEncode(String filePath, String destPath, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        // cipher.init(Cipher.ENCRYPT_MODE, getKey());
        cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
        InputStream is = new FileInputStream(filePath);
        OutputStream out = new FileOutputStream(destPath);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    /**
     * 文件采用 DES 算法解密文件
     *
     * @param filePath 已加密的文件 如 c:/ 加密后文件 .txt *
     * @param destPath 解密后存放的文件名 如 c:/ test/ 解密后文件 .txt
     * @param key      密钥
     * @throws Exception
     */
    public static void desDecode(String filePath, String destPath, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, getKey(key));
        InputStream is = new FileInputStream(filePath);
        OutputStream out = new FileOutputStream(destPath);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }


    /**
     * 3DES CBC加密
     * <p>
     * 3DES又称Triple DES，是DES加密算法的一种模式，它使用3条56位的密钥对数据进行三次加密。数据加密标准（DES）
     * 是美国的一种由来已久的加密标准，它使用对称密钥加密法，并于1981年被ANSI组织规范为ANSI X.3.92。DES使用56
     * 位密钥和密码块的方法，而在密码块的方法中，文本被分成64位大小的文本块然后再进行加密。比起最初的DES，3DES更
     * 为安全。
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        return cipher.doFinal(data);
    }


    /**
     * 生成密钥key对象
     *
     * @param keyStr
     * @return 密钥对象
     * @throws Exception
     */
    private static Key keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESedeKeySpec KeySpec = new DESedeKeySpec(input);
        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance("desede");
        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
    }


    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }


    /**
     * 3DES CBC解密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        return cipher.doFinal(data);
    }


    /**
     * AES 加密
     * <p>
     * AES 是一个新的可以用于保护电子数据的加密算法。明确地说，AES 是一个迭代的、对称密钥分组的密码，它可以使用
     * 128、192 和 256 位密钥，并且用 128 位（16字节）分组加密和解密数据。与公共密钥密码使用密钥对不同，对称密
     * 钥密码使用相同的密钥加密和解密数据。通过分组密码返回的加密数据 的位数与输入数据相同。迭代加密使用一个循环
     * 结构，在该循环中重复置换（permutations ）和替换(substitutions）输入数据。
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static byte[] aesEncode(String data, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec mKey = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = data.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, mKey);// 初始化

            return cipher.doFinal(byteContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static byte[] aesDecode(byte[] data, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec mKey = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, mKey);// 初始化

            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}