package com.company;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.*;


import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.vecmath.Vector3f;

public class Main {
    JPanel panel;
    JFrame frame;

    public Main(){
        frame = new JFrame("First");
        panel = new JPanel();
        frame.add(panel);
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        panel.setLayout(new BorderLayout());
        panel.add(canvas3D);

        SimpleUniverse universo = new SimpleUniverse(canvas3D);
        universo.getViewingPlatform().setNominalViewingTransform();

        BranchGroup escena = crearGrafoEscena();
        escena.compile();

        universo.addBranchGraph(escena);
    }

    public BranchGroup crearGrafoEscena(){
        BranchGroup objetoRaiz = new BranchGroup();

        Transform3D translacion = new Transform3D();
        translacion.set(new Vector3f(0,0,0));
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(45));

        translacion.mul(rotacion);

        rotacion.rotX(Math.toRadians(45));

        translacion.mul(rotacion);

        TransformGroup tgl = new TransformGroup(translacion);

        ColorCube cubo = new ColorCube(0.3f);

        tgl.addChild(cubo);

        objetoRaiz.addChild(tgl);

        return objetoRaiz;
    }

    public void run(){
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");
        Main p = new Main();
        p.run();
    }
}
