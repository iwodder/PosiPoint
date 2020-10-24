package com.wodder;


import com.wodder.controllers.*;
import com.wodder.gui.*;

public class Main {

    private static PosiPoint posiPoint;

    public static void main( String[] args ) {

        posiPoint = new PosiPoint(Controllers.getInstance());
        posiPoint.createAndShow();
    }

}
