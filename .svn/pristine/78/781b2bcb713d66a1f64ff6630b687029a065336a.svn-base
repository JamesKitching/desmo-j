package desmoj.demo.queue2D;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Locale;

import desmoj.extensions.visualization2d.engine.viewer.ViewerFrame;


/**
 * ConfigTool2d is a collection of some static methods to manage 
 * filenames ant to create a animation viewer. There used in model packages.
 * @author Christian
 */
public class ConfigTool2d {

	/**
	 * Absolute path to visual2d_model_data directory. Must end with /
	 */
	public static final String   	PATH_DATA				= 
		new File("").getAbsolutePath() + File.separator + "visual2d_model_data" + File.separator;
	
	public static String buildIconPath(String modelName){
		String out = "";
		out += new File("").getAbsolutePath() + File.separator + "visual2d_model_data";
		out += File.separator+modelName+File.separator+"Icons"+File.separator;
		return out;
	}
	
	public static boolean checkIconPath(String modelName){
		File iconPath = (new File(buildIconPath(modelName)));
		boolean out = true;
		if(! iconPath.exists()){
			out = false;
			System.out.println("IconPath don't exist. IconPath: "+iconPath.getAbsolutePath());
		}else if(! iconPath.isDirectory()){
			out = false;
			System.out.println("IconPath isn't a directory. IconPath: "+iconPath.getAbsolutePath());
		}else if(! iconPath.canRead()){
			out = false;
			System.out.println("IconPath can't read. IconPath: "+iconPath.getAbsolutePath());
		}
		if(buildIconPathURL(modelName) == null){
			out = false;;
			System.out.println("Fehler bei Umwandlung von IconPath in File-URL: "+iconPath.getAbsolutePath());
		}
		return out;
	}
	
	public static URL buildIconPathURL(String modelName){
		File iconPath = (new File(buildIconPath(modelName)));
		URL	 url = null;
		try {
			url = iconPath.toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Fehler bei Umwandlung von IconPath in File-URL: "+iconPath.getAbsolutePath());
			e.printStackTrace();
		}
		return url;
	}
	
	public static boolean createExperimentDir(String modelName, String experimentName){
		boolean out = true;
		String modelDirStr = "";
		String experimentDirStr = "";
		modelDirStr += new File("").getAbsolutePath() + File.separator + "visual2d_model_data";
		modelDirStr += File.separator+modelName+File.separator;
		experimentDirStr += experimentName+File.separator;
		File modelDir = new File(modelDirStr);
		if(! modelDir.exists()){
			out = false;
			System.out.println("ModelPath don't exist. ModelPath: "+modelDir.getAbsolutePath());
		}else if(! modelDir.isDirectory()){
			out = false;
			System.out.println("ModelPath isn't a directory. ModelPath: "+modelDir.getAbsolutePath());
		}
		if(out){
			File commonData = new File(modelDirStr+File.separator+"data"+File.separator);
			File cmds = new File(modelDirStr+experimentDirStr+"cmds"+File.separator);
			File output = new File(modelDirStr+experimentDirStr+"output"+File.separator);
			File data = new File(modelDirStr+experimentDirStr+"data"+File.separator);
			if(! commonData.exists()){
				if(! commonData.mkdirs()){
					out = false;
					System.out.println("Not possible to build commonData dir. Path: "+commonData.getAbsolutePath());
				}
			}
			if(! cmds.exists()){
				if(! cmds.mkdirs()){
					out = false;
					System.out.println("Not possible to build cmds dir. CmdsPath: "+cmds.getAbsolutePath());
				}
			}
			if(! output.exists()){
				if(! output.mkdirs()){
					out = false;
					System.out.println("Not possible to build output dir. OutputPath: "+output.getAbsolutePath());
				}
			}
			if(! data.exists()){
				if(! data.mkdirs()){
					out = false;
					System.out.println("Not possible to build input dir. DataPath: "+data.getAbsolutePath());
				}else{
					// Inputverzeichnis des Experimens wurde eben angelegt.
					// Kopiere den Inhalt von commonInput nach Input
					try {
						File[] files = commonData.listFiles();
						for(int i=0; i<files.length; i++){
							File target = new File(data, files[i].getName());
							ConfigTool2d.copy(files[i], target);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return out;
	}

	public static String buildCmdsPath(String modelName, String experimentName, String extension){
		String out = "";
		out += new File("").getAbsolutePath() + File.separator + "visual2d_model_data";
		out += File.separator+modelName+File.separator+experimentName;
		out += File.separator+"cmds"+File.separator+modelName+extension;
		//System.out.println(out);
		return out;
	}

	public static URL buildCmdsPathURL(String modelName, String experimentName, String extension){
		File cmdsPath = (new File(buildCmdsPath(modelName, experimentName, extension)));
		URL	 url = null;
		try {
			url = cmdsPath.toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Fehler bei Umwandlung von CmdsPath in File-URL: "+cmdsPath.getAbsolutePath());
			e.printStackTrace();
		}
		return url;
	}
	

	
	public static String buildOutputPath(String modelName, String experimentName){
		String out = "";
		out += new File("").getAbsolutePath() + File.separator + "visual2d_model_data";
		out += File.separator+modelName+File.separator+experimentName;
		out += File.separator+"output"+File.separator;
		//System.out.println(out);
		return out;
	}
	
	public static String getDataFile(String modelName){
		String out = "";
		out += new File("").getAbsolutePath() + File.separator + "visual2d_model_data";
		out += File.separator+modelName;
		out += File.separator+"data"+File.separator+modelName+".xml";
		//System.out.println(out);
		return out;
	}
	
	public static String getDataFile(String modelName, String experimentName){
		String out = "";
		out += new File("").getAbsolutePath() + File.separator + "visual2d_model_data";
		out += File.separator+modelName+File.separator+experimentName;
		out += File.separator+"data"+File.separator+modelName+".xml";
		//System.out.println(out);
		return out;
	}
	
	public static String getModelName(String absolutClassName){
		// Annahme zur Struktur
		// absolutClassName = "model".ModelName.ClassName
		int first = absolutClassName.indexOf('.', 0);
		int second = absolutClassName.indexOf('.', first+1);
		return absolutClassName.substring(first+1, second);
	}
	
	public static String getClassName(String absolutClassName){
		// Annahme zur Struktur
		// absolutClassName = "model".ModelName.ClassName
		int first = absolutClassName.indexOf('.', 0);
		int second = absolutClassName.indexOf('.', first+1);
		return absolutClassName.substring(second+1);
	}
	
	/** Fast & simple file copy. */
	private static void copy(File source, File dest) throws IOException {
	     FileChannel in = null, out = null;
	     try {          
	          in = new FileInputStream(source).getChannel();
	          out = new FileOutputStream(dest).getChannel();
	 
	          long size = in.size();
	          MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
	 
	          out.write(buf);
	 
	     } finally {
	          if (in != null)          in.close();
	          if (out != null)     out.close();
	     }
	}
	
	
	/**
	 * create a ViewerFrame for 2d animations
	 * @param cmdFile
	 * @param imagePath
	 * @return
	 */
	public static ViewerFrame createViewer(URL cmdFile, URL imagePath){

		ViewerFrame v = null;
		try{
			v = new ViewerFrame(cmdFile, imagePath, Locale.ENGLISH);
			v.setLocation(0, 0);
			v.setSize(800, 500);
			v.getViewerPanel().setDefaultPath(PATH_DATA, PATH_DATA);
			v.getViewerPanel().lastCall();
		}catch(Exception e){
			if(v != null) v.getViewerPanel().setStatusMessage(e.getMessage());
			//e.printStackTrace(ViewerPanel.getLogWriter());
			e.printStackTrace();
		}
		return v;
		
	}
}
