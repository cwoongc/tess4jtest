package com.cwoongc.tess4jtest;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;

@SpringBootApplication
public class Tess4jtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tess4jtestApplication.class, args);
	}


	@Profile({"doOCR"})
	@Bean
	CommandLineRunner doOCR(ApplicationContext ctx) {
		return args -> {

			File imgFile = new File(getClass().getResource("image007.png").getFile());

			System.out.println(imgFile.isFile());
			System.out.println(imgFile.getAbsolutePath());
			System.out.println(imgFile.getPath());

			ITesseract instance = new Tesseract();

			try {
				instance.setLanguage("kor+eng");

				String result = instance.doOCR(imgFile);
				System.out.println(result);
			} catch (TesseractException e) {
				System.err.println(e.getMessage());
			}

		};
	}

	@Profile({"see-unicode"})
	@Bean
	CommandLineRunner seeUnicode(ApplicationContext ctx) {
		return args -> {
			try(
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hangul.txt",false),"UTF-8"))
			){
				for(int c=0xAC00,i=0;c<=0xD7A3;c++,i++) {

					if(i%38==0 && i!=0) br.write(System.lineSeparator());
					else if(i!=0) br.write(' ');

					br.write((char)c);

					if(i%101 == 0) br.flush();
				}

				br.flush();
			}
		};
	}

	@Profile({"split-43"})
	@Bean
	CommandLineRunner split43(ApplicationContext ctx) {
		return args -> {
				File uniFile = new File(getClass().getResource("uni.txt").getFile());
				try(
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(uniFile),"UTF-8"));
				){

					String c = null;
					int i = 0;
					int docNo = 0;

					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("unichar/h"+docNo+".txt")));
					while ((c = br.readLine()) != null) {

						if (i % 43 == 0 && i != 0) {
							bw.flush();
							bw.close();
							docNo++;
							bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("unichar/h"+docNo+".txt")));


						} else if(i!=0) bw.write(System.lineSeparator());

						bw.write(c);
						i++;
					}

					bw.flush();

					bw.close();
					br.close();
				}
		};
	}
}
