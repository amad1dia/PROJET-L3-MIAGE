package fr.istic.prgr1.tp5;

import fr.istic.prg1.list_util.Iterator;
import fr.istic.prg1.list_util.SuperT;

/**
 * @author Amadou DIA & Estelle ABRAHAM
 */

public class List<T extends SuperT> {
	// liste en double chainage par references

	private class Element {
		// element de List<Item> : (Item, Element, Element)
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}
	} // class Element

	public class ListIterator implements Iterator<T> {
		private Element current;

		private ListIterator() {
			current = flag.right;
		}

		@Override
		public void goForward() {
			current = current.right;
		}

		@Override
		public void goBackward() { 
			current = current.left;
		}

		@Override
		public void restart() {
			flag.right = current;
		}

		@Override
	    public boolean isOnFlag() { 
			return current == flag;
		}

		@Override
		public void remove() {
			try {
				assert current != flag : "\n\n\nimpossible de retirer le drapeau\n\n\n";
				Element leftValue = current.left;
				Element rightValue = current.right;
				leftValue.right = rightValue;
				rightValue.left = leftValue;

				current = rightValue;

//				Element leftNeighbor = current.left;
//				Element rightNeighbor = current.right;
//				leftNeighbor.right = rightNeighbor;
//				rightNeighbor.left = leftNeighbor;
//				current = rightNeighbor;

			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		@Override		 
		public T getValue() {
			return this.current.value;
		}

		@Override
	    public T nextValue() {
			this.goForward();
			return this.getValue();
	    }

		@Override
		public void addLeft(T v) {
			Element element = new Element();
			element.value = v;
			element.right = current;
			element.left = current.left;
			current.left = element;
			current.left.right = element;
		}

		@Override
		public void addRight(T v) {
			this.goForward();
			this.addLeft(v);
		}

		@Override
		public void setValue(T v) {
			this.current.value = v;
		}

		@Override
		public String toString() {
			return "parcours de liste : pas d'affichage possible \n";
		}

	} // class IterateurListe

	private Element flag;

	public List() {
		flag = new Element();
		flag.left = flag;
		flag.right = flag;

	}

	public ListIterator iterator() { 
		return new ListIterator(); 
	}

	public boolean isEmpty() {
		return this.flag.left == this.flag;
	}

	public void clear() {
		flag.right = flag;
		flag.left = flag;
	}

	public void setFlag(T v) {
		this.flag.value = v;
	}

	public void addHead(T v) {
		Iterator<T> iterator = iterator();
		iterator.addLeft(v);
	}

	public void addTail(T v) {
		Iterator<T> iterator = iterator();
		iterator.goBackward();
		iterator.addLeft(v);
	}

	@SuppressWarnings("unchecked")
	public List<T> clone() {
		List<T> nouvListe = new List<T>();
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			nouvListe.addTail((T) p.getValue().clone());
			// UNE COPIE EST NECESSAIRE !!!
			p.goForward();
		}
		return nouvListe;
	}

	@Override
	public String toString() {
		String s = "contenu de la liste : \n";
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			s = s + p.getValue().toString() + " ";
			p.goForward();
		}
		return s;
	}
}
