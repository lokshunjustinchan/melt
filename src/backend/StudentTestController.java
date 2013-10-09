package backend;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import student.FIBQuestionPanel;
import student.FinishTestPanel;
import student.MCQuestionPanel;
import student.MainGui;
import student.QuestionPanel;
import student.TestSectionPanel;

/**
 * This class delegates all UI functionality to the application 'backend' for
 * the use case of a student taking a test.
 * 
 * @author Bruce Steedman
 * @version 2013.10.08
 */
public class StudentTestController {
    
    private Test_ test;
    
    /**
     * Constructor instantiates a demo test.
     */
    public StudentTestController() {
        this.test = Test_.getDemoTest();
    }
    
    public Test_ getTest() {
        return test;
    }
    
    public void startSection(Section section) {
        TestSectionPanel testSectionPanel = new TestSectionPanel(this, section);
        MainGui.setComposite(testSectionPanel);
        SectionTimer timer = new SectionTimer(this, testSectionPanel, section.getSectionTime());
    }
    
    /**
     * TBC
     */
    public void endSection(TestSectionPanel sectionPanel) {
        Iterator<QuestionPanel> it = sectionPanel.getJPanelQuestions().iterator();
        while(it.hasNext()) {
            QuestionPanel questionPanel = it.next();
            questionPanel.submitAnswer(); // Calls method according the subclass of QuestionPanel
        }
        String sectionMarks = String.valueOf(sectionPanel.getSection().gradeSection());
        FinishTestPanel finishpanel = new FinishTestPanel(sectionMarks);
        MainGui.setComposite(finishpanel);
    }
}