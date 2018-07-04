(define (domain rb)
	(:requirements :strips :typing :conditional-effects :universal-preconditions :negative-preconditions :equality :fluents
	:fluents :domain-axioms)

	;-------
	;Types is perception nodes
	;-------
	(:types
			color_constants - constant

	);end-types

	(:constants
		RED BLUE GREEN - color_constants
		
	)

	(:predicates

		( COLOR ?c - color_constants ) :isperceivable
        ( A ) :isperceivable
        ( B ) :isperceivable
        ( C ) :isperceivable
        ( D ) :isperceivable
	)

	(:axiom
		:vars ()
		:context (A)
		:implies (B)
	)

	(:axiom
		:vars ()
		:context (COLOR RED)
		:implies (D)
	)

	(:functions

	)

	
	
    (:action action_a
		:parameters()
		:precondition()
  		:effect (AND

                    ( WHEN (B)
                        (AND
                           (COLOR RED)
                           (C)
                        )
                    )

                    ( WHEN (B)
                        (COLOR BLUE)
                    )

		);effect
	);action a

	(:action action_b
		:parameters()
		:precondition()
  		:effect (AND

                    ( WHEN (C)
                    	(AND
	                        ( NOT (A) )
	                        ( NOT( COLOR RED) )
	                    )
                    )

		);effect
	);action b

	(:action action_c
		:parameters()
		:precondition()
  		:effect (AND

                    (COLOR RED)

		);effect
	);action c

	(:action action_d
		:parameters()
		:precondition()
  		:effect (AND

                    (COLOR BLUE)

		);effect
	);action d

	(:action action_e
		:parameters()
		:precondition()
  		:effect (AND

                    ( WHEN (NOT (COLOR RED))
                    	(COLOR RED)
                    )

                    ( WHEN (COLOR BLUE)
                    	(COLOR GREEN)
                    )

		);effect
	);action d
    
); define

