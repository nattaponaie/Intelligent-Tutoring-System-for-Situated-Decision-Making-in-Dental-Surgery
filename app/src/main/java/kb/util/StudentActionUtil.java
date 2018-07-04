package kb.util;

import com.surgical.decision3.common.bean.studentaction.StudentAction;
import com.surgical.decision3.common.bean.studentaction.StudentTool;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nattapon on 8/6/2560.
 */

public class StudentActionUtil {

    public static List<ArrayList<StudentAction>> studentActionList = new ArrayList<>();

    public static String getSubprocedure_step_code()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getSubprocedure_step_code();
    }
    public static String getAction()
    {
        return studentActionList.get((studentActionList.size() - 1)).get((studentActionList.size() - 1)).getAction();
    }
    public static String getDrill_depth_from_surface()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getDrill_depth_from_surface();
    }
    public static String getEntry_Point()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getEntry_Point();
    }
    public static String getDirection()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getDirection();
    }
    public static String getDrawing_Shape()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getDrawing_Shape();
    }
    public static Boolean getIs_Coated_With_Cement()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getIs_Coated_With_Cement();
    }
    public static Boolean getIs_Rb_Tool_Selected()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getIs_Rb_Tool_Selected();
    }
    public static String getLa_solution_type()
    {
        return studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getLa_solution_type();
    }
    public static ArrayList<StudentTool> getTool_list()
    {
        ArrayList<StudentTool> toolList = new ArrayList<StudentTool>();

        for(int i=0; i<studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getTool_list().size(); i++ )
        {
            toolList.add(studentActionList.get(( studentActionList.size() - 1 )).get(( studentActionList.size() - 1 )).getTool_list().get(i));
        }

        return toolList;
    }

    public static boolean isStudentActionHasConstant()
    {
        boolean hasConstant = false;
        if(getDrill_depth_from_surface().equals(0))//in current situation 0 means null
        {
            hasConstant = true;
        }

        return hasConstant;
    }

//    public static String ToString(){
//        return "Step Code: "+getSubprocedure_step_code()+
//                "\nAction: "+getActionNode()+
//                "\nDrill Depth: "+getDrill_depth_from_surface()+
//                "\nEntry Point: "+getEntry_Point()+
//                "\nDirection: "+getDirection()+
//                "\nDrawing Shape: "+getDrawing_Shape()+
//                "\nIs coated with cement: "+getIs_Coated_With_Cement()+
//                "\nSolution Type: "+getSolution_Type();
//    }
}
