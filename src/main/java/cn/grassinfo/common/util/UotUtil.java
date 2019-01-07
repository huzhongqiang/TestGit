package cn.grassinfo.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UotUtil {
	/**
	 * @Title: outPrintJosn
	 * @Description: 输出数据
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unused")
	private static void outPrintJosn(HttpServletRequest request,HttpServletResponse response,String str, String jsoncallback) {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();
			if (jsoncallback!=null && jsoncallback.length()>0) {
				out.print(jsoncallback + "(" + str + ")");
			} else {
				out.print(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
