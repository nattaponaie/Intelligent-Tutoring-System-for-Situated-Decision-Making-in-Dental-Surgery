package com.surgical.decision3.common.controller;

import android.content.Context;
import android.os.Environment;

import com.surgical.decision3.common.constant.ApplicationConstants;
import com.surgical.decision3.common.constant.MainConstants;
import com.surgical.decision3.logger.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Parser;
import kb.common.datastructure.graph.StudentGraph;
import kb.common.datastructure.graph.TutorGraph;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.common.controller.ParserController;
import kb.common.datastructure.worldstate.State;
import kb.common.tutorpointer.TutorPointer;
import kb.util.InferenceEngineUtil;
import kb.util.StudentGraphUtil;
import kb.util.TutorGraphUtil;
import kb.util.UserActionUtil;
import kb.util.WorldStateUtil;

import static com.surgical.decision3.panel.MainActivity.am;

/**
 * Created by Dell on 6/30/2017.
 */

public class ApplicationController
{
    public static HashMap<String, String> user_action_predicate_map = new HashMap<>();
    public static HashMap<String, String> action_predicate_map = new HashMap<>();
    public static HashMap<String, String> action_predicate_map_reverse = new HashMap<>();
    public static HashMap<String, String> pair_user_action_map = new HashMap<>();

    //---------------------------------------------------------------------------
    // Map for Interventions
    //---------------------------------------------------------------------------
    public static HashMap<String, String> intervention_template_map = new HashMap<>();
    public static HashMap<String, String> domain_predicate_map = new HashMap<>();
    public static HashMap<String, String> i_template_numeric_comparison_map = new HashMap<>();
    //---------------------------------------------------------------------------

    public static Boolean ready_for_next_state = false;

    public static ApplicationController instance;

    private static Context mainContext;

    public static List<State> worldStateList = new ArrayList<State>();

    public static PlanAction currentAction = null;

    public static boolean isActionCorrect = false;
    public static boolean isEffectSatisfyDesiredOutcomes  = false;

    public static List<TutorGraph> tutorGraphList = new ArrayList<>();
	
    public static List<StudentGraph> studentGraphList = new ArrayList<>();

    public static void initializeBooleanForIntervention()
    {
        isActionCorrect = false;
        isEffectSatisfyDesiredOutcomes = false;
    }

    public static void createInstance()
    {
        if (instance == null)
        {
            instance = new ApplicationController();

            if(mainContext == null)
            {
                //DO NOTHING. If UNITY CALL, THIS METHOD WILL BE CALLED FIRST
            }

            Logger.addRecordToLog( "--> ApplicationController, createInstance()" );
        }
    }

    public static void initializeMapsForInterventions() throws Exception
    {
        InputStream interventionTemplateInputStream = am.open("template/intervention.txt");
        InputStream domainPredicateInputStream = am.open("template/domain_predicate.txt");
        InputStream numericComparisonInputStream = am.open("template/numeric_comparison.txt");

        //i_template_numeric_comparison_map

//        InterventionController2.initializeInterventionMap( interventionTemplateInputStream, InterventionController2.intervention_template_map);
//        InterventionController2.initializeInterventionMap( domainPredicateInputStream, InterventionController2.domain_predicate_map);

        InterventionController2.initializeInterventionMap( interventionTemplateInputStream, intervention_template_map);
        InterventionController2.initializeInterventionMap( domainPredicateInputStream, domain_predicate_map);
        InterventionController2.initializeInterventionMap( numericComparisonInputStream, i_template_numeric_comparison_map );
    }

    //Mes - name of subfolder to be read inside folder assets
    public static String subfolder_name = "";

    public static void initialize() throws Exception {

//        InputStream domainInputStream = am.open("main_domain.pddl");
//        InputStream problemInputStream = am.open("main_problem.pddl");
//        InputStream planInputStream = am.open("main_plan.pdl");
        InputStream predicateFunctionInputStream = am.open("user_pair_predicate_function.txt");
        InputStream stepInputStream = am.open("user_action_mapping.txt");

        //InputStream numericComparisonInputStream = am.open("template/numeric_comparison.txt");

        //------------------//
        // PDDL for IUI     //
        //------------------//
        subfolder_name = "pddl_iui/";
        InputStream domainInputStream = am.open(subfolder_name + "main_domain.pddl");
        InputStream problemInputStream = am.open(subfolder_name + "main_problem.pddl");
        InputStream planInputStream = am.open(subfolder_name + "main_plan.pdl");

        initializeMapsForInterventions();

        //parse pddl and pdl files
        Parser parser = ParserController.parse(domainInputStream, problemInputStream, planInputStream);

        //set domain problem and plan to ParserController
        ParserController.domain = parser.getDomain();
        ParserController.problem = parser.getProblem();
        ParserController.plan = parser.getPlan();


        // -------------------------------------
        // Create type map of problem.
        // -------------------------------------
        InferenceEngineUtil.generateTypeMap(parser); // hashmap of type and
        // instance from
        // problem pddl.

        //transform domain axiom into ground object
        InferenceEngineUtil.transformDomainAxiomToGround(parser);
        //transform action into ground object
        InferenceEngineUtil.transformActionToGround(parser);

        //initialize S0 into WorldState
        InferenceEngineUtil.initializedWorldState(parser);

        // -------------------------------------
        // process Inference or domain rules
        // -------------------------------------
        List<Exp> currentFacts = WorldStateUtil.getCurrentFactPredicate();
        InferenceEngineUtil.processInference(currentFacts);//run domain rules

        //initial predicate function for comparing value
        UserActionUtil.initializeComparisonPairs(predicateFunctionInputStream);

        //initial user action mapping into Hash Map
        UserActionUtil.initialUserActionMapping(stepInputStream);

        //initialize first action in currentAction value // Mes edited
        currentAction = TutorPointer.pointToFirstPlanAction();

        //initial student graph
        StudentGraphUtil.initializeStudentGraph();

        //initial tutor graph
        TutorGraphUtil.initialValue();

        //Nui Test
        //List<GraphNode> tutorGraph = TutorGraphUtil.getTutorGraph( currentAction.getActionName() );
        //TutorGraphUtil.printTutorGraphDetails( tutorGraph );

        TutorGraphUtil.printAllTutorGraph();
//        List<GraphNode> graph = TutorGraphUtil.getTutorGraph("insert_rubber_dam");
//        TutorGraphUtil.printTutorGraphDetails(graph);
//        System.out.println("student Graph size: "+ApplicationController.tutorGraphList.size());

//        Logger.addRecordToLog( "--> ApplicationController, initialize()" );
    }

    public static void createFolder()
    {
        File folder = new File( Environment.getExternalStorageDirectory().getPath()
                + MainConstants.APPLICATION_ROOT_PATH );

        if (!folder.exists()) {
            System.out.println("create Folder");
            folder.mkdir();
        }

//        Logger.addRecordToLog( "--> ApplicationController, createFolder()" );
    }


    public static void setMainContext( Context context )
    {
        if( mainContext == null )
        {
            Logger.addRecordToLog( "--> ApplicationController, setMainContext(), mainContext : " + context +".");
            mainContext = context;
            ApplicationConstants.mainContext = context;
        }
    }

    public static Context getMainContext( Context context )
    {
        return mainContext;
    }

    //TEst method for pushing service.
    private static void pushService(String str)
    {
        LogUtils.addLog( "--> APPLICATION_CONTROLLER.PUSH_SERVICE(), Context: " + mainContext + ",  String: " + str + ".");

        //CALL PUSH SERvice.
        CommunicationController.sendDataToUnity( mainContext, str );
    }

    public static void receiveUnityDataStreamWithContext(Context context, String xmlString )
    {
        LogUtils.addLog( "--> APPLICATION_CONTROLLER.receiveUnityDataStreamWithContext(), Context: " + context + ",  String: " + xmlString + ".");
        mainContext = context;
        pushService( xmlString );
    }
}
