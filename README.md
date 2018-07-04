- Parser
-
- For parsing rules, please see readme.md in pddl_parser project
- To see all parsed planAction data after initialized, please find string "All PlanActions obtained from parser" in Android monitor

- Graph
- 
- NOTE please change the buffer size in Settings -> Search -> type 'buffer' -> Console -> tick override console cycle buffer size
- this is because in printing graph process there are a lot of line and characters so you have to increase the buffer size
- in order to see the complete output
        

        - Student Graph

        StudentGraphUtil.printAllStudentGraph();
        
        - Tutor Graph
        
        TutorGraphUtil.printAllTutorGraph();


- User Action
- 


        Select Action: select_anesthesia_type
    	LA_SOLUTION_TYPE : LIDOCAINE
    	
        Select Action: pierce_anesthesia_needle
    	Local Anesthesia Landmark : Between coronoid notch and pterygomandibular raphae
    	Reference Point : WT44
    	
    	Select Action: inject_anesthesia_and_draw
    	Needle Depth : 1.0
    	
    	Select Action: check_numb
    	Target : LOWER_LEFT_LIP

        Select Action: insert_rubber_dam

    	Drill Size	: C0
    	Select RB Tool	: check/tick
    	RB SHEET POS	: Under
    	Add tool	: Clamp
    
        Select Action: drill_to_pulp_chamber
    
    	Entry Point	: OCCLUSAL
    	Direction	: APICAL
    	SHAPE		: OVAL
    	Drill Depth	: 7
    	Drill Size	: 9
    	Add Tool	: oneof(steel_round_bur, round_diamond_bur)
	
- updated 5/7/2017

- updated 3/7/2017 finished until task 26 (only toey's tasks)
- updated 27/6/2017 finished until task 18 (only toey's tasks)
- updated 30/5/2017