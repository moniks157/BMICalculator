package pl.edu.pwr.maczkowiak.lab1.bmicalculator;

/**
 * Created by Monika Maczkowiak on 2017-04-03.
 */

public interface CountBMI {

    public boolean isMassValid(float mass);

    public boolean isHeightValid(float height);

    public float countBMI(float mass,float height);
}
