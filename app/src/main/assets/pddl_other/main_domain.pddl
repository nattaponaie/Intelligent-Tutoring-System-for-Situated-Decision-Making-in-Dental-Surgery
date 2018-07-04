(define (domain rb)
	(:requirements :strips :typing :conditional-effects :universal-preconditions :negative-preconditions :equality
	:fluents :domain-axioms)
	
	;-------
	;Types is perception nodes
	;-------
	(:types 
			tooth_type_constants - constant	
			procedure_constants - constant
			tooth_side_constants - constant
			tooth_jaw_constants - constant
			
			tool_set_constants - constant
			tool_type_constants - constant
			
			working_tooth_number_constants - constant
			
			;;--------------------
			;; LA
			;;--------------------
			anesthesia_type_constants - constant
			landmark_constants - constant
			reference_tooth_no_constants - constant
			target_check_numb_constants - constant
			cartridge_color_constants - constant
			nerve_constants - constant
			;-------------------------------------
			remain_pierce_depth - Action_Parameter
			retract_depth - Action_Parameter
			inject_amount - Action_Parameter
			;-------------------------------------
			
			;;--------------------
			;; RB
			;;--------------------
			clamp_no_constants - constant
			rubber_sheet_position_with_nose_constants - constants
			
			;;--------------------
			;; OC
			;;--------------------
			drill_shape_constants   - constant
			fill_material_constants - constant
			tooth_side_constants - constant
			;bur_type_constants - constant		
			;-------------------------------------
			drill_type - Action_Parameter
			drill_shape - Action_Parameter
			drill_entry - Action_Parameter
			drill_direction - Action_Parameter
			drill_size - Action_Parameter
			drill_depth - Action_Parameter
			;-------------------------------------
			
			
	);end-types
	
	(:constants 
		PREMOLAR MOLAR CANINE ANTERIOR INCISOR - tooth_type_constants
		MANDIBULAR MAXILLARY - tooth_jaw_constants
		LEFT RIGHT - tooth_side_constants
		WT21 - working_tooth_number_constants
		
		RCT TE WTE - procedure_constants
		;ROUND_DIAMOND_BUR STEEL_ROUND_BUR SAFE_TIPPED_TAPER_BUR - bur_type_constants
		
		;;--------------------
		;; LA
		;;--------------------		
		LIDOCAINE MEPIVACAINE - anesthesia_type_constants 
		;AT_CORONOID_NOTCH AT_PTERYGOMANDIBULAR_RAPHAE 
		BETWEEN_CORONOID_NOTCH_AND_PTERYGOMANDIBULAR_RAPHAE - landmark_constants
		RT24 RT26 RT28 - reference_tooth_no_constants
		ID_NERVE LINGUAL_NERVE - nerve_constants
		LOWER_LEFT_LIP LOWER_RIGHT_LIP - target_check_numb_constants
		
		RED CLEAR - cartridge_color_constants
		
		;;--------------------
		;; RB
		;;--------------------	
		UNDER COVER  - rubber_sheet_position_with_nose_constants
		C2 C9 C14 - clamp_no_constants
		RB_TOOL_SET - tool_set_constants
		CLAMP ROUND_DIAMOND_BUR STEEL_ROUND_BUR SAFE_TIPPED_TAPER_BUR - tool_type_constants
		
		;;--------------------
		;; OC
		;;--------------------
		APICAL OCCLUSAL BUCCAL LINGUAL MESIAL DISTAL - tooth_side_constants
		OVAL TRIANGLE UPSIDE_DOWN_TRIANGLE - drill_shape_constants
		AMALGAM MTA - fill_material_constants
	)
			
	(:predicates
		
		( PROCEDURE ?procedure - procedure_constants )
		( WORKING_TOOTH_NO ?wt_no - working_tooth_number_constants)
		( WORKING_TOOTH_SIDE ?wt_side - tooth_side_constants)	:isperceivable
		( WORKING_TOOTH_JAW ?wt_jaw - tooth_jaw_constants )	    :isperceivable
		( WORKING_TOOTH_TYPE ?wt_type - tooth_type_constants )	:isperceivable
		( PATIENT_HAS_UNDERLYING_DISEASE )						:isperceivable
		( TOOL_SET ?tool_set - tool_set_constants )             :isperceivable
		( TOOL_TYPE ?tool_type - tool_type_constants )          :isperceivable
		
		;;--------------------
		;; LA
		;;--------------------		
		(CARTRIDGE ?ct_color - cartridge_color_constants )		            :isperceivable
	    (TIP_AT_ARTERY)
	    (LA_SOLUTION_TYPE  ?sol_type - anesthesia_type_constants)	        :isperceivable
	    (NEEDLE_PIERCED)     									            :isperceivable
	    (LANDMARK ?landmark - landmark_constants )                          :isperceivable
	    (REF_POINT_TOOTH ?tooth_number - reference_tooth_no_constants )     :isperceivable
	    (TARGET_NERVE_BLOCK ?target_nerve - nerve_constants )
	    (ANESTHETIC_IN)  										            :isperceivable
	    (RISK_ANESTHESIA_NOT_LONG_LAST)
	    (TARGET_CHECK_NUMB ?target_numb - target_check_numb_constants)      :isperceivable
	    (LIP_NUMB)   											            :isperceivable
	    (POINT_TO_LINGUAL_NERVE)
	    (POINT_TO_PTERYGOID_MUSCLE)
	    (POINT_TO_CORONOID_NOTCH)
	    (POINT_TO_PTERYGOMANDIBULAR_RAPHAE)
	    (TARGET_ANESTHESIA_AREA_REACH)
	    (PATIENT_HEART_ATTACK )                                             :isperceivable
		(ANESTHESIA_LONG_LAST)                                              :isperceivable
		(PATIENT_ANTIARRHYTHMIC_ACTION)
		
		
		;;--------------------
		;; RB
		;;--------------------	
	    (STRUCTURE_ENOUGH)                          :isPerceivable
		(WT_STRONG)									:isperceivable
		(CLAMP_RISK_TO_RELEASED)					:isperceivable
		(PATIENT_RISK_TO_FAINT)						:isperceivable
		(RB_POS_WITH_NOSE ?rb_pos - rubber_sheet_position_with_nose_constants)  :isperceivable
		(RB_TOOLS_SET)                              :isperceivable
		(FOUR_POINT_CONTACT_ON_TOOTH)               :isperceivable
		(WORKING_TOOTH_SEPARATED)                   :isperceivable
		
		(CLAMP_NO ?clamp_no_constants - clamp_no_constants)         :isperceivable
		;;-------------------------
		(RB_FORCEPS)
		(RB_FRAME)
		(RB_CLAMPS ?clamp_no - clamp_no_constants)
		(RB_SHEET)

		;;--------------------
		;; OC
		;;--------------------
		(PC_FLOOR_VISIBLE)  		            :isperceivable
		(PC_FLOOR_REMOVED)			            :isperceivable
		(CARIES_VISIBLE)                        :isperceivable
		(PC_FLOOR_PERFORATED )		            :isperceivable
		(PC_FLOOR_BLEEDING )                    :isperceivable
		(FILLING_COMPATIBLE_WITH_TISSUE)        :isperceivable
		(WT_FRACTURE)				            :isperceivable
		(WT_ASEPTIC)
		(PC_ASEPTIC)
		(PC_HAS_TISSUE_FALL)
		(PC_XRAY_VISIBLE)			            :isperceivable
		(RESPONSE_CRY_OUT)			            :isperceivable
		(PC_ALL_ROOT_VISIBLE)		            :isperceivable
		(STRUCTURE_OVERCUT)  		            :isperceivable
		(STRUCTURE_LEDGE)                       :isperceivable
		 
	    (DENTINE_SHELF_REMAIN)                  :isperceivable
		(DRILL_SHAPE_ACTION ?wtt - tooth_type_constants )  ;NOTE, parser does not allow to add this to function because it deals with string
		 
		;TOOL_TYPE
		;(BUR_TYPE ?bt - bur_type_constants)
		(DRILL_ENTRY ?entry - tooth_side_constants)             :isperceivable
		(DRILL_DIRECTION ?direction - tooth_side_constants)     :isperceivable
		(DRILL_SHAPE ?shape - drill_shape_constants)            :isperceivable
		 
		;Filling materials
		(FILLING_MATERIAL ?fm - fill_material_constants)        :isperceivable


		;Mes - for testing ONEOF purpose
		(ONEOF_A)
		(ONEOF_B)
	)
	
	
	;;--------------------------------
	;; AXIOMS RB
	;;--------------------------------
	(:axiom 
		:vars ()
		:context( STRUCTURE_ENOUGH )
		:implies ( WT_STRONG )
	)
	 
	(:axiom 
		:vars ()
		:context( NOT (STRUCTURE_ENOUGH ) )
		:implies ( NOT (WT_STRONG) )
	)
	
	;;--------------------------------
	;; AXIOMS OC
	;;--------------------------------
	(:axiom
		:vars ()
		:context ( CARIES_VISIBLE )
		:implies ( NOT( WT_ASEPTIC ))
	)
	
	(:axiom 
		:vars ()
		:context( NOT ( CARIES_VISIBLE ) )
		:implies ( WT_ASEPTIC )
	)
	
	(:axiom 
		:vars ()
		:context( PC_FLOOR_BLEEDING )
		:implies ( PC_FLOOR_PERFORATED )
	)
	
	(:axiom 
		:vars ()
		:context( NOT (PC_FLOOR_BLEEDING) )
		:implies ( NOT (PC_FLOOR_PERFORATED) )
	)
	
	(:functions
		
		;---------------------------------------------------------------------------
		;NOTE: the pair of expressions to compare must be declared here in function.
		;----------------------------------------------------------- 
		
		;;--------------------------------
		;; Function LA
		;;--------------------------------
		( REMAIN_DEPTH_FUNCTION )
		( NEEDLE_LENGTH_FUNCTION )
		( PIERCE_DEPTH_FUNCTION )
		( RETRACT_DEPTH_FUNCTION )
		( ARTERY_DEPTH_FUNCTION )
		( CARTRIDGE_AMT_FUNCTION )
		( INJECT_AMT_FUNCTION )
		
		;;--------------------------------
		;; Function OC
		;;--------------------------------
		( DRILL_SIZE_FUNCTION )
		( PC_WIDTH_SIZE_FUNCTION )
		
		( DRILL_DEPTH_FUNCTION )
		( PC_FLOOR_DEPTH_FUNCTION )
		
		( ENAMEL_DENTINE_THICKNESS_FUNCTION )
		( DIAMETER_SIZE_FUNCTION )
	)
	
	;-----------------------------------------------------------
	; NOTE:  
	; Action is used for testing the domain world.
	; So, the variable declared in the action parameter is not related to 
	; the variables declared in predicate.
	;-----------------------------------------------------------
	; NOTE: PDDL parser cannot do nested conditional effect
	; TODO later: oneOf (non-deterministic)
	

	;;-------------------
	;; Action:
	;;-------------------
	
	;;-------------------
	;; Action: LA select_anesthesia_type
	;;-------------------
    (:action select_anesthesia_type
		:parameters()
		:precondition()
  		:effect ( AND
  				  ;; ASSUMPTION: the anesthesia is set correctly and long last for the whole procedure.
  				  ;; correct 
	      		  (WHEN ( AND ( PATIENT_HAS_UNDERLYING_DISEASE )
	      		              ( LA_SOLUTION_TYPE MEPIVACAINE ) )  ;;Optional solution
	      		  		(AND ( LA_SOLUTION_TYPE MEPIVACAINE )  ;; nothing happen //need to add effect as it is used in the next stage
	      		  			 ( NOT( ANESTHESIA_LONG_LAST ) )
	      		  		     ( NOT(PATIENT_ANTIARRHYTHMIC_ACTION) ) )
	      		  		;; -------------------------------
	      		  		;; NOTE: effect can happen immediately or later with or without conditions. 
	      		  		;; This needs to enhance.
	      		  		;; -------------------------------
			      )
			      
			      ;; correct 
			      (WHEN ( AND ( NOT ( PATIENT_HAS_UNDERLYING_DISEASE ) )
			                  ( LA_SOLUTION_TYPE LIDOCAINE ) )  ;;Optional solution
	      		  		( AND ( LA_SOLUTION_TYPE LIDOCAINE ) 
	      		  			  ( ANESTHESIA_LONG_LAST )
	      		  			  ( NOT(PATIENT_ANTIARRHYTHMIC_ACTION) ) )  ;; nothing happen
			      )
			      
			      ;; incorrect 
			      (WHEN ( AND (PATIENT_HAS_UNDERLYING_DISEASE)(LA_SOLUTION_TYPE LIDOCAINE))  ;;Optional solution
	      		  		( PATIENT_ANTIARRHYTHMIC_ACTION )  ;; actually this effect could happen in the other steps (ref to enhance effect)
			      )
			      
			      (WHEN ( AND (PATIENT_HAS_UNDERLYING_DISEASE)(LA_SOLUTION_TYPE MEPIVACAINE))  ;;Optional solution
	      		  		( NOT( ANESTHESIA_LONG_LAST ) )  ;; the anesthesia may not long last
			      )
			      
	  	);effect
	);action select_anesthesia_type
	
    ;;-------------------
	;; Action: RB insert_rubber_dam
	;;-------------------
    (:action insert_rubber_dam
		:parameters( ?wtt - tooth_type_constants 
					 ?p - rubber_sheet_position_with_nose_constants)
		:precondition()
  		:effect ( AND
			      ;-------------------------------------
			      ; When position of RB Cover/under nose
		      	  ;-------------------------------------
		      	  (when ( RB_POS_WITH_NOSE COVER )
		      	  		( PATIENT_RISK_TO_FAINT) 
		      	  )
				  
;;				  ;; MES: oneof in this section is used for testing purpose only and does not represent final product
;;				  ;; TO TEST THIS ONEOF EFFECT: Please remove line 326 - 328 to avoid duplication of similar action condition, 
;;				  ;; 							then try inserting rubber dam with position cover
;;				  (when ( RB_POS_WITH_NOSE COVER )
;;		      	  		( AND ( PATIENT_RISK_TO_FAINT ) 
;;								(oneof ((0.25) (STRUCTURE_OVERCUT))
;;										((0.25) (NOT (ONEOF_B) ))
;;										((0.5) (ONEOF_A))
;;								)
;;						)
;;		      	  )
		      	  
		      	  (when ( RB_POS_WITH_NOSE UNDER )
		      	  		( NOT (PATIENT_RISK_TO_FAINT) ) 
		      	  )
			      	
			      ;-------------------------------------
			      ; When root canal is strong --> clamp has no risk to be released		
		      	  ;-------------------------------------
				 
  				  (when ( WT_STRONG ) 
						 ( NOT ( CLAMP_RISK_TO_RELEASED ) )
				  )
				  
				  (when ( NOT( WT_STRONG ) ) 
						 ( CLAMP_RISK_TO_RELEASED )  ;; this can be enhance. the clamp may happen any anystep in the procedure
				  )
				  
				  ;------------------------------------------------------
				  ; Correct inserted case
				  ;------------------------------------------------------
				  ( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C2 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  			  (WORKING_TOOTH_SEPARATED)
							  ;( NOT (STRUCTURE_ENOUGH ) )
							  ;( CARIES_VISIBLE )
	      		  		)
			      )

				  ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C14 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
			      
			      ( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C2 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
			      
			      ( WHEN ( AND ( WORKING_TOOTH_TYPE INCISOR )  ( CLAMP_NO C9 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
				  
				;------------------------------------------------------
				; Incorrect Correct inserted case
				;------------------------------------------------------
				; PREMOLAR
				;------------------------------------------------------
				
				( WHEN ( AND ( NOT( TOOL_SET RB_TOOL_SET) ) 
				  			  ( NOT( TOOL_TYPE CLAMP) ) 
				  		) 
					   ;( NOT (CLAMP_WITH_RB_ON_TOOTH) )
					   ( NOT (WORKING_TOOTH_SEPARATED) )
				)
				
				( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C14 )  ;clamp for molar
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
				  			  
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C9 )  ;clamp for incisor
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
			    ;------------------------------------------------------
			    ; Incorrect case for MOLAR
				;------------------------------------------------------
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C9 )
				  			  ( TOOL_SET RB_TOOL_SET)
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C2 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
				;------------------------------------------------------
			    ; Incorrect case for Canine
				;------------------------------------------------------
				( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C9 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C14 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			    )
			    ;------------------------------------------------------
			    ; Incorrect case for Incisor
				;------------------------------------------------------
				( WHEN ( AND ( WORKING_TOOTH_TYPE INCISOR )  ( CLAMP_NO C2 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
			      ( WHEN ( AND ( WORKING_TOOTH_TYPE INCISOR )  ( CLAMP_NO C14 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(WORKING_TOOTH_SEPARATED))  ;; this is leak
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
			      ;--------------------------------------------------------
				  
	  	);effect
	);actions
	
	;;-------------------
	;; Action: OC drill_to_pulp_chamber
	;;-------------------
	(:action drill_to_pulp_chamber
		:parameters( ?tt - tooth_type_constants 
		             ?ds - drill_size 	
		             ?shape - drill_shape_constants	
		             ?drill_entry - tooth_side_constants
		             ?drill_direction - tooth_side_constants
		           )
		:precondition()
  		:effect ( AND
	      		  ;;----------------------------------
	      		  ;; Compare drill depth with pc floor
	      		  ;;----------------------------------
				  
				  ;;----------------------------------
				  ;; CAse: invalid tool, the effect is not drilled
				  ;;----------------------------------
				  ( WHEN ( AND ( NOT (TOOL_TYPE ROUND_DIAMOND_BUR ) )  
                               ( NOT (TOOL_TYPE STEEL_ROUND_BUR ) ) )
				        ( NOT( PC_FLOOR_VISIBLE ) )
				  )
				  
				 
				  
				  ;(TOOL_TYPE ROUND_DIAMOND_BUR STEEL_ROUND_BUR SAFE_TIPPED_TAPER_BUR)
				  ;-----------------------------------------------------------------------
				  
				  (WHEN  (AND ( > (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
                               (TOOL_TYPE ROUND_DIAMOND_BUR ) )
				  		( AND 
			 				( PC_FLOOR_BLEEDING )				;; pc_floor is bleeding
			 				(NOT(PC_FLOOR_VISIBLE))
				  		) 
				  )

				  (WHEN  (AND ( > (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
                               (TOOL_TYPE STEEL_ROUND_BUR ) )
				  		( AND 
			 				( PC_FLOOR_BLEEDING )				;; pc_floor is bleeding
			 				(NOT(PC_FLOOR_VISIBLE))
				  		) 
				  )

				  ;------------------------------------------------------------------------
				  
				  (WHEN (AND ( = (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
							  (TOOL_TYPE ROUND_DIAMOND_BUR ) )
				  		( AND 
			 				( PC_FLOOR_BLEEDING )		
			 				( NOT(PC_FLOOR_VISIBLE ))
				  		) 
				  )

				  (WHEN (AND ( = (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
							  (TOOL_TYPE STEEL_ROUND_BUR ) )
				  		( AND 
			 				( PC_FLOOR_BLEEDING )		
			 				( NOT(PC_FLOOR_VISIBLE ))
				  		) 
				  )
				  ;------------------------------------------------------------------------

				  (WHEN ( AND 
				  			( < (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION) )
				  			( > (DRILL_DEPTH_FUNCTION) (ENAMEL_DENTINE_THICKNESS_FUNCTION) )
							(TOOL_TYPE ROUND_DIAMOND_BUR )
					    ) 
				  		( AND 
			 				(PC_FLOOR_VISIBLE)
			 				(DENTINE_SHELF_REMAIN)
				  		) 
				  )

				  (WHEN ( AND 
				  			( < (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION) )
				  			( > (DRILL_DEPTH_FUNCTION) (ENAMEL_DENTINE_THICKNESS_FUNCTION) )
							( TOOL_TYPE STEEL_ROUND_BUR )
					    ) 
				  		( AND 
			 				( PC_FLOOR_VISIBLE )
			 				( DENTINE_SHELF_REMAIN )
				  		) 
				  )
				  ;------------------------------------------------------------------------
				  (WHEN (AND ( < (DRILL_DEPTH_FUNCTION) (ENAMEL_DENTINE_THICKNESS_FUNCTION) )
                              ( TOOL_TYPE ROUND_DIAMOND_BUR ) )
				  		;( AND
			 				(NOT(PC_FLOOR_VISIBLE))
				  		;)
				  )

				  (WHEN (AND ( < (DRILL_DEPTH_FUNCTION) (ENAMEL_DENTINE_THICKNESS_FUNCTION) )
                              ( TOOL_TYPE STEEL_ROUND_BUR ) )
				  		;( AND
			 				(NOT(PC_FLOOR_VISIBLE))
				  		;)
				  )
				  ;------------------------------------------------------------------------

				  ;;----------------------------------------
	      		  ;; Compare drill entry and drill direction
	      		  ;;----------------------------------------
				  ;;ANSWER 
				  ;; MOLAR,    ENTRY = OCCLUSAL, DIRECTION=APICAL
				  ;; PREMOLAR, ENTRY = OCCLUSAL, DIRECTION=APICAL
				  ;; CANINE,   ENTRY = OCCLUSAL, DIRECTION=APICAL
				  ;; ANTERIOR, ENTRY = LINGUAL, DIRECTION=APICAL
				  
				  
				  ; THIS IS FINE
				  (WHEN ( AND
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(DRILL_ENTRY OCCLUSAL)
				  			(DRILL_DIRECTION APICAL)
						    ( TOOL_TYPE ROUND_DIAMOND_BUR )
				  		)
				  		(NOT(STRUCTURE_LEDGE))
				  )

				(WHEN ( AND
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(DRILL_ENTRY OCCLUSAL)
				  			(DRILL_DIRECTION APICAL)
						    ( TOOL_TYPE STEEL_ROUND_BUR )
				  		)
				  		(NOT(STRUCTURE_LEDGE))
				  )
				  ;;-------------------------------------------------
				  ;; THIS IS NOT CORRECT
				  (WHEN ( AND
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(NOT(DRILL_ENTRY OCCLUSAL))
				  			(NOT(DRILL_DIRECTION APICAL))
							(TOOL_TYPE ROUND_DIAMOND_BUR)
				  		)
				  		(STRUCTURE_LEDGE)
				  )

				  (WHEN ( AND
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(NOT(DRILL_ENTRY OCCLUSAL))
				  			(NOT(DRILL_DIRECTION APICAL))
							( TOOL_TYPE STEEL_ROUND_BUR )
				  		)
				  		(STRUCTURE_LEDGE)
				  )
				  ;;-------------------------------------------------
				  ; THIS IS FINE
				   (WHEN ( AND
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(DRILL_ENTRY LINGUAL)
				  			( DRILL_DIRECTION APICAL)
                            ( TOOL_TYPE ROUND_DIAMOND_BUR )
				  		)
				  		(NOT(STRUCTURE_LEDGE))
				  )
					(WHEN ( AND
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(DRILL_ENTRY LINGUAL)
				  			( DRILL_DIRECTION APICAL)
                            ( TOOL_TYPE STEEL_ROUND_BUR )
				  		)
				  		(NOT(STRUCTURE_LEDGE))
				  )
				  ;;-------------------------------------------------
				  
				  ;; THIS IS NOT CORRECT
				  (WHEN ( AND
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(NOT(DRILL_ENTRY LINGUAL))
				  			(NOT(DRILL_DIRECTION APICAL))
							( TOOL_TYPE ROUND_DIAMOND_BUR )
				  		)
				  		(STRUCTURE_LEDGE)
				  )
				  (WHEN ( AND
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(NOT(DRILL_ENTRY LINGUAL))
				  			(NOT(DRILL_DIRECTION APICAL))
							( TOOL_TYPE STEEL_ROUND_BUR )
				  		)
				  		(STRUCTURE_LEDGE)
				  )
				  ;;-------------------------------------------------
				  
				  ;;----------------------------------
	      		  ;; Compare drill shape
	      		  ;;----------------------------------
	      		  ;(DRILL_SHAPE ?shape - drill_shape_constants)
				  (when (AND 
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(NOT(DRILL_SHAPE OVAL))
							( TOOL_TYPE ROUND_DIAMOND_BUR )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  (when (AND 
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(NOT(DRILL_SHAPE OVAL))
							( TOOL_TYPE STEEL_ROUND_BUR )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  ;;-------------------------------------------------
				  
				  (when (AND 
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(WORKING_TOOTH_JAW MANDIBULAR)	
				  			(NOT(DRILL_SHAPE UPSIDE_DOWN_TRIANGLE) )
							( TOOL_TYPE ROUND_DIAMOND_BUR )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  (when (AND 
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(WORKING_TOOTH_JAW MANDIBULAR)	
				  			(NOT(DRILL_SHAPE UPSIDE_DOWN_TRIANGLE) )
							( TOOL_TYPE STEEL_ROUND_BUR )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  ;;-------------------------------------------------
				  
				  (when (AND 
				  			( WORKING_TOOTH_TYPE ANTERIOR )
				  			( WORKING_TOOTH_JAW MAXILLARY )	
				  			(NOT( DRILL_SHAPE TRIANGLE ) )
							( TOOL_TYPE ROUND_DIAMOND_BUR )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  (when (AND 
				  			( WORKING_TOOTH_TYPE ANTERIOR )
				  			( WORKING_TOOTH_JAW MAXILLARY )	
				  			(NOT( DRILL_SHAPE TRIANGLE ) )
							( TOOL_TYPE STEEL_ROUND_BUR )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
					
				  ;;-------------------------------------------------
				  
	      		  ;;----------------------------------
	      		  ;; Compare drill size with pc width
	      		  ;;----------------------------------
	      		  ;Test function without parameters
	      		  (when (AND (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) )
							( TOOL_TYPE ROUND_DIAMOND_BUR ) )
							( NOT ( STRUCTURE_OVERCUT ) ) 
				  )

				  (when (AND (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) )
							( TOOL_TYPE STEEL_ROUND_BUR ) )
							( NOT ( STRUCTURE_OVERCUT ) ) 
				  )
				  ;;-------------------------------------------------
			      
			      ;test condition that does not reach the goal		
		      	  (when (AND (>= (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) 
							 ( TOOL_TYPE ROUND_DIAMOND_BUR ) )
		      		    ( STRUCTURE_OVERCUT ) 
				  )

				  (when (AND (>= (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) 
							 ( TOOL_TYPE STEEL_ROUND_BUR ) )
		      		    ( STRUCTURE_OVERCUT ) 
				  )
		      		
			      ;;----------------------------------
	      		  ;; Compare with 
	      		  ;;----------------------------------		
  				  ;(NOT (WT_FRACTURE ?wt) )
  				  ;a = t, b = t //SUCCESS CASE
  				  (WHEN ( AND (WT_STRONG) (WT_ASEPTIC)
  				  			  (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) ;COMMENT TO TEST COMPLEX CASE ;Test this predicate in goal, if this is found, SKIP
  				  			  (WORKING_TOOTH_TYPE PREMOLAR)
				  			  (DRILL_ENTRY OCCLUSAL)
				  			  (DRILL_DIRECTION APICAL)
				  			  (DRILL_SHAPE OVAL)
							  ( TOOL_TYPE ROUND_DIAMOND_BUR )
  				  			) 
						( AND (PC_FLOOR_VISIBLE)
							  (PC_ASEPTIC) 
							  (DENTINE_SHELF_REMAIN)
							  (NOT (WT_FRACTURE) )
							  (NOT (STRUCTURE_LEDGE))
							  (NOT (STRUCTURE_OVERCUT))
							  ;(NOT (STRUCTURE_ENOUGH))
						)
				  )

				  (WHEN ( AND (WT_STRONG) (WT_ASEPTIC)
  				  			  (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) ;COMMENT TO TEST COMPLEX CASE ;Test this predicate in goal, if this is found, SKIP
  				  			  (WORKING_TOOTH_TYPE PREMOLAR)
				  			  (DRILL_ENTRY OCCLUSAL)
				  			  (DRILL_DIRECTION APICAL)
				  			  (DRILL_SHAPE OVAL)
							  ( TOOL_TYPE STEEL_ROUND_BUR )
  				  			) 
						( AND (PC_FLOOR_VISIBLE)
							  (PC_ASEPTIC) 
							  (DENTINE_SHELF_REMAIN)
							  (NOT (WT_FRACTURE) )
							  (NOT (STRUCTURE_LEDGE))
							  (NOT (STRUCTURE_OVERCUT))
							  ;(NOT (STRUCTURE_ENOUGH))
						)
				  )
				  ;;-------------------------------------------------
				  
				  ;;missing correct conditions for molar and canine
				  (WHEN ( AND (WT_STRONG) (WT_ASEPTIC)
  				  			  (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) ;COMMENT TO TEST COMPLEX CASE ;Test this predicate in goal, if this is found, SKIP
  				  			  (WORKING_TOOTH_TYPE ANTERIOR)
				  			  (DRILL_ENTRY LINGUAL)
				  			  (DRILL_DIRECTION APICAL)
							  (TOOL_TYPE ROUND_DIAMOND_BUR)
  				  		) 
						( AND (PC_FLOOR_VISIBLE)
							  (PC_ASEPTIC) 
							  (DENTINE_SHELF_REMAIN)
							  (NOT (WT_FRACTURE) )
							  (NOT (STRUCTURE_LEDGE))
							  (NOT (STRUCTURE_OVERCUT) ) )
				  )

				  (WHEN ( AND (WT_STRONG) (WT_ASEPTIC)
  				  			  (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) ;COMMENT TO TEST COMPLEX CASE ;Test this predicate in goal, if this is found, SKIP
  				  			  (WORKING_TOOTH_TYPE ANTERIOR)
				  			  (DRILL_ENTRY LINGUAL)
				  			  (DRILL_DIRECTION APICAL)
							  ( TOOL_TYPE STEEL_ROUND_BUR )
  				  		) 
						( AND (PC_FLOOR_VISIBLE)
							  (PC_ASEPTIC) 
							  (DENTINE_SHELF_REMAIN)
							  (NOT (WT_FRACTURE) )
							  (NOT (STRUCTURE_LEDGE))
							  (NOT (STRUCTURE_OVERCUT) ) )
				  )
				  ;;-------------------------------------------------
				  
				  ;a = t, b = f
			      ;when ( AND (WT_STRONG) (NOT(WT_ASEPTIC) ) )
			       (when (AND ( NOT (WT_ASEPTIC) ) 
								( TOOL_TYPE ROUND_DIAMOND_BUR ) )
			      		 ( NOT (PC_ASEPTIC) ) )

					(when (AND ( NOT (WT_ASEPTIC) ) 
								( TOOL_TYPE STEEL_ROUND_BUR ) )
			      		 ( NOT (PC_ASEPTIC) ) )
				  ;;-------------------------------------------------

			      ;a = f, b = t	
			      ;when (and (NOT (WT_STRONG) ) (WT_ASEPTIC) ) 
			      (when (AND (NOT (WT_STRONG) )
							 ( TOOL_TYPE ROUND_DIAMOND_BUR ) )
			      		(PC_HAS_TISSUE_FALL) 
                  ) 	

				  (when (AND (NOT (WT_STRONG) )
							 ( TOOL_TYPE STEEL_ROUND_BUR ) )
			      		(PC_HAS_TISSUE_FALL) 
                  ) 
				  ;;-------------------------------------------------
			     
	  	);effect
	);action
	

); define

