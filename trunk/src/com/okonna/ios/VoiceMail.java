package com.okonna.ios;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;

import com.okonna.ios.util.*;

public class VoiceMail
{
	private static String strSrcVMDir = System.getenv("HOME") + "/iPhone_fake/Voicemail/";
	private static String strDestVMDir = System.getenv("HOME") + "/iPhone/Voicemail/";

	public static void main(String[] args) throws Exception
	{
		Date dteTemp;
		SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy_MM_dd-E-hh:mm:ss_a");

		AddressBook adbContacts = new AddressBook();

		String strTempName;
		String strTempVMID;
		String strTempVMTS;
		Class.forName("org.sqlite.JDBC");

		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + System.getenv("HOME") + "/iPhone_fake/Voicemail/voicemail.db");
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT * FROM voicemail ORDER BY date;");

		while (rs.next())
		{
			strTempVMID = rs.getString("ROWID");

			dteTemp = SQLLiteData.convertDate(rs.getString("date"));
			strTempVMTS = sdfTemp.format(dteTemp);

			strTempName = adbContacts.lookupContact(rs.getString("sender"));

			createContactDir(strTempName);
			moveVoicemail(strTempName, strTempVMID, strTempVMTS);

			// dteTemp = SQLLiteData.convertDate(rs.getString("expiration"));
			// System.out.print(sdfTemp.format(dteTemp) + " ");
			// System.out.print(adbContacts.lookupContact(rs.getString("callback_num"))
			// + " ");
			// System.out.print(rs.getString("duration") + " ");
			// System.out.print(rs.getString("flags") + " ");
			// System.out.println();
		}
		stat.close();
		rs.close();
		conn.close();
	}

	private static void createContactDir(String strContact)
	{
		File dirCurrent = new File(strDestVMDir + strContact);
		if (!dirCurrent.exists())
		{
			try
			{
				boolean success = (dirCurrent).mkdir();
				if (!success)
				{
					throw new Exception("Well that wasn't planned for...");
				}
			}
			catch (Exception e)
			{
				System.err.println(e.getLocalizedMessage());
				System.err.println();
				System.err.println(e.getStackTrace());
				System.exit(0);
			}
		}
	}

	private static void moveVoicemail(String strContact, String strID, String strTempVMTS)
	{
		System.out.println(strContact + " " + strID + " " + strTempVMTS);

		try
		{
			File fSrc = new File(strSrcVMDir + "/" + strID  + ".amr");
			File fDest = new File(strDestVMDir + strContact + "/" + strTempVMTS  + ".amr");
			InputStream isSrc = new FileInputStream(fSrc);

			OutputStream osDest = new FileOutputStream(fDest);

			byte[] buf = new byte[1024];
			int len;
			while ((len = isSrc.read(buf)) > 0)
			{
				osDest.write(buf, 0, len);
			}
			isSrc.close();
			osDest.close();
			System.out.println("File copied.");
		}
		catch (Exception e)
		{
			System.err.println(e.getLocalizedMessage());
			System.err.println();
			System.err.println(e.getStackTrace());
			System.exit(0);
		}
	}

}
