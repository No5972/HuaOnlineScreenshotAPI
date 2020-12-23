package tk.no5972.huascreenshot.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.sikuli.script.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tk.no5972.huascreenshot.services.ScreenshotService;

@Controller
@RequestMapping("/screenshotAction")
public class ScreenshotAction {
	@Autowired
	private ScreenshotService screenshotService;

	private boolean isAvailble = true;
	
	@RequestMapping("/screenshot.png")
	public void screenshot(
			Long miNum,
			Integer resolutionX, 
			Integer resolutionY, 
			Integer scale, 
			Integer offsetX,
			Integer offsetY,
			HttpServletResponse response) throws IOException {
		try {
			System.out.println(isAvailble);
			if (!isAvailble) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				response.getWriter().write("<title>503 功能正在被占用，等会再试试哦！</title><h1>503 功能正在被占用，等会再试试哦！</h1><hr>错误信息：<pre>503 Service Temporarily Unavailble</pre>");
				return;
			}
			isAvailble = false;
			Screen s = new Screen();
			System.out.println(s);
			/*
			//想要返回图片的路径
			FileInputStream fis = new FileInputStream("C:/Users/Admin/hao123.png") ;
			//得到文件大小
			int size = fis.available();
			byte data[] = new byte[size] ;
			fis.read(data) ;
			fis.close();
					
			//设置返回的文件类型
			response.setContentType("image/jpeg");
			OutputStream os = response.getOutputStream() ;
			os.write(data);
			os.flush();
			os.close();
			*/
			
			response.setContentType("image/png");
			BufferedImage bufferedImage = screenshotService.getResult(miNum, resolutionX, resolutionY, scale, offsetX, offsetY);
			ImageIO.write(bufferedImage, "png", response.getOutputStream());
			isAvailble = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ee) {
			System.err.println("测试");
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("<title>500 好像出错了啦！</title><h1>500 好像出错了啦！</h1><hr>错误信息：<pre>" + ee.toString() + ee.getLocalizedMessage() + "<br>");
			for(StackTraceElement es : ee.getStackTrace()){
				response.getWriter().write("在 " + es.toString());
				response.getWriter().write("<br />");
			}
			response.getWriter().write("</pre>");
			isAvailble = true;
		}
	}
}
