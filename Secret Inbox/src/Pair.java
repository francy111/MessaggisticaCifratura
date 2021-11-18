/**
 * Classe coppia parametrica
 * @author francy111
 *
 * @param <T> Tipo del primo elemento della coppia
 * @param <K> Tipo del secondo elemento della coppia
 */
public class Pair<T, K> {
	private T primo;
	private K secondo;
	
	public Pair(T f, K s) {
		setPrimo(f);
		setSecondo(s);
	}

	public T getPrimo() {
		return primo;
	}

	public void setPrimo(T primo) {
		this.primo = primo;
	}

	public K getSecondo() {
		return secondo;
	}

	public void setSecondo(K secondo) {
		this.secondo = secondo;
	}
	
}
