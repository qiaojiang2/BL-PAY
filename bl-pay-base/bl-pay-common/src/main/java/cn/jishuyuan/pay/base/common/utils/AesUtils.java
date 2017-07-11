package cn.jishuyuan.pay.base.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description AES/CBC模式加密工具类
 * @encoding UTF-8
 * @date 2017/6/29
 * @time 17:16
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class AesUtils {
    //16byte初始向量
    private static final byte[] IV = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    //加密模式常量定义
    public static final String AES_ECB_NOPadding = "AES/ECB/NOPadding";
    public static final String AES_ECB_PKCS5Padding = "AES/ECB/PKCS5Padding";
    public static final String AES_CBC_NOPadding = "AES/CBC/NOPadding";
    public static final String AES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * AES/CBC/PKCS5Padding
     *
     * @param transformation
     * @param hexData(十六进制)
     * @param hexKey(十六进制)
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String transformation, String hexData, String hexKey) throws UnsupportedEncodingException {
        byte[] data = DataConvert.hex2byte(hexData);
        byte[] k = DataConvert.hex2byte(hexKey);

        byte[] resp = encrypt(transformation, data, k);
        return DataConvert.ByteArraytoHexString(resp);
    }

    /**
     * AES/CBC/PKCS5Padding
     *
     * @param srcData 明文字符串
     * @param hexKey  十六进制
     * @param charset 编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String transformation, String srcData, String hexKey, String charset) throws UnsupportedEncodingException {
        byte[] data = srcData.getBytes(charset == null ? CHARSET_UTF8 : charset);
        byte[] k = DataConvert.hex2byte(hexKey);

        byte[] resp = encrypt(transformation, data, k);
        return DataConvert.ByteArraytoHexString(resp);
    }

    /**
     * AES/CBC/PKCS5Padding
     *
     * @param src
     * @param key
     * @return
     */
    public static byte[] encrypt(String transformation, byte[] src, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            if (AES_ECB_NOPadding.equals(transformation) || AES_ECB_PKCS5Padding.equals(transformation)) {
                cipher.init(ENCRYPT_MODE, makeKey(key));
            } else {
                cipher.init(ENCRYPT_MODE, makeKey(key), makeIv());
            }
            return cipher.doFinal(src);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES/CBC/PKCS5Padding
     *
     * @param hexData 十六进制
     * @param hexKey  十六进制
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decrypt(String transformation, String hexData, String hexKey) throws UnsupportedEncodingException {
        byte[] data = DataConvert.hex2byte(hexData);
        byte[] k = DataConvert.hex2byte(hexKey);
        byte[] resp = decrypt(transformation, data, k);
        return new String(resp, CHARSET_UTF8);
    }

    /**
     * AES/CBC/PKCS5Padding
     *
     * @param eData
     * @param key
     * @return
     */
    public static byte[] decrypt(String transformation, byte[] eData, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            if (AES_ECB_NOPadding.equals(transformation) || AES_ECB_PKCS5Padding.equals(transformation)) {
                cipher.init(Cipher.DECRYPT_MODE, makeKey(key));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, makeKey(key), makeIv());
            }
            return cipher.doFinal(eData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MAC算法：采用AES-CBC算法，初始向量全0。如果最后一个数据块长度为16字节，则在此数据块后附加16字节’0x80 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00’。如果最后一个数据块长度小于16字节，则该数据块的最后填补’0x80’，如果填补之后的数据块长度仍小于16字节，则在数据块后填充’0x00’，至数据块长度为16字节。MAC值为最后一块密文数据的前4字节
     *
     * @param hexData
     * @param key
     * @return
     */
    public static byte[] encryptMac(byte[] hexData, byte[] key) {
        System.out.println("src:" + DataConvert.ByteArraytoHexString(hexData));
        byte[] mac = new byte[4];
        int maxLen = hexData.length + (16 - (hexData.length % 16));
        ByteBuffer buff = ByteBuffer.allocate(maxLen);
        buff.put(hexData);
        buff.put((byte) 0x80);
        System.out.println("padding:" + DataConvert.ByteArraytoHexString(buff.array()));
        byte[] eData = encrypt(AES_CBC_NOPadding, buff.array(), key);

        buff = ByteBuffer.wrap(eData);
        buff.position(buff.capacity() - 16);
        buff.get(mac);

        return mac;
    }

    /**
     * 计算MAC
     *
     * @param data
     * @param hexKey
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encryptMac(String data, String hexKey) throws UnsupportedEncodingException {
        byte[] srcData = data.getBytes(CHARSET_UTF8);
        byte[] key = DataConvert.hex2byte(hexKey);

        return DataConvert.ByteArraytoHexString(encryptMac(srcData, key));
    }

    static AlgorithmParameterSpec makeIv() {
        IvParameterSpec ips = new IvParameterSpec(IV);
        return ips;
    }

    static Key makeKey(byte[] encodedKey) {
        SecretKey aesKey = new SecretKeySpec(encodedKey, "AES");
        return aesKey;
    }

    /*public static void main(String[] args) throws UnsupportedEncodingException {
        //秘钥长度为24byte
        String ENCRYPTION_KEY = "d4c8986cf3f7910a3bf0e7cc3036366a";
        String srcJson = "{\"biz_type\": \"cycling\",\"trade_type\": \"id_verify\",\"product_type\": \"id_card\",\"id_name\":\"姜桥\",\"id_number\": \"42102319890305291X\",\"card_number\": \"6225880139934809\",\"phone_number\": \"18610380635\"}";
        System.out.println("src: " + srcJson);
        String encrypted = AesUtils.encrypt(AES_CBC_PKCS5Padding, srcJson, ENCRYPTION_KEY, CHARSET_UTF8);
        System.out.println("encrypted: " + encrypted);
        String decrypted = AesUtils.decrypt(AES_CBC_PKCS5Padding, encrypted, ENCRYPTION_KEY);
        System.out.println("decrypted: " + decrypted);
    }*/
}
