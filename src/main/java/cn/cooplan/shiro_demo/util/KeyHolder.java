package cn.cooplan.shiro_demo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于存放主键值的对象
 * @author 李恒
 *
 */
public class KeyHolder {
	private List<Object> keyList;
	
	public KeyHolder() {
		keyList=new ArrayList<Object>();
	}

	/**
	 * 如果主键有多个字段，调用这个方法获得主键值
	 * @return 封装了多个主键值的集合
	 */
	public List<Object> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<Object> keyList) {
		this.keyList = keyList;
	}
	
	/**
	 * 如果主键只有一个字段，调用此方法获得这个主键值
	 * @return 主键值
	 */
	public Object getKey(){
		if(keyList.size()==0){
			return null;
		}
		return keyList.get(0);
	}
	
}
