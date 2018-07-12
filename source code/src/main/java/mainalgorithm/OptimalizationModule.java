package mainalgorithm;

import java.util.ArrayList;

import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.axisload.SymmetricalTensilingBeamReinforcement;

public class OptimalizationModule extends SymmetricalTensilingBeamReinforcement {
	
	private InternalForces forces;
	private ArrayList<ForcesCombination> combinations;
	private Steel steel;
	private Concrete concrete;
	private DimensionsOfCrossSectionOfConcrete dimensions;
	private double aS;
	private ForcesCombination selectedCombination;
	private double s;
	
	public OptimalizationModule(InternalForces forces, Steel steel,
			Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		super();
		this.forces = forces;
		//this.combinations = forces.getForcesCombinations();
		this.steel = steel;
		this.concrete = concrete;
		this.dimensions = dimensions;
	}
	/*
	public void findMaxAs() {
		aS = 0;
		for(ForcesCombination fc : combinations) {
			if(fc.getaS1req()+fc.getaS2req()>aS)
				aS = fc.getaS1req()+fc.getaS2req();
				selectedCombination = fc;
		}
		
	}
	*/
	
	
	
}
