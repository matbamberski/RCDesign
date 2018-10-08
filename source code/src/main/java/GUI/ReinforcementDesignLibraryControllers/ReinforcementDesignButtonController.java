package GUI.ReinforcementDesignLibraryControllers;

import java.util.Optional;

import GUI.view.GraphScreenController;
import SLS.Sls;
import SLS.cracks.Scratch;
import SLS.creepCoeficent.CreepCoeficent;
import SLS.deflection.DeflectionControl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
				Label wait = new Label();
	            Alert alert = new Alert(
	                    Alert.AlertType.INFORMATION,
	                    "Obliczenia trwaj¹...",
	                    ButtonType.CANCEL
	            );
	            alert.setTitle("RCdesign");
	            alert.setHeaderText("Proszê czekaæ... ");
	            ProgressIndicator progressIndicator = new ProgressIndicator();
	            alert.setGraphic(progressIndicator);
	 
	            Task<Void> task = new Task<Void>() {
	                final int N_ITERATIONS = 5;
	 
	                {
	                    setOnFailed(a -> {
	                        alert.close();
	                        updateMessage("Niepowodzenie");
	                    });
	                    setOnSucceeded(a -> {
	                        alert.close();
	                        updateMessage("Sukces");
	                    });
	                    setOnCancelled(a -> {
	                        alert.close();
	                        updateMessage("Anulowano");
	                    });
	                }
	                
	                @Override
					protected Void call() throws Exception {
	                	updateMessage("Obliczenia trwaj¹...");
	                	
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								//pleaseWaitLabel.setVisible(true);
								//pleaseWaitLabel.setText("Obliczenia trwaj¹...");
								cleanUp();
								updateProgress(0, N_ITERATIONS);
								boolean withDesign = true;
								ResultsToPDF.clearResults();
								if (sls.isMedLessThanZero(internalForces.getmEd()))
									dimensions.setTensileDimensions();
								updateProgress(1, N_ITERATIONS);
								requiredReinforcement.checkWhatIsRequiredReinforcementAndDesign(concrete, steel, internalForces, 
										dimensions, reinforcement, stiffness, cement, creep, checkbox);
								updateProgress(2, N_ITERATIONS);
								//if (!dimensions.getIsColumn())
								sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces, scratch, deflection, withDesign);
								updateProgress(3, N_ITERATIONS);
								if (sls.isMedLessThen0) dimensions.setTensileDimensions();
								//sls.printReport(dimensions, creep, concrete, deflection, scratch, "PROJ");
								resultsPaneControllerULS.dispResults();
								//Graph graph = new Graph(graphController.getLineChart(), steel, dimensions, concrete, reinforcement);
								//graph.plotGraph();
								SaveFileButtonController.enableSaveButtonDesignScene();
								updateProgress(4, N_ITERATIONS);
								
							}
						});
	                
						return null;
	                }
	            };
	            
	            progressIndicator.progressProperty().bind(task.progressProperty());
	            wait.textProperty().unbind();
	            wait.textProperty().bind(task.messageProperty());
	 
	            Thread taskThread = new Thread(task);
	            taskThread.start();
	 
	            //alert.initOwner(stage);
	            Optional<ButtonType> result = alert.showAndWait();
	            if (result.isPresent() && result.get() == ButtonType.CANCEL && task.isRunning()) {
	                task.cancel();
	            }
			/*
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					pleaseWaitLabel.setText("Obliczenia trwaj¹...");
					
				}
			});
			//Platform.runLater(() -> pleaseWaitLabel.setText("Obliczenia trwaj¹..."));
			//pleaseWaitLabel.setVisible(true);
			//pleaseWaitLabel.setText("Obliczenia trwaj¹...");
			
			Task<Void> task = new Task<Void>(){

				@Override
				protected Void call() throws Exception {
					
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							//pleaseWaitLabel.setVisible(true);
							pleaseWaitLabel.setText("Obliczenia trwaj¹...");
							boolean withDesign = true;
							ResultsToPDF.clearResults();
							if (sls.isMedLessThanZero(internalForces.getmEd()))
								dimensions.setTensileDimensions();
							requiredReinforcement.checkWhatIsRequiredReinforcementAndDesign(concrete, steel, internalForces, 
									dimensions, reinforcement, stiffness, cement, creep, checkbox);
							//if (!dimensions.getIsColumn())
							sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces, scratch, deflection, withDesign);
							if (sls.isMedLessThen0) dimensions.setTensileDimensions();
							//sls.printReport(dimensions, creep, concrete, deflection, scratch, "PROJ");
							resultsPaneControllerULS.dispResults();
							//Graph graph = new Graph(graphController.getLineChart(), steel, dimensions, concrete, reinforcement);
							//graph.plotGraph();
							SaveFileButtonController.enableSaveButtonDesignScene();
							
						}
					});

					return null;
				}
				
				@Override
				protected void succeeded() {
					//pleaseWaitLabel.setVisible(false);
					pleaseWaitLabel.setText("Done");
				}
			};
			
			new Thread(task).start();
			*/
			/*
			pleaseWaitLabel.setVisible(true);
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					pleaseWaitLabel.setVisible(false);
					
				}
			});
			boolean withDesign = true;
			ResultsToPDF.clearResults();
			if (sls.isMedLessThanZero(internalForces.getmEd()))
				dimensions.setTensileDimensions();
			requiredReinforcement.checkWhatIsRequiredReinforcementAndDesign(concrete, steel, internalForces, 
					dimensions, reinforcement, stiffness, cement, creep, checkbox);
			//if (!dimensions.getIsColumn())
			sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces, scratch, deflection, withDesign);
			if (sls.isMedLessThen0) dimensions.setTensileDimensions();
			//sls.printReport(dimensions, creep, concrete, deflection, scratch, "PROJ");
			resultsPaneControllerULS.dispResults();
			//Graph graph = new Graph(graphController.getLineChart(), steel, dimensions, concrete, reinforcement);
			//graph.plotGraph();
			SaveFileButtonController.enableSaveButtonDesignScene();
			*/
			
			
		}
		
		public void cleanUp() {
			dimensions.cleanUp();
			stiffness.cleanUp();
		}
		
	}
	
	
}
