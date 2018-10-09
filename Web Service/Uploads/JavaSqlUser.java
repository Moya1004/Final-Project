import java.lang.reflect.Module;
import java.sql.Date;

public class JavaSqlUser {
	private SqlDateUser sqlDateUser;
	
	public JavaSqlUser() {
		Module smod = SqlDateUser.class.getModule();
		System.out.printf("JavaSqlUser.init, SqlDateUser.module:%s, loader: %s\n", smod, smod.getClassLoader());
		sqlDateUser = new SqlDateUser();
	}
	
	public Date getDate() {
		return sqlDateUser.getDate();
	}
	
	public String toString() {
		return String.format("JavaSqlUser(%s)", sqlDateUser);
	}
}
