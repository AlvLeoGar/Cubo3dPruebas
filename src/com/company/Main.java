package com.company;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import org.jdesktop.j3d.loaders.vrml97.impl.TextureCoordinate;

import java.awt.*;
//si

import javax.media.j3d.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class Main implements ChangeListener {
    JPanel canvasPanel;
    JPanel mainPanel;
    JPanel sliderPanel;
    JFrame frame;
    JSlider sliderX;
    JSlider sliderY;
    JSlider sliderZ;
    TransformGroup tgl;
    Transform3D translacion;
    Transform3D rotacion;
    ColorCube cubo;
    //Box cubo;
    BranchGroup escena;
    final int MIN_ANGL = -180;
    final int START_ANGL = 0;
    final int MAX_ANGL = 180;

    public Main(){
        frame = new JFrame("First");
        canvasPanel = new JPanel();
        mainPanel = new JPanel();
        sliderPanel = new JPanel();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        SimpleUniverse universo = new SimpleUniverse(canvas3D);
        universo.getViewingPlatform().setNominalViewingTransform();

        translacion = new Transform3D();
        rotacion = new Transform3D();
        escena = crearGrafoEscena();
        escena.compile();

        universo.addBranchGraph(escena);
        translacion.set(new Vector3f(0,0,0));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(canvas3D);
        sliderX = crearSlider();
        sliderY = crearSlider();
        sliderZ = crearSlider();
        sliderPanel.add(sliderX);
        sliderPanel.add(sliderY);
        sliderPanel.add(sliderZ);
        mainPanel.add(sliderPanel);
        frame.add(mainPanel);
    }

    public BranchGroup crearGrafoEscena(){
        BranchGroup objetoRaiz = new BranchGroup();

        tgl = new TransformGroup(translacion);

        cubo = new ColorCube(0.3f);
        //cubo = new Box(0.2f, 0.2f, 0.2f, appearance());

        tgl.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgl.addChild(cubo);
        objetoRaiz.addChild(tgl);

        return objetoRaiz;
    }

    /*private Appearance appearance(){
        Appearance appearance = new Appearance();

        TexCoordGeneration texCoord = new TexCoordGeneration(
                TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_2
        );
        appearance.setTexCoordGeneration(texCoord);

        String textUbication = "src/com/piedra.jpg";

        TextureLoader loader = new TextureLoader(textUbication, mainPanel);
        ImageComponent2D image = loader.getImage();

        Texture2D texture = new Texture2D(
          Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight()
        );
        texture.setImage(0, image);
        texture.setEnable(true);

        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);

        appearance.setTexture(texture);
        appearance.setTextureAttributes(new TextureAttributes());

        return appearance;
    }*/

    public void run(){
        frame.setVisible(true);
    }

    public JSlider crearSlider(){
        JSlider slider = new JSlider(JSlider.HORIZONTAL,MIN_ANGL, MAX_ANGL, START_ANGL);
        slider.addChangeListener(this);
        return slider;
    }

    public void modX(int value){
        rotacion.rotX(Math.toRadians(value));
        translacion.mul(rotacion);
        tgl.setTransform(translacion);
    }

    public void modY(int value){
        rotacion.rotY(Math.toRadians(value));
        translacion.mul(rotacion);
        tgl.setTransform(translacion);
    }

    public void modZ(int value){
        rotacion.rotZ(Math.toRadians(value));
        translacion.mul(rotacion);
        tgl.setTransform(translacion);
    }

    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");
        Main p = new Main();
        p.run();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if(source.equals(sliderX)){
            modX(source.getValue());
        }
        else if(source.equals(sliderY)){
            modY(source.getValue());
        }
        else if(source.equals(sliderZ)){
            modZ(source.getValue());
        }
    }
}
