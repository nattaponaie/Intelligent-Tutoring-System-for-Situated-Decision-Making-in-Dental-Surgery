(define (problem pb1)
   	(:domain rb)
   	
   	(:objects
              )
              
    ;--------------------------
    ; NOTE: initialize value for calculation, 
    ;       1. = sign is for numeric only. 
    ;		2. for string constant like PREMOLAR, 
    ;		   Test1: use = sign, parser does not allowed.
    ;		   Test2: use predicate, ????
    ;--------------------------
	(:init 
	
		( NOT ( PATIENT_HAS_UNDERLYING_DISEASE ) )		;; Patient is normal
		( WORKING_TOOTH_TYPE PREMOLAR )		;;this is for compare with clamp for tooth type
		;( WORKING_TOOTH_NO 21 )	;; working tooth number
		( WORKING_TOOTH_SIDE LEFT )	;; working tooth side (left or right)
		( WORKING_TOOTH_JAW MANDIBULAR )	
		( PROCEDURES RCT )
		
		;;----------------------
		;; RB init
		;;----------------------
		( STRUCTURE_ENOUGH )
		 
		( NOT (PC_FLOOR_VISIBLE ) )
		( NOT ( CARIES_VISIBLE ) )
		
		;;----------------------
		 ;; OC init
		 ;;----------------------
		 ;( = (PC_WIDTH_SIZE_FUNCTION) 20)		;NOTE: drill size = 14			; this is normal situation
		 ( = (PC_WIDTH_SIZE_FUNCTION) 10)		;NOTE: drill size = 14 			; this is error
		 ( = (PC_FLOOR_DEPTH_FUNCTION) 8)		
		 ( = (ENAMEL_DENTINE_THICKNESS_FUNCTION) 5)
		 ( NOT (PC_FLOOR_BLEEDING) )
	)   
	
	;;NOTE: The number of goals should be exactly the same as in the list of a node.
	(:goal (AND (PC_FLOOR_VISIBLE)
					(PC_ASEPTIC)
					(NOT (WT_FRACTURE) )
					(NOT (STRUCTURE_OVERCUT) )
					(NOT (STRUCTURE_LEDGE) )
					(NOT (DENTINE_SHELF_REMAIN) )
			   )
	);end-goal
)