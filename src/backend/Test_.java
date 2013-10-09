package backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

// Importing the classes below for importing files: 
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Representation of a test
 * 
 * @author Bruce Steedman
 * @version 2013.09.28
 */
public class Test_ implements java.io.Serializable
{
    private String testTitle;
    private String testIntroText;
    private ArrayList<Section> sections;
    private int possibleMarks; // not used yet!
    private int totalMarksAwarded;
    public boolean isLocked = true;
    private int allocatedTime;
    private long testTime;
    
    //Importing the classes below to write student result to text file.
    private PrintWriter printWriter; 
    private File file;
    
    private Map<Integer, Integer> mapWrite;
    private Map<Integer, Integer> mapRead;


    /**
     * Constructor for objects of class Test
     */
    public Test_(String testTitle, String testIntroText) 
    {
        this.testTitle = testTitle;
        this.testIntroText = testIntroText;
        sections = new ArrayList<Section>();
        this.testTime = 0;
    }

    /**
     * Reads a test from a file
     */
    public static Test_ readFromFile(String sourceFile) throws IOException, ClassNotFoundException
    {
        File source = new File(sourceFile);
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(source));
        Test_ t = (Test_)is.readObject();
        is.close();
        return t;
    }
    
    public String getTestTitle()
    {
        return testTitle;
    }
    
    public String getTestIntroText()
    {
        return testIntroText;
    }
    
    /**
     * Writes a test to file
     * 
     */
    public void saveToFile(String destinationFile) throws IOException
    {
        File destination = new File(destinationFile);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(destination));
        os.writeObject(this);
        os.close();
    }
        
    public void addSection(Section s)
    {
        sections.add(s);
        testTime += s.getSectionTime();
    }
    
    public void removeSection(Section s)
    {
        sections.remove(s);
        testTime -= s.getSectionTime();
    }
    
    /**
     * @param SectionNum is an index for the required section, starting at 0.
     */
    public Section getSection(int sectionNum)
    {
        return sections.get(sectionNum);
    }
    
    /**
     * Returns all sections
     */
    public ArrayList<Section> getAllSections()
    {
        return sections;
    }
    
    /**
     * Unlocks the test
     */
    public void unlockTest()
    {
        isLocked = false;
    }
    
     /**
     * Starts the test
     */
    public void startTest() 
    {
         new TestTimer(this, allocatedTime);
    }
    
     /**
     * sets the timer for the entire test. 
     */    
    public void setTestTime(long testTimeToBeSet)
    {
        testTime = testTimeToBeSet;
    }
    
    
    /**
     * Write to File. 
     * This version basically writes the studenID and testResult in a single text file.
     * This method uses Map as its input parameter.
     */   
	public void writeToFile (Map mapWrite) {
		
		this.mapWrite = mapWrite;
		String stringResults = "studentID | testResult";

        // Using an iterator
        Iterator<Map.Entry<Integer, Integer>> it = mapWrite.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry<Integer, Integer>)it.next();
            stringResults = stringResults + e.getKey() + " | " + e.getValue();
        }
		try
		{
			file = new File ("studentTestResults.txt");
			
			printWriter = new PrintWriter ("studentTestResults.txt");
			printWriter.println (stringResults);
			printWriter.close ();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}
    
    /**
     * Read from File. 
     * This version basically reads the student results from a text file.
     * This method uses String filename as its input parameter.
     */ 	
	public void getTextFromFile (String filename)  {
		
		try 
		{
			BufferedReader input = null;
			String lineOutput = "";
			input = new BufferedReader(new FileReader(filename));
			mapRead = new HashMap<Integer, Integer>();
			
			while (input!= null) {
				lineOutput = input.readLine();
				int studentIDposition = lineOutput.indexOf('|'); 
				String studentID = lineOutput.substring(0,studentIDposition);  
				int studentIDint = Integer.parseInt(studentID);
				String studentResult = lineOutput.substring(lineOutput.lastIndexOf('|') + 1);
				int studentResultInt = Integer.parseInt(studentResult);
				System.out.println(lineOutput);
				
				mapRead.put(studentIDint, studentResultInt);
			}
			input.close();

			
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}	
	
    /**
     * Ends the test
     */
    public void endTest()
    {
        gradeTest(); // test can only be grade when it is ended
        isLocked = true; // it is then automatically locked
    }
    
    /**
     * Grades the test for all sections.
     */
    private int gradeTest()
    {
        totalMarksAwarded = 0;
        Iterator<Section> it = sections.iterator();
        while (it.hasNext()) {
            Section s = it.next();
            totalMarksAwarded += s.gradeSection();
        }
        return totalMarksAwarded;
    }
    
    /**
     * Gets the test marks
     */
    public int getTotalMarks()
    {
        return totalMarksAwarded;
    }
    
    /**
     * String representation of the test
     * 
     */
    public String toString()
    {
        String s = testTitle + "\n\n" + testIntroText + "\n";
        Iterator<Section> it = sections.iterator();
        int sNum = 1;
        while (it.hasNext()) {
            s += "Section " + sNum + ": " + it.next().toString() + "\n\n";
            sNum++;
        }
        return s;
    }
    
     /**
     * Demo for testing
     * 
     */
    public static Test_ getDemoTest()
    {
        Test_ t1 = new Test_("Welcome to the Mancunia English test",
            "This test is designed to test your English langauge skills. " +
            "Each section will test a different aspect of those skills.");
        Question q1 = new Question("What is 2 + 2?");
        Section s1 = new Section("Section A", "Grammar section...", 5);
        t1.addSection(s1);
        Section s2 = new Section("Section B", "Vocabulary section...", 10);
        t1.addSection(s2); 
        s1.addQuestion(q1);
        q1.addAnswer("5", false);
        q1.addAnswer("3", false);
        q1.addAnswer("4", true);
        FTBQ q2 = null;
        try {
            q2 = new FTBQ("You can suck my [thumb].");
        }
        catch(InvalidFTBQFormatException e) {
        }
        s1.addQuestion(q2);
        FTBQ q3 = null;
        try {
            q3 = new FTBQ("You can hold my [hand].");
        }
        catch(InvalidFTBQFormatException e) {
        }
        s1.addQuestion(q3);
        return t1;
    }
    
    /**
     * Demo for testing
     * 
     */
    public static void main(String[] args)
    {
        Test_ t = Test_.getDemoTest();
        //System.out.println(t);
        //FTBQ q1 = (FTBQ)t.getSection(0).getQuestion(0);
        //String a = q1.getTheAnswer().getAnswerText();
        //q1.setGivenAnswer("thumb");
    }
}
