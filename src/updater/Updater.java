/*
    Set below four variables in FXMLUpdater.java & DownloadSFTP.java before release.
    *** destDirectory - App exe path
    *** xmlLocal - App xml file path
    *** SFTPWORKINGDIR - Source Directory on SFTP server
    *** LOCALDIRECTORY - Local Target(App) Directory
 */
package updater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Bluechip
 */
public class Updater extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLUpdater.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
