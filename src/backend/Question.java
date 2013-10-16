package backend;

import java.util.ArrayList;

/**
 * A question.
 * 
 * @author Bruce Steedman
 * @version 2013.10.10
 */
public abstract class Question implements java.io.Serializable {
    
    protected String questionText;
    protected int possibleMarks = 1;
    protected int marksAwarded = 0;
    protected String subsectionTitle = "";
    protected ArrayList<Answer> answers;
    
    protected String subSectionTitle;
    protected String subSectionParagraphText;
    
    /**
     * Constructor for questions.
     */
    public Question(String questionText)
    {
        this.questionText = questionText;
        answers = new ArrayList<Answer>();
    }

    /**
     * Constructor for subSection
     * @param String subSectionTitle, String subSectionParagraphText
     */
    public void setsubSection (String subSectionTitle, String subSectionParagraphText) 
    {
    	this.subSectionTitle = subSectionTitle;
    	this.subSectionParagraphText = subSectionParagraphText;
    }
    
    /**
     * @return String subSectionTitle
     */
    public String getsubSectionTitle() 
    {
    	return subSectionTitle;
    }
    
    /**
     * @return String subSectionParagraphText
     */
    public String getsubSectionParagraphText()
    {
    	return subSectionParagraphText;
    }
    
    
    public String getQuestionText()
    {
        return questionText;
    }
    
    public void setQuestionText(String text) // for editing
    {
        questionText = text;
    }
    
    public void setSubsectionTitle(String text) // for editing
    {
        subsectionTitle = text;
    }
    

    /**
     * Returns the mark awarded for the question. Is 0 if wrong or unanswered.
     */
    public int getPossibleMarks()
    {
        return possibleMarks;
    }
    
    public void setPossibleMarks(int marks)
    {
        possibleMarks = marks;
    }
    
    public void addAnswer(Answer answer)
    {
        answers.add(answer);
    }
    
    /**
     * Sets an answer to the question by index, e.g. 0 is the first answer. 
     */
    public void setAnswer(String answerText, boolean isRight, int index)
    {
        answers.add(new Answer(answerText, isRight));
    }
    
    /**
     * Returns an answer by index, for questions with more than one possible answer.
     */
    public Answer getIndexedAnswer(int answerNum)
    {
        return answers.get(answerNum);
    }
    
    protected abstract int getMarksAwarded();
}