/**  
* @Project: xproduct
* @Title: Md5Utils.java
* @Package cn.com.checknull.util
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-17 上午10:49:56
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName Md5Utils
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-17   上午10:49:56
 */
public abstract class Md5Utils {
	
	private static final Logger logger = LoggerFactory.getLogger(Md5Utils.class);
	
	private static final char HEX_DIGITS[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	public final static String md5Encode(String visiblePass){
		 byte[] btInput = visiblePass.getBytes();
		 try {
			 MessageDigest mdInst  = MessageDigest.getInstance("MD5");
			 mdInst .update(btInput);
             byte[] md = mdInst .digest();
             int j = md.length;
             char str[] = new char[j * 2];
             int k = 0;
             for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
             }
            return new String(str).toUpperCase();
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            return null;
        }
	}
}

