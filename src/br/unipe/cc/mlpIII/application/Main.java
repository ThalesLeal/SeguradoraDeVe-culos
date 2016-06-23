package br.unipe.cc.mlpIII.application;
	
import br.unipe.cc.mlpIII.gui.*;
//import br.unipe.cc.mlpIII.util.SmsAutomatica;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	protected Stage loginStage = new Stage();
	protected Stage mainStage = new Stage();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//SmsAutomatica smsAutomatica = new SmsAutomatica();
		//smsAutomatica.run();
		
		TelaLogin telaLogin = new TelaLogin();
		telaLogin.start(loginStage);

		loginStage.setOnHiding( new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (telaLogin.isAutorizado()){
					TelaPrincipal telaPrincipal = new TelaPrincipal();
					try {
						telaPrincipal.start(mainStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} );
	}
}
