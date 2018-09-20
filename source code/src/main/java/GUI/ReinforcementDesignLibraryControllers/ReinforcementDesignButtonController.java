package GUI.ReinforcementDesignLibraryControllers;

import GUI.view.GraphScreenController;
import SLS.Sls;
import SLS.cracks.Scratch;
import SLS.creepCoeficent.CreepCoeficent;
import SLS.deflection.DeflectionControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
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
			CreepCoeficent creep, boolean wasResultsGenerated, NominalStiffness stiffness, CheckBox checkbox,
			Scratch scratch, DeflectionControl deflection) {
		addListener(button, requiredReinforcement, concrete, steel, internalForces, dimensions, reinforcement, 
				resultsPaneControllerULS, cement, sls, forces, creep, wasResultsGenerated, stiffness, checkbox, scratch, deflection);
	}

	private static void addListener(Button button, RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, Cement cement, Sls sls, InternalForces forces,
			CreepCoeficent creep, boolean wasResultsGenerated, NominalStiffness stiffness, CheckBox checkbox, Scratch scratch,
			DeflectionControl deflection) {
		button.setOnAction(new onClick(requiredReinforcement, concrete, steel, internalForces, 
				dimensions, reinforcement, resultsPaneControllerULS, cement, sls, 
				forces, creep, wasResultsGenerated, stiffness, checkbox, scratch, deflection));
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
		CheckBox checkbox;
		Scratch scratch;
		DeflectionControl deflection;


		protected onClick(RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel, InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions,
				Reinforcement reinforcement, ResultsPaneControllerULS resultsPaneControllerULS, 
				Cement cement, Sls sls, InternalForces forces, CreepCoeficent creep, boolean wasResultsGenerated,
				NominalStiffness stiffness, CheckBox checkbox, Scratch scratch, DeflectionControl deflection) {
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
			this.checkbox = checkbox;
			this.scratch = scratch;
			this.deflection = deflection;
		}

		@Override
		public void handle(ActionEvent event) {
			boolean withDesign = true;
			ResultsToPDF.clearResults();
			requiredReinforcement.checkWhatIsRequiredReinforcementAndDesign(concrete, steel, internalForces, 
					dimensions, reinforcement, stiffness, cement, creep, checkbox);
			//if (!dimensions.getIsColumn())
			sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces, scratch, deflection, withDesign);
			//sls.printReport(dimensions, creep, concrete, deflection, scratch, "PROJ");
			resultsPaneControllerULS.dispResults();
			//Graph graph = new Graph(graphController.getLineChart(), steel, dimensions, concrete, reinforcement);
			//graph.plotGraph();
			SaveFileButtonController.enableSaveButtonDesignScene();
		}

	}
}
