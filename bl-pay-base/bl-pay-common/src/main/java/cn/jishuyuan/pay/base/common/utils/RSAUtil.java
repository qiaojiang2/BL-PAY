package cn.jishuyuan.pay.base.common.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description RSA算法签名/验签工具类
 * @encoding UTF-8
 * @date 2017/6/30
 * @time 10:54
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class RSAUtil {
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final String _CHARSET_NAME = "UTF-8";

    /**
     * 解密最大长度
     **/
    private static final int DECRPT_MAX_LEN = 128;
    /**
     * 加密最大长度
     **/
    private static final int ENCRPT_MAX_LEN = 117;

    /**
     * 加密类型
     */
    public interface Transformation {
        public static final String ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding";
        public static final String ECB_NOPADDING = "RSA/ECB/NoPadding";// ios
    }

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @return 签名值
     * @throws Exception
     */
    public static String sign(String content, String privateKey) throws Exception {
        PrivateKey priKey = getPrivateKey(privateKey);
        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(content.getBytes(_CHARSET_NAME));
        byte[] signed = signature.sign();
        return Base64.encode(signed);
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param pubKeyStr 支付宝公钥
     * @return 布尔值
     * @throws Exception
     */
    public static boolean verifySign(String content, String sign, String pubKeyStr) throws Exception {
        PublicKey pubKey = getPublicKey(pubKeyStr);
        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes(_CHARSET_NAME));
        boolean bverify = signature.verify(Base64.decode(sign));
        return bverify;
    }

    /**
     * 私钥解密
     *
     * @param base64Content BASE64加密后的字符串 密文
     * @param privateKeyStr 商户私钥
     * @return 解密后的字符串
     */
    public static String privateDecrypt(String base64Content, String privateKeyStr) throws Exception {
        PrivateKey priKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] b = Base64.decode(base64Content);
        ByteArrayOutputStream out = doFinal(cipher, b, DECRPT_MAX_LEN);
        return new String(out.toByteArray(), _CHARSET_NAME);
    }

    /**
     * 公钥解密
     *
     * @param publicKeyStr 商户私钥
     * @return 解密后的字符串
     */
    public static String publicDecrypt(String base64Content, String publicKeyStr) throws Exception {
        PublicKey pubKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        ByteArrayOutputStream out = doFinal(cipher, Base64.decode(base64Content), DECRPT_MAX_LEN);
        return new String(out.toByteArray(), _CHARSET_NAME);
    }

    /**
     * 公钥解密
     *
     * @param base64Content BASE64加密后的字符串 密文
     * @param publicKeyStr  商户私钥
     * @return 解密后的字符串
     */
    public static String publicDecrypt(String base64Content, String publicKeyStr, String transformation) throws Exception {
        PublicKey pubKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        ByteArrayOutputStream out = doFinal(cipher, Base64.decode(base64Content), DECRPT_MAX_LEN);
        return new String(out.toByteArray(), _CHARSET_NAME);
    }

    /**
     * 公钥加密
     *
     * @param content      密文
     * @param publicKeyStr 商户私钥
     * @return 解密后的字符串
     */
    public static byte[] publicEncrypt(String content, String publicKeyStr) throws Exception {
        PublicKey pubKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        ByteArrayOutputStream out = doFinal(cipher, content.getBytes(_CHARSET_NAME), ENCRPT_MAX_LEN);
        return out.toByteArray();
    }

    /**
     * 私钥加密
     *
     * @param content       密文
     * @param privateKeyStr 商户私钥
     * @return 解密后的字符串
     */
    public static byte[] privateEncrypt(String content, String privateKeyStr) throws Exception {
        PrivateKey priKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        ByteArrayOutputStream out = doFinal(cipher, content.getBytes(_CHARSET_NAME), ENCRPT_MAX_LEN);
        return out.toByteArray();
    }

    /**
     * 私钥加密
     *
     * @param content        密文
     * @param privateKeyStr  商户私钥
     * @param transformation 加密类型
     * @return 解密后的字符串
     */
    public static byte[] privateEncrypt(String content, String privateKeyStr, String transformation) throws Exception {
        PrivateKey priKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        ByteArrayOutputStream out = doFinal(cipher, content.getBytes(_CHARSET_NAME), ENCRPT_MAX_LEN);
        return out.toByteArray();
    }

    /**
     * 加密解密处理
     * </br>加解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
     *
     * @param cipher
     * @param content
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    private static ByteArrayOutputStream doFinal(Cipher cipher, byte[] content, int maxLen) throws IllegalBlockSizeException,
            BadPaddingException, IOException {
        InputStream ins = new ByteArrayInputStream(content);
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buf = new byte[maxLen];
        int bufl;
        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;
            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                System.arraycopy(buf, 0, block, 0, bufl);
            }
            byte[] eData = cipher.doFinal(block);
            writer.write(eData);
        }
        return writer;
    }

    /**
     * 得到私钥
     *
     * @param priKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String priKey) throws Exception {
        byte[] priKeyBytes = Base64.decode(priKey);
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priKeySpec);
    }

    /**
     * 得到公钥
     *
     * @param pubKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PublicKey getPublicKey(String pubKey) throws Exception {
        byte[] pubkeyBytes = Base64.decode(pubKey);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubkeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(pubKeySpec);
    }

    /**
     * 私钥签名
     *
     * @param content    原文
     * @param privateKey Base64私钥
     * @return HexString
     * @throws Exception
     */
    public static String privateSignToHexString(String content, String privateKey) throws Exception {
        byte[] srcData = SHA1.getDigestOfBytes(content.getBytes(_CHARSET_NAME));
        System.out.println("SHA1:" + DataConvert.ByteArraytoHexString(srcData));
        byte[] eData = privateEncrypt(srcData, privateKey);
        return DataConvert.ByteArraytoHexString(eData);
    }

    /**
     * 公钥验签(使用自动验签流程)
     *
     * @param content   原文
     * @param hexSign   16进制签名值
     * @param publicKey Base64公钥
     * @return boolean
     * @throws Exception
     */
    public static boolean publicVerifySign(String content, String hexSign, String publicKey) throws Exception {
        PublicKey pubKey = getPublicKey(publicKey);
        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes(_CHARSET_NAME));
        boolean bverify = signature.verify(DataConvert.hex2byte(hexSign));

        return bverify;
    }

    /**
     * 公钥验签(解拆验证签名流程 与 自动验签结果一致)
     *
     * @param content   原文
     * @param hexSign   16进制签名值
     * @param publicKey Base64公钥
     * @return boolean
     * @throws Exception
     */
    public static boolean publicDecryptVerifySign(String content, String hexSign, String publicKey) throws Exception {
        String sha1Data = SHA1.getDigestOfString(content.getBytes(_CHARSET_NAME));
        System.out.print("rsa.sha1Data:[" + sha1Data + "]");
        byte[] dData = publicDecrypt(DataConvert.hex2byte(hexSign), publicKey);
        String sign = DataConvert.ByteArraytoHexString(dData);
        System.out.print("rsa.sign:[" + sign + "]");
        if (sign.length() > sha1Data.length()) {
            sign = sign.substring(sign.length() - sha1Data.length());
            System.out.print("rsa.subSign:[" + sign + "]");
        }
        return sha1Data.equalsIgnoreCase(sign);
    }

    /**
     * 公钥签名
     *
     * @param content   原文
     * @param publicKey Base64公钥
     * @return HexString
     * @throws Exception
     */
    public static String publicSignToHexString(String content, String publicKey) throws Exception {
        byte[] srcData = SHA1.getDigestOfBytes(content.getBytes(_CHARSET_NAME));
        System.out.println("SHA1:" + DataConvert.ByteArraytoHexString(srcData));
        byte[] eData = publicEncrypt(srcData, publicKey);
        return DataConvert.ByteArraytoHexString(eData);
    }

    /**
     * 私钥验证签名
     *
     * @param content    原文
     * @param hexSign    16进制签名值
     * @param privateKey Base64私钥
     * @return boolean
     * @throws Exception
     */
    public static boolean privateVerifySign(String content, String hexSign, String privateKey) throws Exception {
        System.out.print("rsa.linkStr:[" + content + "]");
        String sha1Data = SHA1.getDigestOfString(content.getBytes(_CHARSET_NAME));
        System.out.print("rsa.sha1Data:[" + sha1Data + "]");
        byte[] dData = privateDecrypt(DataConvert.hex2byte(hexSign), privateKey);
        String sign = DataConvert.ByteArraytoHexString(dData);
        System.out.print("rsa.sign:[" + sign + "]");
        if (sign.length() > sha1Data.length()) {
            sign = sign.substring(sign.length() - sha1Data.length());
            System.out.print("rsa.subSign:[" + sign + "]");
        }
        return sha1Data.equalsIgnoreCase(sign);
    }

    public static byte[] privateDecrypt(byte[] content, String privateKey) throws Exception {
        PrivateKey priKey = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        ByteArrayOutputStream out = doFinal(cipher, content, DECRPT_MAX_LEN);
        return out.toByteArray();
    }

    public static byte[] privateEncrypt(byte[] content, String privateKey) throws Exception {
        PrivateKey priKey = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        ByteArrayOutputStream out = doFinal(cipher, content, ENCRPT_MAX_LEN);
        return out.toByteArray();
    }

    public static byte[] publicDecrypt(byte[] content, String publicKey) throws Exception {
        PublicKey pubKey = getPublicKey(publicKey);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        ByteArrayOutputStream out = doFinal(cipher, content, DECRPT_MAX_LEN);
        return out.toByteArray();
    }

    public static byte[] publicEncrypt(byte[] content, String publicKey) throws Exception {
        PublicKey pubKey = getPublicKey(publicKey);
        Cipher cipher = Cipher.getInstance(Transformation.ECB_PKCS1PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        ByteArrayOutputStream out = doFinal(cipher, content, ENCRPT_MAX_LEN);
        return out.toByteArray();
    }

    /**
     * 去除数据集合中的空值和签名参数，得到待签名数据
     *
     * @param sArray
     * @return
     */
    public static Map<String, ?> paraFilter(Map<String, ?> sArray) {
        Map<String, Object> result = new HashMap<String, Object>();
        if ((sArray == null) || (sArray.size() <= 0)) {
            return result;
        }
        for (String key : sArray.keySet()) {
            Object value = sArray.get(key);
            if ((value == null) || value.equals("") || key.equalsIgnoreCase("sign_info")) {
                continue;
            }
            if (value instanceof Map) {
                Map<String, ?> m = (Map<String, ?>) value;
                result.put(key, paraFilter(m));
            } else if (value instanceof List) {
                continue;// 不应包含多集合数据
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * 将数组所有元素进行排序，并按照“参数1=参数值1&参数2=参数值2&...”的模式拼接成字符串
     *
     * @param params
     * @return
     */
    public static String createLinkStr(Map<String, ?> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer prestr = new StringBuffer("");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object o = params.get(key);
            String value = String.valueOf(o);
            if (o instanceof Map) {
                Map<String, ?> m = (Map<String, ?>) o;
                value = "{" + createLinkStr(m) + "}";
            }
            if (i == (keys.size() - 1)) {// 拼接时，不包括最后一个&字符
                prestr.append(key + "=" + value);
            } else {
                prestr.append(key + "=" + value + "&");
            }
        }
        return prestr.toString();
    }

    /*public static void main(String[] args) throws Exception {
        String privateKey = "MIICXQIBAAKBgQDjFgVea8mYwHcc8iwcoZlgDoB+Id+MAZu21qWbRa1KO1ETGLjCBx1y2p1Ur+uXCmLnlVb0sAfsFSt1w8ImDCFk8uAsN3Rs7IDPzej2UOWpAvsyv2w39uIfO0SJDP3q+iupy61scigdw039j438eMLHLvF5HyxBTxB3sXpPi07d4wIDAQABAoGAZmT0kMnLgjwuuXedWl+nl/+SAs3lTe0fuIb675OwestaWGEKVjr9FV/sF1anxVhilofpSLXjLJmzGgvmDF/l4hRY0Yks+tlVDCPTU6fuLGiM0H0x+C1MSGHN1EJ7xgi9kgrsq+EA4De67Kaise0C136jUVVdi0uGtw84eLy9PTkCQQD71fO4KXvSPvyjTC1C3h2VuH9qWuxBEpE5QYKTVHp+BmQOmTPgL3um2YvNih4+nzXhI1466etlfZ1SJswJHzhdAkEA5tdM98pbWfc02fbyEmNwTi0CD7vSzqTA79ONMwhAVxsn6pIhbO4vC0iZAUldysuo7Hpgo95BSQOCl0IwJsQLPwJBAMRbBqOyHKxKljtQ2v2HJ/QkHZy0df8q1faZoJD1TmjS3NMRGwSSoUvv1XSWW4yiIKWBKSiwWO0SK0bxnycBo7UCQQDkLZvSL79ezYH7liMwVAS18b1g6gr14lsodRfUgup+b8RkFPBfaY0s/STo5amFhQzEPC98q3wi/QpAE9fe6dgnAkAWygfTDnwy5gbQrV7PInvmiQ3PBIZH/lXhGxi9uBpblwovten+EbL+tcMj8D09RPnVet8p9x3/gCXpaFaZzQkU";
        String srcJson = "{\"biz_type\": \"cycling\",\"trade_type\": \"id_verify\",\"product_type\": \"id_card\",\"id_name\":\"姜桥\",\"id_number\": \"42102319890305291X\",\"card_number\": \"6225880139934809\",\"phone_number\": \"18610380635\"}";
        Map<String, ?> paraMap = JsonUtil.jsonToMap(srcJson);
        Map<String, ?> result = paraFilter(paraMap);
        String signData = createLinkStr(result);
        System.out.println("待签名数据->" + signData);
        String privateSignData = privateSignToHexString(signData, privateKey);
        System.out.println("签名信息->" + privateSignData);

        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDjFgVea8mYwHcc8iwcoZlgDoB+Id+MAZu21qWbRa1KO1ETGLjCBx1y2p1Ur+uXCmLnlVb0sAfsFSt1w8ImDCFk8uAsN3Rs7IDPzej2UOWpAvsyv2w39uIfO0SJDP3q+iupy61scigdw039j438eMLHLvF5HyxBTxB3sXpPi07d4wIDAQAB";
        boolean b = publicDecryptVerifySign(signData, privateSignData, pubKey);//公钥验证签名
        System.out.println("公钥验证签名结果->" + b);

    }*/
}
