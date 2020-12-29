package tk.no5972.huascreenshot.services.impl;

import java.awt.*;
import java.awt.event.InputEvent;
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

    @Value("#{'${robot.gyg.coord}'.split(',')}")
    private int[] gygCoord;

    @Value("#{'${robot.inputMi.color}'.split(',')}")
    private int[] inputMiColor;

    @Value("#{'${robot.inputMi.coord}'.split(',')}")
    private int[] inputMiCoord;

    @Value("#{'${robot.inputMi.rect}'.split(',')}")
    private int[] inputMiRect;

    @Value("#{'${robot.confirmMi.coord}'.split(',')}")
    private int[] confirmMiCoord;

    @Value("#{'${robot.dianzan.color}'.split(',')}")
    private int[] dianzanColor;

    @Value("#{'${robot.dianzan.coord}'.split(',')}")
    private int[] dianzanCoord;

    @Value("#{'${robot.dianzan.rect}'.split(',')}")
    private int[] dianzanRect;

    @Value("#{'${robot.shequxinxiang.coord}'.split(',')}")
    private int[] shequxinxiangCoord;

    @Value("#{'${robot.shequxinxiang.rect}'.split(',')}")
    private int[] shequxinxiangRect;

    @Value("#{'${robot.shequxinxiang.color}'.split(',')}")
    private int[] shequxinxiangColor;

    @Value("#{'${robot.shequxinxiangL.coord}'.split(',')}")
    private int[] shequxinxiangLCoord;

    @Value("#{'${robot.shequxinxiangL.rect}'.split(',')}")
    private int[] shequxinxiangLRect;

    @Value("#{'${robot.shequxinxiangL.color}'.split(',')}")
    private int[] shequxinxiangLColor;


    @Value("#{'${robot.cross.coord}'.split(',')}")
    private int[] crossCoord;



    public static void main(String[] args) throws AWTException, InterruptedException {
        // new ScreenshotServiceImpl().getResult(Long.parseLong("299313080"), 4320, 7680, 5, -4500, -2000);
        new ScreenshotServiceImpl().getResult2(Long.parseLong("299313080"), 4320, 7680, 5D, -4500, -2000);
//        new ScreenshotServiceImpl().keyPressString(new Robot(), "299313080");
        System.out.println("test");
    }

    private static BufferedImage base64StringToImage(String base64String) {
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean compareColor(Color color1, Color color2) {
        return color1.getRed() == color2.getRed() &&
                color1.getBlue() == color2.getBlue() &&
                color1.getGreen() == color2.getGreen();
    }

    private void waitColor(Color needColor, int rangeX, int rangeY, int rangeW, int rangeH) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        long t;
        while (true) {
            BufferedImage image = robot.createScreenCapture(new Rectangle(rangeX, rangeY, rangeW, rangeH));
            int x, y;
            try {
                t = new java.util.Date().getTime();
                for (x = 0; x < rangeW - 1; x++) {
                    for (y = 0; y < rangeH - 1; y++) {
                        Color color = new Color(image.getRGB(x - rangeX, y - rangeY));
                        if (compareColor(color, needColor)) {
                            System.out.println(new java.util.Date().getTime() - t);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(100);
        }
    }

    private void waitColor(Color needColor, Rectangle range) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        long t;
        while (true) {
            BufferedImage image = robot.createScreenCapture(range);
            int x, y;
            try {
                t = new java.util.Date().getTime();
                for (x = 0; x < range.getWidth() - 1; x++) {
                    for (y = 0; y < range.getHeight() - 1; y++) {
                        Color color = new Color(image.getRGB(x, y));
                        if (compareColor(color, needColor)) {
                            System.out.println(new java.util.Date().getTime() - t);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(100);
        }
    }

    private static void keyPressString(Robot r, String str) throws InterruptedException {
        byte[] bytes = str.getBytes();
        for (byte e : bytes) {
            r.keyPress(e);
            r.keyRelease(e);
            Thread.sleep(50);
        }
    }

    private static void mouseClick(Robot r, int x, int y) throws AWTException {
    	r = new Robot();
    	// https://stackoverflow.com/questions/48837741/java-robot-mousemovex-y-not-producing-correct-results
    	for(int count = 0;(MouseInfo.getPointerInfo().getLocation().getX() != x || 
                MouseInfo.getPointerInfo().getLocation().getY() != y) &&
                count < 10; count++) {
    		System.out.println("移动了" + (count + 1) + "次");
    		r.mouseMove(x, y);
    	}
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    @SuppressWarnings({ "AccessStaticViaInstance", "static-access" })
    public BufferedImage getResult2(Long miNum, Integer resolutionX, Integer resolutionY, Double scale, Integer offsetX,
                                    Integer offsetY) throws AWTException, InterruptedException {
        // 点击逛一逛
        Robot robot = new Robot();
        robot.mouseMove(0, 0);
        this.mouseClick(robot, gygCoord[0], gygCoord[1]);
        // 等待加载并输入米米号
        Color inputMi = new Color(inputMiColor[0], inputMiColor[1], inputMiColor[2]);
        this.waitColor(inputMi, new Rectangle(inputMiRect[0], inputMiRect[1], inputMiRect[2], inputMiRect[3]));
        this.mouseClick(robot, inputMiCoord[0], inputMiCoord[1]);
        this.keyPressString(robot, miNum.toString());
        // 点击确认
        this.mouseClick(robot, confirmMiCoord[0], confirmMiCoord[1]);
        // 等待加载并点击头像
        Color dianzan = new Color(dianzanColor[0], dianzanColor[1], dianzanColor[2]);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this.waitColor(dianzan, new Rectangle(dianzanRect[0], dianzanRect[1], dianzanRect[2], dianzanRect[3]));
        this.mouseClick(robot, dianzanCoord[0], dianzanCoord[1]);
        // 等待加载社区形象按钮并点击
        Color shequxinxiang = new Color(shequxinxiangColor[0], shequxinxiangColor[1], shequxinxiangColor[2]);
        this.waitColor(shequxinxiang, new Rectangle(shequxinxiangRect[0], shequxinxiangRect[1], shequxinxiangRect[2], shequxinxiangRect[3]));
        robot = new Robot();
        this.mouseClick(robot, shequxinxiangCoord[0], shequxinxiangCoord[1]);
        Color shequxinxiangL = new Color(shequxinxiangLColor[0], shequxinxiangLColor[1], shequxinxiangLColor[2]);
        this.waitColor(shequxinxiangL, new Rectangle(shequxinxiangLRect[0], shequxinxiangLRect[1], shequxinxiangLRect[2], shequxinxiangLRect[3]));


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
        this.mouseClick(robot, crossCoord[0], crossCoord[1]);
        // 返回的base64内容写入PNG文件
        return base64StringToImage(imageBase64);
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
    public BufferedImage getResult(Long miNum, Integer resolutionX, Integer resolutionY, Double scale, Integer offsetX,
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
