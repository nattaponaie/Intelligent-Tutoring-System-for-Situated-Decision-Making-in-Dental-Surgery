package com.surgical.decision3.panel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.surgical.decision3.R;
import com.surgical.decision3.common.bean.datastream.DataStream;
import com.surgical.decision3.common.bean.intervention.Intervention;
import com.surgical.decision3.common.bean.intervention.InterventionData;
import com.surgical.decision3.common.bean.intervention.prompt.Prompt;
import com.surgical.decision3.common.bean.question.Choice;
import com.surgical.decision3.common.bean.question.Question;
import com.surgical.decision3.common.bean.studentaction.StudentAction;
import com.surgical.decision3.common.bean.studentaction.StudentTool;
import com.surgical.decision3.common.controller.ApplicationController;
import com.surgical.decision3.common.controller.StrategyController;
import com.surgical.decision3.functions.StudentActionQuery;
import com.surgical.decision3.functions.TransformStudentAction;

import java.util.ArrayList;

import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Problem;
import kb.common.controller.ParserController;
import kb.common.tutorpointer.TutorPointer;
import kb.util.StudentGraphUtil;
import kb.util.WorldStateUtil;

import static com.surgical.decision3.functions.TransformStudentAction.actionInputName;
import static com.surgical.decision3.functions.TransformStudentAction.studentProjectionEffect;


/**
 * Created by Nattapon on 23/5/2560.
 */

public class MainActivity extends AppCompatActivity {

    public static AssetManager am;

    private Spinner spinner_tool_name;
    private Spinner spinner_tool_size;
    private Spinner spinner_patient_case;
    private Spinner spinner_action;
    private Spinner spinner_la_solution_type;
    private Spinner spinner_la_solution_amt;
    private Spinner spinner_la_landmark;
    private Spinner spinner_tool_length;
    private Spinner spinner_reference_tooth_no;
    private Spinner spinner_target_check_numb;
    private Spinner spinner_retract_depth;

    private static Intervention currentIntervention;
    private static String DrillDepth = "";
    private static String Entry_Point = ""
            , Direction = "", Drawing_Shape = "", Action = ""
            , LaSolutionType = "", LaSolutionAmt = "", Tool_Size = "", RB_SHEET_POS = ""
            , Landmark = "", Tool_Length = "" , Reference_Tooth_No = ""
            , Remain_Depth = "" , Retract_Depth = "", Solution_Amount = "" , TargetNumb = "";

    private static boolean Is_Coated_With_Cement = false , Is_Rb_Tool_Selected = false;

    ArrayList<StudentTool> studentToolList = new ArrayList<StudentTool>();

    public static DataStream dt = new DataStream();


    private EditText drilldepth = null;
    private EditText remaindepth = null, solution_amount = null;
    private Spinner spinner_entrypoint = null;
    private Spinner spinner_direction = null;
    private Spinner spinner_drawing_shape = null;
    private CheckBox is_rb_tool_selected = null;
    private Spinner spinner_rb_sheet_position = null;

    private String actionFromSpinner = "";

    //LT1 Parameters
    private static String RcDepthToCurrentFile = "";
    private EditText rc_depth_to_current_file_text = null;

    //MI1 Parameters
    private static String InitialToolSize = "";
    private Spinner spinner_initial_tool_size = null;
    private static String FinalToolSize = "";
    private Spinner spinner_final_tool_size = null;
    private static String SizeDiffIncremental = "";
    private Spinner spinner_size_diff_incremental = null;

    //MI2 Parameters
    private static String RcDrillDepth = "";
    private Spinner spinner_rc_drill_depth = null;

    //MI4 Parameters
    private static String ToolSizeFunc = "";
    private Spinner spinner_tool_size_func = null;

    //TMC Parameters
    private static String MCSize = "";
    private Spinner spinner_mc_size = null;

    //Press and cut cone
    private static String CutConesDepth = "";
    private EditText cut_cones_depth_text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main);

        initializeUIProperty();

        //spinner_patient_case.setSelection( 0 );  //Set the first position

        setTabLayout();

        addListeners();

        //initialize asset in android studio
        initialAssets();

        //initial pddl, pdl, user action mapping, user action
        try {
            ApplicationController.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ApplicationController.createInstance();
        ApplicationController.setMainContext( this );

        //NUI: INITIALIZE DISPLAY AREA
        hideInterventionDisplay();

        whenSpinnerIsSelected();

        showPatientCase();
    }

    private void initializeUIProperty()
    {
        //LT1
        rc_depth_to_current_file_text = (EditText) findViewById(R.id.rc_depth_to_file_text);

        //MI1
        spinner_initial_tool_size = (Spinner) findViewById(R.id.spinner_initial_size);
        spinner_final_tool_size = (Spinner) findViewById(R.id.spinner_final_size);
        spinner_size_diff_incremental = (Spinner) findViewById(R.id.spinner_size_diff_incremental);

        //MI2
        spinner_rc_drill_depth = (Spinner) findViewById(R.id.spinner_rc_drill_depth);

        //MI4
        spinner_tool_size_func = (Spinner) findViewById(R.id.spinner_tool_size_func);

        //TMC
        spinner_mc_size = (Spinner) findViewById(R.id.spinner_mc_size);

        //Press and cut cones
        cut_cones_depth_text = (EditText) findViewById(R.id.cut_cones_depth_text);

        spinner_action = (Spinner) findViewById(R.id.spinner_action);
        spinner_tool_name = (Spinner) findViewById(R.id.spinner_tool_name);
        spinner_tool_size = (Spinner) findViewById(R.id.spinner_tool_size);
        spinner_patient_case = (Spinner) findViewById(R.id.spinner_patient_case);
        drilldepth = (EditText) findViewById(R.id.sendtext_drilldepth);
        spinner_entrypoint = (Spinner) findViewById(R.id.spinner_entrypoint);
        spinner_direction = (Spinner) findViewById(R.id.spinner_direction);
        spinner_drawing_shape = (Spinner) findViewById(R.id.spinner_shape);
        is_rb_tool_selected = (CheckBox) findViewById(R.id.is_rb_tool_selected_checkbox);
        spinner_rb_sheet_position = (Spinner) findViewById(R.id.spinner_rb_sheet_position);

        //LA
        spinner_la_solution_type = (Spinner) findViewById(R.id.spinner_la_solution_type);
        spinner_la_solution_amt = (Spinner) findViewById(R.id.spinner_la_solution_amt);
        spinner_la_landmark = (Spinner) findViewById(R.id.spinner_la_landmark);
        spinner_tool_length = (Spinner) findViewById(R.id.spinner_tool_length);
        spinner_reference_tooth_no = (Spinner) findViewById(R.id.spinner_reference_tooth_no);
        remaindepth = (EditText) findViewById(R.id.sendtext_laremaindepth);

        spinner_retract_depth = (Spinner) findViewById(R.id.spinner_retract_depth);

        solution_amount = (EditText) findViewById(R.id.sendtext_solution_amount);

        spinner_target_check_numb = (Spinner) findViewById(R.id.spinner_target_check_numb);
    }

    private void setTabLayout()
    {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Information"));
        tabLayout.addTab(tabLayout.newTab().setText("Interaction"));
    }

    private void initialAssets()
    {
        am = getAssets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void addListeners()
    {
        Button submitStudentButton = (Button) findViewById( R.id.submit_action_button );
        submitStudentButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OnSubmitStudentActionClick();
//                if(!isStudentInputNull())
//                {
//                    OnSubmitStudentActionClick();
//                }
//                else
//                {
//                    showAlertButton();
//                }
            }
        } );

        Button interventionSubmitButton = (Button) findViewById( R.id.interventionSubmitButton );
        interventionSubmitButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OnSubmitStudentAnswerClick();
            }
        } );

    }

    private void showAlertButton()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please fill out all the fields!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void getStudentInputData()
    {
        //LT1
        RcDepthToCurrentFile = String.valueOf(rc_depth_to_current_file_text.getText().toString());

        //MI1
        if( spinner_initial_tool_size.getSelectedItemPosition() > 0 )
        {
            InitialToolSize = String.valueOf(spinner_initial_tool_size.getSelectedItem());
        }
        if( spinner_final_tool_size.getSelectedItemPosition() > 0 )
        {
            FinalToolSize = String.valueOf(spinner_final_tool_size.getSelectedItem());
        }
        if( spinner_size_diff_incremental.getSelectedItemPosition() > 0 )
        {
            SizeDiffIncremental = String.valueOf(spinner_size_diff_incremental.getSelectedItem());
        }

        //MI2
        if( spinner_rc_drill_depth.getSelectedItemPosition() > 0 )
        {
            RcDrillDepth = String.valueOf(spinner_rc_drill_depth.getSelectedItem());
        }

        //MI4
        if( spinner_tool_size_func.getSelectedItemPosition() > 0 )
        {
            ToolSizeFunc = String.valueOf(spinner_tool_size_func.getSelectedItem());
        }

        //TMC
        if( spinner_mc_size.getSelectedItemPosition() > 0 )
        {
            MCSize = String.valueOf(spinner_mc_size.getSelectedItem());
        }

        //Press and cut cones
        CutConesDepth = String.valueOf(cut_cones_depth_text.getText().toString());

        DrillDepth = String.valueOf(drilldepth.getText().toString());

        //CLEAN DIRTY DATA
        if( spinner_action.getSelectedItemPosition() > 0 )
        {
            Action = String.valueOf(spinner_action.getSelectedItem());
        }

        if( spinner_entrypoint.getSelectedItemPosition() > 0 )
        {
            Entry_Point = String.valueOf(spinner_entrypoint.getSelectedItem());
        }

        if( spinner_direction.getSelectedItemPosition() > 0 )
        {
            Direction = String.valueOf(spinner_direction.getSelectedItem());
        }

        if( spinner_drawing_shape.getSelectedItemPosition() > 0 )
        {
            Drawing_Shape = String.valueOf(spinner_drawing_shape.getSelectedItem());
        }

        if( spinner_rb_sheet_position.getSelectedItemPosition() > 0 )
        {
            RB_SHEET_POS = String.valueOf(spinner_rb_sheet_position.getSelectedItem());
        }

        if( spinner_tool_size.getSelectedItemPosition() > 0 )
        {
            Tool_Size = String.valueOf(spinner_tool_size.getSelectedItem());
        }

        if( spinner_la_solution_type.getSelectedItemPosition() > 0 )
        {
            LaSolutionType = String.valueOf(spinner_la_solution_type.getSelectedItem());
        }

        if( spinner_la_solution_amt.getSelectedItemPosition() > 0 )
        {
            LaSolutionAmt = String.valueOf(spinner_la_solution_amt.getSelectedItem());
        }

        if( spinner_la_landmark.getSelectedItemPosition() > 0 )
        {
            Landmark = String.valueOf(spinner_la_landmark.getSelectedItem());
        }

        if( spinner_tool_length.getSelectedItemPosition() > 0 )
        {
            Tool_Length = String.valueOf(spinner_tool_length.getSelectedItem());
        }

        if( spinner_reference_tooth_no.getSelectedItemPosition() > 0 )
        {
            Reference_Tooth_No = String.valueOf(spinner_reference_tooth_no.getSelectedItem());
        }

        if( spinner_retract_depth.getSelectedItemPosition() > 0 )
        {
            Retract_Depth = String.valueOf(spinner_retract_depth.getSelectedItem());
        }

        Remain_Depth = String.valueOf(remaindepth.getText().toString());
        Solution_Amount = String.valueOf(solution_amount.getText().toString());

        if( spinner_target_check_numb.getSelectedItemPosition() > 0 )
        {
            TargetNumb = String.valueOf(spinner_target_check_numb.getSelectedItem());
        }

        StudentTool studentTool = new StudentTool();
//        if( spinner_tool_name.getSelectedItemPosition() > 0 )
//        {
            studentTool.setTool_Type(String.valueOf(spinner_tool_name.getSelectedItem()));
//        }
        studentToolList.add(studentTool);

        if(is_rb_tool_selected.isChecked())
        {
            Is_Rb_Tool_Selected = true;
        }
    }

    private void OnSubmitStudentAnswerClick()
    {
        //GetCurrent interventionData
        InterventionData invData = currentIntervention.getInterventionData();
        if( invData.isQuestionAnswerSession() )
        {
            //(Button)
            EditText answerTest = (EditText) findViewById( R.id.studentResponseEditText );

            int answerIndex =  Integer.parseInt( answerTest.getText().toString().trim() ) - 1 ;
            if( answerIndex < invData.getQuestion().getChoices().size() )
            {
                Choice selectedChoice = invData.getChoiceByIndex( answerIndex );
                if(selectedChoice != null)
                {
                    selectedChoice.setSelected( true );     //Update selected choices

                    invData.setStudentAnswer( selectedChoice );
                    invData.setAnswerQuestion( true );

                    //Send it back to intervention generator
                    Intervention responseIntervention = StrategyController.receiveStudentsResponse( currentIntervention );

                    //Hide and Display
                    hideInterventionDisplay();
                    displayIntervention( responseIntervention );
                    //call CommunicationController go send it back.

                    //currentIntervention = null;     //clear
                }
                else
                {
                    //clear editText
                    answerTest.setText( "" );
                }
            }

            //clear text
            answerTest.setText("");
        }

        //Get xml
        //You may need to cheat a little bit.

    }
    private void OnSubmitStudentActionClick()
    {
        //initial currentintervention
        currentIntervention = null;

        getStudentInputData();

        ArrayList<StudentAction> studentActionList = StudentActionQuery.getStudentActions(
                Action
                , studentToolList
                , DrillDepth
                , Entry_Point
                , Direction
                , Drawing_Shape
                , Is_Coated_With_Cement
                , Tool_Size
                , Is_Rb_Tool_Selected
                , RB_SHEET_POS
                , LaSolutionType, LaSolutionAmt, Landmark
                , Tool_Length , Reference_Tooth_No
                , Remain_Depth , Retract_Depth
                , Solution_Amount , TargetNumb
                , RcDepthToCurrentFile
                , InitialToolSize, FinalToolSize, SizeDiffIncremental
                , RcDrillDepth
                , ToolSizeFunc
                , MCSize
                , CutConesDepth);


        dt.setStudentActionList(studentActionList);

        TransformStudentAction.ReceiveStudentAction();

        //TODO: call to generate intervention here.
//        Intervention intervention = StrategyController.evaluateStudentAction();
//        if( intervention != null )
//        {
//            displayIntervention( intervention );
//            TutorPointer.pointToNextPlanAction(); // Mes
//        }
//        else
//        {
//            hideInterventionDisplay();
//        }
        TutorPointer.pointToNextPlanAction(); // Mes

        clearInputData();//to prevent double click submit

        displayEffectDetail();
    }

    //GO TO INTERVENTION PAGE
    private void showInterventionPage()
    {
        Intent interventionPage = new Intent( getApplicationContext(), InterventionActivity.class );
        startActivity( interventionPage );
    }

    private void hideInterventionDisplay( )
    {
        //LayoututorInterventionLayout
        LinearLayout interventionLayout = (LinearLayout) findViewById( R.id.tutorInterventionLayout );
        interventionLayout.setVisibility(View.GONE);

        //Render question
        TextView tutorTextView = (TextView) findViewById( R.id.tutorInterventionTextView );
        tutorTextView.setVisibility(View.GONE);

        //Render UI for student response
        EditText studentEditText = (EditText) findViewById( R.id.studentResponseEditText );
        studentEditText.setVisibility(View.GONE);

        //Hide submit button
        Button interventionSubmitButton = (Button) findViewById( R.id.interventionSubmitButton );
        interventionSubmitButton.setVisibility(View.GONE);
    }

    private void displayIntervention( Intervention intervention )
    {
        currentIntervention = intervention;  //cache the intervention.

        //Show layout
        LinearLayout interventionLayout = (LinearLayout) findViewById( R.id.tutorInterventionLayout );
        interventionLayout.setVisibility(View.VISIBLE);

        if( intervention == null || intervention.getInterventionData()== null )
        {
            //Clear field.
            hideInterventionDisplay();
            return;
        }
        else
        {
            Question q = intervention.getInterventionData().getQuestion();
            Prompt p = intervention.getInterventionData().getPrompt();
            renderResults( q, p );

            //TODO: Transform intervention to xml and send it back
            //TODO: CommunicationController.transformResponseIntoXML
        }
    }

    private void renderResults(Question q, Prompt p )
    {
        if( q != null )
        {
            //There is a question , and might have prompt
            renderQuestionAndPrompt( q, p );
            disableSpinnerAction();
        }
        else
        {
            //Prompt P
            if( p!= null )
            {
                renderPrompt( p );
                ableSpinnerAction();
            }
        }
    }

    private void renderPrompt( Prompt p )
    {
        //populate message
        TextView tutorTextView = (TextView) findViewById( R.id.tutorInterventionTextView );
        tutorTextView.setText( p.getPromptMsg().getText() );
        tutorTextView.setVisibility(View.VISIBLE);

        //Hide submit button
        Button interventionSubmitButton = (Button) findViewById( R.id.interventionSubmitButton );
        interventionSubmitButton.setVisibility(View.GONE);

        System.out.println( "===============================" );
        System.out.println( "---- INTERVENTION : PROMPT ----" );
        System.out.println( p.getPromptMsg().getText() );
        System.out.println( "===============================" );
    }

    private void renderQuestionAndPrompt(Question q, Prompt p )
    {
        String tutorIntString = q.populateQuestionChoicesString();

        System.out.println( "=================================" );
        System.out.println( "---- INTERVENTION : QUESTION ----" );
        System.out.println( tutorIntString );
        System.out.println( "=================================" );

        //Render question
        TextView tutorTextView = (TextView) findViewById( R.id.tutorInterventionTextView );

        //If there is a prompt, insert here.
        if( p != null )
        {
            tutorTextView.setText( p.getPromptMsg().getText() + "\n" + tutorIntString );
        }
        else
        {
            tutorTextView.setText( tutorIntString );
        }

        tutorTextView.setVisibility(View.VISIBLE);

        //Render UI for student response
        EditText studentEditText = (EditText) findViewById( R.id.studentResponseEditText );
        studentEditText.setVisibility(View.VISIBLE);

        //Show submit button
        Button interventionSubmitButton = (Button) findViewById( R.id.interventionSubmitButton );
        interventionSubmitButton.setVisibility(View.VISIBLE);
    }

    private void clearInputData()
    {
        drilldepth.setText("");
        spinner_entrypoint.setSelection(0);
        spinner_action.setSelection(0);
        spinner_direction.setSelection(0);
        spinner_drawing_shape.setSelection(0);
        spinner_rb_sheet_position.setSelection(0);
        spinner_tool_size.setSelection(0);
        spinner_tool_name.setSelection(0);
        is_rb_tool_selected.setChecked(false);
        spinner_reference_tooth_no.setSelection(0);
        spinner_tool_length.setSelection(0);
        spinner_la_landmark.setSelection(0);
        spinner_la_solution_type.setSelection(0);
        spinner_la_solution_amt.setSelection(0);
        spinner_retract_depth.setSelection(0);
        solution_amount.setText("");
        spinner_target_check_numb.setSelection(0);
        remaindepth.setText("");

        //LT1
        rc_depth_to_current_file_text.setText("");

        //MI1 Parameters
        spinner_initial_tool_size.setSelection(0);
        spinner_final_tool_size.setSelection(0);
        spinner_size_diff_incremental.setSelection(0);

        //MI2 Parameters
        spinner_rc_drill_depth.setSelection(0);

        //MI4 Parameters
        spinner_tool_size_func.setSelection(0);

        //TMC Parameters
        spinner_mc_size.setSelection(0);

        //Press and cut cone
        cut_cones_depth_text.setText("");
    }

    public static DataStream getDataStream()
    {
        return dt;
    }

    private void whenSpinnerIsSelected()
    {
        spinner_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                String selected_item = adapter.getItemAtPosition(position).toString();

                hideEveryLayout();

                if( selected_item.equals("select_local_anesthesia_type") )
                {
                    LinearLayout lASolutionLayout = (LinearLayout) findViewById(R.id.la_solution_layout);
                    lASolutionLayout.setVisibility(View.VISIBLE);

                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "select_local_anesthesia_type";
                }
                else if( selected_item.equals("pierce_anesthesia_needle") )
                {
                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout toolLengthLayout = (LinearLayout) findViewById(R.id.tool_length_layout);
                    toolLengthLayout.setVisibility(View.VISIBLE);

                    LinearLayout landmarkLayout = (LinearLayout) findViewById(R.id.la_landmark_layout);
                    landmarkLayout.setVisibility(View.VISIBLE);

                    LinearLayout refToothNoLayout = (LinearLayout) findViewById(R.id.la_ref_tooth_no_layout);
                    refToothNoLayout.setVisibility(View.VISIBLE);

                    LinearLayout remainDepthLayout = (LinearLayout) findViewById(R.id.la_remain_depth_layout);
                    remainDepthLayout.setVisibility(View.VISIBLE);

                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForLA(spinner_tool_name);
                    actionFromSpinner = "pierce_anesthesia_needle";
                }
                else if( selected_item.equals("aspirate") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "aspirate";
                }
                else if( selected_item.equals("retract") )
                {
                    LinearLayout injectLayout = (LinearLayout) findViewById(R.id.la_retract_layout);
                    injectLayout.setVisibility(View.VISIBLE);

                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "retract";
                }
                else if( selected_item.equals("inject_anesthesia_and_draw") )
                {
                    LinearLayout injectLayout = (LinearLayout) findViewById(R.id.la_inject_layout);
                    injectLayout.setVisibility(View.VISIBLE);

                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "inject_anesthesia_and_draw";
                }
                else if( selected_item.equals("check_numb") )
                {
                    LinearLayout checkNumbLayout = (LinearLayout) findViewById(R.id.la_check_numb_layout);
                    checkNumbLayout.setVisibility(View.VISIBLE);

                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "check_numb";
                }
                else if(selected_item.equals("insert_rubber_dam")) {

                    LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.insert_rb_layout);
                    actionParameterLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout toolSizeLayout = (LinearLayout) findViewById(R.id.tool_size_layout);
                    toolSizeLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

//                    CheckBox rbToolSetCheckBox = (CheckBox) findViewById(R.id.is_rb_tool_selected_checkbox);
//                    rbToolSetCheckBox.setChecked(true);

                    setToolTypeForRB(spinner_tool_name);
                    actionFromSpinner = "insert_rubber_dam";

                }
                else if(selected_item.equals("drill_to_pulp_chamber")) {

                    LinearLayout entryPointLayout = (LinearLayout) findViewById(R.id.entry_point_layout);
                    entryPointLayout.setVisibility(View.VISIBLE);

                    LinearLayout directionLayout = (LinearLayout) findViewById(R.id.direction_layout);
                    directionLayout.setVisibility(View.VISIBLE);

                    LinearLayout drillShapLayout = (LinearLayout) findViewById(R.id.drill_shape_layout);
                    drillShapLayout.setVisibility(View.VISIBLE);

                    LinearLayout drillDepthLayout = (LinearLayout) findViewById(R.id.drill_depth_layout);
                    drillDepthLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout toolSizeLayout = (LinearLayout) findViewById(R.id.tool_size_layout);
                    toolSizeLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForOC1(spinner_tool_name);
                    actionFromSpinner = "drill_to_pulp_chamber";
                }
                else if(selected_item.equals("measure_working_length_by_inserting_file")) {

                    LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.lt_1_layout);
                    actionParameterLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForOC1(spinner_tool_name);
                    actionFromSpinner = "measure_working_length_by_inserting_file";
                }
                else if(selected_item.equals("expand_overall_root_canal")) {

                    LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.mi_layout);
                    actionParameterLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    ArrayAdapter<CharSequence> adapterEdit = ArrayAdapter.createFromResource(getApplicationContext(), R.array.tool_size_func_arrays, android.R.layout.simple_list_item_1);
                    adapterEdit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_initial_tool_size.setAdapter(adapterEdit);
                    spinner_final_tool_size.setAdapter(adapterEdit);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "expand_overall_root_canal";
                }
                else if(selected_item.equals("expand_curvical_root_canal")) {

                    LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.mi_layout);
                    actionParameterLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout rcDrillDepthLayout = (LinearLayout) findViewById(R.id.rc_drill_depth_layout);
                    rcDrillDepthLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    ArrayAdapter<CharSequence> adapterEdit = ArrayAdapter.createFromResource(getApplicationContext(), R.array.tool_size_curv_arrays, android.R.layout.simple_list_item_1);
                    adapterEdit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_initial_tool_size.setAdapter(adapterEdit);
                    spinner_final_tool_size.setAdapter(adapterEdit);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "expand_curvical_root_canal";
                }
                else if(selected_item.equals("expand_apical_root_canal")) {

                    LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.mi_layout);
                    actionParameterLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    ArrayAdapter<CharSequence> adapterEdit = ArrayAdapter.createFromResource(getApplicationContext(), R.array.tool_size_func_arrays, android.R.layout.simple_list_item_1);
                    adapterEdit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_initial_tool_size.setAdapter(adapterEdit);
                    spinner_final_tool_size.setAdapter(adapterEdit);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "expand_apical_root_canal";
                }
                else if(selected_item.equals("remove_rc_ledge")) {

                    LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.tool_size_function_layout);
                    actionParameterLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "remove_rc_ledge";
                }
                else if(selected_item.equals("select_and_try_main_cone")) {

                    LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.mc_size_layout);
                    actionParameterLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "select_and_try_main_cone";
                }
                else if(selected_item.equals("coat_cement_to_rc_wall")) {

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "coat_cement_to_rc_wall";
                }
                else if(selected_item.equals("insert_selected_main_cone")){
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "insert_selected_main_cone";
                }
                else if(selected_item.equals("insert_lateral_cones")) {

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "insert_lateral_cones";
                }
                else if(selected_item.equals("press_and_cut_cones")) {

                    LinearLayout actionLayout = (LinearLayout) findViewById(R.id.press_and_cut_cone_layout);
                    actionLayout.setVisibility(View.VISIBLE);

                    LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
                    addToolLayout.setVisibility(View.VISIBLE);

                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    setToolTypeForMI1(spinner_tool_name);
                    actionFromSpinner = "press_and_cut_cones";
                }
                else if( selected_item.equals("irregate") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "irregate";
                }
                else if( selected_item.equals("mop_rc") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "mop_rc";
                }
                else if( selected_item.equals("grab_pen") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "grab_pen";
                }
                else if( selected_item.equals("drop_pen") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "drop_pen";
                }
                else if( selected_item.equals("action_a") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "action_a";
                }
                else if( selected_item.equals("action_b") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "action_b";
                }
                else if( selected_item.equals("action_c") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "action_c";
                }
                else if( selected_item.equals("action_d") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "action_d";
                }
                else if( selected_item.equals("action_e") )
                {
                    //Set active to submit button
                    LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
                    submitLayout.setVisibility(View.VISIBLE);

                    actionFromSpinner = "action_e";
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinner_tool_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                String selected_item = adapter.getItemAtPosition(position).toString();

                if(selected_item.equals("CLAMP")) {
                    setToolSizeArrayForClamp(spinner_tool_size);
                }
                else if(selected_item.equals("ROUND_DIAMOND_BUR")) {
                    setToolSizeArrayForRoundDiamond(spinner_tool_size);
                }
                else if(selected_item.equals("STEEL_ROUND_BUR")) {
                    setToolSizeArrayForSteelRound(spinner_tool_size);
                }
                else if(selected_item.equals("SAFE_TIPPED_TAPER_BUR")) {
                    setToolSizeArrayForSafeTipped(spinner_tool_size);
                }
//                else if(selected_item.equals("LA_NEEDLE")) {
//                    setToolSizeArrayForLANeedle(spinner_tool_size);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinner_patient_case.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                String selected_item = adapter.getItemAtPosition(position).toString();

                LinearLayout patientInfoLayout = (LinearLayout) findViewById(R.id.patient_information_layout);
                TextView patientNameTextView = (TextView) findViewById(R.id.patient_name_textview);
                TextView patientAgeTextView = (TextView) findViewById(R.id.patient_age_textview);
                TextView patientSexTextView = (TextView) findViewById(R.id.patient_sex_textview);
                TextView patientInfoTextView = (TextView) findViewById(R.id.patient_info_textview);

                clearPatientData();

                if(selected_item.equals("Case 1")) {

                    patientInfoLayout.setVisibility(View.VISIBLE);

                    patientNameTextView.setText(patientNameTextView.getText() + getString(R.string.case1_name_string));
                    patientAgeTextView.setText(patientAgeTextView.getText() + getString(R.string.case1_age_string));
                    patientSexTextView.setText(patientSexTextView.getText() + getString(R.string.case1_sex_string));

                    //patientInfoTextView.setText(patientInfoTextView.getText() + getString(R.string.case1_info_string));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void showPatientCase()
    {
        TextView patientInfoTextView = (TextView) findViewById(R.id.patient_info_textview);
        TextView currentStateTextView = (TextView) findViewById(R.id.current_state_textview);
        Problem patient_problem = ParserController.problem;
        String patientInitString = "";
        for( Exp e : patient_problem.getInit())
        {
            patientInitString += e.toString() + '\n';
        }
        patientInfoTextView.setText( patientInitString );
        currentStateTextView.setText( patientInitString );
    }

    private void clearPatientData()
    {
        TextView patientNameTextView = (TextView) findViewById(R.id.patient_name_textview);
        TextView patientAgeTextView = (TextView) findViewById(R.id.patient_age_textview);
        TextView patientSexTextView = (TextView) findViewById(R.id.patient_sex_textview);
        TextView patientInfoTextView = (TextView) findViewById(R.id.patient_info_textview);

        patientNameTextView.setText("Name: ");
        patientAgeTextView.setText("Age: ");
        patientSexTextView.setText("Sex: ");
        patientInfoTextView.setText("");
    }

    private void disableSpinnerAction()
    {
        (spinner_action).getSelectedView().setEnabled(false);
        spinner_action.setEnabled(false);
    }

    private void ableSpinnerAction()
    {
        (spinner_action).getSelectedView().setEnabled(true);
        spinner_action.setEnabled(true);
    }

    private void setToolTypeForLA(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.toolname_la_arrays, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolTypeForRB(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.toolname_rb_arrays, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolTypeForOC1(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.toolname_oc1_arrays, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolTypeForMI1(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.toolname_mi1_arrays, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolSizeArrayForClamp(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.clamp_size_arrays, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolSizeArrayForRoundDiamond(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.round_diamond_size_arrays, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolSizeArrayForSteelRound(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.steel_round_size_arrays,
                android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolSizeArrayForSafeTipped(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.safe_tipped_size_arrays,
                android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

//    private void setToolSizeArrayForLANeedle(Spinner spinner)
//    {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.la_needle_size_arrays,
//                android.R.layout.simple_list_item_1);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    }

    private void hideEveryLayout()
    {

        //LT1
        LinearLayout lt1Layout = (LinearLayout) findViewById(R.id.lt_1_layout);
        lt1Layout.setVisibility(View.GONE);

        //MI1
        LinearLayout miLayout = (LinearLayout) findViewById(R.id.mi_layout);
        miLayout.setVisibility(View.GONE);

        LinearLayout rcDrillDepthLayout = (LinearLayout) findViewById(R.id.rc_drill_depth_layout);
        rcDrillDepthLayout.setVisibility(View.GONE);

        LinearLayout toolSizeFuncLayout = (LinearLayout) findViewById(R.id.tool_size_function_layout);
        toolSizeFuncLayout.setVisibility(View.GONE);

        LinearLayout mcSizeLayout = (LinearLayout) findViewById(R.id.mc_size_layout);
        mcSizeLayout.setVisibility(View.GONE);

        LinearLayout pressAndCutLayout = (LinearLayout) findViewById(R.id.press_and_cut_cone_layout);
        pressAndCutLayout.setVisibility(View.GONE);

        LinearLayout remainDepthLayout = (LinearLayout) findViewById(R.id.la_remain_depth_layout);
        remainDepthLayout.setVisibility(View.GONE);

        LinearLayout laSolutionLayout = (LinearLayout) findViewById(R.id.la_solution_layout);
        laSolutionLayout.setVisibility(View.GONE);

        LinearLayout toolLengthLayout = (LinearLayout) findViewById(R.id.tool_length_layout);
        toolLengthLayout.setVisibility(View.GONE);

        LinearLayout landmarkLayout = (LinearLayout) findViewById(R.id.la_landmark_layout);
        landmarkLayout.setVisibility(View.GONE);

        LinearLayout refToothNoLayout = (LinearLayout) findViewById(R.id.la_ref_tooth_no_layout);
        refToothNoLayout.setVisibility(View.GONE);

        LinearLayout retractLayout = (LinearLayout) findViewById(R.id.la_retract_layout);
        retractLayout.setVisibility(View.GONE);

        LinearLayout injectLayout = (LinearLayout) findViewById(R.id.la_inject_layout);
        injectLayout.setVisibility(View.GONE);

        LinearLayout checkNumbLayout = (LinearLayout) findViewById(R.id.la_check_numb_layout);
        checkNumbLayout.setVisibility(View.GONE);

        LinearLayout entryPointLayout = (LinearLayout) findViewById(R.id.entry_point_layout);
        entryPointLayout.setVisibility(View.GONE);

        LinearLayout directionLayout = (LinearLayout) findViewById(R.id.direction_layout);
        directionLayout.setVisibility(View.GONE);

        LinearLayout drillShapLayout = (LinearLayout) findViewById(R.id.drill_shape_layout);
        drillShapLayout.setVisibility(View.GONE);

        LinearLayout drillDepthLayout = (LinearLayout) findViewById(R.id.drill_depth_layout);
        drillDepthLayout.setVisibility(View.GONE);

        LinearLayout addToolLayout = (LinearLayout) findViewById(R.id.layout_add_tool);
        addToolLayout.setVisibility(View.GONE);

        LinearLayout toolSizeLayout = (LinearLayout) findViewById(R.id.tool_size_layout);
        toolSizeLayout.setVisibility(View.GONE);

        LinearLayout actionParameterLayout = (LinearLayout) findViewById(R.id.insert_rb_layout);
        actionParameterLayout.setVisibility(View.GONE);

        LinearLayout submitLayout = (LinearLayout) findViewById(R.id.submit_layout);
        submitLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.empty_arrays, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tool_size.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.toolname_empty_arrays, android.R.layout.simple_list_item_1);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tool_name.setAdapter(adapter2);

    }

    private void displayEffectDetail() {
        LinearLayout effectLayout = (LinearLayout) findViewById(R.id.effectLayout);
        effectLayout.setVisibility(View.VISIBLE);

        LinearLayout displayActionLayout = (LinearLayout) findViewById(R.id.displayActionLayout);
        displayActionLayout.setVisibility(View.VISIBLE);

        LinearLayout satisfyDesiredOutcomeLayout = (LinearLayout) findViewById(R.id.satisfyDesiredOutcomeLayout);
        satisfyDesiredOutcomeLayout.setVisibility(View.VISIBLE);

        TextView effects_text = (TextView) findViewById(R.id.effects_text);
        effects_text.setText("");

        TextView applied_text = (TextView) findViewById(R.id.applied_text);
        applied_text.setText("");

        TextView new_effect_text = (TextView) findViewById(R.id.new_effect_text);
        new_effect_text.setText("");

        TextView desired_outcome_text = (TextView) findViewById(R.id.desired_outcome_text);

        int greenColor = Color.parseColor("#218502");
        int redColor = Color.parseColor("#FF0000");

        //----------------------- display satisfy d.o. -----------------------//

        boolean isSatisfyDO = StudentGraphUtil.getCurrentStudentGraph().getEndNode().isSatisfyDesiredOutcome();
        String satisfyDOString = "satisfy desired outcome";
        String notSatisfyDOString = "not satisfy desired outcome";
        String actionInvalidString = "action is not related to plan representation";

        if (isSatisfyDO) {
            desired_outcome_text.setText(satisfyDOString);
            desired_outcome_text.setTextColor(greenColor);
        }
        else if (!TransformStudentAction.is_action_followed_plan) {
            desired_outcome_text.setText(actionInvalidString);
            desired_outcome_text.setTextColor(redColor);
        }
        else if (!isSatisfyDO) {
            desired_outcome_text.setText(notSatisfyDOString);
            desired_outcome_text.setTextColor(redColor);
        }

        //---------------------------------------------------------------------//

        //----------------------- display current state -----------------------//
        TextView current_state_text = (TextView) findViewById(R.id.current_state_textview);
        String currentStateString = "";
        for (Exp e : WorldStateUtil.getWorldStatesList().get((WorldStateUtil.getWorldStatesList().size() - 1)).getFactPredicate()) {
            currentStateString += e.toString() + '\n';
        }
        current_state_text.setText(currentStateString);

        //----------------------- display projection effects -----------------------//
        String projectionString = "";
        for (Exp each_projection : studentProjectionEffect) {
            projectionString += '\n' + each_projection.toString();
        }
        effects_text.setText(projectionString);
        //----------------------- display applied state -----------------------//
        String appliedStateString = "";
        for (Exp e : WorldStateUtil.getWorldStatesList().get((WorldStateUtil.getWorldStatesList().size() - 1)).getFactAndEffect()) {
            boolean found = false;
            for (Exp each_projection : studentProjectionEffect) {
                if(e.toString().equals(each_projection.toString()))
                {
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                appliedStateString += '\n' + e.toString();
            }
        }
        applied_text.setText(appliedStateString);

        String projectionString2 = "";
        for (Exp each_projection : studentProjectionEffect) {
            projectionString2 += each_projection.toString() + '\n';
        }
        effects_text.setText(projectionString);
        new_effect_text.setText(projectionString2);

        studentProjectionEffect.clear();
        //---------------------------------------------------------------------//

        //----------------------- display student action -----------------------//

        TextView action_result_text = (TextView) findViewById(R.id.action_result_text);

//        String studentActionName = StudentGraphUtil.getCurrentStudentGraph().getActionNode().getActionNode().getActionName();
        String studentActionName = actionInputName;
        action_result_text.setText(studentActionName);

        //---------------------------------------------------------------------//


        //----------------------- display ready for next action or not ready -----------------------//

//        TextView tutorText = (TextView) findViewById(R.id.tutor_text);
//        tutorText.setVisibility(View.VISIBLE);
//
//        if(StudentGraphUtil.getCurrentStudentGraph().getEndNode().isSatisfyDesiredOutcome()) {
//            tutorText.setText(">> Ready for receiving next action ! <<");
//            tutorText.setTextColor(greenColor);
//
//        }
//        else
//        {
//            tutorText.setText(">> Please go back to make it right ! <<");
//            tutorText.setTextColor(redColor);
//        }
    }

    /*
    		// TEST READ XML FROM FILE
//		getXMLfromAssetFile();
		//String xmlString = getXMLfromAssetFile();
		// String xmlString = TestXMLConstants.getXMLfromFile();
    * */

}
