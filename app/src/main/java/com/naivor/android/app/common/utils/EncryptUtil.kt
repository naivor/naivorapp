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
package com.naivor.android.app.common.utils

import android.util.Base64
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.security.Key
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.*
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

/**
 * EncryptUtil 是一个包含几种当前主流加密方式的工具类，主要包括AES，DES，DES3，MD5
 *
 *
 * Created by tianlai on 16-3-2.
 */
object EncryptUtil {
    /***
     * MD5加密 生成32位md5码
     *
     *
     * 散列算法之一（又译哈希算法、摘要算法等），主流编程语言普遍已有MD5的实现。将数据（如一段文字）运算变为另一固
     * 定长度值，是散列算法的基础原理。对MD5算法简要的叙述可以为：MD5以512位分组来处理输入的信息，且每一分组又被划
     * 分为16个32位子分组，经过了一系列的处理后，算法的输出由四个32位分组组成，将这四个32位分组级联后将生成一个128
     * 位散列值。
     *
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     */
    fun md5Encode(inStr: String): String {
        var md5: MessageDigest? = null //消息摘要算法类
        return try {
            md5 =
                MessageDigest.getInstance("MD5") //可以传入一个参数获得实例（参数可以为MD2，MD5，SHA(JDK自带的)，然后也可以用bcprov里面可以带的MD4等）
            val byteArray = inStr.toByteArray(charset("UTF-8"))
            val md5Bytes = md5.digest(byteArray)
            val hexValue = StringBuffer()
            for (i in md5Bytes.indices) {
                val `val` = md5Bytes[i].toInt() and 0xff //转化成为16进制的字节
                if (`val` < 16) {
                    hexValue.append("0")
                }
                hexValue.append(Integer.toHexString(`val`))
            }
            hexValue.toString() //返回的hash
        } catch (e: Exception) {
            println(e.toString())
            e.printStackTrace()
            ""
        }
    }

    /***
     * SHA加密 生成40位SHA码
     *
     *
     * SHA是一种数据加密算法，该算法经过加密专家多年来的发展和改进已日益完善，现在已成为公认的最安全的散列算法之
     * 一，并被广泛使用。该算法的思想是接收一段明文，然后以一种不可逆的方式将它转换成一段（通常更小）密文，也可以
     * 简单的理解为取一串输入码（称为预映射或信息），并把它们转化为长度较短、位数固定的输出序列即散列值（也称为信
     * 息摘要或信息认证代码）的过程。散列函数值可以说是对明文的一种“指纹”或是“摘要”所以对散列值的数字签名就可以视
     * 为对此明文的数字签名。
     *
     * @param inStr 待加密字符串
     * @return 返回40位SHA码
     */
    fun shaEncode(inStr: String): String {
        var sha: MessageDigest? = null
        return try {
            sha = MessageDigest.getInstance("SHA")
            val byteArray = inStr.toByteArray(charset("UTF-8"))
            val md5Bytes = sha.digest(byteArray)
            val hexValue = StringBuffer()
            for (i in md5Bytes.indices) {
                val `val` = md5Bytes[i].toInt() and 0xff
                if (`val` < 16) {
                    hexValue.append("0")
                }
                hexValue.append(Integer.toHexString(`val`))
            }
            hexValue.toString()
        } catch (e: Exception) {
            println(e.toString())
            e.printStackTrace()
            ""
        }
    }

    /**
     * BASE64加密
     *
     *
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
    @Throws(Exception::class)
    fun base64Encode(inStr: String): ByteArray {
        return Base64.encode(inStr.toByteArray(), Base64.DEFAULT)
    }

    /**
     * BASE64解密
     *
     * @param inByte 需要解密内容
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun base64Decode(inByte: ByteArray?): String {
        return String(Base64.decode(inByte, Base64.DEFAULT))
    }

    /**
     * 根据参数生成 KEY
     *
     *
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
    private fun getKey(strKey: String): Key {
        return try {
            val generator = KeyGenerator.getInstance("DES")
            generator.init(SecureRandom(strKey.toByteArray()))
            generator.generateKey()
        } catch (e: Exception) {
            throw RuntimeException(
                "Error initializing SqlMap class. Cause: $e"
            )
        }
    }

    /**
     * 加密以 byte[] 明文输入 ,byte[] 密文输出
     *
     * @param byteS 加密数据
     * @param key   密钥
     * @return
     */
    fun desEncode(byteS: ByteArray?, key: String): ByteArray? {
        var byteFina: ByteArray? = null
        var cipher: Cipher?
        try {
            cipher = Cipher.getInstance("DES")
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key))
            byteFina = cipher.doFinal(byteS)
        } catch (e: Exception) {
            throw RuntimeException(
                "Error initializing SqlMap class. Cause: $e"
            )
        } finally {
            cipher = null
        }
        return byteFina
    }

    /**
     * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
     *
     * @param byteD 需加密数据
     * @param key   密钥
     * @return
     */
    fun desDecode(byteD: ByteArray?, key: String): ByteArray? {
        var cipher: Cipher?
        var byteFina: ByteArray? = null
        try {
            cipher = Cipher.getInstance("DES")
            cipher.init(Cipher.DECRYPT_MODE, getKey(key))
            byteFina = cipher.doFinal(byteD)
        } catch (e: Exception) {
            throw RuntimeException(
                "Error initializing SqlMap class. Cause: $e"
            )
        } finally {
            cipher = null
        }
        return byteFina
    }

    /**
     * 加密 String 明文输入 ,String 密文输出
     *
     * @param strMing 加密数据
     * @param key     密钥
     * @return
     */
    fun desEncode(strMing: String, key: String): String {
        var byteMi: ByteArray? = null
        var byteMing: ByteArray? = null
        var strMi = ""
        try {
            byteMing = strMing.toByteArray(charset("UTF8"))
            byteMi = desEncode(byteMing, key)
            strMi = Base64.encodeToString(byteMi, Base64.DEFAULT)
        } catch (e: Exception) {
            throw RuntimeException(
                "Error initializing SqlMap class. Cause: $e"
            )
        } finally {
            byteMing = null
            byteMi = null
        }
        return strMi
    }

    /**
     * 解密 以 String 密文输入 ,String 明文输出
     *
     * @param strMi 需解密数据
     * @param key   密钥
     * @return
     */
    fun desDecode(strMi: String, key: String): String {
        var byteMing: ByteArray? = null
        var byteMi: ByteArray? = null
        var strMing = ""
        try {
            byteMi = Base64.decode(strMi.toByteArray(), Base64.DEFAULT)
            byteMing = desDecode(byteMi, key)
            strMing = String(byteMing!!, Charset.defaultCharset())
        } catch (e: Exception) {
            throw RuntimeException(
                "Error initializing SqlMap class. Cause: $e"
            )
        } finally {
            byteMing = null
            byteMi = null
        }
        return strMing
    }

    /**
     * 文件 file 进行加密并保存目标文件 destFile 中
     *
     * @param filePath 要加密的文件 如 c:/test/srcFile.txt
     * @param destPath 加密后存放的文件名 如 c:/ 加密后文件 .txt
     * @param key      密钥
     * @throws Exception
     */
    @Throws(Exception::class)
    fun desEncode(filePath: String?, destPath: String?, key: String) {
        val cipher = Cipher.getInstance("DES")
        // cipher.init(Cipher.ENCRYPT_MODE, getKey());
        cipher.init(Cipher.ENCRYPT_MODE, getKey(key))
        val `is`: InputStream = FileInputStream(filePath)
        val out: OutputStream = FileOutputStream(destPath)
        val cis = CipherInputStream(`is`, cipher)
        val buffer = ByteArray(1024)
        var r: Int
        while (cis.read(buffer).also { r = it } > 0) {
            out.write(buffer, 0, r)
        }
        cis.close()
        `is`.close()
        out.close()
    }

    /**
     * 文件采用 DES 算法解密文件
     *
     * @param filePath 已加密的文件 如 c:/ 加密后文件 .txt *
     * @param destPath 解密后存放的文件名 如 c:/ test/ 解密后文件 .txt
     * @param key      密钥
     * @throws Exception
     */
    @Throws(Exception::class)
    fun desDecode(filePath: String?, destPath: String?, key: String) {
        val cipher = Cipher.getInstance("DES")
        cipher.init(Cipher.DECRYPT_MODE, getKey(key))
        val `is`: InputStream = FileInputStream(filePath)
        val out: OutputStream = FileOutputStream(destPath)
        val cos = CipherOutputStream(out, cipher)
        val buffer = ByteArray(1024)
        var r: Int
        while (`is`.read(buffer).also { r = it } >= 0) {
            cos.write(buffer, 0, r)
        }
        cos.close()
        out.close()
        `is`.close()
    }

    /**
     * 3DES CBC加密
     *
     *
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
    @Throws(Exception::class)
    fun des3EncodeCBC(key: ByteArray?, keyiv: ByteArray?, data: ByteArray?): ByteArray {
        val deskey = keyGenerator(String(key!!))
        val cipher = Cipher.getInstance("desede/CBC/NoPadding")
        val ips = IvParameterSpec(keyiv)
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips)
        return cipher.doFinal(data)
    }

    /**
     * 生成密钥key对象
     *
     * @param keyStr
     * @return 密钥对象
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun keyGenerator(keyStr: String): Key {
        val input = HexString2Bytes(keyStr)
        val KeySpec = DESedeKeySpec(input)
        val KeyFactory = SecretKeyFactory.getInstance("desede")
        return KeyFactory.generateSecret(KeySpec as KeySpec) as Key
    }

    private fun parse(c: Char): Int {
        if (c >= 'a') return c - 'a' + 10 and 0x0f
        return if (c >= 'A') c - 'A' + 10 and 0x0f else c - '0' and 0x0f
    }

    // 从十六进制字符串到字节数组转换
    fun HexString2Bytes(hexstr: String): ByteArray {
        val b = ByteArray(hexstr.length / 2)
        var j = 0
        for (i in b.indices) {
            val c0 = hexstr[j++]
            val c1 = hexstr[j++]
            b[i] = (parse(c0) shl 4 or parse(c1)).toByte()
        }
        return b
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
    @Throws(Exception::class)
    fun des3DecodeCBC(key: ByteArray?, keyiv: ByteArray?, data: ByteArray?): ByteArray {
        val deskey = keyGenerator(String(key!!))
        val cipher = Cipher.getInstance("desede/CBC/NoPadding")
        val ips = IvParameterSpec(keyiv)
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips)
        return cipher.doFinal(data)
    }

    /**
     * AES 加密
     *
     *
     * AES 是一个新的可以用于保护电子数据的加密算法。明确地说，AES 是一个迭代的、对称密钥分组的密码，它可以使用
     * 128、192 和 256 位密钥，并且用 128 位（16字节）分组加密和解密数据。与公共密钥密码使用密钥对不同，对称密
     * 钥密码使用相同的密钥加密和解密数据。通过分组密码返回的加密数据 的位数与输入数据相同。迭代加密使用一个循环
     * 结构，在该循环中重复置换（permutations ）和替换(substitutions）输入数据。
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    fun aesEncode(data: String, key: String): ByteArray? {
        try {
            val kgen = KeyGenerator.getInstance("AES")
            kgen.init(128, SecureRandom(key.toByteArray()))
            val secretKey = kgen.generateKey()
            val enCodeFormat = secretKey.encoded
            val mKey = SecretKeySpec(enCodeFormat, "AES")
            val cipher = Cipher.getInstance("AES") // 创建密码器
            val byteContent = data.toByteArray(charset("utf-8"))
            cipher.init(Cipher.ENCRYPT_MODE, mKey) // 初始化
            return cipher.doFinal(byteContent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    fun aesDecode(data: ByteArray?, key: String): ByteArray? {
        try {
            val kgen = KeyGenerator.getInstance("AES")
            kgen.init(128, SecureRandom(key.toByteArray()))
            val secretKey = kgen.generateKey()
            val enCodeFormat = secretKey.encoded
            val mKey = SecretKeySpec(enCodeFormat, "AES")
            val cipher = Cipher.getInstance("AES") // 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, mKey) // 初始化
            return cipher.doFinal(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    fun parseByte2HexStr(buf: ByteArray): String {
        val sb = StringBuffer()
        for (i in buf.indices) {
            var hex = Integer.toHexString((buf[i] and 0xFF.toByte()).toInt())
            if (hex.length == 1) {
                hex = "0$hex"
            }
            sb.append(hex.uppercase(Locale.getDefault()))
        }
        return sb.toString()
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    fun parseHexStr2Byte(hexStr: String): ByteArray? {
        if (hexStr.length < 1) return null
        val result = ByteArray(hexStr.length / 2)
        for (i in 0 until hexStr.length / 2) {
            val high = hexStr.substring(i * 2, i * 2 + 1).toInt(16)
            val low = hexStr.substring(i * 2 + 1, i * 2 + 2).toInt(16)
            result[i] = (high * 16 + low).toByte()
        }
        return result
    }
}