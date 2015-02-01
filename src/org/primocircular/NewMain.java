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

/**
 *
 * @author aria Virginia Lucena <mariavirginialu@gmail.com>
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CiclycPrimes cp = new CiclycPrimes(200000);
        System.out.println("Procesando....");
        Thread thread = new Thread(cp); 
        thread.start();
    }
}
