package fr.istic.prgr1.tp3;

public class Fourmis {

        private static String next(String ui) {
            StringBuilder next = new StringBuilder();
            int longSeq = 1;
            char carSeq = ui.charAt(0);

            for (int i=1 ; i < ui.length(); i++) {
                if (carSeq == ui.charAt(i)) {
                    longSeq++;
                } else {
                    next.append(longSeq).append(carSeq);
                    longSeq = 1;
                    carSeq = ui.charAt(i);
                }

            }
            next.append(longSeq).append(ui.charAt(ui.length()-1));
            return next.toString();
        }
/*    private static String next(String ui) {
        *//*On initie la longueur de la première séquence de chiffres à 1 et le premier caractère de la séquence à l'élément 0 du String ui*//*
        int lgseq = 1;
        char carseq = ui.charAt(0);
        *//* On va construire progressivement la chaîne next par concaténation, qui donnera le résultat de l'élément suivant de la suite *//*
        StringBuilder next = new StringBuilder();

        *//*On exécute la boucle for tant que strictement inférieur à la longueur du String pour éviter de déborder *//*
        for (int i = 1; i < ui.length(); i++) {
            if (ui.charAt(i) == carseq) {
                lgseq++;
            } else {
                next.append(lgseq).append(carseq);
                lgseq = 1;
                carseq = ui.charAt(i);
            }
        }
        *//*On traite le cas limite de la boucle for en ajoutant la dernière séquence du String ui*//*
        next.append(lgseq).append(ui.charAt(ui.length() - 1));

        return next.toString();
    }*/

    public static void main(String[] args) {
        /*On exécute la suite sur 10 tours*/
        String suite = "1";
        for (int i = 1; i <= 10; i++) {
            suite = next(suite);
            System.out.println("Element u" + i + " = " + suite);
        }
    }
}
