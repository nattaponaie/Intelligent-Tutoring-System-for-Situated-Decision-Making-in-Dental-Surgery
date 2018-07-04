(define (domain rb)
	(:requirements :strips :typing :conditional-effects :universal-preconditions :negative-preconditions :equality :fluents
	:fluents :domain-axioms)

	;-------
	;Types is perception nodes
	;-------
	(:types
			position_constants - constant
			status_constants - constant

	);end-types

	(:constants
		ON_HAND ON_TABLE ON_CHAIR - position_constants
        EMPTY OCCUPIED - status_constants
		
	)

	(:predicates

		( PEN_POSITION ?pp - position_constants )
        ( HAND ?h - status_constants )
        ( TABLE ?t - status_constants )
        ( HAND_AVAILABLE )
        
        (TEST_FUNCTION)
	)

	(:functions

		  ( A_FUNCTION )
		  ( B_FUNCTION )
		  ( C_FUNCTION )
      ( D_FUNCTION )
      ( E_FUNCTION )
      ( F_FUNCTION )
      ( G_FUNCTION )
      ( H_FUNCTION )
      ( I_FUNCTION )
      ( J_FUNCTION )
	)
	
    (:action grab_pen
		:parameters()
		:precondition()
  		:effect (AND
                    (PEN_POSITION ON_HAND)
                    (HAND OCCUPIED)
                    (TABLE EMPTY)
                    (NOT (HAND_AVAILABLE))

                    ( assign ( D_FUNCTION )
                          ( + (F_FUNCTION) (F_FUNCTION) )
                    ) ; This will be correct, if D_FUNCTION = 10

                    ; must be possible to use plus
                    ;;( WHEN (TEST_FUNCTION)
                           ;;( assign ( D_FUNCTION )
                               ;;( + (F_FUNCTION) (F_FUNCTION) )
                           ;;) ; This will be correct, if D_FUNCTION = 10
                    ;;)

                    ; must be possible to use G_FUNCTION and H_FUNCTION that comes from previous assign. (The process must update data from top to bottom)
                    ( WHEN (TEST_FUNCTION)
                        (AND
                           ( assign ( G_FUNCTION )
                               ( + (F_FUNCTION) (F_FUNCTION) )
                           ) ; This will be correct, if G_FUNCTION = 10
                           
                           ( assign ( H_FUNCTION )
                               ( + (G_FUNCTION) (D_FUNCTION) )
                           ) ; This will be correct, if H_FUNCTION = 20
                           
                           ( assign ( I_FUNCTION )
                               ( - (H_FUNCTION) (G_FUNCTION) )
                           ) ; This will be correct, if I_FUNCTION = 10
                        )
                    )
                    
                    (WHEN ( < (I_FUNCTION) 100000 )
                        ( = ( J_FUNCTION ) 1 )
                    )

		);effect
	);action grab_pen
    
    
    (:action drop_pen
		:parameters()
		:precondition()
  		:effect (AND
	      	        (PEN_POSITION ON_TABLE)
                    (HAND EMPTY)
                    (TABLE OCCUPIED)
                    (HAND_AVAILABLE)

		);effect
	);action action_b

    
); define

