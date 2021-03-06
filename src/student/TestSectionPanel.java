package student;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import setter.SetterGUI;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import backend.StudentTestController;
import backend.Question;
import backend.Section;

import java.util.Iterator;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * @author Chondrokoukis Dimitrios
 * @contributor Steedman Bruce
 */
public class TestSectionPanel extends JPanel {
    private JLabel labelTimeRemaining;
    private StudentTestController controller;
    private Section section;
    private JTable tableQuestions;
    private ArrayList<Question> questions;
    private int questionPanelsIndex;
    private ArrayList<QuestionPanel> questionPanels;
    private JPanel questionHolderPanel;
    private ImageIcon tickImageIcon, exImageIcon, scaledTickImageIcon, scaledExImageIcon;
    private Image tickImage, scaledTickImage, exImage, scaledExImage;
    
	
    /**
     * Create the panel.
     */
    public TestSectionPanel(final StudentTestController controller, final Section section, Question question) {
    	tickImageIcon = new ImageIcon(TestSectionPanel.class.getResource("/lib/images/check_b.png"));
    	tickImage = tickImageIcon.getImage();
    	exImageIcon = new ImageIcon(TestSectionPanel.class.getResource("/lib/images/delete.png"));
    	exImage = exImageIcon.getImage();
	this.controller = controller;
        this.section = section;
        questionPanelsIndex=0;
	questions = section.getQuestions();
        
	setLayout(new BorderLayout(0, 0));
	JPanel panel = new JPanel();
	add(panel, BorderLayout.NORTH);
	GridBagLayout gbl_panel = new GridBagLayout();
	gbl_panel.columnWidths = new int[]{0, 0};
	gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
	gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_panel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
	panel.setLayout(gbl_panel);
		
	JPanel panel_8 = new JPanel();
	GridBagConstraints gbc_panel_8 = new GridBagConstraints();
	gbc_panel_8.insets = new Insets(0, 0, 5, 0);
	gbc_panel_8.fill = GridBagConstraints.BOTH;
	gbc_panel_8.gridx = 0;
	gbc_panel_8.gridy = 0;
	panel.add(panel_8, gbc_panel_8);
	GridBagLayout gbl_panel_8 = new GridBagLayout();
	gbl_panel_8.columnWidths = new int[]{0, 0, 0};
	gbl_panel_8.rowHeights = new int[]{0, 0};
	gbl_panel_8.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
	gbl_panel_8.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	panel_8.setLayout(gbl_panel_8);
		
	JLabel lblTestA = new JLabel("");
	GridBagConstraints gbc_lblTestA = new GridBagConstraints();
	gbc_lblTestA.anchor = GridBagConstraints.EAST;
	gbc_lblTestA.insets = new Insets(0, 0, 0, 5);
	gbc_lblTestA.gridx = 0;
	gbc_lblTestA.gridy = 0;
	panel_8.add(lblTestA, gbc_lblTestA);
		
	JLabel labelSectionTitle = new JLabel(section.getSectionTitle());
	labelSectionTitle.setFont(new Font("Maiandra GD", Font.BOLD, 20));
	GridBagConstraints gbc_labelSectionTitle = new GridBagConstraints();
	gbc_labelSectionTitle.anchor = GridBagConstraints.WEST;
	gbc_labelSectionTitle.gridx = 1;
	gbc_labelSectionTitle.gridy = 0;
	panel_8.add(labelSectionTitle, gbc_labelSectionTitle);
	
	JButton buttonEdit = new JButton("Edit");
	buttonEdit.setFont(new Font("MV Boli", Font.PLAIN, 15));
	GridBagConstraints gbc_buttonEdit = new GridBagConstraints();
	gbc_buttonEdit.anchor = GridBagConstraints.EAST;
	gbc_buttonEdit.insets = new Insets(0, 0, 5, 0);
	gbc_buttonEdit.gridx = 0;
	gbc_buttonEdit.gridy = 1;
	panel.add(buttonEdit, gbc_buttonEdit);
	if(!MainGui.isSetter){
		buttonEdit.setVisible(false);
	}
		
	JPanel panel_9 = new JPanel();
	GridBagConstraints gbc_panel_9 = new GridBagConstraints();
	gbc_panel_9.fill = GridBagConstraints.BOTH;
	gbc_panel_9.gridx = 0;
	gbc_panel_9.gridy = 2;
	panel.add(panel_9, gbc_panel_9);
	GridBagLayout gbl_panel_9 = new GridBagLayout();
	gbl_panel_9.columnWidths = new int[]{0, 0, 0};
	gbl_panel_9.rowHeights = new int[]{0, 0};
	gbl_panel_9.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
	gbl_panel_9.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	panel_9.setLayout(gbl_panel_9);
		
	labelTimeRemaining = new JLabel("");
	GridBagConstraints gbc_labelTimeRemaining = new GridBagConstraints();
	gbc_labelTimeRemaining.anchor = GridBagConstraints.EAST;
	gbc_labelTimeRemaining.insets = new Insets(0, 0, 0, 5);
	gbc_labelTimeRemaining.gridx = 0;
	gbc_labelTimeRemaining.gridy = 0;
	panel_9.add(labelTimeRemaining, gbc_labelTimeRemaining);
		
	JLabel lblNewLabel_2 = new JLabel("minutes remaining");
	lblNewLabel_2.setFont(new Font("MV Boli", Font.PLAIN, 15));
	GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
	gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 10);
	gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
	gbc_lblNewLabel_2.gridx = 1;
	gbc_lblNewLabel_2.gridy = 0;
	panel_9.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
	JPanel panel_2 = new JPanel();
	add(panel_2, BorderLayout.CENTER);
	panel_2.setLayout(new BorderLayout(0, 0));
		
	JPanel panel_3 = new JPanel();
	panel_2.add(panel_3, BorderLayout.NORTH);
		
	//JLabel labelSubsection = new JLabel("Example Subsection Heading");
	JLabel labelSubsection = new JLabel("");
	labelSubsection.setFont(new Font("MV Boli", Font.PLAIN, 15));
	panel_3.add(labelSubsection);
		
	JLabel label = new JLabel("");
	//JLabel label = new JLabel(">");
	panel_3.add(label);
		
	JPanel scrollPane_1 = new JPanel();
	panel_2.add(scrollPane_1, BorderLayout.WEST);
		
	String q = "Q";
	Object[][] object = new Object[questions.size()][2];
	for(int i=0; i<questions.size();i++){
            object[i][0]=q+Integer.toString(i+1);
	}
	
	String[] columnNames = new String[] {"Quest", ""};
	
	DefaultTableModel model = new DefaultTableModel(object, columnNames);
	tableQuestions = new JTable( model )
    {
        //  Returning the Class of each column will allow different
        //  renderers to be used based on Class
        public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }
    };
    tableQuestions.setPreferredScrollableViewportSize(tableQuestions.getPreferredSize());
	tableQuestions.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(questionPanels.get(questionPanelsIndex).isAnswered()){
            	tableQuestions.setValueAt(scaledTickImageIcon, questionPanelsIndex, 1);
            }
            else{
            	tableQuestions.setValueAt(scaledExImageIcon, questionPanelsIndex, 1);
            }
			((CardLayout)questionHolderPanel.getLayout()).first(questionHolderPanel);
			for(int i=0; i<tableQuestions.rowAtPoint(e.getPoint()); i++){
				((CardLayout)questionHolderPanel.getLayout()).next(questionHolderPanel);
			}
			questionPanelsIndex = tableQuestions.rowAtPoint(e.getPoint());
		}
	});
	tableQuestions.setModel(new DefaultTableModel(
            object,
            columnNames
	));
	scrollPane_1.add(tableQuestions);
	
	scaledTickImage = tickImage.getScaledInstance(-1, tableQuestions.getRowHeight(),  java.awt.Image.SCALE_SMOOTH);  
	scaledTickImageIcon = new ImageIcon(scaledTickImage);
	scaledExImage = exImage.getScaledInstance(-1, tableQuestions.getRowHeight(),  java.awt.Image.SCALE_SMOOTH);
	scaledExImageIcon = new ImageIcon(scaledExImage);
	
	for(int i=0; i<tableQuestions.getRowCount(); i++){
		tableQuestions.setValueAt(scaledExImageIcon, i, 1);
	}
		
	JPanel panel_5 = new JPanel();
	panel_2.add(panel_5, BorderLayout.CENTER);
	GridBagLayout gbl_panel_5 = new GridBagLayout();
	gbl_panel_5.columnWidths = new int[]{0, 0};
	gbl_panel_5.rowHeights = new int[]{0, 0, 0};
	gbl_panel_5.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_panel_5.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
	panel_5.setLayout(gbl_panel_5);
		
	final JScrollPane scrollPane = new JScrollPane();
	GridBagConstraints gbc_scrollPane = new GridBagConstraints();
	gbc_scrollPane.fill = GridBagConstraints.BOTH;
	gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
	gbc_scrollPane.gridx = 0;
	gbc_scrollPane.gridy = 0;
	panel_5.add(scrollPane, gbc_scrollPane);
		
	questionHolderPanel = new JPanel();
	scrollPane.setViewportView(questionHolderPanel);
	questionHolderPanel.setLayout(new CardLayout(0, 0));
		
	questionPanels = new ArrayList<QuestionPanel>();
        Iterator<Question> it = questions.iterator();
        int qNum = 0;
        QuestionPanel qp = null;
	while(it.hasNext()){
            try {
                qp = QuestionPanelFactory.getInstance().createQuestionPanel(it.next());
                qNum++;
                qp.setQuestionNumber(qNum);
                qp.setPath((String)section.getQuestionsAndPathsWithoutSection().get(qNum-1)[0][1]);
            }
            catch(Exception e) {
                //Handle exceptions if for any reason a panel cannot be instantiated
            }
            questionHolderPanel.add(qp, "name_"+ Integer.toString(qNum));
            questionPanels.add(qp);
	}
	scrollPane.setMinimumSize(scrollPane.getComponent(0).getSize());	
	JPanel panel_7 = new JPanel();
	GridBagConstraints gbc_panel_7 = new GridBagConstraints();
	gbc_panel_7.fill = GridBagConstraints.BOTH;
	gbc_panel_7.gridx = 0;
	gbc_panel_7.gridy = 1;
	panel_5.add(panel_7, gbc_panel_7);
	GridBagLayout gbl_panel_7 = new GridBagLayout();
	gbl_panel_7.columnWidths = new int[]{0, 0, 0};
	gbl_panel_7.rowHeights = new int[]{0, 0};
	gbl_panel_7.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
	gbl_panel_7.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	panel_7.setLayout(gbl_panel_7);
		
	JButton buttonPrevious = new JButton("Previous");
	buttonPrevious.setFont(new Font("MV Boli", Font.PLAIN, 15));
	buttonPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(questionPanelsIndex>0){
        			//Puts tick in the table
                    if(questionPanels.get(questionPanelsIndex).isAnswered()){
                    	tableQuestions.setValueAt(scaledTickImageIcon, questionPanelsIndex, 1);
                    }
                    else{
                    	tableQuestions.setValueAt(scaledExImageIcon, questionPanelsIndex, 1);
                    }
                    questionPanelsIndex--;
                    ((CardLayout)questionHolderPanel.getLayout()).previous(questionHolderPanel);
                    scrollPane.setMinimumSize(scrollPane.getComponent(0).getSize());
                }
            }
	});
	GridBagConstraints gbc_buttonPrevious = new GridBagConstraints();
	gbc_buttonPrevious.insets = new Insets(0, 0, 0, 5);
	gbc_buttonPrevious.anchor = GridBagConstraints.EAST;
	gbc_buttonPrevious.gridx = 0;
	gbc_buttonPrevious.gridy = 0;
	panel_7.add(buttonPrevious, gbc_buttonPrevious);
		
	JButton buttonNext = new JButton("Next");
	buttonNext.setFont(new Font("MV Boli", Font.PLAIN, 15));
	buttonNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(questionPanelsIndex<questions.size()-1){
        			//Puts tick in the table
                    if(questionPanels.get(questionPanelsIndex).isAnswered()){
                    	tableQuestions.setValueAt(scaledTickImageIcon, questionPanelsIndex, 1);
                    }
                    else{
                    	tableQuestions.setValueAt(scaledExImageIcon, questionPanelsIndex, 1);
                    }
                    questionPanelsIndex++;
                    ((CardLayout)questionHolderPanel.getLayout()).next(questionHolderPanel);
                    scrollPane.setMinimumSize(scrollPane.getComponent(0).getSize());
                }
            }
	});
	GridBagConstraints gbc_buttonNext = new GridBagConstraints();
	gbc_buttonNext.anchor = GridBagConstraints.WEST;
	gbc_buttonNext.gridx = 1;
	panel_7.add(buttonNext, gbc_buttonNext);
		
	JPanel panel_1 = new JPanel();
	add(panel_1, BorderLayout.SOUTH);
	GridBagLayout gbl_panel_1 = new GridBagLayout();
	gbl_panel_1.columnWidths = new int[]{117, 0};
	gbl_panel_1.rowHeights = new int[]{29, 0};
	gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	panel_1.setLayout(gbl_panel_1);
		
	JButton buttonSubmitSection = new JButton("Submit Section");
	buttonSubmitSection.setFont(new Font("MV Boli", Font.PLAIN, 15));
	buttonSubmitSection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	controller.endSection(TestSectionPanel.this);
            }
	});
	GridBagConstraints gbc_buttonSubmitSection = new GridBagConstraints();
	gbc_buttonSubmitSection.anchor = GridBagConstraints.NORTH;
	gbc_buttonSubmitSection.gridx = 0;
	gbc_buttonSubmitSection.gridy = 0;
	panel_1.add(buttonSubmitSection, gbc_buttonSubmitSection);
	
	if(question != null){
		for(int i=0; i<questionPanels.size(); i++){
			if(question.equals(questions.get(i))){
				questionPanelsIndex = i;
				for(int j=0; j<questionPanelsIndex; j++){
					((CardLayout)questionHolderPanel.getLayout()).next(questionHolderPanel);
				}
			}
			break;
		}
	}
	
	//editButtonListener
	buttonEdit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {

			//open the setter GUI
	    MainGui.parent.setVisible(true);
	    
	    //suspend this
	    MainGui.frame.setVisible(false);
			
		}
	});
	
    }
    
    public ArrayList<QuestionPanel> getQuestionPanels() {
        return questionPanels;
    }

    public Section getSection() {
        return section;
    }
    
    /*
     * Return the JLabel that will have the countdown
     */
    public JLabel getLabelForTimer(){
    	return this.labelTimeRemaining;
    }
}
