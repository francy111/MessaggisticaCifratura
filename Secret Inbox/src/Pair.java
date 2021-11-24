/**
 * Classe coppia parametrica
 * @author francy111
 *
 * @param <T> Tipo del primo elemento della coppia
 * @param <K> Tipo del secondo elemento della coppia
 */
public class Pair<T, K> {
	/**
	 * Primo elemento della coppia
	 */
	private T primo;
	/**
	 * Secondo elemento della coppia
	 */
	private K secondo;
	
	/**
	 * Costruttore
	 * @param f Primo elemento della coppia
	 * @param s Secondo elemento della coppia
	 */
	public Pair(T f, K s) {
		setPrimo(f);
		setSecondo(s);
	}

	/**
	 * Restituisce il primo elemento
	 * @return Primo elemento della coppia
	 */
	public T getPrimo() {
		return primo;
	}

	/**
	 * Imposta il primo elemento della coppia
	 * @param primo Elemento da impostare come primo della coppia
	 */
	public void setPrimo(T primo) {
		this.primo = primo;
	}

	/**
	 * Restituisce il secondo elemento
	 * @return Secondo elemento della coppia
	 */
	public K getSecondo() {
		return secondo;
	}

	/**
	 * Imposta il secondo elemento della coppia
	 * @param primo Elemento da impostare come secondo della coppia
	 */
	public void setSecondo(K secondo) {
		this.secondo = secondo;
	}
	
}
