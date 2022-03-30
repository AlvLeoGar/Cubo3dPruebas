package com.company;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.*;
//si

import javax.media.j3d.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    BranchGroup escena;
    final int MIN_ANGL = -90;
    final int START_ANGL = 0;
    final int MAX_ANGL = 90;

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
        tgl.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgl.addChild(cubo);
        objetoRaiz.addChild(tgl);

        return objetoRaiz;
    }

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
