package com.surgical.decision3.functions;

import com.surgical.decision3.common.bean.studentaction.StudentAction;
import com.surgical.decision3.common.bean.studentaction.StudentTool;

import java.util.ArrayList;

/**
 * Created by Nattapon on 24/5/2560.
 */

public class StudentActionQuery {

    private static StudentAction studentAction;

    public static ArrayList<StudentAction> getStudentActions(String Action, ArrayList<StudentTool> StudentToolInput
                                                            , String DrillDepth
                                                            , String Entry_Point, String Direction
                                                            , String Drawing_Shape, Boolean Is_Coated_With_Cement
                                                            , String Tool_Size
                                                            , Boolean Is_Rb_Tool_Selected
                                                            , String RB_SHEET_POS
                                                            , String LaSolutionType, String LaSolutionAmt, String Landmark
                                                            , String Tool_Length , String Reference_Tooth_No
                                                            , String Remain_Depth , String Retract_Depth
                                                            , String Solution_Amount , String TargetNumb
                                                             , String RcDepthToCurrentFile
                                                             , String InitialToolSize, String FinalToolSize, String SizeDiffIncremental
                                                             , String RcDrillDepth
                                                             , String ToolSizeFunc
                                                             , String MCSize
                                                             , String CutConesDepth
    )
    {

        ArrayList<StudentAction> studentActionList = new ArrayList<StudentAction>();
        studentAction = new StudentAction();
        studentAction.setAction(Action);
//        if(!StudentToolInput.isEmpty())studentAction.setTool_List(StudentToolInput);
        studentAction.setTool_List(StudentToolInput);
        studentAction.setDrill_depth_from_surface(DrillDepth);
        studentAction.setEntry_Point(Entry_Point);
        studentAction.setDirection(Direction);
        studentAction.setDrawing_Shape(Drawing_Shape);
        studentAction.setIs_Coated_With_Cement(Is_Coated_With_Cement);
        studentAction.setIs_Rb_Tool_Selected(Is_Rb_Tool_Selected);
        studentAction.setTool_Size(Tool_Size);
        studentAction.setRb_sheet_pos(RB_SHEET_POS);

        //Nui add LA
        studentAction.setLa_solution_type( LaSolutionType );
        studentAction.setLa_solution_amt( LaSolutionAmt );
        studentAction.setSolution_amount( Solution_Amount );
        studentAction.setLandmark( Landmark );
        studentAction.setTool_length( Tool_Length );
        studentAction.setReference_tooth_no( Reference_Tooth_No );
        studentAction.setRemain_depth( Remain_Depth );
        studentAction.setRetract_depth( Retract_Depth );
        studentAction.setTargetNumb( TargetNumb );

        //LT1
        studentAction.setRcDepthToCurrentFile(RcDepthToCurrentFile);

        //MI1
        studentAction.setInitialToolSize(InitialToolSize);
        studentAction.setFinalToolSize(FinalToolSize);
        studentAction.setSizeDiffIncremental(SizeDiffIncremental);

        //MI2
        studentAction.setRcDrillDepth(RcDrillDepth);

        //MI4
        studentAction.setToolSizeFunc(ToolSizeFunc);

        //TMC1
        studentAction.setMCSize(MCSize);

        //Press and cut cones
        studentAction.setCutConesDepth(CutConesDepth);

        studentActionList.add( studentAction );


        return studentActionList;
    }
}
