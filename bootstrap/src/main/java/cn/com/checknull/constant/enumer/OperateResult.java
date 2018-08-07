/**  
* @Project: xproduct
* @Title: OperateResult.java
* @Package cn.com.checknull.constant.enumer
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-17 下午5:13:20
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.constant.enumer;
/**
 * @ClassName OperateResult
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-17   下午5:13:20
 */
public enum OperateResult {
	SUCCESS(1),ERROR(0);
	
	private int result;
	private OperateResult(int result){
		this.result = result;
	}
	
	public int getResult(){
		return this.result;
	}
}

