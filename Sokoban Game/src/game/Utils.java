package game;

import java.io.File;

public class Utils {
	//�ж��Ƿ�����ļ�file
	//@param file��D://������//1.map
	//@return���ڼ�Ϊtrue
	static boolean IsExistence(String file) {
		File fileuser = new File(file);
		if(!fileuser.exists()) {
			return false;
		}
		return true;
	}

}
