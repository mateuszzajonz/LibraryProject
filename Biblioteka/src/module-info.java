module Biblioteka {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires sqlite.jdbc;
	requires java.sql;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
