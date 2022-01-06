/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.utils;


import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.List;

/**
 * @author maoyz0621 on 2020/11/8
 * @version v1.0
 */
public class AesUtil {
    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);

    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    public static final String KEY = "something4u";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encrypt(String data, String key) {
        return doAES(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static String decrypt(String data, String key) {
        return doAES(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加解密
     *
     * @param data 待处理数据
     * @param mode 加解密mode
     * @return
     */
    private static String doAES(String data, String key, int mode) {
        try {
            if (data == null || "".equals(data) || key == null || "".equals(key)) {
                return null;
            }
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = parseHexStr2Byte(data);
            }
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);
            // 初始化
            cipher.init(mode, keySpec);
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                return parseByte2HexStr(result);
            } else {
                return new String(result, defaultCharset);
            }
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
    private static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
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
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 进行敏感字段解密/加密处理
     */
    public static void handleItem(Object item, String secretKey, boolean isEncrypted) throws IllegalAccessException {
        // 捕获类中的所有字段
        Field[] fields = item.getClass().getDeclaredFields();
        // 遍历所有字段
        for (Field field : fields) {
            // 若该字段被SecurityParameter注解,则进行解密/加密
            Class<?> fieldType = field.getType();
            if (fieldType == String.class) {
                if (null != AnnotationUtils.findAnnotation(field, CryptField.class)) {
                    // 设置private类型允许访问
                    field.setAccessible(Boolean.TRUE);
                    handleField(item, field, secretKey, isEncrypted);
                    field.setAccessible(Boolean.FALSE);
                }
            } else if (List.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                // 是否参数化类型
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                    if (genericClazz == String.class && null != AnnotationUtils.findAnnotation(field, CryptField.class)) {
                        field.setAccessible(Boolean.TRUE);
                        List list = (List) field.get(item);
                        if (list != null && !list.isEmpty()) {
                            for (int i = 0; i < list.size(); i++) {
                                if (isEncrypted) {
                                    list.set(i, AesUtil.decrypt((String) list.get(i), secretKey));
                                } else {
                                    list.set(i, AesUtil.encrypt((String) list.get(i), secretKey));
                                }
                            }
                        }
                        field.setAccessible(Boolean.FALSE);
                    } else {
                        Field[] subFields = genericClazz.getDeclaredFields();
                        for (Field subField : subFields) {
                            if (subField.getType() == String.class && null != AnnotationUtils.findAnnotation(subField, CryptField.class)) {
                                field.setAccessible(Boolean.TRUE);
                                List list = (List) field.get(item);
                                if (list != null && !list.isEmpty()) {
                                    for (Object subObj : list) {
                                        subField.setAccessible(Boolean.TRUE);
                                        handleField(subObj, subField, secretKey, isEncrypted);
                                        subField.setAccessible(Boolean.FALSE);
                                    }
                                    field.setAccessible(Boolean.FALSE);
                                }
                            }
                        }
                    }
                }
            } else if (fieldType.getName().startsWith("com.jn.ssr")) {
                Field[] subFields = fieldType.getDeclaredFields();
                for (Field subField : subFields) {
                    if (subField.getType() == String.class && null != AnnotationUtils.findAnnotation(subField, CryptField.class)) {
                        field.setAccessible(Boolean.TRUE);
                        Object obj = field.get(item);
                        subField.setAccessible(Boolean.TRUE);
                        handleField(obj, subField, secretKey, isEncrypted);
                        subField.setAccessible(Boolean.FALSE);
                        field.setAccessible(Boolean.FALSE);
                    }
                }
            }
            // T responseData由于泛型擦除，通过反射无法直接获取到实际类型
            else if (fieldType == Object.class) {
                field.setAccessible(Boolean.TRUE);
                handleItem(field.get(item), secretKey, isEncrypted);
                field.setAccessible(Boolean.FALSE);
            }
        }
    }

    private static void handleField(Object item, Field field, String secretKey, boolean isEncrypted) throws IllegalAccessException {
        if (null == item) {
            return;
        }
        if (isEncrypted) {
            field.set(item, AesUtil.decrypt((String) field.get(item), secretKey));
        } else {
            field.set(item, AesUtil.encrypt((String) field.get(item), secretKey));
        }
    }

}