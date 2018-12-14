package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;



public class LinkTest {

	public static void main(String[] args) {
		String[] array = {"1","2","3"};
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
		System.out.println(StringUtils.join(arrayList.toArray()));
		System.out.println(arrayList.size());
	}

}
