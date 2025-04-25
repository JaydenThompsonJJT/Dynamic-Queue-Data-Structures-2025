module FOS {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens FOS to javafx.graphics, javafx.fxml;
	exports FOS;
}
