import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;

/*
 *  Desc: This class generates primes, twin primes, and hexagon crosses using BigInteger data types.
 */
public class Primes {
	
	// Pair class implementation.
	private class Pair<T> {
		// Key: first T element of pair
		// Value: second T element of pair
		private T key;
		private T value;
		
		public Pair(T pair_one, T pair_two) {
			this.key = pair_one;
			this.value = pair_two;
		}
		
		// Getters and setters of pair
		public T getKey() { return this.key; }
		public T getValue() { return this.value; }
		
		public void setKey(T new_key) {
			this.key = new_key;
		}
		
		public void setValue(T new_value) {
			this.value = new_value;
		}
		
		// toString of Pair class for easy outputting
		public String toString() {
			return this.getKey() + ", " + this.getValue();
		}
	}
	
	// Member variables for containing out lists of integers, twin primes, hexagon crosses, and the pairs of twin primes that make up the hex crosses.
	private List<BigInteger> primes = new ArrayList<>();
	private List<Pair<BigInteger>> twin_primes = new ArrayList<>();
	private List<Pair<Pair<BigInteger>>> hexagon_crosses = new ArrayList<>();
	
	// Add a prime to the prime list if and only iff it is not already in the list. (ignore duplicates)
	public void addPrime(BigInteger x)
	{
		this.primes.add(x);
	}
	
	// Output the prime list. Each prime should be on a separate line and the total number of primes should be on the following line.
	public void printPrimes()
	{
		for (int i = 0; i < this.primes.size(); i++) // Go through each prime stored in list and output what is stored
			System.out.println(this.primes.get(i));
		
		System.out.println("Total Primes: " + this.primes.size());
	}
		
	// Output the twin prime list. Each twin prime should be on a separate line with a comma separating them, and the total number of twin primes should be on the following line.
	public void printTwins()
	{
		for (int i = 0; i < this.twin_primes.size(); i++) // Go through each twin prime stored in list and output the pair's toString
			System.out.println(this.twin_primes.get(i));
		
		System.out.println("Total Twins: " + this.twin_primes.size());
	}
		
	// Output the hexagon cross list. Each should be on a separate line listing the two twin primes and the corresponding hexagon cross, with the total number on the following line.
	public void printHexes()
	{
		for (int i = 0; i < this.hexagon_crosses.size(); i++) {
			Pair<BigInteger> pair_one = this.hexagon_crosses.get(i).getKey();
			Pair<BigInteger> pair_two = this.hexagon_crosses.get(i).getValue();
			// Get both pairs and print out the toString and separation data
			System.out.println("Prime Pairs: " + pair_one + " and " + pair_two + " separated by " + pair_one.getKey().add(BigInteger.ONE) + ", " + pair_one.getKey().add(BigInteger.ONE).multiply(BigInteger.valueOf(2)));
		}
		
		System.out.println("Total Hexes: " + this.hexagon_crosses.size());
	}
		
	// Generate and store a list of primes.
	public void generatePrimes(int count)
	{
		BigInteger i = BigInteger.ZERO;
		while (this.primes.size() < count) { // Keep looping until we get the right number of primes in the list
			boolean skip = false;
			i = i.add(BigInteger.ONE);
			// Determine if number is prime by going through all the possible factors
			for (BigInteger j = BigInteger.valueOf(2); i.compareTo(j) > 0; j = j.add(BigInteger.ONE)) {
				if (i.mod(j).compareTo(BigInteger.ZERO) == 0) {
					// Number is NOT prime, break out of factor loop and move on to the next possible prime number
					skip = true;
					break;
				}
			}
			
			if (skip)
				continue;
			
			// Loop through everything, seems to be prime
			this.addPrime(i);
		}
	}
	
	// Generate and store a list of twin primes.
	public void generateTwinPrimes()
	{
		for (int i = 1; i < this.primes.size(); i++) {
			// Go through every prime element, check if it is separated by a middle number
			if (this.primes.get(i).subtract(this.primes.get(i - 1)).compareTo(BigInteger.valueOf(2)) == 0)
				this.twin_primes.add(new Pair<BigInteger>(this.primes.get(i - 1), this.primes.get(i)));
		}
	}
	
	// Generate and store the hexagon crosses, along with the two twin primes that generate the hexagon cross.
	public void generateHexPrimes()
	{
		for (int i = 0; i < this.twin_primes.size(); i++) {
			Pair<BigInteger> pair_one = this.twin_primes.get(i);
			for (int j = (i + 1); j < this.twin_primes.size(); j++) {
				// Going through every possible twin prime pairs, check if they are actual hex crosses
				Pair<BigInteger> pair_two = this.twin_primes.get(j);
				// Check for hex cross conditions
				if (pair_two.getKey().add(BigInteger.ONE).divide(pair_one.getKey().add(BigInteger.ONE)).compareTo(BigInteger.valueOf(2)) == 0 && pair_two.getKey().add(BigInteger.ONE).compareTo(pair_one.getKey().add(BigInteger.ONE).multiply(BigInteger.valueOf(2))) == 0) {
					this.hexagon_crosses.add(new Pair<Pair<BigInteger>>(pair_one, pair_two));
					break;
				}
			}
		}
	}
}
