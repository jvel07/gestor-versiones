/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Jose07
 */
public class MD5 {

    private static final char[] CONSTS_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toMD5(String stringAEncriptar) {
        try {
            MessageDigest messdig = MessageDigest.getInstance("MD5");
            byte[] bytes = messdig.digest(stringAEncriptar.getBytes());
            StringBuilder strbldCadenaMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int bajo = (int) (bytes[i] & 0x0f);
                int alto = (int) ((bytes[i] & 0xf0) >> 4);
                strbldCadenaMD5.append(CONSTS_HEX[alto]);
                strbldCadenaMD5.append(CONSTS_HEX[bajo]);
            }
            return strbldCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
