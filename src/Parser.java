import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author karu
 *
 */
public class Parser {

	private Set<Instance> instances = new HashSet<Instance>();

	/**
	 * @param data
	 */
	public void read(File data) {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(data);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				this.instances.add(new Instance(line));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return instances
	 */
	public Set<Instance> getInstances() {
		return this.instances;
	}

}
