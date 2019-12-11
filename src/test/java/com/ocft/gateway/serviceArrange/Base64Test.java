package com.ocft.gateway.serviceArrange;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * @author lijiaxing
 * @Title: Base64Test
 * @ProjectName gateway
 * @date 2019/12/11下午2:04
 * @Description: TODO
 */
public class Base64Test {
    public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {
        String str = "_portal-medscimeeting_recommendMeeting_state";

        String encode2 = URLEncoder.encode(str,"UTF-8");
        String encode3 = URLEncoder.encode(str,"UTF-8");

        System.out.println(encode2);
        System.out.println(encode3);

        String s = Hex.encodeHexString(str.getBytes());
        System.out.println(s);
        byte[] bytes = Hex.decodeHex(s);
        System.out.println(new String(bytes));


        String encode = CryptoUtil.encode(str);
        System.out.println(encode);
        String decode = CryptoUtil.decode(encode);
        System.out.println(decode);

        String s1 = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(str.getBytes());
        System.out.println(s1);
        byte[] bytes1 = org.apache.commons.codec.binary.Base64.decodeBase64(s1);
        System.out.println(new String(bytes1));

    }
}
