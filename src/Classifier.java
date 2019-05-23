import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author karu
 *
 */
public class Classifier {

	private Set<Instance> spamInst = new HashSet<Instance>();
	private Set<Instance> notSpamInst = new HashSet<Instance>();
	private Map<Integer, Double> trueAttProbSpam = new HashMap<Integer, Double>();
	private Map<Integer, Double> falseAttProbSpam = new HashMap<Integer, Double>();
	private Map<Integer, Double> trueAttProbNotSpam = new HashMap<Integer, Double>();
	private Map<Integer, Double> falseAttProbNotSpam = new HashMap<Integer, Double>();
	private Map<Integer, Double> trueAttProb = new HashMap<Integer, Double>();
	private Map<Integer, Double> falseAttProb = new HashMap<Integer, Double>();

	private double notSpamPriorProb;
	private double spamPriorProb;

	/**
	 * @param data
	 */
	public Classifier(Set<Instance> data) {
		this.intializeMaps();
		this.calculateClassInstances(data);
		this.calculateAttributeClassInstances();
		this.spamPriorProb = ((double) (this.spamInst.size())
				/ (double) (this.notSpamInst.size() + this.spamInst.size()));
		this.notSpamPriorProb = ((double) (this.notSpamInst.size())
				/ (double) (this.notSpamInst.size() + this.spamInst.size()));
	}

	/**
	 * Initialize the maps
	 */
	private void intializeMaps() {
		for (int i = 0; i < 12; i++) {
			this.trueAttProbSpam.put(i, 0.0);
			this.trueAttProbNotSpam.put(i, 0.0);
			this.falseAttProbSpam.put(i, 0.0);
			this.falseAttProbNotSpam.put(i, 0.0);
			this.trueAttProb.put(i, 0.0);
			this.falseAttProb.put(i, 0.0);
		}
	}

	/**
	 * @param data add instances to their lists based off if they are spam or not
	 */
	private void calculateClassInstances(Set<Instance> data) {
		for (Instance instances : data) {
			if (instances.isSpam()) {
				this.spamInst.add(instances);
			} else {
				this.notSpamInst.add(instances);
			}
		}
	}

	/**
	 * since we have the instances loaded into their specified arrays, we can start
	 * calculating
	 */
	private void calculateAttributeClassInstances() {
		for (Instance i : this.spamInst) {
			for (int index = 0; index < i.getAttributeData().length - 1; index++) {
				if (i.getAttributeData()[index] == 1) {
					this.trueAttProbSpam.replace(index, this.trueAttProbSpam.get(index) + 1);
					this.trueAttProb.replace(index, this.trueAttProb.get(index) + 1);
				} else {
					this.falseAttProbSpam.replace(index, falseAttProbSpam.get(index) + 1);
					this.falseAttProb.replace(index, falseAttProb.get(index) + 1);
				}
			}
		}

		for (Instance i : this.notSpamInst) {
			for (int index = 0; index < i.getAttributeData().length - 1; index++) {
				if (i.getAttributeData()[index] == 1) {
					trueAttProbNotSpam.replace(index, trueAttProbNotSpam.get(index) + 1);
					trueAttProb.replace(index, trueAttProb.get(index) + 1);
				} else {
					falseAttProbNotSpam.replace(index, falseAttProbNotSpam.get(index) + 1);
					falseAttProb.replace(index, falseAttProb.get(index) + 1);
				}
			}
		}

		for (int index = 0; index < 12; index++) {

			System.out.println(
					"P(C = spam | F1 = 1) = " + this.trueAttProbSpam.get(index) / (double) this.spamInst.size());
			this.trueAttProbSpam.replace(index, this.trueAttProbSpam.get(index) / (double) this.spamInst.size());
			System.out.println(
					"P(C = spam | F1 = 0) = " + this.falseAttProbSpam.get(index) / (double) this.spamInst.size());
			this.falseAttProbSpam.replace(index, this.falseAttProbSpam.get(index) / (double) spamInst.size());

			System.out.println("P(C = Not Spam | F1 = 1) = "
					+ this.trueAttProbNotSpam.get(index) / (double) this.notSpamInst.size());
			trueAttProbNotSpam.replace(index, this.trueAttProbNotSpam.get(index) / (double) this.notSpamInst.size());

			System.out.println("P(C = Not Spam | F1 = 0) = "
					+ this.falseAttProbNotSpam.get(index) / (double) this.notSpamInst.size());
			falseAttProbNotSpam.replace(index, falseAttProbNotSpam.get(index) / (double) notSpamInst.size());

			System.out.println();

			this.trueAttProb.replace(index, this.trueAttProb.get(index) / 200);
			this.falseAttProb.replace(index, this.falseAttProb.get(index) / 200);

		}
	}

	/**
	 * @param instance this is the main method for classifying our input
	 */
	public void classifyInput(Instance instance) {
		double spamLikelihood = 1.0;
		for (int index = 0; index < instance.getAttributeData().length - 1; index++) {
			if (instance.getAttributeData()[index] == 1) {
				spamLikelihood *= this.trueAttProbSpam.get(index);
			} else {
				spamLikelihood *= this.falseAttProbSpam.get(index);
			}
		}
		spamLikelihood *= this.spamPriorProb;

		double notSpamLikelihood = 1.0;
		for (int index = 0; index < instance.getAttributeData().length - 1; index++) {
			if (instance.getAttributeData()[index] == 1) {
				notSpamLikelihood *= this.trueAttProbNotSpam.get(index);
			} else {
				notSpamLikelihood *= this.falseAttProbNotSpam.get(index);
			}
		}
		notSpamLikelihood *= this.notSpamPriorProb;

		System.out.println(instance.toString());
		System.out.println("Probability its not spam = " + notSpamLikelihood);
		System.out.println("Probability its spam = " + spamLikelihood);

		if (notSpamLikelihood > spamLikelihood) {
			System.out.println("Class : Not Spam");
		} else {
			System.out.println("Class : Spam");
		}
	}

}