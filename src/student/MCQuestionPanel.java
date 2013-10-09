package student;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JRadioButton;

import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;

import backend.Question;
import backend.Answer;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JCheckBox;

public class MCQuestionPanel extends QuestionPanel {
	
	private static final long serialVersionUID = 6648494571735411196L;
        private Question question;
        private ArrayList<Answer> answers;
        private JPanel panel_1;
        
	/**
	 * Create the panel.
	 */
	public MCQuestionPanel(Question question) {
            this.question = question;
		answers = question.getAllAnswers();
            setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblQuestion = new JLabel("Question 1");
		GridBagConstraints gbc_lblQuestion = new GridBagConstraints();
		gbc_lblQuestion.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuestion.gridx = 0;
		gbc_lblQuestion.gridy = 0;
		add(lblQuestion, gbc_lblQuestion);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panel_2.add(panel, gbc_panel);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		JLabel lblHowMuchIs = new JLabel(question.getQuestionText());
		panel.add(lblHowMuchIs);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel_2.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{225, 225, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
                createRadioButtons();
	
	}

    private void createRadioButtons() {
        Iterator<Answer> it = answers.iterator();
        int i=0;
        while(it.hasNext()){
            JCheckBox checkAnswer = new JCheckBox(it.next().getAnswerText());
            GridBagConstraints gbc_radioButton = new GridBagConstraints();
            gbc_radioButton.fill = GridBagConstraints.BOTH;
            gbc_radioButton.insets = new Insets(0, 0, 0, 5);
            gbc_radioButton.gridx = 0;
            gbc_radioButton.gridy = i;
            panel_1.add(checkAnswer, gbc_radioButton);
            i++;
        }
    }
    
    /**
     * Iterates through each check box, for any that are selected the associated Answer is selected.
     */
    @Override
    public void submitAnswer() {
        for(int i=0; i<panel_1.getComponentCount(); i++){
            if(panel_1.getComponent(i) instanceof JCheckBox){
                if(((JCheckBox)panel_1.getComponent(i)).isSelected()){
                    answers.get(i).select();
                }
            }
        }
    }
}
