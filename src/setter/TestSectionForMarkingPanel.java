package setter;

import javax.swing.JPanel;

import backend.EssayQ;
import backend.FIBQ;
import backend.MCQ;
import backend.Question;
import backend.Section;
import backend.SlotQ;
import backend.StudentTestController;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSplitPane;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import student.EssayQuestionPanel;
import student.FTBQuestionPanel;
import student.MCQuestionPanel;
import student.QuestionPanel;
import student.SlotQuestionPanel;
import student.TestSectionPanel;
import sun.security.jca.GetInstance.Instance;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Dimitrios Chondrokoukis
 *
 */

public class TestSectionForMarkingPanel extends JPanel {
	private JTable table;
	private ArrayList<Question> questions;
	private ArrayList<JPanel[][]> questionPanels;
	private SetterTestController controller;
	private Section section;
	private JTextField textFieldMarks;
	private int index=0;
	private JScrollPane scrollPaneSetter;
	private JScrollPane scrollPaneStudent;
	private ImageIcon tickImageIcon, QMarkImageIcon, scaledTickImageIcon, scaledQMarkImageIcon;
    private Image tickImage, scaledTickImage, QMarkImage, scaledQMarkImage;
    
	/**
	 * Create the panel.
	 */
	public TestSectionForMarkingPanel(SetterTestController controller, Section section) {
		tickImageIcon = new ImageIcon(TestSectionPanel.class.getResource("/lib/images/check_b.png"));
    	tickImage = tickImageIcon.getImage();
    	QMarkImageIcon = new ImageIcon(TestSectionPanel.class.getResource("/lib/images/question.png"));
    	QMarkImage = QMarkImageIcon.getImage();
		this.controller = controller;
		this.section = section;
		questions = new ArrayList<>();
		questionPanels = new ArrayList<>();
		questions = section.getQuestions();
		for(int i=0; i<questions.size(); i++){
			JPanel[][] questionpanel = new JPanel[1][2];
			if((questions.get(i) instanceof MCQ)==true){
				MCQuestionPanel setterPanel = new MCQuestionPanel((MCQ)questions.get(i), false);
				MCQuestionPanel studentPanel = new MCQuestionPanel((MCQ)questions.get(i), true);
				questionpanel[0][0] = setterPanel;
				questionpanel[0][1] = studentPanel;
				questionPanels.add(questionpanel);
			}
			else if((questions.get(i) instanceof FIBQ)==true){
				FTBQuestionPanel setterPanel = new FTBQuestionPanel((FIBQ)questions.get(i), false);
				FTBQuestionPanel studentPanel = new FTBQuestionPanel((FIBQ)questions.get(i), true);
				setterPanel.setQuestionNumber(i);
				studentPanel.setQuestionNumber(i);
				questionpanel[0][0] = setterPanel;
				questionpanel[0][1] = studentPanel;
				questionPanels.add(questionpanel);
			}
			else if((questions.get(i) instanceof SlotQ)==true){
				SlotQuestionPanel setterPanel = new SlotQuestionPanel((SlotQ)questions.get(i), false);
				SlotQuestionPanel studentPanel = new SlotQuestionPanel((SlotQ)questions.get(i), true);
				setterPanel.setQuestionNumber(i);
				studentPanel.setQuestionNumber(i);
				questionpanel[0][0] = setterPanel;
				questionpanel[0][1] = studentPanel;
				questionPanels.add(questionpanel);
			}
			else if((questions.get(i) instanceof EssayQ)==true){
				EssayQuestionPanel setterPanel = new EssayQuestionPanel((EssayQ)questions.get(i), false);
				EssayQuestionPanel studentPanel = new EssayQuestionPanel((EssayQ)questions.get(i), true);
				setterPanel.setQuestionNumber(i);
				studentPanel.setQuestionNumber(i);
				questionpanel[0][0] = setterPanel;
				questionpanel[0][1] = studentPanel;
				questionPanels.add(questionpanel);
			}
		}
		setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		JLabel lblSection = new JLabel(section.getSectionTitle());
		lblSection.setFont(new Font("Maiandra GD", Font.BOLD, 20));
		topPanel.add(lblSection);
		
		JPanel leftPanel = new JPanel();
		add(leftPanel, BorderLayout.WEST);
		
		String q = "Q";
		Object[][] object = new Object[questions.size()][2];
		for(int i=0; i<questions.size();i++){
	            object[i][0]=q+Integer.toString(i+1);
		}
		
		String[] columnNames = new String[] {"Quest", ""};
		
		DefaultTableModel model = new DefaultTableModel(object, columnNames);
		table = new JTable( model )
	    {
	        //  Returning the Class of each column will allow different
	        //  renderers to be used based on Class
	        public Class getColumnClass(int column)
	        {
	            return getValueAt(0, column).getClass();
	        }
	    };
	    table.setPreferredScrollableViewportSize(table.getPreferredSize());
	    table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SaveMarking();
				index = table.rowAtPoint(e.getPoint());
				scrollPaneSetter.setViewportView(questionPanels.get(index)[0][0]);
				scrollPaneStudent.setViewportView(questionPanels.get(index)[0][1]);
			}
		});
	    table.setModel(new DefaultTableModel(
	            object,
	            columnNames
		));
		leftPanel.add(table);
		
		scaledTickImage = tickImage.getScaledInstance(-1, table.getRowHeight(),  java.awt.Image.SCALE_SMOOTH);  
    	scaledTickImageIcon = new ImageIcon(scaledTickImage);
    	scaledQMarkImage = QMarkImage.getScaledInstance(-1, table.getRowHeight(),  java.awt.Image.SCALE_SMOOTH);
    	scaledQMarkImageIcon = new ImageIcon(scaledQMarkImage);
    	
    	UpdateTable();
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMark = new JPanel();
		centerPanel.add(panelMark, BorderLayout.NORTH);
		GridBagLayout gbl_panelMark = new GridBagLayout();
		gbl_panelMark.columnWidths = new int[]{290, 0};
		gbl_panelMark.rowHeights = new int[]{16, 0};
		gbl_panelMark.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelMark.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelMark.setLayout(gbl_panelMark);
		
		JLabel lblMarks = new JLabel("Marks:");
		lblMarks.setFont(new Font("MV Boli", Font.PLAIN, 15));
		GridBagConstraints gbc_lblMarks = new GridBagConstraints();
		gbc_lblMarks.anchor = GridBagConstraints.EAST;
		gbc_lblMarks.insets = new Insets(0, 0, 0, 10);
		gbc_lblMarks.fill = GridBagConstraints.VERTICAL;
		gbc_lblMarks.gridx = 0;
		gbc_lblMarks.gridy = 0;
		panelMark.add(lblMarks, gbc_lblMarks);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1);
		centerPanel.add(splitPane, BorderLayout.CENTER);
		
		JPanel rightAnswerPanel = new JPanel();
		splitPane.setLeftComponent(rightAnswerPanel);
		rightAnswerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		rightAnswerPanel.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblThisIsThe = new JLabel("This is the right answer");
		lblThisIsThe.setFont(new Font("MV Boli", Font.PLAIN, 15));
		lblThisIsThe.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblThisIsThe);
		
		scrollPaneSetter = new JScrollPane();
		rightAnswerPanel.add(scrollPaneSetter, BorderLayout.CENTER);
		
		JPanel studentAnswerPanel = new JPanel();
		splitPane.setRightComponent(studentAnswerPanel);
		studentAnswerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		studentAnswerPanel.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblThisIsStudents = new JLabel("This is student's answer");
		lblThisIsStudents.setFont(new Font("MV Boli", Font.PLAIN, 15));
		lblThisIsStudents.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblThisIsStudents);
		
		scrollPaneStudent = new JScrollPane();
		studentAnswerPanel.add(scrollPaneStudent, BorderLayout.CENTER);
		
		scrollPaneSetter.setViewportView(questionPanels.get(index)[0][0]);
		scrollPaneStudent.setViewportView(questionPanels.get(index)[0][1]);
		
		JPanel panel = new JPanel();
		centerPanel.add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{227, 0};
		gbl_panel.rowHeights = new int[]{38, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel totalMarkPanel = new JPanel();
		GridBagConstraints gbc_totalMarkPanel = new GridBagConstraints();
		gbc_totalMarkPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_totalMarkPanel.gridx = 0;
		gbc_totalMarkPanel.gridy = 0;
		panel.add(totalMarkPanel, gbc_totalMarkPanel);
		
		JLabel lblTotalMarks = new JLabel("Total Marks:");
		lblTotalMarks.setFont(new Font("MV Boli", Font.PLAIN, 15));
		totalMarkPanel.add(lblTotalMarks);
		
		textFieldMarks = new JTextField();
		textFieldMarks.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(questions.get(index) instanceof SlotQ){
					((SlotQ)questions.get(index)).setMarked(true);
	    		}
	    		else if(questions.get(index) instanceof EssayQ){
	    			((EssayQ)questions.get(index)).setMarked(true);
	    		}
			}
		});
		totalMarkPanel.add(textFieldMarks);
		textFieldMarks.setColumns(10);
		textFieldMarks.setText(Integer.toString(questions.get(index).getMarksAwarded()));
		
		JPanel feedbackPanel = new JPanel();
		GridBagConstraints gbc_feedbackPanel = new GridBagConstraints();
		gbc_feedbackPanel.anchor = GridBagConstraints.NORTH;
		gbc_feedbackPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_feedbackPanel.gridx = 0;
		gbc_feedbackPanel.gridy = 1;
		panel.add(feedbackPanel, gbc_feedbackPanel);
		GridBagLayout gbl_feedbackPanel = new GridBagLayout();
		gbl_feedbackPanel.columnWidths = new int[]{59, 0};
		gbl_feedbackPanel.rowHeights = new int[]{16, 0, 0};
		gbl_feedbackPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_feedbackPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		feedbackPanel.setLayout(gbl_feedbackPanel);
		
		JLabel lblFeedback = new JLabel("Feedback:");
		lblFeedback.setFont(new Font("MV Boli", Font.PLAIN, 15));
		GridBagConstraints gbc_lblFeedback = new GridBagConstraints();
		gbc_lblFeedback.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFeedback.anchor = GridBagConstraints.NORTH;
		gbc_lblFeedback.gridx = 0;
		gbc_lblFeedback.gridy = 0;
		feedbackPanel.add(lblFeedback, gbc_lblFeedback);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		feedbackPanel.add(scrollPane, gbc_scrollPane);
		
		JTextArea textAreaFeedback = new JTextArea();
		textAreaFeedback.setRows(4);
		scrollPane.setViewportView(textAreaFeedback);
		
		textAreaFeedback.setText(questions.get(index).getFeedback());
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		
		JButton backButton = new JButton("<");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(index>0){
					SaveMarking();
					UpdateTable();
					index--;
					scrollPaneSetter.setViewportView(questionPanels.get(index)[0][0]);
					scrollPaneStudent.setViewportView(questionPanels.get(index)[0][1]);
				}
			}
		});
		panel_1.add(backButton);
		
		JButton nextButton = new JButton(">");
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SaveMarking();
				UpdateTable();
				if(index<questionPanels.size()-1){
					index++;
					scrollPaneSetter.setViewportView(questionPanels.get(index)[0][0]);
					scrollPaneStudent.setViewportView(questionPanels.get(index)[0][1]);
				}
			}
		});
		panel_1.add(nextButton);
		
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[]{75, 0};
		gbl_bottomPanel.rowHeights = new int[]{29, 0};
		gbl_bottomPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_bottomPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		bottomPanel.setLayout(gbl_bottomPanel);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SaveMarking();
				
			}
		});
		btnBack.setFont(new Font("MV Boli", Font.PLAIN, 15));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		bottomPanel.add(btnBack, gbc_btnBack);
		
		
	}
	
	private void SaveMarking() {
		questions.get(index).setFeedback(textFieldMarks.getText());
		if(questions.get(index) instanceof SlotQ){
			((SlotQ)(questions.get(index))).setMarksAwarded(Integer.parseInt(textFieldMarks.getText().equals("")?"0":textFieldMarks.getText()));
		}
		if(questions.get(index) instanceof EssayQ){
			((EssayQ)(questions.get(index))).setMarksAwarded(Integer.parseInt(textFieldMarks.getText().equals("")?"0":textFieldMarks.getText()));
		}
	}

	private void UpdateTable() {
		for(int i=0;i<questions.size(); i++){
    		if(questions.get(i) instanceof SlotQ){
    			if(((SlotQ)questions.get(i)).isMarked()){
    				table.setValueAt(scaledTickImageIcon, i, 1);
    			}
    			else{
    				table.setValueAt(scaledQMarkImageIcon, i, 1);
    			}
    		}
    		else if(questions.get(i) instanceof EssayQ){
    			if(((EssayQ)questions.get(i)).getWholeStudentAnswer().isMarked()){
    				table.setValueAt(scaledTickImageIcon, i, 1);
    			}
    			else{
    				table.setValueAt(scaledQMarkImageIcon, i, 1);
    			}
    		}
    		else{
    			table.setValueAt(scaledQMarkImageIcon, i, 1);
    		}
    	}
	}

}
