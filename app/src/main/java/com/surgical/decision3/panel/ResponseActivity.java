package com.surgical.decision3.panel;

/**
 * Created by Nattapon on 26/5/2560.
 */

import android.support.v7.app.AppCompatActivity;

public class ResponseActivity extends AppCompatActivity {

//    private static XMLParser xmlParser = new XMLParser();

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_response);
//
//        addListeners();
//    }
//
//    private void addListeners() {
//        Button marshallButton = (Button) findViewById( R.id.javaxml_marshall_button);
//        marshallButton.setOnClickListener( new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.javaxml_intervention_radioGroup);
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                RadioButton radioButton = (RadioButton) findViewById(selectedId);
//                initializeMarshall(radioButton.getId());
//            }
//        } );
//        Button unMarshallButton = (Button) findViewById(R.id.javaxml_unmarshall_button);
//        unMarshallButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initializeUnMarshall_TEST();
//            }
//        });
//    }
//
//    private void initializeUnMarshall_TEST() {
//        String xmlString;
//
//        Response testResponse = InterventionController.prepareForcePlayback();
//        Response testResponse1 = InterventionController.prepareIntraOperativeInitialization_SAMPLE();
//        Response testResponse2 = InterventionController.prepareQuestion_SAMPLE();
//        xmlString = xmlParser.marshall(testResponse1);
//        Response responseObject = (Response) xmlParser.unMarshall(Response.class, xmlString);
//
//        showOnTextArea(responseObject.toString());
//    }
//
//    private void initializeMarshall(int v) {
//        Response testResponse = null;
//        switch (v){
//            case R.id.javaxml_promptRadioButton:
//                testResponse = InterventionController.prepareIntraOperativeInitialization_SAMPLE();
//                break;
//            case R.id.javaxml_forcePlaybackRadioButton:
//                testResponse = InterventionController.prepareForcePlayback();
//                break;
//            case R.id.javaxml_questionButton:
//                testResponse = InterventionController.prepareQuestion_SAMPLE();
//                break;
//        }
//        String xml = xmlParser.marshall(testResponse);
//        showOnTextArea(xml);
//    }
//
//    private void showOnTextArea(String output)
//    {
//        TextView textView = (TextView) findViewById( R.id.outputTextArea );
//        textView.setText( output );
//    }


}

