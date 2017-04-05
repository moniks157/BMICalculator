package pl.edu.pwr.maczkowiak.lab1.bmicalculator;

/**
 * Created by Monika Maczkowiak on 2017-04-03.
 */

public class CountBMIforMetric implements CountBMI {
    static final float MINIMAL_MASS=10.0f;
    static final float MAXIMAL_MASS=250.0f;
    static final float MINIMAL_HEIGHT=0.5f;
    static final float MAXIMAL_HEIGHT=2.5f;

    public boolean isMassValid(float mass){
        return mass>=MINIMAL_MASS&&mass<=MAXIMAL_MASS;
    }

    public boolean isHeightValid(float height){
        return height>=MINIMAL_HEIGHT&&height<=MAXIMAL_HEIGHT;
    }

    public float countBMI(float mass, float height){
        if(isMassValid(mass)&&isHeightValid(height)){
            return mass/(height*height);
        }
        else throw new IllegalArgumentException();
    }
}
