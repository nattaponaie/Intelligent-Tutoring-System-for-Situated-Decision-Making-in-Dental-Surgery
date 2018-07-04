package com.surgical.decision3.common.bean.answer.constants;

public class ErrorMessageConstants
{
	public static final String PRE_OPT_INVALID_ACTION_NAME = "Invalid preoperative action name";
	
	public static final String PRE_OPT_INVALID_SCENE_NAME = "Invalid scene name";
	public static final String PRE_OPT_INVALID_LOGIN_EVENT = "Invalid login event";
	public static final String PRE_OPT_INVALID_DOMAIN_EVENT = "Invalid domain selection event";
	public static final String PRE_OPT_INVALID_REGISTRATION_EVENT = "Invalid registration event";
	public static final String PRE_OPT_INVALID_CASE_EVENT = "Invalid case event";
	public static final String PRE_OPT_INVALID_PLAN_EVENT = "Invalid plan event";
	public static final String PRE_OPT_INVALID_TOOLS_SELECTION_EVENT = "Invalid Tool Selection event";
	
	
	public static final String PRE_OPT_LOGIN_LOGIN_FAIL = "Login fail !!";		//DEFAULT MESSAGE
	public static final String PRE_OPT_LOGIN_INCORRECT_PASSWORD = "Login fail, entered password is not correct.";
	public static final String PRE_OPT_LOGIN_USER_NOT_EXISTS = "Login fail, entered user is not available at server. Please register to the system.";
	public static final String PRE_OPT_LOGIN_INPUT_USER_MISSING = "User input is missing.";
	public static final String PRE_OPT_LOGIN_INPUT_PASSWORD_MISSING = "Password is missing.";
	public static final String PRE_OPT_LOGIN_INPUT_MISSING = "Password is missing.";
	
	public static final String PRE_OPT_REG_REGISTRATION_FAIL = "Registration fail !!";		//DEFAULT MESSAGE
	public static final String PRE_OPT_REG_USER_EXISTS = "Registration fail !! Entered user exists.";
	public static final String PRE_OPT_REG_PASSWORD_NOT_EQUAL_TO_CONFIRM_PASSWORD = "Registration fail !! Entered password is not matched to confirm password.";
	public static final String PRE_OPT_REG_PASSWORD_NULL_OR_EMPTY = "Registration fail !! Entered password is missing.";
	public static final String PRE_OPT_REG_CONFIRMED_PASSWORD_NULL_OR_EMPTY = "Registration fail !! Entered confirm password is missing.";
	
	public static final String PRE_OPT_DOMAINS_INITIAL_DOMAINS_FAIL = "Initial domains, subdomains, and procedures fail !!!";	//DEFAULT MESSAGE
	public static final String PRE_OPT_DOMAINS_SELECT_DOMAIN_INVALID_PARAMETER = "Selected domain, subdomain, and procedure are not correct.";
	public static final String PRE_OPT_DOMAINS_LOAD_DOMAINS_ERROR = "Load domains error !!!";
	public static final String PRE_OPT_DOMAINS_LOAD_SUBDOMAINS_ERROR = "Load subdomains error !!!";
	public static final String PRE_OPT_DOMAINS_LOAD_PROCEDURES_ERROR = "Load procedures error !!!";
	
	public static final String PRE_OPT_DOMAINS_SELECT_DOMAIN_INVALID_SELECTING_PARAMETER = "Parameters of one of selected domain, subdomain, procedure, subprocedure, and tools are not correct.";

	
	public static final String PRE_OPT_CASE_INITIAL_FAIL = "Initial cases fail !!!";	//DEFAULT MESSAGE
	public static final String PRE_OPT_CASE_INITIAL_INVALID_PROCEDURE = "Invalid parameter !!!";
	public static final String PRE_OPT_CASE_SELECT_INCORRECT_CASE = "Selected patient case are not correct.";
	
	public static final String PRE_OPT_PLAN_INITIAL_FAIL = "Initial plan fail !!!";		//DEFAULT MESSAGE
	public static final String PRE_OPT_PLAN_INITIAL_INVALID_PARAMETER = "Invalid parameter !!!";
	public static final String PRE_OPT_PLAN_SUBMIT_INVALID_PARAMETER = "Invalid parameter !!!";
	public static final String PRE_OPT_PLAN_SUBMIT_INCORRECT_PLAN = "Selected patient case are not correct.";
	
	public static final String PRE_OPT_PLAN_CHECKING_FAIL_TO_LOAD_ANSWER_PLAN = "Loading correct subprocedures error.!!!";
	public static final String PRE_OPT_PLAN_CHECKING_SIZE_IS_NOT_EQUAL = "Submitted steps are not correct. The required steps are not equal to the correct ones.";
	public static final String PRE_OPT_PLAN_CHECKING_SEQUENCE_IS_NOT_CORRECT= "The sequence of steps is not correct.";
	public static final String PRE_OPT_PLAN_CHECKING_ACCUM_ERRORS_REACH_LIMIT = "Your trial for correct plan reaches to limit. System will load correct plans.";		

	public static final String PRE_OPT_TOOLS_SELECTION_INITIAL_FAIL = "Initial tool selection fail !!!";		//DEFAULT MESSAGE
	public static final String PRE_OPT_TOOLS_SELECTION_INITIAL_LOAD_FAIL = "Load all tools fail !!!";
	public static final String PRE_OPT_TOOLS_SELECTION_SUBMIT_SELECTED_TOOLS_NO_TOOLS_SELECTED = "There is no tool selected.";
	
	public static final String INTRA_OPT_INITIAL_FAIL = "Initial Intraoperative Fail!!";		//DEFAULT MESSAGE
	public static final String INTRA_OPT_INVALID_SCENE_MISSING_SCENE_OBJECT = "Scene object is missing.";
	public static final String INTRA_OPT_INVALID_SCENE_NAME = "Invalid scene name";
	public static final String INTRA_OPT_INVALID_SCENE_EVENT_INITIAL = "Invalid scene event";
}
