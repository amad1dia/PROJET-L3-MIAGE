package fr.istic.prgr1.tp5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import fr.istic.prg1.list_util.Comparison;
import fr.istic.prg1.list_util.Iterator;

import fr.istic.prg1.list_util.SmallSet;
import fr.istic.prg1.list_util.List;


/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2018-10-02
 */

public class MySet extends List<SubSet> {

    /**
     * Borne supérieure pour les rangs des sous-ensembles.
     */
    private static final int MAX_RANG = 128;
    /**
     * Sous-ensemble de rang maximal à mettre dans le drapeau de la liste.
     */
    private static final SubSet FLAG_VALUE = new SubSet(MAX_RANG,
            new SmallSet());
    /**
     * Entrée standard.
     */
    private static final Scanner standardInput = new Scanner(System.in);

    public MySet() {
        super();
        setFlag(FLAG_VALUE);
    }

    /**
     * Fermer tout (actuellement juste l'entrée standard).
     */
    public static void closeAll() {
        standardInput.close();
    }

    private static Comparison compare(int a, int b) {
        if (a < b) {
            return Comparison.INF;
        } else if (a == b) {
            return Comparison.EGAL;
        } else {
            return Comparison.SUP;
        }
    }

    /**
     * Afficher à l'écran les entiers appartenant à this, dix entiers par ligne
     * d'écran.
     */
    public void print() {
        System.out.println(" [version corrigee de contenu]");
        this.print(System.out);
    }

    // //////////////////////////////////////////////////////////////////////////////
    // //////////// Appartenance, Ajout, Suppression, Cardinal
    // ////////////////////
    // //////////////////////////////////////////////////////////////////////////////

    /**
     * Ajouter à this toutes les valeurs saisies par l'utilisateur et afficher
     * le nouveau contenu (arrêt par lecture de -1).
     */
    public void add() {
        System.out.println(" valeurs a ajouter (-1 pour finir) : ");
        this.add(System.in);
        System.out.println(" nouveau contenu :");
        this.printNewState();
    }

    /**
     * Ajouter à this toutes les valeurs prises dans is.
     * C'est une fonction auxiliaire pour add() et restore().
     *
     * @param is flux d'entrée.
     */
    public void add(InputStream is) {
        Scanner scanner = new Scanner(is);
        int x = scanner.nextInt();
        while (x != -1) {
            addNumber(x);
            x = scanner.nextInt();
        }
    }

    /**
     * Ajouter value à this.
     *
     * @param value valuer à ajouter.
     */
    public void addNumber(int value) {
        int rank = value / 256;
        int modulo = value % 256;

        Iterator<SubSet> it = this.iterator();
        SubSet s = it.getValue();
        int thisRank = s.rank;
        while (thisRank < rank) {
            it.goForward();
            s = it.getValue();
            thisRank = s.rank;
        }
        switch (compare(thisRank, rank)) {
            case EGAL:
                s.set.add(modulo);
                break;
            case SUP:
                SmallSet smallSet = new SmallSet();
                smallSet.add(modulo);
                it.addLeft(new SubSet(rank, smallSet));
                break;
        }

    }

    /**
     * Supprimer de this toutes les valeurs saisies par l'utilisateur et
     * afficher le nouveau contenu (arrêt par lecture de -1).
     */
    public void remove() {
        System.out.println("  valeurs a supprimer (-1 pour finir) : ");
        this.remove(System.in);
        System.out.println(" nouveau contenu :");
        this.printNewState();
    }

    /**
     * Supprimer de this toutes les valeurs prises dans is.
     *
     * @param is flux d'entrée
     */
    public void remove(InputStream is) {
        Scanner scanner = new Scanner(is);
        int x = scanner.nextInt();
        while (x != -1) {
            removeNumber(x);
            x = scanner.nextInt();
        }
    }

    /**
     * Supprimer value de this.
     *
     * @param value valeur à supprimer
     */
    public void removeNumber(int value) {
        int rank = value / 256;
        int modulo = value % 256;

        Iterator<SubSet> it = this.iterator();
        SubSet s = it.getValue();
        while (s.rank < rank) {
            it.goForward();
            s = it.getValue();
        }

        if (s.rank == rank) {
            s.set.remove(modulo);
            if (s.set.isEmpty())
                it.remove();
        }

    }

    /**
     * @return taille de l'ensemble this
     */
    public int size() {
        int taille = 0;
        Iterator<SubSet> it = iterator();
        while (!it.isOnFlag()) {
            taille += it.getValue().set.size();
            it.goForward();
        }
        return taille;
    }


    /**
     * @return true si le nombre saisi par l'utilisateur appartient à this,
     * false sinon
     */
    public boolean contains() {
        System.out.println(" valeur cherchee : ");
        int value = readValue(standardInput, 0);
        return this.contains(value);
    }

    /**
     * @param value valeur à tester
     * @return true si valeur appartient à l'ensemble, false sinon
     */

    public boolean contains(int value) {
        int modulo = value % 256;
        int rank = value / 256;
        Iterator<SubSet> it = iterator();
        SubSet s = it.getValue();
        while (s.rank < rank) {
            it.goForward();
            s = it.getValue();
        }
        return s.rank == rank && s.set.contains(modulo);
    }

    // /////////////////////////////////////////////////////////////////////////////
    // /////// Difference, DifferenceSymetrique, Intersection, Union ///////
    // /////////////////////////////////////////////////////////////////////////////

    /**
     * This devient la différence de this et set2.
     *
     * @param set2 deuxième ensemble
     */
    public void difference(MySet set2) {
        if (this == set2)
            this.clear();
        else {
            Iterator<SubSet> it1 = iterator();
            Iterator<SubSet> it2 = set2.iterator();

            while (!it1.isOnFlag() && !it2.isOnFlag()) {
                SubSet s1 = it1.getValue();
                SubSet s2 = it2.getValue();

                if (s1.rank == s2.rank) {
                    s1.set.difference(s2.set);
                    if (s1.set.isEmpty()) {
                        it1.remove();
                    } else {
                        it1.goForward();
                    }
                    it2.goForward();
                } else if (s1.rank < s2.rank) {
                    it1.goForward();
                } else {
                    it2.goForward();
                }
            }
        }
    }

    /**
     * This devient la différence symétrique de this et set2.
     *
     * @param set2 deuxième ensemble
     */
    public void symmetricDifference(MySet set2) {
        if (this == set2) {
            this.clear();
        } else {
            Iterator<SubSet> it1 = iterator();
            Iterator<SubSet> it2 = set2.iterator();

            while (!it2.isOnFlag()) {
                SubSet s1 = it1.getValue();
                SubSet s2 = it2.getValue();
                if (s1.rank == s2.rank) {
                    s1.set.symmetricDifference(s2.set);
                    if (s1.set.isEmpty()) {
                        it1.remove();
                    } else {
                        it1.goForward();
                    }
                    it2.goForward();
                } else if (s1.rank < s2.rank) {
                    it1.goForward();
                } else {
                    it1.addLeft(s2.clone());
                    it1.goForward();
                    it2.goForward();
                }
            }
        }
    }

    /**
     * This devient l'intersection de this et set2.
     *
     * @param set2 deuxième ensemble
     */
    public void intersection(MySet set2) {

        if (this != set2) {
            Iterator<SubSet> it1 = iterator();
            Iterator<SubSet> it2 = set2.iterator();

            while (!it1.isOnFlag()) {
                SubSet s1 = it1.getValue();
                SubSet s2 = it2.getValue();

                if (s1.rank == s2.rank) {
                    s1.set.intersection(s2.set);
                    if (s1.set.isEmpty()) {
                        it1.remove();
                    } else {
                        it1.goForward();
                    }
                    it2.goForward();

                } else if (s1.rank < s2.rank) {
                    it1.remove();
                } else {
                    it2.goForward();
                }
            }
        }
    }

    /**
     * This devient l'union de this et set2.
     *
     * @param set2 deuxième ensemble
     */
    public void union(MySet set2) {

        Iterator<SubSet> it1 = iterator();
        Iterator<SubSet> it2 = set2.iterator();

        while (!it2.isOnFlag()) {
            SubSet s1 = it1.getValue();
            SubSet s2 = it2.getValue();

            if (s1.rank == s2.rank) {
                s1.set.union(s2.set);
                it1.goForward();
                it2.goForward();
            } else if (s1.rank < s2.rank) {
                it1.goForward();
            } else {
                it1.addLeft(s2.clone());
                it1.goForward();
                it2.goForward();
            }
        }

    }

    // /////////////////////////////////////////////////////////////////////////////
    // /////////////////// Egalitï¿½, Inclusion ////////////////////
    // /////////////////////////////////////////////////////////////////////////////

    /**
     * @param o deuxième ensemble
     * @return true si les ensembles this et o sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        boolean b = true;
        if (this == o) {
            b = true;
        } else if (o == null) {
            b = false;
        } else if (!(o instanceof MySet)) {
            b = false;
        } else {
            MySet set2 = (MySet) o;
            Iterator<SubSet> it1 = iterator();
            Iterator<SubSet> it2 = set2.iterator();

            while (it1.getValue().rank == it2.getValue().rank
                    && it1.getValue().set.equals(it2.getValue().set)
                    && !it1.isOnFlag() && !it2.isOnFlag()) {
                it1.goForward();
                it2.goForward();
            }
            b = it1.isOnFlag() && it2.isOnFlag();
        }

        return b;
    }

    /**
     * @param set2 deuxième ensemble
     * @return true si this est inclus dans set2, false sinon
     */
    public boolean isIncludedIn(MySet set2) {
        if (this == set2)
            return  true;
        boolean included = true;
        Iterator<SubSet> it1 = iterator();
        Iterator<SubSet> it2 = set2.iterator();

        while (it1.getValue().rank >= it2.getValue().rank && included & !it2.isOnFlag()) {
            if (it1.getValue().rank == it2.getValue().rank) {
                included = it1.getValue().set.isIncludedIn(it2.getValue().set);
                it1.goForward();
                it2.goForward();
            } else {
                it2.goForward();
            }
        }
        return included && it1.isOnFlag();
    }

    // /////////////////////////////////////////////////////////////////////////////
    // //////// Rangs, Restauration, Sauvegarde, Affichage //////////////
    // /////////////////////////////////////////////////////////////////////////////

    /**
     * Afficher les rangs présents dans this.
     */
    public void printRanks() {
        System.out.println(" [version corrigee de rangs]");
        this.printRanksAux();
    }

    private void printRanksAux() {
        int count = 0;
        System.out.println(" Rangs presents :");
        Iterator<SubSet> it = this.iterator();
        while (!it.isOnFlag()) {
            System.out.print("" + it.getValue().rank + "  ");
            count = count + 1;
            if (count == 10) {
                System.out.println();
                count = 0;
            }
            it.goForward();
        }
        if (count > 0) {
            System.out.println();
        }
    }

    /**
     * Créer this à partir d'un fichier choisi par l'utilisateur contenant une
     * séquence d'entiers positifs terminée par -1 (cf f0.ens, f1.ens, f2.ens,
     * f3.ens et f4.ens).
     */
    public void restore() {
        String fileName = readFileName();
        InputStream inFile;
        try {
            inFile = new FileInputStream(fileName);
            System.out.println(" [version corrigee de restauration]");
            this.clear();
            this.add(inFile);
            inFile.close();
            System.out.println(" nouveau contenu :");
            this.printNewState();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("fichier " + fileName + " inexistant");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("probleme de fermeture du fichier " + fileName);
        }
    }

    /**
     * Sauvegarder this dans un fichier d'entiers positifs terminé par -1.
     */
    public void save() {
        System.out.println(" [version corrigee de sauvegarde]");
        OutputStream outFile;
        try {
            outFile = new FileOutputStream(readFileName());
            this.print(outFile);
            outFile.write("-1\n".getBytes());
            outFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("pb ouverture fichier lors de la sauvegarde");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("probleme de fermeture du fichier");
        }
    }

    /**
     * @return l'ensemble this sous forme de chaîne de caractères.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int count = 0;
        SubSet subSet;
        int startValue;
        Iterator<SubSet> it = this.iterator();
        while (!it.isOnFlag()) {
            subSet = it.getValue();
            startValue = subSet.rank * 256;
            for (int i = 0; i < 256; ++i) {
                if (subSet.set.contains(i)) {
                    String number = String.valueOf(startValue + i);
                    int numberLength = number.length();
                    for (int j = 6; j > numberLength; --j) {
                        number += " ";
                    }
                    result.append(number);
                    ++count;
                    if (count == 10) {
                        result.append("\n");
                        count = 0;
                    }
                }
            }
            it.goForward();
        }
        if (count > 0) {
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Imprimer this dans outFile.
     *
     * @param outFile flux de sortie
     */
    private void print(OutputStream outFile) {
        try {
            String string = this.toString();
            outFile.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Afficher l'ensemble avec sa taille et les rangs présents.
     */
    private void printNewState() {
        this.print(System.out);
        System.out.println(" Nombre d'elements : " + this.size());
        this.printRanksAux();
    }

    /**
     * @param scanner
     * @param min     valeur minimale possible
     * @return l'entier lu au clavier (doit Ãªtre entre min et 32767)
     */
    private static int readValue(Scanner scanner, int min) {
        int value = scanner.nextInt();
        while (value < min || value > 32767) {
            System.out.println("valeur incorrecte");
            value = scanner.nextInt();
        }
        return value;
    }

    /**
     * @return nom de fichier saisi psar l'utilisateur
     */
    private static String readFileName() {
        System.out.print(" nom du fichier : ");
        String fileName = standardInput.next();
        return fileName;
    }
}
