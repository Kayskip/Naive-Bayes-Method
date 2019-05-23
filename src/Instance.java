import java.util.Scanner;

/**
 * @author karu
 *
 */
public class Instance {

	int[] attributes = new int[13];
	boolean spam = false;

	/**
	 * @param features
	 */
	public Instance(String features) {
		Scanner scanner = new Scanner(features);
		int count = 0;
		while (scanner.hasNext()) {
			this.attributes[count] = scanner.nextInt();
			count++;
		}
		scanner.close();
	}

	/**
	 * Check to see if its spam
	 */
	public boolean isSpam() {
		if (this.attributes[12] == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		String string = "\n[";
		for (int i = 0; i < this.attributes.length - 1; i++) {
			string += +this.attributes[i] + " ";
		}
		return string + "]";

	}

	/**
	 * @return
	 */
	public int[] getAttributeData() {
		return attributes;
	}
}
