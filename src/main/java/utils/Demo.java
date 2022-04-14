package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        String appSecret = "8980665c685d4f5a86b61e1c6fef3518";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAINsAeeh45h9NvBGi43M5o6YweBPb8eGgMZ834opqNIHRJF3biHmdI5EPuteVWEpBEby5hEc+1PlFVxsB31aFJYYNl3cU406CHaNkCXthKXixyNG2A7TRbpgjVp6HzizAWXkY5tyDqZQFW/MSHvhYHgtwzcmePsbS7hxwweikZKFAgMBAAECgYAFLX4tuA0VdulxMfZRkd8Lgy0wPzDyNXRhXY4ZDWcfTzX+XSwznba93CZ2c3L9rvYCLZOu3Otx9LaQu5jX8XKaIIdatQfTngUK6Tx2vqmp83KbFE77tMBewG6phrmO8pzzHWLF6cU/XhuubMRCSE1fLmeE4TnMh8X7LohxpFO2SQJBAN252phRiwp+oYmU8hCww/6gmYe3QhDDcGit6XdlwoY1VT7ag0o3rl5p/etNpwrO16Omexkh93GybkBs82dAMjMCQQCXvKNZ9zx/+ypBUREMk5Lb9xfVh4QC8pLckOueR3lUqi7z357xZD2cGaT+qAm6Py/t++Nwa6lIL04bj5FHrSBnAkAdZ08w0eeBbceYSjNLuVVUiBG0KL5PIMvgZefHGKySRbJXUn0Es5uhE6dDtBmJ2mJIZoR+kb2Ry1rJmmkw8Rd3AkAgOpD3quvOwypWdUGJyk6HKhSF5iLD/YH0F1P0ann64arXYcgAvanYXhoNfOOUC5fClJ3aYwOxsVqFwBi7gKpBAkEA0Jpcj6Sxv4z/Oi3n9V2dV8DYoO9Cv7RJsOq5RDdczJ5EC7oHarE/Xn88Q8x8q8Y6nACYE7VSaX+cUpck6Stang==";
        JSONObject client = new JSONObject();
        client.put("pkg", "com.xipu.cwtqmxcwqmxmini.nearme.gamecenter");
        TreeMap<String, String> map = new TreeMap<>();
         map.put("oaid", "");
         map.put("ssoid", "g145009611658752495");
         map.put("imei", "");

        String data = getEncryptData(appSecret, map);
        long t = System.currentTimeMillis();
        System.out.println(t);
        TreeMap<String, String> signContent = new TreeMap();

        signContent.put("t", String.valueOf(t));
        signContent.put("client", JSON.toJSONString(client, SerializerFeature.MapSortField));
        signContent.put("data", data);
        String sign = getSign(privateKey, signContent);
        System.out.println(sign);
        System.exit(0);
        Map<String, Object> request = new HashMap<>();
        request.put("t", t);
        request.put("client", client);
        request.put("data", data);
        request.put("sign", sign);
        System.out.println(request);
    }

    private static String getEncryptData(String appSecret, TreeMap<String, String> map) {
        String key = appSecret.substring(0, 16);
        String content = JSON.toJSONString(map);
        System.out.println(content);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            int blockSize = cipher.getBlockSize();
            System.out.println(blockSize);
            byte[] dataBytes = content.getBytes(StandardCharsets.UTF_8);
            int plaintextLen = dataBytes.length;
            if (plaintextLen % blockSize != 0) {
                plaintextLen = plaintextLen + (blockSize - plaintextLen % blockSize);
            }
            byte[] plaintext = new byte[plaintextLen];
            System.out.println(Arrays.toString(plaintext));
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryptBytes = cipher.doFinal(plaintext);
            return Base64.getEncoder().encodeToString(encryptBytes);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    private static String getSign(String privateKey, TreeMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> parts = map.entrySet().iterator();
        while (parts.hasNext()) {
            Map.Entry<String, String> part = parts.next();
            if (part.getValue() != null) {
                sb.append(part.getKey()).append("=").append(part.getValue()).append("&");
            }
        }
        System.out.println(sb);
        return sign(privateKey, sb.toString());
    }

    private static String sign(String privateKey, String content) {
         try {
             PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec (Base64.getDecoder().decode(privateKey));
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
             Signature signature = Signature.getInstance("SHA1WithRSA");
             signature.initSign(priKey);
             signature.update(content.getBytes(StandardCharsets.UTF_8));
             byte[] signed = signature.sign();
             return new String(Base64.getEncoder().encode(signed), StandardCharsets.UTF_8);
         } catch (Exception e) {
             return StringUtils.EMPTY;
         }
    }
}

