package imageProject;

import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
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
	//Camera camera = new Camera(new Point(0, 0, 10000),  new Vector(0, 1, 0),new Vector(0, 0, -1)) //
		//	.setVPSize(2500, 2500).setVPDistance(10000);
	@Test
	void Image() {
		//x+: right, x-: left
		//y+:up, y-:down
		//z+: close, z-: far
		
		Camera camera = new Camera(new Point(0, 0, 10000),  new Vector(0, 1, 0),new Vector(0, 0, -1)) //
				.setVPSize(2500, 2500).setVPDistance(10000).setAntialiasing(9).setMultiThreading(true);//setAdaptiveSS(true);//

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(173,216,230));//.setSoftShadow(9).setDelta(12.5);
	
		
		scene.geometries.add(
				//Spheres
				new Sphere(new Point(700,-900,400),100d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKd(1).setKt(new Double3(0.3))),//yellow: spot light - down, right
				new Sphere(new Point(900,950,500),150d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(new Double3(0.1))),//yellow: spot light - up, right

				new Sphere(new Point(-850,100,0),150d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//yellow: up, left
				new Sphere(new Point(-1000,-1000,-5000),800d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//yellow: down, left
				new Sphere(new Point(150,-700,0),150d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),//yellow: down, middle
				new Sphere(new Point(800,-300,0),200d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),//yellow: middle, right
				new Sphere(new Point(-200,250,-800),350d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKR(new Double3(0.2))),//yellow: middle, middle
				new Sphere(new Point(500,750,500),100d).setEmission(new Color(190,160,21)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),//yellow: middle-up, middle-right 
				
				new Sphere(new Point(1100,-500,-1200),1000d).setEmission(new Color(15,146,160)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),//blue: down, right
				new Sphere(new Point(-200,1500,-1500),1000d).setEmission(new Color(15,146,160)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),//blue: up, middle-left
				new Sphere(new Point(-1100,-100,-5500),700d).setEmission(new Color(15,146,160)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),//blue:-p[
				

				//Triangles
				//down
				new Triangle(new Point(100,-200,400), new Point(-400,-300,400),new Point(-350,-500,600)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//1
				new Triangle(new Point(100,-200,400), new Point(-400,-300,400),new Point(-600,-250,-400)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//2
				new Triangle(new Point(100,-200,400), new Point(-350,-500,600),new Point(-300,-400,700)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//3
				new Triangle(new Point(100,-200,400), new Point(-300,-400,700),new Point(-200,-550,1200)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//4
				//middle
				new Triangle(new Point(200,400,500), new Point(700,300,500),new Point(650,100,700)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//1
				new Triangle(new Point(200,400,500), new Point(700,300,500),new Point(900,350,-300)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//2
				new Triangle(new Point(200,400,500), new Point(650,100,700),new Point(600,200,800)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//3
				new Triangle(new Point(200,400,500), new Point(600,200,800),new Point(500,50,1300)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//4
				//small up
				new Triangle(new Point(-160,740,-500), new Point(-460,860,-500),new Point(-420,700,-340)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//1
				new Triangle(new Point(-160,740,-500), new Point(-460,860,-500),new Point(-620,900,-1140)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//2
				new Triangle(new Point(-160,740,-500), new Point(-420,700,-340),new Point(-380,780,-260)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)),//3
				new Triangle(new Point(-160,740,-500), new Point(-380,780,-260),new Point(-300,660,140)).setEmission(new Color(83,83,83)).setMaterial(new Material().setKd(1).setKs(0.5).setShininess(60)));//4

		//Lights - spot lights
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-3000, 3000, 400), new Vector(0,-1,-1)) //corner: up, left
				.setKl(4E-5).setKq(2E-7));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(700,-900,400),new Vector(0,1,-4)) //yellow sphere: down, right
				.setKl(4E-5).setKq(2E-7));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(900,950,500),new Vector(-1,-3,0)) //yellow sphere: up, right
				.setKl(4E-5).setKq(2E-7));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(0,0,5000),new Vector(0,1,-3)) //behind camera: front, direction to the picture
				.setKl(4E-5).setKq(2E-7));
		

		ImageWriter imageWriter = new ImageWriter("PROJECT1", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage();//
				camera.writeToImage();
	}
	

}
