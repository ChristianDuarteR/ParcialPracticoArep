package co.edu.escuelaing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Double.parseDouble;

public class ReflexCalculator {
    public static String calculate(String service) throws InvocationTargetException, IllegalAccessException {
        int operationIndex= service.indexOf("?");
        int numerosIndex = service.indexOf("=");
        String operation = service.substring(operationIndex, numerosIndex);
        String[] numeros = service.substring(numerosIndex).split(",");
        ArrayList<Double> nums = mapToDouble(numeros);

        if (operation.equals("bbl")) {
            doBubleAlgorith(nums);
        } else {
            Class<Math> mathClass = Math.class;
            Method[] method = mathClass.getClasses().getClass().getDeclaredMethods();
            for (Method met: method) {
                String name = met.getName();
                if (name.equals(operation)){
                    if (numeros.length == 1) {
                        met.invoke(nums.get(0));
                    }else if (numeros.length == 2){
                        met.invoke(nums.get(0), nums.get(1));
                    } else {
                        met.invoke(nums.get(0), nums.get(1), nums.get(2));
                    }
                }
            }
        }
        return "OK";
    }

    private static ArrayList<Double> mapToDouble(String[] numeros) {
        ArrayList<Double> nums = new ArrayList<>();
        for (String numero: numeros) {
            nums.add(Double.parseDouble(numero));
        }
        return nums;
    }

    private static void doBubleAlgorith(ArrayList<Double> numeros) {
        Double aux = (double) 0;
        for (int i=0; i< numeros.size() ; i++) {
            for (int j=0; j< numeros.size() ; j++) {
                if (numeros.get(i) > numeros.get(j)) {
                    aux = numeros.get(i);
                    numeros.set(i,numeros.get(j));
                    numeros.set(j,aux);
                }
            }
        }
    }
}
