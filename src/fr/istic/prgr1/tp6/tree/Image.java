package fr.istic.prgr1.tp6.tree;

import java.util.Scanner;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * <p>
 * Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 * sous forme d'arbres binaires.
 */

public class Image extends AbstractImage {
    private static final Scanner standardInput = new Scanner(System.in);

    public Image() {
        super();
    }

    public static void closeAll() {
        standardInput.close();
    }

    /**
     * @param x abscisse du point
     * @param y ordonnée du point
     * @return true, si le point (x, y) est allumé dans this, false sinon
     * @pre !this.isEmpty()
     */
    @Override
    public boolean isPixelOn(int x, int y) {
        Iterator<Node> it = this.iterator();
        int finX = 256, milieuX = finX / 2, finY = 256, milieuY = finY / 2;
        //tant qu'on n'est pas sur une feuille on fait un parcours
        while (!it.nodeType().equals(NodeType.LEAF)) {
            //horizontal (carré)
            //la condition suivante vérifie que la figure est un carré
            if (finX == finY) {
                finY = finY / 2;
                //on coupe la figure horizontalement
                //On regarde si Y appartient au rectangle d'en haut ou d'en bas
                if (y < milieuY) {
                    //si Y appartient au rectangle d'en bas on regarde dans la partie inférieure
                    //en diminuant le champ de recherche
                    milieuY -= (finY / 2);
                    it.goLeft();
                } else {
                    milieuY += finY / 2;
                    it.goRight();
                }

            } else {
                finX = finX / 2;
                if (x < milieuX) {
                    milieuX -= (finX / 2);
                    it.goLeft();
                } else {
                    milieuX += (finX / 2);
                    it.goRight();
                }

            }

        }
        return ((it.getValue().state) == 1);
    }

    /**
     * this devient identique à image2.
     *
     * @param image2 image à copier
     * @pre !image2.isEmpty()
     */
    @Override
    public void affect(AbstractImage image2) {
        Iterator<Node> it1 = this.iterator();
        Iterator<Node> it2 = image2.iterator();
        it1.clear();
        affectAux(it1, it2);
    }

    private void affectAux(Iterator<Node> it1, Iterator<Node> it2) {
        it1.addValue(it2.getValue());

        /*Si l'état est du noeud est de "0" ou "1", on va juste cloner le noeud dans this*/
        if (it2.nodeType().equals(NodeType.DOUBLE)) {
            it1.goLeft();
            it2.goLeft();
            affectAux(it1, it2);
            it1.goUp();
            it2.goUp();
            it1.goRight();
            it2.goRight();
            affectAux(it1, it2);
            it1.goUp();
            it2.goUp();
        }
    }

    /**
     * this devient rotation de image2 à 180 degrés.
     *
     * @param image2 image pour rotation
     * @pre !image2.isEmpty()
     */
    @Override
    public void rotate180(AbstractImage image2) {
        Iterator<Node> it1 = this.iterator();
        Iterator<Node> it2 = image2.iterator();
        it1.clear();
        rotate180Aux(it1, it2);
    }

    private void rotate180Aux(Iterator<Node> it1, Iterator<Node> it2) {
        it1.addValue(it2.getValue());
        if (it2.nodeType().equals(NodeType.DOUBLE)) {
            it1.goRight();
            it2.goLeft();
            rotate180Aux(it1, it2);
            it1.goUp();
            it2.goUp();
            it1.goLeft();
            it2.goRight();
            rotate180Aux(it1, it2);
            it1.goUp();
            it2.goUp();
        }

    }

    /**
     * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
     * d'une montre.
     *
     * @param image2 image pour rotation
     * @pre !image2.isEmpty()
     */
    @Override
    public void rotate90(AbstractImage image2) {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println("Fonction non demeand�e");
        System.out.println("-------------------------------------------------");
        System.out.println();
    }

    /**
     * this devient inverse vidéo de this, pixel par pixel.
     *
     * @pre !image.isEmpty()
     */
    @Override
    public void videoInverse() {
        Iterator<Node> it = iterator();
        videoInverseAux(it);
    }

    private void videoInverseAux(Iterator<Node> it) {
        if (!it.isEmpty()) {
            if (it.getValue().state == 1)
                it.setValue(Node.valueOf(0));
            else if (it.getValue().state == 0)
                it.setValue(Node.valueOf(1));
            else {
                it.goRight();
                videoInverseAux(it);
                it.goUp();
                it.goLeft();
                videoInverseAux(it);
                it.goUp();
            }

        }
    }

    /**
     * this devient image miroir verticale de image2.
     *
     * @param image2 image à agrandir
     * @pre !image2.isEmpty()
     */
    @Override
    public void mirrorV(AbstractImage image2) {
        Iterator<Node> it1 = iterator();
        Iterator<Node> it2 = image2.iterator();
        it1.clear();
        mirrorAux(it1, it2, 0);
    }

    /**
     * this devient image miroir horizontale de image2.
     *
     * @param image2 image à agrandir
     * @pre !image2.isEmpty()
     */
    @Override
    public void mirrorH(AbstractImage image2) {
        Iterator<Node> it1 = iterator();
        Iterator<Node> it2 = image2.iterator();
        it1.clear();
        mirrorAux(it1, it2, 1);
    }

    private void mirrorAux(Iterator<Node> it1, Iterator<Node> it2, int niveau) {
        it1.addValue(it2.getValue());
        if (it2.nodeType().equals(NodeType.DOUBLE)) {
            niveau++;
            //Si c'est un niveau pair, dans le cas d'un mirroir horizontal on ajoute juste le noeud dans this
            if (niveau % 2 == 0) {

                it1.goLeft();
                it2.goLeft();
                mirrorAux(it1, it2, niveau);
                it1.goUp();
                it2.goUp();
                it1.goRight();
                it2.goRight();
                mirrorAux(it1, it2, niveau);
                it1.goUp();
                it2.goUp();
            } else {
                //Si c'est un niveau impair on inverse les noeuds

                it1.goLeft();
                it2.goRight();
                mirrorAux(it1, it2, niveau);
                it1.goUp();
                it2.goUp();
                it1.goRight();
                it2.goLeft();
                mirrorAux(it1, it2, niveau);
                it1.goUp();
                it2.goUp();
            }
        }
    }

    /**
     * this devient quart supérieur gauche de image2.
     *
     * @param image2 image à agrandir
     * @pre !image2.isEmpty()
     */
    @Override
    public void zoomIn(AbstractImage image2) {
        //this devient quart supérieur gauche de image2
        Iterator<Node> it = iterator();
        Iterator<Node> it1 = image2.iterator();
        it.clear();
        int cpt = 0;
        while (it1.getValue().state == 2 && cpt < 2) {
            it1.goLeft();
            cpt++;
        }
        affectAux(it, it1);
    }

    /**
     * Le quart supérieur gauche de this devient image2, le reste de this
     * devient éteint.
     *
     * @param image2 image à réduire
     * @pre !image2.isEmpty()
     */
    @Override
    public void zoomOut(AbstractImage image2) {
        Iterator<Node> it = iterator();
        Iterator<Node> it1 = image2.iterator();
        if (it1.getValue().state == 0) {
            it.addValue(it1.getValue());
        } else if (it1.getValue().state == 1) {
            it.addValue(Node.valueOf(2));
            it.goLeft();
            it.addValue(Node.valueOf(2));
            it.goLeft();
            it.addValue(Node.valueOf(1));
        } else {

            it.addValue(Node.valueOf(2));
            it.goRight();
            it.addValue(Node.valueOf(0));
            it.goUp();
            it.goLeft();
            it.addValue(Node.valueOf(2));
            it.goRight();
            it.addValue(Node.valueOf(0));
            it.goUp();
            it.goLeft();
            zoomOutAux(it, it1, 14);
        }


    }

    private void zoomOutAux(Iterator<Node> it, Iterator<Node> it1, int hauteur) {

        if (!it1.isEmpty()) {
            if (hauteur > 0) {
                hauteur--;
                it.addValue(it1.getValue());
                it.goLeft();
                it1.goLeft();
                zoomOutAux(it, it1, hauteur);
                it.goUp();
                it1.goUp();
                it.goRight();
                it1.goRight();
                zoomOutAux(it, it1, hauteur);
                it.goUp();
                it1.goUp();
            } else {
                if (it1.nodeType() == NodeType.LEAF) {
                    it.addValue(it1.getValue());
                } else {
                    it1.goLeft();
                    int leftNode = it1.getValue().state;
                    it1.goUp();
                    it1.goRight();
                    int rightNode = it1.getValue().state;
                    it1.goUp();

                    if ((leftNode == 2 && rightNode == 2)) {
                        it.addValue(Node.valueOf(2));
                    } else if ((leftNode == 2 && rightNode == 0) || (leftNode == 0 && rightNode == 2)) {
                        it.addValue(Node.valueOf(0));
                    } else {
                        it.clear();
                        it.addValue(Node.valueOf(1));
                    }
                }
            }
        }
    }

    /**
     * this devient l'intersection de image1 et image2 au sens des pixels
     * allumés.
     *
     * @param image1 premiere image
     * @param image2 seconde image
     * @pre !image1.isEmpty() && !image2.isEmpty()
     */
    @Override
    public void intersection(AbstractImage image1, AbstractImage image2) {
        Iterator<Node> it1 = iterator();
        Iterator<Node> it2 = image1.iterator();
        Iterator<Node> it3 = image2.iterator();
        it1.clear();
        intersectionAux(it1, it2, it3);
    }

    private void intersectionAux(Iterator<Node> it1, Iterator<Node> it2, Iterator<Node> it3) {
        if (!it2.isEmpty() && !it3.isEmpty()) {
            if (it2.getValue().state == 0 || it3.getValue().state == 0) {
                it1.addValue(Node.valueOf(0));

            } else if (it2.getValue().state == 2 && it3.getValue().state == 2) {
                it1.addValue(Node.valueOf(2));
                it1.goLeft();
                it2.goLeft();
                it3.goLeft();
                intersectionAux(it1, it2, it3);
                int i = it1.getValue().state;
                it1.goUp();
                it2.goUp();
                it3.goUp();
                it1.goRight();
                it2.goRight();
                it3.goRight();
                intersectionAux(it1, it2, it3);
                int j = it1.getValue().state;
                it1.goUp();
                it2.goUp();
                it3.goUp();

                //Si le noeud a deux fils à 0 on  remonte le 0 a la racine en supprimant le sous arbre correspondant
                if (i == 0 && j == 0) {
                    it1.clear();
                    it1.addValue(Node.valueOf(0));
                }

            } else if ((it2.getValue().state == 1) && (it3.getValue().state == 1)) {
                it1.addValue(Node.valueOf(1));
                /*Dans les autres cas (2,1 ou 1,2), le noeud va prendre l'état 2*/
            } else {
                /*Dans le cas où c'est le noeud de l'image 1 qui est à l'état 1*/
                if (it2.getValue().state == 1) {
                    affectAux(it1, it3);
                } else {
                    affectAux(it1, it2);
                }
            }
        }
    }

    /**
     * this devient l'union de image1 et image2 au sens des pixels allumés.
     *
     * @param image1 premiere image
     * @param image2 seconde image
     * @pre !image1.isEmpty() && !image2.isEmpty()
     */
    @Override
    public void union(AbstractImage image1, AbstractImage image2) {
        Iterator<Node> it1 = iterator();
        Iterator<Node> it2 = image1.iterator();
        Iterator<Node> it3 = image2.iterator();

        it1.clear();
        unionAux(it1, it2, it3);

    }

    private void unionAux(Iterator<Node> it1, Iterator<Node> it2, Iterator<Node> it3) {
        if (!it2.isEmpty() && !it3.isEmpty()) {
            /*On va traiter les différents cas possibles rencontrés lors de l'union des arbres*/
            /*Si les deux noeuds sont dans l'état 0, alors le noeud union prend l'état 0*/
            /*Si les deux noeuds sont dans l'état 2, alors le noeud union prend l'état 2*/
            /*Si l'un des noeuds est dans l'état 1, alors le noeud union prend l'état 1*/
            if (it2.getValue().state == 0 && it3.getValue().state == 0) {
                it1.addValue(Node.valueOf(0));
            } else if (it2.getValue().state == 2 && it3.getValue().state == 2) {
                it1.addValue(Node.valueOf(2));
                it1.goLeft();
                it2.goLeft();
                it3.goLeft();
                unionAux(it1, it2, it3);
                int i = it1.getValue().state;
                it1.goUp();
                it2.goUp();
                it3.goUp();
                it1.goRight();
                it2.goRight();
                it3.goRight();
                unionAux(it1, it2, it3);
                int j = it1.getValue().state;
                it1.goUp();
                it2.goUp();
                it3.goUp();
                //Si le noeud a deux fils à 1 on  remonte le 1 a la racine en supprimant le sous arbre correspondant
                if (i == 1 && j == 1) {
                    it1.clear();
                    it1.addValue(Node.valueOf(1));
                }
            } else if ((it2.getValue().state == 1) || (it3.getValue().state == 1)) {
                it1.addValue(Node.valueOf(1));
                if (it2.getValue().state != 1)
                    it2.clear();
                else
                    it3.clear();
                /*Dans les autres cas (2,0 ou 0,2), le noeud va prendre l'état 2*/
            } else {
                /*Dans le cas où c'est le noeud de l'image 1 qui est à l'état 0*/
                if (it2.getValue().state == 0) {
                    affectAux(it1, it3);
                } else {
                    affectAux(it1, it2);
                }
            }

        }
    }


    /**
     * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
     *
     * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
     * sont allumés dans this, false sinon
     */
    @Override
    public boolean testDiagonal() {
        Iterator<Node> it = iterator();


        return testDiagonalAux(it);

    }

    private boolean testDiagonalAux(Iterator<Node> it) {
        boolean leftDiagonalOn = false;
        boolean rightDiagonalOn = false;
        if (it.nodeType().equals(NodeType.LEAF)) {
            return it.getValue().state == 1;
        } else {
            it.goLeft();
            if (it.nodeType().equals(NodeType.LEAF)) {
                leftDiagonalOn = it.getValue().state == 1;
            } else {
                it.goLeft();
                leftDiagonalOn = testDiagonalAux(it);
                it.goUp();
            }
            it.goUp();
            if (leftDiagonalOn) {
                it.goRight();
                if (it.nodeType().equals(NodeType.LEAF)) {
                    rightDiagonalOn = it.getValue().state == 1;
                } else {
                    it.goRight();
                    rightDiagonalOn = testDiagonalAux(it);
                    it.goUp();
                }
                it.goUp();
            }
        }
        return leftDiagonalOn && rightDiagonalOn;
    }


    /**
     * @param x1 abscisse du premier point
     * @param y1 ordonnée du premier point
     * @param x2 abscisse du deuxième point
     * @param y2 ordonnée du deuxième point
     * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
     * la même feuille de this, false sinon
     * @pre !this.isEmpty()
     */
    @Override
    public boolean sameLeaf(int x1, int y1, int x2, int y2) {
        Iterator<Node> it = this.iterator();
        boolean isOnSameLeaf = true;
        int maxX = 256, milieuX = maxX / 2, maxY = 256, milieuY = maxY / 2;
        while (!it.nodeType().equals(NodeType.LEAF) && isOnSameLeaf) {
            if (maxX == maxY) {
                maxY = maxY / 2;
                if (y1 < milieuY && y2 < milieuY) {
                    milieuY -= maxY/2;
                    it.goLeft();
                } else if (y1 >= milieuY && y2 >= milieuY) {
                    milieuY += maxY/2;
                    it.goRight();
                } else {
                    isOnSameLeaf = false;
                }
            } else {
                maxX = maxX / 2;
                if (x1 < milieuX && x2 < milieuX) {
                    milieuX -= maxX/2;
                    it.goLeft();
                } else if (x1 >= milieuX && x2 >= milieuX ) {
                    milieuX += maxX/2;
                    it.goRight();
                } else {
                    isOnSameLeaf = false;
                }
            }
        }
        return isOnSameLeaf;
    }

    /**
     * @param image2 autre image
     * @return true si this est incluse dans image2 au sens des pixels allumés
     * false sinon
     * @pre !this.isEmpty() && !image2.isEmpty()
     */
    @Override
    public boolean isIncludedIn(AbstractImage image2) {
        Iterator<Node> it1 = iterator();
        Iterator<Node> it2 = image2.iterator();
        return isIncludedInAux(it1, it2);
    }

    private boolean isIncludedInAux(Iterator<Node> it1, Iterator<Node> it2) {
        //SI it1 ou it2 sont à 0 ou 1 on ne fait rien sinon on parcours pour chercher l'inclusion
        if (!(it1.getValue().state == 0 || it2.getValue().state == 1)) {
            if (it1.getValue().state == 2 && it2.getValue().state == 2) {
                it1.goLeft();
                it2.goLeft();
                if (isIncludedInAux(it1, it2)) {
                    it1.goUp();
                    it2.goUp();
                    it1.goRight();
                    it2.goRight();
                    if (!isIncludedInAux(it1, it2)) {
                        return false;
                    }
                } else {
                    return false;
                }
                it1.goUp();
                it2.goUp();
            } else {
                return false;
            }
        }
        return true;
    }

}

