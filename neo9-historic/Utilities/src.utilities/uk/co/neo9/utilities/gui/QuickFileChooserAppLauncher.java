package uk.co.neo9.utilities.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;

import javax.sound.midi.SysexMessage;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import uk.co.neo9.utilities.app.UtilitiesAppLaunchDetails;
import uk.co.neo9.utilities.app.UtilitiesAppLaunchDetailsContainer;
import uk.co.neo9.utilities.app.UtilitiesApplicationLauncherI;
import uk.co.neo9.utilities.app.UtilitiesLaunchableApplicationI;
import uk.co.neo9.utilities.file.FileDirectoryTrawlerDefaultPlugIn;
import uk.co.neo9.utilities.file.IFileDirectoryTrawlerPlugIn;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class QuickFileChooserAppLauncher 
	extends javax.swing.JFrame 
	implements UtilitiesApplicationLauncherI {

	
	// The GUI aspect
	protected JCheckBox isRecursiveCHKBX;
	private JFileChooser fileChooser;
	private JMenuBar jMenuBar1;
	private File startingFile = null;
	
	
	
	// for the application launching aspect
	UtilitiesLaunchableApplicationI theApplication = null;
	String[] launchingArgs = null;
	UtilitiesAppLaunchDetailsContainer launchDetails = null;
	

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {

		QuickFileChooserAppLauncher inst = new QuickFileChooserAppLauncher();
		inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inst.setVisible(true);
	}
	
	
	public IFileDirectoryTrawlerPlugIn initialisePlugin(){
		return new FileDirectoryTrawlerDefaultPlugIn();
	}
	
	public QuickFileChooserAppLauncher() {
		super();
	}
	
	protected int getFileSelectionMode(){
		return JFileChooser.FILES_ONLY;
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.getContentPane().setLayout(thisLayout);
			this.setSize(800, 600);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);

			}


			// ------ FileChooser -------------
			{
				fileChooser = new JFileChooser();

			    Neo9FileFilterAdaptor filter = getFileFilter();
			   // fileChooser.setFileFilter(filter);
			    fileChooser.setFileSelectionMode(getFileSelectionMode());
			    fileChooser.setSelectedFile(this.startingFile );
			    
			 //   fileChooser.setApproveButtonText("Run Script");

				
				this.getContentPane().add(fileChooser,BorderLayout.CENTER);
				fileChooser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
							handleDoIt(fileChooser.getSelectedFile());
						} else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection")) {
							System.exit(0);
						}
						
					}
				});
			}
			{
				addControls();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void addControls(){
		
//		isRecursiveCHKBX = new JCheckBox();
//		this.getContentPane().add(isRecursiveCHKBX, BorderLayout.NORTH);
//		isRecursiveCHKBX.setText("Recurse Sub-Directories?");
//		isRecursiveCHKBX.setSelected(true);	
		
		
		Container controlParent = this.getContentPane();

		isRecursiveCHKBX = new JCheckBox();
		controlParent.add(isRecursiveCHKBX, BorderLayout.NORTH);
		isRecursiveCHKBX.setText("Recurse Sub-Directories?");
		isRecursiveCHKBX.setSelected(true);				
		
		do_addControls(controlParent);
		
	}

	protected void do_addControls(Container controlParent){
		
			// overide this
	}

	protected Neo9FileFilterAdaptor getFileFilter() {	
		Neo9FileFilterAdaptor filter = new Neo9FileFilterAdaptor();
		filter.addExtension("*");
		filter.setDescription("Any Files");
		return filter;
	}

	// workings 
	public void handleDoIt(File selectedFile){
		
		// launch the embedded application!
		//... so use the latest version of the args
		
		// get the filename & folder from the selected file
		String filename = selectedFile.getName(); 
		String folder = selectedFile.getParent(); 
		

		// determine which of the args are file & folder and overwrite them
		for (Iterator iterator = launchDetails.getArguments().iterator(); iterator.hasNext();) {
			UtilitiesAppLaunchDetails argDetail = (UtilitiesAppLaunchDetails) iterator.next();
			if (argDetail.isQuickLaunch()) {
				int index = argDetail.getIndex();
				if (argDetail.getType() == UtilitiesAppLaunchDetails.TYPE_FOLDER) {
					this.launchingArgs[index] = folder;
				} else if (argDetail.getType() == UtilitiesAppLaunchDetails.TYPE_FILENAME_ONLY) {
					this.launchingArgs[index] = filename;
				} 
			}
		}
		
		//TODO this does NOT cater for the combined filedetails scenario!!!
		
		
		// launch the application 
		theApplication.launch(launchingArgs);
	
	}


	public void setupLauncher(UtilitiesLaunchableApplicationI app, String[] args) {
		
		theApplication = app;
		launchingArgs = args;
		
		launchDetails = app.getLaunchDetails(args);
		if (launchDetails.validateQuickLaunchArgs() == false){
			System.out.println(launchDetails.getArgumentsHelpText());
			System.err.println("Quick launch file / folder not valid!");
			System.exit(-1);
		}
		
		String quickLaunchFileDetails = launchDetails.getQuickLaunchFileDetails();
		this.setupDefaultFile(quickLaunchFileDetails);		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.initGUI();

		
	}


	
	
	private void setupDefaultFile(String folder, String filename) {

		String filedetails = folder+"/"+filename;
		setupDefaultFile(filedetails);

	}
	
	
	
	private void setupDefaultFile(String fullFileDetails) {

		try {
			File argsFile = new File(fullFileDetails);
			this.startingFile = argsFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
