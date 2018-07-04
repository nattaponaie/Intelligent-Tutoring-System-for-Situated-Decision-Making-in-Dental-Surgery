package kb.util;

import com.surgical.decision3.common.bean.studentaction.StudentAction;
import com.surgical.decision3.common.bean.studentaction.StudentTool;
import com.surgical.decision3.common.controller.ApplicationController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.uga.pddl4j.parser.Domain;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.NamedTypedList;
import fr.uga.pddl4j.parser.Op;
import fr.uga.pddl4j.parser.Symbol;
import fr.uga.pddl4j.parser.TypedSymbol;
import fr.uga.pddl4j.parser.TypedSymbolValue;
import kb.common.controller.ParserController;
import kb.common.datastructure.useraction.ActionDetails;
import kb.common.datastructure.useraction.UserAction;
import kb.common.datastructure.useraction.UserActionDetails;
import kb.common.datastructure.useraction.UserActionParameter;

import static com.surgical.decision3.common.controller.ApplicationController.action_predicate_map;
import static com.surgical.decision3.common.controller.ApplicationController.action_predicate_map_reverse;
import static com.surgical.decision3.common.controller.ApplicationController.pair_user_action_map;
import static com.surgical.decision3.common.controller.ApplicationController.user_action_predicate_map;
import static kb.util.StudentActionUtil.studentActionList;

/**
 * Created by Nattapon on 13/6/2560.
 */

public class UserActionUtil {

    private static Op groundAction = OpUtil.groundActionList.get(0);

    public static UserAction transformIntoQuantitativeAndQualitative(Domain domain) {
        UserAction uAction = transformStudentIntoBothFormat(domain);
        return uAction;
    }

    public static UserAction transformStudentIntoBothFormat(Domain domain)//for both qualitative and quantitative
    {
        ArrayList<NamedTypedList> dFuncList = (ArrayList<NamedTypedList>) domain.getFunctions();

        UserAction ua = new UserAction();

        List<TypedSymbolValue> params = new ArrayList<TypedSymbolValue>();

        //Set name
        Symbol name = new Symbol(Symbol.Kind.ACTION, StudentActionUtil.getAction().toString());
        ua.setName(name);

        //check if that action has parameter
        if (!groundAction.getParameters().isEmpty()) {
            for (TypedSymbol paramList : groundAction.getParameters()) {
//                System.out.println("groundAction Parameter " + paramList.getImage().toString() +" "+ paramList.getTypes().toString());
                Symbol ps2 = new Symbol(Symbol.Kind.TYPE, paramList.getTypes().toString());
                TypedSymbol ts2 = new TypedSymbol(ps2);
                params.add(new TypedSymbolValue(ts2, paramList.getImage().toString(), null));
            }
        }


        for (ActionDetails ad : UserActionDetails.actionWithPredicate) {
            for (NamedTypedList dFunction : dFuncList)
            {
                //if that predicate match to domain function
                if (ad.getPredicate().toLowerCase().equals(dFunction.getName().toString())) {
//                    System.out.println(dFunction.getName());

                    Double value = 0.0;

                    Pattern p = Pattern.compile("[a-zA-Z]");
                    Matcher m = p.matcher(ad.getValue());

                    if(!m.find())
                    {// if this string does not contain A-Z

                        value = Double.parseDouble(ad.getValue());
                    }

                    Symbol ps = new Symbol(Symbol.Kind.TYPE, dFunction.getName().toString());
                    TypedSymbol ts = new TypedSymbol(ps);
                    params.add(new TypedSymbolValue(ts, value, null));

                    UserActionParameter uaParameter = new UserActionParameter();
                    uaParameter.setDoubleValue(value);
                    Exp exp = uaParameter.transformValueToExp_withoutVariable(uaParameter.getDoubleValue(),
                            dFunction.getName().toString(),
                            ParserController.domain.getFunctions(),    ///domain.getFunctions(),
                            InferenceEngineUtil.typeMap);
                    uaParameter.setValueForCompare(exp);

                    ua.userActionWithValue.add(uaParameter);

                    break;
                }
            }
            //check if that input has parameter
            if (ad.getValue() != null) {
                UserActionParameter uaParameter = new UserActionParameter();
                uaParameter.setExpValueParameter(ad.getValue());
                Exp d_exp = uaParameter.transformActionConstantToExpression(ad.getPredicate(), uaParameter.getExpValueParameter());
                uaParameter.setValueExp(d_exp);
                ua.userActionWithValue.add(uaParameter);
            } else //if that input has no parameter then store as a predicate
            {
                UserActionParameter uaParameter = new UserActionParameter();
                Exp pred_exp = uaParameter.createPredicateUserAction(ad.getPredicate(), ad.isNegative());
                ua.addUser_action_predicate(pred_exp);
                ua.userActionWithValue.add(uaParameter);
            }
//            System.err.println(ad.getFactPredicate() + " " + ad.getValue() + " " + ad.isNegative());
        }
        ua.setParameters(params);
//        ua.setActionParameter(uaParameter);

        return ua;
    }

//    public static String getActionName()//change input action name to the correct for comparing in domain.pddl
//    {
//        String actionName = "";
//
//        if (StudentActionUtil.getActionNode().equals("Insert_Rubber_Dam")) {
//                actionName = "insert_rb";
//        } else if (StudentActionUtil.getActionNode().equals("Insert_TMC")) {
//                actionName = "insert_tmc";
//        }
//        return actionName;
//    }

    public static void initialUserActionMapping(InputStream file)
    {
        BufferedReader bfr;
        try {

            bfr = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            String line;

//            boolean isStepMatched = false;

            while ((line = bfr.readLine()) != null)
            {
                if(line.trim().length() == 0)
                {
                    continue;
                }

                if (line.substring(0, 2).equals("//"))//keep checking if that line not contain predicate then break;
                {
                    continue;
                }

                String[] keys = line.split("=");

                action_predicate_map.put(keys[0].toLowerCase(), keys[1].toLowerCase());
                action_predicate_map_reverse.put(keys[1].toLowerCase(), keys[0].toLowerCase());

//                if (!isStepMatched) {//check the step is matched or not
//
//                    if (line.substring(0, 2).equals("//")) {//check the step
//
//                        String[] step = line.split("//");
//
//                        if (step[1].equals(endodontic_step)) {
//                            isStepMatched = true;//step is matched
//                        }
//                    }
//                } else if (isStepMatched)//if the input step is matched
//                {
//                    if (line.substring(0, 2).equals("//"))//keep checking if that line not contain predicate then break;
//                    {
//                        break;
//                    }
//                    String[] keys = line.split("=");
//
//                    action_predicate_map.put(keys[0], keys[1]);
//                }
            }

            bfr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getUserActionPredicate()
    {
        //before accept action, clear the user data first
        clearDataBeforeAcceptNewAction();

        createStudentActionStructure();

        compareStudentActionToGetPredicate();
    }

    private static void clearDataBeforeAcceptNewAction()
    {
//        action_predicate_map.clear();
        user_action_predicate_map.clear();
        UserActionDetails.actionWithPredicate.clear();
    }

    private static void compareStudentActionToGetPredicate()
    {
        for (Map.Entry<String, String> action_pred_map : action_predicate_map.entrySet()) {
            for (Map.Entry<String, String> user_pred_map : user_action_predicate_map.entrySet()) {
                if (user_pred_map.getKey().equals(action_pred_map.getKey())) {

                    ActionDetails ad = new ActionDetails();
                    ad.setPredicate(action_pred_map.getValue());
                    ad.setNegative(false);
                    ad.setValue(user_pred_map.getValue());
                    UserActionDetails.actionWithPredicate.add(ad);

//                    System.err.println("Equal!! "+ action_pred_map.getValue());
                }
//                System.err.println("toey "+ user_pred_map.getKey() +"/"+ action_pred_map.getKey());
            }
        }
    }

    private static void createStudentActionStructure() {

        String action_name = ApplicationController.currentAction.getActionName();
        String actionWithPredicateString = null;
        String connector = "-";
//        String step_sub_string = action_name.substring(0, action_name.length() - 1);//remove 1,2,3 from step code e.g RB1 OC2

        for (ArrayList<StudentAction> sActionList : studentActionList) {
            for (StudentAction sActionDetails : sActionList) {
                for (StudentTool sActionTool : sActionDetails.getTool_list()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    if (!sActionTool.getTool_Type().isEmpty()) {
                        actionWithPredicateString = actionWithPredicateString.concat("tool_type");
                        user_action_predicate_map.put(actionWithPredicateString, sActionTool.getTool_Type().toLowerCase());
                    }
                    //reset string e.g RB-insert_rubber_dam-
//                    actionWithPredicateString = resetString(step_sub_string);
//                    if (!sATool.getTool_Size().equals("")) {
//                        actionWithPredicateString = actionWithPredicateString.concat("tool_size");
//                        user_action_predicate_map.put(actionWithPredicateString, sATool.getTool_Size());
//                    }

                }
                if (!sActionDetails.getDrill_depth_from_surface().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("depth");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getDrill_depth_from_surface());
                }
                if (!sActionDetails.getEntry_Point().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("entry_point");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getEntry_Point());
                }
                if (!sActionDetails.getDirection().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("direction");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getDirection());
                }
                if (!sActionDetails.getDrawing_Shape().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("shape");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getDrawing_Shape());
                }
                if (!sActionDetails.getTool_Size().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("tool_size");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getTool_Size());
                }
                if (sActionDetails.getIs_Rb_Tool_Selected()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("tool_set");
                    user_action_predicate_map.put(actionWithPredicateString, "rb_tool_set");
                }
                if (!sActionDetails.getRb_sheet_pos().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("rubber_sheet_position");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getRb_sheet_pos());
                }
                if (!sActionDetails.getLa_solution_type().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("la_solution_type");
                    if(sActionDetails.getLa_solution_type().equals("Lidocaine with Epinephrine"))
                    {
                        user_action_predicate_map.put(actionWithPredicateString, "Lidocaine");
                    }
                    else
                    {
                        user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getLa_solution_type());
                    }
                }
                if (!sActionDetails.getLa_solution_amt().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("la_solution_amt");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getLa_solution_amt() );
                }
                if (!sActionDetails.getLandmark().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("landmark");

                    String action_value = "";
                    if(sActionDetails.getLandmark().equals("Between coronoid notch and pterygomandibular raphae"))
                    {
                        action_value = "BETWEEN_CORONOID_NOTCH_AND_PTERYGOMANDIBULAR_RAPHAE";
                        user_action_predicate_map.put(actionWithPredicateString, action_value);
                    }
                    else if(sActionDetails.getLandmark().equals("At coronoid notch"))
                    {
                        action_value = "AT_CORONOID_NOTCH";
                        user_action_predicate_map.put(actionWithPredicateString, action_value);
                    }
                    else if(sActionDetails.getLandmark().equals("At pterygomandibular raphae"))
                    {
                        action_value = "AT_PTERYGOMANDIBULAR_RAPHAE";
                        user_action_predicate_map.put(actionWithPredicateString, action_value);
                    }

                }
                if (!sActionDetails.getTool_length().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("tool_length");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getTool_length());
                }
                if (!sActionDetails.getReference_tooth_no().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("reference_tooth_no");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getReference_tooth_no());
                }
                if (!sActionDetails.getRemain_depth().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("remain_depth");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getRemain_depth());
                }
                if (!sActionDetails.getRetract_depth().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("retract_depth");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getRetract_depth());
                }
                if (!sActionDetails.getSolution_amount().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("solution_amount");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getSolution_amount());
                }
                if (!sActionDetails.getTargetNumb().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("target");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getTargetNumb());
                }

                //LT1
                if (!sActionDetails.getRcDepthToCurrentFile().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("rc_depth_to_current_file");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getRcDepthToCurrentFile());
                }

                //MI1
                if (!sActionDetails.getInitialToolSize().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("initial_tool_size");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getInitialToolSize());
                }
                if (!sActionDetails.getFinalToolSize().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("final_tool_size");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getFinalToolSize());
                }
                if (!sActionDetails.getSizeDiffIncremental().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("size_diff");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getSizeDiffIncremental());
                }

                //MI2
                if (!sActionDetails.getRcDrillDepth().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("rc_drill_depth");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getRcDrillDepth());
                }

                //MI4
                if (!sActionDetails.getToolSizeFunc().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("tool_size_func");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getToolSizeFunc());
                }

                //TMC1
                if (!sActionDetails.getMCSize().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("main_cone_size");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getMCSize());
                }

                //Press and cut cones
                if (!sActionDetails.getCutConesDepth().isEmpty()) {
                    //reset string e.g RB-insert_rubber_dam-
                    actionWithPredicateString = resetString(action_name);
                    actionWithPredicateString = actionWithPredicateString.concat("cut_depth");
                    user_action_predicate_map.put(actionWithPredicateString, sActionDetails.getCutConesDepth());
                }
            }
        }
    }

    private static String resetString(String actionName)
    {
        String connector = "-";
        String actionWithPredicateString = "";
        actionWithPredicateString = actionWithPredicateString.concat(actionName);
        actionWithPredicateString = actionWithPredicateString.concat(connector);
//        actionWithPredicateString = actionWithPredicateString.concat(StudentActionUtil.getActionNode().toLowerCase());
//        actionWithPredicateString = actionWithPredicateString.concat(connector);
        return actionWithPredicateString;
    }

    public static void addActionManual(String pred, String value, boolean isNegative) {
        ActionDetails ad = new ActionDetails();
        ad.setPredicate(pred);
        ad.setNegative(isNegative);
        ad.setValue(value);
        UserActionDetails.actionWithPredicate.add(ad);
    }

    public static void initializeComparisonPairs(InputStream file) {

        BufferedReader bfr;

        try {

            bfr = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            String line;


            while ((line = bfr.readLine()) != null) {

                if (!line.isEmpty() )
                {
                    String[] pair = line.trim().split("=");

                    pair_user_action_map.put( pair[0], pair[1] );
                }

            }

            bfr.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
