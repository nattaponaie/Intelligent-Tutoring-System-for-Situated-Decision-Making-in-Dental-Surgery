package com.surgical.decision3.common.bean.answer.constants;

public class ErrorPromptCodeConstants
{
	public static final String INSUFFICIENT_TOOLS = "INSUFFICIENT_TOOLS";
	public static final String INVALID_TOOLS = "INVALID_TOOLS";
	public static final String INVALID_CLAMP_NO_TOO_SMALL = "INVALID_CLAMP_NO_TOO_SMALL";
	public static final String INVALID_CLAMP_NO_TOO_LARGE = "INVALID_CLAMP_NO_TOO_LARGE";
	public static final String INVALID_OUTLINE_SHAPE = "INVALID_OUTLINE_SHAPE";
	public static final String INVALID_TOOL_TYPE ="INVALID_TOOL_TYPE";
	public static final String INVALID_TOOL_NO_TOO_SMALL = "INVALID_TOOL_NO_TOO_SMALL";
	public static final String INVALID_TOOL_NO_TOO_LARGE = "INVALID_TOOL_NO_TOO_LARGE";
	public static final String INVALID_DRILL_DIRECTION_ANTERIOR = "INVALID_DRILL_DIRECTION_ANTERIOR";
	public static final String INVALID_INSERT_DIRECTION = "INVALID_INSERT_DIRECTION";
	public static final String INVALID_INSERT_ENTRY_POINT = "INVALID_INSERT_ENTRY_POINT";
	public static final String INVALID_INSERT_DEPTH_FOR_K_FILE_TO_COAT_TOO_SHALLOW = "INVALID_INSERT_DEPTH_FOR_K_FILE_TO_COAT_TOO_SHALLOW";
	public static final String INVALID_INSERT_DEPTH_FOR_K_FILE_TO_COAT_TOO_DEEP = "INVALID_INSERT_DEPTH_FOR_K_FILE_TO_COAT_TOO_DEEP";
	
	public static final String INVALID_DRILL_DIRECTION = "INVALID_DRILL_DIRECTION";
	public static final String INVALID_DRILL_ENTRY_POINT = "INVALID_DRILL_ENTRY_POINT";
	public static final String INVALID_DRILL_DEPTH_TOO_SHALLOW = "INVALID_DRILL_DEPTH_TOO_SHALLOW";
	public static final String INVALID_DRILL_DEPTH_TOO_DEEP_TO_PULP_CHAMBER_FLOOR = "INVALID_DRILL_DEPTH_TOO_DEEP_TO_PULP_CHAMBER_FLOOR";
	public static final String INVALID_DRILL_DEPTH_TOO_DEEP = "INVALID_DRILL_DEPTH_TOO_DEEP";	//ADD
//	public static final String INVALID_TOOL_FOR_TOOTH_TYPE = "INVALID_TOOL_FOR_TOOTH_TYPE";
	public static final String INVALID_DRILL_DEPTH_TOO_DEEP_TO_APICAL = "INVALID_DRILL_DEPTH_TOO_DEEP_TO_APICAL";
	public static final String INVALID_FILE_DIAMETER_TOO_LARGE = "INVALID_FILE_DIAMETER_TOO_LARGE";
	public static final String INVALID_FILE_DIAMETER_TOO_SMALL = "INVALID_FILE_DIAMETER_TOO_SMALL";
	public static final String INVALID_INSERT_DEPTH_TOO_SHALLOW = "INVALID_INSERT_DEPTH_TOO_SHALLOW";
	public static final String INVALID_INSERT_DEPTH_TOO_DEEP = "INVALID_INSERT_DEPTH_TOO_DEEP";
	public static final String INVALID_ACTION = "INVALID_ACTION"; 
	
	public static final String INVALID_TOOL_NO_NOT_FOUND = "INVALID_TOOL_NO_NOT_FOUND";	//not used
	
	/**
	 * Additional error. from query Distinct error_code from error_prompt
	 * */
	public static final String INVALID_ADJUST_DEPTH_TOO_DEEP					    = "INVALID_ADJUST_DEPTH_TOO_DEEP";
	public static final String INVALID_ADJUST_DEPTH_TOO_SHALLOW                     = "INVALID_ADJUST_DEPTH_TOO_SHALLOW";            
	public static final String INVALID_TOOL_NUMBER                                  = "INVALID_TOOL_NUMBER";                         
	public static final String INVALID_TOOL_LENGTH_TOO_SHORT                        = "INVALID_TOOL_LENGTH_TOO_SHORT";
	public static final String INVALID_TOOL_TYPE_TO_PREPARE_IRREGATION              = "INVALID_TOOL_TYPE_TO_PREPARE_IRREGATION";     
	public static final String INVALID_MATERIAL_TYPE_TO_PREPARE_IRREGATION          = "INVALID_MATERIAL_TYPE_TO_PREPARE_IRREGATION"; 

	public static final String INVALID_TOOL_SET_TYPE_TO_IRREGATE                 	= "INVALID_TOOL_SET_TYPE_TO_IRREGATE";             
	public static final String INVALID_TOOL_SET_TYPE_TO_STERILE	             	    = "INVALID_TOOL_SET_TYPE_TO_STERILE";
	
	public static final String INVALID_TOOL_TYPE_TO_DRY_ROOT_CANAL                  = "INVALID_TOOL_TYPE_TO_DRY_ROOT_CANAL";
	public static final String INVALID_TOOL_TYPE_TO_SOAK                            = "INVALID_TOOL_TYPE_TO_SOAK";
	public static final String INVALID_TOOL_TO_STERILE                              = "INVALID_TOOL_TO_STERILE";                     
	public static final String INVALID_TOOL_TO_COAT                                 = "INVALID_TOOL_TO_COAT";                        
	
	public static final String INVALID_MATERIAL_TYPE_TO_SOAK                        = "INVALID_MATERIAL_TYPE_TO_SOAK";
	public static final String INVALID_MATERIAL_TYPE_TO_STERILE                     = "INVALID_MATERIAL_TYPE_TO_STERILE";
	                   
	           
	public static final String INVALID_TOOL_SET_NOT_STERILE                         = "INVALID_TOOL_SET_NOT_STERILE";                
	public static final String INVALID_MATERIAL_TYPE_TO_MIX_CEMENT                  = "INVALID_MATERIAL_TYPE_TO_MIX_CEMENT";         
	//public static final String INVALID_MATERIAL_SET_TO_COAT                         = "INVALID_MATERIAL_SET_TO_COAT";                
	//public static final String INVALID_TOOL_SET_TO_STERILE                          = "INVALID_TOOL_SET_TO_STERILE";
	public static final String INVALID_TOOL_SET_TYPE_TO_COAT                             = "INVALID_TOOL_SET_TYPE_TO_COAT";
	public static final String INVALID_TOOL_SET_OR_TYPE_NOT_COATED                  = "INVALID_TOOL_SET_OR_TYPE_NOT_COATED";         
	public static final String INVALID_INSERT_DEPTH_FOR_GUTTA_PERCHA_TOO_SHALLOW    = "INVALID_INSERT_DEPTH_FOR_GUTTA_PERCHA_TOO_SHALLOW";       
	public static final String INVALID_INSERT_DEPTH_FOR_GUTTA_PERCHA_TOO_DEEP       = "INVALID_INSERT_DEPTH_FOR_GUTTA_PERCHA_TOO_DEEP";
	public static final String INVALID_TOOL_TO_CUT                                  = "INVALID_TOOL_TO_CUT";       
	
	public static final String INVALID_PRESS_ENTRY_POINT							= "INVALID_PRESS_ENTRY_POINT";
	public static final String INVALID_PRESS_DIRECTION								= "INVALID_PRESS_DIRECTION";
	public static final String INVALID_PRESS_CEMENT_DEPTH_TOO_DEEP                  = "INVALID_PRESS_CEMENT_DEPTH_TOO_DEEP";         
	public static final String INVALID_PRESS_CEMENT_DEPTH_TOO_SHALLOW               = "INVALID_PRESS_CEMENT_DEPTH_TOO_SHALLOW";
	
	public static final String INVALID_UNPREPARED_CEMENT_MIXTURE = "INVALID_UNPREPARED_CEMENT_MIXTURE";
	public static final String INVALID_UNPREPARED_TOOL_GUTTA_PERCHA_NOT_SOAK        = "INVALID_UNPREPARED_TOOL_GUTTA_PERCHA_NOT_SOAK";
	public static final String INVALID_UNPREPARED_TOOL_GUTTA_PERCHA_NOT_SOAK_NOT_STERILE    = "INVALID_UNPREPARED_TOOL_GUTTA_PERCHA_NOT_SOAK_NOT_STERILE";
	public static final String INVALID_UNPREPARED_TOOL_GUTTA_PERCHA_NOT_STERILE_BUT_SOAK    = "INVALID_UNPREPARED_TOOL_GUTTA_PERCHA_NOT_STERILE_BUT_SOAK";
}
