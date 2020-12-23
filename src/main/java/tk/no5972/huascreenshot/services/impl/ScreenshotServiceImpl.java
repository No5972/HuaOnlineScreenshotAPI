package tk.no5972.huascreenshot.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import tk.no5972.huascreenshot.services.ScreenshotService;

@Service
public class ScreenshotServiceImpl implements ScreenshotService {

    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    @Value("${sikuli.hua.imagePath}")
    private String imagePath;


    public static void main(String[] args) throws FindFailed {
        new ScreenshotServiceImpl().getResult(Long.parseLong("299313080"), 4320, 7680, 5, -4500, -2000);
        System.out.println("test");
    }

    static BufferedImage base64StringToImage(String base64String) {
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            return bi1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 测试环境：1920x1080 字体缩放124% 百分浏览器最大化
     * <ol>
     * <li> 点击花灵派对的逛一逛，等待出现米米号的输入框</li>
     * <li> 输入米米号，等待出现点赞</li>
     * <li> 点击头像，等待出现面板</li>
     * <li> 等待出现社区形象按钮并点击，等待社区形象按钮变亮，Sikuli到此结束</li>
     * <li> 使用Selenium按照给定的参数缩放画面并执行<pre>page.screenshot</pre>（返回base64）</li>
     * <li> 把base64转换为字节码</li>
     * </ol>
     */
    @Override
    public BufferedImage getResult(Long miNum, Integer resolutionX, Integer resolutionY, Integer scale, Integer offsetX,
                                   Integer offsetY) throws FindFailed {
        try {
            Screen s = new Screen();
            // 点击逛一逛
            String imagePath = this.imagePath;
            s.click(imagePath + "gyg.png");
            // 等待加载并输入米米号
            s.wait(imagePath + "inputMi.png", 15);
            s.click(imagePath + "inputMi.png");
            s.type(miNum.toString());
            // 点击确认
            s.click(imagePath + "confirmMi.png");
            // 等待加载并点击头像
            s.wait(imagePath + "dianzan.png", 15);
            Match match = s.find(imagePath + "dianzan.png");
            match.setX(match.getX() - 100);
            match.setY(match.getY() - 20);
            s.click(match);
            // 等待加载社区形象按钮并点击
            s.wait(imagePath + "shequxinxiang.png", 15);
            s.click(imagePath + "shequxinxiang.png");
            s.wait(imagePath + "shequxinxiangL.png", 15);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            System.out.println("已点击完成，接下来交由Selenium调整并截图");


            System.setProperty("webdriver.chrome.driver", "C:/Users/Admin/Downloads/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
            ChromeDriver driver = new ChromeDriver(options);

            // 直接开启设备模拟，不要再手动开devtools了，否则截图截的是devtools的界面！
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", false);
            map.put("width", resolutionX);
            map.put("height", resolutionY);
            map.put("deviceScaleFactor", 1);
            driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", map);
            // 缩放Flash
            driver.executeScript("document.getElementsByTagName(\"embed\")[0].Zoom(500)");
            // Flash缩放后的视野位置微调
            driver.executeScript("document.getElementsByTagName(\"embed\")[0].Zoom(" + (new Float(10000 / scale / 100)).intValue() + ");document.getElementsByTagName(\"embed\")[0].Pan(" + offsetX + ", " + offsetY + ", 0)");
            // 等待Flash缩放完成，待完善
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            // 执行截图
            Map<String, Object> map2 = new HashMap<>();
            map2.put("fromSurface", true);
            String imageBase64 = driver.executeCdpCommand("Page.captureScreenshot", map2).get("data").toString();
            System.out.println(imageBase64.length());
            // 关闭设备模拟
            driver.executeCdpCommand("Emulation.clearDeviceMetricsOverride", new HashMap<>());
            // Flash缩放复原
            driver.executeScript("document.getElementsByTagName(\"embed\")[0].Zoom(500)");
            // 关闭面板，等待后续的截图请求
            s.click(imagePath + "cross.png");
            // 返回的base64内容写入PNG文件
            return base64StringToImage(imageBase64);


        } catch (FindFailed e) {
            throw e;
        }

    }

}
