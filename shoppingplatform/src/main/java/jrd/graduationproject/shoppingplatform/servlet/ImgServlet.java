package jrd.graduationproject.shoppingplatform.servlet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet(urlPatterns="/home/imageVal")
public class ImgServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/jpeg");
		int width=40;
		int height=30;
		BufferedImage bufImg=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g=bufImg.getGraphics();
		
		HttpSession hs= request.getSession();
		String str="";
		Random r=new Random(new Date().getTime());
		for(int i=0;i<4;i++){
			int num=r.nextInt(10);
			str+=num;
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.drawString(""+num, width/4*i, height*2/3);
		}
		for(int i=0;i<2;i++){
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
		}
		hs.setAttribute("imgcode", str);
		ImageIO.write(bufImg, "JPEG", response.getOutputStream());
	}

}
