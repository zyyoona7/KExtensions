package com.zyyoona7.lib

import com.zyyoona7.lib.deriator.InsecureSHA1PRNGKeyDerivator
import java.io.File
import java.io.FileInputStream
import java.security.DigestInputStream
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * Created by zyyoona7 on 2017/9/11.
 *
 * 加密/解密相关的扩展函数
 * （相关文章https://zhuanlan.zhihu.com/p/24255780）
 */


/**
 * ByteArray转换成16进制字符串
 * https://stackoverflow.com/a/21178195/8546297
 */
fun ByteArray.toHex(): String {
    val result = StringBuilder()
    forEach {
        result.append(Character.forDigit((it.toInt() shr 4) and 0xF, 16))
        result.append(Character.forDigit(it.toInt() and 0xF, 16))
    }
    return result.toString().toUpperCase()
}

/**
 * 16进制字符串转换成ByteArray
 *
 */
fun String.hexToByteArray(): ByteArray {
    val data = ByteArray(length / 2)
    for (i in 0 until length step 2)
        data[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
    return data
}
/*
  在密码学中，hash算法（散列函数）的作用主要是用于消息摘要和签名，换句话说，它主要用于对整个消息的完整性进行校验。
 */
/*
  MD5：https://zh.wikipedia.org/wiki/MD5
  MD5消息摘要算法（英语：MD5 Message-Digest Algorithm），一种被广泛使用的密码散列函数，
  可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致。

  1996年后被证实存在弱点，可以被加以破解，对于需要高度安全性的数据，专家一般建议改用其他算法，如SHA-1。
  2004年，证实MD5算法无法防止碰撞（collision），因此无法适用于安全性认证，如SSL公开密钥认证或是数字签名等用途。
 */

/**
 * hash 函数模板
 *
 * @param algorithmType
 * @param data
 */
private fun hashFunc(algorithmType: String, data: String): String {
    return try {
        val md = MessageDigest.getInstance(algorithmType)
        md.digest(data.toByteArray()).toHex()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        ""
    }
}

private fun hashFuncForFile(type: String, inputStream: FileInputStream): String {
    return try {
        val md = MessageDigest.getInstance(type)
        val input = DigestInputStream(inputStream, md)
        val buffer = ByteArray(8192)
        input.use {
            while (input.read(buffer) != -1);
        }
        md.digest().toHex()
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

/**
 * md5加密
 *
 * @param salt 盐值
 */
fun String.md5(salt: String = ""): String = hashFunc("MD5", this + salt)

/**
 * 文件MD5加密
 *
 */
fun File.md5(): String = hashFuncForFile("MD5", inputStream())

/*
  SHA家族：https://zh.wikipedia.org/wiki/SHA%E5%AE%B6%E6%97%8F
  安全散列算法（英语：Secure Hash Algorithm，缩写为SHA）是一个密码散列函数家族，是FIPS所认证的安全散列算法。
  能计算出一个数字消息所对应到的，长度固定的字符串（又称消息摘要）的算法。且若输入的消息不同，它们对应到不同字符串的概率很高。
 */

/**
 * SHA1加密
 */
fun String.sha1(): String = hashFunc("SHA1", this)

/**
 * SHA224加密
 */
fun String.sha224(): String = hashFunc("SHA224", this)

/**
 * SHA256加密
 */
fun String.sha256(): String = hashFunc("SHA256", this)

/**
 * SHA384加密
 */
fun String.sha384(): String = hashFunc("SHA384", this)

/**
 * SHA512加密
 */
fun String.sha512(): String = hashFunc("SHA512", this)

/*
  HMAC：https://zh.wikipedia.org/wiki/%E9%87%91%E9%91%B0%E9%9B%9C%E6%B9%8A%E8%A8%8A%E6%81%AF%E9%91%91%E5%88%A5%E7%A2%BC
  密钥散列消息认证码（英语：Keyed-hash message authentication code，缩写为HMAC），
  又称散列消息认证码（Hash-based message authentication code），是一种通过特别计算方式之后产生的消息认证码（MAC），
  使用密码散列函数，同时结合一个加密密钥。它可以用来保证数据的完整性，同时可以用来作某个消息的身份验证。
 */

/**
 * HMAC 模板函数
 *
 * @param data 加密数据
 * @param algorithmType
 * @param key 密钥
 */
private fun hmacFunc(data: String, algorithmType: String, key: String): String {
    return try {
        val mac = Mac.getInstance(algorithmType)
        val secretKey = SecretKeySpec(key.toByteArray(), mac.algorithm)
        mac.init(secretKey)
        mac.doFinal(data.toByteArray()).toHex()
    } catch (e: InvalidKeyException) {
        e.printStackTrace()
        ""
    }
}

/**
 * 获取HMAC-MD5 加密
 *
 * @param key
 */
fun String.hmacMD5(key: String): String = hmacFunc(this, "HmacMD5", key)

/**
 * 获取HMAC-SHA1 加密
 *
 * @param key
 */
fun String.hmacSHA1(key: String): String = hmacFunc(this, "HmacSHA1", key)

/**
 * 获取HMAC-SHA224 加密
 *
 * @param key
 */
fun String.hmacSHA224(key: String): String = hmacFunc(this, "HmacSHA224", key)

/**
 * 获取HMAC-SHA256 加密
 *
 * @param key
 */
fun String.hmacSHA256(key: String): String = hmacFunc(this, "HmacSHA256", key)

/**
 * 获取HMAC-SHA384 加密
 *
 * @param key
 */
fun String.hmacSHA384(key: String): String = hmacFunc(this, "HmacSHA384", key)

/**
 * 获取HMAC-SHA512 加密
 *
 * @param key
 */
fun String.hmacSHA512(key: String): String = hmacFunc(this, "HmacSHA512", key)

/*
  对称加密算法：https://zh.wikipedia.org/wiki/%E5%B0%8D%E7%A8%B1%E5%AF%86%E9%91%B0%E5%8A%A0%E5%AF%86
  这类算法在加密和解密时使用相同的密钥，或是使用两个可以简单地相互推算的密钥。

  常见的对称加密算法有DES、3DES、AES、Blowfish、IDEA、RC5、RC6。
 */

/*
  ---------- AES 加密 兼容7.0以上 （https://android-developers.googleblog.com/2016/06/security-crypto-provider-deprecated-in.html）----------
 */
/**
 * 生成SecretKey
 *
 * @param password  密钥
 * @param keySizeInBytes 密钥长度 需要*8  取值只能为 16 24 32 对应密钥长度为128、192、256
 */
private fun deriveKeyInsecurely(password: String, keySizeInBytes: Int): SecretKey =
        SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(password.toByteArray(), keySizeInBytes), "AES")

/**
 * 获取AES加密后的ByteArray
 *
 * @param raw key的ByteArray
 * @param clear 明文的ByteArray
 * @param isEncrypt 是否是加密模式
 */
private fun encryptOrDecryptAES(raw: ByteArray, clear: ByteArray, transformation: String, isEncrypt: Boolean): ByteArray =
        try {
            val secretKeySpec = SecretKeySpec(raw, "AES")
            val cipher = Cipher.getInstance(transformation)
            cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(kotlin.ByteArray(cipher.blockSize)))
            cipher.doFinal(clear)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyArray<Byte>().toByteArray()
        }

/**
 * AES加密成16进制字符串
 *
 * @param password  密钥
 * @param keySizeInBytes 密钥长度 需要*8  取值只能为 16 24 32 对应密钥长度为128、192、256
 * @param transformation 加密算法参数配置
 */
fun String.encryptAES(password: String, keySizeInBytes: Int = 32, transformation: String = "AES/CBC/PKCS5Padding"): String {
    val rawKey = deriveKeyInsecurely(password, keySizeInBytes).encoded
    val result = encryptOrDecryptAES(rawKey, this.toByteArray(), transformation, true)
    return result.toHex()
}

/**
 * AES加密成Base64字符串
 *
 * @param password  密钥
 * @param keySizeInBytes 密钥长度 需要*8  取值只能为 16 24 32 对应密钥长度为128、192、256
 * @param transformation 加密算法参数配置
 */
fun String.encryptAES2Base64(password: String, keySizeInBytes: Int = 32, transformation: String = "AES/CBC/PKCS5Padding"): String {
    val rawKey = deriveKeyInsecurely(password, keySizeInBytes).encoded
    val result = encryptOrDecryptAES(rawKey, this.toByteArray(), transformation, true)
    return result.base64Encode()
}

/**
 * 16进制字符串用AES解密
 *
 * @param password  密钥
 * @param keySizeInBytes 密钥长度 需要*8  取值只能为 16 24 32 对应密钥长度为128、192、256
 * @param transformation 加密算法参数配置
 */
fun String.decryptAES(password: String, keySizeInBytes: Int = 32, transformation: String = "AES/CBC/PKCS5Padding"): String {
    val rawKey = deriveKeyInsecurely(password, keySizeInBytes).encoded
    val result = encryptOrDecryptAES(rawKey, this.hexToByteArray(), transformation, false)
    return String(result)
}

/**
 * Base64字符串用AES解密
 *
 * @param password  密钥
 * @param keySizeInBytes 密钥长度 需要*8  取值只能为 16 24 32 对应密钥长度为128、192、256
 * @param transformation 加密算法参数配置
 */
fun String.decryptBase64AES(password: String, keySizeInBytes: Int = 32, transformation: String = "AES/CBC/PKCS5Padding"): String {
    val rawKey = deriveKeyInsecurely(password, keySizeInBytes).encoded
    val result = encryptOrDecryptAES(rawKey, this.base64Decode().toByteArray(), transformation, false)
    return String(result)
}

/*
  非对称加密算法：https://zh.wikipedia.org/wiki/%E5%85%AC%E5%BC%80%E5%AF%86%E9%92%A5%E5%8A%A0%E5%AF%86
  它需要两个密钥，一个是公开密钥，另一个是私有密钥；一个用作加密的时候，另一个则用作解密。
  使用其中一个密钥把明文加密后所得的密文，只能用相对应的另一个密钥才能解密得到原本的明文；
  甚至连最初用来加密的密钥也不能用作解密。由于加密和解密需要两个不同的密钥，故被称为非对称加密.

  常见的公钥加密算法有：RSA、ElGamal、背包算法、Rabin（RSA的特例）、迪菲－赫尔曼密钥交换协议中的公钥加密算法、椭圆曲线加密算法（英语：Elliptic Curve Cryptography, ECC）。
 */