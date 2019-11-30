package game;

import java.io.File;

public class Utils {
	//判断是否存在文件file
	//@param file如D://推箱子//1.map
	//@return存在即为true
	static boolean IsExistence(String file) {
		File fileuser = new File(file);
		if(!fileuser.exists()) {
			return false;
		}
		return true;
	}

}
