/**  
 * @Project: xproduct-mybatis
 * @Title: Ognl.java
 * @Package 
 * @Description: TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-6-4 上午10:08:14
 * @Copyright: 2015 check_null Reserved.
 * @version v1.0  
 */
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @ClassName Ognl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-6-4   上午10:08:14
 */
public class Ognl {
	 
	public static boolean isEmpty(Object o) throws IllegalArgumentException {
	        if (o == null) return true;

	        if (o instanceof String) {
	            if (((String) o).length() == 0) {
	                return true;
	            }
	        } else if (o instanceof Collection) {
	            if (((Collection<?>) o).isEmpty()) {
	                return true;
	            }
	        } else if (o.getClass().isArray()) {
	            if (Array.getLength(o) == 0) {
	                return true;
	            }
	        } else if (o instanceof Map) {
	            if (((Map<?, ?>) o).isEmpty()) {
	                return true;
	            }
	        }else {
	            return false;
	        }
	        return false;
	    }
	
	 public static boolean isNotEmpty(Object o) {
	        return !isEmpty(o);
	  }
}

