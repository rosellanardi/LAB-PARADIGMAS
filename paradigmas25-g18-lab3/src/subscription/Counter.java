package subscription;
import java.io.Serializable;

public final class Counter implements Serializable {
	private static final long serialVersionUID = 1L;
	private int value = 0;
	
	public void increment(){
		value = value + 1;
	}
	public int getValue(){
		return value;
	}
	public void printValue(){
		System.out.println(value);
	}

	public static void main(String[] args) {
	Counter c1 = new Counter();
	Counter c2 = new Counter();
	
	c1.increment();
	c2.printValue();
	}	
}
