package tk.no5972.huascreenshot.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
	
	@RequestMapping("/screenshot")
	public void screenshot(
			Long miNum,
			Integer resolutionX, 
			Integer resolutionY, 
			Integer scale, 
			Integer offsetX,
			Integer offsetY,
			HttpServletResponse response) {
		try {
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
