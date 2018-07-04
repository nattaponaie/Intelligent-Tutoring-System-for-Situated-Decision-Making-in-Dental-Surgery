(define (problem pb1)
   	(:domain rb)
   	
   	(:objects
              )
              
	(:init 
	
        (PEN_POSITION ON_TABLE)
        (HAND EMPTY)
        (TABLE OCCUPIED)
        (HAND_AVAILABLE)
        ( = (A_FUNCTION) 20 )
        ( = (B_FUNCTION) 30 )
        ( = (F_FUNCTION) 5 )
        
        
        
        (TEST_FUNCTION)
	)   
	
	;;NOTE: The number of goals should be exactly the same as in the list of a node.
	(:goal (AND 
                (PEN_POSITION ON_CHAIR)
                    (HAND_AVAILABLE) )
	);end-goal
)