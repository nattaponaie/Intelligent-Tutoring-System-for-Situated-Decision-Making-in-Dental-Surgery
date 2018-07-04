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
			
			;working_tooth_number_constants - constant
			
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
			bur_type_constants - constant		
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
		;21 - working_tooth_number_constants
		
		RCT TE WTE - procedure_constants
		ROUND_DIAMOND_BUR STEEL_ROUND_BUR SAFE_TIPPED_TAPER_BUR - bur_type_constants
		
		;;--------------------
		;; RB
		;;--------------------	
		UNDER COVER  - rubber_sheet_position_with_nose_constants
		C0 C9 C14 - clamp_no_constants
		RB_TOOL_SET - tool_set_constants
		CLAMP - tool_type_constants
		
		;;--------------------
		;; OC
		;;--------------------
		APICAL OCCLUSAL BUCCAL LINGUAL MESIAL DISTAL - tooth_side_constants
		OVAL TRIANGLE UPSIDE_DOWN_TRIANGLE - drill_shape_constants
		AMALGAM MTA - fill_material_constants
	)
			
	(:predicates
		
		( PROCEDURES ?procedure - procedure_constants )					;;procedure
		;( WORKING_TOOTH_NO ?wt_no - working_tooth_number_constants)		;; working tooth number
		( WORKING_TOOTH_SIDE ?wt_side - tooth_side_constants)			;; working tooth side (left or right)
		( WORKING_TOOTH_JAW ?wt_jaw - tooth_jaw_constants )	 
		( WORKING_TOOTH_TYPE ?wt_type - tooth_type_constants )			;;this is for compare with clamp for tooth type
		( PATIENT_HAS_UNDERLYING_DISEASE )						;; Patient is normal
		( TOOL_SET ?tool_set - tool_set_constants )
		( TOOL_TYPE ?tool_type - tool_type_constants ) 
		
		;;--------------------
		;; RB
		;;--------------------	
	    (STRUCTURE_ENOUGH)										;; the amount of coronal structure is enough
		(WT_STRONG)												;; working tooth is strong
		(CLAMP_WITH_RB_ON_TOOTH)								;; insert rubber dam with clamp success
		(CLAMP_RISK_TO_RELEASED)								;; clamp is risk to be released.
		(PATIENT_RISK_TO_FAINT)									;; Patient is risk to get fainted.
		(RB_POS_WITH_NOSE ?rb_pos - rubber_sheet_position_with_nose_constants)
		(RB_TOOLS_SET)
		(FOUR_POINT_CONTACT_ON_TOOTH)
		
		(CLAMP_NO ?clamp_no_constants - clamp_no_constants)
		;;-------------------------
		(RB_FORCEPS)
		(RB_FRAME)
		(RB_CLAMPS ?clamp_no - clamp_no_constants)
		(RB_SHEET)

		;;--------------------
		;; OC
		;;--------------------
		(PC_FLOOR_VISIBLE)  		;; pc_floor is visible
		(PC_FLOOR_REMOVED)			;; pc_floor is removed
		(CARIES_VISIBLE)			;; caries is visible
		(PC_FLOOR_PERFORATED )		;; pc_floor is perforated
		(PC_FLOOR_BLEEDING ) ;: isPerceivable	;; pc_floor is bleeding
		(FILLING_COMPATIBLE_WITH_TISSUE)   
		(WT_FRACTURE)				;; working tooth is fractured
		(WT_ASEPTIC)				;; working tooth is aseptic
		(PC_ASEPTIC)				;; pulp chamber is aseptic
		(PC_HAS_TISSUE_FALL)		;; pulp chamber has tissue fall into
		(PC_XRAY_VISIBLE)			;; pulp chamber on xray is visible
		(RESPONSE_CRY_OUT)			;; patient cries out (or quiet)
		(PC_ALL_ROOT_VISIBLE)		;; All root access on pulp chamber floor is visible
		(STRUCTURE_OVERCUT)  		;; the structure of the working tooth is OVERCUT (negation is UNDERCUT)
		(STRUCTURE_LEDGE)
		 
	    (DENTINE_SHELF_REMAIN)
		(DRILL_SHAPE_ACTION ?wtt - tooth_type_constants )  ;NOTE, parser does not allow to add this to function because it deals with string
		 
		;TOOL_TYPE
		(BUR_TYPE ?bt - bur_type_constants)
		(DRILL_ENTRY ?entry - tooth_side_constants)
		(DRILL_DIRECTION ?direction - tooth_side_constants)
		(DRILL_SHAPE ?shape - drill_shape_constants)
		 
		;Filling materials
		(FILLING_MATERIAL ?fm - fill_material_constants)
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
		:context( NOT (PC_FLOOR_BLEEDING ) )
		:implies ( NOT (PC_FLOOR_PERFORATED) )
	)
	
	(:functions
		
		;---------------------------------------------------------------------------
		;NOTE: the pair of expressions to compare must be declared here in function.
		;----------------------------------------------------------- 
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
				  ( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C0 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
							  ;( NOT (STRUCTURE_ENOUGH ) )
							  ;( CARIES_VISIBLE )
	      		  		)
			      )

				  ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C14 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ( CLAMP_WITH_RB_ON_TOOTH)
	      		  			  ( FOUR_POINT_CONTACT_ON_TOOTH)
	      		  		)
			      )
			      
			      ( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C0 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  		)
			      )
			      
			      ( WHEN ( AND ( WORKING_TOOTH_TYPE INCISOR )  ( CLAMP_NO C9 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
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
					   ( NOT (CLAMP_WITH_RB_ON_TOOTH) )
				)
				
				( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C14 )  ;clamp for molar
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
				  			  
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  		)
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C9 )  ;clamp for incisor
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		(NOT(CLAMP_WITH_RB_ON_TOOTH))
			    )
			    
			    ;------------------------------------------------------
			    ; Incorrect case for MOLAR
				;------------------------------------------------------
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C9 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		(NOT(CLAMP_WITH_RB_ON_TOOTH))
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C0 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		(NOT(CLAMP_WITH_RB_ON_TOOTH))
			    )
			    
				;------------------------------------------------------
			    ; Incorrect case for Canine
				;------------------------------------------------------
				( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C9 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		(NOT(CLAMP_WITH_RB_ON_TOOTH))
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C14 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  		)
			    )
			    ;------------------------------------------------------
			    ; Incorrect case for Incisor
				;------------------------------------------------------
				( WHEN ( AND ( WORKING_TOOTH_TYPE INCISOR )  ( CLAMP_NO C0 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  		)
			      )
			      ( WHEN ( AND ( WORKING_TOOTH_TYPE INCISOR )  ( CLAMP_NO C14 )
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
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
				  (WHEN ( > (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
				  		( AND 
			 				( PC_FLOOR_BLEEDING )					;; pc_floor is bleeding
			 				(NOT(PC_FLOOR_VISIBLE))
				  		) 
				  )
				  
				  (WHEN ( = (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
				  		( AND 
			 				( PC_FLOOR_BLEEDING )		
			 				( NOT(PC_FLOOR_VISIBLE ))
				  		) 
				  )
				  
				  (WHEN ( AND 
				  			( < (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION) )
				  			( > (DRILL_DEPTH_FUNCTION) (ENAMEL_DENTINE_THICKNESS_FUNCTION) )
					    ) 
				  		( AND 
			 				(PC_FLOOR_VISIBLE)
			 				(DENTINE_SHELF_REMAIN)
				  		) 
				  )
				  
				  (WHEN ( < (DRILL_DEPTH_FUNCTION) (ENAMEL_DENTINE_THICKNESS_FUNCTION) )
				  		( AND 
			 				(NOT(PC_FLOOR_VISIBLE))
				  		) 
				  )
				  
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
				  		)
				  		(NOT(STRUCTURE_LEDGE))
				  )
				  
				  ;; THIS IS NOT CORRECT
				  (WHEN ( AND
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(NOT(DRILL_ENTRY OCCLUSAL))
				  			(NOT(DRILL_DIRECTION APICAL))
				  		)
				  		(STRUCTURE_LEDGE)
				  )
				  
				  ; THIS IS FINE
				   (WHEN ( AND
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(DRILL_ENTRY LINGUAL)
				  			(DRILL_DIRECTION APICAL)
				  		)
				  		(NOT(STRUCTURE_LEDGE))
				  )
				  
				  ;; THIS IS NOT CORRECT
				  (WHEN ( AND
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(NOT(DRILL_ENTRY LINGUAL))
				  			(NOT(DRILL_DIRECTION APICAL))
				  		)
				  		(STRUCTURE_LEDGE)
				  )
				  
				  ;;----------------------------------
	      		  ;; Compare drill shape
	      		  ;;----------------------------------
	      		  ;(DRILL_SHAPE ?shape - drill_shape_constants)
				  (when (AND 
				  			(WORKING_TOOTH_TYPE PREMOLAR)
				  			(NOT(DRILL_SHAPE OVAL))
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  
				  (when (AND 
				  			(WORKING_TOOTH_TYPE ANTERIOR)
				  			(WORKING_TOOTH_JAW MANDIBULAR)	
				  			(NOT(DRILL_SHAPE UPSIDE_DOWN_TRIANGLE) )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  
				  (when (AND 
				  			( WORKING_TOOTH_TYPE ANTERIOR )
				  			( WORKING_TOOTH_JAW MAXILLARY )	
				  			(NOT( DRILL_SHAPE TRIANGLE ) )
				  		) 
				  		(STRUCTURE_OVERCUT)   ;; this is fine
				  )
				  
	      		  ;;----------------------------------
	      		  ;; Compare drill size with pc width
	      		  ;;----------------------------------
	      		  ;Test function without parameters
	      		  (when (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) )
			      		( NOT ( STRUCTURE_OVERCUT ) ) )
			      
			      ;test condition that does not reach the goal		
		      	  (when (>= (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) 
		      		    ( STRUCTURE_OVERCUT ) )
		      		
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
				  
				  ;;missing correct conditions for molar and canine
				  (WHEN ( AND (WT_STRONG) (WT_ASEPTIC)
  				  			  (< (DRILL_SIZE_FUNCTION) (PC_WIDTH_SIZE_FUNCTION) ) ;COMMENT TO TEST COMPLEX CASE ;Test this predicate in goal, if this is found, SKIP
  				  			  (WORKING_TOOTH_TYPE ANTERIOR)
				  			  (DRILL_ENTRY LINGUAL)
				  			  (DRILL_DIRECTION APICAL)
  				  		) 
						( AND (PC_FLOOR_VISIBLE)
							  (PC_ASEPTIC) 
							  (DENTINE_SHELF_REMAIN)
							  (NOT (WT_FRACTURE) )
							  (NOT (STRUCTURE_LEDGE))
							  (NOT (STRUCTURE_OVERCUT) ) )
				  )
				  
				  ;a = t, b = f
			      ;when ( AND (WT_STRONG) (NOT(WT_ASEPTIC) ) )
			       (when ( NOT (WT_ASEPTIC) ) 
			      		 ( NOT (PC_ASEPTIC) ) )
			      		
			      ;a = f, b = t	
			      ;when (and (NOT (WT_STRONG) ) (WT_ASEPTIC) ) 
			      (when (NOT (WT_STRONG) )
			      		(PC_HAS_TISSUE_FALL) 
                    ) 	
			     
	  	);effect
	);action
	

); define

