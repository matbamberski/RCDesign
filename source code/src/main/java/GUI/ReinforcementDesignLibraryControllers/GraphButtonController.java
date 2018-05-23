package GUI.ReinforcementDesignLibraryControllers;


import GUI.view.ReinforcementDiagnosisController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.graph.Graph;


public class GraphButtonController {

	public static void addPropertiesToDesignButton(Button button, ReinforcementDiagnosisController diagnosis,
			Graph graph) {
		addListener(button, diagnosis, graph);
	}

	private static void addListener(Button button, ReinforcementDiagnosisController diagnosis, Graph graph) {
		button.setOnAction(
				new onClick(diagnosis, graph));
	}

	private static class onClick implements EventHandler<ActionEvent> {
		ReinforcementDiagnosisController diagnosis;
		Graph graph;

		protected onClick(ReinforcementDiagnosisController diagnosis, Graph graph) {
			this.diagnosis = diagnosis;
			this.graph = graph;
		}

		@Override
		public void handle(ActionEvent event) {
			graph.plotGraph();
			diagnosis.switchToGraphScene(event);
		}

	}
}
