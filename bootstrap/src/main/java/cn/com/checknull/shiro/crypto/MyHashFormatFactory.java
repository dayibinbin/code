/**  
* @Project: xproduct-shiro
* @Title: MyHashFormatFactory.java
* @Package cn.com.checknull.shiro.crypto
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com 273244373
* @date 2015-11-20 下午1:25:30
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.shiro.crypto;

import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;
import org.apache.shiro.crypto.hash.format.HashFormat;

/**
 * @ClassName MyHashFormatFactory
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-11-20   下午1:25:30
 */
public class MyHashFormatFactory extends DefaultHashFormatFactory {

	@Override
	public HashFormat getInstance(String in) {
		HashFormat hashFormat = super.getInstance(in);
		if (hashFormat != null) return hashFormat;
		return newHashFormatInstance(MyCryptFormat.class);
	}
	
}

