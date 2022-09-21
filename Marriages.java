/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marriages;

/**
 *
 * @author paria
 */
import java.util.Arrays;
import java.util.Random;

public class Marriages {

    public static void main(String[] args) {
        
 for (int n = 0; n < 100; n += 1) {
        Population village = Population.newRandom(10);
        Population.Marriage marriage = village.galeShapelyMarriage();
            if (marriage.isStable()) {
                System.out.println("Marriage " + n + " is stable.");
            } else {
                throw new IllegalStateException("Marriage "+n+" is not stable!");
            }
         }
    }
    
    
    
static class Population {
    int[][] boysPreferences; // boysPreferences[b][i] is the ith choice of boy b
    int[][] girlsPreferences; // girlsPreferences[g][i] is the ith choice of girl g

        static Population newRandom(int n) {
            Population pop = new Population();
            pop.boysPreferences = new int[n][n];
            pop.girlsPreferences = new int[n][n];
            for (int b = 0; b < n; b += 1) {
                for (int i = 0; i < n; i += 1) {
                    pop.boysPreferences[b][i] = i;
                    pop.girlsPreferences[b][i] = i;
                }
                randomize(pop.boysPreferences[b]);
                randomize(pop.girlsPreferences[b]);
            }
            
            return pop;
        }

        
class Marriage {
    int[] wives;

   int husbandOfGirl(int g) {
                //changed  <= to < in for-loop due to out of bounds exception
                for (int b = 0; b < wives.length; b += 1) {
                    if (wives[b]==g) return b;
                }
                return -1;// return -1 if girl is single
            }

                 int wifeOfBoy(int b) {
                return wives[b];
            }
            // Brute force approach
            boolean isStable() {
                
                int numPeople = boysPreferences[0].length;
                boolean woman_pref_i; // true if woman prefers ith man over current partner
                boolean man_pref_j;   // true if man prefers jth woman over current partner
                for(int i = 0; i < numPeople;i++){
                    for(int j = 0; j < numPeople;j++){
                        if(wives[i] != j){
                          woman_pref_i = higherPrefWomen(husbandOfGirl(j), i, j); 
                          man_pref_j = higherPrefMen(wifeOfBoy(i), j, i);
                          if(woman_pref_i && man_pref_j)
                              return false;
                        }
                    }
                }
                return true;
            }
        }

    Marriage galeShapelyMarriage() {


            int N = boysPreferences.length; //Number of boys or girls
            int engagedCount = 0; // Number couples
            Marriage menPartners = new Marriage();
            menPartners.wives = new int[N];


            Arrays.fill(menPartners.wives, -1);

            while(engagedCount < N){
            int free;
            for (free = 0; free < N; free++)
                if (menPartners.wifeOfBoy(free) == -1)
                        break;

            for (int i = 0; i < N && (menPartners.wifeOfBoy(free) == -1); i++){
                int woman = boysPreferences[free][i];
                if(menPartners.husbandOfGirl(woman) == -1){
                         menPartners.wives[free] = woman;
                         engagedCount++; 
                     }
                else{
                         //wpartner is current husband
                         //free is new proposer
                            int wpartner = menPartners.husbandOfGirl(woman);
                            if(higherPrefWomen(wpartner, free, woman)){
                                menPartners.wives[free] = woman;
                                menPartners.wives[wpartner] = -1;

                            }
                     }

                 }

              }

            return menPartners;
            }


            //return true if the woman prefers the new man
            private boolean higherPrefWomen(int currentMan, int newMan, int woman){

                    int N = boysPreferences.length;
                    for (int i = 0; i < N; i++){
                        if (girlsPreferences[woman][i] == newMan)
                            return true;
                        if (girlsPreferences[woman][i] == currentMan)
                            return false;
                    }
                    return false;
            }
            
            //return true if the man prefers the new woman
             private boolean higherPrefMen(int currentWoman, int newWoman, int man){

                    int N = boysPreferences.length;
                    for (int i = 0; i < N; i++){
                        if (boysPreferences[man][i] == newWoman)
                            return true;
                        if (boysPreferences[man][i] == currentWoman)
                            return false;
                    }
                    return false;
            }
            

        static void swap(int[] a, int i, int j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
        static void randomize(int[] a) {
            Random random = new Random();
            for (int i = 1; i < a.length; i += 1) {
                swap(a, i, random.nextInt(i));
            }
        }

    }
}