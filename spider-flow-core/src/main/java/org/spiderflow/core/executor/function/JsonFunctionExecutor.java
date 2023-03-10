package org.spiderflow.core.executor.function;

import org.spiderflow.annotation.Comment;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;

/**
 * Json和String互相转换 工具类 防止NPE
 * @author Administrator
 *
 */
@Component
@Comment("json常用方法")
public class JsonFunctionExecutor implements FunctionExecutor{

	@Override
	public String getFunctionPrefix() {
		return "json";
	}

	@Comment("将字符串转为json对象")
	@Example("${json.parse('{code : 1}')}")
	public static Object parse(String jsonString){
		return jsonString != null ? JSON.parse(jsonString) : null;
	}

	@Comment("将对象转为json字符串")
	@Example("${json.stringify(objVar)}")
	public static String stringify(Object object){
		return object != null ? JSON.toJSONString(object) : null;
	}

	@Comment("组装Json")
	@Example("${json.assemble(keys,values)}")
	public static String assemble(List<String> keys, List<String> values){
		final HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < keys.size(); i++) {
			map.put(keys.get(i),values.get(i));
		}
		return JSON.toJSONString(map);
	}
}
