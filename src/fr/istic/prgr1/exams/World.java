package fr.istic.prgr1.exams;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.NodeType;
import fr.istic.prgr1.tp6.tree.BinaryTree;


public class World extends BinaryTree<Country> {

    public void addCountry(Country country) {

        if (!exists(country)) {
            Iterator<Country> it = iterator();
            while (!it.isEmpty()) {
                Country currentCountry = it.getValue();
                currentCountry.total += 1;
                int comparison = country.name.compareTo(currentCountry.name);
                if (comparison < 0) {
                    it.goLeft();
                } else {
                    it.goRight();
                }
            }
            country.total += 1;
            it.addValue(country);

        } else
            System.out.println("Pays deja existant");
    }


    public boolean exists(Country country) {

        Iterator<Country> it = iterator();

        while (!it.isEmpty()) {
            Country thisCountry = it.getValue();
            int comparison = country.name.compareTo(thisCountry.name);
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                it.goLeft();
            } else {
                it.goRight();
            }
        }
        return false;

    }


    public void printNodeOfDepth(int depth) {
        Iterator<Country> it = iterator();
        printNodeOfDepthAux(it, depth);

    }

    private void printNodeOfDepthAux(Iterator<Country> it, int depth) {
        if (!it.isEmpty()) {
            if (depth > 0) {
                it.goRight();
                printNodeOfDepthAux(it, depth - 1);
                it.goUp();
                it.goLeft();
                printNodeOfDepthAux(it, depth - 1);
                it.goUp();

            } else {
                System.out.print(it.getValue() + "\t");

            }
        }
    }
    boolean isPresent(int deb, int fin) {
        Iterator<Country> it = A.newIterator();
        int cas = 0;
        while(it.nodeType() != NodeType.FEUILLE){
            cas = getCas(deb, fin, it);
            switch(cas){
                case 1 :
                    if(it.nodeType() != NodeType.SIMPLED)
                        it.goLeft();
                    else
                        return false
                    break;
                case 2 :
                    if(it.nodeType() != NodeType.SIMPLEG)
                        it.goRight();
                    else
                        return false;
                    break;
                case 3 :
                    return true;
            }
        }
        if(it.getValue().valeur > deb && it.getValue().valeur < fin)
            return true;
        return false;
    }

    private int getCas(int deb, int fin, Iterator<Country> it) {
        int cas;
        if(it.getValue().total > fin) cas=1;
        else if(it.getValue().total < deb) cas=2;
        else cas = 3;
        return cas;
    }

}

    public Country largestCountry() {
        return largestCountryAux(this.iterator());
    }

    private Country largestCountryAux(Iterator<Country> it) {
        Country leftMax, rightMax;
        if (it.isEmpty()) {
            return null;
        }
        Country max = it.getValue();
        it.goLeft();
        leftMax = largestCountryAux(it);
        it.goUp();
        it.goRight();
        rightMax = largestCountryAux(it);
        it.goUp();
        if (leftMax != null && leftMax.surface > max.surface)
            max = leftMax;
        if (rightMax != null && rightMax.surface > max.surface)
            max = rightMax;
        return max;
    }

    public static void main(String[] args) {
        Country france = new Country("France", 100, 0);
        Country australie = new Country("Autralie", 11, 0);
        Country leichtenstein = new Country("Leichtenstein", 70, 0);

        Country senegal = new Country("Senégal", 90, 0);
        Country coteIvoire = new Country("Cote Ivoire", 70, 0);
        Country laos = new Country("Laos", 70, 0);
        Country syldavie = new Country("Syldavie", 70, 0);
        Country bordurie = new Country("Bordurie", 70, 0);
        Country danemark = new Country("Danemark", 70, 0);
        Country tchetchenie = new Country("Tchetchenie", 170, 0);

        World world = new World();
        world.addCountry(france);
        world.addCountry(australie);
        world.addCountry(leichtenstein);
        world.addCountry(coteIvoire);
        world.addCountry(bordurie);
        world.addCountry(danemark);
        world.addCountry(laos);
        world.addCountry(syldavie);
        world.addCountry(senegal);
        world.addCountry(tchetchenie);

        for (int i = 0; i < 9; i++) {
            world.printNodeOfDepth(i);
            System.out.println();
        }

        System.out.println(world.largestCountry());

    }


}
