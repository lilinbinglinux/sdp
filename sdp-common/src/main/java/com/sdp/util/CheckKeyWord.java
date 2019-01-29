package com.sdp.util;

import java.util.ArrayList;
import java.util.List;

public class CheckKeyWord {
	  public static boolean isKeyword(String input) {
		  List<String> keywords = new ArrayList<String>();
		  keywords.add("select");
		  keywords.add("from");
		  keywords.add("where");
		  keywords.add("count");
		  keywords.add("max");
		  keywords.add("min");
		  keywords.add("char");
		  keywords.add("varchar");
		  keywords.add("varchar2");
		  keywords.add("smallint");
		  keywords.add("int");
		  keywords.add("numeric");
		  keywords.add("float");
		  keywords.add("double");
		  keywords.add("precision");
		  keywords.add("real");
		  keywords.add("primary");
		  keywords.add("delete");
		  keywords.add("update");
		  keywords.add("set");
		  keywords.add("all");
		  keywords.add("and");
		  keywords.add("or");
		  keywords.add("not");
		  keywords.add("distinct");
		  keywords.add("join");
		  keywords.add("desc");
		  keywords.add("between");
		  keywords.add("union");
		  keywords.add("nion");
		  keywords.add("except");
		  keywords.add("asc");
		  keywords.add("avg");
		  keywords.add("sum");
		  keywords.add("having");
	        if (keywords.contains(input.toLowerCase())) {
	            return true;
	        }
	        return false;
	    } 
}
