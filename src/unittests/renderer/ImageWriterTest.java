package unittests.renderer;
import renderer.*;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageWriterTest {

	/**
	 * 
	 */
	@Test
	void Imagetest() {
		Color RED = new Color(255d,0d,0d);
		Color YELLOW = new Color(255d, 256d,0d);
		ImageWriter imageWriter=new ImageWriter("ImageTest", 800, 500);
		for (int i=0; i<800;i++) {
			for(int j=0;j<500;j++) {
				if(i%50==0||j%50==0) {
					imageWriter.writePixel(i, j,RED);
				}
				else {
					imageWriter.writePixel(i, j, YELLOW);
				}
			} 
		}
		imageWriter.writeToImage();
	}
 
}
