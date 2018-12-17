package com.prg.store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	public static Object createObject(String name) {
		try {
			SAXReader reader = new SAXReader();
			InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			
			Document doc = reader.read(in);
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			
			for (Element ele : elements) {
				String id = ele.attributeValue("id");
				if (id.equals(name)) {
					String str = ele.attributeValue("class");
					Class clazz = Class.forName(str);
					return clazz.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
