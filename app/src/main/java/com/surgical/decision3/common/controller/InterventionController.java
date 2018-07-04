package com.surgical.decision3.common.controller;

import com.surgical.decision3.common.bean.intervention.Intervention;
import com.surgical.decision3.common.bean.intervention.InterventionData;
import com.surgical.decision3.common.bean.intraoperative.IntraOperativeCollection;
import com.surgical.decision3.common.bean.procedure.Domain;
import com.surgical.decision3.common.bean.procedure.PatientCase;
import com.surgical.decision3.common.bean.radiograph.Radiograph;
import com.surgical.decision3.common.bean.tool.DrillAction;
import com.surgical.decision3.common.bean.tool.RoundDiamondBur;
import com.surgical.decision3.common.bean.tool.SelectedToolTray;
import com.surgical.decision3.common.bean.tool.ToolTray;
import com.surgical.decision3.common.bean.tooth.rootcanal.RootCanal;
import com.surgical.decision3.common.bean.tooth.rootcanal.RootCanalSecondThird;
import com.surgical.decision3.common.bean.intervention.action.CommandAction;
import com.surgical.decision3.common.bean.intervention.action.CommandActionObject;
import com.surgical.decision3.common.bean.intervention.action.CommandActionStep;
import com.surgical.decision3.common.bean.intervention.forcePlayback.ForcePlayback;
import com.surgical.decision3.common.bean.patient.Patient;
import com.surgical.decision3.common.bean.procedure.Procedure;
import com.surgical.decision3.common.bean.procedure.SubDomain;
import com.surgical.decision3.common.bean.procedure.SubProcedure;
import com.surgical.decision3.common.bean.question.Choice;
import com.surgical.decision3.common.bean.question.Question;
import com.surgical.decision3.common.bean.response.Response;
import com.surgical.decision3.common.bean.scene.Scene;
import com.surgical.decision3.common.bean.tooth.Dentine;
import com.surgical.decision3.common.bean.tooth.Enamel;
import com.surgical.decision3.common.bean.tooth.PulpChamber;
import com.surgical.decision3.common.bean.tooth.WorkingTooth;
import com.surgical.decision3.common.bean.tooth.rootcanal.RootCanalFirstThird;
import com.surgical.decision3.common.constant.SceneConstants;
import com.surgical.decision3.common.bean.disease.CongenitalDisease;
import com.surgical.decision3.common.bean.patient.PatientMouth;
import com.surgical.decision3.common.bean.procedure.SubProcedureStep;
import com.surgical.decision3.common.bean.tool.Clamp;
import com.surgical.decision3.common.bean.tool.SelectedTool;
import com.surgical.decision3.common.constant.ToolConstants;

import java.util.ArrayList;

public class InterventionController {

	public static Response prepareIntraOperativeInitialization_SAMPLE()
	{
		IntraOperativeCollection intraCollection = new IntraOperativeCollection();

		// TODO: CHANGE DOMAIN INTO SUB DOMAIN, ADD DENTAL SURGERY INTO DOMAIN

		// SCENE, DOMAIN, PROCEDURE, SUBPROCEDURE
//		intraCollection.setScene( new Scene( 1, "INTRAOPERATIVE_SCENE",
//				"INTRAOPERATIVE_INITIALIZE" ) );

		intraCollection.setScene( new Scene( 1,
				SceneConstants.SCENE_INTRAOPERATIVE_NAME,
				SceneConstants.SCENE_INTRAOPERATIVE_EVENT_INITIALIZE));


		// intraCollection.setSubDomain( new SubDomain( 1, "endodontic", "ENDO",
		// "endodontic" ) );
		// intraCollection.setDomain( new Domain( 1, "Dental Surgery", "DN",
		// "Surgery related tooth" ) );

		intraCollection.setDomain( new Domain( 1, "endodontic", "ENDO",
				"endodontic" ) ); // TO REMOVE
		intraCollection.setProcedure( new Procedure( 1, "Root canal Treatment",
				"RCT", "Root canal Treatment", 1) );
		intraCollection.setSubProcedure( new SubProcedure( 1,
				"Access Opening (OC)", "OC", "Access Opening", 1 ) );
		// intraCollection.setSubProcedureStep( new SubProcedureStep(1,
		// "Outline OC Shape", "OC_OUTLINE", "Outline OC Shape", 1 ) );

		// PATIENT CASE
		PatientCase c = new PatientCase( 1, "root canal treatment case1",
				"root canal treatment case1 desc" );
		c.setPatient( new Patient( 3, "p_fn", "p_ln", 25, "MALE" ) );

		c.getRadiographs()
				.add( new Radiograph( 1, "r1", "2015-10-07 08:29:18.239 EDT", "/radio1" ) );
		c.getRadiographs()
				.add( new Radiograph( 2, "r2", "2015-10-07 08:29:18.239 EDT", "/radio2" ) );
		c.getRadiographs()
				.add( new Radiograph( 3, "r3", "2015-10-07 08:29:18.239 EDT", "/radio3" ) );

		c.setWorkingTooth( getWorkingTooth() );

		c.getCongenitalDiseases().add(
				new CongenitalDisease( 1, "High BP", "High Blood Pressure" ) );
//		c.getPatientHistories().add(
//				new PatientHistory( 1, new DateTimeStamp( new Date() ),
//						"Tooth Extraction", "Successful" ) );

		intraCollection.setPatientCase( c );

		// SELECTED TOOLS
		intraCollection.setSelectedToolTray( getSelectedToolTray() );

		return new Response( intraCollection );
	}
	public static Response prepareQuestion_SAMPLE(){
		Question q = new Question();
		q.setQuestion("Can you imagine why the root canal sealing is maximized?");


		Choice choices = new Choice();
		ArrayList<Choice> choiceSet = new ArrayList<Choice>();
		choiceSet.add( new Choice( "the root canal surface is not slipped", true, false ) );
		choiceSet.add( new Choice( "the main cone size equals to the MAF size", false, false ) );
		choiceSet.add( new Choice( "the main cone size is greater than the MAF size", false, false ) );
		choiceSet.add( new Choice( "the main cone size is less than the MAF size", false, false ) );
		q.setChoices( choiceSet );
		//q.getChoice().add(new Choice(0,"the root canal surface is not slipped")); // WHY CAN'T IT BE USED???
		return new Response(q);
	}

	public static Response prepareForcePlayback()
	{
		Intervention i = new Intervention();

		InterventionData invData = new InterventionData();

		ForcePlayback fpb = new ForcePlayback();
		fpb.setDomain( new Domain( 1, "Dental Surgery", "DN", "Surgery about tooth" ) ); // TO REMOVE
		fpb.setSubDomain( new SubDomain( 1, "endodontic", "ENDO", "endodontic", 1 ) );
		fpb.setProcedure( new Procedure( 1, "Root canal Treatment", "RCT",
				"Root canal Treatment", 1) );
		fpb.setSubprocedure( new SubProcedure( 1, "Access Opening (OC)", "OC",
				"Access Opening", 1 ) );
		fpb.setSubprocedureStep( new SubProcedureStep( 1, "Outline OC Shape",
				"OC_OUTLINE", "Outline OC Shape", 1 ) );

		CommandActionStep actionStep_1 = new CommandActionStep();
		actionStep_1.setStepNo( 1 );
		actionStep_1.setStepName( "Tools preparation" );
		actionStep_1
				.setNarration( "To provide the rubber dam application, there are two important steps: preparing tools, and apply tools to target tooth." );
		actionStep_1.addCommandAction( getCommandAction() ); //addCommandAction causes error in method UnMarshall
		fpb.addCommandActionStep( actionStep_1 );

		invData.setForcePlayback( fpb );
		i.setInterventionData( invData );


		return new Response( i );
	}
	public static CommandAction getCommandAction()
	{
		CommandAction action = new CommandAction();
		action.setActionCode( "1" );
		action.setSemanticActionCode( "SHOW" );
		action.setActionDescription( "Show screen of a patient's mouth." );

		PatientMouth pMouth = new PatientMouth();
		pMouth.setWorkingTooth( getWorkingTooth() );
		CommandActionObject toObject = new CommandActionObject();
		toObject.setDetailsObject( pMouth.getWorkingTooth() );
		// toObject.setType( pMouth.getWorkingTooth().toString() );
		toObject.setLocation( new PatientMouth() );

		RoundDiamondBur rdBur = new RoundDiamondBur();
		rdBur.setProduct_id( "697-009M" );
		rdBur.setHeadNo( "009" );
		rdBur.setDiameter( 0.9 );
		rdBur.setToolAction( new DrillAction( "OCCUSAL", 2 ) );
		rdBur.setToolType( "BUR" );
		rdBur.setToolName( "ROUND_DIAMOND_BUR" );

		CommandActionObject onObject = new CommandActionObject();
		onObject.setDetailsObject( rdBur );
		// onObject.setType( rdBur.toString() );
		ToolTray tTray = new ToolTray();
		tTray.addTool( rdBur );
		onObject.setLocation( tTray );

		action.setOnObject( onObject );
		action.setToObject( toObject );

		return action;
	}
	private static SelectedToolTray getSelectedToolTray()
	{
		/*
		 * RoundDiamondBur rdBur = new RoundDiamondBur(); rdBur.setProduct_id(
		 * "697-009M" ); rdBur.setHeadNo( "009" ); rdBur.setDiameter( 0.9 );
		 * rdBur.setToolAction( new DrillAction( "OCCUSAL", 2 ) );
		 * rdBur.setToolType( "BUR" ); rdBur.setToolName( "ROUND_DIAMOND_BUR" );
		 *
		 * CommandActionObject onObject = new CommandActionObject();
		 * onObject.setDetailsObject( rdBur ); //onObject.setType(
		 * rdBur.toString() ); ToolTray tTray = new ToolTray(); tTray.addTool(
		 * rdBur ); onObject.setLocation( tTray );
		 *
		 * action.setOnObject( onObject ); action.setToObject( toObject );
		 */
		SelectedToolTray sToolTray = new SelectedToolTray();

		SelectedTool sTool1 = new SelectedTool();

		Clamp clamp = new Clamp();
		clamp.setToolName( "CLAMP" );
		clamp.setToolType( "CLAMP" );
		clamp.setProduct_id( "1" );
		clamp.setClampNo( "14A" );

		sTool1.setToolObject( clamp );
		sTool1.setToolName( clamp.getToolName() );
		sTool1.setToolType( clamp.getToolType() );
		sTool1.setAmount( 1 );
		sTool1.setHandle( ToolConstants.TOOL_HANDLE_HAND );
		sTool1.setSelected( false );
		sTool1.addToolAction( ToolConstants.TOOL_ACTION_INSERT );

		sToolTray.addTool( sTool1 );

		return sToolTray;
	}
	private static WorkingTooth getWorkingTooth()
	{
		WorkingTooth wt = new WorkingTooth();
		wt.setId( 1 );
		wt.setToothNo( 28 );
		wt.setName( "premolar" );
		wt.setPermanent( true );
		wt.setMandibular( true );
		wt.setWholelength( 20 );

		Enamel en = new Enamel();
		en.setThickness( 2 );
		wt.setEnamel( en );

		Dentine dt = new Dentine();
		dt.setThickness( 2 );
		wt.setDentine( dt );

		PulpChamber pc = new PulpChamber();
		pc.setHeight( 4 );
		pc.setWidth( 2 );
		wt.setPulpChamber( pc );

		RootCanal root = new RootCanal();
		root.setNo( 1 );
		root.setLength( 5 );

		RootCanalFirstThird firstThird = new RootCanalFirstThird();
		firstThird.setCurved( false );
		root.setFirstThird( firstThird );

		RootCanalSecondThird secondThird = new RootCanalSecondThird();
		secondThird.setCurved( false );
		root.setSecondThird( secondThird );

		wt.getRootCanals().add( root );

		return wt;
	}
}