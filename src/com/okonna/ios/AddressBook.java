package com.okonna.ios;

import java.util.HashMap;

public class AddressBook
{
	private HashMap<String, String> strNameNum;

	AddressBook()
	{
		strNameNum = populateContacts();
	}

	String lookupContact(String strPhoneNum)
	{
		if (strNameNum.containsKey(strPhoneNum))
		{
			return strNameNum.get(strPhoneNum);
		}
		else
		{
			return strPhoneNum;
		}
	}

	private static HashMap<String, String> populateContacts()
	{
		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("5556661234", "Test");
		return temp;
	}
}
