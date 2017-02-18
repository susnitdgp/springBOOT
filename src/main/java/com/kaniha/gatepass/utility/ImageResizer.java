package com.kaniha.gatepass.utility;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class ImageResizer {
	
	private static final int IMG_WIDTH = 50;
	private static final int IMG_HEIGHT = 50;
	
	public InputStream resizeImage(InputStream in,int img_width,int img_height){
		
		InputStream out=null;
		
		BufferedImage originalImage=null;
		
		BufferedImage resizedImage=null;
		
		ByteArrayOutputStream baos=null;
		
		try {
			originalImage=ImageIO.read(in);
			
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			
			
			 resizedImage = new BufferedImage(img_width, img_height, type);
			
			Graphics2D g = resizedImage.createGraphics();
			
			g.drawImage(originalImage, 0, 0, img_width, img_height, null);
			
			g.dispose();
			
			baos = new ByteArrayOutputStream();
			
			ImageIO.write(resizedImage,"png",baos);
			
			out = new ByteArrayInputStream(baos.toByteArray());
			
			out.close();
			originalImage.flush();
			resizedImage.flush();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
						
		return out;
		
	}
	
	public BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
		
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		
		return resizedImage;
	    }	

}
