package mainalgorithm;

import java.util.ArrayList;

import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class KNGAlgorithmSymmetrical extends KNGAlgorithm{

	public KNGAlgorithmSymmetrical(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions) {
		super(concrete, steel, dimensions);
	}
	 
	protected void setSValues() {
		s = 0.5;
	}
	
	protected void mainFor(ArrayList<ForcesCombination> combinations) {
		setAsMinAndAs(combinations);
		int i = 0;
		while(true) {
			i++;
			System.out.println("Iteracja nr: "+i);
			setSValues();
			setAsValues();
			countNUltimate();
			while (checkNEdLessN0OrGreaterThanNMax(combinations)) {
				increaseAS();
				setSValues();
				setAsValues();
				countNUltimate();
			}
			for (ForcesCombination combination: combinations) {
				findUpParts(combination);
				findDownParts(combination);
			}
			
			if (isConditionMet(combinations)) {
				if(lastStepDone()) break;
			}
			else {
				increaseAS();
			}
		}
	}

}
