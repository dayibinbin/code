/**  
* @Project: xteach-system
* @Title: DateSerializer.java
* @Package cn.com.checknull.entity.serializer
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-4-3 下午4:03:01
* @Copyright: 2016 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.model.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/**
 * @ClassName DateSerializer
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-4-3 下午4:03:01
 */
public class DateSerializer extends JsonSerializer<Date>{

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonGenerator.writeString(sdf.format(date));
	}

}

