package co.edu.escuelaing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflexCalculator {
    public static String calculate(String service) throws InvocationTargetException, IllegalAccessException {
        int operationIndex= service.indexOf("?");
        int numerosIndex = service.indexOf("=");
        String operation = service.substring(operationIndex + 1, numerosIndex);
        String[] numeros = service.substring(numerosIndex+1).split(",");
        ArrayList<String> numsToUse = clean(numeros);
        System.out.println(numsToUse);
        ArrayList<Double> nums = mapToDouble(numsToUse);
        if (operation.equals("bbl")) {
            doBubleAlgorith(nums);
        } else {
            Class<Math> mathClass = Math.class;
            Method[] method = mathClass.getClasses().getClass().getDeclaredMethods();
            for (Method met: method) {
                String name = met.getName();
                if (name.equals(operation)){
                    if (numsToUse.isEmpty()) {
                        return (String) met.invoke(met.getName());
                    }
                    else if (numsToUse.size() == 1) {
                        return (String) met.invoke(nums.get(0));
                    }else if (numsToUse.size() == 2){
                        return (String) met.invoke(nums.get(0), nums.get(1));
                    } else {
                        return (String) met.invoke(nums.get(0), nums.get(1), nums.get(2));
                    }
                }
            }
        }
        return "OK";
    }

    private static ArrayList<String> clean(String[] numeros) {
        ArrayList<String> nums = new ArrayList<>();
        for (String numero: numeros) {
            if (numero.contains("[") ){
                nums.add(numero.replace("[", ""));
            }

            if (numero.contains("]")){
                nums.add(numero.replace("]", ""));
            }
        }
        return nums;
    }

    private static ArrayList<Double> mapToDouble(ArrayList<String> numeros) {
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
