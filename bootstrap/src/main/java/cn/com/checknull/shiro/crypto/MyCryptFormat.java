/**  
* @Project: xproduct-shiro
* @Title: MyCryptFormat.java
* @Package cn.com.checknull.shiro.crypto
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com 273244373
* @date 2015-11-20 上午10:55:36
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.shiro.crypto;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;

/**
 * @ClassName MyCryptFormat
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-11-20   上午10:55:36
 */
public class MyCryptFormat  extends Shiro1CryptFormat{
	public static final String MY_ID = "check_null";
	public static final String MY_MCF_PREFIX = TOKEN_DELIMITER + MY_ID + TOKEN_DELIMITER;
    public MyCryptFormat() {
    }

    @Override
	public String getId() {
		return MY_ID;
	}

	@Override
	public String format(Hash hash) {
		
    	if (hash == null) {
            return null;
        }
        String algorithmName = hash.getAlgorithmName();
        ByteSource salt = hash.getSalt();
        int iterations = hash.getIterations();
        StringBuilder sb = new StringBuilder(MY_MCF_PREFIX).append(algorithmName).append(TOKEN_DELIMITER).append(iterations).append(TOKEN_DELIMITER);
        if (salt != null) {
            sb.append(salt.toBase64());
        }
        sb.append(TOKEN_DELIMITER);
        sb.append(hash.toBase64());
        return sb.toString();
	}

	public Hash parse(String formatted) {
        if (formatted == null) {
            return null;
        }
        if (!formatted.startsWith(MY_MCF_PREFIX)) {
            String msg = "The argument is not a valid '" + ID + "' formatted hash.";
            throw new IllegalArgumentException(msg);
        }

        String suffix = formatted.substring(MY_MCF_PREFIX.length());
        String[] parts = suffix.split("\\$");

        //last part is always the digest/checksum, Base64-encoded:
        int i = parts.length-1;
        String digestBase64 = parts[i--];
        //second-to-last part is always the salt, Base64-encoded:
        String saltBase64 = parts[i--];
        String iterationsString = parts[i--];
        String algorithmName = parts[i];

        byte[] digest = Base64.decode(digestBase64);
        ByteSource salt = null;

        if (StringUtils.hasLength(saltBase64)) {
            byte[] saltBytes = Base64.decode(saltBase64);
            salt = ByteSource.Util.bytes(saltBytes);
        }

        int iterations;
        try {
            iterations = Integer.parseInt(iterationsString);
        } catch (NumberFormatException e) {
            String msg = "Unable to parse formatted hash string: " + formatted;
            throw new IllegalArgumentException(msg, e);
        }

        SimpleHash hash = new SimpleHash(algorithmName);
        hash.setBytes(digest);
        if (salt != null) {
            hash.setSalt(salt);
        }
        hash.setIterations(iterations);

        return hash;
    }
}

