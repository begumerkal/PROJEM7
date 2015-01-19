package com.wyd.rolequery.util;

import java.util.ArrayList;
import java.util.List;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    List<Integer> list = new ArrayList<Integer>();
	    for (int i = 0; i < 5000; i++) {
	        list.add(i);
        }
	    SqlStringUtil sql = new SqlStringUtil();
	    System.out.println(sql.getTSQLIn(list, 5000, "id"));
	}

}
