import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author karu
 *
 */
public class Main {

	/**
	 * @param file1
	 * @param file2
	 */
	public Main(String file1, String file2) {
        Set<Instance> dataToBuildClassification = new HashSet<Instance>();
        Set<Instance> dataToClassify = new HashSet<Instance>();
        Parser p1 = new Parser();
        Parser p2 = new Parser();
        p1.read(new File(file2));
        p2.read(new File(file1));
        
        
        dataToBuildClassification = p1.getInstances();
        dataToClassify = p2.getInstances();
        Classifier c = new Classifier(dataToBuildClassification);

        for(Instance i:dataToClassify){
            c.classifyInput(i);
        }
	}

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		new Main(args[0],args[1]);
	}
}
