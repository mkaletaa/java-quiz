import questions.*;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
public class MyPanel extends JPanel {
    private int points;
    private JLabel questionLabel, pointsLabel, endScore;
    private JButton buttonA, buttonB, buttonC, buttonD, chooseCat;

    private List<Integer> qnr = new ArrayList<>();
    private int questionNr;
    private Questions questions;
    public MyPanel(String cat) {
        points = 0;
        questionLabel = new JLabel("" + questionNr);
        pointsLabel = new JLabel("wynik: " + points);
        buttonA = new JButton("");
        buttonB = new JButton("");
        buttonC = new JButton("");
        buttonD = new JButton("");

        questionNr = 0;

        switch(cat){
            case "maths" ->  questions = new Maths();
            case "geography" -> questions = new Geography();
            case "animals" -> questions = new Animals();
            case "history" -> questions = new History();
        }


        for (int i = 0; i < questions.getQuestions().size(); i++) {
            qnr.add(i);
        }
        Collections.shuffle(qnr);

        buttonA.addActionListener(e -> {
            click(questions, buttonA.getText(), "A");
        });

        buttonB.addActionListener(e->{
            click(questions, buttonB.getText(), "B");
        });

        buttonC.addActionListener(e->{
            click(questions, buttonC.getText(), "C");
        });

        buttonD.addActionListener(e->{
            click(questions, buttonD.getText(), "D");
        });

        add(questionLabel);
        add(pointsLabel);
        setLayout(new GridLayout(3, 2));
        add(buttonA);
        add(buttonB);
        add(buttonC);
        add(buttonD);
        newQuestion(questions);

    }

    private void click(Questions questions, String answer, String btn){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //end game if out of questions
            if(questionNr==questions.getQuestions().size()) {
                end();
            }

            if(questionNr<questions.getQuestions().size()) {
                newQuestion(questions);
            }
            else questionLabel.setText("koniec pyta??");

                buttonA.setBackground(null);
                buttonB.setBackground(null);
                buttonC.setBackground(null);
                buttonD.setBackground(null);

                buttonA.setEnabled(true);
                buttonB.setEnabled(true);
                buttonC.setEnabled(true);
                buttonD.setEnabled(true);

                }
            }, 2000);

        //checking if the answer is correct or not
        answerCheck(questions, answer, btn);
    }

    private void end(){

        chooseCat = new JButton("Wybierz kategori??");
        endScore = new JLabel("Gratulacje, zdobywasz "+points+" punkt??w!");
        setLayout(new GridLayout(8, 1));
        add(endScore, 0);
        add(chooseCat, 1);

        questionLabel.setVisible(false);
        pointsLabel.setVisible(false);
        buttonA.setVisible(false);
        buttonB.setVisible(false);
        buttonC.setVisible(false);
        buttonD.setVisible(false);

        chooseCat.addActionListener(e->{
            remove(questionLabel);
            remove(pointsLabel);
            remove(buttonA);
            remove(buttonB);
            remove(buttonC);
            remove(buttonD);
            remove(chooseCat);
            remove(endScore);
            Category.chooseCat();
        });
    }

    private void newQuestion(Questions questions){
        List<String> ans = new ArrayList<>();
        ans.add(questions.getAnswers1().get(qnr.get(questionNr)));
        ans.add(questions.getAnswers2().get(qnr.get(questionNr)));
        ans.add(questions.getAnswers3().get(qnr.get(questionNr)));
        ans.add(questions.getAnswers4().get(qnr.get(questionNr)));

        Collections.shuffle(ans);

        questionLabel.setText(""+(questionNr+1)+". "+questions.getQuestion(qnr.get(questionNr)));
        buttonA.setText(ans.get(0));
        buttonB.setText(ans.get(1));
        buttonC.setText(ans.get(2));
        buttonD.setText(ans.get(3));
        questionNr ++;
    }

    private void answerCheck(Questions questions, String answer, String btn){
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

        if(questionNr!=0){
            if(answer.equals(questions.getCorrectAnswers().get(qnr.get(questionNr-1)))) {
                points++;
                pointsLabel.setText("wynik: " + points);

                switch(btn){
                    case "A" -> buttonA.setBackground(new Color(0, 255, 0));
                    case "B" -> buttonB.setBackground(new Color(0, 255, 0));
                    case "C" -> buttonC.setBackground(new Color(0, 255, 0));
                    case "D" -> buttonD.setBackground(new Color(0, 255, 0));
                }
            }else {

                if(buttonA.getText().equals(questions.getCorrectAnswers().get(qnr.get(questionNr-1))))
                    buttonA.setBackground(new Color(0, 255, 0));
                if(buttonB.getText().equals(questions.getCorrectAnswers().get(qnr.get(questionNr-1))))
                    buttonB.setBackground(new Color(0, 255, 0));
                if(buttonC.getText().equals(questions.getCorrectAnswers().get(qnr.get(questionNr-1))))
                    buttonC.setBackground(new Color(0, 255, 0));
                if(buttonD.getText().equals(questions.getCorrectAnswers().get(qnr.get(questionNr-1))))
                    buttonD.setBackground(new Color(0, 255, 0));

                switch (btn) {
                    case "A" -> buttonA.setBackground(new Color(255, 0, 0));
                    case "B" -> buttonB.setBackground(new Color(255, 0, 0));
                    case "C" -> buttonC.setBackground(new Color(255, 0, 0));
                    case "D" -> buttonD.setBackground(new Color(255, 0, 0));
                }
            }
        }
    }
}
