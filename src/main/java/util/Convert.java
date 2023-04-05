/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Amirul_Hafiz
 */
public class Convert {
    
    public static Double[] toArrayDouble(double[] array){
        Double[] tempArray = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = (Double) array[i];
        }
        return tempArray;
    }
    
    public static double[] toArraydouble(Double[] array){
        double[] tempArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = (double) array[i];
        }
        return tempArray;
    }
    
}
