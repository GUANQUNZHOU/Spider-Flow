package org.spiderflow.core.executor.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.spiderflow.annotation.Comment;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExecutor;
import org.springframework.stereotype.Component;

/**
 * List 工具类 防止NPE 添加了类似python的split()方法
 * @author Administrator
 *
 */
@Component
@Comment("list常用方法")
public class ListFunctionExecutor implements FunctionExecutor{

	@Override
	public String getFunctionPrefix() {
		return "list";
	}

	@Comment("获取list的长度")
	@Example("${list.length(listVar)}")
	public static int length(List<?> list){
		return list != null ? list.size() : 0;
	}

	/**
	 *
	 * @param list 原List
	 * @param len 按多长进行分割
	 * @return List<List<?>> 分割后的数组
	 */
	@Comment("分割List")
	@Example("${list.split(listVar,10)}")
	public static List<List<?>> split(List<?> list,int len){
		List<List<?>> result = new ArrayList<>();
		if (list == null || list.size() == 0 || len < 1) {
			return result;
		}
		int size = list.size();
		int count = (size + len - 1) / len;
		for (int i = 0; i < count; i++) {
			List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}

	@Comment("截取List")
	@Example("${list.sublist(listVar,fromIndex,toIndex)}")
	public static List<?> sublist(List<?> list,int fromIndex,int toIndex){
		return list!= null ? list.subList(fromIndex, toIndex) : new ArrayList<>();
	}

	@Comment("过滤字符串list元素")
	@Example("${listVar.filterStr(pattern)}")
	public static List<String> filterStr(List<String> list, String pattern) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<String> result = new ArrayList<>(list.size());
		for (String item : list) {
			if (Pattern.matches(pattern, item)) {
				result.add(item);
			}
		}
		return result;
	}

	@Comment("提取List中Map的values")
	@Example("@{list.extractFromMap(listVar,targetKey)}")
	public static List<String> extractFromMap(List<Map<String,Object>> list, String targetKey){
		if (list == null || list.isEmpty()) {
			return null;
		}
		final ArrayList<String> strings = new ArrayList<>(list.size());
		for (Map<String, Object> stringObjectMap : list) {
			strings.add((String) stringObjectMap.get(targetKey));
		}
		return strings;
	}

	@Comment("用List中的element生成SQL UPDATE语句")
	@Example("@{list.generateUpdateSql(listVar)}")
	public static String generateUpdateSql(List<String> list, String status){
		if (list == null || list.isEmpty()) {
			return null;
		}
		final StringBuilder builder = new StringBuilder();
		builder.append("UPDATE Information SET `status`=");
		builder.append(status);
		builder.append("WHERE `Id` IN (");
		for (String s : list) {
			builder.append(s);
			builder.append(",");
		}
		builder.deleteCharAt(builder.length()-1);// 删掉最后一个逗号
		builder.append(")");
		return builder.toString();
	}

}
