package com.surgical.decision3.intervention.constants;

public class InterventionTemplateKeyConstants
{
	//--------------------------------------------------------------------------
	// PHASE 1.1
	//--------------------------------------------------------------------------

	public static String ST_9_TELL_ANSWER = "st9_tell_answer";
	public static String ST_10_HINT = "st10_hint";
	public static String ST_11_RECONFIRM_ACTION = "st11_reconfirm_action";
	public static String ST_11_RECONFIRM_CORRECT_ACTION = "st11_reconfirm_correct_action";
	public static String ST_6_1_QUESTION = "st6_1_question";
	public static String ST_6_1_QUESTION_PARENT_PLAN = "st6_1_question_parent_plan";
	//public static String ST_6_1_QUESTION_RECONFIRM_ACTION_PERCEPTION = "st6_2_question_reconfirm_perception";
	public static String ST_6_2_QUESTION = "st6_2_question";
	//public static String ST_6_2_QUESTION_RECONFIRM_ACTION_CONSEQUENCE = "st6_2_question_reconfirm_consequence";
	public static String ST_7_1_QUESTION_FROM_6_1 = "st7_1_question_from_6_1";
	public static String ST_7_1_QUESTION_FROM_6_1_PARENT_PLAN = "st7_1_question_from_6_1_parent_plan";

    public static String ST_7_2_QUESTION_FROM_6_2 = "st7_2_question_from_6_2";

	public static String ST_7_1_QUESTION = "st7_1_question";
	public static String ST_7_2_QUESTION = "st7_2_question";
	public static String ST_7_3_QUESTION_RELEVANT_NODE = 	"st7_3_question_relevant_node";

	public static String ST_8_QUESTION = "st8_question";

	public static String ST_14_PROMPT_REPEATED_INCORRECT_ACTION = "st14_prompt_repeated_incorrect_action";
	public static String ST_15_ALLOW_EXPLORE_HINT = "st15_hint_allow_explore";

	public static String ST_X_HINT_INCORRECT_ACTION = "stx_hint_incorrect_action";

	//--------------------------------------------------------------------------
	// PHASE 1.0
	//--------------------------------------------------------------------------
	// Intraoperative
	public static String LEADING_POSITIVE_FB_Main = "leading_positive_fb_";
	public static String LEADING_POSITIVE_FB_1 = "leading_positive_fb_1"; 
	public static String LEADING_POSITIVE_FB_2 = "leading_positive_fb_2";
	public static String LEADING_POSITIVE_FB_3 = "leading_positive_fb_3";
	
	public static String POSITIVE_FEEDBACK = "positive_fb";				//this may not use 
	public static String POSITIVE_FEEDBACK_2_1 = "positive_fb_2_1";
	public static String POSITIVE_FEEDBACK_2_2 = "positive_fb_2_2";		//Cnode
	public static String POSITIVE_FEEDBACK_2_3 = "positive_fb_2_3";		//pjNode

	public static String LEADING_NEGATIVE_FB_Main = "leading_negative_fb_";
	public static String LEADING_NEGATIVE_FB_1 = "leading_negative_fb_1";
	public static String LEADING_NEGATIVE_FB_2 = "leading_negative_fb_2";
	public static String LEADING_NEGATIVE_FB_3 = "leading_negative_fb_3";

	
	public static String NEGATIVE_FEEDBACK_1ST_1 = "negative_fb_1st_1";
	public static String NEGATIVE_FEEDBACK_1ST_2 = "negative_fb_1st_2";
	public static String NEGATIVE_FEEDBACK_1ST_3 = "negative_fb_1st_3";


	public static String NEGATIVE_FEEDBACK_1ST2_1 = "negative_fb_1st2_1";
	public static String NEGATIVE_FEEDBACK_1ST2_2 = "negative_fb_1st2_2";
	public static String NEGATIVE_FEEDBACK_1ST2_3 = "negative_fb_1st2_3";

	public static String NEGATIVE_FEEDBACK_1ST = "negative_fb_1st";
	
	public static String NEGATIVE_FEEDBACK_2ND_1 = "negative_fb_2nd_1";
	public static String NEGATIVE_FEEDBACK_2ND_2 = "negative_fb_2nd_2";
	public static String NEGATIVE_FEEDBACK_2ND_3 = "negative_fb_2nd_3";
	public static String NEGATIVE_FEEDBACK_2ND_4 = "negative_fb_2nd_4";
	
	//-------------------------
	// Question Answer session
	//-------------------------
	public static String LEVEL1_QUESTION_VALIDATE_PJNODE_LABEL = "level1_question_validate_pjnode_label";
	public static String INFORM_OUTCOME="inform_outcome";
	
	public static String STEP2_QUESTION_ACNODE_MAIN = "step2_question_acnode_label_";
	public static String STEP2_QUESTION_ACNODE_LABEL_1 = "step2_question_acnode_label_1";
	public static String STEP2_QUESTION_ACNODE_LABEL_2 = "step2_question_acnode_label_2";
	public static String STEP2_QUESTION_ACNODE_LABEL_3 = "step2_question_acnode_label_3";
	
	//-----------------------------
	// Question Answer for Details
	//-----------------------------
	public static String DETAIL_QUESTION_PNODE_LABEL_1 = "detail_question_pnode_label_1";
	public static String DETAIL_QUESTION_PNODE_LABEL_2 = "detail_question_pnode_label_2";
	public static String DETAIL_QUESTION_PNODE_LABEL_3 = "detail_question_pnode_label_3";
	
	/*
	 detail_question_non_user_action_pnode_label_1=What kind of information required to identify [CNODE] ?
detail_question_non_user_action_pnode_label_2=What kind of information you need to decide for the [CNODE] ?
detail_question_non_user_action_pnode_label_3=What kind of information referring to [CNODE] ?

	 * */
	
	public static String DETAIL_QUESTION_NON_USER_ACTION_LABEL_Main = "detail_question_non_user_action_pnode_label_";
	public static String DETAIL_QUESTION_NON_USER_ACTION_LABEL_1 = "detail_question_non_user_action_pnode_label_1";
	public static String DETAIL_QUESTION_NON_USER_ACTION_LABEL_2 = "detail_question_non_user_action_pnode_label_2";
	public static String DETAIL_QUESTION_NON_USER_ACTION_LABEL_3 = "detail_question_non_user_action_pnode_label_3";
	
	public static String SHORT_POSITIVE_FB_Main = "short_positive_fb_";
	public static String SHORT_POSITIVE_FB_1 = "short_positive_fb_1"; 
	public static String SHORT_POSITIVE_FB_2 = "short_positive_fb_2";
	public static String SHORT_POSITIVE_FB_3 = "short_positive_fb_3";
	public static String SHORT_POSITIVE_FB_4 = "short_positive_fb_4";
	
	public static String SHORT_NEGATIVE_FB_Main = "short_negative_fb_";
	public static String SHORT_NEGATIVE_FB_1 = "short_negative_fb_1";
	public static String SHORT_NEGATIVE_FB_2 = "short_negative_fb_2";
	public static String SHORT_NEGATIVE_FB_3 = "short_negative_fb_3";
	
	public static String TELL_ANSWER_PNODE = "tell_answer_pnode_label";
	public static String TELL_ANSWER_CNODE = "tell_answer_pnode_cnode_label";
	public static String TELL_HINT_PNODE = "hint_pnode_label";
	public static String TELL_HINT_CNODE = "hint_cnode_label";
	
	//level2
	public static String TELL_ANSWER_LEVEL2_LABEL_1 = "tell_answer_level2_label_1";
}
