package student;

import java.util.HashMap;

import backend.EssayQ;
import backend.Question;
import backend.FIBQ;
import backend.MCQ;
import backend.SlotQ;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory to produce question panels according to the question type.
 * It knows all question types and the question panels to be used in each case.
 * 
 * @author Bruce Steedman
 * @version 2013.10.12
 */
public enum QuestionPanelFactory {
    INSTANCE; // Enum ensure it exists as a singleton
    
    private static HashMap<Class, Class> questionPanelMap = new HashMap<>();
    static {
       questionPanelMap.put(FIBQ.class, FTBQuestionPanel.class);
       questionPanelMap.put(MCQ.class, MCQuestionPanel.class);
       questionPanelMap.put(SlotQ.class, SlotQuestionPanel.class);
       questionPanelMap.put(EssayQ.class, EssayQuestionPanel.class);
    }
    
    public static QuestionPanelFactory getInstance() { return INSTANCE; }
    
    /**
     * Method to create the appropriate question panel for the given question type - uses reflection. 
     */
    public QuestionPanel createQuestionPanel(Question question) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class questionClass = question.getClass();
        Class questionPanelClass = questionPanelMap.get(questionClass);
        Constructor productConstructor = questionPanelClass.getDeclaredConstructor(questionClass);
	return (QuestionPanel)productConstructor.newInstance(question);
    }
}