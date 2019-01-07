package cn.grassinfo.common.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.grassinfo.common.Constant;
import cn.grassinfo.common.service.QxysService;
import cn.grassinfo.wap.entity.YingshiMedal;

/**
 * 
 * 气象影视
 * @author Administrator
 *
 */
@Controller
public class QxysWebController {
	private static final Logger log = LoggerFactory
			.getLogger(QxysWebController.class);
	QxysService service = new QxysService();
	private static final String qxjmStore =Constant.propFileManager.getString("qxjmStore");

    /**
     * 
     *	气象影视首页
     * @return
     */
    @RequestMapping("qxys")
    public ModelAndView index(ModelAndView mv) {
    	List<YingshiMedal> list = service.YsList();
		mv.addObject("list", list);
        mv.setViewName("media/list");
        return mv;
    }
    
    /**
     * 
     *	气象影视详情
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "info")
    public ModelAndView index(ModelAndView mv,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
    	String url = request.getParameter("url");
//    	url = new String(url .getBytes("iso8859-1"),"utf-8");
		mv.addObject("url", "video.html?url="+url.replace("\'", ""));
        mv.setViewName("media/info");
        return mv;
    }
    
    @RequestMapping(value = "video", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) {
   
        String name = request.getParameter("url").replace("\'", "");
   
        File file = new File(qxjmStore + name);
   
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            byte[] buffer = new byte[4 * 1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件读取失败,文件不存在");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件流输出异常");
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                System.out.println("文件流关闭异常");
                e.printStackTrace();
            }
        }
   
    }

   

}
