package be.ugent.oomt.quizapp.model;

import android.databinding.ObservableField;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QuizMaster {

    private final Map<Integer,Question> questionRepository;
    private Iterator<Question> iterator;
    private final ObservableField<Question> currentQuestion = new ObservableField<>();
    public final ObservableField<String> userAnswer= new ObservableField<>();
    public final ObservableField<String> feedback = new ObservableField<>();

    private static QuizMaster mySingleton;

    private QuizMaster(){
        questionRepository = new HashMap<Integer, Question>();
        loadQuestions();
        iterator = questionRepository.values().iterator();
        nextQuestion();
    }

    public static QuizMaster getInstance(){
        if(mySingleton == null) {
            mySingleton = new QuizMaster();
        }
        return mySingleton;
    }

    public boolean validateAnswer() {
        return validateAnswer(currentQuestion.get(), userAnswer.get());
    }

    public ObservableField<Question> getCurrentQuestion() {
        return currentQuestion;
    }

    public void nextQuestion(){
        //for now, we just iterate over the questions
        if(!iterator.hasNext()) //at end of list
            iterator = questionRepository.values().iterator();
        currentQuestion.set(iterator.next());
    }

    private void loadQuestions(){
        Question q1 = new Question("How much is 2+2?","4","One more than three","http://terrehautebridge.com/wp-content/uploads/2015/06/take-four.jpg");
        Question q2 = new Question("What is the city of your university?","Gent","It is not Leuven","https://styleguide.ugent.be/files/uploads/logo_UGent_NL_RGB_2400_kleur_witbg.png");
        questionRepository.put(q1.getQuestionID(),q1);
        questionRepository.put(q2.getQuestionID(),q2);
    }

    private boolean validateAnswer(Question q, String answer){
        return q.getAnswer().equalsIgnoreCase(answer);
    }
}
