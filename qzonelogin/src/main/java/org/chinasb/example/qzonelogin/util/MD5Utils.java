package org.chinasb.example.qzonelogin.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class MD5Utils {
    private static boolean hexcase = true;
    private static int mode = 32;
    private static int chrsz = 8;
    
    public static String md5(String A) {
        return hex_md5(A);
    }
    
    public static String hex_md5(String A) {
       return binl2hex(core_md5(str2binl(A), A.length() * chrsz));
    }
    
    public static String str_md5(String A) {
        return binl2str(core_md5(str2binl(A), A.length() * chrsz));
    }
    
    public static int[] core_md5(int[] K, int F) {
        int[] X = new int[(((F + 64) >>> 9) << 4) + 14 + 2];
        for (int i = 0; i < K.length; i++) {
            X[i] = K[i];
        }
        
        X[F >> 5] |= 128 << ((F) % 32);
        X[(((F + 64) >>> 9) << 4) + 14] = F;
        int J = 1732584193;
        int I = -271733879;
        int H = -1732584194;
        int G = 271733878;
        for (int C = 0; C < X.length; C += 16) {
            int E = J;
            int D = I;
            int B = H;
            int A = G;
            J = md5_ff(J, I, H, G, X[C + 0], 7, -680876936);
            G = md5_ff(G, J, I, H, X[C + 1], 12, -389564586);
            H = md5_ff(H, G, J, I, X[C + 2], 17, 606105819);
            I = md5_ff(I, H, G, J, X[C + 3], 22, -1044525330);
            J = md5_ff(J, I, H, G, X[C + 4], 7, -176418897);
            G = md5_ff(G, J, I, H, X[C + 5], 12, 1200080426);
            H = md5_ff(H, G, J, I, X[C + 6], 17, -1473231341);
            I = md5_ff(I, H, G, J, X[C + 7], 22, -45705983);
            J = md5_ff(J, I, H, G, X[C + 8], 7, 1770035416);
            G = md5_ff(G, J, I, H, X[C + 9], 12, -1958414417);
            H = md5_ff(H, G, J, I, X[C + 10], 17, -42063);
            I = md5_ff(I, H, G, J, X[C + 11], 22, -1990404162);
            J = md5_ff(J, I, H, G, X[C + 12], 7, 1804603682);
            G = md5_ff(G, J, I, H, X[C + 13], 12, -40341101);
            H = md5_ff(H, G, J, I, X[C + 14], 17, -1502002290);
            I = md5_ff(I, H, G, J, X[C + 15], 22, 1236535329);
            J = md5_gg(J, I, H, G, X[C + 1], 5, -165796510);
            G = md5_gg(G, J, I, H, X[C + 6], 9, -1069501632);
            H = md5_gg(H, G, J, I, X[C + 11], 14, 643717713);
            I = md5_gg(I, H, G, J, X[C + 0], 20, -373897302);
            J = md5_gg(J, I, H, G, X[C + 5], 5, -701558691);
            G = md5_gg(G, J, I, H, X[C + 10], 9, 38016083);
            H = md5_gg(H, G, J, I, X[C + 15], 14, -660478335);
            I = md5_gg(I, H, G, J, X[C + 4], 20, -405537848);
            J = md5_gg(J, I, H, G, X[C + 9], 5, 568446438);
            G = md5_gg(G, J, I, H, X[C + 14], 9, -1019803690);
            H = md5_gg(H, G, J, I, X[C + 3], 14, -187363961);
            I = md5_gg(I, H, G, J, X[C + 8], 20, 1163531501);
            J = md5_gg(J, I, H, G, X[C + 13], 5, -1444681467);
            G = md5_gg(G, J, I, H, X[C + 2], 9, -51403784);
            H = md5_gg(H, G, J, I, X[C + 7], 14, 1735328473);
            I = md5_gg(I, H, G, J, X[C + 12], 20, -1926607734);
            J = md5_hh(J, I, H, G, X[C + 5], 4, -378558);
            G = md5_hh(G, J, I, H, X[C + 8], 11, -2022574463);
            H = md5_hh(H, G, J, I, X[C + 11], 16, 1839030562);
            I = md5_hh(I, H, G, J, X[C + 14], 23, -35309556);
            J = md5_hh(J, I, H, G, X[C + 1], 4, -1530992060);
            G = md5_hh(G, J, I, H, X[C + 4], 11, 1272893353);
            H = md5_hh(H, G, J, I, X[C + 7], 16, -155497632);
            I = md5_hh(I, H, G, J, X[C + 10], 23, -1094730640);
            J = md5_hh(J, I, H, G, X[C + 13], 4, 681279174);
            G = md5_hh(G, J, I, H, X[C + 0], 11, -358537222);
            H = md5_hh(H, G, J, I, X[C + 3], 16, -722521979);
            I = md5_hh(I, H, G, J, X[C + 6], 23, 76029189);
            J = md5_hh(J, I, H, G, X[C + 9], 4, -640364487);
            G = md5_hh(G, J, I, H, X[C + 12], 11, -421815835);
            H = md5_hh(H, G, J, I, X[C + 15], 16, 530742520);
            I = md5_hh(I, H, G, J, X[C + 2], 23, -995338651);
            J = md5_ii(J, I, H, G, X[C + 0], 6, -198630844);
            G = md5_ii(G, J, I, H, X[C + 7], 10, 1126891415);
            H = md5_ii(H, G, J, I, X[C + 14], 15, -1416354905);
            I = md5_ii(I, H, G, J, X[C + 5], 21, -57434055);
            J = md5_ii(J, I, H, G, X[C + 12], 6, 1700485571);
            G = md5_ii(G, J, I, H, X[C + 3], 10, -1894986606);
            H = md5_ii(H, G, J, I, X[C + 10], 15, -1051523);
            I = md5_ii(I, H, G, J, X[C + 1], 21, -2054922799);
            J = md5_ii(J, I, H, G, X[C + 8], 6, 1873313359);
            G = md5_ii(G, J, I, H, X[C + 15], 10, -30611744);
            H = md5_ii(H, G, J, I, X[C + 6], 15, -1560198380);
            I = md5_ii(I, H, G, J, X[C + 13], 21, 1309151649);
            J = md5_ii(J, I, H, G, X[C + 4], 6, -145523070);
            G = md5_ii(G, J, I, H, X[C + 11], 10, -1120210379);
            H = md5_ii(H, G, J, I, X[C + 2], 15, 718787259);
            I = md5_ii(I, H, G, J, X[C + 9], 21, -343485551);
            J = safe_add(J, E);
            I = safe_add(I, D);
            H = safe_add(H, B);
            G = safe_add(G, A);
        }
        if (mode == 16) {
            int[] r = { I, H };
            return r;
        } else {
            int[] r = { J, I, H, G };
            return r;
        }       
    }

    public static int md5_cmn(int F, int C, int B, int A, int E, int D) {
        return safe_add(bit_rol(safe_add(safe_add(C, F), safe_add(A, D)), E), B);
    }

    public static int md5_ff(int C, int B, int G, int F, int A, int E, int D) {
        return md5_cmn((B & G) | ((~B) & F), C, B, A, E, D);
    }

    public static int md5_gg(int C, int B, int G, int F, int A, int E, int D) {
        return md5_cmn((B & F) | (G & (~F)), C, B, A, E, D);
    }

    public static int md5_hh(int C, int B, int G, int F, int A, int E, int D) {
        return md5_cmn(B ^ G ^ F, C, B, A, E, D);
    }

    public static int md5_ii(int C, int B, int G, int F, int A, int E, int D) {
        return md5_cmn(G ^ (B | (~F)), C, B, A, E, D);
    }

    public static int safe_add(int A, int D) {
        int C = (A & 65535) + (D & 65535);
        int B = (A >> 16) + (D >> 16) + (C >> 16);
        return (B << 16) | (C & 65535);
    }

    public static int bit_rol(int A, int B) {
        return (A << B) | (A >>> (32 - B));
    }
    
    public static int[] str2binl(String D) {
        int[] C = new int[(D.length() / 4) + (D.length() % 4 > 0 ? 1 : 0)];
        int A = (1 << chrsz) - 1;
        for (int B = 0; B < D.length() * chrsz; B += chrsz) {
            C[B >> 5] |= (D.charAt(B / chrsz) & A) << (B % 32);
        }
        return C;
    }
        
    public static String binl2str(int[] C) {
        String D = "";
        int A = (1 << chrsz) - 1;
        for (int B = 0; B < C.length * 32; B += chrsz) {
            D += fromCharCode((C[B >> 5] >>> (B % 32)) & A);
        }
        return D;
    }
    
    public static String fromCharCode(int... codePoints) {
        StringBuilder builder = new StringBuilder(codePoints.length);
        for (int codePoint : codePoints) {
            builder.append(Character.toChars(codePoint));
        }
        return builder.toString();
    }
    
    public static String binl2hex(int[] C) {
        String B = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
        String str = "";
        for (int n : C) {
            for (int j = 0; j <= 3; j++)
                str = str + B.charAt((n >> (j * 8 + 4)) & 0x0F) + B.charAt((n >> (j * 8)) & 0x0F);
        }
        return str.toUpperCase();
    }
    
    public static String hexchar2bin(String str) throws UnsupportedEncodingException {
        String B = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
        ByteArrayOutputStream baos = new ByteArrayOutputStream(str.length() / 2);
        for (int i = 0; i < str.length(); i = i + 2) {
            baos.write((B.indexOf(str.charAt(i)) << 4 | B.indexOf(str.charAt(i + 1))));
        }
        return new String(baos.toByteArray(), "ISO-8859-1");
    }
    
    public static String uin2hex(String str) throws UnsupportedEncodingException {
        int maxLength = 16;
        Integer strValue = Integer.parseInt(str);
        String hex = Integer.toString(strValue, 16);
        int len = hex.length();
        for (int i = len; i < maxLength; i++) {
            hex = "0" + hex;
        }
        return hex;
    }
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String H = hexchar2bin(md5("201314.."));
        String F = md5(H + hexchar2bin("\\x00\\x00\\x00\\x00\\x03\\x7b\\xab\\x09".replace("\\x", "").toUpperCase()));
        String C = md5(F + "!PZM");
        System.out.println(C);
    }
    
}
