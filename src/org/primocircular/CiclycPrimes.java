/*
 * Copyright 2015 Maria Virginia Lucena.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primocircular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Calcula y devuelve una lista de los primos circulares por debajo del 
 * 1 millón usando hilos, Primo circular 197 es un “primo circular“ porque 
 * todas las rotaciones de sus dígitos: 197, 971 y 719 son a su vez números 
 * primos.
 * @author Maria Virginia Lucena <mariavirginialu@gmail.com>
 */
public class CiclycPrimes implements Runnable{
    
    /**
     * Número dado hasta el cual se calculan los primos circulares
     */
    private int limit;
    /**
     * Lista de numeros primos ciclicos
     */
    private List<Integer> ciclycNumbers; 
    /**
     * Lista de combinaciones posibles de un numero para ser evaludas
     */
    private List<Integer> toEvaluate; 
    //private Map<Integer, Boolean> evaluatedNumber;

    /**
     * se recibe por constructor el numero hasta el cual se quieren calcular los
     * numeros primos circulares
     */
    public CiclycPrimes(int limit) {
        this.limit = limit;
        //evaluatedNumber = new HashMap<>();
        toEvaluate = new ArrayList<>(); 
        ciclycNumbers = new ArrayList<>();
    }
    
    /**
     * evalua y almacena el numero en caso de ser primo circular
     * @param number numero a ser evaluado
     */
    public void evalNumber(int number){
        if(!ciclycNumbers.contains(number)){
            if(isCiclyc(toEvaluate))
                for (Integer evaluatedNumber : toEvaluate) 
                    ciclycNumbers.add(evaluatedNumber);
        }
    }
    
    /**
     * Evalua si un numero dado es primo o no 
     * @param number numero a ser evaluado
     * @return true si es primo
     */
    public boolean isPrime(int number){
        for(int i=2; i< number; i++)
            if(number % i == 0) return false; //not prime
        return true; //is prime
    }
    
    /**
     * Evalua si un conjunto combinaciones de un numero es ciclico primo o no 
     * @param numbers lista de combinaciones de un numero dado
     * @return true si es ciclico primo, false si no lo es
     */
    public boolean isCiclyc(List<Integer> numbers){
        for (Integer n : numbers){
            if(!isPrime(n))
                return false;
        }
        return true;
    }
    
    /**
     * Rota el numero inicial en cada posición por ejemplo:
     * 1982->9821->8219->2198 
     * impide que al rotar los numero queden combinaciones repetidas ejemplo
     * 11, 110, 101 tambien impide que se vuelvan a evaluar numeros
     * @param digits Cada digito del numero esta en una posición del arreglo
     */
    public void rotateNumber(int number){
        List<int[]> rotatedNumber = new ArrayList<>();
        //se separa el numero en sus dijitos y se guarda cada digito en una
        //posición de un arreglo
        String numberAsString = Integer.toString(number);
        int[] digits = new int[numberAsString.length()];
        for(int i=0; i<Integer.toString(number).length(); i++)
            digits[i]=(Integer.valueOf(numberAsString.substring(i,i+1)));
        //se guarda la rotación orginal del numero
        rotatedNumber.add(digits);
        //se crean todas sus posibles rotaciones
        for(int f = 0; f<digits.length-1; f++){
            int[] rotatedDigits = new int[digits.length];
            for(int i = 0; i<digits.length;i++)
                rotatedDigits[i] = digits[i];
            for (int i = 1; i < rotatedDigits.length ; i++) {
                int y = rotatedDigits[i-1];
                rotatedDigits[i-1] = rotatedDigits[i];
                rotatedDigits[i] = y;
            }
            rotatedNumber.add(rotatedDigits);
        }
        for(int[] list : rotatedNumber){
            int x=0;
            for (int n : list){
                if(n==0 && x!=0)
                    x*=10;
                else if(n!=0)
                    x=(x*10)+n;
            }
            //para que no se repitan combinaciones y que no haya sido evaluado
            if(!toEvaluate.contains(x) && !ciclycNumbers.contains(x))
                toEvaluate.add(x);
        }
    }

    @Override
    public void run() {
        for (int number = 1; number < limit; number++) {
            //si el numero no a sido evaluado aun
            if(!ciclycNumbers.contains(number)){
                rotateNumber(number);
                evalNumber(number);
                toEvaluate = new ArrayList<>(); 
            }
        }
        Collections.sort(ciclycNumbers);
        System.out.println(ciclycNumbers);
    }
}
