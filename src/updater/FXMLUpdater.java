package updater;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bluechip
 */
public class FXMLUpdater implements Initializable {
    //Editable path variables from here
    String destDirectory = "Prismate.exe"; //App exe path
    File xmlLocal = new File("info.xml"); //App xml file path
    //To here

    String version = "";
    String version2 = "";
    public int major = 0;
    public int minor = 0;
    public int patch = 0;  
        
    @FXML
    private Label label;
    @FXML
    private Label label2;
    @FXML
    private Button button;
    @FXML
    private Button button2;    
    
    @FXML
    private void update(ActionEvent event) throws IOException { //Update

        DownloadSFTP dwnXML = new DownloadSFTP(); 
        dwnXML.download(1); //Download & copy files        
        label.setText("Update Completed.");
        button.setVisible(false);
        button2.setVisible(true);
    }
    
    @FXML
    private void finish(ActionEvent event) throws IOException{
         //Restart the app after updating
        //Runtime.getRuntime().exec("taskkill /F /IM javaw.exe");
        File file = new File(destDirectory);
        Runtime.getRuntime ().exec (destDirectory);
        System.exit(0);
    }
    
    public void runUpdater() throws IOException, Throwable { //Execute updaer GUI
        Parent root2 = FXMLLoader.load(getClass().getResource("/updater/FXMLUpdater.fxml"));
        
        Scene scene2 = new Scene(root2);
        Stage addStage = new Stage();
        addStage.setTitle("My Updater");
        addStage.setScene(scene2);
        addStage.show();
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { //Do these when the updater is staring
        // TODO
        label.setText("Checking for Updates...");
        //Download & read version xlm file
        DownloadSFTP dwnXML = new DownloadSFTP();
        ReadXMLFile read = new ReadXMLFile(); 
        //String link = "https://raw.githubusercontent.com/banny01/Calculator/master/app/info.xml";
        //String path = System.getenv("APPDATA") + File.separator + "BlueChip";
        File fXmlFile = new File(System.getenv("APPDATA") + File.separator + "BlueChip" + File.separator + "info.xml");
        
        read.getLatestVersion(xmlLocal); //Read local version xml file
        major = read.major;
        minor = read.minor;
        patch = read.patch;
        version2 = major + "." + minor + "." + patch;
        
        dwnXML.download(0);        
        read.getLatestVersion(fXmlFile);
        version = read.major + "." + read.minor + "." + read.patch;
        
        //Compare the versions
        if(major < read.major){
            label.setText("Update Available.");
            label2.setText(version2 + " to " + version);
        }
        else if(major==read.major && minor < read.minor){
            label.setText("Update Available.");
            label2.setText(version2 + " to " + version);
        }
        else if(major==read.major && minor == read.minor && patch < read.patch){
            label.setText("Update Available.");
            label2.setText(version2 + " to " + version);            
        }
        else{
            label.setText("You are upto date.");
            label2.setText("Current Version : " + version);
            button.setVisible(false);
            button2.setVisible(true);
            //System.exit(0);
        }       
    }        
}
