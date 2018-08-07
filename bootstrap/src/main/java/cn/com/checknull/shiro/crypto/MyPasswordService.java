/**  
* @Project: xproduct-shiro
* @Title: MyPasswordService.java
* @Package cn.com.checknull.shiro.crypto
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com 273244373
* @date 2015-11-20 上午10:47:10
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.shiro.crypto;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;

/**
 * @ClassName MyPasswordService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-11-20   上午10:47:10
 */
public class MyPasswordService extends DefaultPasswordService{

	public static final String MY_DEFAULT_HASH_ALGORITHM = "SHA-256";
	public static final String MY_DEFAULT_PUBLIC_SALT = "chenwei.liu@163.com"; 
    public static final String MY_DEFAULT_PRIVATE_SALT = "273244373"; 
    public static final int MY_DEFAULT_HASH_ITERATIONS = 5; 

    public MyPasswordService() {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName(MY_DEFAULT_HASH_ALGORITHM);
        hashService.setHashIterations(MY_DEFAULT_HASH_ITERATIONS);
        hashService.setGeneratePublicSalt(true);
        hashService.setPrivateSalt(createByteSource(MY_DEFAULT_PRIVATE_SALT));
        setHashService(hashService);
        setHashFormat(new MyCryptFormat());
        setHashFormatFactory(new MyHashFormatFactory());
    }
    
   @Override
   protected HashRequest createHashRequest(ByteSource plaintext) {
        return new HashRequest.Builder().setSource(plaintext)
        		   .setSalt(ByteSource.Util.bytes(MY_DEFAULT_PUBLIC_SALT))
        		   .build();
    }
    
}

