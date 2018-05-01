package GUI.ReinforcementDesignLibraryControllers;

import SLS.Sls;
import SLS.creepCoeficent.CreepCoeficent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import mainalgorithm.RequiredReinforcement;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.ResultsToPDF;

public class ReinforcementDesignButtonController {

	public static void addPropertiesToDesignButton(Button button, RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, Cement cement, Sls sls, InternalForces forces,
			CreepCoeficent creep, boolean wasResultsGenerated) {
		addListener(button, requiredReinforcement, concrete, steel, internalForces, dimensions, reinforcement, resultsPaneControllerULS, cement, sls, forces, creep, wasResultsGenerated);
	}

	private static void addListener(Button button, RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, Cement cement, Sls sls, InternalForces forces,
			CreepCoeficent creep, boolean wasResultsGenerated) {
		button.setOnAction(new onClick(requiredReinforcement, concrete, steel, internalForces, dimensions, reinforcement, resultsPaneControllerULS, cement, sls, forces, creep, wasResultsGenerated));
	}

	private static class onClick implements EventHandler<ActionEvent> {
		Concrete concrete;
		Steel steel;
		InternalForces internalForces;
		DimensionsOfCrossSectionOfConcrete dimensions;
		RequiredReinforcement requiredReinforcement;
		Reinforcement reinforcement;
		Cement cement;
		Sls sls;
		ResultsPaneControllerULS resultsPaneControllerULS;
		InternalForces forces;
		CreepCoeficent creep;

		protected onClick(RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions,
				Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, Cement cement, Sls sls, InternalForces forces, CreepCoeficent creep, boolean wasResultsGenerated) {
			this.requiredReinforcement = requiredReinforcement;
			this.concrete = concrete;
			this.steel = steel;
			this.internalForces = internalForces;
			this.dimensions = dimensions;
			this.reinforcement = reinforcement;
			this.resultsPaneControllerULS = resultsPaneControllerULS;
			this.cement = cement;
			this.sls = sls;
			this.forces = forces;
			this.creep = creep;

		}

		@Override
		public void handle(ActionEvent event) {
			ResultsToPDF.clearResults();
			requiredReinforcement.checkWhatIsRequiredReinforcementAndDesign(concrete, steel, internalForces, dimensions, reinforcement);
			sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces);
			resultsPaneControllerULS.dispResults();

			SaveFileButtonController.enableSaveButtonDesignScene();
		}

	}
}
