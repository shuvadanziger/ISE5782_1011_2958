package imageProject;

import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

class ImageProgect {
	
	private Scene scene = new Scene("Project1");
	Camera camera = new Camera(new Point(0, 0, 10000),  new Vector(0, 1, 0),new Vector(0, 0, -1)) //
			.setVPSize(2500, 2500).setVPDistance(10000);
	@Test
	void Image() {
		Camera camera = new Camera(new Point(0, 0, 10000),  new Vector(0, 1, 0),new Vector(0, 0, -1)) //
				.setVPSize(2500, 2500).setVPDistance(10000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(173,216,230));
	
		
		scene.geometries.add(
				new Sphere(new Point(1000,0,0),200d).setEmission(new Color(184,134,11)).setMaterial(new Material().setKd(1).setKt(new Double3(0.3))),//green sphere
				//new Sphere(new Point(0,0,-5000),900d).setEmission(new Color(173,216,230)).setMaterial(new Material().setKd(1).setKt(new Double3(0.4))),
				//new Sphere(new Point(0,0,-5000),500d).setEmission(new Color(184,134,11)).setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(60)),
				//up
				new Triangle(new Point(0,0,0), new Point(-500,-100,0),new Point(-450,-300,200)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//1
				new Triangle(new Point(0,0,0), new Point(-500,-100,0),new Point(-700,-50,-800)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//2
				new Triangle(new Point(0,0,0), new Point(-450,-300,200),new Point(-400,-200,300)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//3
				new Triangle(new Point(0,0,0), new Point(-400,-200,300),new Point(-300,-350,800)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//4
				//down
				new Triangle(new Point(0,400,0), new Point(500,300,0),new Point(450,100,200)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//1
				new Triangle(new Point(0,400,0), new Point(500,300,0),new Point(700,350,-800)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//2
				new Triangle(new Point(0,400,0), new Point(450,100,200),new Point(400,200,300)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//3
				new Triangle(new Point(0,400,0), new Point(400,200,300),new Point(300,50,800)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//4
				
				new Triangle(new Point(-60,440,0), new Point(-460,560,0),new Point(-420,400,160)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//1
				new Triangle(new Point(-60,440,0), new Point(-460,560,0),new Point(-620,600,-640)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//2
				new Triangle(new Point(-60,440,0), new Point(-420,400,160),new Point(-380,480,240)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//3
				new Triangle(new Point(-60,440,0), new Point(-380,480,240),new Point(-300,360,640)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)));//4



		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-3000, 3000, 0), new Vector(1,-1,0)) //up-left
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("PROJECT1", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage();//
				camera.writeToImage();
	}
	

}
