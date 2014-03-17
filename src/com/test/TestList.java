package com.test;

import java.util.ArrayList;
import java.util.List;

import com.bean.InstanceTaskBean;
import com.common.Tools;

public class TestList {

	public static void main(String[] args) {
		Class clazz = InstanceTaskBean.class;
		List<InstanceTaskBean> list = new ArrayList<InstanceTaskBean>();
		InstanceTaskBean fb1 = new InstanceTaskBean(10001000, 10001);
		InstanceTaskBean fb2 = new InstanceTaskBean(10001001, 10001);
		InstanceTaskBean fb3 = new InstanceTaskBean(10001002, 10001);
		InstanceTaskBean fb4 = new InstanceTaskBean(100019999, 10001);
		list.add(fb1);
		list.add(fb2);
		list.add(fb3);
		list.add(fb4);

		Tools.printList(list, clazz);

	}
}
