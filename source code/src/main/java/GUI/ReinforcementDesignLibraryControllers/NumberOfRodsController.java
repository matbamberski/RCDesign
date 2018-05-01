package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import mainalgorithm.Reinforcement;

public class NumberOfRodsController {

	public static void addPropertiesToAs1SymmetricalNumberOfRodsTF(TextField tf, Reinforcement reinforcement) {
		addInputListenerToAs1SymmetricalNumberOfRods(tf, reinforcement);
	}

	public static void addPropertiesToAs2SymmetricalNumberOfRodsTF(TextField tf, Reinforcement reinforcement) {
		addInputListenerToAs2SymmetricalNumberOfRods(tf, reinforcement);
	}

	public static void addPropertiesToAs1UnsymmetricalNumberOfRodsTF(TextField tf, Reinforcement reinforcement) {
		addInputListenerToAs1UnsymmetricalNumberOfRods(tf, reinforcement);
	}

	public static void addPropertiesToAs2UnsymmetricalNumberOfRodsTF(TextField tf, Reinforcement reinforcement) {
		addInputListenerToAs2UnsymmetricalNumberOfRods(tf, reinforcement);
	}

	// aS1 symmetrucal number of rods

	private static void addInputListenerToAs1SymmetricalNumberOfRods(TextField tf, Reinforcement reinforcemnt) {
		tf.textProperty().addListener(new InputValueListenerAs1SymmetricalNumberOfRods(reinforcemnt));
	}

	private static class InputValueListenerAs1SymmetricalNumberOfRods implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected InputValueListenerAs1SymmetricalNumberOfRods(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			reinforcement.setRequiredNumberOfSymmetricalRodsAS1(Integer.parseInt(arg2));
			reinforcement.calculateDesignedSymmetricalAs1RodsGiven();
			reinforcement.setRequiredNumberOfUnsymmetricalRodsAS1(Integer.parseInt(arg2));
			reinforcement.calculateDesignedUnsymmetricalAs1RodsGiven();
		}

	}

	// aS2 symmetrica number of rods
	private static void addInputListenerToAs2SymmetricalNumberOfRods(TextField tf, Reinforcement reinforcemnt) {
		tf.textProperty().addListener(new InputValueListenerAs2SymmetricalNumberOfRods(reinforcemnt));
	}

	private static class InputValueListenerAs2SymmetricalNumberOfRods implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected InputValueListenerAs2SymmetricalNumberOfRods(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			reinforcement.setRequiredNumberOfSymmetricalRodsAS2(Integer.parseInt(arg2));
			reinforcement.calculateDesignedSymmetricalAs2RodsGiven();
			reinforcement.setRequiredNumberOfUnsymmetricalRodsAS2(Integer.parseInt(arg2));
			reinforcement.calculateDesignedUnsymmetricalAs2RodsGiven();
		}

	}
	// as1 unsymmetrical number of rods

	private static void addInputListenerToAs1UnsymmetricalNumberOfRods(TextField tf, Reinforcement reinforcemnt) {
		tf.textProperty().addListener(new InputValueListenerAs1UnsymmetricalNumberOfRods(reinforcemnt));
	}

	private static class InputValueListenerAs1UnsymmetricalNumberOfRods implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected InputValueListenerAs1UnsymmetricalNumberOfRods(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			reinforcement.setRequiredNumberOfUnsymmetricalRodsAS1(Integer.parseInt(arg2));
			reinforcement.calculateDesignedUnsymmetricalAs1RodsGiven();
		}
	}

	// as2 unsymmetrical number of rods

	private static void addInputListenerToAs2UnsymmetricalNumberOfRods(TextField tf, Reinforcement reinforcemnt) {
		tf.textProperty().addListener(new InputValueListenerAs2UnsymmetricalNumberOfRods(reinforcemnt));
	}

	private static class InputValueListenerAs2UnsymmetricalNumberOfRods implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected InputValueListenerAs2UnsymmetricalNumberOfRods(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			reinforcement.setRequiredNumberOfUnsymmetricalRodsAS2(Integer.parseInt(arg2));
			reinforcement.calculateDesignedUnsymmetricalAs2RodsGiven();
		}
	}

}
