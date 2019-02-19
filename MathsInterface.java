package mathProblem;

import java.awt.*;
import javax.swing.*;
import javax.swing.UIManager;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.*;
import javax.swing.Timer;


public class  MathsInterface extends JFrame {

 // declaring variables 
 public JLabel clock;
 public JLabel holderclock;
 public JLabel titleDisplay;

 public JPanel topPanel;
 public JPanel midPanel;
 public JPanel bottomPanel;

 public JButton btnGenerate;
 public JButton btnStart;
 public JButton btnSubmit;
 public JButton btnReset;

 public int correct;
 public int score;

 public long startTime;
 public long endTime;
 public long timeDifference;

 private JTextArea displayArea1;
 private JPanel displayArea2;
 private JTextArea displayArea3;

 public String actualAnswer;
 public String userAnswer;
 
//inner timer class
 public class TimerClock {

  Timer timer;

  public JLabel getclock() {
   JLabel timeHolder = new JLabel();

   timeHolder.setHorizontalAlignment(JLabel.CENTER);
   timer = new Timer(200, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
     Date dateobj = new Date();
     DateFormat dt = new SimpleDateFormat("HH:mm:ss");
     timeHolder.setText(dt.format(dateobj));
    }
   });

   timer.setInitialDelay(200);

   return timeHolder;

  }

  public void startTimer() {
   timer.start();
  }

  public void stopTimer() {
   timer.stop();
  }
 }
 
 ArrayList < Integer > list = new ArrayList < > ();
 MathsInterface.TimerClock t = new  MathsInterface.TimerClock();
 public JTextField[] answer = new JTextField[10];

 public  MathsInterface() {

  super("Math Challenge Simulator v2.0");
  clock = t.getclock();
  holderclock = clock;

  JMenu fileMenu = new JMenu("File"); // create file menu
  JMenuItem saveItem = new JMenuItem("Save"); // create Save menu item
  fileMenu.add(saveItem); // add save item to file menu
  saveItem.addActionListener(new Save()); //click on save saves the info in the txt file

  JMenu editMenu = new JMenu("Edit"); // create Edit menu
  JMenuItem resetItem = new JMenuItem("Reset"); //create reset menu item
  editMenu.add(resetItem); // add reset item to file menu
  resetItem.addActionListener(new ResetListener()); //click on reset resets the frame

  JMenu helpMenu = new JMenu("Help"); // create Help menu
  JMenuItem aboutItem = new JMenuItem("About"); //create about menu item
  helpMenu.add(aboutItem); // add about item to file menu
  aboutItem.addActionListener(new About()); //click on about dispplays another frame

  JMenuBar bar;
  bar = new JMenuBar();

  setJMenuBar(bar); // add menu bar to application
  bar.add(fileMenu); // add file menu to menu bar
  bar.add(editMenu); // add edit menu to menu bar
  bar.add(helpMenu); // add help menu to menu bar

  JPanel contentPanel = new JPanel();
  contentPanel.setLayout(new BorderLayout());
  contentPanel.setBorder(new TitledBorder(new EtchedBorder(), ""));

  //layouts and dimension for panels
  topPanel = new JPanel(new GridLayout(2, 1));
  topPanel.setPreferredSize(new Dimension(800, 50));
  midPanel = new JPanel(new GridLayout(1, 3));
  bottomPanel = new JPanel(new GridLayout(1, 5));

  correct = 0;
  score = 0;

  titleDisplay = new JLabel("Arithmetic Operation Exercise", SwingConstants.CENTER);

  topPanel.add(titleDisplay);
  topPanel.add(clock);


  displayArea1 = new JTextArea(13, 21);
  displayArea1.setPreferredSize(new Dimension(200, 150));
  displayArea1.setBorder(BorderFactory.createLineBorder(Color.blue));
  displayArea1.setEditable(false);
  Font font1 = new Font("SansSerif", Font.BOLD, 21);
  displayArea1.setFont(font1);


  displayArea2 = new JPanel();
  displayArea2.setLayout(new BoxLayout(displayArea2, BoxLayout.PAGE_AXIS));
  displayArea2.setPreferredSize(new Dimension(200, 150));
  displayArea2.setBorder(BorderFactory.createLineBorder(Color.blue));

  for (int i = 0; i < 10; i++) {
   answer[i] = new JTextField(20);
   answer[i].setEditable(false);
   displayArea2.add(answer[i]); //add TextArea
   actualAnswer = answer[i].getText();
  }


  displayArea3 = new JTextArea(13, 21);
  displayArea3.setEditable(false); // disable editing
  displayArea3.setPreferredSize(new Dimension(200, 150));
  displayArea3.setBorder(BorderFactory.createLineBorder(Color.blue));
  
  //if btnGenerate is pressed display the randomly generated question
  btnGenerate = new JButton(new AbstractAction("Generate") {
   @Override
   public void actionPerformed(ActionEvent e) {

    displayArea1.setText(null);
    list.clear();
    btnGenerate.setEnabled(false);
    btnStart.setEnabled(true);

    Division d = new Division();

    for (int i = 0; i < 10; i++) {

     int denominator = d.getden();
     int nominator = d.getnom();
     int result = d.getres();
     list.add(result);
     displayArea1.append("            " + denominator + "/" + nominator + "=\n");
     correct = 0;
    }
   }

  });

  //if btnstart is pressed display and start the timer and allow the user to input numbers
  btnStart = new JButton(new AbstractAction("Start") {
   @Override
   public void actionPerformed(ActionEvent e) {

    for (int i = 0; i < 10; i++) {
     answer[i].setEditable(true);
     answer[i].setOpaque(true);
    }

    btnStart.setEnabled(false);
    btnSubmit.setEnabled(true);
    t.startTimer();
    startTime = System.currentTimeMillis();


   }
  });

  //if submit button is pressed display color of wrong write answer and display final result in 3rd text area
  btnSubmit = new JButton(new AbstractAction("Submit") {
   @Override
   public void actionPerformed(ActionEvent ae) {

    t.stopTimer();

    for (int i = 0; i < 10; i++) {
     actualAnswer = answer[i].getText();
     userAnswer = String.valueOf(list.get(i));
     if (actualAnswer.equals(userAnswer)) {//checking if answer is correct or not
      correct++;
      answer[i].setBackground(Color.GREEN);
     } else {
      answer[i].setBackground(Color.RED);
     }
     endTime = System.currentTimeMillis();
     timeDifference = ((endTime - startTime) / 1000); //calculating time for task

     score = (int)(200 * (correct * 2.0) / (timeDifference));//calculating score through formula
    }
    list.clear();

    displayArea3.append("\nTime:" + timeDifference + " seconds \nCorrect Answers:" + correct + "\nYour Score:" + score);
    btnSubmit.setEnabled(false);

   }
  });

  //if reset button is pressed eveything is new
  btnReset = new JButton(new AbstractAction("Reset") {
   @Override
   public void actionPerformed(ActionEvent e) {

    btnGenerate.setEnabled(true);
    btnStart.setEnabled(false);
    btnSubmit.setEnabled(false);

    displayArea1.setText("");
    displayArea3.setText("");
    t.stopTimer();
    holderclock.setText("");


    for (int i = 0; i < 10; i++) {
     answer[i].setText("");
     answer[i].setEditable(false);
     answer[i].setBackground(Color.WHITE);
     answer[i].setOpaque(false);
    }
   }
  });

  
  
  //if exit button is pressed application closes
  JButton btnExit = new JButton(new AbstractAction("Exit") {
   @Override
   public void actionPerformed(ActionEvent e) {
    System.exit(0);

   }
  });


  midPanel.add(displayArea1); //add display area 1
  midPanel.add(displayArea2); //add display area 2
  midPanel.add(displayArea3); //add display area 3

  //bottom panel
  bottomPanel.setPreferredSize(new Dimension(800, 150));
  TitledBorder title;
  title = BorderFactory.createTitledBorder("Buttons");

  JPanel buttonGrp = new JPanel(new GridLayout());
  bottomPanel.add(buttonGrp);

  buttonGrp.setBorder(new CompoundBorder(new EmptyBorder(10, 0, 10, 0), title));
  buttonGrp.setPreferredSize(new Dimension(800, 100));
  buttonGrp.add(btnGenerate);
  buttonGrp.add(btnStart);
  btnStart.setEnabled(false);
  buttonGrp.add(btnSubmit);
  btnSubmit.setEnabled(false);
  buttonGrp.add(btnReset);
  buttonGrp.add(btnExit);

  //add panels to the contentPanel
  contentPanel.add(topPanel, BorderLayout.NORTH);
  contentPanel.add(midPanel, BorderLayout.CENTER);
  contentPanel.add(bottomPanel, BorderLayout.SOUTH);
  add(contentPanel);

 }

 //save saves the doc in a txt format
 public class Save implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {

   try (FileWriter fw = new FileWriter("TestResult.txt", false); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
    out.println("\nTime:" + timeDifference + " seconds \nCorrect Answers: " + correct + "\n Your Score:" + score);
   } catch (IOException ie) {
    //if the document doesnot save the user might be given the given information
   }

  }
 }

 //about listener that displays the about framew
 public class About implements ActionListener {
     
     

  @Override
  public void actionPerformed(ActionEvent e) {
   if ("About".equals(e.getActionCommand())) {
       
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

       
    JTextArea headings = new JTextArea();
    JFrame awindow = new JFrame();
    JPanel scope = new JPanel(new FlowLayout());
    awindow.setSize(350, 200);
    awindow.setTitle("About Math Challenge Simulator v2.0");
    headings.setOpaque(false);
    headings.setEditable(false);
    Icon information=UIManager.getIcon("OptionPane.informationIcon");
    JLabel iconset= new JLabel();
    iconset.setIcon(information);
    headings.append("Math Challenge Simulator v2.0 (Assignment 1)\n\nBy Dr. Micheal Li, CQU  \n\nCopyright (C) 2017");
    scope.add(iconset, c);
    scope.add(headings, c);
    awindow.add(scope);
    awindow.setDefaultCloseOperation(1);
    awindow.setVisible(true);

    JButton ok = new JButton(new AbstractAction("ok") {
     @Override
     public void actionPerformed(ActionEvent e) {

      awindow.dispose();
     }
    });
    scope.add(ok);


   }

  }
 }

 // reset listener that resets the frame
 public class ResetListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {

   holderclock.setText("");
   t.stopTimer();

   btnGenerate.setEnabled(true);
   btnStart.setEnabled(false);
   btnSubmit.setEnabled(false);

   displayArea1.setText("");
   displayArea3.setText("");

   for (int i = 0; i < 10; i++) {
    answer[i].setText("");
    answer[i].setEditable(false);
    answer[i].setBackground(Color.WHITE);
    answer[i].setOpaque(false);
   }
  }
 }

//main function
 
 public static void main(String[] args) {
   MathsInterface displayFrame = new  MathsInterface();
  displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  displayFrame.setSize(800, 550); //setting dimensions for the frame
  displayFrame.setVisible(true);

 }
}