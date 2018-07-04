(define (domain rb)
	(:requirements :strips :typing :conditional-effects :universal-preconditions :negative-preconditions :equality :fluents
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
			diagnosis_constants - constant
            underlying_disease_constants - constant

			;;--------------------
			;; LA
			;;--------------------
			anesthesia_type_constants - constant
			landmark_constants - constant
			reference_tooth_no_constants - constant
			target_check_numb_constants - constant
			cartridge_color_constants - constant
			nerve_constants - constant
			mandibular_anatomy_constants - constant
			needle_length_constants - constant
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
		PREMOLAR MOLAR CANINE ANTERIOR - tooth_type_constants
		MANDIBULAR MAXILLARY - tooth_jaw_constants
		LEFT RIGHT - tooth_side_constants

		WT21 WT24 WT26 WT28 - working_tooth_number_constants  ;NOTE: the working tooth is 21
		;NOTE: CHANGE TOOTH NUMBERING SYSTEM
		;WT21 --> WT34,
		;WT24 --> WT31,
		;WT26 --> WT42,
		;WT28 --> WT44
		WT34 WT31 WT42 WT44 - working_tooth_number_constants  ;NOTE: the working tooth is 21

		RCT TE WTE - procedure_constants
		;ROUND_DIAMOND_BUR STEEL_ROUND_BUR SAFE_TIPPED_TAPER_BUR - bur_type_constants

        ;--- Nui add for LA ---
		PULPITIS PULP_NECROSIS - diagnosis_constants
        HEART_DISEASE - underlying_disease_constants

        ID_NERVE LINGUAL_NERVE
        PTERYGOID_MUSCLE
        CORONOID_NOTCH ARTERY
        PTERYGOMANDIBULAR_RAPHAE - mandibular_anatomy_constants

		;;--------------------
		;; LA CONSTANTS
		;;--------------------
		LIDOCAINE MEPIVACAINE - anesthesia_type_constants

		AT_CORONOID_NOTCH
		AT_PTERYGOMANDIBULAR_RAPHAE
		BETWEEN_CORONOID_NOTCH_AND_PTERYGOMANDIBULAR_RAPHAE - landmark_constants

		;RT24 RT26 RT28 - reference_tooth_no_constants
		ID_NERVE_BLOCK LINGUAL_NERVE_BLOCK - nerve_constants
		LOWER_LEFT_LIP LOWER_RIGHT_LIP - target_check_numb_constants

		RED CLEAR - cartridge_color_constants

		;;--------------------
		;; RB CONSTANTS
		;;--------------------
		UNDER COVER  - rubber_sheet_position_with_nose_constants
		C2 C9 C14 - clamp_no_constants
		RB_TOOL_SET - tool_set_constants
		CLAMP ROUND_DIAMOND_BUR STEEL_ROUND_BUR SAFE_TIPPED_TAPER_BUR - tool_type_constants

		;;--------------------
		;; OC CONSTANTS
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
		( TOOL_SET ?tool_set - tool_set_constants )             :isperceivable
		( TOOL_TYPE ?tool_type - tool_type_constants )          :isperceivable

        ;--- Nui add for LA ---
		( DIAGNOSIS ?diagnosis - diagnosis_constants )			:isperceivable
		( PULP_VITAL )
		;;--------------------
		;; LA
		;;--------------------
		(CARTRIDGE ?ct_color - cartridge_color_constants )		            :isperceivable
	    (LA_SOLUTION_TYPE  ?sol_type - anesthesia_type_constants)	        :isperceivable
	    (LA_SELECTED_SOLUTION_TYPE  ?sol_type - anesthesia_type_constants)	:isperceivable
	    (LA_NEEDLE_PIERCED)     									        :isperceivable
	    (LANDMARK_LOCATION ?landmark - landmark_constants )                 :isperceivable
	    ;(REF_POINT_TOOTH ?tooth_number - reference_tooth_no_constants )    :isperceivable
	    (REF_POINT_TOOTH ?ref_wt_no - working_tooth_number_constants )      :isperceivable
	    (SENSITIVE_TO_EPINEPHRINE)

	    (TARGET_NERVE_BLOCK ?target_nerve - nerve_constants )
	    (ANESTHETIC_IN)  										            :isperceivable
	    (RISK_ANESTHESIA_NOT_LONG_LAST)
	    (TARGET_CHECK_NUMB ?target_numb - target_check_numb_constants)      :isperceivable
	    (LIP_NUMB_LOCATION ?lib_numb_side - target_check_numb_constants)   											            :isperceivable
	    (TIP_NEEDLE_AT ?mandibular_target_anatomy - mandibular_anatomy_constants ) :isperceivable
	    (TARGET_ANESTHESIA_AREA_REACH)

		(ANESTHESIA_LONG_LAST)          :isperceivable
		(PALPITATION)                   :isperceivable   ;heart shaking
		;(UNDERLYING_DISEASE ?underlying_disease_constants )         ;NUI add LA		;This error cannot be detected by "Prelim_Project" checker.
		(UNDERLYING_DISEASE ?underlying_disease - underlying_disease_constants ) :isperceivable		;Mes fix for UNDERLYING_DISEASE
		;(UNDERLYING_DISEASE)            :isperceivable
		(PATIENT_HEART_ATTACK ) 		:isperceivable

		(NEEDLE_NOT_TIPPED_AT_ARTERY)

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
		(CLAMP_WITH_RB_ON_TOOTH)

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
		;(ONEOF_A)
		;(ONEOF_B)
	)

	(:axiom
        	:vars ()
        	:context( AND ( DIAGNOSIS PULPITIS ) ( UNDERLYING_DISEASE HEART_DISEASE ) )
        	:implies( AND ( PALPITATION ) ( SENSITIVE_TO_EPINEPHRINE )  )
    )

    ;;--------------------------------
	;; AXIOMS LA
	;;--------------------------------
	(:axiom
    		:vars ()
    		:context( NOT ( UNDERLYING_DISEASE HEART_DISEASE ) )
    		:implies ( NOT (SENSITIVE_TO_EPINEPHRINE) )
    )

    (:axiom
        	:vars ()
        	:context(  UNDERLYING_DISEASE HEART_DISEASE )
        	:implies ( SENSITIVE_TO_EPINEPHRINE)
    )

    (:axiom
        	:vars ()
        	:context( DIAGNOSIS PULPITIS )
        	:implies( PULP_VITAL )
    )

    (:axiom
            :vars ()
            :context( NOT ( DIAGNOSIS PULPITIS ) )
            :implies( NOT( PULP_VITAL ) )
    )

    (:axiom
        :vars ()
        :context( CARTRIDGE CLEAR )
        :implies( NEEDLE_NOT_TIPPED_AT_ARTERY )
    )

    (:axiom
        :vars ()
        :context( CARTRIDGE RED )
        :implies( TIP_NEEDLE_AT ARTERY )
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
	;; Action: LA select_anesthesia_type
	;;-------------------
    (:action select_local_anesthesia_type
		:parameters()
		:precondition()
  		:effect (AND

  				  ;; ASSUMPTION: the anesthesia is set correctly and long last for the whole procedure.
  				  ;;--------------------------------------
  				  ;; Case 1: Sensitive to E, Mepivacaine, any amount
  				  ;;--------------------------------------
  				  ;; correct
	      		  (WHEN ( AND
	      		              ( SENSITIVE_TO_EPINEPHRINE )
	      		              ( LA_SOLUTION_TYPE MEPIVACAINE ) )  ;;Optional solution
	      		  		( AND
	      		  		     ( LA_SELECTED_SOLUTION_TYPE MEPIVACAINE )  ;; nothing happen //need to add effect as it is used in the next stage
	      		  			 ( NOT( ANESTHESIA_LONG_LAST ) )
	      		  		     ( NOT( PALPITATION ) ) )
	      		  		;; -------------------------------
	      		  		;; NOTE: effect "PALPITATION" can happen immediately or later with or without conditions.
	      		  		;; This needs to enhance.
	      		  		;; -------------------------------
			      )

			      ;;--------------------------------------
  				  ;; Case 2: Sensitive to E, Lidocaine and E (Epinephrine), amt <= 2
  				  ;;--------------------------------------
			      ;This is correct
			      (WHEN ( AND
	      		              ( SENSITIVE_TO_EPINEPHRINE )
	      		              ( LA_SOLUTION_TYPE LIDOCAINE )
	      		              ( < (CARTRIDGE_AMT_FUNCTION) 3 ) )
	      		  		( AND
	      		  		     ( LA_SELECTED_SOLUTION_TYPE LIDOCAINE )  ;; nothing happen //need to add effect as it is used in the next stage
	      		  			 ( ANESTHESIA_LONG_LAST )
	      		  		     ( NOT( PALPITATION ) ) )
			      )

			      ;not correct
			      (WHEN ( AND
	      		              ( SENSITIVE_TO_EPINEPHRINE )
	      		              ( LA_SOLUTION_TYPE LIDOCAINE )
	      		              ( > (CARTRIDGE_AMT_FUNCTION) 3 ) )
	      		  		( AND
	      		  		     ( LA_SELECTED_SOLUTION_TYPE LIDOCAINE )  ;; nothing happen //need to add effect as it is used in the next stage
	      		  			 ( ANESTHESIA_LONG_LAST )
	      		  		     ( PALPITATION ) )
			      )

			      ;not correct
			      (WHEN ( AND
	      		              ( SENSITIVE_TO_EPINEPHRINE )
	      		              ( LA_SOLUTION_TYPE LIDOCAINE )
	      		              ( = (CARTRIDGE_AMT_FUNCTION) 3 ) )
	      		  		( AND
	      		  		     ( LA_SELECTED_SOLUTION_TYPE LIDOCAINE )  ;; nothing happen //need to add effect as it is used in the next stage
	      		  			 ( ANESTHESIA_LONG_LAST )
	      		  		     ( PALPITATION ) )
			      )

			      ;;--------------------------------------
  				  ;; Case 3: not Sensitive to E, Lidocaine and E (Epinephrine), amt <= 7
  				  ;;--------------------------------------
			      ;; correct
			      ( WHEN ( AND ( NOT ( SENSITIVE_TO_EPINEPHRINE ) )
			                  ( LA_SOLUTION_TYPE LIDOCAINE )
			                  ( < (CARTRIDGE_AMT_FUNCTION) 8 ) )  ;;Optional solution
	      		  		( AND ( LA_SELECTED_SOLUTION_TYPE LIDOCAINE )
	      		  			  ( ANESTHESIA_LONG_LAST )
	      		  			  ( NOT( PALPITATION ) ) )
			      )

			      ;; not correct
			      ( WHEN ( AND ( NOT ( SENSITIVE_TO_EPINEPHRINE ) )
			                  ( LA_SOLUTION_TYPE LIDOCAINE )
			                  ( = (CARTRIDGE_AMT_FUNCTION) 8 ) )
	      		  		( AND ( LA_SELECTED_SOLUTION_TYPE LIDOCAINE )
	      		  			  ( ANESTHESIA_LONG_LAST )
	      		  			  ( PALPITATION ) )  ;; nothing happen
			      )

			      ;; not correct
			      ( WHEN ( AND ( NOT ( SENSITIVE_TO_EPINEPHRINE ) )
			                  ( LA_SOLUTION_TYPE LIDOCAINE )
			                  ( > (CARTRIDGE_AMT_FUNCTION) 8 ) )  ;;Optional solution
	      		  		( AND ( LA_SELECTED_SOLUTION_TYPE LIDOCAINE )
	      		  			  ( ANESTHESIA_LONG_LAST )
	      		  			  ( PALPITATION ) )  ;; nothing happen
			      )

			      ;;--------------------------------------
  				  ;; Case 4: not Sensitive to E, Mepivacain, any amount
  				  ;;--------------------------------------
			      ;; incorrect
			      (WHEN ( AND ( SENSITIVE_TO_EPINEPHRINE )
			                  ( LA_SOLUTION_TYPE MEPIVACAINE ) )  ;;Optional solution
	      		  		(AND
	      		  		    ( NOT ( PALPITATION ) )   ;; this is able to enhance.
	      		  		    ( NOT (ANESTHESIA_LONG_LAST) )
	      		  		    ( LA_SELECTED_SOLUTION_TYPE MEPIVACAINE ) )
			      )
	  	);effect
	);action select_anesthesia_type

	;;-------------------
    ;; Action: LA pierce_anesthesia_needle
    ;;-------------------
    (:action pierce_anesthesia_needle
        :parameters( ?landmark - landmark_constants
                     ?needle_length - needle_length_constants
                     ?ref_tooth - reference_tooth_no_constants
                     ?remain_p_depth - remain_pierce_depth )
        :precondition()
        :effect ( AND
                    ( WHEN (LANDMARK_LOCATION AT_CORONOID_NOTCH) ;; at coronoid notch
                    ( TIP_NEEDLE_AT CORONOID_NOTCH )
                    );;end-when

                    ( WHEN ( LANDMARK_LOCATION AT_PTERYGOMANDIBULAR_RAPHAE ) ;;at pterygomandibular raphae
                      ( TIP_NEEDLE_AT PTERYGOMANDIBULAR_RAPHAE )
                    );;end-when

                    ;;correct
                    ( WHEN (AND ( LANDMARK_LOCATION BETWEEN_CORONOID_NOTCH_AND_PTERYGOMANDIBULAR_RAPHAE )  ;; between coronoid notch and pterygomandibular raphae
                                ( REF_POINT_TOOTH WT44  ) )               ;28 ;;correct answer
                           (AND (LA_NEEDLE_PIERCED)
                                (TARGET_ANESTHESIA_AREA_REACH)
                                ( TIP_NEEDLE_AT ID_NERVE )
                           )
                    );;end-when

                    ;;incorrect
                    ( WHEN (AND ( LANDMARK_LOCATION BETWEEN_CORONOID_NOTCH_AND_PTERYGOMANDIBULAR_RAPHAE )  ;; between coronoid notch and pterygomandibular raphae
                                ( REF_POINT_TOOTH WT31 )  ;24
                           )
                           (AND ( LA_NEEDLE_PIERCED )
                                ( TIP_NEEDLE_AT LINGUAL_NERVE ) ) ;; INCORRECT_DIRECTION
                    );;end-when

                    ;;incorrect
                    ( WHEN (AND ( LANDMARK_LOCATION BETWEEN_CORONOID_NOTCH_AND_PTERYGOMANDIBULAR_RAPHAE )  ;; between coronoid notch and pterygomandibular raphae
                                ( REF_POINT_TOOTH WT42 )  ;26
                                                          ;Tooth number is not used for calculation.
                                                          ;So it is predicate, not function
                           )
                           (AND ( LA_NEEDLE_PIERCED )
                                ( TIP_NEEDLE_AT PTERYGOID_MUSCLE ) ;; INCORRECT_DIRECTION
                           )
                    );;end-when

                    ( WHEN (  = (NEEDLE_LENGTH_FUNCTION) 21 )
                           ( AND ( = (NEEDLE_LENGTH_FUNCTION)  21 )
                                 ( NOT ( TARGET_ANESTHESIA_AREA_REACH ) )
                                ;;The needle is too short, it is risk to numb at lingual,
                                ;;not the ID nerve block
                           )
                    );end-when


                    ;; TODO: Comment for further develop in Projection Engine
                    ( WHEN ( = (NEEDLE_LENGTH_FUNCTION) 30 )
                           ( assign ( PIERCE_DEPTH_FUNCTION )
                               ( - (NEEDLE_LENGTH_FUNCTION) ( REMAIN_DEPTH_FUNCTION) ) )
                    )

                    ;;For now just think of PIERCE_DEPTH_FUNCTION as depth
                    ;( WHEN ( = (PIERCE_DEPTH_FUNCTION) 30 )
                    ;        ()
                    ;)
        );;end-effect
    );; end-action

    ;;-------------------
	;; Action: LA aspirate
	;;-------------------
	(:action aspirate
	    :parameters()
	    :precondition()
  		:effect ( ONEOF  ( (0.2) ( CARTRIDGE RED ) )
  				         ( (0.8) ( CARTRIDGE CLEAR ) )
  				);;effect
	);;end-action aspirate

    ;;-------------------
	;; Action: LA retract
	;;-------------------
	(:action retract
	    :parameters(?r_depth - retract_depth)
	    :precondition()
  		:effect ( AND
  					;;get r_depth
  					;;( = (RETRACT_DEPTH_FUNCTION) (?r_depth) )

  					;;adjust p_depth
  					;;( = (PIERCE_DEPTH_FUNCTION) ( (PIERCE_DEPTH_FUNCTION) - (RETRACT_DEPTH_FUNCTION) ) )

                    ;; Automatic adjust 1 mm by default
                    ( assign (PIERCE_DEPTH_FUNCTION) 
                    	( - (PIERCE_DEPTH_FUNCTION) 1 ) 
                    )
                    
  					( CARTRIDGE CLEAR )
  		);;end-effect
	);;end-action retract

    ;;-------------------
	;; Action: LA inject_anesthesia_and_draw
	;;-------------------
	(:action inject_anesthesia_and_draw
	    :parameters( ?inject_amt - inject_amount )
	    :precondition()
  		:effect ( AND
  		            ;( = ( INJECT_AMT_FUNCTION ) inject_amt )
  		            ( NOT( LA_NEEDLE_PIERCED ) )
  		            ( ANESTHETIC_IN )
  		            ( TARGET_NERVE_BLOCK ID_NERVE_BLOCK )

  		            ;; incorrect
  		            ( WHEN ( CARTRIDGE RED )
  		            	   ( PATIENT_HEART_ATTACK )
  		            )

  		            ;;correct
  					(WHEN ( AND
  							( CARTRIDGE CLEAR )
  							( TARGET_NERVE_BLOCK ID_NERVE_BLOCK )
  							( = (INJECT_AMT_FUNCTION) 1.0 ) )
  						    ( NOT ( RISK_ANESTHESIA_NOT_LONG_LAST ) )
  					);;

  					;;incorrect
  					(WHEN ( AND
  							( CARTRIDGE CLEAR )
  							( TARGET_NERVE_BLOCK ID_NERVE_BLOCK )
  							( = (INJECT_AMT_FUNCTION) 0.8 ) )
  						    ( RISK_ANESTHESIA_NOT_LONG_LAST )
  					);;

  					;; ENHANCE THE INVALID SOLUTION TYPE
  					;; incorrect //COPY from SELECT_Anesthesia action
			      	(WHEN ( AND
			      	            ( SENSITIVE_TO_EPINEPHRINE )
			      				( LA_SOLUTION_TYPE LIDOCAINE ) )
	      		  		  ( PALPITATION )  		;; actually this effect could happen in the other steps (ref to enhance effect)
			      	)

			      	(WHEN ( AND
			      	            ( SENSITIVE_TO_EPINEPHRINE )
			      	            ( LA_SOLUTION_TYPE MEPIVACAINE ) )  ;;Optional solution
                          ;; the anesthesia may or may not long last
	      		  		  ( ONEOF ( (0.5) ( NOT( ANESTHESIA_LONG_LAST ) ) )
	      		  		          ( (0.5) ( ANESTHESIA_LONG_LAST ) )
	      		  		  )
			        )
  		);;effect
	);;end-action inject_anesthesia_and_draw

    ;;-------------------
	;; Action: LA check_numb
	;;-------------------
	(:action check_numb
	    :parameters(?tg - target_check_numb_constants )
	    :precondition()
  		:effect ( AND
  					( WHEN  (AND ( TARGET_CHECK_NUMB LOWER_LEFT_LIP )
  					             ( WORKING_TOOTH_SIDE LEFT )
  					             ( WORKING_TOOTH_JAW MANDIBULAR )
  					        )
  							( LIP_NUMB_LOCATION LOWER_LEFT_LIP)   ;;Lip is numb
   					);; end-when

   					( WHEN  (AND ( TARGET_CHECK_NUMB LOWER_RIGHT_LIP )
  					             ( WORKING_TOOTH_SIDE LEFT )
  					             ( WORKING_TOOTH_JAW MANDIBULAR )
  					        )
  							( NOT( LIP_NUMB_LOCATION LOWER_RIGHT_LIP ) )   ;;Lip is NOT NUMB
   					);; end-when

   					( WHEN  (AND ( TARGET_CHECK_NUMB LOWER_LEFT_LIP )
  					             ( WORKING_TOOTH_SIDE RIGHT )
  					             ( WORKING_TOOTH_JAW MANDIBULAR )
  					        )
  							( NOT( LIP_NUMB_LOCATION LOWER_LEFT_LIP) )   ;;Lip is NOT NUMB
   					);; end-when

   					( WHEN  (AND ( TARGET_CHECK_NUMB LOWER_RIGHT_LIP )
  					             ( WORKING_TOOTH_SIDE RIGHT )
  					             ( WORKING_TOOTH_JAW MANDIBULAR )
  					        )
  							( LIP_NUMB_LOCATION LOWER_RIGHT_LIP )   ;;Lip is numb
   					);; end-when
  		) ;;end-effect
	);; end-action check_numb

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
				  ( WHEN ( AND ( NOT( WORKING_TOOTH_SEPARATED ))
				               ( WORKING_TOOTH_TYPE PREMOLAR )
                                    ( CLAMP_NO C2 )
                                    ( TOOL_SET RB_TOOL_SET)
                                    ( TOOL_TYPE CLAMP)
				  	    )
	      		  		( AND (CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )

				  ( WHEN ( AND ( NOT(WORKING_TOOTH_SEPARATED))
				               ( WORKING_TOOTH_TYPE MOLAR )
				                ( CLAMP_NO C14 )
				  			  ( TOOL_SET RB_TOOL_SET)
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
			      
			      ( WHEN ( AND ( NOT(WORKING_TOOTH_SEPARATED))
			                    ( WORKING_TOOTH_TYPE CANINE )
			                    ( CLAMP_NO C2 )
				  			  ( TOOL_SET RB_TOOL_SET)
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (FOUR_POINT_CONTACT_ON_TOOTH)
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
			      
			      ( WHEN ( AND ( NOT(WORKING_TOOTH_SEPARATED))
			                     ( WORKING_TOOTH_TYPE ANTERIOR )
			                   ( CLAMP_NO C9 )
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
				
				( WHEN ( AND (NOT( TOOL_SET RB_TOOL_SET) )
				  			 ( NOT( TOOL_TYPE CLAMP) )
				  			 ( NOT(WORKING_TOOTH_SEPARATED))
				  		) 
					   ;( NOT (CLAMP_WITH_RB_ON_TOOTH) )
					   ( NOT (WORKING_TOOTH_SEPARATED) )
				)
				
				( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C14 )  ;clamp for molar
				             ( NOT(WORKING_TOOTH_SEPARATED))
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
				  			  
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE PREMOLAR )  ( CLAMP_NO C9 )  ;clamp for ANTERIOR
			                ( NOT(WORKING_TOOTH_SEPARATED))
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
			    ;------------------------------------------------------
			    ; Incorrect case for MOLAR
				;------------------------------------------------------
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C9 )
			                 ( NOT(WORKING_TOOTH_SEPARATED))
				  			  ( TOOL_SET RB_TOOL_SET)
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE MOLAR )  ( CLAMP_NO C2 )
			                   ( NOT(WORKING_TOOTH_SEPARATED))
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
				;------------------------------------------------------
			    ; Incorrect case for Canine
				;------------------------------------------------------
				( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C9 )
				            ( NOT(WORKING_TOOTH_SEPARATED))
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		;(NOT(CLAMP_WITH_RB_ON_TOOTH))
	      		  		(NOT(WORKING_TOOTH_SEPARATED))
			    )
			    
			    ( WHEN ( AND ( WORKING_TOOTH_TYPE CANINE )  ( CLAMP_NO C14 )
			                ( NOT(WORKING_TOOTH_SEPARATED))
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			    )
			    ;------------------------------------------------------
			    ; Incorrect case for ANTERIOR
				;------------------------------------------------------
				( WHEN ( AND ( WORKING_TOOTH_TYPE ANTERIOR )  ( CLAMP_NO C2 )
				            ( NOT(WORKING_TOOTH_SEPARATED))
				  			  ( TOOL_SET RB_TOOL_SET) 
				  			  ( TOOL_TYPE CLAMP) )
	      		  		( AND ;(CLAMP_WITH_RB_ON_TOOTH)
	      		  			  (NOT(FOUR_POINT_CONTACT_ON_TOOTH))  ;; this is leak
	      		  			  (WORKING_TOOTH_SEPARATED)
	      		  		)
			      )
			      ( WHEN ( AND ( WORKING_TOOTH_TYPE ANTERIOR )  ( CLAMP_NO C14 )
			                    ( NOT(WORKING_TOOTH_SEPARATED ) )
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
			 				( NOT( PC_FLOOR_VISIBLE ) )
				  		) 
				  )

				  ;------------------------------------------------------------------------
				  
				  (WHEN (AND ( = (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
							  (TOOL_TYPE ROUND_DIAMOND_BUR ) )
				  		( AND 
			 				( PC_FLOOR_BLEEDING )		
			 				( NOT( PC_FLOOR_VISIBLE ) )
				  		) 
				  )

				  (WHEN (AND ( = (PC_FLOOR_DEPTH_FUNCTION) (DRILL_DEPTH_FUNCTION)) 
							  (TOOL_TYPE STEEL_ROUND_BUR ) )
				  		( AND 
			 				( PC_FLOOR_BLEEDING )		
			 				( NOT( PC_FLOOR_VISIBLE ) )
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

