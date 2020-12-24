package tk.no5972.huascreenshot.services;

import org.sikuli.script.FindFailed;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ScreenshotService {
	BufferedImage getResult(Long miNum, Integer resolutionX, Integer resolutionY, Integer scale, Integer offsetX, Integer offsetY) throws FindFailed;
	BufferedImage getResult2(Long miNum, Integer resolutionX, Integer resolutionY, Integer scale, Integer offsetX, Integer offsetY) throws AWTException, InterruptedException;
}
