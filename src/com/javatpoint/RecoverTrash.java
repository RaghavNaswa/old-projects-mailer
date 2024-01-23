package com.javatpoint;

import java.sql.PreparedStatement;

import com.javatpoint.ConProvider;

public class RecoverTrash {
	public static int recover(int id) {
		int status = 0;
		try {
			java.sql.Connection con = ConProvider.getConnection();
			PreparedStatement ps = con
					.prepareStatement("UPDATE `mailer`.`company_mailer_message` SET `TRASH`='no' WHERE  `ID`=?;");
			ps.setInt(1, id);

			status = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}

}