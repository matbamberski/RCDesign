package GUI.ReinforcementDesignLibraryControllers;

import GUI.view.GraphScreenController;
import SLS.Sls;
import SLS.creepCoeficent.CreepCoeficent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import mainalgorithm.RequiredReinforcement;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.graph.Graph;
import util.ResultsToPDF;

public class ReinforcementDesignButtonController {

	public static void addPropertiesToDesignButton(Button button, RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, Cement cement, Sls sls, InternalForces forces,
			CreepCoeficent creep, boolean wasResultsGenerated, NominalStiffness stiffness ) {
		addListener(button, requiredReinforcement, concrete, steel, internalForces, dimensions, reinforcement, 
				resultsPaneControllerULS, cement, sls, forces, creep, wasResultsGenerated, stiffness);
	}

	private static void addListener(Button button, RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, Cement cement, Sls sls, InternalForces forces,
			CreepCoeficent creep, boolean wasResultsGenerated, NominalStiffness stiffness) {
		button.setOnAction(new onClick(requiredReinforcement, concrete, steel, internalForces, 
				dimensions, reinforcement, resultsPaneControllerULS, cement, sls, forces, creep, wasResultsGenerated, stiffness));
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
		GraphScreenController graphController;
		NominalStiffness stiffness;


		protected onClick(RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions,
				Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, 
				Cement cement, Sls sls, InternalForces forces, CreepCoeficent creep, boolean wasResultsGenerated,
				NominalStiffness stiffness) {
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
			this.stiffness = stiffness;
		}

		@Override
		public void handle(ActionEvent event) {
			ResultsToPDF.clearResults();
			requiredReinforcement.checkWhatIsRequiredReinforcementAndDesign(concrete, steel, internalForces, 
					dimensions, reinforcement, stiffness);
			sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces);
			resultsPaneControllerULS.dispResults();
			//Graph graph = new Graph(graphController.getLineChart(), steel, dimensions, concrete, reinforcement);
			//graph.plotGraph();
			SaveFileButtonController.enableSaveButtonDesignScene();
		}

	}
}
