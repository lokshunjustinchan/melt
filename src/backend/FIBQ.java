package backend;

import java.util.Iterator;

/**
 * A fill in the blank question
 * 
 * @author Bruce Steedman 
 * @version 2013.09.27
 */
public class FIBQ extends Question
{
    
    public StudentAnswer studentAnswer;

    private String qFirstPart, qSecondPart;
    private String givenAnswer = "";
    String label = "";
    
    /**
     * Constructor for objects of class FIBQ
     * 
     * @param Question text string must contain the answer string wrapped in [...] to be valid
     */
    public FIBQ(String ftbqText, Object parent) throws InvalidFTBQFormatException {
        super(ftbqText, parent);
        String s[] = parseQandA(ftbqText);
        this.qFirstPart = s[1];
        this.qSecondPart = s[2];
        this.addAnswer(new Answer(s[0], true));
        this.studentAnswer = new StudentAnswer();
    }
    
    public FIBQ(String ftbqText, String label, Object parent) throws InvalidFTBQFormatException {
      super(ftbqText, parent);
      String s[] = parseQandA(ftbqText);
      this.qFirstPart = s[1];
      this.qSecondPart = s[2];
      this.addAnswer(new Answer(s[0], true));
      this.label = label;
      this.studentAnswer = new StudentAnswer();
  }
    
    public void checkQuestion(String Question) throws InvalidFTBQFormatException
    {
    	String parts[]=parseQandA(Question);
    	this.qFirstPart =parts[1];
        this.qSecondPart = parts[2];
        this.addAnswer(new Answer(parts[0], true));
        this.studentAnswer = new StudentAnswer();
    	
    }
    
    public String getQFirstPart()
    {
        return qFirstPart;
    }
    
    public String getQSecondPart()
    {
        return qSecondPart;
    }

    public void setGivenAnswer(String answer) {
        studentAnswer.setAnswer(answer);
        givenAnswer = answer;
    }
    
    public String getGivenAnswer() {
        return givenAnswer;
    }
    
    
    //for manual marking returns
    public StudentAnswer getStudentAnswer()
    {
      return studentAnswer;
    }

    public void setStudentAnswer(StudentAnswer studentAnswer)
    {
      this.studentAnswer = studentAnswer;
    }
    
    public void setStudentAnswerMarks(int marks)
    {
      studentAnswer.setMarksAwarded(marks);
    }
    
    public int getStudentAnswerMarks()
    {
      return studentAnswer.getMarksAwarded();
    }
    
    public void setStudentAnswerFeedback(String feedback)
    {
      studentAnswer.setFeedback(feedback);
    }
    
    public String getStudentAnswerFeedback()
    {
      return studentAnswer.getFeedback();
    }
    @Override
    public int getMarksAwarded()
    {
        Iterator<Answer> it = answers.iterator();
        while(it.hasNext()) {
            if(givenAnswer.equals(it.next().getAnswerText())) {
                return possibleMarks;
            }      
        }
        return 0;
    }
    
     /**
     * Parses the question text for the answer, exception thrown on failure
     * 
     * @return 3 strings representing: the answer string and the question text before and after the blank
     */
    private String[] parseQandA(String rawQtext) throws InvalidFTBQFormatException
    {
        String aText, firstPartQ, secondPartQ;
        try {
            int firstBracket = rawQtext.indexOf("[");
            int lastBracket = rawQtext.lastIndexOf("]");
            aText = rawQtext.substring(firstBracket + 1, lastBracket);
            firstPartQ = rawQtext.substring(0, firstBracket);
            secondPartQ = rawQtext.substring(lastBracket + 1, rawQtext.length());
            if(lastBracket < firstBracket) throw new StringIndexOutOfBoundsException(); // Checks for ...]...[... case
        }
        catch (StringIndexOutOfBoundsException e){ 
             throw  new InvalidFTBQFormatException();
        }
        return new String[] {aText, firstPartQ, secondPartQ};
    }
    
     /**
     * String representation of a fill in the blanks question
     *  
     */
    public String getFIBQ()
    {
        return qFirstPart + "<BLANK>"+ qSecondPart + "\n";
    }
    
    @Override
    public String toString()
    {
    	return "FIBQ " + label;
    }

	@Override
	public void setMarksAwarded(int marks) {
		
	}
}
