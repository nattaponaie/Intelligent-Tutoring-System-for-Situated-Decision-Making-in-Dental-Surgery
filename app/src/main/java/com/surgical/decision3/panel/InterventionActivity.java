package com.surgical.decision3.panel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.surgical.decision3.R;
import com.surgical.decision3.common.bean.intervention.Intervention;
import com.surgical.decision3.common.bean.question.Question;
import com.surgical.decision3.common.controller.StrategyController;

public class InterventionActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);

        addListeners();

        initializeTextFields();

/*
//        //TEST ONLY. TODO, REMOVE THIS.
        String actionName = ApplicationController.currentAction.getActionName();
        System.out.println( "--> actionName : "  + actionName );

        Intervention intervention = InterventionGenerator.generateReconfirmTheAction();

        System.out.println( "--> intervention : "  + intervention.getInterventionData().getPrompt().getPromptMsg().getText() );
*/
        Intervention intervention = StrategyController.evaluateStudentAction();
        displayIntervention( intervention );
    }

    private void initializeTextFields()
    {
        TextView tutorTextView = (TextView ) findViewById(R.id.tutorInterventionTextView );
        tutorTextView.setVisibility(View.GONE);
        //tutorTextView.setText( "X" );  //set x and y for test

        EditText studentEditText = (EditText) findViewById( R.id.studentResponseEditText );
        studentEditText.setVisibility(View.GONE);

        //studentEditText.setText( "Y" );
    }

    private void addListeners()
    {
        Button interventionSubmitButton = (Button) findViewById( R.id.interventionSubmitButton );
        interventionSubmitButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)

            {
                //After finishing the interaction.
                //StrategyController.processForTheNextAction();
                //closeInterventionPage(); //Test only.
            }
        });
    }

    private void closeInterventionPage()
    {
        finish();
    }

    private void displayIntervention( Intervention intervention )
    {
        if( intervention == null || intervention.getInterventionData()== null )
        {
            return;
        }

        if( intervention.getInterventionData().getQuestion() != null )
        {
            Question q = intervention.getInterventionData().getQuestion();
            String tutorIntString = q.populateQuestionChoicesString();

            //Render question
            TextView tutorTextView = (TextView) findViewById( R.id.tutorInterventionTextView );
            tutorTextView.setText( tutorIntString );
            tutorTextView.setVisibility(View.VISIBLE);

            //Render UI for student response
            EditText studentEditText = (EditText) findViewById( R.id.studentResponseEditText );
            studentEditText.setVisibility(View.VISIBLE);

        }
        else if( intervention.getInterventionData().getPrompt() != null )
        {
            //populate message
            TextView tutorTextView = (TextView) findViewById( R.id.tutorInterventionTextView );
            tutorTextView.setText( intervention.getInterventionData().getPrompt().getPromptMsg().getText() );
            tutorTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            //do nothing.
        }
    }



    /*
    EditText editText = (EditText) findViewById(R.id.sendEditText_A2U);

		if (MainConstants.TEST_ON_ANDROID_MODE)
    {
        // USE CONTENT TO SEND TO UNITY
        // Test Start Service

        // TODO: REMOVE THIS. THIS IS FOR TEST ONLY
        // TextView receiveText = (TextView)
        // findViewById(R.id.receive_text_area_A2U);
        // receiveText.setText(editText.getText());

        // TODO: MAKE SURE THAT YOU MODIFIED MANIFEST FILE, SO YOU CAN GET
        // RECEIVER
    }
		else
    {
        // CALL SERVICE TO SEND DATA TO UNITY when user click Send button
        // SEND DATA FROM ANDROID TO UNITY
        Intent intent = new Intent(this, PushToUnityService.class);
        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        startService(intent);
    }
}

    private void onSendButton_U2A()
    {
        // SIMULATE SEND DATA FROM UNITY TO ANDROID HERE.
        EditText editText = (EditText) findViewById(R.id.sendEditText_U2A);

        // NOTE: The code here is to test the function that Unity will actually
        // call to UI.
        ApplicationController.createInstance();
        ApplicationController.receiveUnityDataStream(editText.getText()
                .toString());
    }*/

}
